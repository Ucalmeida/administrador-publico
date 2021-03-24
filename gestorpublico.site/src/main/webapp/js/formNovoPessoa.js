$(document).ready(function() {
    let oTable = $("#tbDependentes").DataTable({
        "responsive" : true,
        "scrolly": "400px",
        "scrollCollapse": true,
        "paging": false,
        "searching": false,
        "aoColumns": [{"sTitle": "Tipo Dependente", "width": "25%"},
            {"sTitle": "Nome", "width": "5%"},
            {"sTitle": "Data Nascimento", "width": "20%"},
            {"sTitle": "CPF", "width": "10%"},
            {"sTitle": "Opção", "width": "10%"}]
    });

    $("#senha2").blur(function() {
      let senha1 = $("#senha").val();
      let senha2 = $("#senha2").val();
      if(senha1 != senha2) {
          exibaMensagem("", "Senhas precisam ser iguais. Por favor, digite novamente");
      }
    });

    $("#cpf").mask('999.999.999-99');
    $("#cpfDep").mask('999.999.999-99');

    $("#dataNascimento").mask('99/99/9999');
    $("#dataNascimento").datepicker();

    $("#dataNascimentoDep").mask('99/99/9999');
    $("#dataNascimentoDep").datepicker();

    $("#cartaoSus").mask('999999999999999');
    $("#cartaoSusDep").mask('999999999999999');
    $("#nis").mask('999999999999999');
    $("#nisDep").mask('999999999999999');

    $("#celular").mask('(99)99999-9999');
    $("#celularDep").mask('(99)99999-9999');

    $(".endereco").css('display', 'none');

    $("#mesmoEndereco").click(function () {
        if($("#mesmoEndereco").is(":checked")) {
            $(".endereco").css('display', 'none');
        } else {
            $(".endereco").css('display', 'block');
        }
    });

    $("#formNovoPessoa").submit(function(e){e.preventDefault();}).validate({
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
            $("#btnSalvar").hide();
            $("#cpf").attr('disabled', 'disabled');
            $("#rg").attr('disabled', 'disabled');
            $("#rgOrgaoEmissor").attr('disabled', 'disabled');
            $("#rgUf").attr('disabled', 'disabled');
            $("#dataEmissaoRg").attr('disabled', 'disabled');
            $("#nome").attr('disabled', 'disabled');
            $("#dataNascimento").attr('disabled', 'disabled');
            $("#sexo").attr('disabled', 'disabled');
            $("#bairro").attr('disabled', 'disabled');
            $("#rua").attr('disabled', 'disabled');
            $("#numero").attr('disabled', 'disabled');
            $("#pontoReferencia").attr('disabled', 'disabled');
            $("#condominio").attr('disabled', 'disabled');
            $("#edificio").attr('disabled', 'disabled');
            $("#uf").attr('disabled', 'disabled');
            $("#pais").attr('disabled', 'disabled');
            $("#cartaoSus").attr('disabled', 'disabled');
            $("#nis").attr('disabled', 'disabled');
            $("#celular").attr('disabled', 'disabled');
            $("#tituloEleitoral").attr('disabled', 'disabled');
            $("#zona").attr('disabled', 'disabled');
            $("#secao").attr('disabled', 'disabled');
            $("#senha").attr('disabled', 'disabled');
            $("#senha2").attr('disabled', 'disabled');
            $("#ckPLiberados").attr('disabled', 'disabled');
            $("#ckMLiberadas").attr('disabled', 'disabled');
            $("#ckAcessaSistema").attr('disabled', 'disabled');
            $("#dependente").show();

            // let appElement = document.querySelector("#dependentes");
            // let button = document.createElement('button');
            // button.setAttribute('id', 'btnDependentes');
            // button.setAttribute('type', 'button');
            // button.setAttribute('class', 'btn btn-success right');
            // button.appendChild(document.createTextNode('Dependentes'));
            // appElement.appendChild(button);

            // let result = enviar(form, true, true, true)
            // return false;
        }
    });
    $("#formNovoDependente").submit(function(e){e.preventDefault();}).validate({
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
            $("#btnSalvarDep").click(function(){
                oTable.clear().draw();
                let aData = {"0": '<p>FILHO</p>',
                    "1": '<p>FULANO JUNIOR</p>',
                    "2": '<p>06/02/2004</p>',
                    "3": '<p>999.888.777-66</p>',
                    "4": '<button id="edicao" class="btn btn-warning">Editar</button>' + ' ' + '<button id="excluir" class="btn btn-danger">Excluir</button>',
                    "DT_RowId": "ln"+1};
                oTable.rows.add([aData]).draw();
            });

            $('#tbDependentes tbody').on( 'click', '.btn-danger', function() {
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