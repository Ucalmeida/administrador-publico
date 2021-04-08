$(document).ready(function() {
    $('a[data-toggle="tab"]').on( 'shown.bs.tab', function (e) {
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    } );

    $('table.table').DataTable( {
        scrollY:        300,
        scrollCollapse: true,
        paging:         false
    } );
    oTableBeneficios = $("#tbBeneficios").DataTable();
    oTableServicos = $("#tbServicos").DataTable();
//     oTableBeneficios = $("#tbBeneficios").DataTable({
//         scrollY:        200,
//         scrollCollapse: true,
//         "bPaginate" : true,
//         "iDisplayLength": 10,
//         "order": [[0, 'asc' ]],
//         "aoColumns": [
//             {"sTitle": "Em"},
//             {"sTitle": "Benefício"},
//             {"sTitle": "Observação"}],
//         columnDefs: [
// /*            {type: 'dataHoraBR', targets: [0]},
//             {className: "dt-body-right", targets: [7]}*/]
//     });
//     oTableServicos = $("#tbServicos").DataTable({
//         scrollY:        200,
//         scrollCollapse: true,
//         "bPaginate" : true,
//         "iDisplayLength": 10,
//         "order": [[0, 'asc' ]],
//         "aoColumns": [
//             {"sTitle": "Em"},
//             {"sTitle": "Serviço"},
//             {"sTitle": "Observação"}],
//         columnDefs: [
// /*            {type: 'dataHoraBR', targets: [0]},
//             {className: "dt-body-right", targets: [7]}*/]
//     });
    $("#nome").autocomplete({
        minLength: 8,
        source: function(request, response) {
            let action = isNaN(request.term.substr(0,1)) ? "getPessoaPorNome" : "getPessoaPorCPF";
            $.ajax({async: false, cache: false, type: "post", dataType: "json", url: action, data: {cpf: request.term, nome: request.term},
                success: function(data) {response(data.objetos);}
            });
        },
        response: function(event, ui) {
            if (!ui.content.length) {let naoEncontrado = {label:"NÃO ENCONTRADO"};ui.content.push(naoEncontrado);}
        },
        select: function(event, ui) {
            if (ui.item.label != "NÃO ENCONTRADO") {
                oTableBeneficios.clear().draw();
                oTableServicos.clear().draw();
                $("#idPessoa").val(ui.item.id);
                $("#nome").val(ui.item.nome);
                // let ag = aguarde();
                $.post("getBeneficiosServicosPorPessoa", {'pessoa.id': ui.item.id}, function(data) {
                    let aData;
                    if (Object.keys(data.beneficios).length > 0) {
                        $.each(data.beneficios, function (key, o) {
                            aData = {
                                "0": o.data,
                                "1": o.nome,
                                "2": o.obse,
                                "DT_RowId": "ln" + o.id
                            };
                            oTableBeneficios.rows.add([aData]).draw();
                        });
                    }
                    if (Object.keys(data.servicos).length > 0) {
                        $.each(data.servicos, function (key, o) {
                            aData = {
                                "0": o.data,
                                "1": o.nome,
                                "2": o.obse,
                                "DT_RowId": "ln" + o.id
                            };
                            oTableServicos.rows.add([aData]).draw();
                        });
                    }
                    // ag.modal("hide");
                });
            }
        }
    });
})