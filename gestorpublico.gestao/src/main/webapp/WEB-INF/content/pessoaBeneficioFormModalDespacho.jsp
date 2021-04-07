<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    $("#frmSenhaAlterar").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'senhaAtual': {required: true},
            'novaSenha': {required: true,},
            'confirmar': {equalTo: "#novaSenha"},
        },
        messages:{
            senhaAtual: {
                required: 'Digite a senha atual',
            },
            novaSenha: {required: 'Digite uma senha nova segura'},
            confirmar: {equalTo: 'A nova senha não está igual'}
        },
        submitHandler: function(form) {
            enviar(form, true, true, true);
            return false;
        }
    });
</script>
<div style="padding-left: 15px; display: inline-block;">
    <form id="frmBeneficioDespachar" action="beneficioDespachar" method="post" class="form" role="form">
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="solicitante" class="control-label">Solicitante</label>
            <input id="solicitante" readonly class="form-control" value="${pessoaBeneficio.beneficiado.nome}" />
        </div>
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="beneficio" class="control-label">Benefício</label>
            <input id="beneficio" readonly class="form-control" value="${pessoaBeneficio.beneficio.nome}" />
        </div>
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="observacao" class="control-label">Observação</label>
            <textarea id="observacao" readonly class="form-control text-area" rows="5">${pessoaBeneficio.observacao}</textarea>
        </div>
        <div class="form-group col-lg-2 col-md-2">
            <label for="autorizacao" class="control-label">Autorizado</label>
            <select id="autorizacao" name="autorizacao" class="form-control form-control-select">
                <option></option>
                <option value="true">Sim</option>
                <option value="false">Não</option>
            </select>
        </div>
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="despacho" class="control-label">Despacho</label>
            <textarea id="despacho" class="form-control text-area" rows="5">${pessoaBeneficio.observacao}</textarea>
        </div>
    </form>
</div>
