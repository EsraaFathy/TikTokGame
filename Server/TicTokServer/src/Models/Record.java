package Models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author El-3ttar
 */
public class Record {

    private String firstName;
    private String secondName;
    private String firstSeq;
    private String secondSeq;
    private String theWinner;
    private String theDateCreated;
    private String playFirst;

    public Record(String firstName, String secondName, String firstSeq, String secondSeq, String theWinner, String theDateCreated, String playFirst) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSeq = firstSeq;
        this.secondSeq = secondSeq;
        this.theWinner = theWinner;
        this.theDateCreated = theDateCreated;
        this.playFirst = playFirst;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstSeq() {
        return firstSeq;
    }

    public void setFirstSeq(String firstSeq) {
        this.firstSeq = firstSeq;
    }

    public String getSecondSeq() {
        return secondSeq;
    }

    public void setSecondSeq(String secondSeq) {
        this.secondSeq = secondSeq;
    }

    public String getTheWinner() {
        return theWinner;
    }

    public void setTheWinner(String theWinner) {
        this.theWinner = theWinner;
    }

    public String getTheDateCreated() {
        return theDateCreated;
    }

    public void setTheDateCreated(String theDateCreated) {
        this.theDateCreated = theDateCreated;
    }

    public String getPlayFirst() {
        return playFirst;
    }

    public void setPlayFirst(String playFirst) {
        this.playFirst = playFirst;
    }
    
    
    
}
