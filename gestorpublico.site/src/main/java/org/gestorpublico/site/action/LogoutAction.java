package org.gestorpublico.site.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction {
	
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	@Action(value="logout",
		results={
			@Result(name="ok", type="redirectAction", params={"actionName", ""}),
			@Result(name="erro", location="404.jsp"),
		}
	)
	public String execute() {
		try {
			SessionMap sessao = (SessionMap) ActionContext.getContext().get(ActionContext.SESSION);
			sessao.invalidate();
			return "ok";
			
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();
			
			response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);
			
			return "erro";
		}
	}
	
	// ****************************** GETs e SETs ******************************
}
