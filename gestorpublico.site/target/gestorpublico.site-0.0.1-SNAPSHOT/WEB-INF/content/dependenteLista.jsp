<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang='pt-BR'>
<head>
    <jsp:include page="head.jsp" />

    <title>Dependente</title>
</head>
<body>
    <jsp:include page="menuLateral.jsp" />
    <div class="content-wrapper" style="min-height: 296px;">
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h4 class="titulo">Dependentes</h4>
                    </div>
                    <div class="col-lg-12 table-responsive">
                        <table id="tbDependentes" class="table table-striped table-bordered" width="100%"><c:forEach items="${dependentes}" var="o">
                            <tr id="ln${o.id}">
                                <td>${o.tipoDependente.nome}</td>
                                <td>${o.dependente.nome}</td>
                                <td>${o.dependente.dataNascimentoFormatada}</td>
                                <td>${o.dependente.sexo.nome}</td>
                            </tr></c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="rodape.jsp" />
    <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/tabela.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/dependenteLista.js"></script>
</body>
</html>
