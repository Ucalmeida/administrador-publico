$(document).ready(function() {
    let oTable = $("#tbServicos").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Serviço"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}]
    });
    $("#frmServicoSolicitar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'servico.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, false, true);
            if (result[0] == true) {
                let id = result[1];
                let aData = {"0": $("#idServico option:selected").text(),
                    "1": $("#observacao").val(),
                    "2": '<a onclick="javascript:excluir(\'pessoaServico\','+id+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
                    "DT_RowId": "ln"+id};
                oTable.rows.add([aData]).draw();
                $(form)[0].reset();
                $('.focus').focus();
            }
            return false;
        }
    });
})