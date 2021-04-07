<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    $('[data-toggle="popover"]').popover();
    $("#frmBeneficioDespachar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'autorizacao': {required: true},
        },
        submitHandler: function(form) {
            enviar(form, false);
            return false;
        }
    });
</script>
<div style="display: inline-block; width: 100%">
    <form id="frmBeneficioDespachar" action="beneficioDespachar" method="post" class="form" role="form">
        <div class="row">
            <div class="form-group col-lg-12 col-md-12 padding-align">
                <label for="solicitante" class="control-label">Solicitante</label>
                <input id="solicitante" readonly class="form-control" value="${pessoaBeneficio.beneficiado.nome}" />
            </div>
            <div class="form-group col-lg-12 col-md-12 padding-align">
                <label for="beneficio" class="control-label">Benefício</label>
                <input id="beneficio" readonly class="form-control" value="${pessoaBeneficio.beneficio.nome}" />
            </div>
            <div class="form-group col-lg-9 col-md-9 padding-align">
                <label for="observacao" class="control-label">Observação</label>
                <input id="observacao" data-container="body" data-toggle="popover" data-placement="bottom"
                       data-content="${pessoaBeneficio.observacao}"readonly value="${pessoaBeneficio.observacao}"
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
                <textarea id="despacho" class="form-control text-area" rows="5">${pessoaBeneficio.despacho}</textarea>
            </div>
        </div>
    </form>
</div>
