package com.bjpowernode.p2p.model.vo;

import java.util.List;

public class PageVO<T> {
    private Integer totalPages;
    private Integer totalRows;
    private List<T> dataList;

    public PageVO() {
    }

    public PageVO(Integer totalPages, Integer totalRows, List<T> dataList) {
        this.totalPages = totalPages;
        this.totalRows = totalRows;
        this.dataList = dataList;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
