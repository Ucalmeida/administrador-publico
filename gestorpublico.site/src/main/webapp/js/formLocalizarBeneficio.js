$(document).ready(function() {
    let oTable = $("#tbBeneficios").DataTable({
        "responsive" : true,
        "scrolly": "400px",
        "scrollCollapse": true,
        "paging": false,
        "searching": false,
        "aoColumns": [{"sTitle": "Nome", "width": "40%"},
            {"sTitle": "Descrição", "width": "30%"},
            {"sTitle": "Valor", "width": "20%"},
            {"sTitle": "Opção", "width": "10%"}]
    });

    $("#nome").maiusculo();

    $("#formLocalizarBeneficio").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            // 'pessoa.cpf': {required: true},
            // 'pessoa.nome': {required: true},
            // 'pessoa.dataNascimento': {required: true},
            // 'sexo.id': {required: true},
            // 'pessoa.celular': {required: true},
            // 'pessoa.senha': {required: true},
        },
        submitHandler: function(form) {
            oTable.clear().draw();
            let aData = {"0": '<p>PEIXE</p>',
                "1": '<p>ENTREGA DE PEIXE</p>',
                "2": '<p>$$$</p>',
                "3": '<button id="excluir" class="btn btn-danger">Excluir</button>',
                "DT_RowId": "ln"+1};
            oTable.rows.add([aData]).draw();

            $('#tbBeneficios tbody').on( 'click', '.btn-danger', function() {
                oTable
                    .row( $(this).parents('tr') )
                    .remove()
                    .draw();
            });
            // let result = enviar(form, true, true, true)
            // return false;
        }

    });
})