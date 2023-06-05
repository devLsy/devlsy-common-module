package study.thboard2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import study.thboard2.domain.vo.UserVo;

import java.util.List;

@Repository @Mapper
public interface UserMapper {

    /* 사용자 정보 저장 */
    void insertUser(UserVo userVo);

    /* 사용자 정보 확인(로그인 시 활용) */
    UserVo selectByUserId(@Param("userId") String userId);
}