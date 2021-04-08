$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        paging: true,
        iDisplayLength: 10,
        order: [[1, 'desc' ]],
        columns: [
            {title: "Beneficiado"},
            {title: "Benefício"},
            {title: "Solicitado"},
            {title: "Observação"},
            {title: "Situação"},
            {title: "Despacho"},
            {title: "Ação"}],
        columnDefs: [
            {type: 'dataHoraBR', targets: [2]}/*,
            {className: "dt-body-right", targets: [7]}*/]
    });
})