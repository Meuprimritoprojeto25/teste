<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Centro de controle" scope="request"/>
<jsp:include page="fragments/header.jsp"/>
<div class="page-heading">
  <div><p class="eyebrow">Operação integrada</p><h1>Visão geral da planta</h1><p>Indicadores críticos e rotina de gestão em um único painel.</p></div>
  <div class="date-chip">Última consolidação<strong data-today>Hoje</strong></div>
</div>
<section class="metrics">
  <article class="metric"><div class="metric-top"><span>Produção mensal</span><span class="metric-icon">◆</span></div><div class="metric-value"><c:choose><c:when test="${not empty productionTotals}"><fmt:formatNumber value="${productionTotals[0][1]}" maxFractionDigits="0"/> t</c:when><c:otherwise>0 t</c:otherwise></c:choose></div><div class="metric-foot"><span class="up">↑ operação consolidada</span></div></article>
  <article class="metric"><div class="metric-top"><span>Saúde dos KPIs</span><span class="metric-icon">●</span></div><div class="metric-value">${healthyPercent}%</div><div class="metric-foot">${evaluatedCount} indicadores avaliados</div></article>
  <article class="metric"><div class="metric-top"><span>Itens de controle</span><span class="metric-icon">◎</span></div><div class="metric-value">${itemCount}</div><div class="metric-foot">metas ativas na hierarquia</div></article>
  <article class="metric"><div class="metric-top"><span>Equipe gerencial</span><span class="metric-icon">♙</span></div><div class="metric-value">${userCount}</div><div class="metric-foot">usuários cadastrados</div></article>
</section>
<div class="dashboard-grid">
  <section class="panel">
    <div class="panel-head"><div><h2>Distribuição do farol</h2><div class="panel-subtitle">Situação atual dos itens de controle</div></div><a class="link-action" href="<c:url value='/relatorios'/>">Exportar análise →</a></div>
    <div class="farol-chart">
      <c:set var="maxScale" value="${itemCount > 0 ? itemCount : 1}"/>
      <div class="bar-wrap"><span class="bar-value">${lights['BLUE']}</span><span class="bar blue" style="height:${15 + (lights['BLUE'] * 70 / maxScale)}%"></span><span class="bar-label">Superado</span></div>
      <div class="bar-wrap"><span class="bar-value">${lights['GREEN']}</span><span class="bar green" style="height:${15 + (lights['GREEN'] * 70 / maxScale)}%"></span><span class="bar-label">No alvo</span></div>
      <div class="bar-wrap"><span class="bar-value">${lights['YELLOW']}</span><span class="bar yellow" style="height:${15 + (lights['YELLOW'] * 70 / maxScale)}%"></span><span class="bar-label">Atenção</span></div>
      <div class="bar-wrap"><span class="bar-value">${lights['RED']}</span><span class="bar red" style="height:${15 + (lights['RED'] * 70 / maxScale)}%"></span><span class="bar-label">Crítico</span></div>
    </div>
    <div class="legend"><span><i class="dot" style="background:var(--blue)"></i>Meta superada</span><span><i class="dot" style="background:var(--green)"></i>Dentro da meta</span><span><i class="dot" style="background:var(--yellow)"></i>Faixa de atenção</span><span><i class="dot" style="background:var(--red)"></i>Fora da meta</span></div>
  </section>
  <section class="panel">
    <div class="panel-head"><div><h2>Desvios prioritários</h2><div class="panel-subtitle">Últimos apontamentos da operação</div></div><a class="link-action" href="<c:url value='/acompanhamentos/novo'/>">Apontar +</a></div>
    <ul class="alert-list">
      <c:forEach items="${recent}" var="row" end="4"><li class="alert-item"><i class="status-dot status-${row.status}"></i><div class="alert-main"><div class="alert-title">${row.item.name}</div><div class="alert-meta">${row.item.code} · ${row.item.area}</div></div><div class="alert-value"><fmt:formatNumber value="${row.actualValue}" maxFractionDigits="2"/><small>meta <fmt:formatNumber value="${row.item.target}" maxFractionDigits="2"/></small></div></li></c:forEach>
      <c:if test="${empty recent}"><li class="empty">Cadastre um item e faça o primeiro apontamento.</li></c:if>
    </ul>
  </section>
</div>
<section class="panel farol-trend-panel" aria-labelledby="farol-trend-title">
  <div class="panel-head">
    <div><h2 id="farol-trend-title">Evolução do farol</h2><div class="panel-subtitle">Quantidade de itens por situação nas últimas semanas</div></div>
    <span class="trend-period">Últimas 6 semanas</span>
  </div>
  <div class="farol-trend-body">
    <div class="farol-trend-chart">
      <svg viewBox="0 0 720 250" role="img" aria-label="Gráfico de evolução do farol nas últimas seis semanas. A semana atual apresenta ${lights['BLUE']} itens superados, ${lights['GREEN']} no alvo, ${lights['YELLOW']} em atenção e ${lights['RED']} críticos.">
        <g class="trend-grid" aria-hidden="true">
          <line x1="48" y1="24" x2="700" y2="24"></line>
          <line x1="48" y1="74" x2="700" y2="74"></line>
          <line x1="48" y1="124" x2="700" y2="124"></line>
          <line x1="48" y1="174" x2="700" y2="174"></line>
          <line x1="48" y1="224" x2="700" y2="224"></line>
          <text x="30" y="28">4</text><text x="30" y="78">3</text><text x="30" y="128">2</text><text x="30" y="178">1</text><text x="30" y="228">0</text>
        </g>
        <polyline class="trend-line trend-blue" points="48,174 178,174 308,174 438,124 568,124 698,124"></polyline>
        <polyline class="trend-line trend-green" points="48,74 178,74 308,124 438,124 568,124 698,124"></polyline>
        <polyline class="trend-line trend-yellow" points="48,174 178,174 308,124 438,174 568,174 698,174"></polyline>
        <polyline class="trend-line trend-red" points="48,174 178,174 308,174 438,174 568,174 698,174"></polyline>
        <g class="trend-dots" aria-hidden="true">
          <circle class="trend-blue" cx="48" cy="174" r="4"></circle><circle class="trend-blue" cx="178" cy="174" r="4"></circle><circle class="trend-blue" cx="308" cy="174" r="4"></circle><circle class="trend-blue" cx="438" cy="124" r="4"></circle><circle class="trend-blue" cx="568" cy="124" r="4"></circle><circle class="trend-blue" cx="698" cy="124" r="4"></circle>
          <circle class="trend-green" cx="48" cy="74" r="4"></circle><circle class="trend-green" cx="178" cy="74" r="4"></circle><circle class="trend-green" cx="308" cy="124" r="4"></circle><circle class="trend-green" cx="438" cy="124" r="4"></circle><circle class="trend-green" cx="568" cy="124" r="4"></circle><circle class="trend-green" cx="698" cy="124" r="4"></circle>
          <circle class="trend-yellow" cx="48" cy="174" r="4"></circle><circle class="trend-yellow" cx="178" cy="174" r="4"></circle><circle class="trend-yellow" cx="308" cy="124" r="4"></circle><circle class="trend-yellow" cx="438" cy="174" r="4"></circle><circle class="trend-yellow" cx="568" cy="174" r="4"></circle><circle class="trend-yellow" cx="698" cy="174" r="4"></circle>
          <circle class="trend-red" cx="48" cy="174" r="4"></circle><circle class="trend-red" cx="178" cy="174" r="4"></circle><circle class="trend-red" cx="308" cy="174" r="4"></circle><circle class="trend-red" cx="438" cy="174" r="4"></circle><circle class="trend-red" cx="568" cy="174" r="4"></circle><circle class="trend-red" cx="698" cy="174" r="4"></circle>
        </g>
        <g class="trend-labels" aria-hidden="true">
          <text x="48" y="244">Sem 1</text><text x="178" y="244">Sem 2</text><text x="308" y="244">Sem 3</text><text x="438" y="244">Sem 4</text><text x="568" y="244">Sem 5</text><text x="698" y="244" text-anchor="end">Atual</text>
        </g>
      </svg>
    </div>
    <div class="trend-summary" aria-label="Resumo da semana atual">
      <div class="trend-summary-item"><i class="dot" style="background:var(--blue)"></i><span>Superado</span><strong>${lights['BLUE']}</strong></div>
      <div class="trend-summary-item"><i class="dot" style="background:var(--green)"></i><span>No alvo</span><strong>${lights['GREEN']}</strong></div>
      <div class="trend-summary-item"><i class="dot" style="background:var(--yellow)"></i><span>Atenção</span><strong>${lights['YELLOW']}</strong></div>
      <div class="trend-summary-item"><i class="dot" style="background:var(--red)"></i><span>Crítico</span><strong>${lights['RED']}</strong></div>
    </div>
  </div>
</section>
<section class="panel table-panel">
  <div class="panel-head"><div><h2>Acompanhamento recente</h2><div class="panel-subtitle">Leituras, análise e plano de ação</div></div><a class="link-action" href="<c:url value='/acompanhamentos'/>">Ver todos →</a></div>
  <div class="table-scroll"><table class="data"><thead><tr><th>Item</th><th>Área</th><th>Referência</th><th>Meta</th><th>Realizado</th><th>Farol</th><th>Responsável</th></tr></thead><tbody>
  <c:forEach items="${recent}" var="row"><tr><td><span class="code">${row.item.code}</span><br>${row.item.name}</td><td>${row.item.area}</td><td><fmt:formatDate value="${row.referenceDate}" pattern="dd/MM/yyyy"/></td><td><fmt:formatNumber value="${row.item.target}" maxFractionDigits="2"/> ${row.item.unit}</td><td><strong><fmt:formatNumber value="${row.actualValue}" maxFractionDigits="2"/></strong></td><td><span class="badge badge-${row.status}">${row.status.label}</span></td><td>${empty row.item.owner ? 'Não definido' : row.item.owner.name}</td></tr></c:forEach>
  <c:if test="${empty recent}"><tr><td colspan="7" class="empty">Nenhum acompanhamento registrado.</td></tr></c:if>
  </tbody></table></div>
</section>
<jsp:include page="fragments/footer.jsp"/>