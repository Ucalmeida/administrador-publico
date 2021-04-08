$(document).ready(function() {
    oTable = $("#tbConcessoes").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "order": [[0, 'asc' ]],
        "aoColumns": [
            {"sTitle": "Solicitante"},
            {"sTitle": "Serviço"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}],
        columnDefs: [
/*            {type: 'dataHoraBR', targets: [0]},
            {className: "dt-body-right", targets: [7]}*/]
    });
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
                $("#idSolicitante").val(ui.item.id);
                $("#nome").val(ui.item.nome);
            }
        }
    });
    $("#frmServicoConceder").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'nome': {required: true, haValor: "#idSolicitante"},
            'servico.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, false);
            if (result[0] == true) {
                let id = result[1];
                let aData = {"0": $("#nome").val(),
                    "1": $("#idServico option:selected").text(),
                    "2": $("#observacao").val(),
                    "3": '<a onclick="javascript:excluir(\'pessoaServico\','+id+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
                    "DT_RowId": "ln"+id};
                oTable.rows.add([aData]).draw();
                $(form)[0].reset();
                $('.focus').focus();
            }
        }
    });
})