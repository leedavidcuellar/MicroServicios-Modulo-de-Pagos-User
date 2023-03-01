package com.Microservice.ModulosPagosUser.utils;

public final class Utils {

    public Utils() {
    }

    public static Boolean verifyNumber(String number){
        try {
            Double.parseDouble(number);
            return true;
        }catch (NumberFormatException e){
            e.getMessage();
            return false;
        }
    }

}
