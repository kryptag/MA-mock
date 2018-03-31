import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import testex.IDateFormatter;
import testex.JokeException;
import testex.JokeFetcher;
import testex.Jokes;
import testex.jokefetching.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    private JokeFetcher fetch;
    String[] invalidTokens;
    @Mock
    IDateFormatter dateFormatter;
    @Mock
    EduJoke eduJokeMock;
    @Mock
    ChuckNorris chuckMock;
    @Mock
    Moma momaMock;
    @Mock
    Tambal tambalMock;
    @Mock
    IFetcherFactory factory;


    @Before
    public void setup() {
        fetch = new JokeFetcher(new SimpleDateFormat("dd MMM yyyy hh:mm aa"), factory);
        invalidTokens = new String[]{"ieduprog", "ichucknorris", "imoma", "itambal"};
        when(factory.getJokeFetchers(any())).thenReturn(Arrays.asList(eduJokeMock, chuckMock, momaMock, tambalMock));
        when(factory.getAvailableTypes()).thenReturn(Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal"));
    }

    @Test
    @DisplayName("Factory returns objects of isntance IJokeFetcher")
    public void factoryTest() {
        List<IJokeFetcher> result = factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertAll("Checking each iJokeFetcher", () -> {
            assertEquals(4, result.size());
            result.forEach(jokeFetcher -> {
                assertTrue(jokeFetcher instanceof IJokeFetcher);
            });
        });
    }

    @Test
    @DisplayName("Checking available types")
    public void getAvailableTypesTest() {
        assertAll("Testing the arraylist has all the types", () -> {
            List<String> list = factory.getAvailableTypes();
            assertFalse(list.isEmpty());
            factory.getAvailableTypes().forEach(token -> assertTrue(list.contains(token)));
        });
    }

    @Test
    @DisplayName("Checking String")
    public void isStringValidTest() {
        assertAll("Testing for tokens", () -> {
            factory.getAvailableTypes().forEach(token -> assertTrue(fetch.isStringValid(token)));
            Arrays.stream(invalidTokens).forEach(invalidToken -> assertFalse(fetch.isStringValid(invalidToken)));
        });
    }

    @Test
    @DisplayName("Getting Jokes")
    public void getJokesTest() throws JokeException {
        when(dateFormatter.getFormattedDate(any(), any(), any())).thenReturn("Europe/Copenhagen");
        Jokes jokes = fetch.getJokes("EduJoke,ChuckNorris,ChuckNorris,Moma,Tambal", "Europe/Copenhagen", dateFormatter);
        assertAll("Checking the state in Jokes", () -> {
            assertFalse(jokes.getJokes().isEmpty());
            assertEquals("Europe/Copenhagen", jokes.getTimeZoneString());
            verify(dateFormatter, times(1)).getFormattedDate(any(), any(), any());
            verify(factory, times(1)).getJokeFetchers(any());
        });
        assertAll("Checking each joke", () -> {
            jokes.getJokes().forEach(joke -> {
                if (joke != null) {
                    assertFalse(joke.getJoke().isEmpty());
                    assertFalse(joke.getReference().isEmpty());
                }
            });
        });
    }
}
