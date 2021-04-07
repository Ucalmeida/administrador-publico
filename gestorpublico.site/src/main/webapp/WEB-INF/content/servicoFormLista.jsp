<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="gep" uri="gepTags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<!DOCTYPE html>--%>
<%--<html lang='pt-BR'>--%>
<%--<head>--%>
<%--    <jsp:include page="head.jsp" />--%>

<%--    <title>Serviço Solicitado</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--    <div class="wrapper">--%>
<%--        <jsp:include page="menuLateral.jsp" />--%>
    <gep:pagina titulo="Serviços Solicitados" js="servicoFormLista" tabela="true" link="painel">
<%--        <div class="content-wrapper" style="min-height: 296px;">--%>
<%--            <section class="content">--%>
<%--                <div class="container-fluid">--%>
<%--                    <div class="row">--%>
<%--                        <div class="col-lg-12">--%>
<%--                            <h4 class="titulo">Serviços solicitados</h4>--%>
<%--                        </div>--%>
                        <div class="col-lg-12 table-responsive">
                            <table id="tbServicos" class="table table-striped table-bordered" width="100%"><c:forEach items="${servicos}" var="o">
                                <tr>
                                    <td>${o.servico.nome}</td>
                                    <td>${o.dataHoraCadastroFormatada}</td>
                                    <td>${o.observacao}</td>
                                    <td>${o.autorizadoFormatado}</td>
                                    <td>${o.despacho}</td>
                                    <td><c:if test="${o.autorizado eq null}"><a onclick="javascript:excluir('pessoaServico',${o.id})" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a></c:if></td>
                                </tr></c:forEach>
                            </table>
                        </div>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </section>--%>
<%--        </div>--%>
    </gep:pagina>
    <jsp:include page="rodape.jsp" />
<%--    </div>--%>
<%--    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>--%>
<%--    <script type="text/javascript" charset="utf-8" src="js/tabela.js"></script>--%>
<%--    <script type="text/javascript" charset="utf-8" src="js/servicoFormLista.js"></script>--%>
<%--</body>--%>
<%--</html>--%>