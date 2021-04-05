package org.gestorpublico.util;

import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

public class PadraoAction extends ActionSupport {
    
    private static final long serialVersionUID = 1L;
    protected Logger logger = Logger.getLogger(getClass().getName());
    protected HttpServletRequest request = ServletActionContext.getRequest();
    protected HttpServletResponse response = ServletActionContext.getResponse();
    protected final String SUCESSO = "ok";
    protected final String ERRO = "erro";

    public PadraoAction() {
        super();
    }

    /**
     * Verifica se a requisição foi do tipo POST
     * @return true caso seja POST
     */
    protected boolean isPost() {
        return ServletActionContext.getRequest().getMethod().toUpperCase().equals("POST");
    }

    protected boolean isGet() {
        return ServletActionContext.getRequest().getMethod().toUpperCase().equals("GET");
    }

    protected Session getSession() {
        return (Session) ServletActionContext.getRequest().getAttribute("sessao");
    }

    protected void insereNaSessao(String chave, Object valor) {
        ActionContext.getContext().getSession().put(chave, valor);
    }

    protected void removeDaSessao(String chave) {
        ActionContext.getContext().getSession().remove(chave);
    }

    protected Object getDadosSessao(String chave) {
        return ActionContext.getContext().getSession().get(chave);
    }

    protected void addMsg(String mensagem) {
        ServletActionContext.getResponse().addHeader("msg", mensagem);
    }

    protected void addId(String mensagem) {
        ServletActionContext.getResponse().addHeader("id", mensagem);
    }

    protected void addErro(String erro) {
        ServletActionContext.getResponse().addHeader("erro", erro);
    }

    protected Pessoa getPessoaLogada() {
        return (Pessoa) ActionContext.getContext().getSession().get("pessoaLogada");
    }

    protected String getActionName() {
        return ActionContext.getContext().getName();
    }

    protected List<String> getAcoesPessoaLogada()   {return (List<String>) ActionContext.getContext().getSession().get("acoes");}

    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

//    protected Boolean permiteImpressaoPorSetor() {
//        ModuloDAO moduloDao = new ModuloDAO(getSession());
//        Modulo_AcaoDAO daoModuloAcao = new Modulo_AcaoDAO(getSession());
//        Modulo_Acao permiteMarcaDagua = daoModuloAcao.getModuloAcaoPorLinkModulo("permiteImpressaoPorSetor", moduloDao.getModuloPorNome("Portal"));
//        Pessoa pessoa = new PessoaDAO(getSession()).localizar(getPessoaLogada().getId());
//        return permiteMarcaDagua != null && pessoa.getAcoes().contains(permiteMarcaDagua);
//    }
}
