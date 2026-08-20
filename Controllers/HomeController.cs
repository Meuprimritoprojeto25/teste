using FerroGestao.Services;
using Microsoft.AspNetCore.Mvc;

namespace FerroGestao.Controllers;

public sealed class HomeController(DashboardService dashboard) : Controller
{
    [HttpGet("/")]
    public async Task<IActionResult> Index(CancellationToken cancellationToken) =>
        View(await dashboard.GetAsync(cancellationToken));

    [HttpGet("/Home/Error")]
    public IActionResult Error() => Problem("Não foi possível processar a solicitação.");
}