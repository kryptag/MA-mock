package testex;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter implements IDateFormatter {


/**
 * Returns a formatted string representing NOW, adjusted to the time zone string
 * passed in
 * @param timeZone Must be a valid time zone as returned by:TimeZone.getAvailableIDs()
 * @return Time Zone string formatted like ("dd MMM yyyy hh:mm aa") and adjusted to the provided
 * time zone
 * @throws JokeException If the timeZone string is not a valid string
 */
  public String getFormattedDate(String timeZone, SimpleDateFormat simpleFormat, Date time) throws JokeException  {
    if(!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)){
      throw new JokeException("Illegal Time Zone String");
    }
    simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
    return simpleFormat.format(time);
  }


}
