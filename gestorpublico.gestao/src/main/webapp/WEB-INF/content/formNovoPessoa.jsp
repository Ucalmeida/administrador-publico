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
    <style>
        #btnCadastro {
            margin: auto;
            text-align: center;
        }
    </style>
    <title>Cadastro</title>
</head>
<body>
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content-header">
            <div class="container-fluid">
                <div class="row">
                    <form id="formNovoPessoa" action="cadastrarPessoa" method="post" class="form" role="form">
                        <div id="dadosPessoa" class="row">
                            <div class="form-group col-lg-3">
                                <label for="cpf" class="control-label">CPF</label>
                                <input id="cpf" name="pessoa.cpf" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rg" class="control-label">RG</label>
                                <input id="rg" name="pessoa.rg" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rgOrgaoEmissor" class="control-label">Rg Orgão Emissor</label>
                                <input id="rgOrgaoEmissor" name="pessoa.rgOrgaoEmissor" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rgUf" class="control-label">RG UF</label>
                                <input id="rgUf" name="pessoa.rgUf" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="dataEmissaoRg" class="control-label">Data Emissão RG</label>
                                <input id="dataEmissaoRg" name="pessoa.dataEmissaoRg" class="form-control data" maxlength="10">
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
                                        <option value="${s.get('id')}">${s.get('nome')}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-lg-2">
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
                            <div class="form-group col-lg-3">
                                <label for="condominio" class="control-label">Condomínio</label>
                                <input id="condominio" name="endereco.condominio" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="edificio" class="control-label">Edifício</label>
                                <input id="edificio" name="endereco.edificio" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="uf" class="control-label">UF</label>
                                <input id="uf" name="endereco.uf" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="pais" class="control-label">País</label>
                                <input id="pais" name="endereco.pais" class="form-control">
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
                            <div class="form-group col-lg-4">
                                <label for="tituloEleitoral" class="control-label">Título Eleitoral Número</label>
                                <input id="tituloEleitoral" name="pessoa.titulo" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="zona" class="control-label">Zona</label>
                                <input id="zona" name="pessoa.zona" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="secao" class="control-label">Seção</label>
                                <input id="secao" name="pessoa.secao" class="form-control">
                            </div>
                            <div class="col-12 col-sm-6 col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <label for="senha" class="control-label">Digitar Senha</label>
                                        <input id="senha" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-sm-6 col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <label for="senha2" class="control-label">Repetir Senha</label>
                                        <input id="senha2" name="pessoa.senha" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckPLiberados"  name="postsLiberados" checked="" />
                                            <label for="ckPLiberados" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Posts</label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckMLiberadas"  name="midiasLiberadas" checked="" />
                                            <label for="ckMLiberadas" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Mídias</label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckAcessaSistema"  name="acessaSistema" checked="" />
                                            <label for="ckAcessaSistema" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Acesso</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="info-box">
                                    <div id="btnCadastro" class="form-group col-lg-2 mt-3">
                                        <button id="btnSalvar" type="submit" class="btn btn-success right">Cadastrar</button>
                                    </div>
                                    <div class="form-group col-lg-2 mt-3" id="dependentes">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="dependente" class="row" style="display: none">
                    <form id="formNovoDependente" action="cadastrarPessoa" method="post" class="form" role="form">
                        <div id="dadosDependente" class="row">
                            <div class="form-group col-lg-3">
                                <label for="tipoDependente" class="control-label">Tipo Dependente</label>
                                <select id="tipoDependente" name="tipo.id" class="form-control form-control-select form-control-width">
                                    <option value=""></option>
                                    <option value="1">Pai</option>
                                    <option value="2">Mãe</option>
                                    <option value="3">Filho(a)</option>
                                    <option value="4">Avô</option>
                                    <option value="5">Avó</option>
                                    <option value="6">Irmão</option>
                                    <option value="7">Irmã</option>
                                </select>
                            </div>
                            <div class="form-group col-lg-5">
                                <label for="nomeDep" class="control-label">Nome</label>
                                <input id="nomeDep" name="pessoa.nome" class="form-control maiusculo" style="text-transform: uppercase;">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="dataNascimentoDep" class="control-label">Nascimento</label>
                                <input id="dataNascimentoDep" name="pessoa.dataNascimento" class="form-control data" maxlength="10">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="cpfDep" class="control-label">CPF</label>
                                <input id="cpfDep" name="pessoa.cpf" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rgDep" class="control-label">RG</label>
                                <input id="rgDep" name="pessoa.rg" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rgOrgaoEmissorDep" class="control-label">Rg Orgão Emissor</label>
                                <input id="rgOrgaoEmissorDep" name="pessoa.rgOrgaoEmissor" class="form-control">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="rgUfDep" class="control-label">RG UF</label>
                                <input id="rgUfDep" name="pessoa.rgUf" class="form-control">
                            </div>
                            <div class="form-group col-lg-3">
                                <label for="dataEmissaoRgDep" class="control-label">Data Emissão RG</label>
                                <input id="dataEmissaoRgDep" name="pessoa.dataEmissaoRg" class="form-control data" maxlength="10">
                            </div>
                            <div class="form-group col-lg-2">
                                <label for="sexoDep" class="control-label">Sexo</label>
                                <select id="sexoDep" name="sexo.id" class="form-control form-control-select form-control-width">
                                    <option value=""></option>
                                    <c:forEach items="${sexos}" var="s">
                                        <option value="${s.get('id')}">${s.get('nome')}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <label for="mesmoEndereco">
                                            <input type="checkbox" id="mesmoEndereco"  name="mesmoEndereco" checked="" />
                                        Mesmo endereço</label>
                                    </div>
                                </div>
                            </div>
                            <div class="endereco form-group col-lg-2">
                                <label for="bairroDep" class="control-label">Bairro</label>
                                <select id="bairroDep" name="bairro.id" class="form-control form-control-select form-control-width">
                                    <option value=""></option>
                                    <option value="1">Exemplo 1</option>
                                    <option value="2">Exemplo 2</option>
                                    <option value="3">Exemplo 3</option>
                                    <option value="4">Exemplo 4</option>
                                    <option value="5">Exemplo 5</option>
                                </select>
                            </div>
                            <div class="endereco form-group col-lg-4">
                                <label for="ruaDep" class="control-label">Rua</label>
                                <input id="ruaDep" name="rua.nome" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-2">
                                <label for="numeroDep" class="control-label">Número</label>
                                <input id="numeroDep" name="endereco.numero" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-3">
                                <label for="pontoReferenciaDep" class="control-label">Ponto Referência</label>
                                <input id="pontoReferenciaDep" name="endereco.pontoReferencia" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-3">
                                <label for="condominioDep" class="control-label">Condomínio</label>
                                <input id="condominioDep" name="endereco.condominio" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-3">
                                <label for="edificioDep" class="control-label">Edifício</label>
                                <input id="edificioDep" name="endereco.edificio" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-1">
                                <label for="ufDep" class="control-label">UF</label>
                                <input id="ufDep" name="endereco.uf" class="form-control">
                            </div>
                            <div class="endereco form-group col-lg-2">
                                <label for="paisDep" class="control-label">País</label>
                                <input id="paisDep" name="endereco.pais" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="cartaoSusDep" class="control-label">Cartão SUS</label>
                                <input id="cartaoSusDep" name="pessoa.cartaoSus" class="form-control">
                            </div>
                            <div class="form-group col-lg-4">
                                <label for="nisDep" class="control-label">NIS</label>
                                <input id="nisDep" name="pessoa.nis" class="form-control">
                            </div>
                            <div class="grupoMod form-group col-lg-4">
                                <label for="celularDep" class="control-label">Celular</label>
                                <input id="celularDep" name="pessoa.celular" class="form-control">
                            </div>
                            <div class="grupoMod form-group col-lg-4">
                                <label for="tituloEleitoralDep" class="control-label">Título Eleitoral Número</label>
                                <input id="tituloEleitoralDep" name="pessoa.titulo" class="form-control">
                            </div>
                            <div class="grupoMod form-group col-lg-4">
                                <label for="zonaDep" class="control-label">Zona</label>
                                <input id="zonaDep" name="pessoa.zona" class="form-control">
                            </div>
                            <div class="grupoMod form-group col-lg-4">
                                <label for="secaoDep" class="control-label">Seção</label>
                                <input id="secaoDep" name="pessoa.secao" class="form-control">
                            </div>
                            <div class="col-12 col-sm-6 col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <label for="senhaDep" class="control-label">Digitar Senha</label>
                                        <input id="senhaDep" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-sm-6 col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <label for="senha2Dep" class="control-label">Repetir Senha</label>
                                        <input id="senha2Dep" name="pessoa.senha" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="info-box">
                                    <div class="info-box-content">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckPLiberadosDep"  name="postsLiberados" checked="" />
                                            <label for="ckPLiberadosDep" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Posts</label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckMLiberadasDep"  name="midiasLiberadas" checked="" />
                                            <label for="ckMLiberadasDep" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Mídias</label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="ckAcessaSistemaDep"  name="acessaSistema" checked="" />
                                            <label for="ckAcessaSistemaDep" class="custom-control-label" _msthash="5976295" _msttexthash="1318109">Liberar Acesso</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="info-box">
                                    <div class="form-group col-lg-2 mt-3">
                                        <button id="btnSalvarDep" type="submit" class="btn btn-success right">Cadastrar Dependente</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="form-group col-lg-12">
                        <form id="formTabelaNovosDependentes" method="post" role="form" data-toggle="validator" style="background-color: white;">
                            <table id="tbDependentes" class="table table-striped table-bordered"></table>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/dataTables/datatables.defaults.js"></script>
    <script type='text/javascript' charset='utf-8' src='js/formNovoPessoa.js'></script>
</body>
</html>
