/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jully;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author icaro
 */
public class foto {
    
    private String diretorio_atual = Paths.get(".").toAbsolutePath().normalize().toString()+File.separator;
    private BufferedImage img;
    private String foto;
    private String nome_foto;
    private String pasta = null;
    private String caminho_salvar;
    
    foto(String _caminho_salvar){
        caminho_salvar = _caminho_salvar;
    }
    
    public void set_pasta(String pasta_){
        pasta = pasta_;
    }
    
    /* faz a operação na imagem e salva em uma pasta especifica; */
    public boolean recorte_salva_pasta(String arquivo_foto_ponto_JPG, String nome_da_foto, String pasta) throws IOException{
        set_pasta(pasta);
        return recorte(arquivo_foto_ponto_JPG, nome_da_foto);
    }
    
    public boolean recorte(String arquivo_foto_ponto_JPG, String nome_da_foto) throws IOException{
        boolean flag = false;
        this.foto = arquivo_foto_ponto_JPG;
        this.img = ImageIO.read(new File(this.foto));
        this.nome_foto = nome_da_foto;
        
        if(se_tamanho_original()){
            /* retira a linha preta da imagem e salva com um nome aleatorio; 
            e salva a imagem com um nome aleatorio. */
            recortar_foto();
            flag = true;
        }
        return flag;
    }
    
    /* verifica se a imagem possui o tamanho original das fotos da camera. */
    private boolean se_tamanho_original(){
        int altura = img.getHeight();
        int largura = img.getWidth();
        return ( (largura == 5184) && (altura == 3456) );
    }
    
    private void criar_imagem(BufferedImage img) throws IOException{
        String dir_atual_nome = caminho_salvar + this.nome_foto;
        File outputfile = new File(dir_atual_nome);
        ImageIO.write(img, "jpg", outputfile);
        Image image  = ImageIO.read(new File(dir_atual_nome));
                
        /*try{
            String nome = "__" + this.nome_foto;
            File outputfile = null;
            if(pasta != null){//se apontado uma pasta;
                String dir_arq = pasta + File.separator + nome;
                outputfile = new File(dir_arq);
                outputfile.getParentFile().mkdirs();
                ImageIO.write(img, "jpg", outputfile);
                Image image  = ImageIO.read(new File(dir_arq));
            }else{//se apontado uma imagem;
                String dir_atual_nome = diretorio_atual + "__" + this.nome_foto;
                outputfile = new File(dir_atual_nome);
                ImageIO.write(img, "jpg", outputfile);
                Image image  = ImageIO.read(new File(dir_atual_nome));
            }
        }catch(java.io.FileNotFoundException e){ 
            JOptionPane.showMessageDialog(null, "erro no diretório para criar a nova imagem/pasta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }*/
    }
    
    private void recortar_foto() throws IOException{
        
        int altura = img.getHeight();
        int largura = img.getWidth();
        
        // ponto fixo da linha preta na foto;
        int ponto_fixo_y = 1232;//y;
        int N_linhas_pular = 4;// numero de linhas acima e abaixo a ignorar;
        
        int l_cima = ponto_fixo_y-N_linhas_pular;
        int l_baixo = ponto_fixo_y+N_linhas_pular;
 
        BufferedImage img2 = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < l_cima; i++){
            for (int j = 0; j < largura; j++){
                int px = img.getRGB(j,i);
                img2.setRGB(j, i, px);
            }
        }

        /* numero total de linhas que ficaram vagas, de acordo com o calculo do numero 
        de linhas acima e abaixo do ponto da linha preta; Ignorando N linhas acima e mesma N quantidade de
        linhas abaixo, o total de linhas a ignorar é (N * 2). e neste espaço vazio deve ser colocado os 
        pixels uteis da imagem.*/
        int n_linhas_vagas = (N_linhas_pular * 2);
        
        for (int i = l_baixo; i < altura; i++){
            for (int j = 0; j < largura; j++){
                int px = img.getRGB(j,i);
                img2.setRGB(j, i-n_linhas_vagas, px);
            }
        }
        
        criar_imagem(img2);
    }
    
    
}