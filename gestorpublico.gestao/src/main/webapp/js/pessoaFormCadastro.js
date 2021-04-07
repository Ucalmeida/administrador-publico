$(document).ready(function() {
    $(".cpf").mask("###.###.###-##");
    $("#vivo").change(function () {
        if (this.value == "true") {
            $("#dataFalecimento").prop("disabled", true);
            $("#dataFalecimento").val("");
        } else {
            $("#dataFalecimento").prop("disabled", false);
        }
    });
    $("#idUF").change(function () {
        $("#idMunicipio, #idBairro, #idRua, #idCondominio, #idEdificio, #idPontoReferencia").empty();
        let id = this.value;
        if (id > 0) {
            popularSelect( "#idMunicipio", "getMunicipiosPorUF", {"uf.id": id})
        }
    });
    $("#idMunicipio").change(function () {
        $("#idBairro, #idRua, #idCondominio, #idEdificio, #idPontoReferencia").empty();
        let id = this.value;
        if (id > 0) {
            popularSelect( "#idBairro", "getBairrosPorMunicipio", {"municipio.id": id})
        }
    });
    $("#idBairro").change(function () {
        $("#idRua, #idCondominio, #idEdificio, #idPontoReferencia").empty();
        let id = this.value;
        if (id > 0) {
            popularRuasCondominiosEdificiosReferencias("#idRua", "#idCondominio", "#idEdificio", "#idPontoReferencia", "getRuasCondominiosEdificiosReferenciasPorBairro", {"bairro.id": id})
        }
    });
    $("#frmPessoaCadastrar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'pessoa.cpf': {required: true, cpf: true},
            'pessoa.nome': {required: true, minlength: 10},
            'pessoa.dataNascimento': {required: true, dateISO: true},
            'pessoa.sexo.id': {required: true},
            'pessoa.vivo': {required: true},
            'pessoa.dataFalecimento': {required: function(){return $("#vivo").val() == "false";}},
            'uf': {required: true},
            'municipio': {required: true},
            'bairro': {required: true},
            'pessoaEndereco.rua.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, true, true);
            return false;
        }
    });
})