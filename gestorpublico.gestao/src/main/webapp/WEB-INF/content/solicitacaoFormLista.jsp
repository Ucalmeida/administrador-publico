<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Solicitações</title>
</head>
<body>
    <div class="wrapper">
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h4 class="titulo">Solicitações</h4>
                    </div>
                    <div class="row col-lg-12">
                        <div id="accordion" class="col-lg-12">
                            <div class="card">
                                <div class="card-header bg-success" id="headingOne">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link" style="text-align: left; color: white; padding: 0px; width: 100%;" data-toggle="collapse" data-target="#beneficios" aria-expanded="true" aria-controls="beneficios">
                                            <i class="nav-icon fa fa-handshake"></i> BENEFÍCIOS
                                        </button>
                                    </h5>
                                </div>

                                <div id="beneficios" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="col-lg-12 table-responsive mt-1">
                                        <table id="tbBeneficios" class="table table-striped table-bordered" width="100%"><c:forEach items="${beneficios}" var="o">
                                            <tr id="ln${o.get('id')}">
                                                <td>${o.get('data')}</td>
                                                <td>${o.get('beneficiado')}</td>
                                                <td>${o.get('beneficio')}</td>
                                                <td>${o.get('observacao')}</td>
                                                <td><a onclick="javascript:beneficioDespachar(${o.get('id')})" class="btn btn-default"><i class="fas fa-home"></i> Despachar</a></td>
                                            </tr></c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div class="card">
                                <div class="card-header bg-info" id="headingTwo">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link collapsed" style="text-align: left; color: white; padding: 0px; width: 100%;" data-toggle="collapse" data-target="#servicos" aria-expanded="false" aria-controls="servicos">
                                            <i class="nav-icon fas fa-cog"></i> SERVIÇOS
                                        </button>
                                    </h5>
                                </div>
                                <div id="servicos" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                                    <div class="col-lg-12 table-responsive mt-1">
                                        <table id="tbServicos" class="table table-striped table-bordered" width="100%"><c:forEach items="${servicos}" var="o">
                                            <tr id="ln${o.get('id')}">
                                                <td>${o.get('data')}</td>
                                                <td>${o.get('solicitante')}</td>
                                                <td>${o.get('servico')}</td>
                                                <td>${o.get('observacao')}</td>
                                                <td><a onclick="javascript:servicoDespachar(${o.get('id')})" class="btn btn-default"><i class="fas fa-home"></i> Despachar</a></td>
                                            </tr></c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/dataTables.bootstrap4.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/solicitacaoFormLista.js"></script>
</body>
</html>
