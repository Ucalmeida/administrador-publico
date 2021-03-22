<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="head.jsp" />
	<title>GESTOR PÚBLICO | Esqueci a Senha</title>
</head>
<body class="hold-transition login-page">
<div class="login-box">
	<div class="login-logo">
		<a href="index">
			<img src="images/brasoes/brasao_riachuelo_se_100_100.png" alt="PMSE Logo" class="brand-image">
		</a>
	</div>
	<!-- /.login-logo -->
	<div class="card">
		<div class="card-body card-outline card-info login-card-body">
			<p class="login-box-msg">Esqueceu sua senha?</p>
			<p class="login-box-msg text-justify">Por favor informe abaixo o seu CPF para enviarmos um link com as instruções de redefinição de senha para o seu email.</p>

			<form id="formRecuperaSenha" name="formRecuperaSenha" action="esqueciMinhaSenhaCadastrar" method="post">
				<div class="input-group mb-3">
					<input type="text" name="cpf" id="inputText" class="form-control" placeholder="CPF" autofocus>
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-envelope"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="submit" class="btn btn-info btn-block" id="enviar">Enviar</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			<p class="mt-3 mb-1">
				<a href="index" class="text-info"><i class="fas fa-arrow-alt-circle-left mr-1"></i>Voltar para o login</a>
			</p>
		</div>
		<!-- /.login-card-body -->
	</div>
	<footer class="text-xs text-center mt-5">
		<p>&copy; 2021 - Gestor Público - Todos os Direitos Reservados!</p>
		<p>Riachuelo</p>
	</footer>
</div>
<!-- /.login-box -->

<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="plugins/jquery/jquery-validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="plugins/jquery/jquery-validate/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="plugins/jquery/jquery.mask.js"></script>
<script type="text/javascript" src="plugins/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="plugins/bootbox/bootbox.all.min.js"></script>
<script type="text/javascript" src="js/core/adminlte.min.js"></script>
<script type="text/javascript" src="js/core/cassUtil.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/esqueciMinhaSenha.js"></script>

</body>
</html>
