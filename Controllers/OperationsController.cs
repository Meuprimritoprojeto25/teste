using FerroGestao.Infrastructure;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FerroGestao.Controllers;

public sealed record CatalogViewModel(string Title, string NewPath, IReadOnlyList<string> Headers, IReadOnlyList<IReadOnlyList<string>> Rows);

/// <summary>Read screens for the operational modules retained from the Java application.</summary>
public sealed class OperationsController(FerroGestaoContext db) : Controller
{
    [HttpGet("/itens-controle")]
    public async Task<IActionResult> Items(CancellationToken ct) => View("Catalog", new CatalogViewModel("Itens de controle", "/api/itens-controle",
        ["Código", "Indicador", "Área", "Meta", "Direção"],
        (await db.ControlItems.AsNoTracking().OrderBy(x => x.Code).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Code, x.Name, x.Area, $"{x.Target:N2} {x.Unit}", x.Direction.ToString()]).ToList()));

    [HttpGet("/acompanhamentos")]
    public async Task<IActionResult> FollowUps(CancellationToken ct) => View("Catalog", new CatalogViewModel("Farol e acompanhamentos", "/api/acompanhamentos",
        ["Item", "Referência", "Realizado", "Status", "Análise"],
        (await db.FollowUps.Include(x => x.Item).AsNoTracking().OrderByDescending(x => x.ReferenceDate).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Item.Code, x.ReferenceDate.ToString("dd/MM/yyyy"), x.ActualValue.ToString("N2"), x.Status.ToString(), x.Analysis ?? "—"]).ToList()));

    [HttpGet("/desdobramentos")]
    public async Task<IActionResult> Deployments(CancellationToken ct) => View("Catalog", new CatalogViewModel("Desdobramentos", "#",
        ["Indicador pai", "Indicador filho", "Peso", "Justificativa"],
        (await db.Deployments.Include(x => x.ParentItem).Include(x => x.ChildItem).AsNoTracking().ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.ParentItem.Code, x.ChildItem.Code, $"{x.Weight:N1}%", x.Rationale ?? "—"]).ToList()));

    [HttpGet("/producao")]
    public async Task<IActionResult> Production(CancellationToken ct) => View("Catalog", new CatalogViewModel("Produção siderúrgica", "/api/producao",
        ["Data", "Planta", "Forno", "Turno", "Produção"],
        (await db.ProductionRecords.AsNoTracking().OrderByDescending(x => x.ProductionDate).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.ProductionDate.ToString("dd/MM/yyyy"), x.Plant, x.Furnace, x.Shift, $"{x.ProducedTons:N0} t"]).ToList()));

    [HttpGet("/treinamentos")]
    public async Task<IActionResult> Training(CancellationToken ct) => View("Catalog", new CatalogViewModel("Treinamentos de motorista", "#",
        ["Motorista", "Curso", "Conclusão", "Vencimento", "Nota"],
        (await db.DriverTrainings.Include(x => x.Driver).AsNoTracking().ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Driver.Name, x.Course, x.CompletionDate.ToString("dd/MM/yyyy"), x.ExpirationDate.ToString("dd/MM/yyyy"), x.Score?.ToString("N1") ?? "—"]).ToList()));

    [HttpGet("/documentos")]
    public async Task<IActionResult> Documents(CancellationToken ct) => View("Catalog", new CatalogViewModel("Documentos controlados", "#",
        ["Código", "Título", "Categoria", "Situação", "Revisão"],
        (await db.Documents.AsNoTracking().OrderBy(x => x.Code).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Code, x.Title, x.Category ?? "—", x.Status.ToString(), x.CurrentRevision.ToString()]).ToList()));

    [HttpGet("/r3g")]
    public async Task<IActionResult> R3G(CancellationToken ct) => View("Catalog", new CatalogViewModel("Relatórios R3G", "#",
        ["Título", "Área", "Referência", "Situação"],
        (await db.R3GReports.AsNoTracking().OrderByDescending(x => x.ReferenceDate).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Title, x.Area, x.ReferenceDate.ToString("dd/MM/yyyy"), x.Status.ToString()]).ToList()));

    [HttpGet("/usuarios")]
    public async Task<IActionResult> Users(CancellationToken ct) => View("Catalog", new CatalogViewModel("Usuários e acessos", "/api/usuarios",
        ["Nome", "E-mail", "Área", "Perfil", "Ativo"],
        (await db.Users.AsNoTracking().OrderBy(x => x.Name).ToListAsync(ct)).Select(x => (IReadOnlyList<string>)[x.Name, x.Email, x.Department ?? "—", x.Role ?? "—", x.Active ? "Sim" : "Não"]).ToList()));

    [HttpGet("/relatorios")]
    public IActionResult Reports() => View("Catalog", new CatalogViewModel("Central de relatórios", "#",
        ["Relatório", "Formato", "Disponibilidade"], [["Farol gerencial", "JSON", "API em /api/farol"], ["Dados operacionais", "HTML", "Módulos acima"]]));
}