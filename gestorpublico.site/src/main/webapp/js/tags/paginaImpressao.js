
//Termos
function imprimirPagina() {
    let conteudo = document.getElementById('printable').innerHTML;
        tela_impressao = window.open('about:blank');
        tela_impressao.document.write('<html lang="pt-BR"><head>' +
        '<link rel="stylesheet" href="/portal/css/adminlte.min.css">' +
        '<link rel="stylesheet" href="/portal/css/adminlte.portal.css">' +
        '<link rel="stylesheet" href="/portal/plugins/fontawesome/css/all.min.css">' +
        '<link rel="stylesheet" href="/portal/css/tags/paginaImpressao.css">' +
        '<link rel="icon" href="/portal/images/favicon.png" />' +
            '<style>' +
            '#divbotoes {' +
                'display: none;' +
            '}' +
            '</style>' +
        '</head><body onload="window.print()">');
    tela_impressao.document.write(conteudo);
    tela_impressao.document.write('<div class="row d-print-none fixed-bottom align-items-center">' +
        '<div class="col-12 text-center">' +
            '<button onClick="print()" class="btn btn-primary btn-lg m-1">' +
            '<i class="fas fa-print mr-1"></i>Imprimir' +
            '</button>' +
            "<button class='btn btn-danger m-1' onclick='window.close()'><i class='fas fa-times-circle mr-1'></i>Fechar</button>" +
        "</div>" +
        "</div>");
    tela_impressao.document.write('<script type="text/javascript" charset="utf-8" src="/portal/plugins/jquery/jquery.min.js"></script>' +
        '<script type="text/javascript" charset="utf-8" src="/portal/js/core/adminlte.min.js\"></script></body></html>');
    tela_impressao.document.close();
}