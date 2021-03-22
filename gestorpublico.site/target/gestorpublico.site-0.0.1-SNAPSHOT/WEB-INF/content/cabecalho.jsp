<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top" style="background-color: black !important; border-bottom: solid 1px #717171;">
	<a class="navbar-brand" href="/">Logomarca</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
<%-- 		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<a class="nav-link" href="sorteios">SORTEIOS</a>
			</li>
		</ul><c:if test="${logado}">
		<li class="logado nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="void(0)" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> ${pessoaLogada.nome} | ${pessoaLogada.saldoFormatado}</a>
			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
				<a class="dropdown-item" href="caixa">Caixa</a>
				<a class="dropdown-item" href="logout">Sair</a>
			</div>
		</li></c:if><c:if test="${!logado}">
		<form id="formLogin" name="formLogin" action="logar" method="post">
			<input id="login" name="login" type="text" placeholder="login" />
			<input id="senha" name="senha" type="password" placeholder="senha" />
			<button id="entrar" class="btn btn-warning btn-sm" type="submit">Entrar</button>
		</form></c:if> --%>
	</div>
</nav>
