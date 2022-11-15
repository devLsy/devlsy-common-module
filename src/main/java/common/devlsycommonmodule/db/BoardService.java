package common.devlsycommonmodule.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
//    아래 의존성 주입했다고 가정
//    private final BoardMapper boardMapper;

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

//        boardMapper.insertBoard(mapperParam);
    }

    public List<Map<String, Object>> findMember(List<String> column, String tableName) {
        Map<String, Object> mapperParam = new HashMap<String, Object>();
        // 컬럼과 테이블명 map에 저장 후 mybatis에 넘김
        mapperParam.put("column", column);
        mapperParam.put("tableName", tableName);
//        return boardMapper.findMember(mapperParam);
    }
}
