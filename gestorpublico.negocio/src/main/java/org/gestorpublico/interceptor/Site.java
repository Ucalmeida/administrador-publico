package org.gestorpublico.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class Site implements Interceptor {

	private static final long serialVersionUID = 1L;
	
	public void destroy() {}

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception {
////		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
////		Session session = (Session) request.getAttribute("sessao");
		
//		Pessoa pessoaLogada = (Pessoa) invocation.getInvocationContext().getSession().get("pessoaLogada");
//		
//		if (pessoaLogada == null) {
//			ServletActionContext.getResponse().setHeader("interceptador", "erro");
//			ServletActionContext.getResponse().setHeader("erro", "Nao logado.");
//			
//			return "siteNaoLogado";
//		}
		
		ServletActionContext.getResponse().setHeader("interceptador", "ok");
		
		return invocation.invoke();
	}

}
