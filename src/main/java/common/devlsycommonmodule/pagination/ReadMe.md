# 페이지네이션 화면 처리를 위한 클래스
## 사용법 예시(https://devlsy.tistory.com/344)

[mapper xml(mysql 기준)]
```xml
<!-- parameter로 Criteria를 받아서 데이터 limit에 활용 --> 
<select id="findBoardListPaging" parameterType="paging.study.domain.Criteria" resultMap="boardMap">
        SELECT * FROM t_board
        ORDER BY reg_date DESC
        limit #{limitStart}, #{amount}
    </select>
```

[mapper(interface)]
```java    
List<BoardVO> findBoardListPaging(Criteria cri);
```

[service]
```java    
public List<BoardVO> findBoardListPaging(Criteria cri) {
        return boardMapper.findBoardListPaging(cri);
    }
```    

[controller]
```java    
@GetMapping("/board/list")
    // 클라이언트에서 cri를 parameter로 받음(현재 몇페이지인지(cri의 pageNum))
    public String listPaging(@ModelAttribute("cri") Criteria cri, Model model) {
        List<BoardVO> list = boardService.findBoardListPaging(cri);
        int boardCount = boardService.findBoardCount();
        // view에 전달할 데이터에 pageMaker를 같이 넘김(데이터 전체 개수와 cri를 가지고 페이지네이션 데이터 계산한 값이 들어 있음)
        model.addAttribute("list", list);
        model.addAttribute("pageMaker", new PageMaker(boardCount, cri));
        return "board/boardList";
    }
```    

[view](thymeleaf)
```html
<!-- 게시판 하단 페이지네이션 영역 start -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <!-- prev -->
                <li class="page-item" th:if="${pageMaker.prev} == true">
                    <a class="page-link" th:href="@{/board/list(pageNum=${pageMaker.startPage}-1)}">Prev</a>
                </li>
                <!-- pageMaker의 startPage부터 endPage까지 루프, a태그의 href에 idx를 링크(get방식으로 pageNum을 붙여서) -->
                <li class="page-item" id="paginate_btn" th:each="idx: ${#numbers.sequence(pageMaker.startPage, pageMaker.endPage)}" th:classappend="${pageMaker.cri.pageNum} == ${idx} ? active : null">
                    <a class="page-link" th:href="@{/board/list(pageNum=${idx})}" th:text="${idx}"></a>
                </li>
                <!-- next -->
                <li class="page-item" th:if="${pageMaker.next} == true and ${pageMaker.endPage > 0}">
                    <a class="page-link" th:href="@{/board/list(pageNum=${pageMaker.endPage}+1)}">Next</a>
                </li>
            </ul>
        </nav>
        <!-- // 게시판 하단의 페이지네이션 영역 end -->
```
