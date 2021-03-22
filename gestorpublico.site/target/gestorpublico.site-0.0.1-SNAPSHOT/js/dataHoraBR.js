/**
 * DataTables internal date sorting replies on `Date.parse()` which is part of 
 * the Javascript language, but you may wish to sort on dates which is doesn't 
 * recognise. The following is a plug-in for sorting dates in the format 
 * `dd/mm/yy`.
 * 
 * An automatic type detection plug-in is available for this sorting plug-in.
 *
 * Please note that this plug-in is **deprecated*. The
 * [datetime](//datatables.net/blog/2014-12-18) plug-in provides enhanced
 * functionality and flexibility.
 *
 *  @name Date (dd/mm/YY HH:mm)
 *  @summary Sort dates in the format `dd/mm/YY HH:mm`
 *  @author Andy McMaster
 *  @alter Alex Costa - CASS Softwares
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'dataHoraBR', targets: 0 }
 *       ]
 *    } );
 */

 jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"dataHoraBR-pre": function ( a ) {
		if (a == null || a == "") {
			return 0;
		}
		var brDatea = a.split('/');
		var brHora = brDatea[2].split(" ")[1];
		brHora = brHora.split(":");
		brDatea[2] = brDatea[2].split(" ")[0];
		return (brDatea[2] + brDatea[1] + brDatea[0] + brHora[0] + brHora[1]) * 1;
	},

	"dataHoraBR-asc": function ( a, b ) {
		return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"dataHoraBR-desc": function ( a, b ) {
		return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
} );
