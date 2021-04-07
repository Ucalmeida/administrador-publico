package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ParentPackage("default")
public class PessoaBeneficioFormModalDespacharAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa_Beneficio pessoaBeneficio;

    @Action(value="beneficioFormDespachar",
        results={
            @Result(name="ok", location="pessoaBeneficioFormModalDespacho.jsp"),
            @Result(name="erro", location="paginaErroModal.jsp", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = (Session) request.getAttribute("sessao");
        try {
            pessoaBeneficio = new Pessoa_BeneficioDAO(session).getBeneficio(pessoaBeneficio);

            if (pessoaBeneficio == null) {
                response.setHeader("erro", "Solicitação de Benefício não encontrada");
                return "erro";
            }

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String action = ActionContext.getContext().getName();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            if (session.getTransaction().isActive()) {
                Session sessao = HibernateUtil.getSession();
                new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(action, erro));
                sessao.close();
            } else {
                new Log_Erro_ExecucaoDAO(session).salvar(new Log_Erro_Execucao(action, erro));
            }

            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public void setPessoaBeneficio(Pessoa_Beneficio pessoaBeneficio) {
        this.pessoaBeneficio = pessoaBeneficio;
    }
}