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


