$(document).ready(function() {
	$("#frmRequerimentoNomeGuerra").submit(function(e){e.preventDefault();}).validate({
		rules: {
			'nomeGuerraNovo': {required: true}
		},
		submitHandler: function(form) {
			var result = enviar(form, false);
		}
	});
});	
