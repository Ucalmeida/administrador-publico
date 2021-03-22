$(document).ready(function() {
	let oTable = $("#tbCaixa").DataTable({
		responsive : true,
		"paging":   false,
        "info":     false,
		"searching": false,
        "aoColumns": [{"sTitle": "Tipo"},
		              {"sTitle": "Bilhetes"},
		              {"sTitle": "Bruto"},
		              {"sTitle": "Comissão"},
		              {"sTitle": "Prêmio"},
		              {"sTitle": "Líquido"}],
		"footerCallback": function ( row, data, start, end, display ) {
            var api = this.api(), data;
 
            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ? i.replace(/[\.]/g, '').replace(/[\,]/g, '.')*1 : typeof i === 'number' ? i : 0;
            };
            
            // calcular total
            function total(coluna, tipo) {
            	return api.column( coluna ).data().reduce( function (a, b) {
            		a = typeof a === 'string' ? a.replace(/[\.]/g, '').replace(/[\,]/g, '.')*1 : typeof a === 'number' ? a : 0;
            		b = typeof b === 'string' ? b.replace(/[\.]/g, '').replace(/[\,]/g, '.')*1 : typeof b === 'number' ? b : 0;
            		if (tipo == "int") {
            			return intVal(a) + intVal(b)
            		} else {
            			var valor = parseFloat(a) + parseFloat(b);
            			return valor.formatarNumero(2,3,".",",")
            		}
            	}, 0 );
            }
            // Update footer
            $(api.column(1).footer()).html(total(1, "int"));
            $(api.column(2).footer()).html(total(2, "float"));
            $(api.column(3).footer()).html(total(3, "float"));
            $(api.column(4).footer()).html(total(4, "float"));
            $(api.column(5).footer()).html(total(5, "float"));
       	},
		columnDefs: [
			{type: 'dataHoraBR', targets: [3]},
			{className: "dt-body-center", targets: [1]},
			{className: "dt-body-right", targets: [2,3,4,5]}]
	});

	let oTable2 = $("#tbApostas").DataTable({
		responsive : true,
        "aoColumns": [{"sTitle": "Sorteio"},
		              {"sTitle": "Código"},
		              {"sTitle": "Número"},
		              {"sTitle": "Cliente"},
		              {"sTitle": "Status"},
		              {"sTitle": "Ação"}]
	});
	$("#frmCaixa").validate({
		tooltip_options: {'_all_': { placement: 'top' }},
		rules: {
			'inicio': {required: true, dateISO: true},
			'termino': {required: true, dateISO: true},
		}
	});
});
