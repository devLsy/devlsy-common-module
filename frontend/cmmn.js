//서버에서 세팅되는 데이터는 아래와 같음(map안에 list와 페이징 객체가 들어있음)
/**
 * @param annualVo
 * @param session
 * @return
 * @throws Exception
 */
public ResponseEntity selectList(EmpVo empVo, HttpSession session) throws Exception {
    //resultMap
    Map<String, Object> resultMap = new HashMap<String, Object>();
    //전체 count
    int count = annualMapper.selectCount(empVo);
    //페이지네이션 세팅
    PaginationInfo pageVo = commonService.getPagination(empVo.getCurrentPage(), count);
    empVo.setFirstRecordIndex(pageVo.getFirstRecordIndex());
    empVo.setLastRecordIndex(pageVo.getLastRecordIndex());
    //리스트 조회
    List<AnnualVo> list = EmpMapper.selectList(empVo);
    resultMap.put("list", list);
    resultMap.put("paging", pageVo);
    resultMap.put("totalCount", count);
    }
    return new ResponseEntity<>(resultMap, HttpStatus.OK);
}

/**
 * 페이지 이동
 * @param currentPage
 */
function movePage(currentPage){
	//검색조건이 있을 경우에만 검색조건 추가(검색조건 유지한 채 페이지 이동)
	(searchData != null) ? getList(searchData, currentPage, menu) : getList("", currentPage, menu);
}

/**
 * ajax 통신으로 리스트를 조회한다.
 * @param searchParam 검색조건
 * @param currentPage 현재 페이지
 * @param type 메뉴타입
 */
function getList(searchParam, currentPage, type){
	$.ajax({
		url: "/api/" + type +"?currentPage=" + currentPage,
		type:"get",
		//IE 브라우저 사용 안한다는 가정하에 주석처리
		// cache: false,
		data: searchParam,
		success: function (data){
			//draw tbody
			console.log(data.list);
			drawTbody(data, type);
      //draw pagination
			drawPagination(data.paging);
			}
		},
		error:function(e){
		}
	});
}
// end function getList()

/****************************** tbody draw start **************************************************/
/**
 * tbody에 html을 덮어쓴다.
 * @param data
 * @param gubun
 */
function drawTbody(data, gubun) {
    //drawHtml, draw 대상 tbody, 신청자 아이디
    let htmlData, targetTbody, userId = "";
    const today = getToday();

    switch(gubun) {
        //타입
        case 'annual':
            targetTbody = "tbody";  //그려질 tbody의 id

			if(data.list != null && data.list.length > 0) {
				// foreach start
				for (let i = 0; i < data.list.length; i++) {
					htmlData += "<tr>";
					htmlData += "<td>";
					htmlData += data.list[i].no;
					htmlData += "</td>";
					htmlData += "<td>";
					htmlData += data.list[i].empNm;
          htmlData += "</td>";
          //...
				}
				// foreach end
			} else {
				htmlData += "<tr>";
				htmlData += "<td colspan='9' style='text-align: center'>";
				htmlData += "검색결과가 없습니다.";
				htmlData += "</td>";
				htmlData += "</tr>";
			}
        break;

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
		pageHtml += "<button type='button' class='page-link' disabled>";
	} else {
		pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ first +")'>";
	}
	pageHtml += "<span class='hidden'>first page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	//prev
	const prev = parseInt(paging.currentPage) -1;
	pageHtml += "<li class='paginate_button page-item prev'>";

	if (paging.firstPage === paging.currentPage) {
		pageHtml += "<button type='button' class='page-link' disabled>";
	} else {
		pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ prev +")'>";
	}
	pageHtml += "<span class='hidden'>prev page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>";

	//① ~ ⑩
	for (let i = paging.firstPageNo; i <= paging.lastPageNo; i++) {
		pageHtml += "<li class='paginate_button page-item";
		//현재 페이지가 인덱스와 같으면 active class 추가
		if (paging.currentPage === i) {
			pageHtml += " active'>";
			pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ i +")'>";
			pageHtml += i;
			pageHtml += "</button>";
			pageHtml += "</li>"
		} else {
			pageHtml += "'>";
			pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ i +")'>";
			pageHtml += i;
			pageHtml += "</button>";
			pageHtml += "</li>"
		}
	}

	//next
	const next = parseInt(paging.currentPage) + 1;
	pageHtml += "<li class='paginate_button page-item next'>";

	if (paging.currentPage === paging.lastPage) {
		pageHtml += "<button type='button' class='page-link' disabled>";
	} else {
		pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ next +")'>";
	}
	pageHtml += "<span class='hidden'>next page</span>";
	pageHtml += "</button>";
	pageHtml += "</li>"

	//last
	const last = parseInt(paging.lastPage);
	pageHtml += "<li class='paginate_button page-item last'>";

	if (paging.currentPage === paging.lastPage) {
		pageHtml += "<button type='button' class='page-link' disabled>";
	} else {
		pageHtml += "<button type='button' class='page-link' style='cursor: pointer;' onclick='movePage("+ last +")'>";
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
 * form 객체 초기화
 * @param formId
 */
function formReset(formId) {
	$("#" + formId + "")[0].reset();
}

/**
 * 인자로 넘어온 아이디의 입력값을 정규표현식 조건에 따라 변경한다.
 * @param id 엘리먼트 아이디
 * @param type 정규표현식 종류
 */
function processElementWithRegex(id, type) {

	console.log(id, type);
	let regexObject = "";
	switch (type) {
		//영문,숫자만 허용
		case 'AN':
			regexObject = /[^a-zA-Z0-9]/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;

		//영문만 허용
		case 'A':
			regexObject = /[^a-zA-Z]/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;

		//숫자만 허용
		case 'N':
			regexObject = /[^0-9]/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;

		//한글만 허용
		case 'H':
			regexObject = /[^ㄱ-ㅎㅏ-ㅣ가-힣]/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;

		//이메일 형식만 허용
		case 'E':
			regexObject = /[^a-zA-Z0-9@._-]/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;

		//한글, 영문만 허용
		case 'HA':
			regexObject = /[0-9~!@#$%^&*()_+={}[\]|\\:;"'<>,.?/-]+/g;
			$("#" + id).on("input", function(e) {
			replaceProcess(e.target, regexObject);
		});
		break;	
	}
}

/**
 * 정규식 치환
 * @param element
 * @param regex
 */
function replaceProcess(element, regex) {
	$(element).val($(element).val().replace(regex, ''));
}
