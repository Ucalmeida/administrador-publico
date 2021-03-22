$(document).ready(function() {
	var oTableLocalizar = $("#tbLocalizar").DataTable({
		"bPaginate" : false,
		"iDisplayLength": 10,
		"aoColumns": [
			{"sTitle": "Policial"},
			{"sTitle": "Número"},
            {"sTitle": "Tipo"}],
        "searching": false
	});
	oTableAbertas = $("#tbAbertos").DataTable({
		"bPaginate" : true,
		"iDisplayLength": 10,
		"order": [[0,"desc"]],
        "aoColumns": [
        	{"sTitle": "Número"},
            {"sTitle": "Tipo"},
            {"sTitle": "Anexos"},
            {"sTitle": "Status"},
            {"sTitle": "A&ccedil;&atilde;o"}]
	});
	var oTableAcompanha = $("#tbAcompanha").DataTable({
		"bPaginate" : true,
		"iDisplayLength": 10,
		"aoColumns": [
			{"sTitle": "Número"},
            {"sTitle": "Tipo"},
            {"sTitle": "Anexos"},
            {"sTitle": "Enviado em"},
            {"sTitle": "De"},
            {"sTitle": "Para"},
            {"sTitle": "Status"},
            {"sTitle": "A&ccedil;&atilde;o"}]
	});
	$("#frmPolicialNotaLocalizar").submit(function(e){e.preventDefault();}).validate({
		rules: {
			'nome': {required: true, haValor: "#idPolicial"}
		},
		submitHandler: function(form) {
			$.blockUI();
			$.ajax({async: false, cache: false, type: "post", dataType: "json",
				url: $(form).attr("action"), 
				data: $(form).serialize(),
				success: function(data, textStatus, XMLHttpRequest) {
					var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
					if (interceptador === "ok") {
						var objetos = data.objetos;
						if (objetos != null && objetos != undefined && Object.keys(objetos).length > 0) {
							$.each(objetos, function(key, o) {
								var aData = {0: $("#nome").val(),
										"1": '<a href="javascript:notaModal(\'Ver\','+o.id+')" class="btn btn-default btn-sm"><i class="fas fa-eye text-info mr-2"></i> '+o.numero+'</a>',
										"2": o.tipo,
										"DT_RowId": "ln"+o.id};
								oTableLocalizar.fnAddData(aData);
								oTableHomologar.search(o.numero).draw();
								$("#collapseTwo").addClass("in");
							});
							$(form)[0].reset();
							$("#nome").focus();
						} else {
							$(form)[0].reset();
							exibaMensagem("", "Nenhuma nota encontrada com esse Policial.");
						}
					} else {
						exibaMensagem("", '<i class="text-danger far fa-trash-alt"></i> '+XMLHttpRequest.getResponseHeader('erro'));
					}
					$.unblockUI();
				},
				error: function(data) {
					$.unblockUI();
					exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro'));
				}
			});
		}
	});
});	
