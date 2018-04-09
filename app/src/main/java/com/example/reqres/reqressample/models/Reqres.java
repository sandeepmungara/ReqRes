package com.example.reqres.reqressample.models;

/**
 * Created by sandeep on 05/04/18.
 */

public class Reqres {

    private int page;

    private int per_page;

    private int total_pages;

    private User[] data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return per_page;
    }

    public void setPerPage(int perPage) {
        this.per_page = perPage;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int totalPages) {
        this.total_pages = totalPages;
    }

    public User[] getUsere() {
        return data;
    }

    public void setUsere(User[] usere) {
        this.data = usere;
    }
}
