# ViaSegura

Painel de gestão de frota para cadastro de usuários, treinamento de motoristas
e acompanhamento de itens de controle, como faróis, pneus e extintores.

## Stack legada

O projeto está preparado como uma aplicação WAR compatível com Java 7:

- JSP e Servlet API 2.5;
- JavaScript puro na interface;
- Hibernate Core 3.6.

O dashboard pode ser visualizado pela rota `/`. Para implantação em um
container Java, o ponto de entrada JSP está em `src/main/webapp/index.jsp`.
