/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Models.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tictokserver.DataBaseHandling;

/**
 *
 * @author Mohamed
 */


     
public class LoginHandler {
    
    private void handleLogin(String data)
    {
        
    }
    private void handleRegister()
    {
        
    }
    private void handleAskToPlay()
    {
        
    }
    private void handleResponseToPlay()
    {
        
    }
    private void newPlayerconnected()
    {
        
    }
    
    /*
    private void handleUpdatePlayerPoints(String jsonStr) throws ParseException {
            
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Type", "LoginReplay");
        jsonObject.put("key", "Sucess");
        

        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        
        Object obj = new JSONParser().parse(jsonText); 
        
        JSONObject jo2 = (JSONObject) obj; 
        
        String firstName = (String) jo2.get("firstName"); 
        String lastName = (String) jo2.get("lastName"); 
        long age = (long) jo2.get("age"); 
    }

    
    private void handleMessageFromClient(String jsonText) throws ParseException {
        
                
        Object obj = new JSONParser().parse(jsonText); 
        
        JSONObject jo2 = (JSONObject) obj; 
        
        String type  = (String) jo2.get("Type"); 
        String key = (String) jo2.get("key"); 
        long age = (long) jo2.get("age"); 
        
        switch (type) {
            case "LoginReplay":
                if(key=="sucess")
                    // show 
                    if(Key== incorect password)
                //ghdklsfdj
                
                
               //handleLogin(jsonStr);
                break;

            case "register":
                handleRegister(jsonStr);
                break;

            case "player1 ask to play":
                handleAskToPlay(jsonStr);
                break;

            case "player2 respone":
                handleResponseToPlay(jsonStr);
                break;
        }
    }
    
    
    
    public String LogIn(String mail, String password){
        String login="notfound";
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER WHERE NAME = ? & EMAIL = ?");
            s.setString(1, name);
            s.setString(2, mail);
            ResultSet resultSet=s.executeQuery();
            if(resultSet.next()){
            
            login=resultSet.getString(1)+"#"+resultSet.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    } 
    */
}
