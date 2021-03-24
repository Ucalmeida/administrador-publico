<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 23/03/2021
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <jsp:include page="head.jsp" />
    <title>Localizar</title>
</head>
<body>
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content-header">
            <div class="container-fluid">
                <form id="formLocalizarPessoa" action="localizarPessoa" method="post" class="form" role="form">
                    <div id="dadosPessoa" class="row">
                        <div class="form-group col-lg-4">
                            <label for="cpf" class="control-label">CPF</label>
                            <input id="cpf" name="pessoa.cpf" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="rg" class="control-label">RG</label>
                            <input id="rg" name="pessoa.rg" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="nome" class="control-label">Nome</label>
                            <input id="nome" name="pessoa.nome" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="bairro" class="control-label">Bairro</label>
                            <select id="bairro" name="bairro.id" class="form-control form-control-select form-control-width">
                                <option value=""></option>
                                <option value="1">Exemplo 1</option>
                                <option value="2">Exemplo 2</option>
                                <option value="3">Exemplo 3</option>
                                <option value="4">Exemplo 4</option>
                                <option value="5">Exemplo 5</option>
                            </select>
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="rua" class="control-label">Rua</label>
                            <input id="rua" name="rua.nome" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="numero" class="control-label">Número</label>
                            <input id="numero" name="endereco.numero" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="pontoReferencia" class="control-label">Ponto Referência</label>
                            <input id="pontoReferencia" name="endereco.pontoReferencia" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="condominio" class="control-label">Condomínio</label>
                            <input id="condominio" name="endereco.condominio" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="edificio" class="control-label">Edifício</label>
                            <input id="edificio" name="endereco.edificio" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="uf" class="control-label">UF</label>
                            <input id="uf" name="endereco.uf" class="form-control">
                        </div>
                        <div class="form-group col-lg-4">
                            <label for="pais" class="control-label">País</label>
                            <input id="pais" name="endereco.pais" class="form-control">
                        </div>
                        <div class="form-group col-lg-3" style="margin-top: auto">
                            <button id="btnPesquisar" type="submit" class="btn btn-success right">Localizar Pessoa</button>
                        </div>
                    </div>
                </form>
                <form id="formTabelaLocalizarPessoas" method="post" role="form" data-toggle="validator" style="background-color: white;">
                    <table id="tbPessoas" class="table table-striped table-bordered"></table>
                </form>
            </div>
        </section>
    </div>
    <jsp:include page="importacaoScripts.jsp" />
    <script src="plugins/jquery/jquery.dataTables.js" type="text/javascript"></script>
    <script type='text/javascript' charset='utf-8' src='js/formLocalizarPessoa.js'></script>
</body>
</html>