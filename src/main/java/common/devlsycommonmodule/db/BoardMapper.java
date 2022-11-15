package common.devlsycommonmodule.db;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    void insertBoard(Map<String, Object> map);
    List<Map<String, Object>> findMember(Map<String, Object> param);
}
