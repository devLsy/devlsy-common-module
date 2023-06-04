package study.thboard2.domain.vo;

import lombok.Data;

@Data
//공통 vo
public class CommonVo {
    /*검색 필드*/
    private String type;                              //검색 타입
    private String keyword;                           //검색 키워드
    
    /* 페이지네이션 필드 */
    private int currentPage = 1;                          //현재 페이지 번호
    private int totalCount;                           //전체 게시글 개수
    private int firstRecordIndex, lastRecordIndex;    // 페이징 SQL에서 사용하는 첫번 째, 마지막 인덱스
    
    /* 날짜 필드 */
    private String regDate;                           //등록일
    private String modDate;                           //수정일
}
