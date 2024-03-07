import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class Actors {
    public static final String API_KEY = "WSqTpRe09KvWO4lFJ3Zu/w==gOxbU22JL68VwobK";
    String netWorth;
    Boolean isAlive;

    public Actors(String netWorth, boolean isAlive){
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name="+
                    name.replace(" ", "+")+"&apikey="+API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
//            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getNetWorthViaApi(String actorInfoJson){
        JSONObject jsonObject = new JSONObject(actorInfoJson.substring(1, actorInfoJson.length()-1));
        double netWorth = jsonObject.getDouble("net_worth");
        this.netWorth = Double.toString(netWorth);
        return netWorth;
    }

    public boolean isAlive(String actorInfoJson){
        JSONObject jsonObject = new JSONObject(actorInfoJson.substring(1, actorInfoJson.length()-1));
        boolean status;
        try {
            status = jsonObject.getBoolean("is_alive");
        }catch (Exception ex){
//            If there wasn't an is_alive key in json, determine isAlive by death key.
            try {
                jsonObject.getString("death");
                status = false;
            }catch (Exception e){
                status = true;
            }
        }
        this.isAlive = status;
        return status;
    }

    public String getDateOfDeathViaApi(String actorInfoJson){
        String date = "";
        JSONObject jsonObject = new JSONObject(actorInfoJson.substring(1, actorInfoJson.length()-1));

//        Handling Error if the death key was not on the json object.
        try {
            date = jsonObject.getString("death");
        }catch (Exception ex){
            date = "Unknown";
        }

        return date;
    }

    public String getNationality(String actorInfoJson){
        JSONObject jsonObject = new JSONObject(actorInfoJson.substring(1, actorInfoJson.length()-1));
        return jsonObject.getString("nationality");
    }

    public String getBirthday(String actorInfoJson){
        JSONObject jsonObject = new JSONObject(actorInfoJson.substring(1, actorInfoJson.length()-1));
        return jsonObject.getString("birthday");
    }

}