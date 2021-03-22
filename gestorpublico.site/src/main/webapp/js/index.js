$(document).ready(function(){
	$("#login").keypress(function(event) {
		if (event.which==13) {
			$("#entrar").click();
		}
	});
	$("#senha").keypress(function(event) {
		if (event.which==13) {
			$("#entrar").click();
		}
	});
	$("#entrar").click(function() {
		$("#formLogin").submit();
	});
});

function logar(login, senha) {
	enviar()
}

function semUsuario() {
	window.alert("Caso nao tenha ou não saiba seu usuário procure a prefeitura.");
}
