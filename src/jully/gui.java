/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author icaro
 */
public class gui extends JFrame {
    
    private JFileChooser ip = null;
    
    private static final int GAP = 5;
    private JPanel painel = new JPanel(new GridBagLayout());
    private GBHelper pos = new GBHelper();
    
    private JTextArea log = new JTextArea();
    private JButton iniciar = new JButton("Iniciar", new ImageIcon("icones/iniciar.png"));
    
    private JTextField caminho_foto_ou_pasta_fotos = new JTextField(20);
    private JButton btn_selecionar_foto = new JButton("Selecionar imagem", new ImageIcon("icones/foto.png"));
    
    private JTextField caminho_pasta = new JTextField(20);
    private JButton btn_selecionar_pasta = new JButton("Selecionar local", new ImageIcon("icones/pasta.png"));
    
    private JMenu menu = new JMenu("Arquivo");
    private JMenuItem item_menu = new JMenuItem("Sobre");
    private JMenuBar barra_de_menu = new JMenuBar();
    
    private int largura = 800;
    private int altura = 500;
    
    public void montar_gui(String titulo_janela) {
        
        iniciar.setEnabled(false);
        log.setEditable(false);
        log.setColumns(20);
        log.setRows(5);
        
        caminho_foto_ou_pasta_fotos.setEditable(false);
        caminho_pasta.setEditable(false);
        
        JScrollPane scroll_observacoes = new JScrollPane();
        scroll_observacoes.setViewportView(log);
        scroll_observacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Processo:"));
        painel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        painel.add(btn_selecionar_foto, pos.expandW());
        painel.add(caminho_foto_ou_pasta_fotos, pos.nextCol().expandW());
        
        painel.add(new Gap(GAP) , pos.nextRow());
        painel.add(new Gap(GAP) , pos.nextRow());
        
        painel.add(btn_selecionar_pasta, pos.nextRow().expandW());
        painel.add(caminho_pasta, pos.nextCol().expandW());
        
        painel.add(new Gap(GAP) , pos.nextRow());
        painel.add(new Gap(GAP) , pos.nextRow());
        
        painel.add(iniciar, pos.nextRow().expandW());
        painel.add(new Gap(GAP) , pos.nextRow());
        painel.add(scroll_observacoes, pos.nextRow().width(2).expandir());
        
        //***
        
        btn_selecionar_foto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                    log.setText("");// inicia com o log zerado;

                    JFileChooser fc = new JFileChooser();
                    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                    int returnVal = fc.showOpenDialog(gui.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) 
                    {
                        ip = fc;
                        /* coloca no campo de texto o nome do arquivo ou pasta selecionada; */
                        caminho_foto_ou_pasta_fotos.setText(fc.getSelectedFile().getName());
                    }

                    if( !caminho_pasta.getText().isEmpty() ) {
                        iniciar.setEnabled(true);
                    }
            }
        });
        
        btn_selecionar_pasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                    JFileChooser fc = new JFileChooser();
                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int returnVal = fc.showOpenDialog(gui.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) 
                    {
                        File file = fc.getSelectedFile();
                        String caminho = file.getAbsolutePath();

                        /* se o ultimo caractere do caminho não existir um separador, ele insere um; */
                        char ultima_letra_caminho = caminho.charAt(caminho.length()-1);
                        if( ultima_letra_caminho != File.separatorChar ){
                            caminho += File.separator;
                        }

                        /* coloca no campo de texto o caminho completo para salvar; */
                        caminho_pasta.setText(caminho);

                        if( ip != null ) {
                            iniciar.setEnabled(true);
                        }  
                    }
            }
        });
        
        
        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                log.setText("");
                new selecionar().selecionar_imagem_ou_pasta(ip, log, caminho_pasta.getText());
            }
        });
        
        //***
        
        barra_de_menu.add(menu);
        menu.add(item_menu);
        item_menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new sobre();
            }
        });
        
        super.setJMenuBar(barra_de_menu);
        super.setContentPane(painel);
        super.pack();
        super.setTitle(titulo_janela);
        super.setSize(new Dimension(largura, altura));
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
    }
    
    
}
