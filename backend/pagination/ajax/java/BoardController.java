package study.thboard2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import study.thboard2.domain.vo.*;
import study.thboard2.service.BoardService;
import study.thboard2.service.FileService;
import study.thboard2.service.ReplyService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class BoardController extends CommonController{

    private final BoardService boardService;
    private final ReplyService replyService;
    private final FileService fileService;

    
     /**
     * 게시글 목록(ajax)
     * @param commonVo
     * @return
     */
    @PostMapping("listAjax")
    @ResponseBody
    public ResponseEntity<?> listAjax(@ModelAttribute("commonVo") CommonVo commonVo) throws Exception {
        ModelAndView mv = new ModelAndView("pages/main");

        Map<String, Object> map = new HashMap<>();
        //전체 게시글 수
        int totalCnt = boardService.getBoardCnt(commonVo);
        commonVo.setTotalCount(totalCnt);
        //페이징 처리 후 반환 객체
        PaginationInfo paging = boardService.getPaginationInfo(commonVo);
        log.info("commonVo = [{}]", commonVo);
        log.info("paging = [{}]", paging);
        commonVo.setFirstRecordIndex(paging.getFirstRecordIndex());
        commonVo.setLastRecordIndex(paging.getLastRecordIndex());

        List<BoardVo> boardList = boardService.getBoardList(commonVo);

        map.put("list", boardList);
        map.put("paging", paging);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}