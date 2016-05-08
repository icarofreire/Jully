/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Component;
import java.io.File;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author icaro
 */
public class selecionar {
    
    private void alerta(Component c, String titulo, String mensagem){
        JOptionPane.showMessageDialog(c, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
    }
    
    /* gerar um nome aleatório para a foto; */
    private String gerar_nome(int tamanho)
    {
        String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        String sb = "";
        for( int i = 0; i < tamanho; i++ ){
           sb += AB.charAt( rnd.nextInt(AB.length()) );
        }
        return sb;
    }
    
    /* recebe o objeto do seletor de arquivos como parametro, analisa se foi uma
    imagem ou uma pasta, e executa o programa; */
    public void selecionar_imagem_ou_pasta(JFileChooser fc, JTextArea log, String caminho_salvar){
            File file = fc.getSelectedFile();
            if(file.isDirectory())/* Se selecionado uma pasta de fotos; */
            {
                String nome_pasta = file.getName();
                String caminho_completo = file.getAbsoluteFile().toString();
                
                Vector<String> foto = new Vector<String>();
                Vector<String> nomefoto = new Vector<String>();
                
                File dir = new File(caminho_completo);
                boolean se_imagens = false;
                for ( File f: dir.listFiles() )
                {
                    if( 
                         (f.getName().indexOf(".JPG") != -1) ||
                         (f.getName().indexOf(".jpg") != -1)
                      )
                    {
                        se_imagens = true;
                        String arquivo_foto = f.getAbsoluteFile().toString();                       
                            
                        String nome_foto = gerar_nome(20) + ".JPG";
                            
                        foto.add(arquivo_foto);
                        nomefoto.add(nome_foto);
                    }
                }// fim for;
                
                if(!se_imagens){
                    alerta(new JFrame(), "Atenção!", "Não contém nenhuma imagem .JPG na pasta.");
                }else{
                    log.append("Pasta obtida: " + caminho_completo + "\n");
                    String pasta_nova = "__" + nome_pasta;
                    
                    (new Thread(new fun(log, foto, nomefoto, pasta_nova, caminho_salvar))).start();
                }
                
                
            }else if(file.isFile())/* Se selecionado apenas uma foto; */
            {
                String nome_arq = file.getName();
                String caminho_completo = file.getAbsoluteFile().toString();
                
                if( 
                    (caminho_completo.indexOf(".JPG") != -1) ||
                    (caminho_completo.indexOf(".jpg") != -1) 
                  )
                {

                    String nome_foto = gerar_nome(20) + ".JPG";
                    
                    log.append("Imagem obtida: " + caminho_completo + "\n");
                    
                    (new Thread(new fun(log, caminho_completo, nome_foto, caminho_salvar))).start();
                    
                }else{
                    alerta(new JFrame(), "Atenção!", "O arquivo selecionado não é uma imagem .JPG.");
                }
                
            }// fim if-else;
    }
    
}
