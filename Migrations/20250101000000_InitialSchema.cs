using FerroGestao.Infrastructure;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;

namespace FerroGestao.Migrations;

[DbContext(typeof(FerroGestaoContext))]
[Migration("20250101000000_InitialSchema")]
public sealed class InitialSchema : Migration
{
    protected override void Up(MigrationBuilder migrationBuilder)
    {
        migrationBuilder.Sql("""
            CREATE TABLE fg_user (Id INTEGER NOT NULL CONSTRAINT PK_fg_user PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, Name TEXT NOT NULL, Email TEXT NOT NULL, Registration TEXT NULL, Department TEXT NULL, Role TEXT NULL, PasswordHash TEXT NOT NULL, Active INTEGER NOT NULL);
            CREATE UNIQUE INDEX IX_fg_user_Email ON fg_user (Email);
            CREATE TABLE control_item (Id INTEGER NOT NULL CONSTRAINT PK_control_item PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, Code TEXT NOT NULL, Name TEXT NOT NULL, Area TEXT NOT NULL, Unit TEXT NOT NULL, Periodicity TEXT NULL, Target REAL NOT NULL, WarningLimit REAL NOT NULL, LowerLimit REAL NULL, Direction TEXT NOT NULL, OwnerId INTEGER NULL, Active INTEGER NOT NULL, FOREIGN KEY (OwnerId) REFERENCES fg_user (Id) ON DELETE SET NULL);
            CREATE UNIQUE INDEX IX_control_item_Code ON control_item (Code);
            CREATE INDEX IX_control_item_OwnerId ON control_item (OwnerId);
            CREATE TABLE item_follow_up (Id INTEGER NOT NULL CONSTRAINT PK_item_follow_up PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, ItemId INTEGER NOT NULL, ReferenceDate TEXT NOT NULL, ActualValue REAL NOT NULL, Status TEXT NOT NULL, Analysis TEXT NULL, RootCause TEXT NULL, ActionPlan TEXT NULL, ReportedById INTEGER NULL, FOREIGN KEY (ItemId) REFERENCES control_item (Id) ON DELETE RESTRICT, FOREIGN KEY (ReportedById) REFERENCES fg_user (Id) ON DELETE SET NULL);
            CREATE INDEX IX_item_follow_up_ItemId ON item_follow_up (ItemId);
            CREATE INDEX IX_item_follow_up_ReportedById ON item_follow_up (ReportedById);
            CREATE TABLE KpiSnapshots (Id INTEGER NOT NULL CONSTRAINT PK_KpiSnapshots PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, ItemId INTEGER NOT NULL, ReferenceDate TEXT NOT NULL, TargetValue REAL NOT NULL, ActualValue REAL NOT NULL, Achievement REAL NOT NULL, Status TEXT NOT NULL, FOREIGN KEY (ItemId) REFERENCES control_item (Id) ON DELETE RESTRICT);
            CREATE INDEX IX_KpiSnapshots_ItemId ON KpiSnapshots (ItemId);
            CREATE TABLE Deployments (Id INTEGER NOT NULL CONSTRAINT PK_Deployments PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, ParentItemId INTEGER NOT NULL, ChildItemId INTEGER NOT NULL, Weight REAL NOT NULL, Rationale TEXT NULL, FOREIGN KEY (ParentItemId) REFERENCES control_item (Id) ON DELETE RESTRICT, FOREIGN KEY (ChildItemId) REFERENCES control_item (Id) ON DELETE RESTRICT);
            CREATE INDEX IX_Deployments_ParentItemId ON Deployments (ParentItemId);
            CREATE INDEX IX_Deployments_ChildItemId ON Deployments (ChildItemId);
            CREATE TABLE ProductionRecords (Id INTEGER NOT NULL CONSTRAINT PK_ProductionRecords PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, ProductionDate TEXT NOT NULL, Plant TEXT NOT NULL, Furnace TEXT NOT NULL, Shift TEXT NOT NULL, ProducedTons REAL NOT NULL, OreTons REAL NOT NULL, ScrapTons REAL NOT NULL, EnergyMwh REAL NOT NULL, QualityIndex REAL NOT NULL, DowntimeMinutes INTEGER NOT NULL);
            CREATE TABLE Drivers (Id INTEGER NOT NULL CONSTRAINT PK_Drivers PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, Name TEXT NOT NULL, Registration TEXT NOT NULL, Cpf TEXT NOT NULL, LicenseNumber TEXT NOT NULL, LicenseCategory TEXT NOT NULL, Active INTEGER NOT NULL);
            CREATE UNIQUE INDEX IX_Drivers_Registration ON Drivers (Registration);
            CREATE TABLE DriverTrainings (Id INTEGER NOT NULL CONSTRAINT PK_DriverTrainings PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, DriverId INTEGER NOT NULL, Course TEXT NOT NULL, Instructor TEXT NOT NULL, CompletionDate TEXT NOT NULL, ExpirationDate TEXT NOT NULL, WorkloadHours INTEGER NOT NULL, Score REAL NULL, FOREIGN KEY (DriverId) REFERENCES Drivers (Id) ON DELETE RESTRICT);
            CREATE INDEX IX_DriverTrainings_DriverId ON DriverTrainings (DriverId);
            CREATE TABLE Documents (Id INTEGER NOT NULL CONSTRAINT PK_Documents PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, Code TEXT NOT NULL, Title TEXT NOT NULL, Category TEXT NULL, OwnerId INTEGER NULL, Status TEXT NOT NULL, Content TEXT NOT NULL, CurrentRevision INTEGER NOT NULL, FOREIGN KEY (OwnerId) REFERENCES fg_user (Id) ON DELETE SET NULL);
            CREATE UNIQUE INDEX IX_Documents_Code ON Documents (Code);
            CREATE INDEX IX_Documents_OwnerId ON Documents (OwnerId);
            CREATE TABLE DocumentRevisions (Id INTEGER NOT NULL CONSTRAINT PK_DocumentRevisions PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, DocumentId INTEGER NOT NULL, RevisionNumber INTEGER NOT NULL, Content TEXT NOT NULL, ChangeReason TEXT NULL, AuthorId INTEGER NULL, FOREIGN KEY (DocumentId) REFERENCES Documents (Id) ON DELETE CASCADE, FOREIGN KEY (AuthorId) REFERENCES fg_user (Id) ON DELETE NO ACTION);
            CREATE INDEX IX_DocumentRevisions_DocumentId ON DocumentRevisions (DocumentId);
            CREATE INDEX IX_DocumentRevisions_AuthorId ON DocumentRevisions (AuthorId);
            CREATE TABLE R3GReports (Id INTEGER NOT NULL CONSTRAINT PK_R3GReports PRIMARY KEY AUTOINCREMENT, CreatedAt TEXT NOT NULL, UpdatedAt TEXT NOT NULL, RowVersion BLOB NULL, Title TEXT NOT NULL, Area TEXT NOT NULL, ReferenceDate TEXT NOT NULL, AuthorId INTEGER NULL, Status TEXT NOT NULL, ResultSummary TEXT NOT NULL, Gaps TEXT NOT NULL, Gains TEXT NOT NULL, NextSteps TEXT NOT NULL, FOREIGN KEY (AuthorId) REFERENCES fg_user (Id) ON DELETE SET NULL);
            CREATE INDEX IX_R3GReports_AuthorId ON R3GReports (AuthorId);
            """);
    }

    protected override void Down(MigrationBuilder migrationBuilder) =>
        migrationBuilder.Sql("DROP TABLE IF EXISTS R3GReports; DROP TABLE IF EXISTS DocumentRevisions; DROP TABLE IF EXISTS Documents; DROP TABLE IF EXISTS DriverTrainings; DROP TABLE IF EXISTS Drivers; DROP TABLE IF EXISTS ProductionRecords; DROP TABLE IF EXISTS Deployments; DROP TABLE IF EXISTS KpiSnapshots; DROP TABLE IF EXISTS item_follow_up; DROP TABLE IF EXISTS control_item; DROP TABLE IF EXISTS fg_user;");
}