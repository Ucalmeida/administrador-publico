let total;
$(document).ready(function(){
	let width = $( window ).width();
	if (width < 990) {
		$("#divValor").addClass("offset-1");
	} else {
		$("#divValor").removeClass("offset-1");
	}
	$('[data-toggle="tooltip"]').tooltip();
	$(".fone").mask("(##)#####-##");
	$(".btn-num").on("click", function(){
		let num = this.text;
		let val = $("#sorteioValor").val();
		if ($(this).hasClass("selecionado")) {
			$(this).removeClass("selecionado");
			$("#ns"+num).remove();
		} else if (!$(this).hasClass("reserved") && !$(this).hasClass("paid")) {
			$(this).addClass("selecionado");
			$('<input type="hidden" id="ns'+num+'" name="numeros" value="'+num+'" />').appendTo("#frmReservar");
		}
		let nums = $("input[name='numeros']").length;
		let total = nums * val;
		$("#total").text("R$ " + total.toLocaleString('de-DE', {minimumFractionDigits: 2, maximumFractionDigits: 2}));
		if (nums > 0) {$("#divReservar").slideDown();} else {$("#divReservar").slideUp();}
	});
	$("#btnConfirmar").click(function(){
		let escolha = $("input[name='numeros']").toArray().map(item => item.value).join();
		$("#pTotal").text($("#total").text());
		$("#sNumeros").text(escolha);
	});
	$("#btnTodos").on("click", function(){
		$(".btn-num").parent("li").show();
	});
	$("#btnDisponiveis").on("click", function(){
		$(".btn-num").parent("li").show();
		$(".btn-num.paid").parent("li").hide();
		$(".btn-num.reserved").parent("li").hide();
	});
	$("#btnReservados").on("click", function(){
		$(".btn-num").parent("li").hide();
		$(".btn-num.reserved").parent("li").show();
	});
	$("#btnPagos").on("click", function(){
		$(".btn-num").parent("li").hide();
		$(".btn-num.paid").parent("li").show();
	});
	$("#btnMeus").on("click", function(){
		let boot = bootbox.dialog({
			title: "CONSULTAR RESERVA(S)",
			message: '<form id="frmReservas" action="minhasReservas" method="post" class="form" role="form">'+
						'<div class="form-group">'+
							'<label for="codigo" class="col-form-label">CÓDIGO DA SUA RESERVA</label>'+
							'<input type="text" name="codigo" class="focus form-control form-control-lg" id="codigo" placeholder="Digite o código da sua reserva" required minlength="11" />'+
						'</div></form>',
			onEscape: true,
			closeButton: false,
			buttons: {
				success: {
					label: "Consultar",
					className: "btn-success",
					callback: function() {
						let result = enviar($("#frmReservas"), false, true); 
						return false;
					}
				},
				danger: {
					label: "Fechar",
					className: "btn-light",
				}
			}
		});
		boot.find(".modal-content").addClass("bg-black");
		boot.find(".modal-content").css("border", "solid 1px #777777");
		boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
	});
	$("#btnComprovante").on("click", function(){
		let boot = bootbox.dialog({
			title: "Envio de Comprovante",
			message: '<form id="frmComprovante" action="enviarComprovante" method="post" class="form" role="form">'+
						'<div class="form-group">'+
							'<label for="codigo" class="col-form-label">CÓDIGO DA SUA RESERVA</label>'+
							'<input type="text" name="codigo" class="focus form-control form-control-lg" id="codigo" placeholder="Digite o código da sua reserva" required minlength="11" />'+
						'</div></form>',
			onEscape: true,
			closeButton: false,
			buttons: {
				success: {
					label: "Consultar",
					className: "btn-success",
					callback: function() {
						if ($("#codigo").val() == "") {
							exibaMensagem("Atenção", "Informe o código");
							return false;
						}
						let result = enviar($("#frmComprovante"), false);
					}
				},
				danger: {
					label: "Fechar",
					className: "btn-light",
				}
			}
		});
		boot.find(".modal-content").addClass("bg-black");
		boot.find(".modal-content").css("border", "solid 1px #777777");
		boot.bind('shown.bs.modal', function(){boot.find(".focus").focus();});
	});
	$("#frmReservar").submit(function(e){
		e.preventDefault();
		let result = enviar($("#frmReservar"), false, true, true);
	});
});