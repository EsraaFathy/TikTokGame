/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Records;

/**
 *
 * @author Mohamed
 */

    //*************************************************************************************//
    // Record Object model
    //****************************************************************************************//
public class RecordObject {
    
    private static Records selectedRecord;

    /**
     * @return the selectedRecord
     */
    public static Records getSelectedRecord() {
        return selectedRecord;
    }

    /**
     * @param aSelectedRecord the selectedRecord to set
     */
    public static void setSelectedRecord(Records aSelectedRecord) {
        selectedRecord = aSelectedRecord;
    }
    
}
