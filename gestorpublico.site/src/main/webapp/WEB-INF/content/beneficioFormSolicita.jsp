<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Benefício</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Solicitar Benefício</h4>
                            <form id="frmBeneficioSolicitar" action="pessoaBeneficioCadastrar" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="idBeneficiado" class="control-label">Benefíciado</label>
                                        <select id="idBeneficiado" name="beneficiado.id" class="focus form-control form-control-select">
                                            <option></option>
                                            <option value="${pessoaLogada.id}">${pessoaLogada.nome}</option><c:forEach items="${dependentes}" var="o">
                                            <option value="${o.get('id')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-5 col-md-5">
                                        <label for="idBeneficio" class="control-label">Benefício</label>
                                        <select id="idBeneficio" name="beneficio.id" class="focus form-control form-control-select">
                                            <option></option><c:forEach items="${beneficios}" var="o">
                                            <option value="${o.get('id')}" title="${o.get('setor')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-1">
                                        <button type="submit" class="btn-bottom btn btn-primary">Solicitar</button>
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
                            <table id="tbBeneficios" class="table table-striped table-bordered" width="100%"></table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="../js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="../plugins/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="../plugins/dataTables/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="../plugins/dataTables/datatables.defaults.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/beneficioFormSolicita.js"></script>
</body>
</html>
