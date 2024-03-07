import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.ArrayList;
import org.json.JSONObject;

public class Movie {

    public static final String API_KEY = "bcaf055c";
    int ImdbVotes;
    ArrayList<String> actorsList;
    String rating;

    public Movie(ArrayList<String> actorsList, String rating, int ImdbVotes){
        this.actorsList = actorsList;
        this.rating = rating;
        this.ImdbVotes = ImdbVotes;
    }

    @SuppressWarnings("deprecation")
    /**
     * Retrieves data for the specified movie.
     *
     * @param title the name of the title for which MovieData should be retrieved
     * @return a string representation of the MovieData, or null if an error occurred
     */

    public String getMovieData(String title) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?t="+title+"&apikey="+API_KEY);
        URLConnection Url = url.openConnection();
        Url.setRequestProperty("Authorization", "Key" + API_KEY);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Url.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine())!=null) {
            stringBuilder.append(line);
        }
        reader.close();
        //handle an error if the chosen movie is not found
        return stringBuilder.toString();
    }

    public int getImdbVotesViaApi(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        String votes = (String) jsonObject.get("imdbVotes");
        votes = votes.replaceAll("[,.]", "");

        int votesCount = Integer.parseInt(votes);
        this.ImdbVotes =  votesCount;

        return votesCount;
    }

    public String getRatingViaApi(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        String rating = (String) jsonObject.get("imdbRating") + "/10";

        this.rating = rating;

        return rating;
    }

    public void getActorListViaApi(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        String actors = jsonObject.getString("Actors");
        String[] actorNames =  actors.split(", ");

//        Adding Actors to the ActorsList and Outputting them.
        int i = 1;
        System.out.println("\tActors: ");
        for(String name : actorNames){
            System.out.println("\t" + i + "-" + name);
            actorsList.add(name);
            i++;
        }
    }

    public String getYear(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Year");
    }

    public String getGenre(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Genre");
    }

    public String getCountry(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Country");
    }

    public String getRated(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Rated");
    }

    public String getTitle(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Title");
    }
}