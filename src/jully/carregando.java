/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author icaro
 */
public class carregando implements PropertyChangeListener {

  private JPanel painel;
  private JProgressBar pbar;

  private final int MY_MINIMUM = 0;
  private final int MY_MAXIMUM = 100;
  private String arquivo_foto_ponto_JPG;
  private String nome_da_foto;
  private boolean quebrar = false;
  private Vector<String> foto;
  private Vector<String> nomefoto;
  private String nome_pasta;
  private boolean ERRO = false;
  
  public carregando(String _arquivo_foto_ponto_JPG, String _nome_da_foto) 
  {
    painel = new JPanel();
    pbar = new JProgressBar();
    pbar.setMinimum(MY_MINIMUM);
    pbar.setMaximum(MY_MAXIMUM);
    pbar.setValue(0);
    painel.add(pbar);
    arquivo_foto_ponto_JPG = _arquivo_foto_ponto_JPG;
    nome_da_foto = _nome_da_foto;
  }
  
  public carregando(Vector<String> _foto, Vector<String> _nomefoto, String _nome_pasta) 
  {
    painel = new JPanel();
    pbar = new JProgressBar();
    pbar.setMinimum(MY_MINIMUM);
    pbar.setMaximum(MY_MAXIMUM);
    pbar.setValue(0);
    painel.add(pbar);
    foto = _foto;
    nomefoto = _nomefoto;
    nome_pasta = _nome_pasta;
  }
  
  private void esperar(int segundos){
        try {
            Thread.sleep( (segundos * 1000) );//1000 milliseconds = 1 segundo.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
  
  private void alerta(java.awt.Component c, String titulo, String mensagem){
        JOptionPane.showMessageDialog(c, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
    }
  
  //===
  class tarefa extends SwingWorker<Void, Void> {

    private JFrame painel;
  
    tarefa(JFrame p){
        painel = p;
    }
    
    
    @Override
    protected Void doInBackground() throws Exception {
        
        (new Thread(new fun())).start(); /* <- Thread para outra operação que ocorrerá. */
        aguarde();
        
        return null;
    }
    
    @Override
    protected void done() {
        painel.dispose();
        if(!ERRO){
            JOptionPane.showMessageDialog(new JFrame(), "Concluído.");
        }else{
            alerta(new JFrame(),"Atenção!","Selecione imagens do tamanho padrão gerado pela câmera.");
        }
        System.out.println("Feito.");
    }
    
}
  //===

  private void updateBar(int newValue) {
    pbar.setValue(newValue);
  }
  
  public void visu(){
        JFrame frame = new JFrame("Aguarde...");
        frame.setContentPane(painel);
        frame.setSize(500, 100);
        frame.setVisible(true);
        
        tarefa t = new tarefa(frame);
        t.addPropertyChangeListener(this);
        t.execute();
  }

  /* metodo para inicia a barra de processo crescendo. */
  private void aguarde() {  

    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) 
    {
        if(pbar.getValue() == pbar.getMaximum()-1){
                    i = 0;
        }
        
        final int percent = i;

        try {
            SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                updateBar(percent);
              }
            });
            java.lang.Thread.sleep(100);
        } catch (InterruptedException e) { }
        if(quebrar) break;
    }
    
  }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        
    }
  
    class fun implements Runnable{
        @Override
        public void run() {
            
           System.out.println("Operando...");
           
           foto ft = new foto();
           try {
                if( (foto != null) && (nomefoto != null) ){//varias imagens;
                    for(int i=0; i<foto.size(); i++)
                    {
                        ft.recorte_salva_pasta(foto.elementAt(i), nomefoto.elementAt(i), nome_pasta);
                    }
                }else{// somente uma imagem;
                    if( ft.recorte(arquivo_foto_ponto_JPG, nome_da_foto) == false ){
                        quebrar = true;
                        ERRO = true;
                    }
                }
            } catch (IOException ex) {
                    Logger.getLogger(carregando.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           /* \/ variavel usada para finalizar a barra de progresso e dar OK. */
           quebrar = true;
        }
    }
    
  
}

