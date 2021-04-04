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
    <a href="painel" class="brand-link border-bottom-amareloriachuelo">
        <img src="../images/brasoes/brasao_riachuelo_se_100_100.png" alt="GESTOR" class="brand-image">
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
                                    <i class="nav-icon fas fa-clinic-medical"></i>
                                    <p class="">Saúde<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="consultaFormAgenda"
                                           onclick="javascript:execute('consulta')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Agendar consulta</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="consultaFormLista"
                                           onclick="javascript:execute('consultas')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Minhas consultas</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-cog"></i>
                                    <p class="">Serviço<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="pessoaFormSolicita"
                                           onclick="javascript:execute('servico')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Solicitar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="servicoFormLocaliza"
                                           onclick="javascript:execute('servicoLocalizar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Localizar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="servicoFormLista"
                                           onclick="javascript:execute('servicos')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Minhas solicitações</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fa fa-handshake"></i>
                                    <p class="">Benefícios<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="beneficioNovoFormCadastro"
                                           onclick="javascript:execute('beneficio')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Solicitar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="beneficioFormLista"
                                           onclick="javascript:execute('beneficios')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Meus benefícios</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-target="dependenteLista"
                                   onclick="javascript:execute('dependentes')">
                                    <i class="nav-icon fas fa-users"></i>
                                    <p class="">Dependentes</p>
                                </a>
                            </li>
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