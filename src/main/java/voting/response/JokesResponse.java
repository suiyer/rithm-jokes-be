package voting.response;

import java.util.List;
import java.util.Objects;

public class JokesResponse {
    List<Joke> jokes;

    public List<Joke> getJokes() {
        return jokes;
    }

    public void setJokes(List<Joke> jokes) {
        this.jokes = jokes;
    }

    public static class Joke {
        private String id;
        private String text;
        private int upvotes;
        private int downvotes;

        public Joke(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setTitle(String text) {
            this.text = text;
        }

        public int getUpvotes() {return upvotes;}

        public void setUpvotes(int votes){this.upvotes = votes;}

        public int getDownvotes() {
            return downvotes;
        }

        public void setDownvotes(int downvotes) {
            this.downvotes = downvotes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Joke joke = (Joke) o;
            return Objects.equals(id, joke.id) &&
                    Objects.equals(text, joke.text);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, text);
        }

        @Override
        public String toString() {
            return "Joke{" +
                    "id='" + id + '\'' +
                    ", text='" + text + '\'' +
                    ", upvotes=" + upvotes +
                    ", downvotes=" + downvotes +
                    '}';
        }
    }
}
