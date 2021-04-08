<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Serviço Concessão</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Conceder Serviço</h4>
                        </div>
                        <div class="col-lg-12">
                            <form id="frmServicoConceder" action="pessoaServicoConceder" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="nome" class="control-label">Solicitante</label>
                                        <input id="nome" name="nome" class="focus form-control" placeholder="Digite o CPF ou nome do Solicitante" />
                                        <input hidden id="idSolicitante" name="solicitante.id" />
                                    </div>
                                    <div class="form-group col-lg-5 col-md-5">
                                        <label for="idServico" class="control-label">Serviço</label>
                                        <select id="idServico" name="servico.id" class="form-control form-control-select">
                                            <option></option><c:forEach items="${servicos}" var="o">
                                            <option value="${o.get('id')}" title="${o.get('setor')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-1">
                                        <button type="submit" class="btn-bottom btn btn-primary">Conceder</button>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="observacao" class="control-label">Observação</label>
                                        <textarea id="observacao" name="observacao" class="form-control" rows="3"></textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-lg-12 table-responsive">
                            <table id="tbConcessoes" class="table table-striped table-bordered" width="100%"></table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/tabelaGestorPublico.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/servicoFormConcede.js"></script>
</body>
</html>
