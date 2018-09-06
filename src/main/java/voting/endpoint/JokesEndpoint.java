package voting.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voting.response.JokeVoteResponse;
import voting.response.JokesResponse;
import voting.service.JokesService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

@RestController
public class JokesEndpoint {
    @Autowired
    private JokesService service;

    @CrossOrigin
    @RequestMapping("/jokes")
    public JokesResponse jokes()
            throws MalformedURLException, IOException {
        JokesResponse response = new JokesResponse();

        response.setJokes(service.getJokes());
        return response;
    }

    @CrossOrigin
    @RequestMapping("/jokes/{jokeId}/vote")
    public JokeVoteResponse vote(@PathVariable String jokeId, @RequestParam(value = "isUpvote", defaultValue = "true") boolean isUpvote)
            throws SQLException {
        int numSongsVotedOn = service.vote(isUpvote, jokeId);
        if (numSongsVotedOn < 1) {
            throw new IllegalArgumentException("Joke with ID " + jokeId + " does not exist.");
        }

        return new JokeVoteResponse(true);
    }

    @CrossOrigin
    @RequestMapping("/topjokes")
    public JokesResponse topJokes() {
        JokesResponse response = new JokesResponse();

        response.setJokes(service.getTopJokes());
        return response;
    }

    @CrossOrigin
    @RequestMapping("/bottomjokes")
    public JokesResponse bottomJokes() {
        JokesResponse response = new JokesResponse();

        response.setJokes(service.getBottomJokes());
        return response;
    }

    /**
     * Handles any IllegalArgumentException thrown by the controller.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ApiError(badRequest, ex.getLocalizedMessage()), new HttpHeaders(), badRequest);
    }

    /**
     * Handles all Exceptions other than IllegalArgumentException.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOtherExceptions(Exception ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ApiError(internalServerError, ex.getLocalizedMessage()), internalServerError);
    }

    /**
     * Class that encloses the error to be returned to the user.
     */
    public static class ApiError {

        private HttpStatus status;
        private String message;

        ApiError(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
