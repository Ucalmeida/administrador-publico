$(document).ready(function() {
    let oTable = $("#tbServicos").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Serviço"},
            {"sTitle": "Solicitado em"},
            {"sTitle": "Observação"},
            {"sTitle": "Situação"},
            {"sTitle": "Despacho"},
            {"sTitle": "Ação"}]
    });
})