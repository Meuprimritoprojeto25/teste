using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace FerroGestao.Domain;

public abstract class BaseEntity
{
    public long Id { get; set; }
    [Required] public DateTime CreatedAt { get; set; }
    [Required] public DateTime UpdatedAt { get; set; }
    [Timestamp] public byte[]? RowVersion { get; set; }
}

public enum Direction { HigherIsBetter, LowerIsBetter, Range }
public enum TrafficStatus { Blue, Green, Yellow, Red }
public enum RecordStatus { Draft, InReview, Approved, Obsolete }

public sealed class User : BaseEntity
{
    [Required, StringLength(120)] public string Name { get; set; } = string.Empty;
    [Required, EmailAddress, StringLength(160)] public string Email { get; set; } = string.Empty;
    [StringLength(30)] public string? Registration { get; set; }
    [StringLength(80)] public string? Department { get; set; }
    [StringLength(40)] public string? Role { get; set; }
    [Required, StringLength(128)] public string PasswordHash { get; set; } = string.Empty;
    public bool Active { get; set; } = true;
}

public sealed class ControlItem : BaseEntity
{
    [Required, StringLength(30)] public string Code { get; set; } = string.Empty;
    [Required, StringLength(140)] public string Name { get; set; } = string.Empty;
    [Required, StringLength(80)] public string Area { get; set; } = string.Empty;
    [Required, StringLength(20)] public string Unit { get; set; } = string.Empty;
    [StringLength(20)] public string? Periodicity { get; set; }
    public double Target { get; set; }
    public double WarningLimit { get; set; }
    public double? LowerLimit { get; set; }
    public Direction Direction { get; set; } = Direction.HigherIsBetter;
    public long? OwnerId { get; set; }
    public User? Owner { get; set; }
    public bool Active { get; set; } = true;
    public ICollection<FollowUp> FollowUps { get; set; } = new List<FollowUp>();
}

public sealed class FollowUp : BaseEntity
{
    public long ItemId { get; set; }
    public ControlItem Item { get; set; } = null!;
    public DateOnly ReferenceDate { get; set; }
    public double ActualValue { get; set; }
    public TrafficStatus Status { get; set; }
    [StringLength(1000)] public string? Analysis { get; set; }
    [StringLength(1000)] public string? RootCause { get; set; }
    [StringLength(1500)] public string? ActionPlan { get; set; }
    public long? ReportedById { get; set; }
    public User? ReportedBy { get; set; }
}

public sealed class KpiSnapshot : BaseEntity
{
    public long ItemId { get; set; }
    public ControlItem Item { get; set; } = null!;
    public DateOnly ReferenceDate { get; set; }
    public double TargetValue { get; set; }
    public double ActualValue { get; set; }
    public double Achievement { get; set; }
    public TrafficStatus Status { get; set; }
}

public sealed class Deployment : BaseEntity
{
    public long ParentItemId { get; set; }
    public ControlItem ParentItem { get; set; } = null!;
    public long ChildItemId { get; set; }
    public ControlItem ChildItem { get; set; } = null!;
    public double Weight { get; set; }
    [StringLength(1000)] public string? Rationale { get; set; }
}

public sealed class ProductionRecord : BaseEntity
{
    public DateOnly ProductionDate { get; set; }
    [Required, StringLength(100)] public string Plant { get; set; } = string.Empty;
    [Required, StringLength(100)] public string Furnace { get; set; } = string.Empty;
    [Required, StringLength(60)] public string Shift { get; set; } = string.Empty;
    public double ProducedTons { get; set; }
    public double OreTons { get; set; }
    public double ScrapTons { get; set; }
    public double EnergyMwh { get; set; }
    public double QualityIndex { get; set; }
    public int DowntimeMinutes { get; set; }
}

public sealed class Driver : BaseEntity
{
    [Required, StringLength(120)] public string Name { get; set; } = string.Empty;
    [Required, StringLength(30)] public string Registration { get; set; } = string.Empty;
    [Required, StringLength(20)] public string Cpf { get; set; } = string.Empty;
    [Required, StringLength(30)] public string LicenseNumber { get; set; } = string.Empty;
    [Required, StringLength(10)] public string LicenseCategory { get; set; } = string.Empty;
    public bool Active { get; set; } = true;
}

public sealed class DriverTraining : BaseEntity
{
    public long DriverId { get; set; }
    public Driver Driver { get; set; } = null!;
    [Required, StringLength(160)] public string Course { get; set; } = string.Empty;
    [Required, StringLength(120)] public string Instructor { get; set; } = string.Empty;
    public DateOnly CompletionDate { get; set; }
    public DateOnly ExpirationDate { get; set; }
    public int WorkloadHours { get; set; }
    public double? Score { get; set; }
}

public sealed class ManagedDocument : BaseEntity
{
    [Required, StringLength(30)] public string Code { get; set; } = string.Empty;
    [Required, StringLength(180)] public string Title { get; set; } = string.Empty;
    [StringLength(80)] public string? Category { get; set; }
    public long? OwnerId { get; set; }
    public User? Owner { get; set; }
    public RecordStatus Status { get; set; } = RecordStatus.Draft;
    [Required] public string Content { get; set; } = string.Empty;
    public int CurrentRevision { get; set; } = 1;
}

public sealed class DocumentRevision : BaseEntity
{
    public long DocumentId { get; set; }
    public ManagedDocument Document { get; set; } = null!;
    public int RevisionNumber { get; set; }
    [Required] public string Content { get; set; } = string.Empty;
    [StringLength(500)] public string? ChangeReason { get; set; }
    public long? AuthorId { get; set; }
    public User? Author { get; set; }
}

public sealed class R3GReport : BaseEntity
{
    [Required, StringLength(180)] public string Title { get; set; } = string.Empty;
    [Required, StringLength(80)] public string Area { get; set; } = string.Empty;
    public DateOnly ReferenceDate { get; set; }
    public long? AuthorId { get; set; }
    public User? Author { get; set; }
    public RecordStatus Status { get; set; } = RecordStatus.Draft;
    [Required] public string ResultSummary { get; set; } = string.Empty;
    [Required] public string Gaps { get; set; } = string.Empty;
    [Required] public string Gains { get; set; } = string.Empty;
    [Required] public string NextSteps { get; set; } = string.Empty;
}