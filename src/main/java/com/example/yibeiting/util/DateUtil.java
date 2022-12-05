package com.example.yibeiting.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static Date strToDateyyyMMdd(String strDate) throws ParseException {
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
        Date nowDate = fdf.parse(strDate);
        LocalDate localDate = nowDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return java.sql.Date.valueOf(localDate);
    }
}
