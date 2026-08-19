<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Itens de controle"/></jsp:include>
<div class="page-head"><div><h1>Itens de controle</h1><p>Gestão por farol para resposta rápida aos desvios da operação.</p></div><div class="date-note">VERDE ≥ META · AMARELO ≥ 90% · VERMELHO &lt; 90%</div></div>
<div class="module-grid">
 <form class="panel form-panel" method="post" action="${pageContext.request.contextPath}/indicadores"><h2>Novo indicador</h2>
   <div class="field"><label for="nome">Nome do controle</label><input id="nome" name="nome" required maxlength="120"></div>
   <div class="field"><label for="area">Área responsável</label><input id="area" name="area" required maxlength="60" placeholder="Ex.: Alto-forno"></div>
   <div class="form-row"><div class="field"><label for="meta">Meta</label><input id="meta" type="number" step="0.01" min="0.01" name="meta" required></div><div class="field"><label for="atual">Valor atual</label><input id="atual" type="number" step="0.01" min="0" name="atual" required></div></div>
   <div class="form-actions"><button class="button" type="submit">Criar indicador</button></div>
 </form>
 <section class="panel data-panel"><div class="panel-title"><h2>Semáforo operacional</h2><span>${indicadores.size()} itens</span></div><div class="table-wrap"><table class="data-table"><thead><tr><th>Indicador</th><th>Meta</th><th>Atual</th><th>Farol</th><th>Apontar</th></tr></thead><tbody>
 <c:forEach var="i" items="${indicadores}"><tr><td><strong><c:out value="${i.nome}"/></strong><small><c:out value="${i.area}"/></small></td><td><fmt:formatNumber value="${i.meta}" maxFractionDigits="2"/></td><td><fmt:formatNumber value="${i.valorAtual}" maxFractionDigits="2"/></td><td><span class="badge ${i.status}"><i class="status-dot ${i.status}"></i>${i.status}</span></td><td><form class="inline-form" method="post" action="${pageContext.request.contextPath}/indicadores"><input type="hidden" name="acao" value="apontar"><input type="hidden" name="id" value="${i.id}"><input name="valor" required type="number" step="0.01" min="0" aria-label="Novo valor"><button class="text-button" type="submit">SALVAR</button></form></td></tr></c:forEach>
 </tbody></table></div></section>
</div>
<jsp:include page="componentes/rodape.jsp"/>