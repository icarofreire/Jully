/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

/**
 *
 * @author icaro
 */
import java.awt.*;
import javax.swing.*;

////////////////////////////////////////////////////////////////////// Class Gap
public class Gap extends JComponent {
    
    //============================================================== constructor
    /* Creates filler with minimum size, but expandable infinitely. */
    public Gap() {
        Dimension min = new Dimension(0, 0);
        Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        setMinimumSize(min);
        setPreferredSize(min);
        setMaximumSize(max);
    }
    
    //============================================================== constructor
    /* Creates rigid filler. */
    public Gap(int size) {
        Dimension dim = new Dimension(size, size);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }
}
