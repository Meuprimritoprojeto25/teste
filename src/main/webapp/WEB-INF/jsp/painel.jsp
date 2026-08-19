<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="componentes/cabecalho.jsp"><jsp:param name="titulo" value="Visão geral"/></jsp:include>
<div class="page-head">
  <div><h1>Painel de operação</h1><p>Acompanhe o ritmo da produção e os pontos que precisam de ação.</p></div>
  <div class="date-note">ATUALIZADO EM TEMPO REAL &nbsp;•&nbsp; UNIDADE MG</div>
</div>
<div class="cards">
  <article class="metric orange"><span class="metric-label">Produção acumulada</span><strong><fmt:formatNumber value="${resumo.producaoTotal}" maxFractionDigits="0"/> t</strong><small class="trend">↑ Apontamento do alto-forno 01</small></article>
  <article class="metric green"><span class="metric-label">Usuários ativos</span><strong>${resumo.usuariosAtivos}</strong><small>Equipe autorizada no sistema</small></article>
  <article class="metric amber"><span class="metric-label">Treinamentos pendentes</span><strong>${resumo.treinamentosPendentes}</strong><small class="risk">Requer validação de segurança</small></article>
  <article class="metric red"><span class="metric-label">Estoque em alerta</span><strong>${resumo.estoqueCritico}</strong><small class="risk">Abaixo do mínimo operacional</small></article>
</div>
<div class="panel-grid">
  <section class="panel">
    <div class="panel-title"><h2>Itens de controle</h2><a href="${pageContext.request.contextPath}/indicadores">GERENCIAR INDICADORES →</a></div>
    <div class="semaphore">
      <c:forEach var="item" items="${resumo.indicadores}">
        <div class="semaphore-row">
          <span class="lamp ${item.status}"></span>
          <div class="item-name"><strong><c:out value="${item.nome}"/></strong><small><c:out value="${item.area}"/></small></div>
          <div class="value"><fmt:formatNumber value="${item.valorAtual}" maxFractionDigits="1"/><small>meta <fmt:formatNumber value="${item.meta}" maxFractionDigits="1"/></small></div>
          <span class="badge ${item.status}">${item.status}</span>
        </div>
      </c:forEach>
    </div>
  </section>
  <section class="panel">
    <div class="panel-title"><h2>Saúde dos indicadores</h2><a href="${pageContext.request.contextPath}/indicadores">VER DETALHES</a></div>
    <div class="donut-wrap">
      <div class="donut" aria-label="Distribuição dos itens de controle"></div>
      <ul class="legend">
        <li><span><i class="lamp VERDE"></i> Dentro da meta</span><b>${resumo.verdes}</b></li>
        <li><span><i class="lamp AMARELO"></i> Em atenção</span><b>${resumo.amarelos}</b></li>
        <li><span><i class="lamp VERMELHO"></i> Fora da meta</span><b>${resumo.vermelhos}</b></li>
      </ul>
    </div>
    <div class="panel-title"><h2>Último apontamento</h2><a href="${pageContext.request.contextPath}/producao">PRODUÇÃO →</a></div>
    <div class="quick-list"><c:forEach var="p" items="${resumo.producoes}" begin="0" end="1"><div><strong><c:out value="${p.altoForno}"/> — <fmt:formatNumber value="${p.toneladas}" maxFractionDigits="0"/> t</strong><span>Qualidade <fmt:formatNumber value="${p.qualidade}" maxFractionDigits="1"/>% · <fmt:formatDate value="${p.data}" pattern="dd/MM/yyyy"/></span></div></c:forEach></div>
  </section>
</div>
<jsp:include page="componentes/rodape.jsp"/>