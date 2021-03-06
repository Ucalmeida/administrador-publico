<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Benefício Solicitado</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Benefícios solicitados</h4>
                        </div>
                        <div class="col-lg-12 table-responsive">
                            <table id="tbBeneficios" class="table table-striped table-bordered" width="100%"><c:forEach items="${beneficios}" var="o">
                                <tr id="ln${o.id}">
                                    <td>${o.beneficiado.nome}</td>
                                    <td>${o.beneficio.nome}</td>
                                    <td>${o.dataHoraCadastroFormatada}</td>
                                    <td>${o.observacao}</td>
                                    <td>${o.autorizadoFormatado}</td>
                                    <td>${o.despacho}</td>
                                    <td><c:if test="${o.autorizado eq null}"><a onclick="javascript:excluir('pessoaServico',${o.id})" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a></c:if></td>
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
    <script type="text/javascript" charset="utf-8" src="js/beneficioFormLista.js"></script>
</body>
</html>
