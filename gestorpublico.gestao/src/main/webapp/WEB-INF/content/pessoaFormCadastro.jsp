<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Pessoa</title>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="menuLateral.jsp" />
        <div class="content-wrapper" style="min-height: 296px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h4 class="titulo">Cidadão - cadastrar</h4>
                            <form id="frmPessoaCadastrar" action="pessoaCadastrar" method="post" class="form" role="form">
                                <div class="row">
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="cpf" class="control-label">CPF</label>
                                        <input id="cpf" name="pessoa.cpf" autofocus class="cpf form-control" />
                                    </div>
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="nome" class="control-label">Nome</label>
                                        <input id="nome" name="pessoa.nome" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="dataNascimento" class="control-label">Data de Nascimento</label>
                                        <input type="date" id="dataNascimento" name="dataNascimento" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="celular" class="control-label">Celular</label>
                                        <input id="celular" name="pessoa.celular" class="telefone form-control" />
                                    </div>
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="idSexo" class="control-label">Sexo</label>
                                        <select id="idSexo" name="sexo.id" class="form-control form-control-select">
                                            <option></option></option><c:forEach items="${sexos}" var="o">
                                            <option value="${o.get('id')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-1 col-md-1">
                                        <label for="vivo" class="control-label">Vivo</label>
                                        <select id="vivo" name="pessoa.vivo" class="form-control form-control-select">
                                            <option></option>
                                            <option value="true">Sim</option>
                                            <option value="false">Não</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="dataFalecimento" class="control-label">Data de Felecimento</label>
                                        <input type="date" id="dataFalecimento" disabled name="dataFalecimento" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="cartaoSus" class="control-label">Cartão do SUS</label>
                                        <input id="cartaoSus" name="pessoa.cartaoSus" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="cartaoNis" class="control-label">Cartão do NIS</label>
                                        <input id="cartaoNis" name="pessoa.cartaoNis" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-2 col-md-2">
                                        <label for="tituloEleitoral" class="control-label">Título Eleitoral</label>
                                        <input id="tituloEleitoral" name="pessoa.tituloEleitoral" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-1 col-md-1">
                                        <label for="zonaEleitoral" class="control-label">Zona</label>
                                        <input id="zonaEleitoral" name="pessoa.zonaEleitoral" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="mae" class="control-label">Mãe</label>
                                        <input id="mae" name="nomeMae" class="form-control" placeholder="Digite o CPF ou nome da mãe" />
                                        <input hidden id="idMae" name="mae.id" />
                                    </div>
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="pai" class="control-label">Pai</label>
                                        <input id="pai" name="nomePai" class="form-control" placeholder="Digite o CPF ou nome do pai" />
                                        <input hidden id="idPai" name="pai.id" />
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="divisor control-label">ENDEREÇO</label>
                                </div>
                                <div class="row">
                                    <div class="form-group col-lg-1 col-md-1">
                                        <label for="idUF" class="control-label">UF</label>
                                        <select id="idUF" name="uf" class="form-control form-control-select">
                                            <option></option><c:forEach items="${ufs}" var="o">
                                            <option value="${o.get('id')}" ${municipio.uf.id eq o.get('id') ? 'selected' : ''}>${o.get('sigla')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="idMunicipio" class="control-label">Município</label>
                                        <select id="idMunicipio" name="municipio" class="form-control form-control-select">
                                            <option></option><c:forEach items="${municipios}" var="o">
                                            <option value="${o.get('id')}" ${municipio.id eq o.get('id') ? 'selected' : ''}>${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="idBairro" class="control-label">Bairro <a class="btn btn-primary btn-xs text-white" onclick="javascript:novoBairro()" title="Novo Bairro"><i class="fas fa-plus mr-1"></i>Novo</a></label>
                                        <select id="idBairro" name="bairro" class="form-control form-control-select">
                                            <option></option><c:forEach items="${bairros}" var="o">
                                            <option value="${o.get('id')}">${o.get('nome')}</option>
                                        </c:forEach></select>
                                    </div>
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="idRua" class="control-label">Rua <a class="btn btn-primary btn-xs text-white" onclick="javascript:novaRua()" title="Nova Rua"><i class="fas fa-plus mr-1"></i>Novo</a></label>
                                        <select id="idRua" name="rua.id" class="form-control form-control-select"></select>
                                    </div>
                                    <div class="form-group col-lg-1 col-md-1">
                                        <label for="numero" class="control-label">Nº</label>
                                        <input id="numero" name="pessoaEndereco.numero" maxlength="5" class="form-control" />
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="idCondominio" class="control-label">Condomínio <a class="btn btn-primary btn-xs text-white" onclick="javascript:novoCondominio()" title="Nova Condomínio"><i class="fas fa-plus mr-1"></i>Novo</a></label>
                                        <select id="idCondominio" name="condominio.id" class="form-control form-control-select"></select>
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="idEdificio" class="control-label">Edifício <a class="btn btn-primary btn-xs text-white" onclick="javascript:novaEdificio()" title="Novo Edifício"><i class="fas fa-plus mr-1"></i>Novo</a></label>
                                        <select id="idEdificio" name="edificio.id" class="form-control form-control-select"></select>
                                    </div>
                                    <div class="form-group col-lg-3 col-md-3">
                                        <label for="idPontoReferencia" class="control-label">Ponto de referência <a class="btn btn-primary btn-xs text-white" onclick="javascript:novoPontoReferencia()" title="Novo Ponto de Referência"><i class="fas fa-plus mr-1"></i>Novo</a></label>
                                        <select id="idPontoReferencia" name="pontoReferencia.id" class="form-control form-control-select"></select>
                                    </div>
                                    <div class="form-group col-lg-4 col-md-4">
                                        <label for="complemento" class="control-label">Complemento</label>
                                        <input id="complemento" name="pessoaEndereco.complemento" maxlength="50" class="form-control" />
                                    </div>
                                    <div class="form-group col-1">
                                        <button type="submit" class="btn-bottom btn btn-success">Salvar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="rodape.jsp" />
    </div>
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/pessoaFormCadastro.js"></script>
</body>
</html>
