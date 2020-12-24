/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import Models.Records;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Mohamed
 */
public class FileWriterController {

//    public static void WriteJson(String firstname, String secondname,
//            String firstseq, String secondseq, String thewinner, String FirstPlay) {
//
//        JSONObject record = new JSONObject();
//        record.put("firstPlayerName", firstname);
//        record.put("secondPlayerName", secondname);
//        record.put("firstSequence", firstseq);
//        record.put("secondSequence", secondseq);
//        record.put("theWinner", thewinner);
//        record.put("FirstPlay", FirstPlay);
//
//        JSONObject recObj = new JSONObject();
//        recObj.put("player", record);
//
//        JSONArray recordList = new JSONArray();
//        recordList.add(recObj);
//
//        try (FileWriter file = new FileWriter("records.json", true)) {
//            file.append(recordList.toJSONString());
//            file.flush();
//        } catch (IOException ex) {
//            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public static void readJson() {
//        JSONParser jsonP = new JSONParser();
//        JSONArray plList = null;
//
//        try (FileReader reader = new FileReader("records.json")) {
//            //Read JSON File
//            Object obj = jsonP.parse(reader);
//            plList = (JSONArray) obj;
//            System.out.println(plList);
//            //Iterate over emp array
//
//            for (Object object : plList) {
//                parseEmpObj((JSONObject) object);
//            }
////            
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        //return plList;
//    }
//
//    private static void parseEmpObj(JSONObject emp) {
//        JSONObject empObj = (JSONObject) emp.get("player");
//        //get emp firstname, lastname, website
//        String firstname = (String) empObj.get("firstPlayerName");
//        String secondname = (String) empObj.get("secondPlayerName");
//        String firstseq = (String) empObj.get("firstSequence");
//        String secondseq = (String) empObj.get("secondSequence");
//        String thewinner = (String) empObj.get("theWinner");
//        String playfirst = (String) empObj.get("FirstPlay");
//
//        Records h = new Records();
//
//        h.setFirstName(firstname);
//        h.setSecondName(secondname);
//        h.setFirstSeq(firstseq);
//        h.setSeconSeq(secondseq);
//        h.setTheWinner(thewinner);
//        h.setPlayFirst(playfirst);
//
//        System.out.println(firstname);
//        System.out.println(firstname);
//        System.out.println(firstname);
//        System.out.println(firstname);
//        System.out.println(firstname);
//
//        //return h;
//    }
    
    
    //*************************************************************************************//
    // record one game
    //****************************************************************************************//
    static Map recordOneGame(String firstname, String secondname, String firstseq, String secondseq, String thewinner, String FirstPlay) {
        //JSONObject record = new JSONObject();
        Map record = new LinkedHashMap(6);
        record.put("firstPlayerName", firstname);
        record.put("secondPlayerName", secondname);
        record.put("firstSequence", firstseq);
        record.put("secondSequence", secondseq);
        record.put("theWinner", thewinner);
        record.put("FirstPlay", FirstPlay);
        return record;
    }


    //*************************************************************************************//
    // create the file for the first time
    //****************************************************************************************//
    public static void createGamefile() {
        try {
            FileWriter h = new FileWriter("records.txt");
            h.write("{\"GAME\":[]}");
            h.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //*************************************************************************************//
    // read data from file
    //****************************************************************************************//
    static String readTheFileJson() {
        String line = "";

        try {
            ///// \\\\\\\\\\\\\
            BufferedReader data = new BufferedReader((new FileReader("records.txt")));
            while ((line = data.readLine()) != null) {
                System.out.println(line);
                return line;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(line);

        return line;
    }

    //*************************************************************************************//
    // get recorded file and add the new one
    //****************************************************************************************//
    public static void addTheRecordedGame(String firstname, String secondname, String firstseq, String secondseq, String thewinner, String FirstPlay) {
        String allFile = readTheFileJson();
        System.out.println("........" + allFile);
        Map map = recordOneGame(firstname, secondname, firstseq, secondseq, thewinner, FirstPlay);
        try {

            JSONParser jsonp = new JSONParser();
            JSONObject jSONObject1 = (JSONObject) jsonp.parse(allFile);
            JSONArray array = (JSONArray) jSONObject1.get("GAME");
            array.add(map);

            String AllFile = jSONObject1.toJSONString(jSONObject1);
            System.out.println("......" + AllFile);
            try (BufferedWriter file = new BufferedWriter(new FileWriter("records.txt"))) {
                file.write(AllFile);
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParseException ex) {
            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //*************************************************************************************//
    // get all records from file
    //****************************************************************************************//
    public static ArrayList<Records> getAllRecords() {
        ArrayList<Records> historys = new ArrayList<>();
        try {
            String fileAsString = readTheFileJson();
            JSONParser jSONParser = new JSONParser();
            JSONObject jSONObject = (JSONObject) jSONParser.parse(fileAsString);
            JSONArray array = (JSONArray) jSONObject.get("GAME");
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsono = (JSONObject) array.get(i);
                historys.add(new Records((String) jsono.get("firstPlayerName"),
                        (String) jsono.get("secondPlayerName"),
                        (String) jsono.get("firstSequence"),
                        (String) jsono.get("secondSequence"),
                        (String) jsono.get("theWinner"),
                        (String) jsono.get("FirstPlay")));
            }

        } catch (ParseException ex) {
            Logger.getLogger(FileWriterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historys;
    }
}
