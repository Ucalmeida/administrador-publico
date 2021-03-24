<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 24/03/2021
  Time: 02:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 20/03/2021
  Time: 00:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <jsp:include page="head.jsp" />
    <style>
        #btnCadastro {
            margin: auto;
            text-align: center;
        }
    </style>
    <title>Cadastro</title>
</head>
<body>
<jsp:include page="menuLateral.jsp" />
<div class="content-wrapper" style="min-height: 296px;">
    <section class="content-header">
        <div class="container-fluid">
            <form id="formNovoBeneficio" action="cadastrarBeneficio" method="post" class="form" role="form">
                <div id="dadosBeneficio" class="row">
                    <div class="form-group col-lg-3">
                        <label for="nome" class="control-label">Nome</label>
                        <input id="nome" name="beneficio.nome" class="form-control">
                    </div>
                    <div class="form-group col-lg-3">
                        <label for="valor" class="control-label">Valor</label>
                        <input id="valor" name="beneficio.valor" class="form-control">
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="descricao" class="control-label">Descrição</label>
                        <textarea id="descricao" name="beneficio.descricao" class="form-control"></textarea>
                    </div>
                    <div class="col-lg-12">
                        <div class="info-box">
                            <div class="form-group col-lg-2 mt-3">
                                <button id="btnCadastrar" type="submit" class="btn btn-success right">Cadastrar Benefício</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="form-group col-lg-12">
                <form id="formTabelaNovosBeneficios" method="post" role="form" data-toggle="validator" style="background-color: white;">
                    <table id="tbBeneficios" class="table table-striped table-bordered"></table>
                </form>
            </div>
        </div>
    </section>
</div>
<jsp:include page="importacaoScripts.jsp" />
<script src="plugins/jquery/jquery.dataTables.js" type="text/javascript"></script>
<script type='text/javascript' charset='utf-8' src='js/formNovoBeneficio.js'></script>
</body>
</html>