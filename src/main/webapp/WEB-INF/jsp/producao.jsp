<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Produção"/></jsp:include>
<div class="page-head"><div><h1>Apontamento de produção</h1><p>Registre o volume de ferro-gusa e a qualidade entregue por alto-forno.</p></div></div>
<div class="module-grid">
 <form class="panel form-panel" method="post" action="${pageContext.request.contextPath}/producao"><h2>Novo apontamento</h2>
  <div class="form-row"><div class="field"><label for="data">Data</label><input id="data" type="date" name="data" required></div><div class="field"><label for="forno">Alto-forno</label><select id="forno" name="forno"><option>Alto-forno 01</option><option>Alto-forno 02</option><option>Alto-forno 03</option></select></div></div>
  <div class="form-row"><div class="field"><label for="toneladas">Produção (t)</label><input id="toneladas" type="number" name="toneladas" step="0.01" min="0.01" required></div><div class="field"><label for="qualidade">Qualidade (%)</label><input id="qualidade" type="number" name="qualidade" step="0.01" min="0" max="100" required></div></div>
  <div class="form-actions"><button class="button" type="submit">Salvar apontamento</button></div>
 </form>
 <section class="panel data-panel"><div class="panel-title"><h2>Histórico recente</h2><span>Últimos 12 registros</span></div><div class="table-wrap"><table class="data-table"><thead><tr><th>Data</th><th>Alto-forno</th><th>Volume</th><th>Qualidade</th></tr></thead><tbody>
 <c:forEach var="p" items="${producoes}"><tr><td><fmt:formatDate value="${p.data}" pattern="dd/MM/yyyy"/></td><td><strong><c:out value="${p.altoForno}"/></strong></td><td><fmt:formatNumber value="${p.toneladas}" maxFractionDigits="2"/> t</td><td><span class="badge REGULAR"><fmt:formatNumber value="${p.qualidade}" maxFractionDigits="1"/>%</span></td></tr></c:forEach>
 </tbody></table></div></section>
</div>
<jsp:include page="componentes/rodape.jsp"/>