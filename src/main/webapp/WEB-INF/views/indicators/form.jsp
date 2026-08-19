<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${empty item.id ? 'Novo item de controle' : 'Editar item'}" scope="request"/><jsp:include page="../fragments/header.jsp"/>
<div class="page-heading"><div><p class="eyebrow">KPI industrial</p><h1>${empty item.id ? 'Novo item de controle' : 'Editar item de controle'}</h1><p>Configure a regra que alimentará o farol automaticamente.</p></div></div>
<form class="form-card" action="<c:url value='/itens-controle/salvar'/>" method="post"><input type="hidden" name="id" value="${item.id}"><input type="hidden" name="version" value="${item.version}">
<div class="form-section"><h2>Definição do indicador</h2><div class="form-grid">
<div class="field"><label>Código *</label><input name="code" value="${item.code}" required placeholder="PRD-001"></div><div class="field"><label>Nome *</label><input name="name" value="${item.name}" required></div>
<div class="field"><label>Área *</label><input name="area" value="${item.area}" required placeholder="Aciaria"></div><div class="field"><label>Responsável</label><select name="owner.id"><option value="">Selecione</option><c:forEach items="${users}" var="u"><option value="${u.id}" ${item.owner.id == u.id ? 'selected' : ''}>${u.name}</option></c:forEach></select></div>
<div class="field"><label>Periodicidade</label><select name="periodicity"><option>Diário</option><option>Semanal</option><option>Mensal</option><option>Por turno</option></select></div><div class="field"><label>Unidade *</label><input name="unit" value="${item.unit}" required placeholder="t, %, kWh/t"></div>
</div></div><div class="form-section"><h2>Meta e regra do farol</h2><div class="form-grid">
<div class="field"><label>Direção</label><select name="direction"><c:forEach items="${directions}" var="d"><option value="${d}" ${item.direction == d ? 'selected' : ''}>${d.label}</option></c:forEach></select></div>
<div class="field"><label>Meta *</label><input type="number" step="any" name="target" value="${item.target}" required></div>
<div class="field"><label>Limite de atenção *</label><input type="number" step="any" name="warningLimit" value="${item.warningLimit}" required></div>
<div class="field"><label>Limite inferior (para faixa)</label><input type="number" step="any" name="lowerLimit" value="${item.lowerLimit}"></div>
<div class="field full"><label class="check"><input type="checkbox" name="active" value="true" ${item.active ? 'checked' : ''}> Indicador ativo para apontamentos</label></div>
</div></div><div class="form-actions"><a class="btn btn-secondary" href="<c:url value='/itens-controle'/>">Cancelar</a><button class="btn btn-primary">Salvar item</button></div></form>
<jsp:include page="../fragments/footer.jsp"/>