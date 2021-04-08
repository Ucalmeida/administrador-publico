<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Pessoa Localiza</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Localizar Pessoa</h4>
                        </div>
                        <div class="col-lg-12">
                            <form id="frmPessoaLocalizar" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="nome" class="control-label">Cidadão</label>
                                        <input id="nome" name="nome" class="focus form-control" placeholder="Digite o CPF ou nome da Pessoa" />
                                        <input hidden id="idPessoa" name="pessoa.id" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-lg-12 table-responsive">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="active">
                                    <a href="#tab-table1" data-toggle="tab">Benefícios</a>
                                </li>
                                <li>
                                    <a href="#tab-table2" data-toggle="tab">Serviços</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab-table1">
                                    <table id="tbBeneficios" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                        <thead>
                                        <tr>
                                            <th>Em</th>
                                            <th>Benefício</th>
                                            <th>Observação</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                                <div class="tab-pane" id="tab-table2">
                                    <table id="tbServicos" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                        <thead>
                                        <tr>
                                            <th>Em</th>
                                            <th>Serviço</th>
                                            <th>Observação</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
<%--                        <div class="col-lg-12 table-responsive">--%>
<%--                            <table id="tbBeneficios" class="table table-striped table-bordered" width="100%"></table>--%>
<%--                        </div>--%>
<%--                        <div class="col-lg-12 table-responsive">--%>
<%--                            <table id="tbServicos" class="table table-striped table-bordered" width="100%"></table>--%>
<%--                        </div>--%>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/tabelaGestorPublico.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/pessoaFormLocaliza.js"></script>
</body>
</html>
