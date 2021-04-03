$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Benefício"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}]
    });
    $("#frmBeneficioSolicitar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'beneficio.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, false, true);
            if (result[0] == true) {
                let id = result[1];
                let aData = {"0": $("#idBeneficio option:selected").text(),
                    "1": $("#observacao").val(),
                    "2": '<a onclick="javascript:excluir(\'pessoaBeneficio\','+id+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
                    "DT_RowId": "ln"+id};
                oTable.rows.add([aData]).draw();
                $(form)[0].reset();
                $('.focus').focus();
            }
            return false;
        }
    });
})