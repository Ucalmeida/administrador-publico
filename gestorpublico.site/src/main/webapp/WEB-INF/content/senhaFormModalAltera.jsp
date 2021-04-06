<%--
  Created by IntelliJ IDEA.
  User: Urian
  Date: 05/04/2021
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" charset="utf-8" src="/portal/js/md5.pack.js"></script>
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
<div class="padding-right" style="padding-left: 15px; display: inline-block;">
    <form id="frmSenhaAlterar" action="senhaAlterar" method="post" class="form" role="form">
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="senhaAtual" class="control-label">Senha atual</label>
            <input type="password" id="senhaAtual" name="senhaAtual" class="form-control" />
        </div>
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="novaSenha" class="control-label">Nova Senha</label>
            <input type="password" id="novaSenha" name="novaSenha" class="form-control" />
        </div>
        <div class="form-group col-lg-12 col-md-12 padding-align">
            <label for="confirmar" class="control-label">Confirme nova senha</label>
            <input type="password" id="confirmar" name="confirmar" class="form-control" />
        </div>
    </form>
</div>