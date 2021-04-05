<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="mobile-web-app-capable" content="yes">
	<!-- TELA CHEIA PARA WEB APP -->

	<!-- Inicio tags nao salvar cache -->
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
	<!-- Fim tags nao salvar cache -->

	<link rel="icon" href="images/brasoes/brasao_riachuelo_se_100_100.png" />

	<!-- Font-->
	<link href="plugins/fontawesome/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

	<!-- CSS-->
	<link href="css/adminlte.min.css" rel="stylesheet">
	<link href="css/adminlte.portal.css" rel="stylesheet">

	<title>PREFEITURA RIACHUELO</title>

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body class="hold-transition login-page">

<div class="login-box">
	<div class="login-logo">
		<a href="index">
			<img alt="Brasão" src="images/brasoes/brasao_riachuelo_se_100_100.png">
		</a>
	</div>
	<!-- /.login-logo -->
	<c:if test="${mensagem ne null}">
		<div class="card card-outline card-primary">
			<div class="card-body login-card-body">
				<p class="login-box-msg">${mensagem}</p>
			</div>
		</div>
	</c:if>
	${mensagem}

	<div class="card card-outline card-danger">
		<div class="card-body login-card-body">
			<p class="login-box-msg">Bem-vindo ao <span class="font-weight-bold text-info">PORTAL</span> da <span class="font-weight-bold text-verdepetroleo">Gestão Pública</span></p>
			<p class="login-box-msg">Insira abaixo suas credenciais de acesso.</p>

			<form id="formLogin" name="formLogin" action="login" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="login" name="login" required autofocus placeholder="Usuário">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-user"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="password" class="form-control" id="senha" name="senha" required placeholder="Senha" value="">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-lock"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- /.col -->
					<div class="col-12">
						<button type="submit" class="btn btn-info btn-block" id="entrar">Entrar</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			<div class="social-auth-links text-center mb-3">
				<br>
				<p>
					<i class="fas fa-chevron-down"></i>
				</p>
			</div>

			<p class="mb-0 text-center">
				<a class="text-info" href="esqueciMinhaSenha">Esqueci a Senha</a>
				<br>
				<a class="text-info" href="javascript:semUsuario()" class="text-center">Não tenho um usuário</a>
			</p>
		</div>
		<!-- /.login-card-body -->
	</div>
	<footer class="text-xs text-center">
		<div>
			&copy; 2021 - Gestor Público - Todos os Direitos Reservados!<br/>
			Riachuelo
		</div>
	</footer>
</div>
<!-- /.login-box -->
<script type="text/javascript" src="plugins/jquery/jquery.min.js" />
<script type="text/javascript" src="plugins/bootstrap/bootstrap.bundle.min.js" />
<script type="text/javascript" src="plugins/bootbox/bootbox.all.js" />
<script type="text/javascript" src="plugins/jquery/jquery-validate/jquery.validate.min.js" />
<script type="text/javascript" src="plugins/jquery/jquery-validate/jquery.validate.defaults.js" />
<script type="text/javascript" src="plugins/jquery/jquery.mask.js" />
<script type="text/javascript" src="plugins/jquery/jquery.blockUI.js" />
<script type="text/javascript" src="js/core/adminlte.min.js" />
<script type="text/javascript" src="js/core/cassUtil.js" />
<script type="text/javascript" src="js/index.js" />
</body>
</html>
