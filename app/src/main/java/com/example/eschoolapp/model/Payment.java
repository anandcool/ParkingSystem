package com.example.eschoolapp.model;

public class Payment {
    String companyName,license,contact,payment;

    public Payment(String companyName, String license, String contact, String payment) {
        this.companyName = companyName;
        this.license = license;
        this.contact = contact;
        this.payment = payment;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
