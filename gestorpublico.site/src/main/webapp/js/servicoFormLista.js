$(document).ready(function() {
    let oTable = $("#tbServicos").DataTable({
        paging : true,
        iDisplayLength: 10,
        columns: [
            {title: "Serviço"},
            {title: "Solicitado em"},
            {title: "Observação"},
            {title: "Situação"},
            {title: "Despacho"},
            {title: "Ação"}],
        order: [[1, 'desc' ]],
        columnDefs: [
            {type: 'dataHoraBR', targets: [1]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})