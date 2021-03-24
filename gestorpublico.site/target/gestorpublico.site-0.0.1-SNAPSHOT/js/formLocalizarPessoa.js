$(document).ready(function() {
    let oTable = $("#tbPessoas").DataTable({
        "responsive" : true,
        "scrolly": "400px",
        "scrollCollapse": true,
        "paging": false,
        "searching": false,
        "aoColumns": [{"sTitle": "Nome", "width": "50%"},
            {"sTitle": "CPF", "width": "25%"},
            {"sTitle": "Opção", "width": "25%"}]
    });

    $("#cpf").mask('999.999.999-99');
    $("#nome").maiusculo();

    $("#formLocalizarPessoa").submit(function(e){e.preventDefault();}).validate({
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
            let aData = {"0": '<p>FULANO DE TAL JUNIOR</p>',
                "1": '<p>999.888.777-66</p>',
                "2": '<button id="beneficios" class="btn btn-warning" onclick="javascript:verBeneficiosPorPessoa()">Beneficios</button>' + ' ' + '<button id="alterar" class="btn btn-primary">Alterar</button>',
                "DT_RowId": "ln"+1};
            oTable.rows.add([aData]).draw();

            $('#tbPessoas tbody').on( 'click', '.btn-danger', function() {
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