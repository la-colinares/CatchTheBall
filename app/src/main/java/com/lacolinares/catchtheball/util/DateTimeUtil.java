package com.lacolinares.catchtheball.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Colinares on 5/31/2019.
 */

public class DateTimeUtil {

    public static String getDate() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return format.format(calendar.getTimeInMillis());
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a", Locale.US);

        return format.format(calendar.getTimeInMillis());
    }



}
