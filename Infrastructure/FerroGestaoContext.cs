using FerroGestao.Domain;
using Microsoft.EntityFrameworkCore;

namespace FerroGestao.Infrastructure;

public sealed class FerroGestaoContext(DbContextOptions<FerroGestaoContext> options) : DbContext(options)
{
    public DbSet<User> Users => Set<User>();
    public DbSet<ControlItem> ControlItems => Set<ControlItem>();
    public DbSet<FollowUp> FollowUps => Set<FollowUp>();
    public DbSet<KpiSnapshot> KpiSnapshots => Set<KpiSnapshot>();
    public DbSet<Deployment> Deployments => Set<Deployment>();
    public DbSet<ProductionRecord> ProductionRecords => Set<ProductionRecord>();
    public DbSet<Driver> Drivers => Set<Driver>();
    public DbSet<DriverTraining> DriverTrainings => Set<DriverTraining>();
    public DbSet<ManagedDocument> Documents => Set<ManagedDocument>();
    public DbSet<DocumentRevision> DocumentRevisions => Set<DocumentRevision>();
    public DbSet<R3GReport> R3GReports => Set<R3GReport>();

    protected override void OnModelCreating(ModelBuilder model)
    {
        base.OnModelCreating(model);
        model.Entity<User>().ToTable("fg_user").HasIndex(x => x.Email).IsUnique();
        model.Entity<ControlItem>().ToTable("control_item").HasIndex(x => x.Code).IsUnique();
        model.Entity<FollowUp>().ToTable("item_follow_up");
        model.Entity<ControlItem>().HasOne(x => x.Owner).WithMany().HasForeignKey(x => x.OwnerId).OnDelete(DeleteBehavior.SetNull);
        model.Entity<FollowUp>().HasOne(x => x.Item).WithMany(x => x.FollowUps).HasForeignKey(x => x.ItemId).OnDelete(DeleteBehavior.Restrict);
        model.Entity<FollowUp>().HasOne(x => x.ReportedBy).WithMany().HasForeignKey(x => x.ReportedById).OnDelete(DeleteBehavior.SetNull);
        model.Entity<KpiSnapshot>().HasOne(x => x.Item).WithMany().HasForeignKey(x => x.ItemId).OnDelete(DeleteBehavior.Restrict);
        model.Entity<Deployment>().HasOne(x => x.ParentItem).WithMany().HasForeignKey(x => x.ParentItemId).OnDelete(DeleteBehavior.Restrict);
        model.Entity<Deployment>().HasOne(x => x.ChildItem).WithMany().HasForeignKey(x => x.ChildItemId).OnDelete(DeleteBehavior.Restrict);
        model.Entity<DriverTraining>().HasOne(x => x.Driver).WithMany().HasForeignKey(x => x.DriverId).OnDelete(DeleteBehavior.Restrict);
        model.Entity<Driver>().HasIndex(x => x.Registration).IsUnique();
        model.Entity<DocumentRevision>().HasOne(x => x.Document).WithMany().HasForeignKey(x => x.DocumentId).OnDelete(DeleteBehavior.Cascade);
        model.Entity<ManagedDocument>().HasIndex(x => x.Code).IsUnique();
        model.Entity<ManagedDocument>().HasOne(x => x.Owner).WithMany().HasForeignKey(x => x.OwnerId).OnDelete(DeleteBehavior.SetNull);
        model.Entity<R3GReport>().HasOne(x => x.Author).WithMany().HasForeignKey(x => x.AuthorId).OnDelete(DeleteBehavior.SetNull);
        model.Entity<ControlItem>().Property(x => x.Direction).HasConversion<string>();
        model.Entity<FollowUp>().Property(x => x.Status).HasConversion<string>();
        model.Entity<KpiSnapshot>().Property(x => x.Status).HasConversion<string>();
        model.Entity<ManagedDocument>().Property(x => x.Status).HasConversion<string>();
        model.Entity<R3GReport>().Property(x => x.Status).HasConversion<string>();
    }

    public override int SaveChanges(bool acceptAllChangesOnSuccess)
    {
        SetAuditFields();
        return base.SaveChanges(acceptAllChangesOnSuccess);
    }

    public override Task<int> SaveChangesAsync(bool acceptAllChangesOnSuccess, CancellationToken cancellationToken = default)
    {
        SetAuditFields();
        return base.SaveChangesAsync(acceptAllChangesOnSuccess, cancellationToken);
    }

    private void SetAuditFields()
    {
        var now = DateTime.UtcNow;
        foreach (var entry in ChangeTracker.Entries<BaseEntity>())
        {
            if (entry.State == EntityState.Added) entry.Entity.CreatedAt = now;
            if (entry.State is EntityState.Added or EntityState.Modified) entry.Entity.UpdatedAt = now;
        }
    }
}