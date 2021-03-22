package org.gestorpublico.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class Gestao implements Interceptor {

	private static final long serialVersionUID = 1L;
	
//	private HttpServletRequest request = ServletActionContext.getRequest();
	
	public void destroy() {}

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception {
//		Pessoa pessoa = (Pessoa) invocation.getInvocationContext().getSession().get("pessoaLogada");
//		if (pessoa == null) {
//			ServletActionContext.getResponse().setHeader("interceptador", "erro");
//			return "naoLogado";
//		}
//		
//		if (!pessoa.isAdministrativo()) { 
//			ServletActionContext.getResponse().setHeader("interceptador", "ok");
//			ServletActionContext.getResponse().setHeader("msg", "Você não tem acesso a esse recurso.");
//			return "desautorizado";
//		}
		
		ServletActionContext.getResponse().setHeader("interceptador", "ok");
		
		return invocation.invoke();
	}

}
