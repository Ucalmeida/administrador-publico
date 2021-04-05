<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Serviço</title>
</head>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Solicitar Serviço</h4>
                            <form id="frmServicoSolicitar" action="pessoaServicoCadastrar" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="idServico" class="control-label">Serviço</label>
                                        <select id="idServico" name="servico.id" class="focus form-control form-control-select" required>
                                            <option></option><c:forEach items="${servicos}" var="o">
                                            <option value="${o.get('id')}" title="${o.get('setor')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-1" style="margin-top: auto">
                                        <button type="submit" class="btn btn-primary right">Solicitar</button>
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
                            <table id="tbServicos" class="table table-striped table-bordered" width="100%"></table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
<%--    <script type="text/javascript" charset="utf-8" src="js/tabela.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="js/datatables.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/dataTables.bootstrap4.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/servicoFormSolicita.js"></script>
</body>
</html>
