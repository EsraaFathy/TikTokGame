package tictokserver;

import Models.Player;
import Models.Record;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.derby.jdbc.ClientDriver;

public class DataBaseHandling {

    private static DataBaseHandling instance;
    public static Connection connection;
    private static Statement stmt;

    private DataBaseHandling() {

        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TikTokGame", "group1", "group1");
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setContentText("There is some Errors Acurate while accessing to database");
            alert.showAndWait();

        }
    }

    public static DataBaseHandling getInstance() {
        if (instance == null) {
            instance = new DataBaseHandling();
        }
        return instance;
    }

    ////////// get all games Played
    public int returnTotalNumberOfGames() {
            int id=0;

        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM GAME");
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                id++;
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean chechMail(String EMAIL) {
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER WHERE EMAIL= ?");
            s.setString(1, EMAIL);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int signUp(String NAME, String EMAIL, String PASSWORD, int POINTS, boolean STATUS) {
        ///     register///
        // email is alreadu found  -1
        //  register if succed     1
        if (!chechMail(EMAIL)) {

            int id = 0;
            int i;
            try {
                PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER");
                ResultSet resultSet = s.executeQuery();
                while (resultSet.next()) {
                    id++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("..............." + (id++));
            try {
                PreparedStatement pst = connection.prepareStatement("INSERT INTO PLAYER ( ID,NAME, EMAIL,PASSWORD,POINTS,STATUS) VALUES(?,?,?,?,?,?)");
                pst.setInt(1, id);
                pst.setString(2, NAME);
                pst.setString(3, EMAIL);
                pst.setString(4, PASSWORD);
                pst.setInt(5, POINTS);
                pst.setBoolean(6, STATUS);
                try {
                    i = pst.executeUpdate();
                    //update Gui
                    HomeServer.offlineText.setText("");
                    HomeServer.setOffText();

                    return i;
                } catch (SQLIntegrityConstraintViolationException e) {
                    Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, e);
                }

            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public int LogIn(String mail, String password) {
        int login = -1;

        try {

            PreparedStatement s = connection.prepareStatement("SELECT * FROM PLAYER WHERE EMAIL = ? AND PASSWORD = ?");
            s.setString(1, mail);
            s.setString(2, password);
            ResultSet resultSet = s.executeQuery();
            if (resultSet.next()) {
                PreparedStatement s1 = connection.prepareStatement("UPDATE PLAYER SET STATUS = ? WHERE EMAIL = ?");
                s1.setString(2, mail);
                s1.setBoolean(1, true);

                s1.executeUpdate();

                login = 1;
            } else if (chechMail(mail)) {
                login = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }

    //return -1 if not added others if add
    public int creatGame(String PLAYER1_EMAIL, String PLAYER2_EMAIL, boolean STATUS) {
        int i = -1;
        int id = 1;
        try {

            PreparedStatement s = connection.prepareStatement("SELECT * FROM GAME");
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                id++;
            }
            PreparedStatement pst = connection.prepareStatement("INSERT INTO GAME ( ID,PLAYER1_EMAIL,PLAYER2_EMAIL,STATUS) VALUES(?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, PLAYER1_EMAIL);
            pst.setString(3, PLAYER2_EMAIL);
            pst.setBoolean(4, STATUS);
            i = pst.executeUpdate();
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /// method to update score when player win the game
    public void upDataScore(String EMAIL) {

        try {
            PreparedStatement s1 = connection.prepareStatement("SELECT POINTS FROM PLAYER WHERE EMAIL= ?");
            s1.setString(1, EMAIL);
            ResultSet r = s1.executeQuery();
            if (r.next()) {

                int p = 6;
                p = r.getInt(1);
                System.out.println("points" + p);
                p++;
                PreparedStatement s = connection.prepareStatement("UPDATE PLAYER SET POINTS = ? WHERE EMAIL= ?");
                s.setInt(1, p++);
                s.setString(2, EMAIL);
                s.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /// make game offline by using email of one player
    public void updateGame(String EMAIL) {
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE Game SET STATUS = false WHERE PLAYER1_EMAIL = ? OR PLAYER2_EMAIL=?");
            s.setString(1, EMAIL);
            s.setString(2, EMAIL);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /// update status of game by id
    public void updateGame(int id, boolean STATUS) {
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE Game SET STATUS=? WHERE ID= ?");
            s.setBoolean(1, STATUS);
            s.setInt(2, id);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // return status of  player false if offline true if online
    public boolean chechPlayerStatus(String player1email) {

        try {

            PreparedStatement s = connection.prepareStatement("SELECT STATUS FROM PLAYER WHERE EMAIL= ?");
            s.setString(1, player1email);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {

                return resultSet.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //// method to make one client ofline when he loged out
    public void makeClientOffline(String EMAIL) {

        try {
            PreparedStatement s = connection.prepareStatement("UPDATE PLAYER SET STATUS = ? WHERE EMAIL = ?");
            s.setBoolean(1, false);
            s.setString(2, EMAIL);
            System.out.println("..." + EMAIL);
            System.out.println(s.executeUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /// method to make all players offline and stop all games in database when server is stoping
    public void stopServerOfflineAll() {
        try {
            PreparedStatement s = connection.prepareStatement("UPDATE PLAYER SET STATUS = FALSE");
            s.executeUpdate();
            PreparedStatement ps = connection.prepareStatement("UPDATE GAME SET STATUS = FALSE");
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //// method to check if a specific player is online
    public ArrayList<String> getOnLineE() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select EMAIL from PLAYER WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1);

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public ArrayList<String> getOnLine() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS from PLAYER WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "   [Score = " + rs.getString(2) + "]";

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public ArrayList<String> getOffLine() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS from PLAYER WHERE STATUS = false");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "    " + rs.getString(2);

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public ArrayList<String> getOnGaming() {

        ArrayList<String> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select PLAYER1_EMAIL, PLAYER2_EMAIL from GAME WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String line = rs.getString(1) + "  Vs  " + rs.getString(2);

                arr.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    public void clossingDataBase() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /// delete Game when came is canced
    public void deleteGame(int GAME_ID) {

        try {
            PreparedStatement s = connection.prepareStatement("DELETE FROM GAME WHERE ID=?");
            s.setInt(1, GAME_ID);
            s.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///// method to return online players 
    public ArrayList<Player> getOnLineForClient() {
        ArrayList<Player> arr = new ArrayList<>();
        try {
            PreparedStatement s = connection.prepareStatement("select NAME, POINTS,EMAIL from PLAYER WHERE STATUS = true");
            ResultSet rs = s.executeQuery();

            while (rs.next() == true) {
                String NAME = rs.getString(1);
                int POINTS = rs.getInt(2);
                String EMAIL = rs.getString(3);
                String status = returnIfPlayerOnGameOrNot(EMAIL);
                arr.add(new Player(NAME, EMAIL, POINTS, status));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    /////method to chech if specfic person is on game 
    public String returnIfPlayerOnGameOrNot(String Email) {
        String b = "0";
        try {
            PreparedStatement s = connection.prepareStatement("select PLAYER1_EMAIL, PLAYER2_EMAIL from GAME WHERE STATUS = true");
            ResultSet rs = s.executeQuery();
            while (rs.next() == true) {
                if (rs.getString(1).equals(Email) || rs.getString(2).equals(Email)) {
                    b = "1";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return b;
    }

    ////////////////check if ther is a game now between two plaayer
    public int checkGame(String EMAIL, String EMAILTARGT) {
        int id = -1;
        try {
            PreparedStatement s = connection.prepareStatement("SELECT ID FROM GAME WHERE STATUS = TRUE AND PLAYER1_EMAIL = ? AND PLAYER2_EMAIL= ?");
            s.setString(1, EMAIL);
            s.setString(2, EMAIL);
            ResultSet rs = s.executeQuery();
            while (rs.next() == true) {
                id = rs.getInt(1);
                System.out.println("..." + id);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    //INSERTO TABLE RECORDES TO RECORD GAME MOVES
    public void insert(String player1, String player2, String seq, String date, String playfirst, String winner) {
        int id = 0;
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM Records");
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()) {
                id++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("..............." + (id++));
        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO Record ( ID,PLAYER1, PLAYER2,SEQOFPLAYING,DATEOFGAME,PLAYFIRST,WINNER) VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, player1);
            pst.setString(3, player2);
            pst.setString(4, seq);
            pst.setString(5, date);
            pst.setString(6, playfirst);
            pst.setString(7, winner);

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // to insert in database 
    public void insertGame(String player1, String player2, String seq1, String seq2, String playfirst, String winner) {
        int id = 1;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM RECORDES");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO RECORDES (ID,PLAYER1EMAIL, PLAYER2EMAIL,SEQUOOFPLAYER1,SEQUOOFPLAYER2,DATAOFGAME,PLAYERFRIST,WINNER) VALUES(?,?,?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, player1);
            pst.setString(3, player2);
            pst.setString(4, seq1);
            pst.setString(5, seq2);
            pst.setString(6, calcTimeDate());
            pst.setString(7, playfirst);
            pst.setString(8, winner);

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //// method to return the history of specefic player
    public ArrayList<Record> getTheHistory(String p1ayeremail) {
        ArrayList<Record> arr = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement("select * from RECORDES WHERE PLAYER1EMAIL = ? OR PLAYER2EMAIL = ?");
            pst.setString(1, p1ayeremail);
            pst.setString(2, p1ayeremail);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString(2);
                String secondName = rs.getString(3);
                String firstSeq = rs.getString(4);
                String secondSeq = rs.getString(5);
                String theWinner = rs.getString(8);
                String theDateCreated = rs.getString(6);
                String playFirst = rs.getString(7);

                arr.add(new Record(firstName, secondName, firstSeq, secondSeq, theWinner, theDateCreated, playFirst));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    /////cac the time and the date
    private String calcTimeDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String timeDate = formatter.format(date);
        return timeDate;
    }

}
