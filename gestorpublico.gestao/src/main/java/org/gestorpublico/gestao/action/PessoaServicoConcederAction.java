package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.entidade.Servico;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@ParentPackage("default")
public class PessoaServicoConcederAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa solicitante;
    private Servico servico;
    private String observacao;

    @Action(value="pessoaServicoConceder",
        results={
            @Result(name="ok", type="httpheader", params={"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = (Session) request.getAttribute("sessao");
        try {
            Pessoa pessoaLogada = (Pessoa) ActionContext.getContext().getSession().get("pessoaLogada");
            Pessoa_ServicoDAO pessoaServicoDAO = new Pessoa_ServicoDAO(session);

            if (solicitante == null || solicitante.getId() == null) {
                response.setHeader("erro","Pessoa não encontrada.");
                return "erro";
            }

            if (servico == null || servico.getId() == null) {
                response.setHeader("erro","Serviço não encontrado.");
                return "erro";
            }

            Pessoa_Servico pessoaServico = new Pessoa_Servico();
            pessoaServico.setResponsavel(pessoaLogada);
            pessoaServico.setSolicitante(solicitante);
            pessoaServico.setServico(servico);
            pessoaServico.setDataHoraDespacho(LocalDateTime.now());
            pessoaServico.setObservacao(observacao);
            pessoaServico.setAutorizado(true);
            pessoaServico.setDespacho("Autorizado");

            pessoaServicoDAO.salvar(pessoaServico);

            response.addHeader("id", pessoaServico.getId().toString());
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
    public void setSolicitante(Pessoa solicitante) {
        this.solicitante = solicitante;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}