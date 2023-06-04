package study.thboard2.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PaginationInfo {

 /* Required Fields - 페이징 계산을 위해 반드시 입력되어야 하는 필드들이다. */
    @Getter @Setter
    private int currentPage = 1;            // 현재 페이지 번호 (초기값 : 1)
    @Getter @Setter
    private int totalCount; 		        // 전체 목록 건수 (목록)
    @Getter @Setter
    private int recordCountPerPage = 10;	// 페이지당 목록 건수 (목록)
    @Getter @Setter
    private int pageSize = 10; 		        // 페이징 목록에 게시되는 페이지 건수 (페이징)

    /* Not Required Fields - 이 값들은 Required Fields의 값들을 바탕으로 계산되어 정해지는 필드값이다. */
    private int totalPageCount;		        // 전체 페이지 건수 (페이징)
    private int firstPageNo; 		        // 페이지 목록의 첫 페이지 번호 (페이징)
    private int lastPageNo; 		        // 페이지 목록의 마지막 페이지 번호 (페이징)
    private int firstRecordIndex;	        // 페이징 SQL의 조건절에 사용되는 시작 Index (SQL)
    private int lastRecordIndex;	        // 페이징 SQL의 조건절에 사용되는 마지막 Index (SQL)


    public int getTotalPageCount() {
        totalPageCount = ((getTotalCount() - 1) / getRecordCountPerPage()) + 1;
        return totalPageCount;
    }

    public int getFirstPageNo() {
        firstPageNo = ((getCurrentPage() - 1) / getPageSize()) * getPageSize() + 1;
        return firstPageNo;
    }

    public int getLastPageNo() {
        lastPageNo = getFirstPageNo() + getPageSize() - 1;
        if(lastPageNo > getTotalPageCount()) {
            lastPageNo = getTotalPageCount();
        }
        return lastPageNo;
    }

    public int getFirstRecordIndex() {
        firstRecordIndex = (getCurrentPage() - 1) * getRecordCountPerPage() + 1;
        return firstRecordIndex;
    }

    public int getLastRecordIndex() {
        lastRecordIndex = getCurrentPage() * getRecordCountPerPage();
        return lastRecordIndex;
    }

    public int getFirstPage() {
        return 1;
    }
    public int getLastPage() {
        return getTotalPageCount();
    }

}
