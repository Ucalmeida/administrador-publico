<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="icon" href="images/favicon.ico" />
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="css/style.css" />
	
	<script type="text/javascript" charset="utf-8" src="js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
	<script type="text/javascript" charset="utf-8" src="js/pagina_inicial.js"></script>
	
	<title>${titulo}</title>
</head>
<body>
	<jsp:include page="cabecalho.jsp" />
	
	<div>
	Bem-vindo a Gestão!
	</div>

	<jsp:include page="rodape.jsp" />
</body>
</html>