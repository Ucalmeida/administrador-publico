$(document).ready(function() {
    $(".cpf").mask("###.###.###-##");
    $("#frmPessoaCadastrar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'pessoa.cpf': {required: true, cpf: true},
            'pessoa.nome': {required: true, minLength: 10},
            'pessoa.dataNascimento': {required: true, dataBR: true},
            'pessoa.sexo.id': {required: true, dataBR: true},
            'pessoaEndereco.rua.id': {required: true}
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