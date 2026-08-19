# FerroGestão Industrial

Monólito gerencial para indústria siderúrgica, implementado deliberadamente com a stack legada da era Java 7.

## Stack

- Java 7 e Maven, empacotamento WAR;
- Spring MVC 3.2, Spring ORM e transações;
- Hibernate 4.2 com persistência H2 em arquivo (`~/ferrogestao.mv.db`);
- JSP/JSTL, HTML5, CSS e JavaScript puro;
- iText 5, Apache POI 3.17 e JFreeChart 1.0 para relatórios.

## Módulos funcionais

- centro de controle e farol automático;
- cadastro de usuários, áreas e perfis;
- itens de controle, metas, KPI e apontamentos;
- desdobramento hierárquico de indicadores;
- relatórios R3G (resultado, gaps e ganhos);
- produção siderúrgica por planta, forno e turno;
- motoristas, habilitações e treinamentos;
- documentos editáveis com revisão e histórico;
- relatórios de farol e desdobramento em PDF, Excel e Word;
- gráficos de farol em PNG, JPG e SVG;
- API JSON de consulta em `/api/farol`.

## Execução

O artefato é um WAR Servlet 3.0. Após gerar `ferro-gestao.war`, publique-o em um contêiner compatível com Java 7, como Tomcat 7/8. A aplicação cria e atualiza o schema no primeiro início e inclui uma carga inicial idempotente para permitir a navegação.

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
| `/relatorios` | PDF, Excel, Word e gráficos |

Para trocar o H2 por MySQL 5.x, altere o bean `dataSource` e o dialeto em
`src/main/webapp/WEB-INF/spring/application-context.xml`; o driver legado já está declarado no Maven.
