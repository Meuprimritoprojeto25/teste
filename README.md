# FerroGestão Industrial

Monólito gerencial para indústria siderúrgica migrado para **C# / ASP.NET Core 8**.

## Stack

- .NET 8, ASP.NET Core MVC e Razor;
- Entity Framework Core 8 com SQLite;
- migração inicial versionada em `Migrations/`;
- API JSON em `/api/farol` e endpoints de gravação dos módulos principais;
- CSS responsivo servido por `wwwroot`.

## Módulos funcionais

- centro de controle e farol automático;
- cadastro de usuários, áreas e perfis;
- itens de controle, metas, KPI e apontamentos;
- desdobramento hierárquico de indicadores;
- relatórios R3G (resultado, gaps e ganhos);
- produção siderúrgica por planta, forno e turno;
- motoristas, habilitações e treinamentos;
- documentos controlados com revisão inicial;
- central de consulta dos dados operacionais;
- API JSON de consulta em `/api/farol`.

## Execução

```bash
dotnet run
```

No primeiro início, a aplicação aplica a migração SQLite em
`App_Data/ferrogestao.db` e inclui uma carga demonstrativa idempotente. Não há
dependência de Tomcat, Maven, H2 ou Java.

Rotas principais:

| Rota | Função |
| --- | --- |
| `/` | Centro de controle |
| `/itens-controle` | Cadastro de KPI |
| `/acompanhamentos` | Farol e planos de ação |
| `/desdobramentos` | Árvore de metas |
| `/r3g` | Relatórios R3G |
| `/producao` | Apontamento siderúrgico |
| `/treinamentos` | Motoristas e capacitações |
| `/documentos` | Editor e revisão documental |
| `/relatorios` | Central de relatórios |

Para mudar o provedor de dados, altere a cadeia `FerroGestao` e o registro do
`FerroGestaoContext` em `Program.cs`.
