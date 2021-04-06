// Funcoes
function getHorariosPorDataProfissional(id) {
	let card = $("#card" + id);
	if (card.find(".card-body").is(":visible")) {
		card.find(".card-body").hide();
	} else {
		$(".card-body").hide();
		card.find(".card-body").show(500);
	}
}

function referenciaRemover(){
	$("#idArquivoUpload").val("");
	$("#documentoReferencia").val("");
}

function downloadFileId(id) {
	window.open("downloadFile?id="+id);
}

function downloadFile(tipo, nome, unidade) {
	if (unidade === undefined) {
		window.open("downloadFile?tipo="+tipo+"&nome="+nome);
	} else {
		window.open("downloadFile?tipo="+tipo+"&nome="+nome+"&unidade.id="+unidade);
	}
}

function downloadDoc(tipo, nome, unidade) {
	if (unidade === undefined) {
		window.open("docDownload?tipo="+tipo+"&fileName="+nome);
	} else {
		window.open("docDownload?tipo="+tipo+"&fileName="+nome+"&unidade.id="+unidade);
	}
}

function downloadDocumento(tipo, nome) {
	window.open("docDownload?tipo="+tipo+"&fileName="+nome);
}

function cadastrarNovoBeneficio() {
	window.open("beneficios", "_blank");
}

function verBeneficiosPorPessoa() {
	window.open("beneficiosPorPessoa", "_blank");
}

function abrir(id) {
	window.open("apostaVer?aposta.idAposta="+id, "_blank");
}

function abrirFicha(id) {
	window.open("ficha?pessoa.idPessoa="+id, "_blank");
}

function abrirFicha2(id) {
	window.open("ficha2?id="+id, "_blank");
}

function abrirUrl(url) {
	window.open(url, "_blank");
}

function verUltimoBol() {
	window.open("ultimoBOL", "_blank");
}

function bloquearAcesso(id) {
	bootbox.dialog({
		message: 'Tem certeza que deseja bloquear esse acesso?',
		title: 'Bloquear acesso',
		onEscape: true,
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					var ag = aguarde();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'acessoBloquear',
						data: {'funcionario.idPessoa': id},
						success: function (data, textStatus, XMLHttpRequest) {
							ag.modal("hide");
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true);
						},
						error: function (data) {
							ag.modal("hide");
							switch (data.status) {
								case 409:
									exibaMensagem("", data.getResponseHeader('erro'));
									break;
								default:
									exibaMensagem("", "Ocorreu o seguinte erro: "+data.getResponseHeader('erro')+". Favor informar ao NTI.");
									break;
							}
						}
					});
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
}

function uploadAnexo(id) {
	let md = bootbox.dialog({
		title: "Upload de Anexo",
		message: 'Aguarde...',
		onEscape: true,
		buttons: {
			success: {
				label: "Upload",
				className: "btn-primary",
				callback: function() {
					$("#frmUploadAnexo").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('anexoUploadFormModal', {'id': id});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
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

function anexoLimpar(id) {
	bootbox.dialog({
		message: "Tem certeza que deseja remover o arquivo enviado para esse anexo?",
		title: "Limpar anexo",
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					$.ajax({type: 'post',
						url: "solicitacaoAnexoLimpar",
						data: {'id': id},
						success: function(data, textStatus, XMLHttpRequest) {
							exibaMensagem("", '<i class="verde glyphicon glyphicon-ok"></i> ' + XMLHttpRequest.getResponseHeader('msg'), false, true);
						},
						error: function(data) {
							exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> ' + data.getResponseHeader('erro'));
						}
					});
				}
			},
			danger: {
				label: "Não",
				className: "btn-default",
			}
		}
	});
}

function anexoVer(id) {
	window.open("psaDownload?id="+id);
}

function alterar(obj, id) {
	mdAlterar = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar " + obj,
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('formModalAlterar', {'id': id, 'obj': obj});
	mdAlterar.bind('shown.bs.modal', function(){mdAlterar.find("form").attr("action", obj+"Alterar");});
	mdAlterar.bind('shown.bs.modal', function(){mdAlterar.find("form :input:visible:enabled:not([readonly]):first").focus();});
}

function alterarContatos() {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Atualizar contatos",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i> Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmContatoAlterar").submit();
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i> Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('contatoFormModalAltera');
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function alterarEndereco() {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Atualizar endereço",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i> Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmEnderecoAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i> Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('enderecoFormModalAlterar');
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function atualizar(objeto, id, id2) {
	var dados = {};
	var obj = objeto.toLowerCase()+".id"+objeto;
	dados[obj] = id;
	var largura = objeto == "Forum" ? "large" : "small";

	modalAtualizar = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar " + objeto,
		size: largura,
		onEscape: true,
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					$("#frm"+objeto+"Atualizar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load(objeto.toLowerCase()+'ModalFormAtualizar', dados);
	modalAtualizar.bind('shown.bs.modal', function(){modalAtualizar.find("form :input:visible:enabled:first").focus();});
}

function novoBairro() {
	let idMunicipio = $("#nvMunicipio").val();
	idMunicipio = idMunicipio == undefined ? $("#idMunicipio").val() : idMunicipio;
	if (idMunicipio > 0) {
		let nvBairro = bootbox.dialog({
			message: 'Aguarde...',
			title: "Novo Bairro",
			onEscape: true,
			buttons: {
				success: {
					label: "Salvar",
					className: "btn-primary",
					callback: function() {
						$("#frmBairroCadastrar").submit();
						return false;
					}
				},
				danger: {
					label: "Fechar",
					className: "btn-default"
				}
			}
		});
		nvBairro.find(".bootbox-body").load('bairroFormModalCadastrar', {'municipio.id': idMunicipio});
		nvBairro.bind('shown.bs.modal', function(){nvBairro.find(".focus").focus();});
	} else {
		exibaMensagem("", "Selecione o Município");
	}
}

function novaRua() {
	let idBairro = $("#nvBairro").val();
	idBairro = idBairro == undefined ? $("#idBairro").val() : idBairro;
	if (idBairro > 0) {
		let nvLogra = bootbox.dialog({
			message: 'Aguarde...',
			title: "Nova Rua",
			onEscape: true,
			buttons: {
				success: {
					label: "<i class='far fa-check-circle mr-1'></i> Salvar",
					className: "btn-success",
					callback: function() {
						$("#frmRuaCadastrar").submit();
						return false;
					}
				},
				danger: {
					label: "<i class='far fa-times-circle mr-1'></i> Fechar",
					className: "btn-default"
				}
			}
		});
		nvLogra.find(".bootbox-body").load('ruaFormModalCadastrar', {'bairro.id': idBairro});
		nvLogra.bind('shown.bs.modal', function(){nvLogra.find("#nvCep").val($("#nCep").val());});
		nvLogra.bind('shown.bs.modal', function(){nvLogra.find(".focus").focus();});
	} else {
		exibaMensagem("", "Selecione a bairro");
	}
}

function novoSetor() {
	modalNovoSetor = bootbox.dialog({
		message: 'Aguarde...',
		title: "Novo Setor",
		size: "small",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmSetorCadastrar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('setorFormModalCadastrar');
	modalNovoSetor.bind('shown.bs.modal', function(){modalNovoSetor.find("#nome").focus();});
}

function excluir(especie, id, id2, mensagem, force) {
	var url = especie+'Excluir';
	var dados = {};
	mensagem = mensagem === undefined ? "Tem certeza que deseja excluir?" : mensagem;
	force = force === undefined ? false : force;
	bootbox.dialog({
		message: mensagem,
		title: "Confirmar exclusão",
		buttons: {
			success: {
				label: "<i class='far fa-trash-alt mr-1'></i>Excluir",
				className: "btn-danger",
				callback: function() {
					if (id2 > 0) {
						dados['id'] = id;
						dados['id2'] = id2;
						dados['excluirNaoVazia'] = force;
					} else {
						dados['id'] = id;
						dados['excluirNaoVazia'] = force;
					}
					$.ajax({
						url: url,
						type: 'post',
						data: dados,
						success: function(data, textStatus, XMLHttpRequest) {
							if (XMLHttpRequest.getResponseHeader('interceptador') == "ok") {
								removerLinhaDaTabela((id2 == undefined ? id : ""+id+id2))
								exibaMensagem("", '<i class="verde glyphicon glyphicon-ok"></i> ' + XMLHttpRequest.getResponseHeader('msg'));
								var badge = $(".badge-"+especie);
								$(badge).text($(badge).text()-1);
							} else {
								exibaMensagem("", '<i class="vermelho fa fa-ban"></i> ' + XMLHttpRequest.getResponseHeader('erro'));
							}
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
									exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> Ocorreu o seguinte erro: ' + msg + '.<br/>Favor informar ao NTI.');
									break;
							}
						}
					});
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Cancelar",
				className: "btn-secondary",
			}
		}
	});
}

function despachos(id) {
	bootbox.dialog({
		message: 'Aguarde...',
		title: "Despachos",
		size: "large",
		buttons: {
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoDespachosVer', {'requerimento.id': id});
}

function despachoSolicitacaoExcluir(id) {
	bootbox.dialog({
		message: 'Tem certeza que deseja cancelar essa solicitação de despacho?',
		title: 'Cancelar solicitação de despacho',
		onEscape: true,
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
//					var ag = aguarde();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'requerimentoSolicitacaoDespachoExcluir',
						data: {'requerimento.id': id},
						success: function (data, textStatus, XMLHttpRequest) {
//							ag.modal("hide");
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true);
						},
						error: function (data) {
//							ag.modal("hide");
							switch (data.status) {
								case 409:
									exibaMensagem("", data.getResponseHeader('erro'));
									break;
								default:
									exibaMensagem("", "Erro: "+data.getResponseHeader('erro')+". Favor informar ao NTI.");
									break;
							}
						}
					});
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
}

function cepNovo() {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Novo CEP",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i> Salvar",
				className: "btn-success",
				callback: function() {
					$("#frmCepCadastrar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i> Fechar",
				className: "btn-default",
			}
		}
	});
	md.find(".bootbox-body").load('cepFormModalCadastrar');
	md.bind('shown.bs.modal', function(){md.find("#nvCep").val($("#nCep").val());});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function dependenteExcluir(id) {
	bootbox.dialog({
		message: '<font color="red">Essa pessoa será excluída da lista de seus dependentes</font>.<br/>Tem certeza que deseja <b>EXCLUIR</b> esse dependente?',
		title: 'Excluir dependente',
		onEscape: true,
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					var ag = aguarde();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'pessoaDependenteExcluir',
						data: {'id': id},
						success: function (data, textStatus, XMLHttpRequest) {
							ag.modal("hide");
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true);
						},
						error: function (data) {
							ag.modal("hide");
							switch (data.status) {
								case 409:
									exibaMensagem("", data.getResponseHeader('erro'));
									break;
								default:
									exibaMensagem("", "Ocorreu o seguinte erro: "+data.getResponseHeader('erro')+". Favor informar ao NTI.");
									break;
							}
						}
					});
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
}

function documentoReferenciar(id) {
	mmd = bootbox.dialog({
		title: "Referenciar documento",
		message: 'Aguarde...',
		onEscape: true,
		buttons: {
			success: {
				label: "Referenciar",
				className: "btn-primary",
				callback: function() {
					$("#frmReferencia").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('documentoFormModalReferenciar',{"arquivoUpload.id": $("#idArquivoUpload").val()});
	mmd.bind('shown.bs.modal', function(){mmd.find(".focus").focus();});
}

function downloadArquivo(tipo, cpf) {
	window.open("downloadArquivo?tipo="+tipo+"&cpf="+cpf);
}

function downloadOtimizador() {
	window.open("downloadOtimizador");
}

function notificacaoCiente(id) {
	$.ajax({type: 'post',
		url: "pessoaNotificacaoCiente",
		data: {"id": id},
		success: function(data, textStatus, XMLHttpRequest) {
			window.location.reload();
		},
		error: function(data) {
			var msg = data.getResponseHeader('erro');
			switch (data.status) {
				case 409:
					exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> ' + msg);
					break;
				default:
					exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> Ocorreu o seguinte erro: ' + msg + '.<br/>Favor informar ao NTI.');
					break;
			}
		}
	});
}

function requerimentoSolicitarDespacho(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Solicitar despacho",
		buttons: {
			success: {
				label: "Solicitar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmRequerimentoModalDespachoSolicitar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFormModalDespachoSolicitar', {"id": id});
//	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function cadastrarBeneficio() {
	let cBeneficio = bootbox.dialog({
		message: 'Aguarde...',
		title: "Cadastrar Benefício",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-primary",
				callback: function() {
					$("#formModalCadastrarBeneficio").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-danger",
				callback: function(){
					// location.reload();
				}
			}
		}
	});
	cBeneficio.find(".bootbox-body").load('cadastrarBeneficio');
}

function requerimentoNovo() {
	boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Novo Requerimento",
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Criar",
				className: "btn-primary",
				callback: function() {
					const sair = false;
					$("#frmRequerimentoModalCadastrar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFormModalCadastrar');
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function requerimentoVer(id) {
	var md = bootbox.dialog({
		size: "large",
		message: "Aguarde...",
		title: "Requerimento",
		buttons: {
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	md.find(".bootbox-body").load('requerimentoVer', {'requerimento.id': id});
}

function solicitarTransporte(opcao) {
	var msg = 'Tem certeza que deseja ' + (opcao == true ? '<b>DESISTIR</b> do ' : '<b>SOLICITAR</b> o ') + 'transporte?';
	if (opcao == undefined) {
		msg = '<form class="form" role="form">' +
			'<div>' +
			'<div class="row padding-row padding-right">' +
			'<div class="col-md-12 col-lg-12 padding-left">' +
			'<div class="form-group">' +
			'<label for="opcao" class="control-label">Deseja transporte?</label>' +
			'<select id="opcao" name="requerTransporte" class="form-control form-control-select">' +
			'<option></option>' +
			'<option value=0>Não</option>' +
			'<option value=1>Sim</option>' +
			'</select>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</form>';
	}
	bootbox.dialog({
		message: msg,
		title: "Confirmar",
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Sim",
				className: "btn-success",
				callback: function() {
					opcao = msg.indexOf('DESISTIR') != -1 ? '0': '1';
					aguarde();
					$.ajax({async: false, cache: false, type: 'post',
						url: "solicitarTransporteAlterar",
						data: {'requerTransporte': opcao},
						success: function(data, textStatus, XMLHttpRequest) {
							$("#tbMinhaEscala tr").each(function(i, linha) {
								if (i > 0) {
									var texto = (opcao == true ? 'Sim' : 'Não') + ' <a href="javascript:solicitarTransporte('+opcao+')" class="btn btn-default" style="height: 25px; padding-top: 2px;"><i class="fa fa-undo"></i> Alterar</a>';
									$(linha).find('td:eq(5)').html(texto);
								}
							});
							window.location.reload();
						},
						error: function(data) {
							fecharAguarde();
							switch (data.status) {
								case 409:
									exibaMensagem("", data.getResponseHeader('erro'));
									break;
								default:
									exibaMensagem("", "Ocorreu o seguinte erro: "+data.status+".<br/>Informe ao NTI.");
									break;
							}
						}
					});
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Não",
				className: "btn-secondary",
			}
		}
	});
}
