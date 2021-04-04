$(document).ready(function() {
    let oTable = $("#tbConsultas").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Especialidade"},
            {"sTitle": "Médico"},
            {"sTitle": "Paciente"},
            {"sTitle": "Data e horário"},
            {"sTitle": "Local"},
            {"sTitle": "Ação"}],
        "order": [[1, 'desc' ]],
        columnDefs: [
            {type: 'dataHoraBR', targets: [3]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})