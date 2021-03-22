<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="pmTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:pagina titulo="ERRO!" menu="true">
	<div class="error-page">
		<h2 class="headline text-warning">404</h2>
		<div class="error-content">
			<h3><i class="fas fa-exclamation-triangle text-warning"></i> Oops! Página não encontrada.</h3>
			<p>
				A página que você solicitou não foi encontrada, foi movida ou não existe.<br>
				Verifique se você digitou o endereço correto.
			</p><p>
				Todavia você também pode voltar a página principal do módulo <a href="principal">clicando aqui</a>
			</p>
			<form class="search-form dropdown-menu">
				<div class="input-group">
					<input type="text" name="search" class="form-control" placeholder="Search">

					<div class="input-group-append">
						<button type="submit" name="submit" class="btn btn-warning"><i class="fas fa-search"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</t:pagina>