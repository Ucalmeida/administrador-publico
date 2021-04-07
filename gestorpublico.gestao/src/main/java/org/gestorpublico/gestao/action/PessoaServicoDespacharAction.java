package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ParentPackage("default")
public class PessoaServicoDespacharAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa_Servico pessoaServico;
    private boolean autorizacao;
    private String despacho;

    @Action(value="servicoDespachar",
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
            pessoaServico = pessoaServicoDAO.getServico(pessoaServico);

            if (pessoaServico == null) {
                response.setHeader("erro","Solicitação não encontrada.");
                return "erro";
            }

            if (despacho == null || despacho.trim().isEmpty()) {
                despacho = autorizacao ? "Autorizado." : "Não autorizado";
            }

            pessoaServico.setDataHoraDespacho(LocalDateTime.now());
            pessoaServico.setAutorizado(autorizacao);
            pessoaServico.setDespacho(despacho);
            pessoaServico.setResponsavel(pessoaLogada);

            pessoaServicoDAO.atualizar(pessoaServico);

            response.addHeader("id", pessoaServico.getId().toString());
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
    public void setPessoaServico(Pessoa_Servico pessoaServico) {
        this.pessoaServico = pessoaServico;
    }

    public void setAutorizacao(boolean autorizacao) {
        this.autorizacao = autorizacao;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho == null || despacho.trim().isEmpty() ? null : despacho.trim();
    }
}