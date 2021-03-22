package org.gestorpublico.site.action;

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

public class NaoLogadoAction {
	
//	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	private String url;
	
	@Action(value="naoLogado",
		results={
			@Result(name="ok", type="redirectAction", params={"actionName", ""}),
			@Result(name="erro", type="httpheader", params={"status", "409"})
		}
	)
	public String execute() {
		try {
			
			return "ok";
			
		} catch (Exception e) {
			e.printStackTrace();
			String action = ActionContext.getContext().getName();
			String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();
			
			response.addHeader("erro", "Desculpe, ocorreu um erro. Tente novamente, se persistir informe a central");
			
			Session sessao = HibernateUtil.getSession();
			new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(action, "Erro: " + erro));
			sessao.close();
			
			return "erro";
		}
	}
	
	// ****************************** GETs e SETs ******************************
	public String getUrl() {
		return url;
	}
	
}
