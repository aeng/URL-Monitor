/**
 * 
 */
package org.urlMonitor.util;

import java.util.Date;

import org.joda.time.*;
import org.joda.time.format.*;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
public final class DateUtil
{
   private final static String DATE_TIME_SHORT_PATTERN = "dd/MM/yy HH:mm";
   private final static String TIME_SHORT_PATTERN = "hh:mm:ss";

   /**
    * Format date to dd/MM/yy hh:mm a
    * @param date
    * @return
    */
   public static String formatShortDate(Date date)
   {
      if(date != null)
      {
         DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_TIME_SHORT_PATTERN);
         return fmt.print(new DateTime(date));
      }
      return null;
   }
   
   /**
    * Format date to hh:mm:ss
    * @param date
    * @return
    */
   public static String formatTime(Date date)
   {
      if(date != null)
      {
         DateTimeFormatter fmt = DateTimeFormat.forPattern(TIME_SHORT_PATTERN);
         return fmt.print(new DateTime(date));
      }
      return null;
   }

   public static String getHowLongAgoDescription(Date startDate, Date endDate)
   {
      Long milliseconds = endDate.getTime() - startDate.getTime();
      Duration duration = new Duration(milliseconds);
      PeriodFormatter formatter = new PeriodFormatterBuilder()
              .appendDays()
              .appendSuffix("d ")
              .appendHours()
              .appendSuffix("h ")
              .appendMinutes()
              .appendSuffix("m ")
              .appendSeconds()
              .appendSuffix("s ")
              .toFormatter();
      return formatter.print(duration.toPeriod()) + getDateDiffSuffix(milliseconds) ;
   }

   private static String getDateDiffSuffix(Long milliseconds)
   {
      if(milliseconds < 0)
      {
         return " incoming ";
      }
      return " ago ";
   }
}
