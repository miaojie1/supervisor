package com.xinguan.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhan
 * @date 2019-04-06 14:25
 */
public class PageInfo<T> {

    private Page<T> page;

    public PageInfo(Page<T> page, Map<String, Object> param) {
        this.page = page;
        this.condition = param;
        intiData();
    }

    //默认每页的记录数为10条
    private static final int PAGE_SIZE = 10;

    //总记录数
    private long totalElements;
    //总页数
    private int totalPages;
    private List<T> content;
    private int pageSize;
    private int pageNo;
    private Sort sort;
    //查询条件
    private Map<String, Object> condition = new HashMap<>();

    private void intiData() {
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        content = page.getContent();
        sort = page.getSort();
        pageSize = page.getNumberOfElements();
        pageNo = page.getNumber() + 1;
        page = null;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
