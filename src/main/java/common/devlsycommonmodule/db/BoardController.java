package common.devlsycommonmodule.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

//    아래 의존성 주입 했다고 가정
//    private final BoardService boardService;
    
    // 예시
    @PostMapping("/board/new")
    public String create(@RequestParam Map<String, Object> map) throws Exception
    {
//        서비스의 저장 메소드 호출
//        boardService.insertBoard(map);
        return "redirect:/board/list";
    }
}
