package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.gestorpublico.dao.*;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.gestorpublico.util.Constantes.SENHA_EMAIL_PORTAL;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 3619440897616238220L;

	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();

	private String login;
	private String senha;

	@Action(value="login",
			results={
					@Result(name="ok", type="redirectAction", params = {"actionName", "dashboard"}),
					@Result(name="erro", type="httpheader", params={"status", "409"}),
					@Result(name="naoAutorizado", type="redirectAction", params={"actionName", "naoLogado"}),
			}
	)
	public String execute() {
		Session session = (Session) request.getAttribute("sessao");
		try {
			PessoaDAO pessoaDAO = new PessoaDAO(session);
			if (CassUtil.validaRegexSenha(senha)) {
				senha = CassUtil.criptografar(senha);
			}
			Pessoa pessoaLogada = pessoaDAO.getPessoaAcessoPermitidoPorLoginSenha(login, senha);

			if (pessoaLogada == null) {
				return "naoAutorizado";
			}

			request.getSession().setAttribute("userName", pessoaLogada.getNome());
			ActionContext.getContext().getSession().put("pessoaLogada", pessoaLogada);

			/**
			 * Retorno criado para o diferenciar o redirecionamento do login com Certificado digital
			 */
			if (System.getenv().get("AMBIENTE") != null && System.getenv().get("AMBIENTE").equals("PROD")) {
				return "okHttps";
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

	private String md5(String senha) throws NoSuchAlgorithmException {
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}

	// ****************************** GETs e SETs ******************************
	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}