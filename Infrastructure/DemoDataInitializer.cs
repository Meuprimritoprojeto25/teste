using FerroGestao.Domain;
using FerroGestao.Services;
using Microsoft.EntityFrameworkCore;
using System.Security.Cryptography;
using System.Text;

namespace FerroGestao.Infrastructure;

public static class DemoDataInitializer
{
    public static async Task InitializeAsync(FerroGestaoContext db)
    {
        if (await db.Users.AnyAsync()) return;

        var marina = User("Marina Soares", "marina.soares@ferrogestao.com.br", "FG-0142", "Gerência Industrial", "ADMINISTRADOR");
        var carlos = User("Carlos Nogueira", "carlos.nogueira@ferrogestao.com.br", "FG-0287", "Aciaria", "GESTOR");
        var luciana = User("Luciana Reis", "luciana.reis@ferrogestao.com.br", "FG-0311", "Manutenção", "GESTOR");
        db.Users.AddRange(marina, carlos, luciana);
        await db.SaveChangesAsync();

        var items = new[]
        {
            Item("PRD-001", "Produção de aço bruto", "Aciaria", "t/dia", 4850, 4600, Direction.HigherIsBetter, marina),
            Item("ENE-004", "Consumo específico de energia", "Utilidades", "kWh/t", 465, 490, Direction.LowerIsBetter, carlos),
            Item("QLD-012", "Índice de conformidade química", "Laboratório", "%", 98.5, 96, Direction.HigherIsBetter, carlos),
            Item("MAN-008", "Disponibilidade dos altos-fornos", "Manutenção", "%", 94, 90, Direction.HigherIsBetter, luciana),
            Item("AMB-003", "Geração específica de escória", "Meio ambiente", "kg/t", 285, 310, Direction.LowerIsBetter, marina),
            Item("SEG-001", "Taxa de frequência de acidentes", "Segurança", "taxa", .25, .5, Direction.LowerIsBetter, marina)
        };
        db.ControlItems.AddRange(items);
        await db.SaveChangesAsync();

        var yesterday = DateOnly.FromDateTime(DateTime.UtcNow.AddDays(-1));
        foreach (var (item, actual, analysis, action) in new[]
        {
            (items[0], 4928d, "Ritmo estável após normalização do lingotamento.", ""),
            (items[1], 478d, "Consumo acima da meta no forno-panela.", "Revisar curva de aquecimento e perdas até sexta-feira."),
            (items[2], 99.1d, "Composição química dentro da faixa.", ""),
            (items[3], 88.7d, "Parada não programada no sistema de sopro.", "Inspecionar válvulas e executar preventiva na janela de domingo."),
            (items[4], 301d, "Maior teor de ganga no lote de minério.", "Segregar lote e ajustar mistura da carga."),
            (items[5], .18d, "Período sem acidente com afastamento.", "Manter diálogos de segurança nos turnos.")
        })
        {
            var status = IndicatorRules.CalculateStatus(item, actual);
            db.FollowUps.Add(new FollowUp { Item = item, ReferenceDate = yesterday, ActualValue = actual, Status = status, Analysis = analysis, ActionPlan = action });
            db.KpiSnapshots.Add(new KpiSnapshot { Item = item, ReferenceDate = yesterday, TargetValue = item.Target, ActualValue = actual, Status = status, Achievement = IndicatorRules.CalculateAchievement(item, actual) });
        }
        db.Deployments.AddRange(
            new Deployment { ParentItem = items[0], ChildItem = items[3], Weight = 35, Rationale = "Disponibilidade dos fornos direciona o volume diário." },
            new Deployment { ParentItem = items[0], ChildItem = items[1], Weight = 20, Rationale = "Eficiência energética sustenta custo competitivo." });
        db.ProductionRecords.AddRange(
            Production("A — 06h às 14h", 1620, 1750, 110, 740, 98.8, 18),
            Production("B — 14h às 22h", 1598, 1730, 105, 752, 98.2, 27),
            Production("C — 22h às 06h", 1710, 1830, 120, 770, 99, 5));
        var driver = new Driver { Name = "João Batista Lima", Registration = "MOT-1048", Cpf = "123.456.789-00", LicenseNumber = "00984512011", LicenseCategory = "E" };
        db.Drivers.Add(driver);
        db.DriverTrainings.Add(new DriverTraining { Driver = driver, Course = "Direção defensiva e circulação em área industrial", Instructor = "Centro de Segurança Operacional", CompletionDate = DateOnly.FromDateTime(DateTime.UtcNow.AddDays(-330)), ExpirationDate = DateOnly.FromDateTime(DateTime.UtcNow.AddDays(35)), WorkloadHours = 16, Score = 92 });
        var procedure = new ManagedDocument { Code = "PO-ACI-014", Title = "Partida segura do forno elétrico", Category = "Procedimento operacional", Owner = carlos, Status = RecordStatus.Approved, Content = "Estabelece a sequência segura para inspeção e partida do forno elétrico." };
        db.Documents.Add(procedure);
        db.DocumentRevisions.Add(new DocumentRevision { Document = procedure, RevisionNumber = 1, Content = procedure.Content, ChangeReason = "Emissão inicial", Author = carlos });
        db.R3GReports.Add(new R3GReport { Title = "R3G mensal — desempenho da Aciaria", Area = "Aciaria", ReferenceDate = DateOnly.FromDateTime(DateTime.UtcNow), Author = marina, Status = RecordStatus.InReview, ResultSummary = "Produção superou a meta do período.", Gaps = "Consumo específico de energia e disponibilidade requerem atenção.", Gains = "Redução de retrabalho e maior conformidade química.", NextSteps = "Concluir análise de falha e revisar curva de aquecimento." });
        await db.SaveChangesAsync();
    }

    private static User User(string name, string email, string registration, string department, string role) => new()
    {
        Name = name, Email = email, Registration = registration, Department = department, Role = role,
        PasswordHash = Convert.ToHexString(SHA256.HashData(Encoding.UTF8.GetBytes("Ferro@123")))
    };
    private static ControlItem Item(string code, string name, string area, string unit, double target, double warning, Direction direction, User owner) =>
        new() { Code = code, Name = name, Area = area, Unit = unit, Target = target, WarningLimit = warning, Direction = direction, Owner = owner, Periodicity = "Diário" };
    private static ProductionRecord Production(string shift, double output, double ore, double scrap, double energy, double quality, int downtime) =>
        new() { ProductionDate = DateOnly.FromDateTime(DateTime.UtcNow.AddDays(-1)), Plant = "Usina Sudeste", Furnace = shift == "C — 22h às 06h" ? "Alto-forno 02" : "Alto-forno 01", Shift = shift, ProducedTons = output, OreTons = ore, ScrapTons = scrap, EnergyMwh = energy, QualityIndex = quality, DowntimeMinutes = downtime };
}