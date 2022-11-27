package common.devlsycommonmodule.pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Criteria {

    // for paging
    private int pageNum;        // 페이지 번호(현재 페이지가 몇 페이지인지)
    private int amount;         // 한 화면에 출력한 페이지 개수

    // for search
    private String title;
    private String name;
    // 기본값 페이지 출력 개수 10개로 세팅, 시작 페이지는 당연히 1번이고
    public Criteria() {
        this.pageNum = 1;
        this.amount = 10;
    }
    
    public int getPageNum() {
        return (pageNum - 1) * this.amount;
    }

    public Criteria(int pageNum, int amount, String title, String name) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.title = title;
        this.name = name;
    }
}
