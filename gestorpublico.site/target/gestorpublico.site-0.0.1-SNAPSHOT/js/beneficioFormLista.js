$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Benefício"},
            {"sTitle": "Solicitado em"},
            {"sTitle": "Observação"},
            {"sTitle": "Situação"},
            {"sTitle": "Despacho"},
            {"sTitle": "Ação"}],
        "order": [[1, 'desc' ]],
        columnDefs: [
            {type: 'dataHoraBR', targets: [1]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})