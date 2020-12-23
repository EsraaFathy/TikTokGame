package tictokserver;

import Models.Player;
import Models.Record;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerNetwork {

    private static ServerNetwork instanceServer;
    private static ServerSocket myServerSocket;
    private static Socket s;
    private static String msg;
    Handler h;

    private ServerNetwork() {
// handle vector
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    myServerSocket = new ServerSocket(5005);

                    while (true) {
                        System.out.println("server conastracror");
                        s = myServerSocket.accept();
                        h = new Handler(s);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }

    public static ServerNetwork getInstance() {
        if (instanceServer == null) {
            instanceServer = new ServerNetwork();
        }

        return instanceServer;
    }

    private void jesonParsing(String msg, PrintStream ps) {

        System.out.println("here" + msg);

        try {
            Object obj = new JSONParser().parse(msg);
            JSONObject jo2 = (JSONObject) obj;
            String type = (String) jo2.get("TYPE");
            String emailOfSender = (String) jo2.get("EMAIL");

            System.out.println(".................." + msg);

            switch (type) {
                case "LOGIN":
                    String email = (String) jo2.get("EMAIL");
                    String password = (String) jo2.get("PASSWORD");
                    h.setEmail(email);
                    System.out.println(email);
                    //Handler.addVectorClient(h, mail)
                    logIN(email, password, ps);
                    break;

                case "REGISTER":
                    String nameR = (String) jo2.get("NAME");
                    String emailR = (String) jo2.get("EMAIL");
                    String passwordR = (String) jo2.get("PASSWORD");
                    regester(nameR, emailR, passwordR, ps);
                    break;

                case "SEND_PLAY_INVITATION":
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    //someone ask to play ok / n
                    System.out.println("ask to play");
                    String EMAIL = (String) jo2.get("EMAIL");
                    String EMAIL_TARGET = (String) jo2.get("EMAIL_TARGET");
                    // if (DataBaseHandling.getInstance().checkGame(EMAIL, EMAIL_TARGET)!=-1) {
                    sendPlayingRequest(EMAIL, EMAIL_TARGET, ps);
                    // }
                    break;

                case "SEND_RESPONSE_PLAY_INVITATION":
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    String EMAIL2 = (String) jo2.get("EMAIL");
                    String EMAIL_TARGET2 = (String) jo2.get("EMAIL_TARGET");
                    String key2 = (String) jo2.get("KEY");

                    System.out.println("keyyyyyyyyy" + key2);
                    int id = -1;
                    if (key2.equals("OK")) {
                        id = DataBaseHandling.getInstance().creatGame(EMAIL2, EMAIL_TARGET2, true);
                        Handler.sendallOnline();
                    }
                    handleAskToPlayResponse(EMAIL2, EMAIL_TARGET2, key2, id, ps);
                    break;
                //// when client log out
                case "CLIENT_IS_CLOSING":
                    System.out.println("client sent is ");
                    String EMAILc = (String) jo2.get("EMAIL");
                    DataBaseHandling.getInstance().makeClientOffline(EMAILc);
                    Handler.sendallOnline();
                    handlCancelClient(EMAILc, ps);
                    break;

                case "INITIALIZATION":

//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    Handler.sendallOnline();
                    ps.flush();
                    break;

                case "SEND_PLAY_INDEX":
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    handlePlayIndex((String) jo2.get("EMAIL"),
                            (String) jo2.get("EMAIL_TARGET"),
                            (String) jo2.get("GAME_ID"),
                            (String) jo2.get("INDEX"), ps);
                    break;

                case "SEND_PLAY_PAUSE":
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    handlePlayPause((String) jo2.get("EMAIL"), (String) jo2.get("EMAIL_TARGET"), (String) jo2.get("GAME_ID"), ps);
                    break;
                case "SEND_PLAY_RESUME":
                    handlePlayResume((String) jo2.get("EMAIL"), (String) jo2.get("EMAIL_TARGET"), (String) jo2.get("GAME_ID"), ps);
                    break;

                case "SEND_PLAY_CANCEL":
                    // table records
                    String s = (String) jo2.get("ALLOW_RECORD");
                    if (s.equals("YES")) {
                        DataBaseHandling.getInstance().insertGame((String) jo2.get("EMAIL"),
                                (String) jo2.get("EMAIL_TARGET"), (String) jo2.get("FIRST_SEQUENCE"),
                                (String) jo2.get("SECOND_SEQUENCE"), (String) jo2.get("PLAY_FIRST"),
                                (String) jo2.get("THE_WINNER"));
                    }
                    handlePlayCancel((String) jo2.get("EMAIL"),
                            (String) jo2.get("EMAIL_TARGET"),
                            (String) jo2.get("GAME_ID"), ps);
                    break;
                case "SEND_Game_RESULT": {
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    String ss = (String) jo2.get("ALLOW_RECORD");
                    if (ss.equals("YES")) {
                        System.out.println("YESYESYESYESYESYESYESYESYESYESYES");
                        DataBaseHandling.getInstance().insertGame((String) jo2.get("EMAIL"),
                                (String) jo2.get("EMAIL_TARGET"), (String) jo2.get("FIRST_SEQUENCE"),
                                (String) jo2.get("SECOND_SEQUENCE"), (String) jo2.get("PLAY_FIRST"),
                                (String) jo2.get("THE_WINNER"));
                    }
                    String draw=(String) jo2.get("THE_WINNER");
                    if (draw != ("Draw")) {
                        DataBaseHandling.getInstance().upDataScore((String) jo2.get("THE_WINNER"));
                    }
                    int i = Integer.parseInt((String) jo2.get("GAME_ID"));

                    ///// table players
                    DataBaseHandling.getInstance().updateGame(i, false);
                    Handler.sendallOnline();
                }
                break;
                case "NEED_HISTORY":
//                    Handler.addVectorClient(h, emailOfSender);
//                    h.setEmail(emailOfSender);
                    System.out.println("he need history");
                    Handler.sendHistory((String) jo2.get("EMAIL"));
                    break;
                case "SEND_NOTIFY_RECORD":
                    handelAskToRecord((String) jo2.get("EMAIL"), (String) jo2.get("EMAIL_TARGET"), ps);
                    break;

            }
        } catch (ParseException ex) {
            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void handelAskToRecord(String EMAIL, String EMAIL_TARGET, PrintStream ps) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECIEVE_NOTIFY_RECORD");
                jsonObject.put("EMAIL", EMAIL);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("handlePlayIndex send" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    private void handlePlayCancel(String EMAIL1, String EMAIL_TARGET, String GAME_ID, PrintStream ps) {

        new Thread(new Runnable() {
            int id = Integer.parseInt(GAME_ID);

            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_GAME_CANCEL");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                jsonObject.put("GAME_ID", GAME_ID);
                String jsonText = JSONValue.toJSONString(jsonObject);
                // DataBaseHandling.getInstance().updateGame(id, false);
                DataBaseHandling.getInstance().deleteGame(id);
                DataBaseHandling.getInstance().updateGame(EMAIL1);
                Handler.sendallOnline();
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("handlePlayIndex send" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    private void handlePlayResume(String EMAIL1, String EMAIL_TARGET, String GAME_ID, PrintStream ps) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_PLAY_RESUME");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                jsonObject.put("GAME_ID", GAME_ID);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("handlePlayIndex send" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void handlePlayPause(String EMAIL1, String EMAIL_TARGET, String GAME_ID, PrintStream ps) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_PLAY_PAUSE");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                jsonObject.put("GAME_ID", GAME_ID);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("handlePlayIndex send" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();

    }

    public void handlePlayIndex(String EMAIL1, String EMAIL_TARGET, String GAME_ID, String INDEX, PrintStream ps) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_PLAY_INDEX");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                jsonObject.put("GAME_ID", GAME_ID);
                jsonObject.put("INDEX", INDEX);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("handlePlayIndex send" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void handlCancelClient(String EMAIL1, PrintStream ps) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBaseHandling.getInstance().makeClientOffline(EMAIL1);
                DataBaseHandling.getInstance().updateGame(EMAIL1);
                System.out.println("it`s removed" + EMAIL1);
                Handler.sendallOnline();
                ps.flush();
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void sendPlayingRequest(String EMAIL1, String EMAIL_TARGET, PrintStream ps) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_PLAY_INVITATION");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                System.out.println("djcbjksdjhsjhds" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void handelCancelGameWhenPlayerDowen(String EMAIL) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "CLIENT_DOWEN");
                jsonObject.put("EMAIL", EMAIL);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                System.out.println("djcbjksdjhsjhds" + jsonText);
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void handleAskToPlayResponse(String EMAIL1, String EMAIL_TARGET, String key, int id, PrintStream ps) {

        String ID = "" + id;
        //create game or send mesage to say no
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TYPE", "RECEIVE_RESPONSE_PLAY_INVITATION");
                jsonObject.put("EMAIL", EMAIL1);
                jsonObject.put("EMAIL_TARGET", EMAIL_TARGET);
                jsonObject.put("GAME_ID", ID);
                jsonObject.put("KEY", key);
                String jsonText = JSONValue.toJSONString(jsonObject);
                Handler.sendallOnline(jsonText);
                ps.flush();
                /////////// stop thread
                Thread.currentThread().stop();
            }
        }).start();
    }

    public void regester(String NAME, String EMAIL, String PASSWORD, PrintStream ps) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = DataBaseHandling.getInstance().signUp(NAME, EMAIL, PASSWORD, 0, false);
                if (id == -1) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REGISTER_REPLAY");
                    jsonObject.put("KEY", "EMAIL_IS_USED_BEFORE");
                    String jsonText = JSONValue.toJSONString(jsonObject);

                    ps.println(jsonText);
                    ps.flush();
                    /////////// stop thread
                    Thread.currentThread().stop();

                } else {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REGISTER_REPLAY");
                    jsonObject.put("KEY", "SUCCESS");
                    String jsonText = JSONValue.toJSONString(jsonObject);

                    ps.println(jsonText);
                    ps.flush();

                    /////////// stop thread
                    Thread.currentThread().stop();

                }
            }
        }).start();
    }

    public void logIN(String mail, String password, PrintStream ps) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int a = DataBaseHandling.getInstance().LogIn(mail, password);
                if (a == -1) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "LOGIN_REPLAY");
                    jsonObject.put("KEY", "EMAIL_NOT_FOUND");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                    ps.println(jsonText);
                    ps.flush();
                    /////////// stop thread
                    Thread.currentThread().stop();
                } else if (a == 1) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "LOGIN_REPLAY");
                    jsonObject.put("KEY", "SUCCESS");
                    String jsonText = JSONValue.toJSONString(jsonObject);
//                    Handler.addVectorClient(h, mail);
                    ps.println(jsonText);
                    ps.flush();

                    /// push online list to update online in client when one login
                    Handler.sendallOnline();
                    ps.flush();
                    /////////// stop thread
                    Thread.currentThread().stop();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "LOGIN_REPLAY");
                    jsonObject.put("KEY", "INCORRECT_PASSWORD");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                    ps.println(jsonText);
                    ps.flush();
                    /////////// stop thread
                    Thread.currentThread().stop();
                }
            }
        }).start();
    }

    public void closeNetwork() {
        try {
            if (s != null) {
                s.close();
            }
            if (myServerSocket != null) {
                myServerSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopServer() {
//        try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SERVER_IS_CLOSED");
        String jsonText = JSONValue.toJSONString(jsonObject);
//            dis = new DataInputStream(s.getInputStream());
//            ps = new PrintStream(s.getOutputStream());
//            ps.println(jsonText);
//            ps.flush();
//        } catch (IOException ex) {
//            Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

//    private void removeVector(String EMAIL1) {
//        for (int i = 0; i < Handler.clientsVector.size(); i++) {
//            if (Handler.clientsVector.get(i).Email.equals(EMAIL1)) {
//                Handler.clientsVector.remove(Handler.clientsVector.get(i));
//                System.out.println("remove....." + EMAIL1);
//            }
//        }
//    }
    static public class Handler {

        DataInputStream dis;
        PrintStream ps;
        String msg;
        static Vector<Handler> clientsVector = new Vector<Handler>();
        String Email;
        static boolean forTest = true;

        public void setEmail(String email) {
            this.Email = email;
        }

        public Handler(Socket s) {

            try {
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());
                clientsVector.add(this);
//                if (forTest) {
//                    clientsVector.add(this);
//                    forTest=false;
//                }
                new Thread(new Runnable() {
                    public void run() {
                        while (true) {
                            try {
                                msg = dis.readLine();
                                System.out.println("befour json parse " + msg);
                                getInstance().jesonParsing(msg, ps);
                            } catch (IOException ex) {
                                try {
                                    System.out.println(Email + "close");
                                    DataBaseHandling.getInstance().makeClientOffline(Email);
                                    DataBaseHandling.getInstance().updateGame(Email);
                                    if (Email != null) {
                                        getInstance().handelCancelGameWhenPlayerDowen(Email);
                                    }
                                    ps.close();
                                    dis.close();
                                    clientsVector.remove(this);
                                    Handler.sendallOnline();
                                    Thread.currentThread().stop();

                                } catch (IOException ex1) {
                                    Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            }
                        }
                    }
                }).start();

            } catch (IOException ex) {
                Logger.getLogger(ServerNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public String getMessage() {
            return msg;
        }

        public static void printVECTOR() {
            for (int i = 0; i < clientsVector.size(); i++) {
                System.out.println(".." + clientsVector.get(i).Email);
            }

        }

        public static void sendallOnline() {

            ArrayList<Player> onlineList = DataBaseHandling.getInstance().getOnLineForClient();
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            jsonObject.put("TYPE", "ONLINEPLAYERS");
            for (int i = 0; i < onlineList.size(); i++) {
                Map p = new LinkedHashMap(3);
                p.put("NAME", onlineList.get(i).getName());
                p.put("EMAIL", onlineList.get(i).getEmail());
                p.put("SCORE", onlineList.get(i).getScore());
                p.put("STATUS", onlineList.get(i).getStatus());
                array.add(p);
            }
            jsonObject.put("PLAYERS", array);
            String jsonText = JSONValue.toJSONString(jsonObject);
            for (Handler s : clientsVector) {
                s.ps.println(jsonText);
                System.out.println("......." + jsonText + "\n");
            }
        }

        public static void sendallOnline(String str) {
            ArrayList<Player> onlineList = DataBaseHandling.getInstance().getOnLineForClient();
            for (Handler s : clientsVector) {
                s.ps.println(str);
                System.out.println("..sendtoAll..." + str + "\n");
            }

        }

        public static void sendHistory(String email) {
            ArrayList<Record> history = DataBaseHandling.getInstance().getTheHistory(email);
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            jsonObject.put("TYPE", "HISTORY_OF_GAME");
            jsonObject.put("EMAIL", email);
            for (int i = 0; i < history.size(); i++) {
                Map p = new LinkedHashMap(7);
                p.put("PLAYER1", history.get(i).getFirstName());
                p.put("PLAYER2", history.get(i).getSecondName());
                p.put("SEQUENCE1", history.get(i).getFirstSeq());
                p.put("SEQUENCE2", history.get(i).getSecondSeq());
                p.put("WINNER", history.get(i).getTheWinner());
                p.put("DATE", history.get(i).getTheDateCreated());
                p.put("FIRST_PLAYER", history.get(i).getPlayFirst());
                array.add(p);
            }
            jsonObject.put("GAMES", array);
            System.out.println("I send to client");
            String jsonText = JSONValue.toJSONString(jsonObject);
            System.out.println(jsonText);
            for (Handler s : clientsVector) {
                s.ps.println(jsonText);

            }
        }

//        private static void addVectorClient(Handler h, String Email) {
//            System.out.println("Email of vector " + Email);
//            boolean flag = false;
//            for (int i = 0; i < clientsVector.size(); i++) {
//                if (clientsVector.get(i).Email.equals(Email)) {
//                    System.out.println("here flag is true");
//                    flag = true;
//                    clientsVector.remove(clientsVector.get(i));
//                    clientsVector.add(h);
//                    h.setEmail(Email);
////                    clientsVector.set(i, h);
////                    h.setEmail(Email);
//                    //clientsVector.add(h);
//                }
//            }
//            if (flag == false) {
//                System.out.println("here flag is false");
//                clientsVector.add(h);
//                h.setEmail(Email);
//            }
//        }
    }
}
