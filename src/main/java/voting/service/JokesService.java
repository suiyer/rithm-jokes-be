package voting.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import voting.response.JokesResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Component
public class JokesService {

    private static final String JOKES_URL = "https://icanhazdadjoke.com/";

    private Map<Integer, Set<JokesResponse.Joke>> upvotesToJoke = new TreeMap<>();
    private Map<Integer, Set<JokesResponse.Joke>> downvotesToJoke = new TreeMap<>();
    private Set<JokesResponse.Joke> upvotedJokes = new HashSet<>();
    private Set<JokesResponse.Joke> downvotedJokes = new HashSet<>();
    private Map<String, JokesResponse.Joke> allJokes = new HashMap<>();

    public List<JokesResponse.Joke> getJokes() throws IOException {
        URL jokesAPI = new URL(JOKES_URL);
        Set<JokesResponse.Joke> jokes = new HashSet<>();
        while (jokes.size() < 20) {
            URLConnection connection = jokesAPI.openConnection();
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("User-Agent", "My local application");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()))) {
                String jokeJson = "";
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    jokeJson = jokeJson + inputLine;
                }
                    JsonObject jsonObject = new JsonParser().parse(jokeJson).getAsJsonObject();
                    String jokeId = jsonObject.get("id").getAsString();
                    String text = jsonObject.get("joke").getAsString();
                    JokesResponse.Joke joke = new JokesResponse.Joke(jokeId, text);
                    jokes.add(joke);
                    allJokes.put(jokeId, joke);
            }
        }
        return new ArrayList<JokesResponse.Joke>(jokes);
    }

    public List<JokesResponse.Joke> getTopJokes() {
        List<JokesResponse.Joke> jokes = new ArrayList<>(upvotedJokes);
        Collections.sort(jokes, new Comparator<JokesResponse.Joke>() {
            @Override
            public int compare(JokesResponse.Joke o1, JokesResponse.Joke o2) {
                return o1.getUpvotes() == o2.getUpvotes() ? 0 : o1.getUpvotes() < o2.getUpvotes() ? 1 : -1;
            }
        });
        return jokes.size() <= 5 ? jokes : jokes.subList(0, 5);
    }

    public List<JokesResponse.Joke> getBottomJokes() {
        List<JokesResponse.Joke> jokes = new ArrayList<>(downvotedJokes);
        Collections.sort(jokes, new Comparator<JokesResponse.Joke>() {
            @Override
            public int compare(JokesResponse.Joke o1, JokesResponse.Joke o2) {
                return o1.getDownvotes() == o2.getDownvotes() ? 0 : o1.getDownvotes() < o2.getDownvotes() ? 1 : -1;
            }
        });
        return jokes.size() <= 5 ? jokes : jokes.subList(0, 5);
    }

    public int vote(boolean isUpvote, String jokeId) {
        JokesResponse.Joke joke = allJokes.get(jokeId);
       if (joke == null) {
           return 0;
       }

       if (isUpvote) {
           if (upvotedJokes.contains(joke)) {
               upvotedJokes.remove(joke);
           }
           int jokeVotes = joke.getUpvotes() + 1;
           joke.setUpvotes(jokeVotes);
           upvotedJokes.add(joke);
       } else {
           if (downvotedJokes.contains(joke)) {
               downvotedJokes.remove(joke);
           }
           int jokeVotes = joke.getDownvotes() + 1;
           joke.setDownvotes(jokeVotes);
           downvotedJokes.add(joke);
       }

       return 1;
    }
}
