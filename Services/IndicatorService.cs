using FerroGestao.Domain;
using FerroGestao.Infrastructure;
using Microsoft.EntityFrameworkCore;

namespace FerroGestao.Services;

public static class IndicatorRules
{
    public static TrafficStatus CalculateStatus(ControlItem item, double actual) => item.Direction switch
    {
        Direction.HigherIsBetter when actual >= item.Target => actual > item.Target * 1.05 ? TrafficStatus.Blue : TrafficStatus.Green,
        Direction.HigherIsBetter => actual >= item.WarningLimit ? TrafficStatus.Yellow : TrafficStatus.Red,
        Direction.LowerIsBetter when actual <= item.Target => actual < item.Target * .95 ? TrafficStatus.Blue : TrafficStatus.Green,
        Direction.LowerIsBetter => actual <= item.WarningLimit ? TrafficStatus.Yellow : TrafficStatus.Red,
        Direction.Range when actual >= item.LowerLimit && actual <= item.Target => TrafficStatus.Green,
        Direction.Range when actual >= item.LowerLimit - Math.Abs(item.WarningLimit - item.Target) && actual <= item.WarningLimit => TrafficStatus.Yellow,
        _ => TrafficStatus.Red
    };

    public static double CalculateAchievement(ControlItem item, double actual) => item.Direction switch
    {
        Direction.LowerIsBetter => actual == 0 ? 100 : item.Target / actual * 100,
        Direction.Range => CalculateStatus(item, actual) == TrafficStatus.Green ? 100 : 0,
        _ => item.Target == 0 ? (actual == 0 ? 100 : 0) : actual / item.Target * 100
    };
}

public sealed class IndicatorService(FerroGestaoContext db)
{
    public async Task<FollowUp> RecordAsync(FollowUp value, CancellationToken cancellationToken = default)
    {
        var item = await db.ControlItems.SingleOrDefaultAsync(x => x.Id == value.ItemId && x.Active, cancellationToken)
            ?? throw new ArgumentException("Item de controle inválido ou inativo.", nameof(value.ItemId));
        if (value.ReferenceDate == default) value.ReferenceDate = DateOnly.FromDateTime(DateTime.UtcNow);
        value.Status = IndicatorRules.CalculateStatus(item, value.ActualValue);
        db.FollowUps.Add(value);
        db.KpiSnapshots.Add(new KpiSnapshot
        {
            ItemId = item.Id, ReferenceDate = value.ReferenceDate, TargetValue = item.Target,
            ActualValue = value.ActualValue, Status = value.Status,
            Achievement = IndicatorRules.CalculateAchievement(item, value.ActualValue)
        });
        await db.SaveChangesAsync(cancellationToken);
        return value;
    }

    public IQueryable<FollowUp> Latest() =>
        db.FollowUps.Include(x => x.Item).ThenInclude(x => x.Owner)
          .Where(x => x.Id == db.FollowUps.Where(y => y.ItemId == x.ItemId).Max(y => y.Id));
}

public sealed record DashboardViewModel(
    long ItemCount, long UserCount, long EvaluatedCount, long HealthyPercent,
    IReadOnlyDictionary<TrafficStatus, long> Lights, double ProductionTotal, IReadOnlyList<FollowUp> Recent);

public sealed class DashboardService(FerroGestaoContext db, IndicatorService indicators)
{
    public async Task<DashboardViewModel> GetAsync(CancellationToken cancellationToken = default)
    {
        var latest = await indicators.Latest().OrderByDescending(x => x.ReferenceDate).Take(8).ToListAsync(cancellationToken);
        var lights = Enum.GetValues<TrafficStatus>().ToDictionary(
            status => status, status => (long)latest.Count(x => x.Status == status));
        var evaluated = lights.Values.Sum();
        var healthy = lights[TrafficStatus.Blue] + lights[TrafficStatus.Green];
        var production = await db.ProductionRecords.SumAsync(x => (double?)x.ProducedTons, cancellationToken) ?? 0;
        return new DashboardViewModel(
            await db.ControlItems.LongCountAsync(x => x.Active, cancellationToken),
            await db.Users.LongCountAsync(cancellationToken), evaluated,
            evaluated == 0 ? 0 : (long)Math.Round(healthy * 100d / evaluated),
            lights, production, latest);
    }
}