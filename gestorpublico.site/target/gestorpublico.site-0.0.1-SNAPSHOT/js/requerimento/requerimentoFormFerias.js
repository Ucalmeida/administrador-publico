$(document).ready(function() {
	$("#anoReferencia").change(function(){
		let diasPadrao = $('#dias > option').map(function(){return this.value;}).get();
		let dias = this.selectedOptions[0].getAttribute("data-dias");
		if (diasPadrao.includes(dias)) {
			$("#dias").val(dias);
		} else {
			$("#dias").append('<option value="'+dias+'" selected >'+dias+'</option>');
		}
	});
	$("#frmRequerimentoFeriasRegulamentar").submit(function(e){e.preventDefault();}).validate({
		rules: {
			'anoReferencia': {required: true},
			'anoGozo': {required: true},
			'mesGozo': {required: true},
			'dias': {required: true}
		},
		submitHandler: function(form) {
			var result = enviar(form, false);
		}
	});
});	
