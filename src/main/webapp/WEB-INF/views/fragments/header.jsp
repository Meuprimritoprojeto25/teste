<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <meta name="description" content="FerroGestão, gestão de performance e rotina siderúrgica">
  <title>${empty pageTitle ? 'FerroGestão Industrial' : pageTitle}</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/app.css'/>">
</head>
<body>
<div class="shell">
  <aside class="sidebar" id="sidebar">
    <a class="brand" href="<c:url value='/'/>"><span class="brand-mark">FG</span><span><b>FerroGestão</b><small>Performance industrial</small></span></a>
    <div class="nav-title">Visão gerencial</div>
    <a class="nav-link" href="<c:url value='/'/>"><span class="nav-icon">▦</span>Centro de controle</a>
    <a class="nav-link" href="<c:url value='/acompanhamentos'/>"><span class="nav-icon">●</span>Farol &amp; acompanhamento</a>
    <a class="nav-link" href="<c:url value='/itens-controle'/>"><span class="nav-icon">◎</span>Itens de controle</a>
    <a class="nav-link" href="<c:url value='/desdobramentos'/>"><span class="nav-icon">⌘</span>Desdobramentos</a>
    <a class="nav-link" href="<c:url value='/r3g'/>"><span class="nav-icon">R3</span>Relatórios R3G</a>
    <div class="nav-title">Operação</div>
    <a class="nav-link" href="<c:url value='/producao'/>"><span class="nav-icon">⚙</span>Produção siderúrgica</a>
    <a class="nav-link" href="<c:url value='/treinamentos'/>"><span class="nav-icon">▣</span>Treinamento motorista</a>
    <a class="nav-link" href="<c:url value='/documentos'/>"><span class="nav-icon">▤</span>Documentos</a>
    <a class="nav-link" href="<c:url value='/relatorios'/>"><span class="nav-icon">↧</span>Central de relatórios</a>
    <div class="nav-title">Administração</div>
    <a class="nav-link" href="<c:url value='/usuarios'/>"><span class="nav-icon">♙</span>Usuários e acessos</a>
  </aside>
  <main class="main">
    <header class="topbar">
      <div style="display:flex;align-items:center;gap:12px"><button class="mobile-menu" id="mobile-menu" aria-expanded="false" aria-label="Abrir menu">☰</button><div class="crumb">FerroGestão &nbsp;/&nbsp; <strong>${empty pageTitle ? 'Centro de controle' : pageTitle}</strong></div></div>
      <div class="top-actions"><input class="search" type="search" placeholder="Buscar indicador, relatório..."><span style="font-size:16px">♧</span><span class="avatar">MS</span></div>
    </header>
    <div class="content">
      <c:if test="${not empty message}"><div class="flash">${message}</div></c:if>