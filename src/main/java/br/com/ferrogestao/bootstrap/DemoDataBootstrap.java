package br.com.ferrogestao.bootstrap;

import br.com.ferrogestao.domain.ControlItem;
import br.com.ferrogestao.domain.Deployment;
import br.com.ferrogestao.domain.Driver;
import br.com.ferrogestao.domain.DriverTraining;
import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.ManagedDocument;
import br.com.ferrogestao.domain.ProductionRecord;
import br.com.ferrogestao.domain.R3GReport;
import br.com.ferrogestao.domain.User;
import br.com.ferrogestao.domain.enums.Direction;
import br.com.ferrogestao.domain.enums.RecordStatus;
import br.com.ferrogestao.service.DeploymentService;
import br.com.ferrogestao.service.DocumentService;
import br.com.ferrogestao.service.IndicatorService;
import br.com.ferrogestao.service.ProductionService;
import br.com.ferrogestao.service.R3GService;
import br.com.ferrogestao.service.TrainingService;
import br.com.ferrogestao.service.UserService;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DemoDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired private UserService users;
    @Autowired private IndicatorService indicators;
    @Autowired private DeploymentService deployments;
    @Autowired private TrainingService trainings;
    @Autowired private DocumentService documents;
    @Autowired private R3GService r3g;
    @Autowired private ProductionService production;
    private boolean initialized;

    public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized || event.getApplicationContext().getParent() != null || users.count() > 0) { return; }
        initialized = true;

        User manager = user("Marina Soares", "marina.soares@ferrogestao.com.br", "FG-0142", "Gerência Industrial", "ADMINISTRADOR");
        User process = user("Carlos Nogueira", "carlos.nogueira@ferrogestao.com.br", "FG-0287", "Aciaria", "GESTOR");
        User maintenance = user("Luciana Reis", "luciana.reis@ferrogestao.com.br", "FG-0311", "Manutenção", "GESTOR");

        ControlItem productionItem = item("PRD-001", "Produção de aço bruto", "Aciaria", "t/dia", 4850d, 4600d, Direction.HIGHER_IS_BETTER, manager);
        ControlItem energy = item("ENE-004", "Consumo específico de energia", "Utilidades", "kWh/t", 465d, 490d, Direction.LOWER_IS_BETTER, process);
        ControlItem quality = item("QLD-012", "Índice de conformidade química", "Laboratório", "%", 98.5d, 96d, Direction.HIGHER_IS_BETTER, process);
        ControlItem availability = item("MAN-008", "Disponibilidade dos altos-fornos", "Manutenção", "%", 94d, 90d, Direction.HIGHER_IS_BETTER, maintenance);
        ControlItem slag = item("AMB-003", "Geração específica de escória", "Meio ambiente", "kg/t", 285d, 310d, Direction.LOWER_IS_BETTER, manager);
        ControlItem safety = item("SEG-001", "Taxa de frequência de acidentes", "Segurança", "taxa", 0.25d, 0.5d, Direction.LOWER_IS_BETTER, manager);

        follow(productionItem, 4928d, "Ritmo estável após normalização do lingotamento.", "");
        follow(energy, 478d, "Consumo acima da meta no forno-panela.", "Revisar curva de aquecimento e perdas até sexta-feira.");
        follow(quality, 99.1d, "Composição química dentro da faixa.", "");
        follow(availability, 88.7d, "Parada não programada no sistema de sopro.", "Inspecionar válvulas e executar preventiva na janela de domingo.");
        follow(slag, 301d, "Maior teor de ganga no lote de minério.", "Segregar lote e ajustar mistura da carga.");
        follow(safety, 0.18d, "Período sem acidente com afastamento.", "Manter diálogos de segurança nos turnos.");

        Deployment deployment = new Deployment();
        deployment.setParentItem(productionItem); deployment.setChildItem(availability); deployment.setWeight(35d);
        deployment.setRationale("Disponibilidade dos fornos é direcionador direto do volume diário.");
        deployments.save(deployment);
        deployment = new Deployment();
        deployment.setParentItem(productionItem); deployment.setChildItem(energy); deployment.setWeight(20d);
        deployment.setRationale("Eficiência energética sustenta ritmo e custo competitivo.");
        deployments.save(deployment);

        Driver driver = new Driver();
        driver.setName("João Batista Lima"); driver.setRegistration("MOT-1048"); driver.setCpf("123.456.789-00");
        driver.setLicenseNumber("00984512011"); driver.setLicenseCategory("E");
        driver = trainings.saveDriver(driver);
        DriverTraining training = new DriverTraining();
        training.setDriver(driver); training.setCourse("Direção defensiva e circulação em área industrial");
        training.setInstructor("Centro de Segurança Operacional"); training.setCompletionDate(days(-330));
        training.setExpirationDate(days(35)); training.setWorkloadHours(16); training.setScore(92d);
        trainings.saveTraining(training);

        ManagedDocument procedure = new ManagedDocument();
        procedure.setCode("PO-ACI-014"); procedure.setTitle("Partida segura do forno elétrico");
        procedure.setCategory("Procedimento operacional"); procedure.setOwner(process); procedure.setStatus(RecordStatus.APPROVED);
        procedure.setContent("1. OBJETIVO\nEstabelecer a sequência segura para inspeção e partida do forno elétrico.\n\n"
                + "2. RESPONSABILIDADES\nO líder de turno deve confirmar o checklist, os intertravamentos e a liberação da manutenção.\n\n"
                + "3. PROCEDIMENTO\nVerificar refratário, sistema hidráulico, exaustão e ausência de pessoas na área restrita antes da energização.");
        documents.save(procedure, "Emissão inicial", process);

        R3GReport report = new R3GReport();
        report.setTitle("R3G mensal — desempenho da Aciaria"); report.setArea("Aciaria"); report.setReferenceDate(new Date());
        report.setAuthor(manager); report.setStatus(RecordStatus.IN_REVIEW);
        report.setResultSummary("A produção superou a meta do período, com estabilidade no lingotamento e recuperação do ritmo dos conversores.");
        report.setGaps("Consumo específico de energia e disponibilidade do alto-forno ficaram na faixa de atenção.");
        report.setGains("Redução de retrabalho, maior conformidade química e melhor aderência ao plano diário.");
        report.setNextSteps("Concluir análise de falha do sistema de sopro e revisar a curva de aquecimento do forno-panela.");
        r3g.save(report);

        production("Usina Sudeste", "Alto-forno 01", "A — 06h às 14h", 1620d, 1750d, 110d, 740d, 98.8d, 18);
        production("Usina Sudeste", "Alto-forno 01", "B — 14h às 22h", 1598d, 1730d, 105d, 752d, 98.2d, 27);
        production("Usina Sudeste", "Alto-forno 02", "C — 22h às 06h", 1710d, 1830d, 120d, 770d, 99.0d, 5);
    }

    private User user(String name, String email, String registration, String department, String role) {
        User value = new User(); value.setName(name); value.setEmail(email); value.setRegistration(registration);
        value.setDepartment(department); value.setRole(role); return users.save(value, "Ferro@123");
    }
    private ControlItem item(String code, String name, String area, String unit, double target, double warning,
                             Direction direction, User owner) {
        ControlItem value = new ControlItem(); value.setCode(code); value.setName(name); value.setArea(area); value.setUnit(unit);
        value.setTarget(target); value.setWarningLimit(warning); value.setDirection(direction); value.setOwner(owner);
        value.setPeriodicity("Diário"); return indicators.saveItem(value);
    }
    private void follow(ControlItem item, double actual, String analysis, String action) {
        FollowUp value = new FollowUp(); value.setItem(item); value.setReferenceDate(days(-1)); value.setActualValue(actual);
        value.setAnalysis(analysis); value.setActionPlan(action); indicators.record(value);
    }
    private void production(String plant, String furnace, String shift, double output, double ore, double scrap,
                            double energy, double quality, int downtime) {
        ProductionRecord value = new ProductionRecord(); value.setProductionDate(days(-1)); value.setPlant(plant);
        value.setFurnace(furnace); value.setShift(shift); value.setProducedTons(output); value.setOreTons(ore);
        value.setScrapTons(scrap); value.setEnergyMwh(energy); value.setQualityIndex(quality);
        value.setDowntimeMinutes(downtime); production.save(value);
    }
    private Date days(int amount) {
        Calendar calendar = Calendar.getInstance(); calendar.add(Calendar.DAY_OF_MONTH, amount); return calendar.getTime();
    }
}