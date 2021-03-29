<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 24/03/2021
  Time: 01:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <jsp:include page="head.jsp" />
    <title>Benefícios</title>
</head>
<body>
<jsp:include page="menuLateral.jsp" />
<div class="content-wrapper" style="min-height: 296px;">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row">
                <div class="form-group col-lg-4">
                    <label for="nome" class="control-label">Nome</label>
                    <input id="nome" value="FULANO DE TAL JUNIOR" class="form-control" readonly>
                </div>
                <div class="form-group col-lg-4">
                    <label for="cpf" class="control-label">CPF</label>
                    <input id="cpf" value="999.888.777-66" class="form-control" readonly>
                </div>
                <div class="form-group col-lg-4">
                    <label for="valorTotal" class="control-label">Valor Total Recebido</label>
                    <input id="valorTotal" value="$$$$$$" class="form-control" readonly>
                </div>
            </div>
            <form id="formTabelaBeneficiosPorPessoa" action="cadastrarBeneficio" method="post" role="form" class="form" data-toggle="validator" style="background-color: white;">
                <table id="tbBeneficios" class="table table-striped table-bordered"></table>
            </form>
            <div class="form-group col-lg-3" style="margin-top: auto">
                <a class="btn btn-default" href="javascript:cadastrarBeneficio()"><span class="fa fa-fw fa-edit orange"></span>Cadastrar Benefício</a>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
<script src="plugins/jquery/jquery.dataTables.js" type="text/javascript"></script>
<script type='text/javascript' charset='utf-8' src='js/formVerBeneficios.js'></script>
</body>
</html>