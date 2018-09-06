# rithm-jokes-be
Backend for a Jokes API backed by icanhazdadjokes

Setup Instructions
====
* Install Java JDK 8 and Maven 3
* Clone the project - `git clone https://github.com/suiyer/rithm-jokes-be.git`
* Build the project using `mvn clean install`
* Run the API server with `java -jar target/jokesVoting-1.0-SNAPSHOT.jar`

Implementation Details
====
* Used Spring Boot 2.0 for the Rest service.

API Endpoints
====
* List random jokes with `localhost:8080/jokes`
* List random jokes with `localhost:8080/topjokes`
* List random jokes with `localhost:8080/bottomjokes`
* Upvote a joke with `localhost:8080/jokes/{:jokeId}/vote`
* Downvote a joke with `localhost:8080/jokes/{:jokeId}/vote?isUpvote=false`

Design
====
* It's a very simple design. 
* Jokes are fetched from the icanhazdadjokes API and stored in a map to ensure unique jokes are returned.
* The list of upvoted jokes is sorted every time a joke is upvoted to return the top 5 jokes.
* The list of downvoted jokes is sorted every time a joke is downvoted to return the bottom 5 jokes.
