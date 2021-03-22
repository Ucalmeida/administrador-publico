// -*- coding: utf-8 -*-
// CASS - FUNCOES UTEIS E GENERICAS

function exibaConfirmacao(titulo, mensagem, limpar, reload, fn, parametro) {
	bootbox.dialog({
		message: mensagem == "" ? "Operação realizada com sucesso." : mensagem,
		title: titulo == "" ? "" : titulo,
		buttons: {
			success: {
				label: "SIM",
				className: "btn-danger",
				callback: function() {
					if (limpar) {$('body form:first').each(function() {this.reset();$(':input:visible:enabled:first').focus();});}
					if (reload) window.location.reload();
					if (fn !== undefined) {
						fn(parametro);
					}
				}
			},
			danger: {
				label: "NÃO",
				className: "btn-default",
			}
		}
	});
}

function resetarSenha(id) {
	bootbox.dialog({
		message: 'Tem certeza que deseja resetar a senha dessa pessoa?',
		title: 'Resetar senha',
		onEscape: true,
		buttons: {
			success: {
				label: "Sim",
				className: "btn-primary",
				callback: function() {
					$.blockUI();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'senhaResetar',
						data: {'pessoa.id': id},
						success: function (data, textStatus, XMLHttpRequest) {
							$.unblockUI();
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
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

function download(fileName, id) {
	$.ajax({async: false, cache: false, type: 'post',
		url: 'downloadFile',
		data: {'fileName': fileName, "id": id},
		success: function(data) {
			var win = window.open("about:blank");
            with (win.document) {
                write(data);
                close();
            }
        },
		error: function(data) {
			exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI");
		}
	});
}

function aguarde(mensagem) {
	if (mensagem == undefined) mensagem = "Aguarde...";
	return bootbox.dialog({
		closeButton: false,
		size: "small",
		message: '<span id="aguardeAlert" class="glyphicon glyphicon-refresh glyphicon-refresh-animate" style:"font-size=20px"></span><span id="msgAguarde">'+mensagem+'</span>',
	});
}

function exibaMensagemGerarNota(titulo, mensagem, limpar, reload, ids) {
	bootbox.dialog({
		message: mensagem == "" ? "Operação realizada com sucesso." : mensagem,
		title: titulo == "" ? "" : titulo,
		buttons: {
			success: {
				label: "Abrir notas",
				className: "btn-success",
				callback: function() {
					if (limpar) {$('body form:first').each(function() {this.reset();$(':input:visible:enabled:first').focus();});}
					if (reload) window.location.reload();
					if (ids != null && ids !== undefined && ids != "") {
						ids = ids.split(",");
						$.each(ids, function(idx, id){
							window.open('bolNotaFormAlterar?bolNota.id='+id, "_blank");
						});
					}
				}
			},
			danger: {
				label: "OK",
				className: "btn-primary",
				callback: function() {
					if (limpar) {$('body form:first').each(function() {this.reset();$(':input:visible:enabled:first').focus();});}
					if (reload) window.location.reload();
				}
			}
		}
	});
}

function exibaMensagem(titulo, mensagem, limpar, reload, link, fecharJanela) {
	boot = bootbox.dialog({
		message: mensagem == "" ? "Operação realizada com sucesso." : mensagem,
		title: titulo == "" ? "" : titulo,
		buttons: {
			success: {
				label: "OK",
				className: "btn-primary",
				callback: function() {
					if (limpar) {$('body form:first').each(function() {this.reset();$(':input:visible:enabled:first').focus();});}
					if (reload) window.location.reload();
					if (link != null && link !== undefined && link != "") window.location.href = link;
					if (fecharJanela != null && fecharJanela !== undefined && fecharJanela != "" && fecharJanela == true) window.close;
				}
			}
		}
	});
}

function fecharModulo(idPessoa) {
	$.ajax({
		async: false,
		cache: false,
		type: 'post',
		url: 'fecharModulo',
		success: function(data, textStatus, XMLHttpRequest) {
			var url = XMLHttpRequest.getResponseHeader('cassURL');
			var site = XMLHttpRequest.getResponseHeader('siteURL');
			$.ajax({
				async: false,
				cache: false,
				type: 'post',
				url: site + 'logarDeModulo',
				data: {'pessoa.id': idPessoa},
				success: function() {
					window.location.href = url;
				},
				error: function(data) {
					window.location.href = site;
				}
			});
		},
		error: function(data, textStatus, XMLHttpRequest) {
			switch (data.status) {
			case 409:
				exibaMensagem("", data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI");
				break;
			}
		}
	});
}

function popularSelect2(selectPai, select, url, dados, idSelected) {
	$(select).empty();
	if ($(selectPai).val() > 0) {
		$.blockUI();
		$.ajax({
			url: url,
			type: 'post',
			data: dados,
			success: function(data, textStatus, XMLHttpRequest) {
				var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
				if (interceptador === "ok") {
					var objetos = data.objetos;
					if (objetos != undefined && Object.keys(objetos).length > 0) {
						$(select).append("<option></option>");
						$.each(data.objetos, function(key, o) {
							$(select).append('<option value="'+o.id+'">'+o.nome+'</option>');
						});
						if (idSelected !== undefined & idSelected != "") {
							if (idSelected > 0)
								$(select).val(idSelected);
							else
								$("#"+select.attr('id')+" option").filter(function() {return this.text == idSelected;}).attr('selected', true);
						}
					}
				} else {
					exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
				}
				$.unblockUI();
			},
			error: function(data) {
				$.unblockUI();
				exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro'));
			}
		});
	}
}

function popularSelect(select, url, dados, idSelected, vazio) {
	$.blockUI();
	$(select).empty();
	$.ajax({
		url: url,
		type: 'post',
		data: dados,
		success: function(data, textStatus, XMLHttpRequest) {
			var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
			if (interceptador === "ok") {
				var objetos = data.objetos;
				if (objetos != undefined && Object.keys(objetos).length > 0) {
					if (vazio != false) $(select).append("<option></option>");
					$.each(objetos, function(key, o) {
						$(select).append('<option value="'+o.id+'">'+o.nome+'</option>');
					});
					if (idSelected !== undefined & idSelected != "") {
						if (idSelected > 0)
							$(select).val(idSelected);
						else
							$("#"+select.attr('id')+" option").filter(function() {return this.text == idSelected;}).attr('selected', true);
					}
				}
			} else {
				exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
			}
			$.unblockUI();
		},
		error: function(data) {
			$.unblockUI();
			exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro'));
		}
	});
}

function execute(parametro) {
	if (typeof parametro === 'string') {
		$.blockUI();
		$.ajax({
			url: parametro,
			success: function(data, textStatus, XMLHttpRequest) {
				$.unblockUI();
				var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
				if (interceptador === "erro") {
					logar(parametro);
//					window.location.href = "formLogar"
				} else {
				    with (window.document) {open();write(data);history.pushState("", "", parametro);close();}
				}
			}
		});

	} else 	if (typeof parametro === 'function') {
		var argumentos = [];
		for (var x = 1; x < arguments.length; x++) {argumentos[x-1] = arguments[x];}
		switch (argumentos.length) {
			case 1:parametro.call(this, argumentos[0]);break;
			case 2:parametro.call(this, argumentos[0], argumentos[1]);break;
			case 3:parametro.call(this, argumentos[0], argumentos[1], argumentos[2]);break;
			case 4:parametro.call(this, argumentos[0], argumentos[1], argumentos[2], argumentos[3]);break;
			case 5:parametro.call(this, argumentos[0], argumentos[1], argumentos[2], argumentos[3], argumentos[4]);break;
			default:parametro.call(this);break;
		}
	} else {
		exibaMensagem("Função Execute", "Parâmetro incorreto. Verifique o parâmetro passado.");
	}
}

function logar(parametro) {
	param = parametro;
	params = arguments;
	var nv = bootbox.dialog({
		message: 'Aguarde...',
		title: "Logar",
		size: "small",
		buttons: {
			success: {
				label: "Logar",
				className: "btn-primary",
				callback: function() {
					$("#formLogar").submit();
					return false;
				}
			},
			danger: {
				label: "Cancelar",
				className: "btn-default",
			}
		}
	});
	$(".bootbox-body").load('formModalLogar');
	nv.bind('shown.bs.modal', function(){nv.find(".focus").focus();});
}

function logarModulo(modulo, idPessoa, idLocalTrabalho) {
	$.blockUI();
	$.ajax({
		async: false,
		cache: false,
		type: 'post',
		url: modulo+"/login",
		data: {'pessoa.id': idPessoa, 'ltb.id': idLocalTrabalho},
		success: function(data, textStatus, XMLHttpRequest) {
			$.unblockUI();
			var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
			if (interceptador === "erro") {
				logar(parametro);
			} else {
				window.location.href = modulo + XMLHttpRequest.getResponseHeader('url');
			}
		},
		error: function(data) {
			$.unblockUI();
			switch (data.status) {
			case 409:
				exibaMensagem("", data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI.");
				break;
			}
		}
	});
}

function sair() {
	$.ajax({
		async: false,
		type: 'post',
		url: 'logout',
		success: function(data, textStatus, XMLHttpRequest) {
			window.location = XMLHttpRequest.getResponseHeader('cassURL');
		},
		error: function (data) {
			switch (data.status) {
			case 409:
				exibaMensagem("", data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI");
				break;
			}
		}
	});
}

function enviar(form, limpar, exibirMensagem, reload) {
	if (limpar !== false) limpar = true;
	if (exibirMensagem !== false) exibirMensagem = true;
	if (reload !== true) reload = false;
	var result = [false, "", ""];
	var dados = new FormData($(form)[0]);
	var url = $(form).attr("action");
	$.ajax({async: false, cache: false, type: 'post', contentType: false, processData: false,
		url: url,
		data: dados,
		success: function (data, textStatus, XMLHttpRequest) {
			var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
			if (interceptador === "ok") {
				if (exibirMensagem == true) {
					exibaMensagem("", '<i class="verde glyphicon glyphicon-ok"></i> '+XMLHttpRequest.getResponseHeader('msg'), limpar, reload);
				} else {
					if (limpar) {
						$(form)[0].reset();
						$(':input:visible:enabled:first').focus();
					}
				}
				result[0] = true;
				result[1] = XMLHttpRequest.getResponseHeader('id');
				result[2] = data.resposta;
				result[3] = data;
			} else {
				exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+XMLHttpRequest.getResponseHeader('erro'));
			}
		},
		error: function (data) {
			switch (data.status) {
			case 409:
				exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> '+data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> Erro: '+data.getResponseHeader('erro')+". Favor informar ao NTI.", true, reload);
				break;
			}
		}
	});
	return result;
}

function enviar2(form, limpar, exibirMensagem, reload) {
	if (limpar !== false) limpar = true;
	if (exibirMensagem !== false) exibirMensagem = true;
	if (reload !== true) reload = false;
	var result = [false, ""];
	var dados = new FormData($(form)[0]);
	var url = $(form).attr("action");
	var ag = aguarde();
	
	$.ajax({async: false, cache: false, type: 'post', contentType: false, processData: false,
		url: url,
		data: dados,
		success: function (data, textStatus, XMLHttpRequest) {
			ag.modal("hide");
			if (exibirMensagem == true) {
				exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'), limpar, reload);
			} else {
				if (limpar) {$(form).each(function() {$(form).reset();$(':input:visible:enabled:first').focus();});}
				if (reload) window.location.reload();
			}
			result[0] = true;
			result[1] = XMLHttpRequest.getResponseHeader('id');
			result[2] = XMLHttpRequest.getResponseHeader('arquivo');
			result[3] = data.resposta;
		},
		error: function (data) {
			ag.modal("hide");
			switch (data.status) {
			case 409:
				exibaMensagem("", data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", "Ocorreu o seguinte erro: "+data.getResponseHeader('erro')+". Favor informar ao NTI:<br/>Fone:(79)3226-7108 - 3226-7100 (ramal 208).", true, reload);
				break;
			}
		}
	});
	return result;
}

function removerLinhaDaTabela(id) {
	var trId = "#ln"+id;
	var oTable = $(trId).parents("table").DataTable();
	$(trId).addClass("selected");
	oTable.rows('.selected').remove().draw();
}

function tabelaAtualizarBadge(id, complemento) {
	var total = $("#tb"+id).DataTable().rows().count();
	complemento = complemento !== undefined && complemento != "" ? " " + complemento : "";
	$("#badge"+id).text(total+complemento);
}

function tabelaAtualizarDadosDaLinha(id, celulas, dados) {
	var tr = "tr#ln"+id;
	for (var i = 0; i < celulas.length; i++) {
		$(tr).find("td").eq(celulas[i]).html(dados[i]);
	}
}

function localizarEnderecoPorCEP(cep) {
	cep = removerMascara(cep);
	if (cep != "" && cep.length == 8) {
		$.ajax({
			async: false,
			type: 'post',
			url: "http://cep.republicavirtual.com.br/web_cep.php",
			data: {'cep': cep, 'formato': 'jsonp', 'callback': "cep"},
			success: function(data) {
				if (data.resultado == '0')
					exibaMensagem("", "CEP n&atilde;o encontrado.");
				else {
					$("#idUF option").filter(function() {return this.text == data.uf;}).attr('selected', true);
					popularSelect($("#idCidade"), "ufGetCidades", {"uf.idUF": $("#idUF").val()}, data.cidade);
					$("#bairro").val(data.bairro);
					$("#logradouro").val(data.tipo_logradouro + " " + data.logradouro);
				}
			}
		});
	}
}

function validarCPF(cpf) {
	var digitsString = cpf.replace(/[^0-9]/g, '');
	var digits;
	var a,b,c,d,e,f,g,h,i,j,k;
	var dv1, dv2;
	var soma, resto;
	
	if (digitsString.length == 11) {
		digits = digitsString.split('');
		a = parseInt(digits[ 0 ]);
		b = parseInt(digits[ 1 ]);
		c = parseInt(digits[ 2 ]);
		d = parseInt(digits[ 3 ]);
		e = parseInt(digits[ 4 ]);
		f = parseInt(digits[ 5 ]);
		g = parseInt(digits[ 6 ]);
		h = parseInt(digits[ 7 ]);
		i = parseInt(digits[ 8 ]);
		j = parseInt(digits[ 9 ]);
		k = parseInt(digits[ 10 ]);
		soma = a*10 + b*9 + c*8 + d*7 + e*6 + f*5 + g*4 + h*3 + i*2;
		resto = soma % 11;
		dv1 = (11 - resto < 10 ? 11 - resto : 0);
		soma = a*11 + b*10 + c*9 + d*8 + e*7 + f*6 + g*5 + h*4 + i*3 + dv1*2;
		resto = soma % 11;
		dv2 = (11 - resto < 10 ? 11 - resto : 0);
		return dv1 == j && dv2 == k;
	}
	return false;
}

//function validarEmail(email) {
//	return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(email);	
//}

function horaAtual() {
	return new Date().toLocaleString().split(" ")[1];
}

function dataAtual() {
	return new Date().toLocaleString().split(" ")[0];
}

function dataHoraAtual() {
	var agora = new Date();
	var options = {year: "numeric", month: "numeric", day: "numeric",
	           hour: "numeric", minute: "numeric", hour12: false};
	return agora.toLocaleString("pt-BR", options);
}

function validarData(value) {
	//contando chars
	if(value.length != 10) return false;
	// verificando data
	var data        = value;
	var dia         = data.substr(0,2);
	var barra1      = data.substr(2,1);
	var mes         = data.substr(3,2);
	var barra2      = data.substr(5,1);
	var ano         = data.substr(6,4);
	if(data.length!=10||barra1!="/"||barra2!="/"||isNaN(dia)||isNaN(mes)||isNaN(ano)||dia>31||mes>12) return false;
	if((mes==4||mes==6||mes==9||mes==11) & dia==31)return false;
	if(mes==2 & (dia>29||(dia==29 & ano%4!=0))) return false;
	if(ano < 1900) return false;

	return true;
}

function validarHora(value) {
	//contando chars
	if(value.length!=5) return false;
	var horario     = value;
	var hora        = horario.substr(0,2);
	var doispontos  = horario.substr(2,1);
	var minuto      = horario.substr(3,2);
	if(horario.length!=5||isNaN(hora)||isNaN(minuto)||hora>23||minuto>59||doispontos!=":") return false;
	
	return true;
}

function removerMascara(valor) {
	if (valor == "") return "";
	var tokens = [".", "/", "-", "(", ")"];
	for ( var i = 0; i < tokens.length; i++) {
		var token = tokens[i];
		while (valor.indexOf(token) != -1) {
	 		valor = valor.replace(token, "");
		}
	}
	return valor; 
}

function getIndexNo(oTable, id) {
	var idx = oTable.fnGetPosition($("#"+id)[0]);
	return idx === undefined ? -1 : idx;
}

function capitalize(texto) {
	return texto.charAt(0).toUpperCase() + texto.slice(1);
}

$.fn.extend({limiter: function(limit, elem) {
	$(this).on("keyup focus", function() {setCount(this, elem);});
	function setCount(src, elem) {
		var chars = src.value.length;
		if (chars > limit) {
			src.value = src.value.substr(0, limit);
			chars = limit;
		}
		elem.html( limit - chars );
	}
	setCount($(this)[0], elem);}
});

$.fn.extend({maiusculo: function() {
	$(this).css("text-transform", "uppercase");
	$(this).keyup(function() {this.value = this.value.toUpperCase();});}
});

$.fn.extend({minusculo: function() {
	$(this).css("text-transform", "lowercase");
	$(this).keyup(function() {this.value = this.value.toLowerCase();});}
});

$.fn.extend({soLetra: function() {
	$(this).keyup(function() {this.value = this.value.replace(/[^A-z\.]/g,'');});}
});

$.fn.extend({soNumero: function() {
	$(this).keyup(function() {this.value = this.value.replace(/[^0-9\.]/g,'');});}
});

$.fn.extend({alfanumerico: function() {
	$(this).keyup(function() {this.value = this.value.replace(/[^a-zA-Z0-9]/g,'');});}
});

$.fn.extend({textopadrao: function(texto) {
	if ($(this).val() == "") $(this).val(texto);
	$(this).focus(function() {if ($(this).val() == texto) {$(this).val("");}});
	$(this).blur(function() {if ($(this).val().trim().length == 0) {$(this).val(texto);}});}
});

Array.prototype.consta = function (valor) {
	for (i in this) {
		if (this[i] == valor) return true;
	}
	return false;
};

Number.prototype.formatarNumero = function(numDecimais, numInteiros, separadorMilhar, separadorDecimal) {
	var n = numDecimais, x = numInteiros, s = separadorMilhar, c = separadorDecimal;
    var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\D' : '$') + ')',
        num = this.toFixed(Math.max(0, ~~n));

    return (c ? num.replace('.', c) : num).replace(new RegExp(re, 'g'), '$&' + (s || ','));
};

String.prototype.formateDataBR = function () {
	var data = this;
	if (data == "") return;
	data = data.substring(8,10)+"/"+data.substring(5,7)+"/"+data.substring(0,4);
	return data;
};

String.prototype.formateDataHoraBR = function () {
	var dataHora = this;
	if (dataHora == "") return;
	if (dataHora.includes("T")) {
		dataHora = dataHora.replace("T", " ");
	}
	var dataHora = dataHora.substring(8,10)+"/"+dataHora.substring(5,7)+"/"+dataHora.substring(0,4)+" "+dataHora.substring(11);
	return dataHora;
};

String.prototype.totalLinhas = function () {
	return this.split(/\r*\n/).length;
};

String.prototype.trim = function () {
	return this.replace(/^\s+|\s+$/g,"");
};

var Desenho = {
	_init : function(el) {
		Desenho.obj = el;
		Desenho.contexto = el.getContext("2d");
		Desenho.obj.onmousemove = Desenho._over;
		Desenho.obj.onmousedown = Desenho._ativa;
		Desenho.obj.onmouseup = Desenho._inativa;
		Desenho.obj.onselectstart = function() {
			return false;
		};
	},
	_over : function(e) {
		if (!Desenho.ativo)
			return;
		Desenho.contexto.beginPath();
		Desenho.contexto.lineTo(Desenho.x, Desenho.y);
		Desenho.contexto.lineTo(e.layerX, e.layerY);
		Desenho.contexto.stroke();
		Desenho.contexto.closePath();
		Desenho.x = e.layerX;
		Desenho.y = e.layerY;
	},
	_ativa : function(e) {
		Desenho.ativo = true;
		Desenho.x = e.layerX;
		Desenho.y = e.layerY;
	},
	_inativa : function() {
		Desenho.ativo = false;
	}
};
