/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author icaro
 */
public class sobre {
    sobre(){
        JLabel jUserName = new JLabel("Jully\n");
        jUserName.setFont(new Font("", Font.BOLD, 30));
        
        String objetivo = "Programa desenvolvido para retirar uma linha escura nas fotos "
                + "de uma câmera digital Canon.";
        
        JLabel _conteudo = new JLabel(objetivo);
        
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.add(jUserName);
        painel.add(Box.createRigidArea(new Dimension(10, 10)));
        painel.add(_conteudo);
        painel.add(Box.createRigidArea(new Dimension(10, 10)));
        painel.add(new JLabel("Desenvolvido por: Ícaro Martins."));
        painel.add(Box.createRigidArea(new Dimension(5, 5)));
        painel.add(new JLabel("Email: icarofre99@gmail.com"));
        
        JOptionPane.showMessageDialog(new JFrame(),
            painel,
            "Sobre",
            JOptionPane.PLAIN_MESSAGE);
    }
}
