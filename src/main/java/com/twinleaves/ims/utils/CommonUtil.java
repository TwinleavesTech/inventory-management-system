package com.twinleaves.ims.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    /**
     * Returns date from given String.
     * @param dateStr String
     * @param dateFormat String
     * @return Date
     * @throws ParseException
     */
    public static Date getDateFromString(final String dateStr, final String dateFormat) throws ParseException {
        Date date = null;
        SimpleDateFormat simpleDateFormat;
        if (!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(dateFormat)) {
            simpleDateFormat = new SimpleDateFormat(dateFormat);
            date = simpleDateFormat.parse(dateStr);
        }
        return date;
    }
}
