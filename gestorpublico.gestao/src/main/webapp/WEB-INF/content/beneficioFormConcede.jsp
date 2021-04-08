<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Cidadãos</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Cidadãos</h4>
                        </div>
                        <div class="col-lg-12 table-responsive">
                            <table id="tbCidadaos" class="table table-striped table-bordered" width="100%"><c:forEach items="${pessoas}" var="o">
                                <tr id="ln${o.get('id')}">
                                    <td>${o.get('nome')}</td>
                                    <td><span data-mask="000.000.000-00">${o.get('cpf')}</span></td>
                                    <td>${o.get('dataNascimento')}</td>
                                    <td><span data-mask="(00)00000-0000">${o.get('celular')}</span></td>
                                    <td><a onclick="javascript:void()" class="btn btn-default"><i class="fas fa-home"></i> Endereço</a></td>
                                </tr></c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/tabelaGestorPublico.js"></script>
<%--    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/jquery.dataTables.min.js"></script>--%>
<%--    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>--%>
<%--    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/datatables.defaults.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="js/beneficioFormConcede.js"></script>
</body>
</html>
