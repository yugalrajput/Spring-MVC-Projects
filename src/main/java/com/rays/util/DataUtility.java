package com.rays.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtility {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static Date stringToDate(String value) {
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date value) {

        return sdf.format(value);

    }


}
