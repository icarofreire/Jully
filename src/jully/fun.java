/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author icaro
 */
public class fun implements Runnable{
        
        private Vector<String> foto;
        private Vector<String> nomefoto;
        private String arquivo_foto_ponto_JPG;
        private String nome_da_foto;
        private String nome_pasta;
        private JTextArea log;
        private String caminho_salvar;
        
        fun(JTextArea _log, String _arquivo_foto_ponto_JPG, String _nome_da_foto, String _caminho_salvar){
            arquivo_foto_ponto_JPG = _arquivo_foto_ponto_JPG;
            nome_da_foto = _nome_da_foto;
            log = _log;
            caminho_salvar = _caminho_salvar;
        }
        
        fun(JTextArea _log, Vector<String> _foto, Vector<String> _nomefoto, String _nome_pasta, String _caminho_salvar){
            foto = _foto;
            nomefoto = _nomefoto;
            nome_pasta = _nome_pasta;
            log = _log;
            caminho_salvar = _caminho_salvar;
        }
        
        private void OK(){
            alerta(new JFrame(), "OK!", "Concluído.");
        }
        
        private void alerta(Component c, String titulo, String mensagem){
            JOptionPane.showMessageDialog(c, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
        }
        
        
        @Override
        public void run() {
           
                    foto ft = new foto(caminho_salvar);
                    String caminho_atual = Paths.get(".").toAbsolutePath().normalize().toString();
                    
                    try {
                            boolean flag = false;
                            if( (foto != null) && (nomefoto != null) )//varias imagens;
                            {
                                for(int i=0; i<foto.size(); i++)
                                {   
                                    log.append("Operando " + foto.elementAt(i) + " ...\n");
                                    if( ft.recorte_salva_pasta(foto.elementAt(i), nomefoto.elementAt(i), nome_pasta) )
                                    {
                                        flag = true;
                                        log.append("Imagem concluída.\n");
                                        log.append("***\n");
                                    }else{
                                        log.append("Ocorreu um erro ao processar a imagem: "+ foto.elementAt(i) +"\n");
                                        log.append("***\n");
                                    }
                                }// fim for;
                                
                                if(flag){
                                    log.append("Concluído.\n");
                                    OK();
                                }
                                
                            }else{// somente uma imagem;
                                log.append("Operando...\n");
                                if( ft.recorte(arquivo_foto_ponto_JPG, nome_da_foto) ){
                                    log.append("Concluído.\n");
                                    log.append("***\n");
                                    OK();
                                }else{
                                    log.append("Ocorreu um erro ao processar a imagem.\n");
                                }
                            }
                    } catch (IOException ex) {
                        Logger.getLogger(Jully_v2.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }// fim run;
              
        
}