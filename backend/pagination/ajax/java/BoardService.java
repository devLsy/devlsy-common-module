package study.thboard2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import study.thboard2.domain.vo.BoardVo;
import study.thboard2.domain.vo.CommonVo;
import study.thboard2.domain.vo.PaginationInfo;
import study.thboard2.mapper.BoardMapper;

import java.util.List;

import static study.thboard2.common.utils.ValidationUtil.invokeErrors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService extends CommonService{

    private final BoardMapper boardMapper;

    /**
     * 게시글 목록 조회
     * @param commonVo
     * @return
     * @throws Exception
     */
    public List<BoardVo> getBoardList(CommonVo commonVo) throws Exception{
        return boardMapper.selectBoardList(commonVo);
    }

    /**
     * 게시글 전체 카운트
     * @param commonVo
     * @return
     */
    public int getBoardCnt(CommonVo commonVo) throws Exception{
        return boardMapper.selectBoardCnt(commonVo);
    }
}