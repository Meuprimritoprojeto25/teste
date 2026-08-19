<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Treinamentos"/></jsp:include>
<div class="page-head"><div><h1>Treinamento de motorista</h1><p>Garanta que os condutores estejam habilitados para a operação logística.</p></div></div>
<div class="module-grid">
 <form class="panel form-panel" method="post" action="${pageContext.request.contextPath}/treinamentos"><h2>Registrar capacitação</h2>
  <div class="field"><label for="motorista">Motorista</label><input id="motorista" name="motorista" required maxlength="120"></div>
  <div class="field"><label for="curso">Treinamento</label><select id="curso" name="curso"><option>Direção Defensiva</option><option>Operação de Carreta</option><option>Transporte de Carga</option><option>Segurança em Pátio</option></select></div>
  <div class="field"><label for="validade">Validade</label><input id="validade" type="date" name="validade" required></div><div class="form-actions"><button class="button" type="submit">Registrar treinamento</button></div>
 </form>
 <section class="panel data-panel"><div class="panel-title"><h2>Controle de habilitações</h2><span>${treinamentos.size()} registros</span></div><div class="table-wrap"><table class="data-table"><thead><tr><th>Motorista</th><th>Capacitação</th><th>Validade</th><th>Situação</th></tr></thead><tbody>
 <c:forEach var="t" items="${treinamentos}"><tr><td><strong><c:out value="${t.motorista}"/></strong></td><td><c:out value="${t.curso}"/></td><td><fmt:formatDate value="${t.validade}" pattern="dd/MM/yyyy"/></td><td><span class="badge ${t.situacao}">${t.situacao}</span></td></tr></c:forEach>
 </tbody></table></div></section>
</div>
<jsp:include page="componentes/rodape.jsp"/>