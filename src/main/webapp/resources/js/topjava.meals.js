const mealAjaxUrl = "ui/meals";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

function updateFilter() {
    $.ajax({
        type: "GET",
        url: "ui/meals/filter",
        data: $("#filterForm").serialize()
    }).done(updateTable);
}

$(function () {
    makeEditable({
        ajaxUrl: "ui/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                },
                {
                    "defaultContent": "Delete",
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
    });
});