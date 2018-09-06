package voting.response;

public class JokeVoteResponse {
    public boolean isVotedSuccessfully() {
        return votedSuccessfully;
    }

    public void setVotedSuccessfully(boolean votedSuccessfully) {
        this.votedSuccessfully = votedSuccessfully;
    }

    private boolean votedSuccessfully;

    public JokeVoteResponse(boolean votedSuccessfully) {
        this.votedSuccessfully = votedSuccessfully;
    }
}
