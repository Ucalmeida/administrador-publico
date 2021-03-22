// -*- coding: utf-8 -*-

// function downloadDoc(id) {
// 	window.open("docMpDownload?id=" + id);
// }

function downloadDoc(tipo, nome) {
	window.open("docMpDownload?tipo="+tipo+"&fileName="+nome);
}

function novoBairro(idCidade) {
	if (idCidade > 0) {
		var md = bootbox.dialog({
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
					className: "btn-default",
				}
			}
		});
		$(".bootbox-body").load('bairroFormModalCadastrar', {'cidade.id': idCidade});
		md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
	} else {
		exibaMensagem("", "Selecione a cidade");
	}
}

function novoLogradouro(idBairro) {
	if (idBairro > 0) {
		var md = bootbox.dialog({
			message: 'Aguarde...',
			title: "Novo Logradouro",
			onEscape: true,
			buttons: {
				success: {
					label: "Salvar",
					className: "btn-primary",
					callback: function() {
						$("#frmRuaCadastrar").submit();
						return false;
					}
				},
				danger: {
					label: "Fechar",
					className: "btn-default",
				}
			}
		});
		$(".bootbox-body").load('ruaFormModalCadastrar', {'bairro.id': idBairro});
		md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
	} else {
		exibaMensagem("", "Selecione a bairro");
	}
}

function verificarSePolicialCadastrado(cpf) {
	var ag = aguarde("Aguarde...");
	$.ajax({async: false, cache: false, type: 'post',
		url: "getPolicialPorCPF",
		data: {'cpf': cpf},
		success: function(data, textStatus, XMLHttpRequest) {
			ag.modal("hide");
			if (Object.keys(data.objetos).length > 0)
				exibaMensagem("", "Policia já cadastrado", false, true);
		},
		error: function(data) {
			ag.modal("hide");
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

function policialApresentar(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Data da apresentação",
		onEscape: true,
		buttons: {
			success: {
				label: "Apresentar",
				className: "btn-primary",
				callback: function() {
					$("#frmPolicialApresentar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('policialFormModalApresentar');
	md.bind('shown.bs.modal', function(){md.find("#pusf").val(id);});
	md.bind('shown.bs.modal', function(){md.find("#nome").val($("tr#ln"+id).find("td").eq(1).html());});
	md.bind('shown.bs.modal', function(){md.find("#dataApresentacao").val(dataHoraAtual());});
	md.bind('shown.bs.modal', function(){md.find("#dataApresentacao").focus();});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function bolNovo() {
	var ag = aguarde("Aguarde...");
	$.ajax({async: false, cache: false, type: 'post',
		url: "bolCadastrar",
		success: function(data, textStatus, XMLHttpRequest) {
			ag.modal("hide");
			exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true);
		},
		error: function(data) {
			ag.modal("hide");
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

function bol(acao, id) {
	var url = "bol" + acao;
	var mensagem = "";
	var titulo = "";
	var reload = false;
	var msg = undefined;
	
	switch (acao) {
		case 'Assentar':
			mensagem = "Ao assentar este BOL, não será possível para o processo e todas as alterações nos prontuários dos militares serão realizadas.<br/>Tem certeza que deseja assentá-lo?";
			titulo = "Confirmar assentamento";
			reload = true;
			msg = "Assentamento sendo realizado...";
			break;
		case 'Fechar':
			mensagem = "Ao fechar este BOL, não será possível adicionar-lhe notas.<br/>Tem certeza que deseja fechá-lo?";
			titulo = "Confirmar fechamento";
			reload = true;
			break;
		case 'Reabrir':
			mensagem = "Tem certeza que deseja reabrir este BOL?";
			titulo = "Confirmar reabertura";
			reload = true;
			break;
	}
	
	var md = bootbox.dialog({
		message: mensagem,
		title: titulo,
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					$.blockUI();
					$.ajax({async: true, cache: false, type: 'post',
						url: url,
						data: {"bol.id": id},
						success: function(data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, reload);
						},
						error: function(data) {
							$.unblockUI();
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
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
}

function bolFormAssentar(id) {
	var bfa = bootbox.dialog({
		size: "small",
		message: "Aguarde...",
		title: "BOL Assentar",
		buttons: {
			success: {
				label: "Assentar",
				className: "btn-primary",
				callback: function() {
					$("#frmBolAssentar").submit();
					return false;
				}
			},
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('bolFormAssentar',{'bol.id': id}, function(response, status, xhr) {
		if (xhr.status == 409) {
			$(".bootbox-body").html(xhr.getResponseHeader('erro'));
		}
	});
	bfa.bind('shown.bs.modal', function(){bfa.find("#bgoReferencia").focus();});
}

function bolFormNovo() {
	var bfn = bootbox.dialog({
		size: "small",
		message: "Aguarde...",
		title: "Nova BOL",
		buttons: {
			danger: {
				label: "Cancelar",
				className: "btn-default",
			},
			success: {
				label: "Salvar",
				className: "btn-default",
				callback: function() {
					$("#frmBolCadastrar").submit();
					return false;
				}
			}
		}
	});
	$(".bootbox-body").load('bolFormNovo');
}

function alterarPolicialPatente(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados da promoção",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$("#frmBolNotaPromocaoAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('bolNotaPromocaoFormAlterar', {"policialPatente.id": id});
	$('.modal-content').draggable();
}

function admissaoAlterar(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados da admissão",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='fas fa-edit mr-1'></i>Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmPessoaAdmissaoAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	$(".bootbox-body").load('pessoaAdmissaoFormModalAlterar', {"pessoaAdmissao.id": id});
	$('.modal-content').draggable();
}

function licencaFeriasPublicadaCancelar(id) {
	var md = bootbox.dialog({
		title: "Cancelar férias publicada",
		message: "Tem certeza que deseja cancelar a publicação dessas férias?",
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					$.blockUI();
					$.ajax({type: 'post',
						url: "licencaFeriasPublicadaCancelar",
						data: {"licencaFerias.id": id},
						success: function(data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
							if (interceptador === "ok") {
								$("tr#ln"+id).addClass("transfCancelada");
								tabelaAtualizarDadosDaLinha(id,[8],["SIM"]);
								exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
							} else {
								exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
							}
						},
						error: function(data) {
							$.unblockUI();
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
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default"
			}
		}
	});
}

function inatividade(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados da Inatividade",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='fas fa-edit mr-1'></i>Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmPessoaInatividadeAlterar").submit();
					return false;
				}
			},
			cancelar: {
				label: "<i class='fas fa-ban mr-1'></i>Cancelar inatividade",
				className: "btn-danger",
				callback: function() {
					$.blockUI();
					$.ajax({async: true, cache: false, type: 'post',
						url: "pessoaAdmissaoCancelarInatividade",
						data: {"pessoaAdmissao.id": id},
						success: function(data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							var form = md.find("#frmPessoaInatividadeAlterar");
							$(form)[0].reset();
							tabelaAtualizarDadosDaLinha(id,[6,7,8,9,10,11,12],["","","","","","",$("#nvObservacao").val()]);
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
						},
						error: function(data) {
							$.unblockUI();
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
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	$(".bootbox-body").load('pessoaInatividadeFormModalAlterar', {"pessoaAdmissao.id": id});
	$('.modal-content').draggable();
}

function alterarLicencaFerias(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados das férias",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$("#frmBolNotaFeriasAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('bolNotaFeriasFormAlterar', {"licencasFerias.id": id});
	$('.modal-content').draggable();
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function alterarPessoaUnidadeSetorFuncaoPermuta(tipo, id) {
	var md = bootbox.dialog({
		size: "large",
		message: 'Aguarde...',
		title: "Alterar dados da permuta",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$("#frmPUSFPermutaAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('pusf'+tipo+'ModalAlterar', {"pusf.id": id});
	md.bind('shown.bs.modal', function(){md.find("#nLocal").focus();});
}

function alterarPessoaUnidadeSetorFuncao(tipo, id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados da transferência",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$("#frmPessoaUnidadeSetorFuncaoAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('pusf'+tipo+'ModalAlterar', {"pusf.id": id});
	md.bind('shown.bs.modal', function(){md.find("#nLocal").focus();});
}

function punicaoAlterar(id) {
	let md = bootbox.dialog({
		message: "Aguarde...",
		title: "Punição alterar",
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmPolicialPunicaoAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-danger",
			}
		}
	});
	$(".bootbox-body").load('policialPunicaoFormModalAlterar',{'policialPunicao.id': id});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

//function referenciarDocumento(id) {
//	let tipo = $("#tipoDocumento").val();
//	if (tipo == "") {
//		exibaMensagem("", "Selecione o Tipo de Documento");
//		return;
//	}
//	let idUnid = $("#idUnidade").val(); 
//	if (tipo == "BI" && (idUnid == "" || idUnid < 1)) {
//		exibaMensagem("", "Selecione a Unidade");
//		return;
//	}
//	let ano = $("#ano").val(); 
//	if (ano == null || ano == undefined || ano == "") {
//		exibaMensagem("", "Selecione o ano");
//		return;
//	}
//	let idDocu = $("#documento").val(); 
//	if (idDocu == undefined || idDocu == "" || idDocu < 1) {
//		exibaMensagem("", "Selecione o Documento");
//		return;
//	}
//	$.ajax({async: true, cache: false, type: 'post',
//		url: "comunicacaoInternaReferenciaCadastrar",
//		data: {"comunicacaoInterna.id": id, "arquivoUpload.id": idDocu},
//		success: function(data, textStatus, XMLHttpRequest) {
//			exibaMensagem("Sucesso!", XMLHttpRequest.getResponseHeader("msg"));
//			let idAU = $("#documento").val();
//			let nume = $("#documento option:selected").text().substring(0,12);
//			let aData = {"0": $("#tipoDocumento option:selected").text(),
//					"1": $("#idUnidade option:selected").text(),
//					"2": $("#ano option:selected").text(),
//					"3": '<a class="btn btn-default" onclick="javascript:downloadFileId('+idAU+')"><i class="far fa-file-pdf text-danger m-1"></i> '+nume+'</a>',
//					"4": '<a onclick="javascript:excluir(\'comunicacaoInternaReferencia\','+id+','+idAU+')" class="btn btn-default"><i class="red fa fa-trash"></i> Excluir</a>',
//					"DT_RowId": "ln"+id+idAU};
//			oTableRefencia.rows.add([aData]).draw();
//			$(form)[0].reset();
//		},
//		error: function(data) {
//			switch (data.status) {
//			case 409:
//				exibaMensagem("", data.getResponseHeader('erro'));
//				break;
//			default:
//				exibaMensagem("", "Ocorreu o seguinte erro: "+data.status+".<br/>Informe ao NTI.");
//			break;
//			}
//		}
//	});
//}
//
function relacionarAnexo() {
	var idSolicitacao = $("#idSolicitacao").val();
	if (idSolicitacao < 1) {
		exibaMensagem("", "Selecione a Solicitação");
		return;
	}
	var idTipoSolicitado = $("#idTipoSolicitado").val();
	if (idTipoSolicitado === undefined || idTipoSolicitado == "") {
		exibaMensagem("", "Informe a pessoa");
		return;
	}
	var idTipoAnexo = $("#idTipoAnexo").val();
	if (idTipoAnexo === undefined || idTipoAnexo == "") {
		exibaMensagem("", "Informe o tipo de anexo");
		return;
	}
	var anexos = tipos.get(idTipoSolicitado);
	if (anexos == null || anexos == undefined) {
		anexos = new Array();
	}
	if (anexos.indexOf(idTipoAnexo) == -1) {
		anexos.push(idTipoAnexo);
		tipos.set(idTipoSolicitado, anexos);
		
		var pessoa = $("#idTipoSolicitado option:selected").text();
		var tipoAnexoNome = $("#idTipoAnexo option:selected").text();
		var anexo = '<div id="div'+idTipoSolicitado+''+idTipoAnexo+'" class="divAnexos form-group col-lg-3 col-md-3 padding-align">' +
		'<input type="hidden" value="'+idTipoAnexo+'" /> '+
		'<input disabled value="'+pessoa+": "+tipoAnexoNome+'" class="jgCruzado form-control" />'+
		'<a class="btnExcluirJogo" onclick="javascript:relacionarAnexoExcluir('+idTipoSolicitado+','+idTipoAnexo+')">x</a>'+
		'</div>';
		$("#divTiposAnexo").append(anexo);
	} else {
		exibaMensagem("Ops!", "Você já adicionou esse tipo de anexo à essa pessoa.");
	}
}

function relacionarAnexoExcluir(idTipoSolicitado, idTipoAnexo) {
	var soli = ""+idTipoSolicitado;
	var anex = ""+idTipoAnexo;
	var anexos = tipos.get(soli);
	if (anexos != null || anexos != undefined) {
		var idx = anexos.indexOf(anex);
		anexos.splice(idx,1);
	}
	tipos.set(soli, anexos);
	$("#div"+soli+anex).remove();
}

function removerIncluir(id) {
	var trId = "#ln"+id;
	if ($(trId).length > 0) {
		var oTable = $(trId).parents("table").dataTable();
		oTable.fnDeleteRow(oTable.fnGetPosition($(trId)[0]));
	}
	boot.modal("hide");
	$("#removerIncluir").val(true);
	$("#formBolNotaTransferenciaCadastrar").submit();
}

function removerIncluirPermuta(id) {
	var trId = "#ln"+id;
	if ($(trId).length > 0) {
		var oTable = $(trId).parents("table").dataTable();
		oTable.fnDeleteRow(oTable.fnGetPosition($(trId)[0]));
	}
	boot.modal("hide");
	$("#removerIncluir").val(true);
	$("#formBolNotaTransferenciaPermutaCadastrar").submit();
}

function requerimentoDespachos(id) {
	var boot = bootbox.dialog({
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
	$(".bootbox-body").load('requerimentoDespachosModalVer', {'requerimento.id': id});
}

function requerimentoDespachar(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Despachar requerimento",
		buttons: {
			success: {
				label: "Despachar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmRequerimentoModalDespachar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFormModalDespachar', {"id": id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function requerimentoDevolver(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Devolver requerimento",
		buttons: {
			success: {
				label: "Devolver",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmRequerimentoDevolver").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFormModalDevolver', {"requerimento.id": id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function requerimentoFeriasDevolver(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Devolver requerimento",
		buttons: {
			success: {
				label: "Devolver",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmRequerimentoDevolver").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFeriasFormModalDevolver', {"requerimento.id": id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function requerimentoHomologar(id,exec) {
	let boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Homologar requerimento",
		buttons: {
			success: {
				label: "Homologar",
				className: "btn-primary",
				callback: function() {
					$("#frmRequerimentoHomologar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	if (exec != undefined && exec == true) {
		$(".bootbox-body").load('requerimentoFormModalHomologaExecuta');
	} else {
		$(".bootbox-body").load('requerimentoFormModalHomologa');
	}
	boot.bind('shown.bs.modal', function(){boot.find("#idRequerimento").val(id);});
}

function requerimentoRedirecionar(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Redirecionamento",
		buttons: {
			success: {
				label: "Encaminhar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmRequerimentoRedirecionar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoFormModalRedirecionar', {"requerimento.id": id});
}

function requerimentoVer(id, modal) {
	if (modal == undefined || modal) {
		let md = bootbox.dialog({
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
	} else {
		window.open("requerimentoVer?requerimento.id="+id, "_blank");
	}
}

function suspensaoFeriasAdicionarSetor() {
	let md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Férias exceção - Adcionar Setor",
		onEscape: true,
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-success",
				callback: function() {
					$("#frmSuspensaoFeriasExcecaoSetorAdicionar").submit();
					return false;
				}
			},
			cancel: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	md.find(".bootbox-body").load('suspensaoFeriasExcecaoSetorFormModalAdiciona');
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function suspensaoTransferenciaAdicionarSetor() {
	let md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Transferência exceção - Adcionar Setor",
		onEscape: true,
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-success",
				callback: function() {
					$("#frmSuspensaoTransferenciaExcecaoSetorAdicionar").submit();
					return false;
				}
			},
			cancel: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	md.find(".bootbox-body").load('suspensaoTransferenciaExcecaoSetorFormModalAdiciona');
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function notaModal(acao, id) {
	var md = bootbox.dialog({
		size: "large",
		message: "Aguarde...",
		title: "Nota Nº: ",
		buttons: {
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('bolNota'+acao, {'bolNota.id': id}, function(data, textStatus, XMLHttpRequest) {
		$(".bootbox H4").attr("class", "modal-title").text(XMLHttpRequest.getResponseHeader('titulo'));
	});
}

function nota(acao, id) {
	var url = "bolNota" + acao;
	var mensagem = "";
	var titulo = "";
	var dados = {"bolNota.id": id};
	var exibir = true;
	
	switch (acao) {
		case 'CancelarPublicacao':
			mensagem = "Tem certeza que deseja cancelar a publicação desta nota no BOL atual?";
			titulo = "Cancelar publicação";
			break;
		case 'Desomologar':
			mensagem = "Tem certeza que deseja desomologar está nota?";
			titulo = "Desomologar";
			break;
		case 'Devolver':
			mensagem = '<div class="row">  ' +
					        '<div class="form-group col-lg-12 padding"> ' +
						        '<textarea class="form-control focus" rows="5" id="despacho" name="despacho" placeholder="Informe o motivo da devolução" '+
						        	'class="form-control" required /> ' +
						    '</div> ' +
					   '</div>';
			titulo = "Motivo";
			break;
		case 'LiberarPublicacao':
			mensagem = "Tem certeza que deseja liberar a publicação desta nota?";
			titulo = "Liberar publicação";
			break;
		case 'LiberarPublicacaoFerias':
			mensagem = "Tem certeza que deseja liberar a publicação desta nota?";
			titulo = "Liberar publicação";
			break;
		case 'Publicar':
			mensagem = "Tem certeza que deseja incluir esta nota para publicação no BOL atual?";
			titulo = "Publicar";
			exibir = false;
			break;
		case 'SuspenderPublicacao':
			mensagem = "Tem certeza que deseja suspender a publicação desta nota?";
			titulo = "Suspender publicação";
			break;
	}
	var md = bootbox.dialog({
		message: mensagem,
		title: titulo,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Sim",
				className: "btn-primary",
				callback: function() {
					var result = "true";
					switch (acao) {
						case 'Devolver':
							var despacho = $("#despacho").val();
							if (despacho.length >= 10) {
								url = "bolNotaDevolver";
								dados = {'bolNota.id': id, 'despacho': despacho};
							} else {
								bootbox.alert("O motivo é obrigatório e com mínimo de 10 caracteres.");
								result = 'false';
							}
							if (result == "false") return false;
							break;
					}
					$.ajax({async: true, cache: false, type: 'post',
						url: url,
						data: dados,
						success: function(data, textStatus, XMLHttpRequest) {
							exibir ? exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true) : window.location.reload(); 
						},
						error: function(data) {
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
					return result == "true" ? true : false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function novaNota() {
	nn = bootbox.dialog({
		message: 'Aguarde...',
		title: "Nova nota",
		onEscape: true,
		buttons: {
			success: {
				label: "Criar",
				className: "btn-primary",
				callback: function() {
					$("#frmNovaNotaCadastrar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('bolNotaFormModalNova');
	nn.bind('shown.bs.modal', function(){nn.find("form :input:visible:enabled:not([readonly]):first").focus();});
}

function notaHomologar(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Homologação",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='fas fa-gavel mr-1'></i>Homologar",
				className: "btn-primary",
				callback: function() {
					$("#frmBolNotaHomologar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	$(".bootbox-body").load('homologarModalForm', {'bolNota.id': id});
	md.bind('shown.bs.modal', function(){md.find("form :input:visible:enabled:not([readonly]):first").focus();});
}

function permutaHomologar(id) {
	var md = bootbox.dialog({
		message: 'Tem certeza que deseja homologar essa permuta?',
		title: "Confirmar",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='fas fa-thumbs-up mr-1'></i>Autorizo",
				className: "btn-primary",
				callback: function() {
					$.blockUI();
					$.ajax({async: false, cache: false, type: 'post',
						url: "permutaHomologar",
						data: {"bolNota.id": id, "autorizacao": true},
						success: function(data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
							if (interceptador === "ok") {
								removerLinhaDaTabela(id)
								exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
							} else {
								exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
							}
						},
						error: function(data) {
							$.unblockUI();
							switch (data.status) {
							case 409:
								exibaMensagem("", data.getResponseHeader('erro'));
								break;
							default:
								exibaMensagem("", "Erro: "+data.status+".<br/>Informe ao NTI.");
							break;
							}
						}
					});
					return true;
				}
			},
			naoAutorizo: {
				label: "<i class='fas fa-thumbs-down mr-1'></i>Não autorizo",
				className: "btn-danger",
				callback: function() {
					$.blockUI();
					$.ajax({async: false, cache: false, type: 'post',
						url: "permutaHomologar",
						data: {"bolNota.id": id, "autorizacao": false},
						success: function(data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
							if (interceptador === "ok") {
								removerLinhaDaTabela(id)
								exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
							} else {
								exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
							}
						},
						error: function(data) {
							$.unblockUI();
							switch (data.status) {
							case 409:
								exibaMensagem("", data.getResponseHeader('erro'));
								break;
							default:
								exibaMensagem("", "Erro: "+data.status+".<br/>Informe ao NTI.");
							break;
							}
						}
					});
					return true;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
}

function planoFeriasAlterarMes(id) {
	var boot = bootbox.dialog({
		message: "Aguarde...",
		title: "Alterar mês",
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmRequerimentoPlanoFeriasMesAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoPlanoFeriasMesFormModalAlterar',{'requerimentoFerias.id': id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function verDevolucao(id) {
	$.ajax({
		async: true,
		cache: false,
		type: 'post',
		url: 'getBolNotaDevolucaoMotivo',
		data: {'bolNota.id': id},
		success: function(data) {
			bootbox.alert(data.objeto.mensagem);
		}
	});
}

function voluntario(acao, id) {
	$.blockUI();
	$.ajax({async: false, cache: false, type: 'post',
		url: "policialServicoVoluntario"+acao,
		data: {"id": id},
		success: function(data, textStatus, XMLHttpRequest) {
			$.unblockUI();
			var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
			if (interceptador === "ok") {
				var dados = "";
				if (acao == 'Escalar') {
					dados = ["Sim", '<a href="javascript:voluntario(\'Desescalar\','+id+')" class="btn btn-danger btn-sm"><i class="fas fa-ban mr-1"></i>Desescalar</a>'];
				} else {
					dados = ["Não", '<a href="javascript:voluntario(\'Escalar\','+id+')" class="btn btn-primary btn-sm"><i class="far fa-check-circle mr-1"></i>Escalar</a>'];
				}
				tabelaAtualizarDadosDaLinha(id, [4,6], dados);
				exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
			} else {
				exibaMensagem("", '<i class="far fa-trash-alt text-danger"></i> '+XMLHttpRequest.getResponseHeader('erro'));
			}
		},
		error: function(data) {
			$.unblockUI();
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

function bgoDownload(nome) {
	window.open("bgoDownload?fileName="+nome);
}

function dependenteAlterar(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar dados do dependente",
		onEscape: true,
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Alterar",
				className: "btn-primary",
				callback: function() {
					$("#frmPessoaDependenteAlterar").submit();
					return false;
				}
			},
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	$(".bootbox-body").load('pessoaDependenteFormModalAlterar', {"pessoaDependente.id": id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
	$('.modal-content').draggable();
}

function downloadCIAnexo(id) {
	window.open("downloadCIAnexo?id="+id);
}

function downloadFile(tipo, nome) {
	window.open("downloadFile?tipo="+tipo+"&nome="+nome);
}

function downloadUltimoBgo() {
	window.open("ultimoBgo");
}

function abrirUrl(url) {
	window.open(url, "_blank");
}

function abrirFicha(id) {
	window.open("ficha?id="+id, "_blank");
}

function abrirElogio(id) {
	window.open("bolNotaElogioFormAlterar?bne.id="+id, "_blank");
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
					$.blockUI();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'acessoBloquear',
						data: {'funcionario.idPessoa': id},
						success: function (data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), false, true);
						},
						error: function (data) {
							$.unblockUI();
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

function alterarSenha() {
	var mdAlterar = bootbox.dialog({
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

//function alterar(obj, id) {
//	mdAlterar = bootbox.dialog({
//		message: 'Aguarde...',
//		title: "Alterar " + obj,
//		size: "small",
//		onEscape: true,
//		buttons: {
//			success: {
//				label: "Alterar",
//				className: "btn-primary",
//				callback: function() {
//					$("#frmAlterar").submit();
//					return false;
//				}
//			},
//			danger: {
//				label: "Fechar",
//				className: "btn-default",
//			}
//		}
//	});
//	$(".bootbox-body").load('formModalAlterar', {'id': id, 'obj': obj});
//	mdAlterar.bind('shown.bs.modal', function(){mdAlterar.find("form").attr("action", obj+"Alterar");});
//	mdAlterar.bind('shown.bs.modal', function(){mdAlterar.find("form :input:visible:enabled:not([readonly]):first").focus();});
//}

function alterar(objeto, id, id2, largura) {
	var dados = {};
	var objet = objeto.split(".");
	objet[0] = objet[0].toLowerCase();
	objet = objet.toString().replace(/,/g, "");
	objeto = objeto.replace(/\./g, "");
	
	var obj = objet+".id";
	dados[obj] = id;
	largura = largura == undefined || largura == '' ? null : largura;
	
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Alterar",
		size: largura,
		onEscape: true,
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					$("#frm"+objeto+"Alterar").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load(objet+'FormModalAlterar', dados);
	md.bind('shown.bs.modal', function(){md.find("form :input:visible:enabled:first").focus();});
}

function novoTipoAnexo() {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Novo tipo de Anexo",
		size: "small",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmTipoAnexoCadastrar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('tipoAnexoFormModalCadastrar');
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
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

function excluir(especie, id, id2, mensagem, force, reload) {
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
								exibaMensagem("", '<i class="verde glyphicon glyphicon-ok"></i> ' + XMLHttpRequest.getResponseHeader('msg'), false, reload);
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
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
}

function anexoHomologacao(id) {
	var md = bootbox.dialog({
		message: 'Aguarde...',
		title: "Homologação de Anexo",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmPessoaSolicitacaoAnexoHomologacao").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('pessoaSolicitacaoAnexoFormModalHomologacao', {'psa.id': id});
	md.bind('shown.bs.modal', function(){md.find(".focus").focus();});
}

function anexoVer(id) {
//	var md = bootbox.dialog({
//		title: "Anexos",
//		message: '<object class="preview-pdf-file" type="application/pdf" data="anexoDownload?id='+id+'">This is not working as expected</object>',
//		"className" : "preview-pdf-modal",
//		size: "large",
//		onEscape: true,
//		buttons: {
////			success: {
////				label: "Alterar",
////				className: "btn-primary",
////				callback: function() {
////					$("#frmSenhaAlterar").submit();
////					return false;
////				}
////			},
//			danger: {
//				label: "Fechar",
//				className: "btn-default",
//			}
//		}
//	});
//	 $('.preview-pdf-modal .modal-content')
//     .draggable({
//     	cancel: ".preview-pdf-modal .preview-pdf-file"
//     })
//     .resizable({
//       minWidth: 600,
//       minHeight: 700,
//       alsoResize: '.preview-pdf-modal .bootbox-body'
//     });
//	$(".bootbox-body").find("#iPDF").html();
	window.open("anexoDownload?id="+id);
}

function atenderOS(id) {
	gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Atender O.S.",
		size: "large",
		className: 'col-lg-12',
		onEscape: true,
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#formOSAtender").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Sair",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('atendimentoOSModalExibir', {'ordemServico.id': id});
	gp.bind('shown.bs.modal', function(){gp.find("form :input:visible:enabled:first").focus();});
}

function transferirOS(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Transferir Ordem de Serviço",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "Transferir",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#formOStransferir").submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('transferirOSModalExibir', {'ordemServico.id': id});
	gp.bind('shown.bs.modal', function(){gp.find(".focus").focus();});
}

function homologacoesVer(id) {
	var mdHomo = bootbox.dialog({
		size: "xl",
		message: "Aguarde...",
		title: "Homologações",
		buttons: {
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-secondary",
			}
		}
	});
	mdHomo.find(".bootbox-body").load('homologacoesVer', {'bolNota.id': id});
}

function equipamentosOS(id) {
	gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Equipamento da O.S.",
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('equipamentoOSModalExibir', {'ordemServico.id': id});
	gp.bind('shown.bs.modal', function(){gp.find("form :input:visible:enabled:first").focus();});
}

function equipamentosEditar(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Editar Máquina",
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('maquinaModalExibir', {'material.id': id});
	gp.bind('shown.bs.modal', function(){gp.find("form :input:visible:enabled:first").focus();});
}


function comunicacaoInternaEncaminhar(id) {
	let boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Encaminhar Comunicação Interna",
		buttons: {
			success: {
				label: '<i class="fas fa-arrow-right"></i> Encaminhar',
				className: "btn-default",
				callback: function() {
					$('#frmComunicacaoInternaEncaminhar').submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('comunicacaoInternaFormModalEncaminhar', {'comunicacaoInterna.id': id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function comunicacaoInternaDespachar(id) {
	let boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Despachar Comunicação Interna",
		size: 'large',
		buttons: {
			success: {
				label: '<i class="fas fa-gavel"></i> Despachar',
				className: "btn-default",
				callback: function() {
					$('#frmComunicacaoInternaDespachar').submit();
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('comunicacaoInternaFormModalDespachar', {'comunicacaoInterna.id': id});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function comunicacaoInternaNova() {
	let boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Nova Comunicação Interna",
		buttons: {
			success: {
				label: '<i class="green fa fa-check" aria-hidden="true"></i> Criar',
				className: "btn-default",
				callback: function() {
					$('#frmComunicacaoIntermaCadastrar').submit();
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('ciFormModalCadastrar');
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function comprovanteOS(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Emitir comprovante",
		onEscape: true,
		buttons: {
			success: {
				label: "Imprimir",
				className: "btn-primary",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('comprovanteModalExibir', {'ordemServico.id': id});
	gp.bind('shown.bs.modal', function(){gp.find(".focus").focus();});
}

function comprovanteOSEntregar(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Emitir comprovante",
		onEscape: true,
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-default",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('comprovanteModalExibirEntregar', {'ordemServico.id': id});
	gp.bind('shown.bs.modal', function(){gp.find("form :input:visible:enabled:first").focus();});
}

function comunicacaoInternaVer(id, modal) {
	if (modal == undefined || modal) {
		let md = bootbox.dialog({
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
	} else {
		window.open("comunicacaoInternaVer?comunicacaoInterna.id="+id, "_blank");
	}
}

function creditoAlimenticioParecer(id) {
	let gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Emitir parecer",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-success",
				callback: function() {
					$('#frmParecer').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('requerimentoCreditoAlimenticioFormModalParecer', {'rcas.id': id});
	gp.bind('shown.bs.modal', function(){gp.find(".focus").focus();});
}

function historicoOS(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Parecer Técnico",
		size: "large",
		onEscape: true,
		buttons: {
			danger: {
				label: "Imprimir",
				className: "btn-primary",
				callback: function() {
//					if ($("#osVisita").val() == "true") {
//						exibaMensagem("", "Ordem de Serviço para Visita Técnica não tem parecer.");
//					} else {
						var win = window.open("ordemServicoParecerImprimir?ordemServico.id="+id, "_blank");
						win.print();
//					}
				}
			},
			success: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('historicoOSModalExibir', {'ordemServico.id': id});
}


function escolaCadastrar(policial) {
	var titulo = 'Cadastro de Escolas';
	var reload = false;
	boot = 
	bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-default",
				callback: function() {
					$('#frmEscola').submit();
					return false;
				}
			}, 
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('escolaFormNovo', {'policial.id': policial});
}


function excluir2(especie, id, id2, mensagem, force) {
	var url = especie+'Excluir';
	var dados = '';
	mensagem = mensagem === undefined ? "Tem certeza que deseja excluir?" : mensagem;
	force = force === undefined ? false : force;
	bootbox.dialog({
		message: mensagem,
		title: "Confirmar exclus&atilde;o",
		buttons: {
			success: {
				label: "Sim",
				className: "btn-default",
				callback: function() {
					switch (especie) {
						case 'dependente': dados = {'dependente.id': id};break;
						case 'filiacao': dados = {'filiacao.id': id, 'policial.idPessoa': id2};break;
						case 'escolaridade': dados = {'policialEscolaridade.id': id, 'policial.id': id2};break;
						case 'medalha': dados = {'policial.id': id, 'medalha.id': id2};break;
						case 'promocao': dados = {'policialPatente.id': id};break;
						case 'licencaFerias': dados = {'id': id};break;
						case 'licencaEspecial': dados = {'licencaEspecial.id': id};break;
						case 'averbacao': dados = {'averbacao.id': id};break;
						case 'restricao': dados = {'restricao.id': id};break;
					}
					$.ajax({
						url: url,
						type: 'post',
						data: dados,
						success: function() {
							var trId = id2 == undefined ? "#ln"+id : "#ln"+id+id2;
							var oTable = $(trId).parents("table").dataTable();
							oTable.fnDeleteRow(oTable.fnGetPosition($(trId)[0]));
							if (especie == "bolNota") {
								$("#spEmAberto").text($("#spEmAberto").text()-1);
							}
						},
						error: function(data) {
							var msg = data.getResponseHeader('erro');
							switch (data.status) {
							case 408:
								excluir(especie, id, id2, msg, true);
								break;
							default:
								exibaMensagem("", "Ocorreu o seguinte erro: " + msg + ".<br/>Favor informar ao NTI:<br/>Fone:(79)3226-7108 - 3226-7100 (ramal 208).");
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

function exportar() {
	window.open("arquivoExcelAjudanciaGerar", "_blank");
//	var ag = aguarde();
//	$.ajax({async: true, cache: false, type: 'post',
//		url: "arquivoExcelAjudanciaGerar",
//		success: function(data, textStatus, XMLHttpRequest) {
//			ag.modal("hide");
//			window.location.assign(XMLHttpRequest.getResponseHeader('arquivo'));
//		},
//		error: function(data) {
//			ag.modal("hide");
//			switch (data.status) {
//			case 409:
//				exibaMensagem("", data.getResponseHeader('erro'));
//				break;
//			default:
//				exibaMensagem("", "Ocorreu o seguinte erro: "+data.status+".<br/>Informe ao NTI.");
//			break;
//			}
//		}
//	});
}

//--------- Entrada de Máquinas ----------------
function entradaMaquina(unidade) {
	var url = 'entradaMaquinasFormNovo';
	var titulo = 'Cadastrar Máquinas';
	var reload = false;
	boot = 
	bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-default",
				callback: function() {
					$('#idFormNovoAutuacao').submit();
					return false;
				}
			},
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('entradaMaquinasFormNovo', {'unidade.id': unidade});
}

function adicionarRestricaoPolicial() {
	var url = 'policialRestricaoExibirModal';
	var titulo = 'Policiais com restrição';
	var reload = false;
	boot = 
	bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-default",
				callback: function() {
					$('#idFormNovoAutuacao').submit();
					return false;
				}
			},
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('policialRestricaoExibirModal');
}

function materialCadastrar() {
	var titulo = 'Cadastro de Material';
	var reload = false;
	boot = 
	bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Adicionar",
				className: "btn-primary",
				callback: function() {
					$('#formInserir1').submit();
					return false;
				}
			}, 
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Cancelar",
				className: "btn-default",
			}
		}
	});
	
	$('.bootbox-body').load('materialFormNovo');
}

function tipoMaterialCadastrarModal() {
	var titulo = 'Cadastrar tipo';
	var reload = false;
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					$('#formInserir1').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('tipoMaterialFormNovo');
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function marcaMaterialCadastrarModal() {
	var id = $("#idTipoMaterial").val()
	var titulo = 'Cadastrar marca';
	var reload = false;
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					$('#formInserir1').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('marcaMaterialFormNovo', {});
	boot.bind('shown.bs.modal', function(){boot.find("#idTipoMaterial1").val(id);});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function modeloMaterialCadastrarModal() {
//	var idTipo = $("#idTipoMaterial").val();
//	var idMarca = $("#idMarcaMaterial").val();
	var titulo = 'Cadastrar modelo';
	var reload = false;
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "Salvar",
				className: "btn-primary",
				callback: function() {
					$('#formInserir1').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('modeloMaterialFormNovo');
//	boot.bind('shown.bs.modal', function(){boot.find("#idTipoMaterial1").val(idTipo);});
//	boot.bind('shown.bs.modal', function(){boot.find("#idMarcaMaterial1").val(idMarca);});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function equipamentosTransferir(id) {
	var gp = bootbox.dialog({
		message: 'Aguarde...',
		title: "Transferir Máquina",
		size: "small",
		onEscape: true,
		buttons: {
			success: {
				label: "Alterar",
				className: "btn-default",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".modal-body").load('transferenciaMaterialModalExibir', {'material.id': id});
	gp.bind('shown.bs.modal', function(){gp.find("form :input:visible:enabled:first").focus();});
}

function feriasAlterar(id) {
	$("#frmFeriasPolicial").prop("action", "licencaFeriasAlterar");
	$("#idLicencaFerias").prop("value", id);
	$.post("getLicencaFerias", {'id': id}, function(data) {
		$("#idDataInicioFerias,#idDataTerminoFerias,#idQtdeDias").prop("disabled", "disabled");
		$("#feriasMajorado").val((data.objeto.majo ? 'true' : 'false'));
		$("#feriasRemunerada").val((data.objeto.remu ? 'true' : 'false'));
		$("#idDataInicioFerias").val(data.objeto.inic == null ? "" : data.objeto.inic.formateDataBR());
		$("#idDataTerminoFerias").val(data.objeto.term == null ? "" : data.objeto.term.formateDataBR());
		$("#idQtdeDias").val(data.objeto.dias);
		$("#idNumeroBGOFerias").val(data.objeto.nbgo);
		$("#idAnoBGOFerias").val(data.objeto.abgo);
		$("#idAnoReferenciaFerias").val(data.objeto.refe);
		$("#idTipoDocumentoFerias").val(data.objeto.tipo);
		$("#idObservacaoFerias").val(data.objeto.obse);
	});
}

function planoFeriasRequerimentoVerificado(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Requerimento",
		size: "small",
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					var verificado = $("#verificado").val();
					var ag = aguarde();
					$.ajax({type: 'post',
						url: "planoFeriasRequerimentoVerificado",
						data: {"requerimento.id": id, 'verificado': verificado},
						success: function(data, textStatus, XMLHttpRequest) {
							ag.modal("hide");
							if (verificado == "true") {
								var conf = XMLHttpRequest.getResponseHeader('conf')
								tabelaAtualizarDadosDaLinha(id, [6], ['<span title=\"'+conf+'\">Sim</span>']);
								$("tr#ln"+id).addClass("verificado");
							} else {
								tabelaAtualizarDadosDaLinha(id, [6], ['Não']);
								$("tr#ln"+id).removeClass("verificado");
							}
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
						},
						error: function(data) {
							ag.modal("hide");
							switch (data.status) {
							case 409:
								exibaMensagem("", data.getResponseHeader('erro'));
								break;
							default:
								exibaMensagem("", "Erro: "+data.status+".<br/>Informe ao NTI.");
							break;
							}
						}
					});
					return false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoPlanoFeriasFormModalVerificado');
	boot.bind('shown.bs.modal', function(){boot.find("#idRequerimento").val(id);});
	boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
}

function planoFeriasHomologar(ano, mes) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: "Homologar Plano de Férias",
		buttons: {
			success: {
				label: "Homologar",
				className: "btn-primary",
				callback: function() {
					var sair = false;
					$("#frmPlanoFeriasHomologar").submit();
					return sair == "true" ? true : false;
				}
			},
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('requerimentoPlanoFeriasFormModalHomologar', {'ano': ano, 'mes': mes});
}

function novoEndereco(policial) {
	var titulo = 'Novo endereço';
	var reload = false;
	boot = 
	bootbox.dialog({
		message: 'Aguarde...',
		title: titulo,
		size: "small",
		buttons: {
			success: {
				label: "Adicionar",
				className: "btn-default",
				callback: function() {
					$('#formAlterar').submit();
					return false;
				}
			}, 
			danger: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	
	$('.bootbox-body').load('enderecoFormNovo', {'policial.id': policial});
}

function policiaisElogiados(id) {
	var bb = bootbox.dialog({
		message: 'Aguarde...',
		title: "Elogiados",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('policiaisModalExibir', {'bolNotaElogio.id': id});
	bb.bind('shown.bs.modal', function(){bb.find("form :input:visible:enabled:first").focus();});
}

function guarnicaoPoliciais() {
	var bb = bootbox.dialog({
		message: 'Aguarde...',
		title: "Guarnição",
		size: "large",
		onEscape: true,
		buttons: {
			success: {
				label: "Fechar",
				className: "btn-default",
			}
		}
	});
	var id = $("#idBolNota").val();
	$(".bootbox-body").load('policiaisModalExibir', {'bolNotaElogio.id': id});
	bb.bind('shown.bs.modal', function(){bb.find("form :input:visible:enabled:first").focus();});
}

function novaPosicao(id) {
	var boot = bootbox.dialog({
		message: 'Aguarde...',
		title: 'Nova Posição',
		size: "small",
		buttons: {
			success: {
				label: "<i class='far fa-check-circle mr-1'></i>Adicionar",
				className: "btn-success",
				callback: function() {
					$("#formAlterar").submit();
					return false;
				}
			}, 
			danger: {
				label: "<i class='far fa-times-circle mr-1'></i>Fechar",
				className: "btn-default",
			}
		}
	});
	$('.bootbox-body').load('posicaoFormNova', {'pessoa.id': id});
}
