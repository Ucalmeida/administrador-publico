$(document).ready(function() {
    $(".cpf").mask("###.###.###-##");
    $(".telefone").mask("(99)99999-9999");
    $("#mae").blur(function () {
        if (this.value == "") {
            $("#idMae").val("");
        }
    });
    $("#pai").blur(function () {
        if (this.value == "") {
            $("#idPai").val("");
        }
    });
    $("#mae").autocomplete({
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
                $("#idMae").val(ui.item.id);
                $("#mae").val(ui.item.nome);
            }
        }
    });
    $("#pai").autocomplete({
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
                $("#idPai").val(ui.item.id);
                $("#pae").val(ui.item.nome);
            }
        }
    });
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
            'mae': {required: true, haValor: "#idMae"},
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