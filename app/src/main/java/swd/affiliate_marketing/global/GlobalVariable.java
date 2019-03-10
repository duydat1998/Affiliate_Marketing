package swd.affiliate_marketing.global;

import android.app.Application;

import swd.affiliate_marketing.model.Publisher;

public class GlobalVariable extends Application {
    public Publisher publisher;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomPromotioncode(String input){
        //promotionCode has maximum 15 characters
        String promotionCode = "";
        if(input.length() < 15){
            promotionCode += input;
        } else {
            promotionCode += input.substring(0,5);
        }
        int other = 15 - promotionCode.length();
        for(int i=0; i<other; i++){
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            promotionCode += ALPHA_NUMERIC_STRING.charAt(character);
        }
        return promotionCode.toUpperCase();
    }
}
