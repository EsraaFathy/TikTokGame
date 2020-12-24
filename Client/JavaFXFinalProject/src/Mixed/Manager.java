/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mixed;

import javafx.scene.layout.Pane;


public class Manager {
      public static LoginBase login = new LoginBase();
      public static RegisterBase signup = new  RegisterBase();
     
     public static void viewPane(Pane pane)
    {
        login.setVisible(false);
        signup.setVisible(false);
        pane.setVisible(true);
    }
    
}
