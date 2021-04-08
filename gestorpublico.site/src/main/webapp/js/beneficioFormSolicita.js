$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        paging: true,
        iDisplayLength: 10,
        columns: [
            {title: "Beneficiado"},
            {title: "Benefício"},
            {title: "Observação"},
            {title: "Ação"}]
    });
    $("#frmBeneficioSolicitar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'beneficiado.id': {required: true},
            'beneficio.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, false, true);
            if (result[0] == true) {
                let id = result[1];
                let aData = {"0": $("#idBeneficiado option:selected").text(),
                    "1": $("#idBeneficio option:selected").text(),
                    "2": $("#observacao").val(),
                    "3": '<a onclick="javascript:excluir(\'pessoaBeneficio\','+id+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
                    "DT_RowId": "ln"+id};
                oTable.rows.add([aData]).draw();
                $(form)[0].reset();
                $('.focus').focus();
            }
            return false;
        }
    });
})