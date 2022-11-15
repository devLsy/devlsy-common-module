# mybatis 동적 insert, select
## 아래처럼 insert의 컬럼과 값, SELECT 컬럼을 동적으로 세팅
```
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
```

## 참조 코드
- BoarcController, BoardService, BoardMapper, boardMapper.xml
- 