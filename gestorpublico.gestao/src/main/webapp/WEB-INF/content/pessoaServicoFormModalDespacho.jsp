<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    $('[data-toggle="popover"]').popover();
    $("#frmServicoDespachar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'autorizacao': {required: true},
        },
        submitHandler: function(form) {
            let result = enviar(form, false);
            if (result[0] == true) {
                removerLinhaDaTabela(result[1])
            }
            return false;
        }
    });
</script>
<div style="display: inline-block; width: 100%">
    <form id="frmServicoDespachar" action="servicoDespachar" method="post" class="form" role="form">
        <input hidden id="idPessoaServico" name="pessoaServico.id" value="${pessoaServico.id}" />
        <div class="row">
            <div class="form-group col-lg-12 col-md-12 padding-align">
                <label for="solicitante" class="control-label">Solicitante</label>
                <input id="solicitante" readonly class="form-control" value="${pessoaServico.solicitante.nome}" />
            </div>
            <div class="form-group col-lg-12 col-md-12 padding-align">
                <label for="servico" class="control-label">Benefício</label>
                <input id="servico" readonly class="form-control" value="${pessoaServico.servico.nome}" />
            </div>
            <div class="form-group col-lg-9 col-md-9 padding-align">
                <label for="observacao" class="control-label">Observação</label>
                <input id="observacao" data-container="body" data-toggle="popover" data-placement="bottom"
                       data-content="${pessoaServico.observacao}"readonly value="${pessoaServico.observacao}"
                       class="form-control cursor-pointer" />
            </div>
            <div class="form-group col-lg-3 col-md-3">
                <label for="autorizacao" class="control-label">Autorizado</label>
                <select id="autorizacao" name="autorizacao" class="form-control form-control-select">
                    <option></option>
                    <option value="true">Sim</option>
                    <option value="false">Não</option>
                </select>
            </div>
            <div class="form-group col-lg-12 col-md-12 padding-align">
                <label for="despacho" class="control-label">Despacho</label>
                <textarea id="despacho" name="despacho" class="form-control text-area" rows="5">${pessoaServico.despacho}</textarea>
            </div>
        </div>
    </form>
</div>
