// Funcoes
function abrirUrl(url) {
	window.open(url, "_blank");
}

function alterarSenha() {
	mdAlterar = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar senha",
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmSenhaAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('senhaFormAlterar');
	mdAlterar.bind('shown.bs.modal', function(){mdAlterar.find("form :input:visible:enabled:not([readonly]):first").focus();});
}

function beneficioDespachar(id) {
	let box = bootbox.dialog({
		message: 'Aguarde...',
		title: "Benefício: Despachar solicitação",
		onEscape: true,
		buttons: {
			success: {
				label: "Despachar",
				className: "btn-success",
				callback: function() {
					$("#frmBeneficioDespachar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('beneficioFormDespachar', {'pessoaBeneficio.id': id});
	box.bind('shown.bs.modal', function(){box.find(".focus").focus();});
}

function servicoDespachar(id) {
	let box = bootbox.dialog({
		message: 'Aguarde...',
		title: "Serviço: Despachar Solicitação",
		onEscape: true,
		buttons: {
			success: {
				label: "Despachar",
				className: "btn-success",
				success: {
					label: "Despachar",
					className: "btn-success",
					callback: function() {
						$("#frmServicoDespachar").submit();
						return false;
					}
				},
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('servicoFormDespachar', {'pessoaServico.id': id});
	box.bind('shown.bs.modal', function(){box.find(".focus").focus();});
}

function excluir(especie, id, id2, mensagem, force) {
	var url = especie+'Excluir';
	var dados = {};
	mensagem = mensagem === undefined ? "Tem certeza que deseja excluir?" : mensagem;
	force = force === undefined ? false : force;
	bootbox.dialog({
		message: mensagem,
		title: "Confirmar exclus&atilde;o",
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					if (id2 > 0) {
						dados['id'] = id;
						dados['id2'] = id2;
					} else {
						dados['id'] = id;
					}
					$.ajax({
						url: url,
						type: 'post',
						data: dados,
						success: function(data, textStatus, XMLHttpRequest) {
							removerLinhaDaTabela((id2 == undefined ? id : ""+id+id2))
							exibaMensagem("", '<i class="verde glyphicon glyphicon-ok"></i> ' + XMLHttpRequest.getResponseHeader('msg'));
						},
						error: function(data) {
							var msg = data.getResponseHeader('erro');
							switch (data.status) {
							case 408:
								excluir(especie, id, id2, msg, true);
								break;
							case 409:
								exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> ' + msg);
								break;
							default:
								exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> Ocorreu o seguinte erro: ' + msg + '.<br/>Favor informar ao Analista.');
								break;
							}
						}
					});
				}
			},
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
}
