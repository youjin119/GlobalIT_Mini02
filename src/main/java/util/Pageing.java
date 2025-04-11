package util;

public class Pageing {
    public static String pagingStr(int totalCount, int pageSize, int blockPage,
            int pageNum, String reqUrl, String tag) { // tag 파라미터 추가
        String pagingStr = "";
        tag = tag.replace("#", "%23");

        //  전체 페이지 수 계산
        int totalPages = (int) (Math.ceil(((double) totalCount / pageSize)));

        // '이전 페이지 블록 바로가기' 출력
        int pageTemp = (((pageNum - 1) / blockPage) * blockPage) + 1;
        if (pageTemp != 1) {
            pagingStr += "<a href='" + reqUrl + "?pageNum=1&tag=" + tag + "'>&lt;&lt;</a>"; // tag 파라미터 추가
            pagingStr += " ";
            pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1) + "&tag=" + tag
                         + "'>&lt;</a>"; // tag 파라미터 추가
        }

        // 각 페이지 번호 출력
        int blockCount = 1;
        while (blockCount <= blockPage && pageTemp <= totalPages) {
            if (pageTemp == pageNum) {
                // 현재 페이지는 링크를 걸지 않음
                pagingStr += "<span> " + pageTemp + " </span>";
            } else {
                pagingStr += " <a href='" + reqUrl + "?pageNum=" + pageTemp + "&tag=" + tag
                             + "'>" + pageTemp + "</a> "; // tag 파라미터 추가
            }
            pageTemp++;
            blockCount++;
        }

        // '다음 페이지 블록 바로가기' 출력
        if (pageTemp <= totalPages) {
            pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + "&tag=" + tag
                         + "'>&gt;</a>"; // tag 파라미터 추가
            pagingStr += " ";
            pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages + "&tag=" + tag
                         + "'>&gt;&gt;</a>"; // tag 파라미터 추가
        }

        return pagingStr;
    }
}