package ninja.paranoidandroid.ttm2.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by anton on 18.12.16.
 */

public class TimeStamp {

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    public String getCurrentTime(){

        Calendar calendar = Calendar.getInstance();

        return calendar.getTime().toString();
    }

    public String getSimpleTimeStamp(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

}
