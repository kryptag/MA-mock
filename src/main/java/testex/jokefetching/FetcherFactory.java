package testex.jokefetching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetcherFactory implements IFetcherFactory {

    private final List<String> availableTypes =
            Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes(){ return availableTypes;}

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {

        List list = new ArrayList();
        list.add(new EduJoke());
        list.add(new ChuckNorris());
        list.add(new Moma());
        list.add(new Tambal());
        return list;
    }
}
