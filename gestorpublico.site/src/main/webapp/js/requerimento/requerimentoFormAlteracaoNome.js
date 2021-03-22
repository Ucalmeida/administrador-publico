$(document).ready(function() {
	$("#frmRequerimentoAlteracaoNome").submit(function(e){e.preventDefault();}).validate({
		rules: {
			'nomeNovo': {required: true}
		},
		submitHandler: function(form) {
			var result = enviar(form, false);
		}
	});
});	

function arquivo(path) {
	var nome = extraindoNomeArquivo(path);
	document.getElementById('nomeArquivo').textContent = " " + nome;
}

function extraindoNomeArquivo(path) {
	if (path.substr(0, 11) == "C:\\fakepath\\") {
		return path.substr(11);
	}
	var x = path.lastIndexOf('\\');
	if (x >= 0) {
		return path.substr(x + 1); // Windows- Caminho base
	}
	return path;
}
