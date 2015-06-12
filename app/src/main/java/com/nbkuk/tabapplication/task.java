package com.nbkuk.tabapplication;

/**
 * Created by jason.dobo on 29/05/2015.
 */
public class task {

    private int id;
    private String task;
    private int status;
    private int date;

    public task(){}

    public task(String task, int status, int date) {
        super();
        this.task = task;
        this.status = status;
        this.date = date;
    }

    //getters & setters
    @Override
    public String toString() {
        return "Book [id=" + id + ", task=" + task + ", status=" + status + ", date=" + date + "]";
    }
}
