package study.thboard2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.thboard2.domain.vo.CommonVo;
import study.thboard2.domain.vo.PaginationInfo;

@Service
@Slf4j
//공통 서비스
public class CommonService {

    /**
     * 페이지네이션 처리 후 페이지네이션 객체 반환
     * @param commonVo
     * @return
     */
    public PaginationInfo getPaginationInfo(CommonVo commonVo) {
        PaginationInfo paging = new PaginationInfo();
        paging.setTotalCount(commonVo.getTotalCount());
        paging.setCurrentPage(commonVo.getCurrentPage());
        return paging;
    }
    
}