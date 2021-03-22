<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 19/03/2021
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <ul class="navbar-nav">
        <li class="nav-item"><a class="nav-link" data-widget="pushmenu" href="#" onclick="ajustarMenu()">
            <i class="fas fa-bars"></i></a>
        </li>
    </ul>
</nav>
<aside class="main-sidebar sidebar-dark-info elevation-4">
    <a href="principal" class="brand-link border-bottom-info">
        <img src="images/brasoes/brasao_riachuelo_se_100_100.png" alt="GESTOR" class="brand-image">
        <span class="brand-text font-weight-bold">Gestão<span class="ml-5">
            <i class="fas fa-home nav-icon-modulo"></i>
        </span><br>Pública</span>
    </a>
    <div class="sidebar os-host os-theme-light os-host-resize-disabled os-host-scrollbar-horizontal-hidden os-host-transition os-host-overflow os-host-overflow-y">
        <div class="os-resize-observer-host">
            <div class="os-resize-observer observed" style="left: 0px; right: auto;"></div>
        </div>
        <div class="os-size-auto-observer" style="height: calc(100% + 1px); float: left;">
            <div class="os-resize-observer observed"></div>
        </div>
        <div class="os-content-glue" style="margin: 0px -8px; width: 249px; height: 352px;"></div>
        <div class="os-padding">
            <div class="os-viewport os-viewport-native-scrollbars-invisible"
                 style="overflow-y: scroll; right: 0px; bottom: 0px;">
                <div class="os-content" style="padding: 0px 8px; height: 100%; width: 100%;">
                    <nav class="mt-2 pb-5">
                        <ul class="nav nav-pills nav-sidebar flex-column nav-child-indent" data-widget="treeview"
                            role="menu" data-accordion="false">
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-users"></i>
                                    <p class="">Pessoas<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="pessoaNovoFormCadastro"
                                           onclick="javascript:execute('pessoaNovoFormCadastro')">
                                            <i class="nav-icon mr-1 far fa-file-alt"></i>
                                            <p class="">Nova</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="beneficios"
                                           onclick="javascript:verBeneficios()">
                                            <i class="nav-icon mr-1 fa fa-handshake"></i>
                                            <p class="">Benefícios</p>
<%--                                            <span class="badge badge-primary right">055</span>--%>
                                        </a>
                                    </li>
<%--                                    <li class="nav-item">--%>
<%--                                        <a class="nav-link" data-target="ultimoBOL" onclick="javascript:verUltimoBol()">--%>
<%--                                            <i class="nav-icon mr-1 fas fa-passport"></i>--%>
<%--                                            <p class="">Último BOL<span class="badge badge-primary right">049</span></p>--%>
<%--                                        </a>--%>
<%--                                    </li>--%>
                                </ul>
                            </li>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="escalas"--%>
<%--                                                    onclick="javascript:execute('escalas')"><i--%>
<%--                                    class="nav-icon far fa-calendar-alt"></i>--%>
<%--                                <p class="">Escalas</p></a>--%>
<%--                            </li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button"--%>
<%--                                                    data-target="docenciaFormCadastrar"--%>
<%--                                                    onclick="javascript:execute('docenciaFormCadastrar')"><i--%>
<%--                                    class="nav-icon fas fa-user-graduate"></i>--%>
<%--                                <p class="text-sm d-inline-flex">Cadastro de Docência</p></a>--%>
<%--                            </li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="modulos"--%>
<%--                                                    onclick="javascript:execute('modulos')"><i--%>
<%--                                    class="nav-icon fas fa-server"></i>--%>
<%--                                <p class="">Módulos</p></a>--%>
<%--                            </li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="cursos"--%>
<%--                                                    onclick="javascript:execute('cursos')"><i--%>
<%--                                    class="nav-icon fab fa-leanpub"></i>--%>
<%--                                <p class="">Cursos</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="ficha"--%>
<%--                                                    onclick="javascript:execute('ficha')"><i--%>
<%--                                    class="nav-icon fas fa-address-card"></i>--%>
<%--                                <p class="">Minha ficha</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button"--%>
<%--                                                    data-target="policialAntiguidadeListar"--%>
<%--                                                    onclick="javascript:execute('policialAntiguidadeListar')"><i--%>
<%--                                    class="nav-icon fas fa-sort-numeric-up"></i>--%>
<%--                                <p class="">Listar Antiguidade</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="solicitacoes"--%>
<%--                                                    onclick="javascript:execute('solicitacoes')"><i--%>
<%--                                    class="nav-icon fas fa-retweet"></i>--%>
<%--                                <p class="">Solicitações</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="formDownloadMp"--%>
<%--                                                    onclick="javascript:execute('formDownloadMp')"><i--%>
<%--                                    class="nav-icon fas fa-book"></i>--%>
<%--                                <p class="">Biblioteca Virtual</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="requerimento"--%>
<%--                                                    onclick="javascript:execute('requerimento')"><i--%>
<%--                                    class="nav-icon fas fa-hand-rock"></i>--%>
<%--                                <p class="">Requerimento</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button"--%>
<%--                                                    data-target="pesquisaAvancada"--%>
<%--                                                    onclick="javascript:execute('pesquisaAvancada')"><i--%>
<%--                                    class="nav-icon fab fa-searchengin"></i>--%>
<%--                                <p class="">Pesquisa Avançada</p></a></li>--%>
<%--                            <li class="nav-item"><a class="nav-link" href="#" role="button" data-target="downloadForm"--%>
<%--                                                    onclick="javascript:execute('downloadForm')"><i--%>
<%--                                    class="nav-icon fas fa-file-download"></i>--%>
<%--                                <p class="">Download Úteis</p></a></li>--%>
<%--                            <li class="nav-item has-treeview"><a class="nav-link" href="#" onclick="javascript:void(0)"><i--%>
<%--                                    class="nav-icon fas fa-search"></i>--%>
<%--                                <p class="">Consultas<i class="right fas fa-angle-down"></i></p></a>--%>
<%--                                <ul class="nav nav-treeview">--%>
<%--                                    <li class="nav-item"><a class="nav-link" data-target="consultaVeiculo"--%>
<%--                                                            onclick="javascript:execute('consultaVeiculo')"><i--%>
<%--                                            class="nav-icon mr-1 fas fa-car"></i>--%>
<%--                                        <p class="text-sm d-inline-flex">Consulta Veicular</p></a></li>--%>
<%--                                    <li class="nav-item"><a class="nav-link" data-target="consultaIndividuo"--%>
<%--                                                            onclick="javascript:execute('consultaIndividuo')"><i--%>
<%--                                            class="nav-icon mr-1 far fa-user"></i>--%>
<%--                                        <p class="text-sm d-inline-flex">Consulta Indivíduo</p></a></li>--%>
<%--                                    <li class="nav-item"><a class="nav-link" data-target="incluirRestricaoVeicular"--%>
<%--                                                            onclick="javascript:execute('incluirRestricaoVeicular')"><i--%>
<%--                                            class="nav-icon mr-1 fas fa-car"></i>--%>
<%--                                        <p class="text-sm d-inline-flex">Incluir Restrição</p></a></li>--%>
<%--                                </ul>--%>
<%--                            </li>--%>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="os-scrollbar os-scrollbar-horizontal os-scrollbar-unusable os-scrollbar-auto-hidden">
            <div class="os-scrollbar-track">
                <div class="os-scrollbar-handle" style="width: 100%; transform: translate(0px, 0px);"></div>
            </div>
        </div>
        <div class="os-scrollbar os-scrollbar-vertical os-scrollbar-auto-hidden">
            <div class="os-scrollbar-track">
                <div class="os-scrollbar-handle" style="height: 57.1197%; transform: translate(0px, 30px);"></div>
            </div>
        </div>
        <div class="os-scrollbar-corner"></div>
    </div>
</aside>