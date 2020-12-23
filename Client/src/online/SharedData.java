/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;

import Models.Player;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Mohamed
 */
public class SharedData {
   
    public static String myEmail;
    public static String targetEmail;

    public static String player1Name;
    public static String player2Name;

    public static String player1Email;
    public static String player2Email;

    private ArrayList<String> jsonArray = new ArrayList<>();

    private void createJson() {

        ArrayList<String> jsonArray = new ArrayList<>();
        ArrayList<Player> onlineList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "ONLINEPLAYERS");
        for (int i = 0; i < onlineList.size(); i++) {
            Map p = new LinkedHashMap(4);

            p.put("NAME", onlineList.get(i).getName());
            p.put("EMAIL", onlineList.get(i).getEmail());
            p.put("SCORE", onlineList.get(i).getScore());
            p.put("STATUS", onlineList.get(i).getStatus());

            jsonObject.put("PLAYER", p);
            String jsonText = JSONValue.toJSONString(jsonObject);
            jsonArray.add(jsonText);
        }
          //        ps.println(jsonArray);
          //         ps.flush();
          //
          //System.out.println(jsonArray);
            
    }

    // putting data to JSONObject 
    private void readJson() throws ParseException {
        /* ArrayList<Player> onlineList = new ArrayList<>();
        Player player;*/
        ArrayList<String> jsonArray = new ArrayList<>();

        Object obj = new JSONParser().parse(jsonArray.get(1));
        JSONObject jo2 = (JSONObject) obj;
        String key = (String) jo2.get("KEY");

        for (int i = 0; i < jo2.size(); i++) {
            String name = (String) jo2.get("NAME");
            String email = (String) jo2.get("EMAIL");
            String score = (String) jo2.get("SCORE");

            System.out.println("name= "+name );
        }
    }

}
