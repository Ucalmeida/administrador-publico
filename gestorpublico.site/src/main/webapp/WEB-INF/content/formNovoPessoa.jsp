<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 20/03/2021
  Time: 00:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <jsp:include page="head.jsp" />
    <title>Cadastro</title>
</head>
<body>
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content-header">
            <div class="container-fluid">
                <div class="row">
                    <form id="formNovoPessoa" action="cadastrarPessoa" method="post" class="form" role="form">
                        <div class="row">
                            <div class="form-group col-lg-2">
                                <label for="cpf" class="control-label">CPF</label>
                                <input id="cpf" name="cpf" class="form-control">
                            </div>
                            <div class="form-group col-lg-5">
                                <label for="nome" class="control-label">Nome</label>
                                <input id="nome" name="pessoa.nome" class="form-control maiusculo" style="text-transform: uppercase;">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="dataNascimento" class="control-label">Nascimento</label>
                                <input id="dataNascimento" name="pessoa.dataNascimento" class="form-control data" maxlength="10">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="sexo" class="control-label">Sexo</label>
                                <select id="sexo" name="sexo.id" class="form-control form-control-select form-control-width">
                                    <option value=""></option>
                                    <c:forEach items="${sexos}" var="s">
                                        <option value="${s.id}">${s.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="cartaoSus" class="control-label">Cartão SUS</label>
                                <input id="cartaoSus" name="pessoa.cartaoSus" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="nis" class="control-label">NIS</label>
                                <input id="nis" name="pessoa.nis" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="celular" class="control-label">Celular</label>
                                <input id="celular" name="pessoa.celular" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="senha" class="control-label">Senha 1</label>
                                <input id="senha" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="senha2" class="control-label">Senha 2</label>
                                <input id="senha2" name="pessoa.senha" class="form-control">
                            </div>
                            <div class="card-columns">
                                <div class="form-group col-lg-2">
                                    <label for="ckPLiberados" class="control-label">Liberar Posts</label>
                                    <input id="ckPLiberados" type="checkbox" name="postsLiberados" checked />
                                </div>
                                <div class="form-group col-lg-2">
                                    <label for="ckMLiberadas" class="control-label">Liberar Mídias</label>
                                    <input id="ckMLiberadas" type="checkbox" name="midiasLiberadas" checked />
                                </div>
                                <div class="form-group col-lg-2">
                                    <label for="ckAcessaSistema" class="control-label">Liberar Acesso</label>
                                    <input id="ckAcessaSistema" type="checkbox" name="acessaSistema" checked />
                                </div>
                            </div>
                            <div class="form-group col-lg-2 mt-3">
                                <button id="btnSalvar" type="submit" class="btn btn-success right">Cadastrar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="rodape.jsp" />
    <script type='text/javascript' charset='utf-8' src='plugins/jquery/jquery.js'></script>
    <script type='text/javascript' charset='utf-8' src='js/formNovoPessoa.js'></script>
    <jsp:include page="importacaoScripts.jsp" />
</body>
</html>
