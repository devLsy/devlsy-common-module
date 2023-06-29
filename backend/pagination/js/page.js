/**
 * ajax 통신으로 리스트를 조회한다.
 * @param input
 * @param currentPage
 * @param type
 */
function getList(input, currentPage, type){

	$.ajax({
		url: "/api/" + type +"?currentPage=" + currentPage,
		type:"get",
		cache: false,
		data: input,
		success: function (data){

			//draw tbody
			drawTbody(data.list, type);
			//draw pagination
			drawPagination(data.paging);
		},
		error:function(e){
		}
	});
}
// end function getList()


/****************************** tbody draw start **************************************************/
/**
 * tbody에 html을 덮어쓴다.
 * @param list
 * @param gubun
 */
function drawTbody(list, gubun) {
	//drawHtml, //draw 대상 tbody
	let htmlData, targetTbody = "";
	const today = getToday();

	switch(gubun) {
		//연차
		case 'annual':
			targetTbody = "annualBody";
			// foreach start
			// TODO 이곳도 중복코드 분리 필요
			for (let i = 0; i < list.length; i++) {
				//휴가자 표시
				if (list[i].isHoliday === "Y") {
					htmlData += "<tr style='background: #FA9A44'>";
				} else {
					htmlData += "<tr>";
				}
				htmlData += "<td>";
				htmlData += list[i].no;
				htmlData += "</td>";
				htmlData += "<td>";
				htmlData += list[i].empNm;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].annualStrDt;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].annualGbNm;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].useAnnualNum;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].grantedAnnualNum;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].useSpcAnnualNum;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].grantedSpcAnnualNum;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].cfrmCompGbNm;
				htmlData += "</td>";
				//연차승인자
				if (list[i].annualApprEmpNm != null) {
					htmlData += "<td>" + list[i].annualApprEmpNm;
				} else {
					htmlData += "<td>";
				}
				htmlData += "</td>";

				if (list[i].apprDt != "년월일") {
					htmlData += "<td>" + list[i].apprDt;
				} else {
					htmlData += "<td>";
				}
				htmlData += "</td>";
				htmlData += "<td>";

				//신청사유 있을 경우
				if(list[i].annualReqReason != null) {
					htmlData += "<a href='#pop01' class='pop-open' onclick='return false;'>";
					htmlData += "<span class='material-icons text-red' onclick='getReason("+ list[i].annualSno + ', "request"' + ")'>search";
					htmlData += "</span>"
					htmlData += "</a>";
				}
				//승인/반려 사유 있을 경우
				if (list[i].apprCompReason != null) {
					htmlData += "<a href='#pop02' class='pop-open' onclick='return false;'>";
					htmlData += "<span class='material-icons text-blue' onclick='getReason("+ list[i].annualSno + ', "return"' + ")'>search";
					htmlData += "</span>"
					htmlData += "</a>";
				}
				htmlData += "</td>";
				htmlData += "</tr>";
			}
			// foreach end
		break;

		//사원
		case 'login/empinfo':
			targetTbody = "loginBody";
			// foreach start
			// TODO 이곳도 중복코드 분리 필요
			for (let i = 0; i < list.length; i++) {
				//휴가자 표시
				htmlData += "<tr>";
				htmlData += "<td>";
				htmlData += list[i].no;
				htmlData += "</td>";
				htmlData += "<td><a class='man' onclick='popup("+'"'+list[i].empId+'"'+")'>";
				htmlData += list[i].empNm;
				htmlData += "</a></td>";
				htmlData += "<td>" + list[i].empId;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].empHp;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].workStateCd;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].positionCd;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].stationCd;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].authCd;
				htmlData += "</td>";
				htmlData += "<td>" + list[i].hireDt;
				htmlData += "</td>";
				if (list[i].annualApprEmpNm != null) {
					htmlData += "<td>" + list[i].workStateDt;
				} else {
					htmlData += "<td>";
				}
				htmlData += "</td>";
				htmlData += "</tr>";
			}
			// foreach end
		break;

		//프로젝트
		case 'project':
			targetTbody = "projectBody";
			// foreach start
			for (let i = 0; i < list.length; i++) {

				htmlData += "<tr>";

				/** 순번 */
				htmlData += "<td>";
				htmlData += (i+1);
				htmlData += "</td>";

				/** 실행년도 */
				htmlData += "<td>";
				htmlData += list[i].projPlanYear;
				htmlData += "</td>";

				/** 상태 */
				htmlData += "<td>";
				if(list[i].projStartDt > today){
					htmlData += "예정";
				}else if(list[i].projStartDt <= today &&  list[i].projEndDt >= today ){
					htmlData += "진행";
				}else if(list[i].projEndDt < today){
					htmlData += "종료";
				}
				htmlData += "</td>";

				/** 프로젝트 명 */
				htmlData += "<td>";
				htmlData += "<a href='/project/detail/"+list[i].projPlanSno+"'>";
				htmlData += list[i].projNm;
				htmlData += "</a>";
				htmlData += "</td>";

				/** 발주처 */
				htmlData += "<td>";
				htmlData += list[i].projClientNm;
				htmlData += "</td>";

				/** 프로젝트 기간 */
				htmlData += "<td>";

				htmlData += "<span>";
				htmlData += list[i].projStartDt;
				htmlData += "</span>";

				htmlData += "<span> ~ </span>";

				htmlData += "<span>";
				htmlData += list[i].projEndDt;
				htmlData += "</span>";

				htmlData += "</td>";

				/** 총 투입인원 */
				htmlData += "<td>";
				htmlData += "<a class='pop-open' href='#pop-info' onclick='showProjEmpList("+list[i].projPlanSno+")'>";
				htmlData += list[i].totalEmployees;
				htmlData += "</a>";
				htmlData += "</td>";

				/** 현재인원 */
				htmlData += "<td>";
				htmlData += list[i].activeEmployees;
				htmlData += "</td>";

				htmlData += "</tr>";
			}
		break;

		//type add..
		// case '':
		//입력 필요
		// break;

		// default:
		// break;
	}
	//tbody에 반영
	$("#" + targetTbody + "").html(htmlData);
}
/****************************** tbody draw end ****************************************************/


/****************************** pagination draw start *********************************************/
/**
 * 페이지네이션을 그린다.
 * @param paging
 */
function drawPagination(paging) {

	let pageHtml = "";
	pageHtml += "<ul class='pagination'>";

	//first
	const first = parseInt(paging.firstPage);
	pageHtml += "<li class='paginate_button page-item first'>";

	if (paging.currentPage === paging.firstPage) {
		pageHtml += "<button class='page-link' disabled>";
	} else {
		pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ first +")'>";
	}
	pageHtml += "<span class='hidden'>first page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	//prev
	const prev = parseInt(paging.currentPage) -1;
	pageHtml += "<li class='paginate_button page-item prev'>";

	if (paging.firstPage === paging.currentPage) {
		pageHtml += "<button class='page-link' disabled>";
	} else {
		pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ prev +")'>";
	}
	pageHtml += "<span class='hidden'>prev page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	//① ~ ⑩
	for (let i = paging.firstPageNo; i <= paging.lastPageNo; i++) {
		pageHtml += "<li class='paginate_button page-item";
		//현재 페이지가 인덱스와 같으면 active class 추가
		if (paging.currentPage === i) {
			pageHtml += " active'>";
			pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ i +")'>";
			pageHtml += i;
			pageHtml += "</button>";
			pageHtml += "</li>"
		} else {
			pageHtml += "'>";
			pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ i +")'>";
			pageHtml += i;
			pageHtml += "</button>";
			pageHtml += "</li>"
		}
	}

	//next
	const next = parseInt(paging.currentPage) + 1;
	pageHtml += "<li class='paginate_button page-item next'>";

	if (paging.currentPage === paging.lastPage) {
		pageHtml += "<button class='page-link' disabled>";
	} else {
		pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ next +")'>";
	}
	pageHtml += "<span class='hidden'>last page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	//last
	const last = parseInt(paging.lastPage);
	pageHtml += "<li class='paginate_button page-item last'>";

	if (paging.currentPage === paging.lastPage) {
		pageHtml += "<button class='page-link' disabled>";
	} else {
		pageHtml += "<button class='page-link' style='cursor: pointer;' onclick='movePage("+ last +")'>";
	}
	pageHtml += "<span class='hidden'>last page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	pageHtml += "</ul>";
	//페이지네이션 영역에 반영
	$(".tbl-paging").html(pageHtml);
}
/****************************** pagination draw end ***********************************************/

/**
 * 페이지 이동
 * @param currentPage
 */
function movePage(currentPage){
	getList(searchData, currentPage, menu);
}