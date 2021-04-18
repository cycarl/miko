package com.xana.acg.fac.model.api;
import java.util.List;

public class PageResult<T> {
    private int pageNum;
    private int pageSize;
    private long totalSize;
    private int totalPages;
    private List<T> content;

    public int getPageNum() {
        return pageNum;
    }
    public List<T> getContent() {
        return content;
    }


    public boolean hasMore(){
        return pageNum<totalPages;
    }


    @Override
    public String toString() {
        return "PageResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", totalPages=" + totalPages +
                ", content=" + content +
                '}';
    }
}