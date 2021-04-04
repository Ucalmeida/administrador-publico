$(document).ready(function() {
    let horarioSelecionado;
    let oTable = $("#tbConsultas").DataTable({
        "bPaginate" : true,
        "iDisplayLength": 10,
        "aoColumns": [
            {"sTitle": "Serviço"},
            {"sTitle": "Observação"},
            {"sTitle": "Ação"}]
    });
    $("#idEspecialidade").change(function () {
        let id = this.value;
        if (id > 0) {
            $("#divProfissionais").show();
        } else {
            $("#divProfissionais").hide();
        }
    });
    $(".btn-horario").on("click", function(){
        if ($(this).hasClass("btn-success")) {
            horarioSelecionado = null;
            $(this).removeClass("btn-success");
            $(this).addClass("btn-default");
        } else if (horarioSelecionado != null) {
            $(horarioSelecionado).removeClass("btn-success");
            $(horarioSelecionado).addClass("btn-default");
            $(this).removeClass("btn-default");
            $(this).addClass("btn-success");
            horarioSelecionado = this;
        } else {
            $(this).removeClass("btn-default");
            $(this).addClass("btn-success");
            horarioSelecionado = this;
        }
    });
    $("#frmConsultaAgendar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'servico.id': {required: true}
        },
        submitHandler: function(form) {
            let result = enviar(form, false, true);
            if (result[0] == true) {
                let id = result[1];
                let aData = {"0": $("#idServico option:selected").text(),
                    "1": $("#observacao").val(),
                    "2": '<a onclick="javascript:excluir(\'pessoaServico\','+id+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
                    "DT_RowId": "ln"+id};
                oTable.rows.add([aData]).draw();
                $(form)[0].reset();
                $('.focus').focus();
            }
            return false;
        }
    });
})