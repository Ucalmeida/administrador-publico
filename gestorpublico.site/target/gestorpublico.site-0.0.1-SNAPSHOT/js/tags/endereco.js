function alterarEndereco() {
    $('#frmEndereco').submit();
};
function alterarEndereco2() {
    var uf = $('#idUF');
    var cep = $('#idCep');
    var cidade = $('#idCidadeEnd');
    var cidadeF = $('#idCidadefixa');
    var bairro = $('#idBairro');
    var rua = $('#idLogradouro');
    var numero = $('#numero');
    var complemento = $('#complemento');
    var referencia = $('#referencia');

    uf.removeAttr('disabled');
    cep.removeAttr('disabled').val('');

    cidadeF.addClass('hide');
    cidade.removeAttr('disabled').removeClass('hide');

    bairro.removeAttr('disabled').val('');
    rua.removeAttr('disabled').val('');
    numero.removeAttr('disabled').val('');
    complemento.removeAttr('disabled').val('');
    referencia.removeAttr('disabled').val('');
};

window.onload = function() {

    $("#idUF").change(function () {
        var id = this.value;
        if (id > 0) {
            popularSelect($("#idCidadeEnd"), "getCidadesPorUF", {"uf.id": id});
        } else {
            $("#idCidadeEnd").empty();
        }
    });

    $("#idCidadeEnd").change(function () {
        var id = this.value;
        if (id > 0) {
            $("#btnNovoBairro").attr("onclick", "novoBairro(" + id + ")");
            $("#idBairro").empty();
            popularSelect($("#idBairro"), "getBairrosPorCidade", {"cidade.id": id});
        } else {
            $("#btnNovoBairro").attr("onclick", "");
            $("#idBairro").empty();
        }
    });

    $("#idBairro").change(function () {
        var id = this.value;
        if (id > 0) {
            $("#btnNovoLogradouro").attr("onclick", "novoLogradouro(" + id + ")");
            $("#idLogradouro").empty();
            popularSelect($("#idLogradouro"), "getLogradourosPorBairro", {"bairro.id": id});
        } else {
            $("#btnNovoLogradouro").attr("onclick", "");
            $("#idLogradouro").empty();
        }
    });

    $("#idCida").maiusculo();
    $("#idCida").autocomplete({
        minLength: 4,
        source: function (request, response) {
            $.ajax({
                async: false, cache: false, type: "post", dataType: "json",
                url: "getCidadesPorNome",
                data: {nome: request.term},
                success: function (data) {
                    response(data.objetos);
                }
            });
        },
        select: function (event, ui) {
            $("#idCid").val(ui.item.id);
            $("#nome").val(ui.item.post);
        }
    });


    $('#idUf').change(function () {
        popularSelect($("#idNaturalidade"), 'getCidadesPorUF', {"uf.id": $('#id').val()});
    });

    $('#idEstado').change(function () {
        popularSelect($("#idCid"), 'getCidadesPorUF', {"uf.id": $('#idEstado').val()});
    });

    $("#idCep").autocomplete({
        minLength: 10,
        source: function (request, response) {
            $.ajax({
                async: false, cache: false, type: "post", dataType: "json",
                url: "/pgetLogradourosPorCEP",
                data: {'cep': request.term},
                success: function (data) {
                    response(data.objetos);
                }
            });
        },
        response: function (event, ui) {
            if (!ui.content.length) {
                var naoEncontrado = {label: "NÃO ENCONTRADO"};
                ui.content.push(naoEncontrado);
            }
        },
        select: function (event, ui) {
            if (ui.item.label != "NÃO ENCONTRADO") {
                $("#idUF").val(ui.item.idUF);
                popularSelect("#idCidadeEnd", "getCidadesPorUF", {'uf.id': ui.item.idUF}, ui.item.idCidade);
                popularSelect("#idBairro", "getBairrosPorCidade", {'cidade.id': ui.item.idCidade}, ui.item.idBairro);
                popularSelect("#idLogradouro", "getLogradourosPorBairro", {'bairro.id': ui.item.idBairro}, ui.item.id);
                $("#btnNovoBairro").attr("onclick", "novoBairro(" + ui.item.idCidade + ")");
                $("#btnNovoLogradouro").attr("onclick", "novoLogradouro(" + ui.item.idBairro + ")");
            }
        }
    });
    $("#idCidade").maiusculo();
    $("#idCidade").autocomplete({
        minLength: 3,
        source: function (request, response) {
            $.ajax({
                async: true, cache: false, type: "post", dataType: "json",
                url: "getCidadesPorNome",
                data: {nomeCidade: request.term},
                success: function (data) {
                    response(data.objetos);
                }
            });
        },
        select: function (event, ui) {
            $("#idCidadeI").val(ui.item.id);
        }
    });

    $("#frmEndereco").submit(function (e) {
        e.preventDefault();
    }).validate({
        rules: {
            'cep': {required: true},
            'logradouro': {required: true},
            'numero': {required: true},
            'bairro': {required: true},
            'uf.idUF': {required: true},
            'cidade.id': {required: true},
        },
        submitHandler: function (form) {
            var result = enviar(form, false);
            if (result[0] == true) {
                var id = result[1];
            }
            return false;
        }
    });

    $('#idLogradouro').maiusculo();
    $('#idNumero').maiusculo();
    $('#idNumero').mask('999999');
    $('#idBairro').maiusculo();
    $('#idComplemento').maiusculo();
    $('#idCep').mask('99.999-999');
};