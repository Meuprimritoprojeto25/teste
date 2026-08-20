using FerroGestao.Domain;
using FerroGestao.Infrastructure;
using FerroGestao.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FerroGestao.Controllers;

[ApiController]
[Route("api")]
public sealed class ApiController(FerroGestaoContext db, IndicatorService indicators) : ControllerBase
{
    [HttpGet("farol")]
    public async Task<IActionResult> TrafficLight(CancellationToken cancellationToken)
    {
        var rows = await indicators.Latest().OrderByDescending(x => x.ReferenceDate).Take(100)
            .Select(x => new { code = x.Item.Code, name = x.Item.Name, target = x.Item.Target, actual = x.ActualValue, status = x.Status, referenceDate = x.ReferenceDate })
            .ToListAsync(cancellationToken);
        var counts = Enum.GetValues<TrafficStatus>().ToDictionary(x => x.ToString().ToUpperInvariant(), x => rows.LongCount(y => y.status == x));
        return Ok(new { counts, items = rows });
    }

    [HttpGet("itens-controle")]
    public Task<List<ControlItem>> Items(CancellationToken cancellationToken) =>
        db.ControlItems.AsNoTracking().Where(x => x.Active).OrderBy(x => x.Code).ToListAsync(cancellationToken);

    [HttpPost("itens-controle")]
    public async Task<IActionResult> SaveItem([FromBody] ControlItem item, CancellationToken cancellationToken)
    {
        if (item.Direction == Direction.Range && item.LowerLimit is null)
            return BadRequest(new { message = "Indicadores por faixa exigem um limite inferior." });
        if (await db.ControlItems.AnyAsync(x => x.Code == item.Code && x.Id != item.Id, cancellationToken))
            return Conflict(new { message = "Já existe um item com este código." });
        if (item.Id == 0) db.ControlItems.Add(item);
        else db.Entry(item).State = EntityState.Modified;
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/itens-controle/{item.Id}", item);
    }

    [HttpPost("acompanhamentos")]
    public async Task<IActionResult> SaveFollowUp([FromBody] FollowUp value, CancellationToken cancellationToken) =>
        Ok(await indicators.RecordAsync(value, cancellationToken));

    [HttpPost("producao")]
    public async Task<IActionResult> SaveProduction([FromBody] ProductionRecord record, CancellationToken cancellationToken)
    {
        db.ProductionRecords.Add(record);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/producao/{record.Id}", record);
    }

    [HttpPost("desdobramentos")]
    public async Task<IActionResult> SaveDeployment([FromBody] Deployment deployment, CancellationToken cancellationToken)
    {
        if (deployment.ParentItemId == deployment.ChildItemId) return BadRequest(new { message = "Os indicadores pai e filho devem ser diferentes." });
        if (!await db.ControlItems.AnyAsync(x => x.Id == deployment.ParentItemId, cancellationToken) ||
            !await db.ControlItems.AnyAsync(x => x.Id == deployment.ChildItemId, cancellationToken))
            return BadRequest(new { message = "Selecione indicadores existentes." });
        db.Deployments.Add(deployment);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/desdobramentos/{deployment.Id}", deployment);
    }

    [HttpPost("motoristas")]
    public async Task<IActionResult> SaveDriver([FromBody] Driver driver, CancellationToken cancellationToken)
    {
        if (await db.Drivers.AnyAsync(x => x.Registration == driver.Registration && x.Id != driver.Id, cancellationToken))
            return Conflict(new { message = "Já existe um motorista com esta matrícula." });
        db.Drivers.Add(driver);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/motoristas/{driver.Id}", driver);
    }

    [HttpPost("treinamentos")]
    public async Task<IActionResult> SaveTraining([FromBody] DriverTraining training, CancellationToken cancellationToken)
    {
        if (!await db.Drivers.AnyAsync(x => x.Id == training.DriverId && x.Active, cancellationToken))
            return BadRequest(new { message = "Selecione um motorista ativo." });
        db.DriverTrainings.Add(training);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/treinamentos/{training.Id}", training);
    }

    [HttpPost("documentos")]
    public async Task<IActionResult> SaveDocument([FromBody] ManagedDocument document, CancellationToken cancellationToken)
    {
        if (document.OwnerId is not null && !await db.Users.AnyAsync(x => x.Id == document.OwnerId, cancellationToken))
            return BadRequest(new { message = "Responsável inválido." });
        if (await db.Documents.AnyAsync(x => x.Code == document.Code && x.Id != document.Id, cancellationToken))
            return Conflict(new { message = "Já existe um documento com este código." });
        document.CurrentRevision = 1;
        db.Documents.Add(document);
        db.DocumentRevisions.Add(new DocumentRevision { Document = document, RevisionNumber = 1, Content = document.Content, ChangeReason = "Emissão inicial", AuthorId = document.OwnerId });
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/documentos/{document.Id}", document);
    }

    [HttpPost("r3g")]
    public async Task<IActionResult> SaveR3G([FromBody] R3GReport report, CancellationToken cancellationToken)
    {
        if (report.AuthorId is not null && !await db.Users.AnyAsync(x => x.Id == report.AuthorId, cancellationToken))
            return BadRequest(new { message = "Autor inválido." });
        db.R3GReports.Add(report);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/r3g/{report.Id}", report);
    }

    [HttpPost("usuarios")]
    public async Task<IActionResult> SaveUser([FromBody] User user, CancellationToken cancellationToken)
    {
        if (await db.Users.AnyAsync(x => x.Email == user.Email && x.Id != user.Id, cancellationToken))
            return Conflict(new { message = "Já existe um usuário com este e-mail." });
        if (string.IsNullOrWhiteSpace(user.PasswordHash)) return BadRequest(new { message = "Senha é obrigatória." });
        db.Users.Add(user);
        await db.SaveChangesAsync(cancellationToken);
        return Created($"/api/usuarios/{user.Id}", user);
    }
}