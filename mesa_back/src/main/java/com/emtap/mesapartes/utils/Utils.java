package com.emtap.mesapartes.utils;

public class Utils {
    public static String generateRandomLetter(int length){
        String [] _cadena = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M","N","O","P","Q","R","S","T","U","V","W", "X","Y","Z","0","1",
                "2","3","4","5","6","7","8","9"};
        String [] cadena = {"0","1",
                "2","3","4","5","6","7","8","9"};
        String  randomString = "";
        try {
            for (int i = 0; i < length; i++) {
                int index = (int) Math.round(Math.random() * 9 ) ;
                randomString += cadena[index];
            }
        } catch (Exception e) {
            randomString = "0";
        }
        return randomString;
    }
}
