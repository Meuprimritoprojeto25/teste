using FerroGestao.Infrastructure;
using FerroGestao.Services;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
var connectionString = builder.Configuration.GetConnectionString("FerroGestao")
    ?? throw new InvalidOperationException("A conexão FerroGestao é obrigatória.");
Directory.CreateDirectory(Path.Combine(builder.Environment.ContentRootPath, "App_Data"));

builder.Services.AddDbContext<FerroGestaoContext>(options => options.UseSqlite(connectionString));
builder.Services.AddScoped<IndicatorService>();
builder.Services.AddScoped<DashboardService>();
builder.Services.AddControllersWithViews();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseStaticFiles();
app.UseRouting();
app.MapControllerRoute(name: "default", pattern: "{controller=Home}/{action=Index}/{id?}");

// The database is initialized before requests are accepted. The initializer is
// idempotent, therefore an empty preview database receives the demo data once.
using (var scope = app.Services.CreateScope())
{
    var database = scope.ServiceProvider.GetRequiredService<FerroGestaoContext>();
    await database.Database.MigrateAsync();
    await DemoDataInitializer.InitializeAsync(database);
}

app.Run();

public partial class Program { }