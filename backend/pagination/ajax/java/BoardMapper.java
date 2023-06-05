package study.thboard2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import study.thboard2.domain.vo.BoardVo;
import study.thboard2.domain.vo.CommonVo;

import java.util.List;

@Repository @Mapper
public interface BoardMapper {

    /*게시글 목록 조회*/
    List<BoardVo> selectBoardList(CommonVo commonVo);

    /*게시글 전체 카운트*/
    int selectBoardCnt(CommonVo commonVo);
}