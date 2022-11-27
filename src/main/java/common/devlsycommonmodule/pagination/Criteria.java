package common.devlsycommonmodule.pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Criteria {

    // for paging
    private int pageNum;                // 페이지 번호(현재 페이지가 몇 페이지인지)
    private int amount;                 // 한 화면에 출력한 페이지 개수
    private int limitStart;             // 쿼리에서 (pageNum -1) * amount 사용하기 위한 변수

    // for search
    private String keyword;
    private String searchType;

    // 시작 페이지 1번, 기본값 페이지 출력 개수 10개로 세팅(향후 변경 가능)
    public Criteria() {
        this.pageNum = 1;
        this.amount = 10;
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    // 쿼리에서 limit 1번 째 parameter로 쓸 값(mybatis에서 이 getter 이용해서 값을 )
    public int getLimitStart() {
        return this.limitStart = (pageNum - 1) * this.amount;
    }
}
