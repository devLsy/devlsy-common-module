package common.devlsycommonmodule.pagination;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
// 페이지네이션 화면 처리를 위한 공통 모듈
public class PageMaker {
    private int startPage;  //페이징 화면 하단의 시작 번호(5페이지라고 하면 [1][2][3][4][5] 여기서 제일 첫 번 째 시작번호인 [1])
    private int endPage;    //페이징 화면 하단의 끝 번호(5페이지라고 하면 [1][2][3][4][5] 여기서 제일 끝 번호인 [5])
    private boolean prev, next;     // 이전, 다음 존재 여부

    private int totalCount;         // 전체 게시글 수
    private Criteria cri;      // 프론트에서 전달하는 pageNum(현재 페이지), amount(출력 페이지) 전달 역할

    /**
     * *
     * @param totalCount
     * @param cri
     */
    public PageMaker(int totalCount, Criteria cri) {
        this.totalCount = totalCount;
        this.cri = cri;
        // 화면에 출력할 페이지 개수는 10개인 경우에 해당
        this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0) * 10);
        this.startPage = this.endPage - 9;
        // 실제 끝 번호
        int realEnd = (int)(Math.ceil(totalCount * 1.0/ cri.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

    }
}
