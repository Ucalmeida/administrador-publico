$.extend( true, $.fn.dataTable.defaults, {
    lengthChange: false,
    processing: true,
    order: [[0,"desc"]],
    language: {
        url: '/portal/plugins/dataTables/Portuguese-Brasil.json',
    },
    lengthMenu: [
        [ 10, 25, 50, 100, -1 ],
        [ '10', '25', '50', '100', 'Todas' ]
    ],
    buttons: [ 'copy',
        {
            extend: 'print',
            autoPrint: false,
            exportOptions: {
                columns: ':visible'
            }
        },
        {
            extend: 'pdf',
            exportOptions: {
                columns: ':visible'
            }
        },
        {
            extend: 'csv',
            exportOptions: {
                columns: ':visible'
            }
        },
        {
            extend: 'excelHtml5',
            exportOptions: {
                columns: ':visible'
            }
        },
        'colvis',
        'pageLength',
    ],
    dom:
        "<'row'<'col-sm-8'B><'col-sm-1 text-center'l><'col-sm-3'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
} );