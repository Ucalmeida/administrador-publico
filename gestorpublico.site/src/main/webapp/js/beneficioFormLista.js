$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Beneficiado"},
            {"sTitle": "Benefício"},
            {"sTitle": "Solicitado"},
            {"sTitle": "Observação"},
            {"sTitle": "Situação"},
            {"sTitle": "Despacho"},
            {"sTitle": "Ação"}],
        "order": [[1, 'desc' ]],
        columnDefs: [
            {type: 'dataHoraBR', targets: [2]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})