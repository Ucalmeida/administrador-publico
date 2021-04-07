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
    <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown user-menu">
            <a class="nav-link d-flex cursor-pointer mt-n1 p-0" data-toggle="dropdown" aria-expanded="false">
            <span class="d-lg-inline text-gray-600 text-center">${pessoaLogada.nome} <i class="fas fa-user-circle"></i></span>
            </a>
            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-right animated--grow-in mt-2" style="left: inherit; right: 0px;">
                <li class="user-body border-bottom-amareloriachuelo">
                    <div class="row">
                        <div class="col-12 text-center">
                            <button class="btn btn-default btn-sm" onclick="alterarSenha()"> Alterar Senha</button>
                        </div>
                    </div>
                </li>
                <li class="user-footer">
                    <button class="btn btn-danger float-right" onclick="sair()"><i
                            class="fas fa-power-off fa-sm fa-fw mr-2"></i>Sair
                    </button>
                </li>
            </ul>
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
        <div class="os-content-glue" style="margin: 0px -8px; width: 249px; height: 590px;"></div>
        <div class="os-padding">
            <div class="os-viewport os-viewport-native-scrollbars-invisible"
                 style="overflow-y: scroll; right: 0px; bottom: 0px;">
                <div class="os-content" style="padding: 0px 8px; height: 100%; width: 100%;">
                    <nav class="mt-2 pb-5">
                        <ul class="nav nav-pills nav-sidebar flex-column nav-child-indent" data-widget="treeview"
                            role="menu" data-accordion="false">
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-user-alt"></i>
                                    <p class="">Cidadão<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="pessoaFormCadastro"
                                           onclick="javascript:execute('cidadao')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Cadastrar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="pessoaFormLocaliza"
                                           onclick="javascript:execute('pessoaLocalizar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Localizar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="pessoaFormLista"
                                           onclick="javascript:execute('cidadaos')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Listar</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-briefcase-medical"></i>
                                    <p class="">Saúde<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-clinic-medical"></i>
                                            <p class="">Unidade de Saúde<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-user-md"></i>
                                            <p class="">Profissional<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-procedures"></i>
                                            <p class="">Paciente<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-ambulance"></i>
                                            <p class="">Unidade Móvel<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-graduation-cap"></i>
                                    <p class="">Educação<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-school"></i>
                                            <p class="">Unidade de Ensino<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-chalkboard-teacher"></i>
                                            <p class="">Professor<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item has-treeview">
                                        <a class="nav-link" href="#" onclick="javascript:void(0)">
                                            <i class="nav-icon fas fa-user-graduate"></i>
                                            <p class="">Aluno<i class="right fas fa-angle-down"></i></p>
                                        </a>
                                        <ul class="nav nav-treeview">
                                            <li class="nav-item">
                                                <a class="nav-link" data-target="servicoFormLocaliza"
                                                   onclick="javascript:execute('servicoLocalizar')">
                                                    <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                                    <p class="">Localizar</p>
                                                </a>
                                            </li>
                                        </ul>
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
                                        <a class="nav-link" data-target="servicoFormCadatro"
                                           onclick="javascript:execute('servico')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Novo</p>
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
                                           onclick="javascript:execute('servicosListar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Listar</p>
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
                                        <a class="nav-link" data-target="beneficioFormCadastro"
                                           onclick="javascript:execute('beneficio')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Novo</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="beneficioFormLocaliza"
                                           onclick="javascript:execute('beneficioLocalizar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Localizar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="beneficioFormLista"
                                           onclick="javascript:execute('beneficioListar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Listar</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-map-marker-alt"></i>
                                    <p class="">Rua<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="ruaFormCadastro"
                                           onclick="javascript:execute('rua')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Cadastrar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="ruaFormLocaliza"
                                           onclick="javascript:execute('ruaLocalizar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Localizar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="ruaFormLista"
                                           onclick="javascript:execute('ruaListar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Listar</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a class="nav-link" href="#" onclick="javascript:void(0)">
                                    <i class="nav-icon fas fa-cogs"></i>
                                    <p class="">Secretaria|Setor<i class="right fas fa-angle-down"></i></p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="setorPessoaFormAloca"
                                           onclick="javascript:execute('alocar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Alocar Funcionário</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="setorFormCadastro"
                                           onclick="javascript:execute('setor')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Cadastrar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="setorFormLocaliza"
                                           onclick="javascript:execute('setorLocalizar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Localizar</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-target="setorFormLista"
                                           onclick="javascript:execute('setorListar')">
                                            <i class="nav-icon mr-1 fas fa-caret-right"></i>
                                            <p class="">Listar</p>
                                        </a>
                                    </li>
                                </ul>
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