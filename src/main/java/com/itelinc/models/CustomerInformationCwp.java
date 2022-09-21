package com.itelinc.models;

import com.opencsv.bean.CsvBindByName;

public class CustomerInformationCwp {

    @CsvBindByName(column = "customer")
    private String customer;
    @CsvBindByName(column = "controlNumber")
    private String controlNumber;
    @CsvBindByName(column = "custId")
    private String custID;
    @CsvBindByName(column = "address")
    private String address;
    @CsvBindByName(column = "dateRcvd")
    private String dateRcvd;
    @CsvBindByName(column = "dateInv")
    private String dateInv;
    @CsvBindByName(column = "addCust")
    private String addCust;
    @CsvBindByName(column = "adjuster")
    private String adjuster;
    @CsvBindByName(column = "rep")
    private String rep;
    @CsvBindByName(column = "comments")
    private String comments;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateRcvd() {
        return dateRcvd;
    }

    public void setDateRcvd(String dateRcvd) {
        this.dateRcvd = dateRcvd;
    }

    public String getDateInv() {
        return dateInv;
    }

    public void setDateInv(String dateInv) {
        this.dateInv = dateInv;
    }

    public String getAddCust() {
        return addCust;
    }

    public void setAddCust(String addCust) {
        this.addCust = addCust;
    }

    public String getAdjuster() {
        return adjuster;
    }

    public void setAdjuster(String adjuster) {
        this.adjuster = adjuster;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
