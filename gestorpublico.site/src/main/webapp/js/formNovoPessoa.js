$(document).ready(function() {
    $("#senha2").blur(function() {
      let senha1 = $("#senha").val();
      let senha2 = $("#senha2").val();
      if(senha1 != senha2) {
          exibaMensagem("", "Senhas precisam ser iguais. Por favor, digite novamente");
      }
    });

    $("#formNovoPessoa").submit(function(e){e.preventDefault();}).validate({
        tooltip_options: {'_all_': { placement: 'top' }},
        rules: {
            'pessoa.cpf': {required: true},
            'pessoa.nome': {required: true},
            'pessoa.dataNascimento': {required: true},
            'sexo.id': {required: true},
            'pessoa.celular': {required: true},
            'pessoa.senha': {required: true},
        },
        submitHandler: function(form) {
            let result = enviar(form, true, true, true)
            return false;
        }
    });
})