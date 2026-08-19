# Sideral Gerencial

Monólito de gestão para uma indústria de produção de ferro, construído na stack
corporativa do período Java 7: **Spring MVC 3.2, JSP, JavaScript puro,
Hibernate 4.1 e H2**.

## Módulos entregues

* Painel gerencial de KPIs e distribuição de farol.
* Cadastro de usuários e perfis operacionais.
* Itens de controle com regra de negócio de farol verde/amarelo/vermelho,
  registro de leituras e histórico de acompanhamento.
* Desdobramentos e planos de ação vinculados a um item de controle.
* Controle de treinamento e validade de motoristas/equipamentos.
* Gestão documental com criação, edição, revisão e geração individual em PDF
  e Word.
* Relatórios de farol, itens de controle e desdobramentos em PDF, Word e
  Excel; relatório R3G para análise e decisões gerenciais.

## Arquitetura

As entidades JPA são persistidas pelo Hibernate no banco H2 local
`~/sideral-gerencial`. Os controllers MVC delegam as regras de negócio para
services transacionais, que usam repositories Hibernate. Não há dados de
demonstração em memória: os cadastros e relatórios usam os dados registrados
no banco.

## Execução em ambiente legado

Empacote como WAR em um container Servlet 3.0 compatível (por exemplo,
Tomcat 7) com JDK 7. A rota inicial do monólito é `/dashboard`; a raiz `/`
também encaminha para o painel. O `index.html` na raiz é uma visão visual de
entrada para hospedagens estáticas, enquanto o funcionamento completo ocorre
na aplicação WAR.
