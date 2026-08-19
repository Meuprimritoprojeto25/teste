<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Usuários"/></jsp:include>
<div class="page-head"><div><h1>Cadastro de usuários</h1><p>Controle os acessos da equipe industrial e administrativa.</p></div></div>
<div class="module-grid">
 <form class="panel form-panel" method="post" action="${pageContext.request.contextPath}/usuarios">
  <h2>Novo usuário</h2><div class="field"><label for="nome">Nome completo</label><input id="nome" name="nome" required maxlength="120"></div>
  <div class="form-row"><div class="field"><label for="matricula">Matrícula</label><input id="matricula" name="matricula" required maxlength="20"></div><div class="field"><label for="perfil">Perfil</label><select id="perfil" name="perfil"><option>Operador</option><option>Supervisor</option><option>Gerente Industrial</option><option>Segurança</option></select></div></div>
  <div class="field"><label for="email">E-mail corporativo</label><input id="email" type="email" name="email" required maxlength="100"></div><div class="form-actions"><button class="button" type="submit">Cadastrar usuário</button></div>
 </form>
 <section class="panel data-panel"><div class="panel-title"><h2>Usuários cadastrados</h2><span>${usuarios.size()} registros</span></div><div class="table-wrap"><table class="data-table"><thead><tr><th>Colaborador</th><th>Perfil</th><th>Situação</th><th>Ação</th></tr></thead><tbody>
 <c:forEach var="u" items="${usuarios}"><tr><td><strong><c:out value="${u.nome}"/></strong><small><c:out value="${u.matricula}"/> · <c:out value="${u.email}"/></small></td><td><c:out value="${u.perfil}"/></td><td><span class="badge ${u.ativo ? 'REGULAR' : 'VENCIDO'}">${u.ativo ? 'ATIVO' : 'INATIVO'}</span></td><td><form method="post" action="${pageContext.request.contextPath}/usuarios"><input type="hidden" name="acao" value="alternar"><input type="hidden" name="id" value="${u.id}"><button class="text-button" type="submit">${u.ativo ? 'DESATIVAR' : 'ATIVAR'}</button></form></td></tr></c:forEach>
 </tbody></table></div></section>
</div>
<jsp:include page="componentes/rodape.jsp"/>