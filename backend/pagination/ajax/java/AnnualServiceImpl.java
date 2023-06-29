package kr.renosoft.portal.serviceImpl.annual;


import kr.renosoft.common.model.PaginationInfo;
import kr.renosoft.portal.mapper.AnnualMapper;
import kr.renosoft.portal.model.enu.ResultType;
import kr.renosoft.portal.service.Mail.MailService;
import kr.renosoft.portal.service.annual.AnnualService;
import kr.renosoft.portal.service.common.CommonService;
import kr.renosoft.portal.service.login.LoginService;
import kr.renosoft.portal.vo.EmpVO;
import kr.renosoft.portal.vo.annual.AnnualVo;
import kr.renosoft.portal.vo.common.CommonCdVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.renosoft.portal.util.ValidationUtil.invokeErrors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AnnualServiceImpl implements AnnualService {

    @Autowired
    private AnnualMapper annualMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private CommonService commonService;

    /**
     * 연차 신청 목록 ajax(관리자)
     * @param mv
     * @param annualVo
     * @param empVO
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity selectAnnualReqList(ModelAndView mv, AnnualVo annualVo, EmpVO empVO) throws Exception {
        //resultMap
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //로그인한 사용자가 연차승인권자 여부 체크
        String cfrmerYn = commonService.checkCfrmerYn(empVO.getEmpId());
        //로그인한 사용자가 경력 몇년차인지
        annualVo.setCareerYear(loginService.getYear(empVO.getEmpId()));
        //연차승인권자인 경우 팀원 연차 조회
        if (empVO.getEmpAuthLevel().equals("1") && "Y".equals(cfrmerYn)) {
            annualVo.setAnnualEmpNm(empVO.getEmpId());
            //전체 count
            int teamAnnualHistoryCnt = annualMapper.selectTeamAnnualHistoryCnt(annualVo);
            //페이지네이션 세팅
            PaginationInfo pageVo = commonService.getPagination(annualVo.getCurrentPage(), teamAnnualHistoryCnt);
            annualVo.setFirstRecordIndex(pageVo.getFirstRecordIndex());
            annualVo.setLastRecordIndex(pageVo.getLastRecordIndex());
            //팀원 연차 신청 목록
            List<AnnualVo> list = annualMapper.selectTeamAnnualHistory(annualVo);
            resultMap.put("list", list);
            resultMap.put("paging", pageVo);
            resultMap.put("totalCount", teamAnnualHistoryCnt);

        } else {
            //연차정보 cnt
            int annualHistoryCnt = annualMapper.selectAnnualHistoryCnt(annualVo);
            //페이지네이션 세팅
            PaginationInfo pageVo = commonService.getPagination(annualVo.getCurrentPage(), annualHistoryCnt);
            annualVo.setFirstRecordIndex(pageVo.getFirstRecordIndex());
            annualVo.setLastRecordIndex(pageVo.getLastRecordIndex());
            //전사원 연차 신청 목록
            List<AnnualVo> list = annualMapper.selectAnnualHistory(annualVo);
            resultMap.put("list", list);
            resultMap.put("paging", pageVo);
            resultMap.put("totalCount", annualHistoryCnt);
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
