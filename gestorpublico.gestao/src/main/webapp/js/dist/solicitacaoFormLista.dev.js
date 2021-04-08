"use strict";

$(document).ready(function () {
  var oTableBaneficios = $("#tbBeneficios").DataTable({
    paging: true,
    iDisplayLength: 10,
    order: [[0, 'asc']],
    columns: [{
      title: "Em"
    }, {
      title: "Solicitante"
    }, {
      title: "Benefício"
    }, {
      title: "Observação"
    }, {
      title: "Ação"
    }],
    columnDefs: [{
      type: 'dataHoraBR',
      targets: [0]
    }
    /*,
    {className: "dt-body-right", targets: [7]}*/
    ]
  });
  var oTableServicos = $("#tbServicos").DataTable({
    paging: true,
    iDisplayLength: 10,
    order: [[0, 'asc']],
    columns: [{
      title: "Em"
    }, {
      title: "Solicitante"
    }, {
      title: "Serviço"
    }, {
      title: "Observação"
    }, {
      title: "Ação"
    }],
    columnDefs: [{
      type: 'dataHoraBR',
      targets: [0]
    }
    /*,
    {className: "dt-body-right", targets: [7]}*/
    ]
  });
});