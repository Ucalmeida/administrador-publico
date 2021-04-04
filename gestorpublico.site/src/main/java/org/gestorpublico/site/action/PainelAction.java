package org.gestorpublico.site.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.gestorpublico.hibernate.HibernateUtil.getSession;

@ParentPackage("default")
public class PainelAction {
    private Pessoa pessoaLogada;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    @Action(value="painel",
        results={
            @Result(name="ok", location="interfacePainel.jsp"),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        String action = ActionContext.getContext().getName();
        try {
            Session session = (Session) request.getAttribute("sessao");
            pessoaLogada = (Pessoa) ActionContext.getContext().getSession().get("pessoaLogada");
            this.pessoaLogada = new PessoaDAO(getSession()).localizar(this.pessoaLogada.getId());

            if (pessoaLogada == null) {
                return "erro";
            }

            boolean temAvisos = false;

            if (temAvisos) return "notificacao";

            // ************************************************************************
            request.getSession().setAttribute("userName", pessoaLogada.getNome());
            ActionContext.getContext().getSession().put("pessoaLogada", pessoaLogada);

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            Session sessao = HibernateUtil.getSession();
            new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(action, erro));
            sessao.close();

            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public Pessoa getPessoaLogada() {
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa pessoaLogada) {
        this.pessoaLogada = pessoaLogada;
    }

    public String getDataDiaAtual() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dddd, dd 'de' MMMM 'de' yyyy"));
    }

    public String getSaudacao() { return CassUtil.getSaudacao(); }
}
