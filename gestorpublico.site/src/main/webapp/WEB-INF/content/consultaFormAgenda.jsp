<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Consulta</title>
</head>
<body>
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h4 class="titulo">Agendar Consulta</h4>
                        <form id="frmConsultaAgendar" action="pessoaConsultaCadastrar" method="post" class="form" role="form">
                            <div class="row">
                                <div class="form-group col-lg-4 col-md-4">
                                    <label for="idPaciente" class="control-label">Paciente</label>
                                    <select id="idPaciente" name="paciente.id" class="focus form-control form-control-select">
                                        <option></option><c:forEach items="${pacientes}" var="o">
                                        <option value="${o.get('id')}">${o.get('nome')}</option>
                                    </c:forEach></select>
                                </div>
                                <div class="form-group col-lg-4 col-md-4">
                                    <label for="idEspecialidade" class="control-label">Especialidade</label>
                                    <select id="idEspecialidade" class="form-control form-control-select">
                                        <option></option><c:forEach items="${especialidades}" var="o">
                                        <option value="${o.id}">${o.nome}</option>
                                    </c:forEach></select>
                                </div>
                                <div class="form-group col-1">
                                    <button type="submit" class="btn-bottom btn btn-success">Agendar</button>
                                </div>
                            </div>
                            <div id="divProfissionais" class="row" style="display: none">
                                <div class="form-group col-lg-6 col-md-6">
                                    <div id="card1" class="card text-white bg-success mb-3">
                                        <h5 class="card-header" style="cursor: pointer;" onclick="javascript:getHorariosPorDataProfissional(1)">
                                            <span class="spProfissional">José dos Santos</span>
                                            <span class="spEndereco"><i class="nav-icon fas fa-clinic-medical m-1"></i>Clínica da Família, rua Santa Luzia, nº 05, bairro Divinéia</span>
                                        </h5>
                                        <div class="card-body bg-white" style="display: none">
                                            <h5 class="card-title">Escolha a hora</h5><br/>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">10h30</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">10h50</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">11h10</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">11h30</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">11h50</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-lg-6 col-md-6">
                                    <div id="card2" class="card text-white bg-success mb-3">
                                        <h5 class="card-header" style="cursor: pointer;" onclick="javascript:getHorariosPorDataProfissional(2)">
                                            <span class="spProfissional">Maria dos Santos</span>
                                            <span class="spEndereco"><i class="nav-icon fas fa-clinic-medical m-1"></i>Clínica da Família, Praça de Eventos, sn, bairro Sítio do Meio</span>
                                        </h5>
                                        <div class="card-body bg-white" style="display: none">
                                            <h5 class="card-title">Escolha a hora</h5><br/>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">9h10</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">9h40</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">10h10</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">10h40</a>
                                            <a onclick="void(0)" class="btn-horario btn btn-default">11h10</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-12 table-responsive">
                        <table id="tbConsultas" class="table table-striped table-bordered" width="100%"></table>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="rodape.jsp" />
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/datatables.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/dataTables.bootstrap4.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/consultaFormAgenda.js"></script>
</body>
</html>
