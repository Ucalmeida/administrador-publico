$(document).ready(function() {
    let oTableBaneficios = $("#tbBeneficios").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "order": [[0, 'asc' ]],
        "aoColumns": [
            {"sTitle": "Em"},
            {"sTitle": "Solicitante"},
            {"sTitle": "Benefício"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}],
        columnDefs: [
            {type: 'dataHoraBR', targets: [0]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
    let oTableServicos = $("#tbServicos").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "order": [[0, 'asc' ]],
        "aoColumns": [
            {"sTitle": "Em"},
            {"sTitle": "Solicitante"},
            {"sTitle": "Serviço"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}],
        columnDefs: [
            {type: 'dataHoraBR', targets: [0]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})