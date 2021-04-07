$(document).ready(function() {
    let oTable = $("#tbCidadaos").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "order": [[0, 'asc' ]],
        "aoColumns": [
            {"sTitle": "Nome"},
            {"sTitle": "CPF"},
            {"sTitle": "Nascimento"},
            {"sTitle": "Celular"},
            {"sTitle": "Ação"}],
        columnDefs: [
            {type: 'dataBR', targets: [2]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})