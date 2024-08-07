package com.yksj.monitor.vo;

import lombok.Data;
import lombok.Setter;

import java.util.List;

//@Data
public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int total;
    private List<T> data;

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }


    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }
}
