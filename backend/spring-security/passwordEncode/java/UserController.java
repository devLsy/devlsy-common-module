package study.thboard2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.thboard2.domain.vo.UserVo;
import study.thboard2.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인 화면
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        String id = (String) session.getAttribute("id");
        log.info("stored session id =[{}]", id);
        return id != null ? "redirect:/" : "pages/login" ;
    }

    /**
     * 회원가입 화면
     * @return
     */
    @GetMapping("/register")
    public String registerForm() {
        return "pages/register";
    }

    /**
     * 회원가입 처리
     * @param userVo
     * @return
     */
    @PostMapping("/register")
    public String register(@ModelAttribute UserVo userVo) {
        try {
            userService.regUser(userVo);
        } catch (Exception e) {
            log.info("Exception => [{}] ", e.getMessage());
        }
        return "redirect:/login";
    }

    /**
     * 사용자 로그인
     * @param userId
     * @param userPassword
     * @param session
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String userId,
                                @RequestParam String userPassword,
                                HttpSession session) throws Exception {

        String id = userService.login(userId, userPassword);
        if(id == "none") return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);

        session.setAttribute("id", id);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

}