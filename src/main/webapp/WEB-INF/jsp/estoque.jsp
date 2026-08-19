<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Estoque e insumos"/></jsp:include>
<div class="page-head"><div><h1>Estoque e insumos</h1><p>Monitore materiais críticos para manter a continuidade da produção.</p></div></div>
<div class="module-grid">
 <form class="panel form-panel" method="post" action="${pageContext.request.contextPath}/estoque"><h2>Novo item de estoque</h2>
  <div class="field"><label for="descricao">Descrição</label><input id="descricao" name="descricao" required maxlength="100"></div>
  <div class="field"><label for="categoria">Categoria</label><select id="categoria" name="categoria"><option>Insumo</option><option>Peça de manutenção</option><option>EPI</option><option>Combustível</option></select></div>
  <div class="form-row"><div class="field"><label for="quantidade">Saldo atual</label><input id="quantidade" type="number" name="quantidade" step="0.01" min="0" required></div><div class="field"><label for="minimo">Estoque mínimo</label><input id="minimo" type="number" name="minimo" step="0.01" min="0" required></div></div>
  <div class="form-actions"><button class="button" type="submit">Cadastrar item</button></div>
 </form>
 <section class="panel data-panel"><div class="panel-title"><h2>Posição de estoque</h2><span>${itens.size()} materiais</span></div><div class="table-wrap"><table class="data-table"><thead><tr><th>Material</th><th>Categoria</th><th>Saldo</th><th>Mínimo</th><th>Situação</th></tr></thead><tbody>
 <c:forEach var="item" items="${itens}"><tr><td><strong><c:out value="${item.descricao}"/></strong></td><td><c:out value="${item.categoria}"/></td><td><fmt:formatNumber value="${item.quantidade}" maxFractionDigits="2"/></td><td><fmt:formatNumber value="${item.minimo}" maxFractionDigits="2"/></td><td><span class="badge ${item.critico ? 'CRITICO' : 'REGULAR'}">${item.critico ? 'REPOR' : 'REGULAR'}</span></td></tr></c:forEach>
 </tbody></table></div></section>
</div>
<jsp:include page="componentes/rodape.jsp"/>