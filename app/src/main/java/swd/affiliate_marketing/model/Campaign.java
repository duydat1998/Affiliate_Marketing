package swd.affiliate_marketing.model;


import java.io.Serializable;

public class Campaign implements Serializable {
    public String campaignID ;
    public String campaignName ;
    public String banner ;
    public String campaignContent ;
    public String advertiserID ;
    public String startDate ;
    public String endDate ;
    public double percentDiscount ;
    public double minBill ;
    public double maxAmountDiscount ;
    public double percentComission ;
    public boolean isWorking ;
    public String advertiserName;
}
