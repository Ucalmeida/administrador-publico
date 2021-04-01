<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Serviço</title>
</head>
<body>
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
                                    <select id="idServico" name="servico.id" class="focus form-control form-control-select">
                                        <option></option><c:forEach items="${servicos}" var="o">
                                        <option value="${o.get('id')}" title="${o.get('setor')}">${o.get('nome')}</option>
                                    </c:forEach></select>
                                </div>
                                <div class="form-group col-1">
                                    <button type="submit" class="btn-top btn btn-primary">Solicitar</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-lg-4 col-md-4">
                                    <label for="observacao" class="control-label">Observação</label>
                                    <textarea id="observacao" class="form-control" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="row">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="rodape.jsp" />
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/servicoFormSolicita.js"></script>
</body>
</html>
