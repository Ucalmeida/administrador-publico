package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@ParentPackage("default")
public class PessoaBeneficioConcederAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa beneficiado;
    private Beneficio beneficio;
    private String observacao;

    @Action(value="pessoaBeneficioConceder",
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

            if (beneficiado == null || beneficiado.getId() == null) {
                response.setHeader("erro","Pessoa não encontrada.");
                return "erro";
            }

            if (beneficio == null || beneficio.getId() == null) {
                response.setHeader("erro","Benefício não encontrado.");
                return "erro";
            }

            Pessoa_Beneficio pessoaBeneficio = new Pessoa_Beneficio();
            pessoaBeneficio.setSolicitante(pessoaLogada);
            pessoaBeneficio.setResponsavel(pessoaLogada);
            pessoaBeneficio.setBeneficiado(beneficiado);
            pessoaBeneficio.setBeneficio(beneficio);
            pessoaBeneficio.setDataConcessao(LocalDate.now());
            pessoaBeneficio.setObservacao(observacao);
            pessoaBeneficio.setAutorizado(true);
            pessoaBeneficio.setDespacho("Autorizado");

            pessoaBeneficioDAO.salvar(pessoaBeneficio);

            response.addHeader("id", pessoaBeneficio.getId().toString());
            response.addHeader("msg", "Concessão realizada com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String action = ActionContext.getContext().getName();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Erro: " + erro);

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
    public void setBeneficiado(Pessoa beneficiado) {
        this.beneficiado = beneficiado;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}