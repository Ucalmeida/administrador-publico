// CASS - FUNCOES UTEIS E GENERICAS

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
					var ag = aguarde();
					$.ajax({async: false, cache: false, type: 'post',
						url: 'senhaResetar',
						data: {'pessoa.id': id},
						success: function (data, textStatus, XMLHttpRequest) {
							ag.modal("hide");
							exibaMensagem("", XMLHttpRequest.getResponseHeader('msg'));
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

function download(fileName) {
	$.ajax({async: false, cache: false, type: 'post',
		url: 'downloadFile',
		data: {'fileName': fileName},
		success: function(data) {
			var win = window.open("about:blank");
			with(win.document) {write(data);close();};
		},
		error: function(data) {
			exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI");
		}
	});
}

function aguardeProgresso(mensagem) {
	if (mensagem == undefined) mensagem = "Aguarde...";
	return bootbox.dialog({
		closeButton: false,
		size: "small",
		message: '<span id="aguardeAlert" class="glyphicon glyphicon-refresh glyphicon-refresh-animate" style:"font-size=20px"></span><span id="msgAguarde"><br/><div class="progress"></div>'+mensagem+'</span>',
	});
}

function aguarde(mensagem) {
	if (mensagem == undefined) mensagem = "Aguarde...";
	return bootbox.dialog({
		closeButton: false,
		size: "small",
		message: '<span id="msgAguarde"><i class="fa fa-spinner fa-spin"></i> '+mensagem+'</span>',
	});
}

function exibaMensagem(titulo, mensagem, limpar, reload, link) {
	let a = bootbox.dialog({
		message: mensagem == "" ? "Operação realizada com sucesso." : mensagem,
		title: titulo == "" ? "" : titulo,
		closeButton: false,
		buttons: {
			success: {
				label: "OK",
				className: "btn-primary",
				callback: function() {
					if (limpar) {$('body form:first').each(function() {this.reset();$(':input:visible:enabled:first').focus();});}
					if (reload) window.location.reload();
					if (link != null && link !== undefined && link != "") window.location.href = link;
				}
			}
		}
	});
	a.find(".modal-content").css("color", "black");
}

function fecharModulo(idPessoaAcesso) {
	$.ajax({
		async: false,
		cache: false,
		type: 'post',
		url: 'fecharModulo',
		success: function(data, textStatus, XMLHttpRequest) {
			var url = XMLHttpRequest.getResponseHeader('cassURL');
			$.ajax({
				async: false,
				cache: false,
				type: 'post',
				url: url + '/logarDeModulo',
				data: {'pessoaAcesso.idPessoaAcesso': idPessoaAcesso},
				success: function() {
					window.location.href = url;
				},
				error: function(data) {
					exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro') + "<br/>Informe ao NTI");
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
		var agd = aguarde();
		$.ajax({
			url: url,
			type: 'post',
			data: dados,
			success: function(data) {
				if (Object.keys(data.objetos).length > 0) {
					$(select).append("<option></option>");
					$.each(data.objetos, function(key, o) {
						$(select).append('<option value="'+o.id+'">'+o.nome+'</option>');
					});
					if (idSelected !== undefined & idSelected != "") {
						if (idSelected > 0)
							$(select).val(idSelected)
						else
							$("#"+select.attr('id')+" option").filter(function() {return this.text == idSelected;}).attr('selected', true);
					}

				}
				agd.modal("hide");
			},
			error: function(data) {
				agd.modal("hide");
				exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro'));
			}
		});
	}
}

function popularSelect(select, url, dados, idSelected) {
	var agd = aguarde();
	$(select).empty();
	$.ajax({
		url: url,
		type: 'post',
		data: dados,
		success: function(data) {
			if (Object.keys(data.objetos).length > 0) {
				$(select).append("<option></option>");
				$.each(data.objetos, function(key, o) {
					$(select).append('<option value="'+o.id+'">'+o.nome+'</option>');
				});
				if (idSelected !== undefined & idSelected != "") {
					if (idSelected > 0)
						$(select).val(idSelected)
					else
						$("#"+select.attr('id')+" option").filter(function() {return this.text == idSelected;}).attr('selected', true);
				}
				
			}
			agd.modal("hide");
		},
		error: function(data) {
			agd.modal("hide");
			exibaMensagem("", "Ocorreu o seguinte erro: " + data.getResponseHeader('erro'));
		}
	});
}

function execute(parametro) {
	if (typeof parametro === 'string') {
		let ag = aguarde();
		$.ajax({
			url: parametro,
			success: function(data, textStatus, XMLHttpRequest) {
				ag.modal("hide");
				var interceptador = XMLHttpRequest.getResponseHeader('interceptador');
				if (interceptador === "erro") {
//					logar(parametro);
					window.location.href = "formLogar"
				} else {
				    with (window.document) {open();write(data);history.pushState("", "", parametro);close();}
				}
			}
		});
		return;
	} else {
		exibaMensagem("Função Execute", "Parâmetro incorreto. Verifique o parâmetro passado.");
	}
}

function logar(parametro) {
	var param = parametro;
	var params = arguments;
	var nv = bootbox.dialog({
		message: 'Aguarde...',
		title: "Logar",
		size: "small",
		buttons: {
			danger: {
				label: "Cancelar",
				className: "btn-default",
			},
			success: {
				label: "Logar",
				className: "btn-primary",
				callback: function() {
					$("#formLogar").submit();
					return false;
				}
			}
		}
	});
	$(".bootbox-body").load('formLogar');
	nv.bind('shown.bs.modal', function(){nv.find("#login").focus();});
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
	var result = new Array(false, "", "");
	var dados = new FormData($(form)[0]);
	var url = $(form).attr("action");
	$.blockUI();
	
	$.ajax({async: false, cache: false, type: 'post', contentType: false, processData: false,
		url: url,
		data: dados,
		success: function (data, textStatus, XMLHttpRequest) {
			$.unblockUI();
			if (exibirMensagem == true) {
				exibaMensagem("", '<i class="verde fa fa-check"></i> '+XMLHttpRequest.getResponseHeader('msg'), limpar, reload);
			} else {
				if (limpar) {
					$(form)[0].reset();
					$(':input:visible:enabled:first').focus();
				}
			}
			result[0] = true;
			result[1] = XMLHttpRequest.getResponseHeader('id');
			result[2] = data.resposta;
		},
		error: function (data) {
			$.unblockUI();
			switch (data.status) {
			case 409:
				exibaMensagem("", '<i class="vermelho fa fa-times"></i> '+data.getResponseHeader('erro'));
				break;
			default:
				exibaMensagem("", '<i class="vermelho glyphicon glyphicon-remove"></i> Ocorreu o seguinte erro: '+data.getResponseHeader('erro')+". Favor informar ao NTI.", true, reload);
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

function validarEmail(email) {
	if (email == "") return false;
    const re = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/;
    return re.test(String(email).toLowerCase());
} 

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

function validarDataUSA(value) {
	//contando chars
	if(value.length != 10) return false;
	// verificando data
	var data        = value;
	var ano         = data.substr(0,4);
	var barra1      = data.substr(4,1);
	var mes         = data.substr(5,2);
	var barra2      = data.substr(7,1);
	var dia         = data.substr(8,2);
	if(data.length!=10||barra1!="-"||barra2!="-"||isNaN(dia)||isNaN(mes)||isNaN(ano)||dia>31||mes>12) return false;
	if((mes==4||mes==6||mes==9||mes==11) & dia==31)return false;
	if(mes==2 & (dia>29||(dia==29 & ano%4!=0))) return false;
	if(ano < 1900) return false;

	return true;
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

function dinheiro(valor) {
    return valor.match("\\d{1,3}(\\.\\d{3})*(,\\d{2})?");
}

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

