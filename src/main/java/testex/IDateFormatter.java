package testex;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IDateFormatter {

    public String getFormattedDate (String timezone, SimpleDateFormat formatter, Date time) throws JokeException;
}
