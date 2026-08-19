<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${empty user.id ? 'Novo usuário' : 'Editar usuário'}" scope="request"/><jsp:include page="../fragments/header.jsp"/>
<div class="page-heading"><div><p class="eyebrow">Cadastro</p><h1>${empty user.id ? 'Novo usuário' : 'Editar usuário'}</h1><p>Defina identificação, lotação e perfil de acesso.</p></div></div>
<form class="form-card" action="<c:url value='/usuarios/salvar'/>" method="post"><input type="hidden" name="id" value="${user.id}"><input type="hidden" name="version" value="${user.version}">
<div class="form-section"><h2>Identificação</h2><div class="form-grid">
<div class="field"><label>Nome completo *</label><input name="name" value="${user.name}" required minlength="3"></div>
<div class="field"><label>Matrícula</label><input name="registration" value="${user.registration}"></div>
<div class="field"><label>E-mail corporativo *</label><input type="email" name="email" value="${user.email}" required></div>
<div class="field"><label>Área / departamento</label><input name="department" value="${user.department}" placeholder="Ex.: Alto-forno"></div>
<div class="field"><label>Perfil</label><select name="role"><option value="ANALISTA">Analista</option><option value="GESTOR">Gestor</option><option value="ADMINISTRADOR">Administrador</option><option value="LEITOR">Leitor</option></select></div>
<div class="field"><label>${empty user.id ? 'Senha inicial *' : 'Nova senha'}</label><input type="password" name="password" ${empty user.id ? 'required' : ''} minlength="6"><div class="help">${empty user.id ? 'Mínimo de 6 caracteres.' : 'Deixe em branco para manter a senha atual.'}</div></div>
<div class="field full"><label class="check"><input type="checkbox" name="active" value="true" ${user.active ? 'checked' : ''}> Usuário ativo</label></div>
</div></div><div class="form-actions"><a class="btn btn-secondary" href="<c:url value='/usuarios'/>">Cancelar</a><button class="btn btn-primary" type="submit">Salvar usuário</button></div></form>
<jsp:include page="../fragments/footer.jsp"/>