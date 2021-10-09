$(document).ready(function () {
    var $pagination = $("#pagination"),
            totalRecords = 0,
            records = [],
            recPorPag = 0,
            proxPagToken = "",
            totalPaginas = 0;
    var search = "";

    $("#myForm").submit(function (e) {
        e.preventDefault();

        search = $("#search").val();

        var url = `http://localhost:8080/aps-iso/rest/youtube/search/${search}`;

        $.ajax({
            method: "GET",
            url: url,
            beforeSend: function () {
                $("#btn").attr("disabled", true);
                $("#results").empty();
            },
            success: function (data) {
                console.log(data);
                $("#btn").attr("disabled", false);
                displayVideos(data);
            },
        });
    });

    function apply_pagination() {
        $pagination.twbsPagination({
            totalPages: totalPaginas,
            visiblePages: 6,
            onPageClick: function (event, page) {
                console.log(event);
                displayRecordsIndex = Math.max(page - 1, 0) * recPorPag;
                endRec = displayRecordsIndex + recPorPag;
                console.log(displayRecordsIndex + "ssssssssss" + endRec);
                displayRecords = records.slice(displayRecordsIndex, endRec);
                generateRecords(recPorPag, proxPagToken);
            },
        });
    }

    $("#search").change(function () {
        search = $("#search").val();
    });

    function generateRecords(recPerPage, nextPageToken) {
        var url2 = `http://localhost:8080/aps-iso/rest/youtube/search/${search}`;

        $.ajax({
            method: "GET",
            url: url2,
            beforeSend: function () {
                $("#btn").attr("disabled", true);
                $("#results").empty();
            },
            success: function (data) {
                console.log(data);
                $("#btn").attr("disabled", false);
                displayVideos(data);
            },
        });
    }

    function displayVideos(data) {
        recPorPag = data.pageInfo.resultsPerPage;
        proxPagToken = data.nextPageToken;
        console.log(records);
        totalRecords = data.pageInfo.totalResults;
        totalPaginas = Math.ceil(totalRecords / recPorPag);
        apply_pagination();
        $("#search").val("");

        var videoData = "";

        $("#table").show();

        data.items.forEach((item) => {
            videoData = `
                    
                    <tr>
                    <td>
                    <a target="_blank" href="https://www.youtube.com/watch?v=${item.id.videoId}">
                    ${item.snippet.title}</td>
                    <td>
                    <img width="200" height="200" src="${item.snippet.thumbnails.high.url}"/>
                    </td>
                    <td>
                    <a target="_blank" href="https://www.youtube.com/channel/${item.snippet.channelId}">${item.snippet.channelTitle}</a>
                    </td>
                    </tr>

                    `;

            $("#results").append(videoData);
        });
    }
});