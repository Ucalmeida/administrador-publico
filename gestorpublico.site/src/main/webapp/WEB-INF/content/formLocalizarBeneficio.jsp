<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 24/03/2021
  Time: 02:43
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 23/03/2021
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <jsp:include page="head.jsp" />
    <title>Localizar</title>
</head>
<body>
<jsp:include page="menuLateral.jsp" />
<div class="content-wrapper" style="min-height: 296px;">
    <section class="content-header">
        <div class="container-fluid">
            <form id="formLocalizarBeneficio" action="localizarBeneficio" method="post" class="form" role="form">
                <div id="dadosBeneficio" class="row">
                    <div class="form-group col-lg-4">
                        <label for="nome" class="control-label">Nome Benefício</label>
                        <input id="nome" name="beneficio.nome" class="form-control">
                    </div>
                    <div class="form-group col-lg-3" style="margin-top: auto">
                        <button id="btnPesquisar" type="submit" class="btn btn-success right">Localizar Benefício</button>
                    </div>
                </div>
            </form>
            <form id="formTabelaLocalizarBeneficios" method="post" role="form" data-toggle="validator" style="background-color: white;">
                <table id="tbBeneficios" class="table table-striped table-bordered"></table>
            </form>
        </div>
    </section>
</div>
<jsp:include page="importacaoScripts.jsp" />
<script src="plugins/jquery/jquery.dataTables.js" type="text/javascript"></script>
<script type='text/javascript' charset='utf-8' src='js/formLocalizarBeneficio.js'></script>
</body>
</html>