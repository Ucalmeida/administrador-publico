$(document).ready(function() {
    let oTable = $("#tbDependentes").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Tipo"},
            {"sTitle": "Nome"},
            {"sTitle": "Nascimento"},
            {"sTitle": "Sexo"}],
        "order": [[1, 'desc' ]],
        columnDefs: [
            {type: 'dataBR', targets: [2]}]
    });
})