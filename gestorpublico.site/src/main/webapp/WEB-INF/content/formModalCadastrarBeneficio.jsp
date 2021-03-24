<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 24/03/2021
  Time: 01:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    let oTable = $("#tbBeneficios").DataTable({
        destroy: true,
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

    $("#formModalCadastrarBeneficio").submit(function (e) {
        e.preventDefault();
    }).validate({
        tooltip_options: {'_all_': {placement: 'top'}},
        rules: {
            // 'pessoa.cpf': {required: true},
            // 'pessoa.nome': {required: true},
            // 'pessoa.dataNascimento': {required: true},
            // 'sexo.id': {required: true},
            // 'pessoa.celular': {required: true},
            // 'pessoa.senha': {required: true},
        },
        submitHandler: function (form) {
            oTable.clear().draw();
            let aData = {
                "0": '<p>PEIXE</p>',
                "1": '<p>ENTREGA DE PEIXE</p>',
                "2": '<p>$$$</p>',
                "3": '<button id="excluir" class="btn btn-danger">Excluir</button>',
                "DT_RowId": "ln" + 1
            };
            let bData = {
                "0": '<p>AZEITE</p>',
                "1": '<p>ENTREGA DE AZEITE</p>',
                "2": '<p>$$$</p>',
                "3": '<button id="excluir" class="btn btn-danger">Excluir</button>',
                "DT_RowId": "ln" + 2
            };
            let cData = {
                "0": '<p>BOTIJÃO</p>',
                "1": '<p>ENTREGA DE BOTIJÃO</p>',
                "2": '<p>$$$</p>',
                "3": '<button id="excluir" class="btn btn-danger">Excluir</button>',
                "DT_RowId": "ln" + 3
            };
            oTable.rows.add([cData]).draw();
            oTable.rows.add([bData]).draw();
            oTable.rows.add([aData]).draw();

            $('#tbBeneficios tbody').on('click', '.btn-danger', function () {
                oTable
                    .row($(this).parents('tr'))
                    .remove()
                    .draw();
            });
            // let result = enviar(form, true, true, true)
            // return false;
        }

    });
</script>
<form id="formModalCadastrarBeneficio" action="beneficioCadastrar" method="post" class="form" role="form">
    <div id="dadosBeneficio" class="row">
        <div class="form-group col-lg-12">
            <label for="beneficio" class="control-label">Beneficios</label>
            <select id="beneficio" name="beneficio.id" class="form-control form-control-select form-control-width">
                <option value=""></option>
                <option value="1">AZEITE</option>
                <option value="2">FEIJÃO</option>
                <option value="3">BOTIJÃO</option>
                <option value="4">Exemplo 4</option>
                <option value="5">Exemplo 5</option>
            </select>
        </div>
    </div>
</form>