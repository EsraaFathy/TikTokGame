/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Mohamed
 */

    //*************************************************************************************//
    // Records model
    //****************************************************************************************//
public class Records {
    
    private int id;
    private String firstName;
    private String secondName;
    private String firstSeq;
    private String secondSeq;
    private String theWinner;
    private String theDateCreated;
    private String playFirst;
    
    
    
    public Records()
    {
        
    }
    public Records(String firstName,String secondName,String firstseq ,
            String secondSeq,String theWnner,String playFirst)
    {
        this.firstName=firstName;
        this.secondName=secondName;
        this.firstSeq=firstseq;
        this.secondSeq=secondSeq;
        this.theWinner=theWnner;
        this.playFirst=playFirst;
    }
    public Records(String firstName,String secondName,String firstseq ,
            String secondSeq,String theWnner,String playFirst,String theDateCreated)
    {
        this.firstName=firstName;
        this.secondName=secondName;
        this.firstSeq=firstseq;
        this.secondSeq=secondSeq;
        this.theWinner=theWnner;
        this.playFirst=playFirst;
        this.theDateCreated=theDateCreated;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return the firstSeq
     */
    public String getFirstSeq() {
        return firstSeq;
    }

    /**
     * @param firstSeq the firstSeq to set
     */
    public void setFirstSeq(String firstSeq) {
        this.firstSeq = firstSeq;
    }

    /**
     * @return the seconSeq
     */
    public String getSeconSeq() {
        return secondSeq;
    }

    /**
     * @param seconSeq the seconSeq to set
     */
    public void setSeconSeq(String secondSeq) {
        this.secondSeq = secondSeq;
    }

    /**
     * @return the theWinner
     */
    public String getTheWinner() {
        return theWinner;
    }

    /**
     * @param theWinner the theWinner to set
     */
    public void setTheWinner(String theWinner) {
        this.theWinner = theWinner;
    }

    /**
     * @return the theDateCreated
     */
    public String getTheDateCreated() {
        return theDateCreated;
    }

    /**
     * @param theDateCreated the theDateCreated to set
     */
    public void setTheDateCreated(String theDateCreated) {
        this.theDateCreated = theDateCreated;
    }

    /**
     * @return the playFirst
     */
    public String getPlayFirst() {
        return playFirst;
    }

    /**
     * @param playFirst the playFirst to set
     */
    public void setPlayFirst(String playFirst) {
        this.playFirst = playFirst;
    }
    
    
    
    
    
}
