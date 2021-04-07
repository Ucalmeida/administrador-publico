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
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@ParentPackage("default")
public class PessoaBeneficioDespacharAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa_Beneficio pessoaBeneficio;
    private boolean autorizacao;
    private String despacho;

    @Action(value="pessoaBeneficioDespachar",
        results={
            @Result(name="ok", type="httpheader", params={"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = (Session) request.getAttribute("sessao");
        try {
            Pessoa pessoaLogada = (Pessoa) ActionContext.getContext().getSession().get("pessoaLogada");
            Pessoa_BeneficioDAO pessoaBeneficioDAO = new Pessoa_BeneficioDAO(session);
            pessoaBeneficio = pessoaBeneficioDAO.getBeneficio(pessoaBeneficio);

            if (pessoaBeneficio == null) {
                response.setHeader("erro","Benefício não encontrado.");
                return "erro";
            }

            if (despacho == null || despacho.trim().isEmpty()) {
                despacho = autorizacao ? "Autorizado." : "Não autorizado";
            }

            pessoaBeneficio.setDataConcessao(LocalDate.now());
            pessoaBeneficio.setAutorizado(autorizacao);
            pessoaBeneficio.setDespacho(despacho);
            pessoaBeneficio.setResponsavel(pessoaLogada);

            pessoaBeneficioDAO.atualizar(pessoaBeneficio);

            response.addHeader("id", pessoaBeneficio.getId().toString());
            response.addHeader("msg", "Despacho realizado com sucesso!");

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

    public void setAutorizacao(boolean autorizacao) {
        this.autorizacao = autorizacao;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho == null || despacho.trim().isEmpty() ? null : despacho.trim();
    }
}