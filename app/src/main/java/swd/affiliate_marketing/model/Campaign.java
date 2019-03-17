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

    public Campaign(String campaignID, String campaignName, String banner, String campaignContent, String advertiserID, String startDate, String endDate, double percentDiscount, double minBill, double maxAmountDiscount, double percentComission, boolean isWorking, String advertiserName) {
        this.campaignID = campaignID;
        this.campaignName = campaignName;
        this.banner = banner;
        this.campaignContent = campaignContent;
        this.advertiserID = advertiserID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentDiscount = percentDiscount;
        this.minBill = minBill;
        this.maxAmountDiscount = maxAmountDiscount;
        this.percentComission = percentComission;
        this.isWorking = isWorking;
        this.advertiserName = advertiserName;
    }

    public String getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(String campaignID) {
        this.campaignID = campaignID;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCampaignContent() {
        return campaignContent;
    }

    public void setCampaignContent(String campaignContent) {
        this.campaignContent = campaignContent;
    }

    public String getAdvertiserID() {
        return advertiserID;
    }

    public void setAdvertiserID(String advertiserID) {
        this.advertiserID = advertiserID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public double getMinBill() {
        return minBill;
    }

    public void setMinBill(double minBill) {
        this.minBill = minBill;
    }

    public double getMaxAmountDiscount() {
        return maxAmountDiscount;
    }

    public void setMaxAmountDiscount(double maxAmountDiscount) {
        this.maxAmountDiscount = maxAmountDiscount;
    }

    public double getPercentComission() {
        return percentComission;
    }

    public void setPercentComission(double percentComission) {
        this.percentComission = percentComission;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }
}
