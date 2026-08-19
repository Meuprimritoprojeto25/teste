<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Sistema gerencial da operação siderúrgica">
  <title>Sideral Gerencial</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sideral.css">
</head>
<body>
<div class="app-shell">
  <aside class="sidebar" id="sidebar">
    <a class="brand" href="${pageContext.request.contextPath}/"><span class="brand-mark">S</span><span>Sideral<small>GERENCIAL</small></span></a>
    <nav aria-label="Navegação principal">
      <a href="${pageContext.request.contextPath}/" class="${pageContext.request.servletPath == '/painel' ? 'active' : ''}"><i>◈</i> Visão geral</a>
      <p class="nav-label">OPERAÇÃO</p>
      <a href="${pageContext.request.contextPath}/producao" class="${pageContext.request.servletPath == '/producao' ? 'active' : ''}"><i>▥</i> Produção</a>
      <a href="${pageContext.request.contextPath}/indicadores" class="${pageContext.request.servletPath == '/indicadores' ? 'active' : ''}"><i>◉</i> Itens de controle</a>
      <a href="${pageContext.request.contextPath}/estoque" class="${pageContext.request.servletPath == '/estoque' ? 'active' : ''}"><i>□</i> Estoque e insumos</a>
      <p class="nav-label">PESSOAS</p>
      <a href="${pageContext.request.contextPath}/usuarios" class="${pageContext.request.servletPath == '/usuarios' ? 'active' : ''}"><i>♙</i> Usuários</a>
      <a href="${pageContext.request.contextPath}/treinamentos" class="${pageContext.request.servletPath == '/treinamentos' ? 'active' : ''}"><i>↗</i> Treinamento motorista</a>
    </nav>
    <div class="sidebar-foot"><span class="online-dot"></span> Operação sincronizada<br><small>Unidade Minas Gerais</small></div>
  </aside>
  <main class="content">
    <header class="topbar">
      <button class="menu-button" type="button" data-menu aria-label="Abrir navegação">☰</button>
      <div class="crumb"><span>OPERAÇÕES</span><strong>${param.titulo}</strong></div>
      <div class="top-user"><span class="user-avatar">MS</span><span>Marina Santos<small>Gerente industrial</small></span></div>
    </header>
    <section class="page">
      <c:if test="${not empty mensagem}"><div class="flash" role="status"><span>✓</span><c:out value="${mensagem}"/></div></c:if>