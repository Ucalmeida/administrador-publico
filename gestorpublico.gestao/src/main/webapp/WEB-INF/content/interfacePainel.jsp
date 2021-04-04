<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang='pt-BR'>
    <head>
        <jsp:include page="head.jsp" />

        <title>Painel</title>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="menuLateral.jsp" />
            <div class="content-wrapper" style="min-height: 296px;">
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-12">
                                <div class="row">
                                    <div class="col-auto"><h1 class="m-0 text-verdepetroleo"></h1></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <section class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12" style="text-align: center; top: 25px;">
                                <img alt="USUARIO" height="480px" src="images/brasoes/brasao_riachuelo_se_100_100.png">
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <jsp:include page="rodape.jsp" />
            <script type="text/javascript" charset="utf-8" src="js/nucleo.js"></script>
            <script type="text/javascript" charset="utf-8" src="js/interfacePainel.js"></script>
        </div>
    </body>
</html>
