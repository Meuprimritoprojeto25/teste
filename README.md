# Sideral Gerencial

Monólito Java 7 para a operação de uma indústria siderúrgica. A aplicação usa
Servlet 3, JSP/JSTL, JavaScript puro, Hibernate 3.6 e H2 para permitir uma
execução local sem infraestrutura externa.

## Executar

Faça o deploy do `war` em um contêiner Servlet 3 compatível, como Tomcat 7. O
banco é criado automaticamente em `./data/sideral`; em produção, substitua as
propriedades de conexão de `src/main/resources/hibernate.cfg.xml`.

## Módulos

* Painel de indicadores e semáforos de controle;
* cadastro e ativação de usuários;
* treinamento e validade de habilitação dos motoristas;
* registros de produção de ferro-gusa;
* estoque de insumos operacionais.

As rotas são `/`, `/usuarios`, `/treinamentos`, `/indicadores`, `/producao` e
`/estoque`. As mutações usam POST, validação de serviço e transações Hibernate.
