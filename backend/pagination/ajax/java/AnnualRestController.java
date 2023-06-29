package kr.renosoft.portal.controller.annual;


import kr.renosoft.portal.controller.common.CommonController;
import kr.renosoft.portal.service.annual.AnnualService;
import kr.renosoft.portal.service.api.ApiService;
import kr.renosoft.portal.service.common.CommonService;
import kr.renosoft.portal.service.login.LoginService;
import kr.renosoft.portal.serviceImpl.project.projectServiceImpl;
import kr.renosoft.portal.vo.annual.AnnualVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
//연차 관련 ajax 통신 controller
@RequestMapping(value = "api/annual")
public class AnnualRestController extends CommonController {

    @Autowired
    AnnualService annualService;

    /**
     * 연차 신청 목록 ajax
     * @param annualVo
     * @param session
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity selectAnnualReqList(ModelAndView mv, @ModelAttribute("annualVo") AnnualVo annualVo, HttpSession session) throws Exception {
        log.info("annualVo = [{}]", annualVo);
        return annualService.selectAnnualReqList(mv, annualVo, getSessionUserInfo(session));
    }

}
