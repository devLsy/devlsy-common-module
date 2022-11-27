# mybatis 동적 insert, select
## 아래처럼 insert의 컬럼과 값, SELECT 컬럼을 동적으로 세팅
### 관리자 페이지에서 컬럼을 동적으로 추가할 수 있고 추가된 컬럼을 동적으로 저장/조회 등 하는 경우
### 실제 프로젝트에서는 사용 불가할 듯

#### 예시
[xml]
```xml
<!-- 등록 -->
<insert id="insertBoard" parameterType="hashmap">
INSERT INTO t_board
(
<foreach collection="column" item="column" separator=",">
${column}
</foreach>
, reg_date
, update_date
)
VALUES
(
<foreach collection="column_value" item="column_value" separator=",">
#{column_value}
</foreach>
, now()
, now()
)
</insert>

<!-- 조회 -->
<select id="findTest" parameterType="hashmap" resultType="hashmap">
SELECT
<foreach collection="column" item="column" separator=",">
${column}
</foreach>
FROM ${tableName}
</select>
```

[controller]
```java
// 화면에서 넘긴 값을 map으로 받음
@PostMapping("/board/new")
public String create(@RequestParam Map<String, Object> param) throws Exception {
        boardService.insertBoard(param);
        return "redirect:/admin/board/list";
}
```

[service]
```java
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardMapper boardMapper;

    @Transactional
    public void insertBoard(Map<String, Object> map) {
        // mybatis에 전달하는 param값
        List<Object> columns = new ArrayList<>();
        List<Object> column_values = new ArrayList<>();
        // 화면에서 넘어온 parameter인 map에서 key, value 추출해서 list에 각각 담아서 mybatis에 넘김
        for (Object obj: map.keySet()) {
            columns.add(obj);
            column_values.add(map.get(obj));
        }
        Map<String, Object> mapperParam = new HashMap<String, Object>();
        mapperParam.put("column", columns);
        mapperParam.put("column_value", column_values);

      boardMapper.insertBoard(mapperParam);
    }

    public List<Map<String, Object>> findTest(List<String> column, String tableName) {
        Map<String, Object> mapperParam = new HashMap<String, Object>();
        // 컬럼과 테이블명 map에 저장 후 mybatis에 넘김
        mapperParam.put("column", column);
        mapperParam.put("tableName", tableName);
        return boardMapper.findMember(mapperParam);
    }
}
```

[mapper]
```java
@Mapper
public interface BoardMapper {
    // 저장
    void insertBoard(Map<String, Object> map);
    // 조회
    List<Map<String, Object>> findBoard(Map<String, Object> param);
}
```

[test]
```java
@SpringBootTest
@Slf4j
@Transactional
class BoardServiceTest {
    @Autowired BoardService boardService;
    
    @Test
    @DisplayName("insert")
    @Commit
    public void insert() {
        List<String> column = new ArrayList<>();
        List<String> column_value = new ArrayList<>();

        column.add("title");
        column.add("contents");
        column.add("name");

        column_value.add("제목입니다.");
        column_value.add("내용입니다.");
        column_value.add("작성자겠죠?");

        Map<String, Object> param = new HashMap<>();
        param.put("column", column);
        param.put("column_value", column_value);
        boardService.insertBoard(param);
    }

    @Test
    @DisplayName("findTest")
    @Commit
    public void findTest() throws Exception {
        Map<String, Object> param = new HashMap<>();
        // 조회할 컬럼
        List<Object> Columns = new ArrayList<>();
        Columns.add("board_seq");
        Columns.add("title");
        Columns.add("contents");
        // 조회 테이블
        String TableName = "t_board";

        param.put("column", Columns);
        param.put("tableName", TableName);
        boardService.findTest(param);
    }
}
```

# 페이지에서 테이블의 컬럼을 추가, 삭제 등을 하는 예시
## 테이블 컬럼 
![image](https://user-images.githubusercontent.com/44331989/204124581-67c0a90b-e22c-4eb8-9e1e-3ebb8e97795e.png) <br>
![image](https://user-images.githubusercontent.com/44331989/204124778-c9c5378d-b201-4cb6-9361-0e481d7f6626.png) <br>
[view]
```html
<div layout:fragment="content" class="container">
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <h5 class="my-3 border-bottom pb-2">필드추가</h5>
        <form th:action="@{/tables/addField}" method="post">
            <div class="mb-3">
                <label for="tableName" class="form-label">테이블명</label>
                <input type="text" name="tableName" class="form-control" th:value="${param.tableName}" readonly>
            </div>
            <div class="mb-3">
                <label for="field" class="form-label">필드명</label>
                <input type="text" name="field" id="field" class="form-control">
            </div>
            <div class="mb-3">
                <label for="dataType" class="form-label">데이터타입</label>
                <select class="form-select" name="dataType">
                    <option value="">선택해주세요.</option>
                    <option th:value="VARCHAR" th:text="VARCHAR"></option>
                    <option th:value="TEXT" th:text="TEXT"></option>
                </select>
            </div>
            <div class="mb-3">
                <label for="length" class="form-label">데이터크기</label>
                <input type="number" name="length" id="length" class="form-control" min="1" max="250">
            </div>
            <button type="submit" class="btn btn-primary my-2">저장하기</button>
            <a th:href="@{/tables/list}" class="btn btn-secondary">목록</a>
        </form>

    </main>
</div>
```
[contoller]
```java
@GetMapping(value = "/addField")
    public String addField(Model model, @RequestParam("tableName") String tableName) {
        List<MenuVo> menuList = menuService.selectMenuList(new Criteria());
        model.addAttribute("menuList", menuList);
        return "pages/table/table_modify";
    }

@PostMapping(value = "/addField")
    public String addField(@RequestParam("tableName") String tableName,
                           @RequestParam("field") String field,
                           @RequestParam("dataType") String dataType,
                           @RequestParam("length") String length) throws Exception {
        tableService.addField(tableName, field, dataType, length);
        return "redirect:/tables/list";
    }
```

[service]
```java
    /**
     * 필드 추가
     * @param tableName
     * @param addField
     * @param fieldType
     * @param length
     * @throws Exception
     */
    @Transactional
    public void addField(String tableName, String addField, String fieldType, String length) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append(fieldType).append("(").append(length).append(")");

        fieldType = String.valueOf(sb);
        tableMapper.addColumn(tableName, addField, fieldType);
    }
```

[mapper interface]
```java
void addColumn(@Param("tableName") String tableName, @Param("addField") String addField, @Param("fieldType") String fieldType);
```

[maper xml]
```xml
<!-- 컬럼 추가 -->
<update id="addColumn" parameterType="map">
        ALTER TABLE ${tableName} ADD COLUMN ${addField} ${fieldType}
</update>
```
## 테이블 컬럼 삭제
![image](https://user-images.githubusercontent.com/44331989/204124834-acc9bd3e-bb63-4698-87be-86d7759acb59.png) <br>
[view]
```html
<div layout:fragment="content" class="container">
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <h5 class="my-3 border-bottom pb-2">테이블 컬럼 삭제</h5>
        <form th:action="@{/tables/delField}" method="post">
        <div class="alert alert-danger" role="alert">
            <span>삭제할 컬럼을 선택 해주세요.(복구 불가하니 신중히 선택 해주세요.)</span>
        </div>
        <div class="mb-3">
            <label for="tableName" class="form-label">테이블명</label>
            <input type="text" name="tableName" class="form-control" th:value="${param.tableName}" readonly>
        </div>
        <div class="mb-3">
            <label for="delField" class="form-label">컬럼 목록</label>
            <select class="form-select" name="delField">
                <option th:each="list : ${tableInfo}" th:value="${list.columnName}" th:text="${list.columnName}"></option>
            </select>
        </div>
            <button type="submit" class="btn btn-danger my-2">삭제하기</button>
            <a th:href="@{/tables/list}" class="btn btn-secondary">목록</a>
        </form>
    </main>
</div>
</html>
```

[controller]
```java
@GetMapping(value = "/delField")
    public String delField(Model model, @RequestParam("tableName") String tableName) {
        List<TableVO> tableInfo = tableService.selectTableDetail(tableName);
        List<MenuVo> menuList = menuService.selectMenuList(new Criteria());
        model.addAttribute("tableInfo", tableInfo);
        model.addAttribute("menuList", menuList);
        return "pages/table/table_del";
    }

    @PostMapping(value = "/delField")
    public String delField(@RequestParam("tableName") String tableName, @RequestParam("delField") String delField) throws Exception {
        tableService.delField(tableName, delField);
        return "redirect:/tables/list";
    }
```

[service]
```java
    /**
     * 필드 삭제
     * @param tableName
     * @param delField
     * @throws Exception
     */
    @Transactional
    public void delField(String tableName, String delField) throws Exception{
        tableMapper.delColumn(tableName, delField);
    }
```

[mapper interface]
```java
void delColumn(@Param("tableName") String tableName, @Param("delField") String delField);
```

[mapper xml]
```xml
<!-- 컬럼 삭제 -->
<update id="delColumn" parameterType="map">
        ALTER TABLE ${tableName} DROP COLUMN ${delField}
</update>
```




