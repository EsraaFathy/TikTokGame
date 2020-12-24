/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefrence;

/**
 *
 * @author Mohamed
 */
public class ClientData {
    
    private static String myEmail;
    private static String otherEmail;
    private static String gameId;

    /**
     * @return the myEmail
     */
    public static String getMyEmail() {
        return myEmail;
    }

    /**
     * @param aMyEmail the myEmail to set
     */
    public static void setMyEmail(String aMyEmail) {
        myEmail = aMyEmail;
    }

    /**
     * @return the otherEmail
     */
    public static String getOtherEmail() {
        return otherEmail;
    }

    /**
     * @param aOtherEmail the otherEmail to set
     */
    public static void setOtherEmail(String aOtherEmail) {
        otherEmail = aOtherEmail;
    }

    /**
     * @return the gameId
     */
    public static String getGameId() {
        return gameId;
    }

    /**
     * @param aGameId the gameId to set
     */
    public static void setGameId(String aGameId) {
        gameId = aGameId;
    }
    
    
}
