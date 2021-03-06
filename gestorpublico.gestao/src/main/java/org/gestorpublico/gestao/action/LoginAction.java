package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction extends PadraoAction {
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	private String login;
	private String senha;
	
	@Action(value="login",
		results={
			@Result(name="ok", type="redirectAction", params={"actionName", "painel"}),
			@Result(name="naoAutorizado", type="redirectAction", params={"actionName", ""}),
			@Result(name="erro", type="httpheader", params={"status", "409"})
		}
	)
	public String execute() {
		Session session = getSession();
		try {
			if (CassUtil.validaRegexSenha(senha)) {
				senha = CassUtil.criptografar(senha);
			}
			PessoaDAO pessoaDAO = new PessoaDAO(session);

			Pessoa pessoa = pessoaDAO.getPessoaAcessoAdministrativoPermitidoPorLoginSenha(login, senha);

			if (pessoa == null) {
				return "naoAutorizado";
			}

			request.getSession().setAttribute("userName", pessoa.getNome());
			ActionContext.getContext().getSession().put("pessoaLogada", pessoa);
			ActionContext.getContext().getSession().put("logado", true);
			
			return "ok";
			
		} catch (Exception e) {
			e.printStackTrace();
			String action = ActionContext.getContext().getName();
			String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();
			
			response.addHeader("erro", CassUtil.getBytesString("Ocorreu o seguinte erro: " + erro));
			
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
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
