package org.gestorpublico.site.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import org.gestorpublico.util.Constantes;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;

@ParentPackage("default")
public class SenhaAlterarAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private String senhaAtual;
    private String novaSenha;

    @Action(value="senhaAlterar",
            results={
                    @Result(name="ok", type="httpheader", params={"status", "200"}),
                    @Result(name="erro", type="httpheader", params={"status", "409"})
            }
    )
    public String execute() {
        Session session = (Session) request.getAttribute("sessao");
        try {
            PessoaDAO pessoaDAO = new PessoaDAO(session);
            Pessoa pessoaLogada = (Pessoa) ActionContext.getContext().getSession().get("pessoaLogada");
            if (pessoaLogada == null) {
                response.addHeader("erro", "Acesso negado.");
                return "erro";
            }

            senhaAtual = CassUtil.criptografar(senhaAtual);

            Matcher m = Constantes.PADRAO_SENHA.matcher(novaSenha);
            if (!m.matches()) {
                response.addHeader("erro", "Senha atual não contempla os requisitos mínimos.");
                return "erro";
            }
            if (!pessoaLogada.getSenha().equals(senhaAtual)) {
                response.addHeader("erro", "Senha atual incorreta.");
                return "erro";
            }
            novaSenha = CassUtil.criptografar(novaSenha);
            pessoaLogada.setSenha(novaSenha);
            pessoaDAO.atualizar(pessoaLogada);

            response.addHeader("msg", "Senha alterada com sucesso.");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String action = ActionContext.getContext().getName();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            if (session.getTransaction().isActive()) {
                Session sessao = HibernateUtil.getSession();
//                new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(action, erro));
                sessao.close();
            } else {
//                new Log_Erro_ExecucaoDAO(session).salvar(new Log_Erro_Execucao(action, erro));
            }

            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}