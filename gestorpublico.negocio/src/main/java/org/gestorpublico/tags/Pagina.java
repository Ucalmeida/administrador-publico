package org.gestorpublico.tags;

import org.gestorpublico.entidade.Modulo;
import org.gestorpublico.entidade.Modulo_Acao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Poder_Setor_Funcao;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;
import java.util.Map;

public class Pagina extends BodyTagSupport {

    //Variáveis passadas como parâmetro pelo usuario da TAG
    private String titulo;
    private String subTitulo;
    private String link;
    private StringBuilder css;
    private StringBuilder js;
    private Boolean menu;
    private boolean tabela;
    private boolean editorTexto;
    private boolean card;
    private boolean form;
    private boolean react;
    private boolean dashboard;

    //Variáveis da classe
//    private Modulo modulo;
    private String infoUsuario;
    private String ultimoBGO;
    private String ultimoBOL;
    private Session session;
    private Pessoa pessoaLogada;
    private StringBuilder cssPlugins;
    private StringBuilder jsPlugins;
    private List<Modulo_Acao> acoes;
    private Poder_Setor_Funcao ltb;
    private List<Map<String,Object>> ltbs;
    private List<Map<String, Object>> notificacoes;

    public Pagina() {}

    public int doAfterBody() {

        setup();

        JspWriter out = getBodyContent().getEnclosingWriter();

        gerarPlugins();

        //Construção da página
        try {
            out.println(
                    "<!DOCTYPE html>" +
                            "<html lang='pt-BR'>" +
                            getHead() +
                            "<body onload=\"$(\'.focus\').focus();\" class=\'hold-transition "+getMenu()+"\'>" +
                            "<div class='wrapper'>" +
                            getNavSuperior() +
                            getMenuLateral() +
                            getConteudo() +
//                            getRodape() +
                            "</div>" +
                            getScripts() +
                            "</body>" +
                            "</html>"
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }


    private String getHead() {
        String titulo = "";
        if (this.titulo != null) {
            if(this.titulo.contains("$") || this.titulo.contains("<")) titulo = ""; else titulo = this.titulo; //Só exibe o título da página se o titulo for uma String simples.
        }
        return
                "<head>" +
                        "<meta charset='utf-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                        "<meta http-equiv='Cache-Control' content='no-cache, no-store, must-revalidate' />"+
                        "<meta http-equiv='Pragma' content='no-cache' />" +
                        "<meta http-equiv='Expires' content='0' />" +
                        //Cache
//					"<meta http-equiv=\"cache-control\" content=\"max-age=0\" />" +
//					"<meta http-equiv=\"cache-control\" content=\"no-cache\" />" +
//					"<meta http-equiv=\"expires\" content=\"0\" />" +
//					"<meta http-equiv=\"expires\" content=\"Tue, 01 Jan 1980 1:00:00 GMT\" />" +
//					"<meta http-equiv=\"pragma\" content=\"no-cache\" />" +
                        //Fim cache
                        "<title>" + titulo + "</title>" +
                        "<link rel='icon' href='../images/brasoes/brasao_riachuelo_se_100_100.png' />" +
                        cssPlugins.toString() +
                        "<link rel='stylesheet' href='../plugins/fontawesome/css/all.min.css'>" +
                        "<link rel='stylesheet' href='../plugins/jquery/jquery-ui/jquery-ui.css'/>"+
                        "<link rel='stylesheet' href='../css/adminlte.min.css'>" +
                        "<link rel='stylesheet' href='../plugins/overlayScrollbars/OverlayScrollbars.min.css'>" +
                        "<link rel='stylesheet' href='../css/adminlte.portal.css'>" +
                        getCssImportado() +
                        "<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=swap' rel='stylesheet'>" +
                        "</head>"
                ;
    }

    private String getMenu() {
        if (this.menu == null) return "sidebar-mini layout-fixed"; else if (!this.menu)return "layout-top-nav"; else return "sidebar-mini layout-fixed sidebar-collapse";
    }

    private String getMenuLateral() {
        String menuLateral;

        if (this.menu != null && !this.menu) {
            menuLateral = "";
        } else if(this.link.equalsIgnoreCase("painel")) {
            menuLateral = "<aside class='main-sidebar sidebar-dark-primary elevation-4'>" +
                "<a href='"+this.link+"' class='brand-link border-bottom-amareloriachuelo'>" +
                "<img src='../images/brasoes/brasao_riachuelo_se_100_100.png' alt='GESTOR' class='brand-image' >" +
                "<span class='brand-text font-weight-bold'>GESTÃO" +
                "<span class='ml-5'><i class='fas fa-home nav-icon-modulo'></i></span>" +
                "<br>Pública</span>" +
                "</a>" +
                "<div class='sidebar'>" +
                "<nav class='mt-2 pb-5'>" +
                "<ul class='nav nav-pills nav-sidebar flex-column nav-child-indent' data-widget='treeview' role='menu' data-accordion='false'>" +
                "<li class='nav-item has-treeview'>" +
                "<a class='nav-link' href = '#' onclick = 'javascript:void(0)' >" +
                "<i class='nav-icon fas fa-clinic-medical' ></i >" +
                "<p class=''>Saúde<i class='right fa fa-angle-down'></i ></p >" +
                "</a >" +
                "<ul class='nav nav-treeview' >" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'consultaFormAgenda' onclick = \"javascript:execute('consulta')\">" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class='' > Agendar consulta</p >" +
                "</a >" +
                "</li >" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'consultaFormLista' onclick = \"javascript:execute('consultas')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class='' > Minhas consultas</p >" +
                "</a >" +
                "</li >" +
                "</ul >" +
                "</li >" +
                "<li class='nav-item has-treeview' >" +
                "<a class='nav-link' href = '#' onclick = 'javascript:void(0)' >" +
                "<i class='nav-icon fas fa-cog' ></i >" +
                "<p class=''>Serviço<i class='right fa fa-angle-down'></i ></p >"+
                "</a >" +
                "<ul class='nav nav-treeview'>" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'pessoaFormSolicita' onclick = \"javascript:execute('servico')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right'></i >" +
                "<p class=''>Solicitar</p >" +
                "</a >" +
                "</li >" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'servicoFormLocaliza' onclick = \"javascript:execute('servicoLocalizar')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class=''>Localizar</p >" +
                "</a >" +
                "</li >" +
                "<li class='nav-item'>" +
                "<a class='nav-link' data - target = 'servicoFormLista' onclick = \"javascript:execute('servicos')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class='' > Minhas solicitações</p >" +
                "</a >" +
                "</li >" +
                "</ul >" +
                "</li >" +
                "<li class='nav-item has-treeview' >" +
                "<a class='nav-link' href = '#' 'onclick = javascript:void(0)' >" +
                "<i class='nav-icon fa fa-handshake' ></i >" +
                "<p class=''>Benefícios<i class='right fa fa-angle-down'></i ></p >" +
                "</a >" +
                "<ul class='nav nav-treeview' >" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'beneficioNovoFormCadastro' onclick = \"javascript:execute('beneficio')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class='' > Solicitar </p >" +
                "</a >" +
                "</li >"+
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'beneficioFormLista' onclick = \"javascript:execute('beneficios')\" >" +
                "<i class='nav-icon mr-1 fas fa-caret-right' ></i >" +
                "<p class='' > Meus benefícios</p >" +
                "</a >" +
                "</li >" +
                "</ul >" +
                "</li >" +
                "<li class='nav-item' >" +
                "<a class='nav-link' data - target = 'dependenteLista' onclick = \"javascript:execute('dependentes')\" >" +
                "<i class='nav-icon fas fa-users' ></i >" +
                "<p class='' > Dependentes </p >" +
                "</a >" +
                "</li >" +
                "</ul>" +
                "</nav>" +
                "</div>" +
                "</aside>";
            }
            else {
                menuLateral = "<aside class='main-sidebar sidebar-dark-primary elevation-4'>" +
                    "<a href='"+link+"' class='brand-link border-bottom-amareloriachuelo'>" +
                    "<img src='../images/brasoes/brasao_riachuelo_se_100_100.png' alt='GESTOR' class='brand-image' >" +
                    "<span class='brand-text font-weight-bold'>GESTÃO" +
                    "<span class='ml-5'><i class=\'fas fa-home nav-icon-modulo\'></i></span>" +
                    "<br>Pública</span>" +
                    "</a>" +
                    "<div class='sidebar'>" +
                    "<nav class='mt-2 pb-5'>" +
                    "<ul class='nav nav-pills nav-sidebar flex-column nav-child-indent' data-widget='treeview' role='menu' data-accordion='false'>" +
                    menu +
                    "</ul>" +
                    "</nav>" +
                    "</div>" +
                    "</aside>";
            }

        return menuLateral;
    }

//    private String getModuloIcone() {
//        String css = " nav-icon-modulo";
//        if (modulo.getIcone() == null) {
//            return "<i class='fas fa-home"+css+"'></i>";
//        } else if (modulo.isIconeImg()) {
//            return "<img class='"+css+"' src='"+modulo.getIcone()+"' />";
//        } return "<i class='"+modulo.getIcone() + css+"'></i> ";
//    }

    private String getNavSuperior() {
        String funcoes = "";
        String fecharModulo = "";
        String notificacoes = "";
        String noficicacoesIcone = "";
        StringBuilder menuLtb = new StringBuilder();
        String iconeMenu;
        String contadorSessao;

        contadorSessao =
                "<li class='nav-item dropdown mr-2 text-center d-none d-sm-inline-block text-gray-600 cursor-pointer'>" +
                        "<a class='nav-link d-flex cursor-pointer mt-n1 p-0'>" +
                        "<span class='d-lg-inline text-gray-600 text-center'>" +
                        "<span class='text-sm align-bottom' id='timerSessao'></span>" +
                        "</span>"+
                        "</a>" +
                        "</li>";

            funcoes = "<li class='user-body border-bottom-amareloriachuelo'>" +
                        "<div class='row'>" +
                        "<div class='col-4 text-center'> " +
                        "<img src='../images/brasoes/brasao_riachuelo_se_100_100.png' alt='GESTOR' class='brand-image' >" +
                        "</div>" +
                        "</div" +
                        "</li> ";
//                    "<li class='user-body border-bottom-amareloriachuelo'>" +
//                            "<div class='row'>" +
//                            "<div class='col-4 text-center'> " +
//                            "<button class='btn btn-info btn-sm' onclick='alterarSenha()'> " +
//                            "Alterar Senha " +
//                            "</button> " +
//                            "</div> " +
//                            "</li> ";
//        } else {
            fecharModulo = "<button class='btn btn-warning' onclick='alterarSenha()'>" +
                    "<i class='fa fa-pencil'></i>" +
                    "Alterar Senha" +
                    "</button>";
//        }
        if (menu == null || menu) {
            iconeMenu = "<ul class='navbar-nav'><li class='nav-item'>" +
                    "<a class='nav-link' data-widget='pushmenu' href='#' onclick='ajustarMenu()'><i class='fas fa-bars'></i></a>" +
                    "</li></ul>";
        } else iconeMenu = "";
        return
                "<nav class='main-header navbar navbar-expand navbar-white navbar-light'>" +
                        iconeMenu +
                        //MenuSuperior Direito
                        "<ul class='navbar-nav ml-auto'>" +
                        menuLtb +
                        notificacoes +
                        contadorSessao +
                        "<li class='nav-item dropdown user-menu'>" +
                        "<a class='nav-link d-flex cursor-pointer mt-n1 p-0' data-toggle='dropdown' >" +
                        this.infoUsuario +
                        "</a>" +
                        "<ul class='dropdown-menu dropdown-menu-lg dropdown-menu-right animated--grow-in mt-2'>" +
                        "<p>" +
//                        pessoaLogada.getNome()+ "<br>" +
//                        "<small>Na corporação desde: "+ CassUtil.getDataFormatada(pessoaLogada.getDataAdmissao()) +"</small>" +
                        "</p>" +
                        "</li>" +
                        //-- Menu Body --
                        funcoes+
                        //-- Menu Footer--
                        "<li class='user-footer'>" +
                        fecharModulo +
                        "<button class='btn btn-danger float-right' onclick='sair()'>" +
                        "<i class='fas fa-power-off fa-sm fa-fw mr-2'></i>" +
                        "Sair" +
                        "</button>" +
                        "</li>"+
                        "</ul>" +
                        "</li>" +
                        "</ul>" +
                        "</nav>";
    }

    private String geraNotificacoes(List<Map<String, Object>> maps) {
        StringBuilder stringNotificacao = new StringBuilder();
        if (maps.size() > 0) {
            for (Map<String, Object> notificacao : maps) {
                stringNotificacao.append("<a href='#' class='dropdown-item'>" + "<i class='fas fa-envelope mr-2'></i>")
                        .append(notificacao.get("nome"))
                        .append("<span class='float-right text-muted text-sm'>3 mins</span>")
                        .append("</a>").append("<div class='dropdown-divider'></div>");
            }
        } else {
            stringNotificacao.append(
                    "<a href='#' class='dropdown-item text-center'>" +
                            "<span class='font-italic text-sm'>Sem novas notificações</span>" +
                            "</a>" +
                            "<div class='dropdown-divider'></div>"
            );
        }
        return stringNotificacao.toString();
    }

    private String getConteudo() {
        String cardAbre, cardFecha;
        String botaoFechar = this.menu != null && this.menu == false ? "<div class='col'><button onclick='window.close()' class='btn btn-xs btn-danger float-right'><i class='fas fa-times-circle mr-2'></i>Fechar</button></div>" : "";
        if (this.card) {
            cardAbre = "<div class='card'><div class='card-body'>";
            cardFecha = "</div></div>";
        } else 	cardAbre = cardFecha = "";
        return
                "<div class='content-wrapper'>" +
                        "<section class='content-header'>" +
                        "<div class='container-fluid'>" +
                        "<div class='row mb-2'>" +
                        "<div class='col-sm-12'>" +
                        "<div class='row'>"+
                        "<div class='col-auto'><h1 class='m-0 text-verdepetroleo'>" +
                        //Titulo da página
                        getTitulo() +
                        "</h1></div>" + botaoFechar +
                        "</div>"+
                        getSubTitulo()+
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</section>" +
                        "<section class='content'>" +
                        "<div class='container-fluid'>" +
                        cardAbre +
                        getBodyContent().getString() +
                        cardFecha +
                        "</div>" +
                        "</section>" +
                        "</div>"
                ;
    }

    private String getScripts() {
        long time = System.currentTimeMillis() + (pageContext.getSession().getMaxInactiveInterval()*1000);
        return
                //Bootstrap
                "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-migrate.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/bootstrap/bootstrap.bundle.min.js'></script>"+
                        "<script type='text/javascript' charset='utf-8' src='../plugins/bootbox/bootbox.all.min.js'></script>" +
                        //JQUERY
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery.mask.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-validate/jquery.validate.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-validate/additional-methods.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-validate/localization/messages_pt_BR.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-validate/jquery.validate.defaults.js'></script>" +
//			"<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-validate/jquery-validate.bootstrap-tooltip.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery.easing.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery-ui/jquery-ui.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/jquery/jquery.blockUI.js'></script>" +
                        //CORE
                        "<script type='text/javascript' charset='utf-8' src='../js/core/adminlte.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../plugins/overlayScrollbars/jquery.overlayScrollbars.min.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../js/core/cassUtil.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../js/core/menu.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='../js/core/scripts.js'></script>" +
                        "<script type='text/javascript' charset='utf-8' src='js/funcoes.js'></script>" +
                        //Importado e Plugins
                        jsPlugins.toString() + getJsImportado() +
                        //Funções a executar ao carregar a página
                        "<script>" +
                        "$(document).ready(function () {" +
                        //Contador da sessão
                        "contadorSessao(" + time + ")" +
                        "});" +
                        "</script>" +
                        getAnalytics();
    }

    private String getAnalytics() {
        return
                "<script async src='https://www.googletagmanager.com/gtag/js?id=UA-169908160-1'></script>" +
                        "<script>" +
                        "  window.dataLayer = window.dataLayer || [];" +
                        "  function gtag(){dataLayer.push(arguments);}" +
                        "  gtag('js', new Date());" +
                        "  gtag('config', 'UA-169908160-1');" +
                        "</script>";
    }

    private void gerarPlugins(){
        cssPlugins = new StringBuilder();
        jsPlugins = new StringBuilder();
        if (tabela) { //DataTable Tabelas
            cssPlugins.append("<link rel='stylesheet' href='../plugins/dataTables/datatables-bs4/css/dataTables.bootstrap4.css'/>");
            cssPlugins.append("<link rel='stylesheet' href='../plugins/dataTables/datatables-buttons/css/buttons.bootstrap4.min.css'/>");

            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/jquery.dataTables.min.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-bs4/js/dataTables.bootstrap4.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-buttons/js/dataTables.buttons.min.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-buttons/js/buttons.bootstrap4.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-buttons/js/buttons.print.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-buttons/js/buttons.colVis.min.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables-buttons/js/buttons.html5.min.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datatables.defaults.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/pdfmake/pdfmake.min.js'></script>"); //Gerar PDF
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/pdfmake/vfs_fonts.js'></script>"); //Gerar PDF
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/jszip/jszip.min.js'></script>"); //Gerar Excel
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/natural.js'></script>"); // Ordenação
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/moment.min.js'></script>"); // Ordenação por datas
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/dataTables/datetime-moment.js'></script>"); // Ordenação por datas
        }
        if (editorTexto) { // TinyMCE Editor de Texto
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/tinymce/tinymce.min.js'></script>");
        }
        if (form) { //Complementos para Formulários
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/select2/select2.full.min.js'></script>");
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/select2/i18n/pt-BR.js'></script>");
            cssPlugins.append("<link rel='stylesheet' href='../plugins/icheck-bootstrap/icheck-bootstrap.min.css'/>");
            cssPlugins.append("<link rel='stylesheet' href='../plugins/select2/select2.min.css'/>");
        }
        if (react) { // Componentes para página com REACT
            jsPlugins.append("<script src='https://unpkg.com/babel-standalone@6/babel.min.js'></script>");
            jsPlugins.append("<script src='https://unpkg.com/react@16/umd/react.production.min.js' crossorigin></script>");
            jsPlugins.append("<script src='https://unpkg.com/react-dom@16/umd/react-dom.production.min.js' crossorigin></script>");
        }
        if (dashboard) {
            jsPlugins.append("<script type='text/javascript' charset='utf-8' src='../plugins/chart.js/Chart.min.js'></script>");
        }

    }

    private String getCssImportado() {
        if (css != null) {
            //		if (this.hasTabela) {
            //			this.cssImportado += "<link rel='stylesheet' href='../plugins/dataTables/datatables.min.css'/>";
            //		}
            String[] cssLista = this.css.toString().split(",");
            css = new StringBuilder();
            for (String cssTemp : cssLista) {
                if (cssTemp.contains(".css"))
                    css.append("<link rel='stylesheet' href='../").append("css/").append(cssTemp).append("'/>");
                else
                    css.append("<link rel='stylesheet' href='../").append("css/").append(cssTemp).append(".css'/>");
            } return css.toString();
        } return "";
    } // .append(getModuloDir())

    private String getJsImportado() {
        if (js != null) {
            String[] jsLista = js.toString().split(",");
            js = new StringBuilder();
            for (String jsTemp : jsLista) {
                String tipoJs;
                if (jsTemp.contains("componentes")) tipoJs = "babel"; else tipoJs = "javascript";

                if (jsTemp.contains(".js"))
                    js.append("<script type='text/javascript' charset='utf-8' src='../").append("js/").append(jsTemp).append("'></script>");
                else
                    js.append("<script type='text/").append(tipoJs).append("' charset='utf-8' src='../").append("js/").append(jsTemp).append(".js'></script>");
            }
            return js.toString();
        } else return "";
    } // .append(getModuloDir())

    private void setup() {
        String modulo;
        session = (Session) this.pageContext.getRequest().getAttribute("sessao");
	//Laço para páginas de erro
        boolean fecharSessao = false;
        if (!session.isOpen()) {
            fecharSessao = true;
            session = HibernateUtil.getSession();
        }
        try {
            pessoaLogada = (Pessoa) this.pageContext.getSession().getAttribute("pessoaLogada");
            ltb = (Poder_Setor_Funcao) this.pageContext.getSession().getAttribute("ltb");
            ltbs = (List<Map<String, Object>>) this.pageContext.getSession().getAttribute("ltbs");
//            try {
//                this.modulo = (Modulo) this.pageContext.getSession().getAttribute("modulo");
//            } catch (Exception e) {
//                ModuloDAO daoModulo = new ModuloDAO(session);
//                this.modulo = new Modulo();
//                this.modulo = daoModulo.getModuloPorNome((String) this.pageContext.getSession().getAttribute("modulo"));
//                e.printStackTrace();
//            }
//            if (this.modulo.getNome().equalsIgnoreCase("/")) {
//                if (this.pessoaLogada.isMilitar()) {
//                    this.acoes = new ArrayList<>(new LinkedHashSet<>(pessoaLogada.getAcoes()));
//                } else {
//                    this.acoes = getAcoesPorModuloCivil(this.modulo);
//                }
//            } else {
//                if (pessoaLogada.isMilitar()) {
//                    this.acoes = new Modulo_AcaoDAO(session).listarModuloAcaoPorModuloPessoaLocalTrabalho(this.modulo.getNome(), ltb);
//                } else {
//                    this.acoes = getAcoesPorModuloCivil(this.modulo);
//                }
//            }
//            Pessoa_NotificacaoDAO pessoa_notificacaoDAO = new Pessoa_NotificacaoDAO(session); //Notificacoes
//            notificacoes = pessoa_notificacaoDAO.listeNotificacaoPorPessoa(pessoaLogada);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fecharSessao) session.close();
        }


//		//Verifica se tem tabela na página para inserir automaticamente na JSP.
//		this.hasTabela = (getBodyContent().getString().indexOf("<table") > 0) ? true : false;

        //Barra de dados do usuário no menu superior
//        if (pessoaLogada.isMilitar()) {
//            infoUsuario = "<span class='d-lg-inline text-gray-600 text-center'>"+
//                    getPatente().getSigla()+
//                    "<span class='text-bold text-verdepetroleo'> "+((Policial)pessoaLogada).getNomeGuerra()+"</span><br>"+
//                    ((Policial)pessoaLogada).getMatriculaFormatada()+
//                    "</span>";
//        }else {
            infoUsuario = "<p class='mt-2'>"+pessoaLogada.getNome()+"</p>";
//        }
//    }

//    private List<Modulo_Acao> getAcoesPorModuloCivil(Modulo modulo) {
//        List<Modulo_Acao> acoes = new PessoaDAO(session).localizar(pessoaLogada.getId()).getAcoes();
//        ArrayList<Modulo_Acao> acoesPolicial = new ArrayList<>();
//        for(Modulo_Acao m : acoes) {
//            if (m.getModulo().equals(modulo)) {
//                acoesPolicial.add(m);
//            }
//        }
//        return acoesPolicial;
    }

//    public String getModuloDir() {
//        if (this.modulo.getNome().equalsIgnoreCase("")) {
//            return "";
//        } else {
//            return CassUtil.substituirCaracteresEspeciais("gestao/");
//        }
//    }

    // ****************************** GETs e SETs ******************************

    public String getTitulo() {
        if (titulo == null) {
            return "";
        } else {
            return "<span id='tituloIcone'></span>" + titulo;
        }
    }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSubTitulo(){
        if (this.subTitulo == null)	return "";
        else
            return "<p class='mb-2'>"+this.subTitulo+"</p>";
    }
    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getLink(){
        if (this.link == null)	return "";
        else
            return "<p class='mb-2'>"+this.link+"</p>";
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCss(String css) {
        this.css = new StringBuilder(css);
    }

    public void setJs(String js) {
        this.js = new StringBuilder(js);
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public void setTabela(boolean tabela) {
        this.tabela = tabela;
    }

    public void setEditorTexto(boolean editorTexto) {
        this.editorTexto = editorTexto;
    }

    public void setForm(boolean form) {
        this.form = form;
    }

    public void setReact(boolean react) {
        this.react = react;
    }

    public void setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
    }
}