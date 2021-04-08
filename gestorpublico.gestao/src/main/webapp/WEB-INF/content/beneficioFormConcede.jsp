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
                            <h4 class="titulo">Conceder Benefício</h4>
                        </div>
                        <div>
                            <form id="frmBeneficioConceder" action="pessoaBeneficioConceder" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="idBeneficiado" class="control-label">Benefíciado</label>
                                        <input id="idBeneficiado" name="nome" class="form-control" placeholder="Digite o CPF ou nome da mãe" />
                                        <input hidden id="idPessoa" name="beneficiado.id" />
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
                            <table id="tbConcessoes" class="table table-striped table-bordered" width="100%"><c:forEach items="${pessoas}" var="o">
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
    <script type="text/javascript" charset="utf-8" src="js/beneficioFormConcede.js"></script>
</body>
</html>
