//게시글 목록
function getList(params, currentPage) {
    $.ajax({
        url : '/listAjax?currentPage=' + currentPage,
        type : 'post',
        data: params,
        success: function (result) {
            let boardHtml = "";

            let list = result.list;
            let paging = result.paging;
            //테이블 draw
            for (let i = 0; i < list.length; i++) {
                    boardHtml += "<tr>";
                    boardHtml += "<td>";
                    boardHtml += "<a href=/reg?boardNo=" + list[i].boardNo + ">" + list[i].no;
                    boardHtml += "</a>";
                    boardHtml += "</td>";
                    boardHtml += "<td>" + list[i].title;
                    boardHtml += "</td>";
                    boardHtml += "<td>" + list[i].content;
                    boardHtml += "</td>";
                    boardHtml += "<td>" + list[i].userId;
                    boardHtml += "</td>";
                    boardHtml += "<td>" + list[i].regDate;
                    boardHtml += "</td>";
                    boardHtml += "<td>" + list[i].modDate;
                    boardHtml += "</td>";
                    boardHtml += "<td>";
                    boardHtml += "<a href='#' class='btn btn-danger' onclick='delAjax(" + list[i].boardNo + ");'>삭제";
                    boardHtml += "</a>";
                    boardHtml += "</td>";
                    boardHtml += "</tr>";
            }
            $("#boardBody").html(boardHtml);

            //페이지네이션 draw
            let pageHtml = "";

            pageHtml += "<ul class='pagination justify-content-center'>";

            if (result.paging.firstPageNo > 1) {
                const prev = parseInt(result.paging.firstPageNo) - 1;
                pageHtml += "<li class='page-item'>";
                pageHtml += "<a href='#' class='page-link' onclick='movePage("+ prev +")' style='cursor: pointer'>Prev</a>";
                pageHtml += "</li>";
            }

            for (let i = result.paging.firstPageNo; i <= result.paging.lastPageNo; i++) {
                pageHtml += "<li class='page-item'>";

                if (result.paging.currentPage === i) {
                    pageHtml += "<a class='page-link active' onclick='movePage(" + i + ")' style='cursor: pointer'>" + i + "</a>";
                    pageHtml += "</li>";
                } else {
                    pageHtml += "<a class='page-link' onclick='movePage(" + i + ")' style='cursor: pointer'>" + i + "</a>";
                    pageHtml += "</li>";
                }
            }

            if (result.paging.lastPageNo < result.paging.totalPageCount) {
                const next = parseInt(result.paging.lastPageNo) + 1;
                pageHtml += "<li class='page-item'>";
                pageHtml += "<a href='#' class='page-link' onclick='movePage("+ next +")' style='cursor: pointer'>Next</a>";
                pageHtml += "</li>";
            }
            pageHtml += "</ul>";

            $("#pagiNav").html(pageHtml);


        },
        error: function (request, status, error) {
            console.log(error);
        }
    });
}

//게시글 삭제(ajax)
function delAjax(no) {

    if (!confirm("정말 삭제할거야? 후회 안하지?")) {
        return;
        
    } else {
        $.ajax({
            url : '/delAjax/' + no,
            type : 'post',
            success: function (code) {
                console.log("code : " + code);
                if(code === 1) {
                    alert("게시글이 삭제 되었어.");
                    location.href = "/";
                } else {
                    alert("시스템 에러다. 삭제가 안되었네?");
                    location.href = "/";
                }
            },
            error: function (request, status, error) {
                console.log(error);
            }
        });
    }
}