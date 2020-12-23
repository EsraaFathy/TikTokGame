/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Mohamed
 */
public class JsonHandler {
    
    
    private void s(){
        
        // REGISTER
        /*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "LOGIN");
        jsonObject.put("EMAIL","mohamed");
        jsonObject.put("PASSWORD","abcd");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        
        // SEND   jsonText  TO SERVER
        
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "REGISTER");
        jsonObject.put("NAME",user name);
        jsonObject.put("EMAIL",user email);
        jsonObject.put("PASSWORD","mohamed");
        
        String jsonText = JSONValue.toJSONString(jsonObject); 
        
                // SEND   jsonText  TO SERVER

    
        // LOGIN
    /*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "LOGIN_REPLAY");
        jsonObject.put("KEY", "SUCCESS");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "LOGIN_REPLAY");
        jsonObject.put("KEY", "INCORRECT_PASSWORD");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "LOGIN_REPLAY");
        jsonObject.put("KEY", "EMAIL_NOT_FOUND");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        
        
        
        
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "REGISTER_REPLAY");
        jsonObject.put("KEY", "SUCCESS");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "REGISTER_REPLAY");
        jsonObject.put("KEY", "EMAIL_IS_USED_BEFORE");
        String jsonText = JSONValue.toJSONString(jsonObject);  
        
        ////////////////////////////////////////////////////////////////////////////
        Object obj = new JSONParser().parse(jsonText); 
        JSONObject jo2 = (JSONObject) obj; 
        String firstName = (String) jo2.get("firstName"); 
        String lastName = (String) jo2.get("lastName"); 
        
        
        Object obj = new JSONParser().parse(jsonText); 
        JSONObject jo2 = (JSONObject) obj; 
        String firstName = (String) jo2.get("firstName"); 
        String lastName = (String) jo2.get("lastName"); 
        
        
        
        
        ////////////////////////////////////////////////////////////////////////////
        
        Object obj = new JSONParser().parse(jsonText); 
        JSONObject jo2 = (JSONObject) obj; 
        String type = (String) jo2.get("TYPE"); 
        String key = (String) jo2.get("KEY"); 
        
        switch (type) {
            case "LOGIN_REPLAY":
                if(key=="SUCCESS")
                {
                    //////
                }
                else if(key=="INCORRECT_PASSWORD")
                {
                    //////
                }
                else if (key=="EMAIL_NOT_FOUND")
                {
                    
                }
                break;
                

            case "REGISTER_REPLAY":
                if(key=="SUCCESS")
                {
                    //////
                }
                else if(key=="EMAIL_IS_USED_BEFORE")
                {
                    //////
                }
                break;

        }
        
        
        
        
        
        Object obj = new JSONParser().parse(jsonText); 
        JSONObject jo2 = (JSONObject) obj; 
        String type = (String) jo2.get("TYPE"); 
        
        
        switch (type) {
            case "LOGIN":
                String email = (String) jo2.get("EMAIL"); 
                String password = (String) jo2.get("PASSWORD"); 
                
                // do login (email,password)
                break;
                

            case "REGISTER":
                String name = (String) jo2.get("NAME"); 
                String email = (String) jo2.get("EMAIL"); 
                String password = (String) jo2.get("PASSWORD"); 
                
                // do register (name,email,password);

                break;

        }
        
        
        
        
        // REGISTER

*/
        
        
    
}
}