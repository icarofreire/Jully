/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author icaro
 */
public class Jully {

    public static void GTK(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e){}
    }
    
    public static void iniciar(){
        GTK();
        new visual().setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        iniciar();
        
    }
    
}
