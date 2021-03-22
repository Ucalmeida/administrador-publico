package org.gestorpublico.gestao.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;

public class IndexAction {
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	@Action(value="",
		results={
			@Result(name="ok", location="pagina_inicial.jsp"),
			@Result(name="erro", location="404.jsp"),
		}
	)
	public String execute() {
		Session session = (Session) request.getAttribute("sessao");
		try {
			
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
	public String getTitulo() {
		return "Gestor Público";
	}
	
}
