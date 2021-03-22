package org.gestorpublico.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

public class FiltroConexao implements Filter {
	
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		/*
		 * Instancia uma sessao com o Hibernate para acesso ao Banco de dados e
		 * adiciona ao REQUEST
		 */
		Session session = HibernateUtil.getSession();
		request.setAttribute("sessao", session);
		
		/*
		 * Armazena o IP do cliente que solicita acesso ao sistema
		 */
//		Log_IpDAO logIpDAO = new Log_IpDAO(session);
//		String ip = request.getRemoteAddr();
//		Log_Ip logIp = logIpDAO.getLogIpPorIp(ip);
//		
//		if (logIp == null) {
//			logIp = new Log_Ip(ip);
//			logIpDAO.salvar(logIp);
//		}
		
		/*
		 * Usado para bloquear clientes indesejados com base no IP
		 */
//		if (logIp != null && logIp.getNegar()) {
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
//			//send error or maybe redirect to some error page
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }
		
		// ****************** EXECUTADO QUANDO O REQUISICAO E RESPONDIDA ******************
		chain.doFilter(request, response);

		session.close();
		
	}

	public void init(FilterConfig config) throws ServletException {}

}
