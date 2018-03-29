
package testex;
import testex.jokefetching.IFetcherFactory;
import testex.jokefetching.IJokeFetcher;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher {

  private SimpleDateFormat simpleFormat;
  private IFetcherFactory factory;

  public JokeFetcher(SimpleDateFormat sdf, IFetcherFactory factory) {
    this.simpleFormat = sdf;
    this.factory = factory;
  }

  /**
   * Verifies whether a provided value is a valid string (contained in availableTypes)
   * @param jokeTokens Example (with valid values only): "eduprog,chucknorris,chucknorris,moma,tambal"
   * @return true if the param was a valid value, otherwise false
   */
  public boolean isStringValid(String jokeTokens){
    String[] tokens = jokeTokens.split(",");
      for(String token: tokens){
      if(!factory.getAvailableTypes().contains(token)){
        return false;
      }
    }
    return true;
  }
  
  /**
   * Fetch jokes from external API's as given in the input string - jokesToFetch
   * @param jokesToFetch A comma separated string with values (contained in availableTypes) indicating the jokes
   * to fetch. Example: "eduprog,chucknorris,chucknorris,moma,tambal" will return five jokes (two chucknorris)
   * @param timeZone Must be a valid timeZone string as returned by: TimeZone.getAvailableIDs()
   * @return A Jokes instance with the requested jokes + time zone adjusted string representing fetch time
   * (the jokes list can contain null values, if a server did not respond correctly)
   * @throws JokeException Thrown if either of the two input arguments contains illegal values
   */
  public Jokes getJokes(String jokesToFetch, String timeZone, IDateFormatter formatter) throws JokeException {
    if(!isStringValid(jokesToFetch)) {
      throw new JokeException("Inputs (jokesToFetch) contain types not recognized");
    }
    Jokes jokes = new Jokes();
    for (IJokeFetcher fetcher : factory.getJokeFetchers(jokesToFetch)) {
      jokes.addJoke(fetcher.getJoke());
    }
    String tzString = formatter.getFormattedDate(timeZone, this.simpleFormat ,new Date());
    jokes.setTimeZoneString(tzString);
    return jokes;
  }

}
