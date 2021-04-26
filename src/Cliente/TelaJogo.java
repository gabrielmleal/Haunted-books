/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Servidor.Criatura;
import Servidor.Objeto;
import Acoes.Acoes;
import Servidor.Jogador;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class TelaJogo extends Tela implements Runnable {
    
    
    private Acoes acoes;
    private int id;
    private boolean direita, esquerda, baixo, cima;
    private ArrayList<Criatura> criaturas;
    private ArrayList<Objeto> objetos;
    private boolean inicializando, falhou, aguardando, permiteatualizacao;
    private Font fonteCarregando;
    private Color corCarregando;
    private ArrayList<BufferedImage[]> sprites;
    private int[] numFrames =  {3, 3, 3, 3};
    
    
    private ArrayList<BufferedImage[]> spritesflechas;
    private int[] numFramesFlechas =  {2, 2, 2, 2, 3};
    
    private ArrayList<BufferedImage[]> spritesatacar;
    private int[] numFramesAtacar =  {1 ,1 ,1 ,1 };
    
    private ArrayList<BufferedImage[]> spritesmonstro;
    private int[] numFramesMonstros =  {2, 2, 2, 2};
    
    private Thread thread;
    private int animacaoAtual;
    private int animacaoAtualMonstro;
    
    public TelaJogo(GerenciadorDeTelas gt){
        this.gt = gt;
        inicializando = true;
        falhou = false;
        aguardando = true;
        permiteatualizacao = false;
        direita = esquerda = baixo = cima = false;
        
        try{
            
            BufferedImage personagemsprite = ImageIO.read(getClass().getResourceAsStream("/Imagens/PersonagemSD.png"));
                    
            sprites = new ArrayList<>();
            for(int i=0;i<numFrames.length;i++){
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j=0;j<numFrames[i];j++){
                        bi[j] = personagemsprite.getSubimage(30*j, 30*i, 30, 30);
                }
                sprites.add(bi);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            BufferedImage personagemsprite = ImageIO.read(getClass().getResourceAsStream("/Imagens/Flecha.png"));
                    
            spritesflechas = new ArrayList<>();
            for(int i=0;i<numFramesFlechas.length;i++){
                BufferedImage[] bi = new BufferedImage[numFramesFlechas[i]];
                for(int j=0;j<numFramesFlechas[i];j++){
                        bi[j] = personagemsprite.getSubimage(30*j, 30*i, 30, 30);
                }
                spritesflechas.add(bi);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            BufferedImage personagemsprite = ImageIO.read(getClass().getResourceAsStream("/Imagens/Atirar.png"));
                    
            spritesatacar = new ArrayList<>();
            for(int i=0;i<numFramesAtacar.length;i++){
                BufferedImage[] bi = new BufferedImage[numFramesAtacar[i]];
                for(int j=0;j<numFramesAtacar[i];j++){
                        bi[j] = personagemsprite.getSubimage(30*j, 30*i, 30, 30);
                }
                spritesatacar.add(bi);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            BufferedImage personagemsprite = ImageIO.read(getClass().getResourceAsStream("/Imagens/InimigoSD.png"));
                    
            spritesmonstro = new ArrayList<>();
            for(int i=0;i<numFramesMonstros.length;i++){
                BufferedImage[] bi = new BufferedImage[numFramesMonstros[i]];
                for(int j=0;j<numFramesMonstros[i];j++){
                        bi[j] = personagemsprite.getSubimage(30*j, 30*i, 30, 30);
                }
                spritesmonstro.add(bi);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void inicializa(){
        
        objetos = new ArrayList<>();
        fonteCarregando = new Font("Arial",Font.PLAIN,20);
        //define as cores
        corCarregando = Color.RED.darker();
            
        String j = JOptionPane.showInputDialog(null,"Digite o IP do servidor","IP do servidor", JOptionPane.INFORMATION_MESSAGE);
        aguardando = false;
        try{
            acoes = (Acoes) Naming.lookup("//"+j+":1234/Jogo");
            id = acoes.getID();
            inicializando = false;
            permiteatualizacao = true;
            animacaoAtual = animacaoAtualMonstro = 0;
            if(thread==null){
                thread = new Thread(this);
                thread.start();
            }
        }
        catch(Exception e){
            falhou = true;
            e.printStackTrace();
        }
    }
    
    public void atualiza(){
        if(permiteatualizacao){
            try {
                objetos = acoes.getObjetos();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void desenha(Graphics2D g){
        g.setColor(Color.GREEN.darker());
        g.fillRect(0, 0, Cliente.LARGURA, Cliente.ALTURA);
        if(inicializando){
            g.setFont(fonteCarregando);
            g.setColor(corCarregando);
            if(aguardando){
                g.drawString("Aguardando IP do servidor", 300-"Aguardando IP do servidor".length()/2*fonteCarregando.getSize(), 100);
            }
            else if(!falhou){
                g.drawString("Conectando-se...", 300-"Conectando-se...".length()/2*fonteCarregando.getSize(), 100);
            }
            else{
                g.drawString("Falha ao conectar!", 300-"Falha ao conectar!".length()/2*fonteCarregando.getSize(), 100);
                
            }
        }
        else{
            desenharObjetos(g);
            
        }
    }
    public void keyPressed(int k){
        try{
            if(k == KeyEvent.VK_RIGHT) acoes.andar(id, 0);
            if(k == KeyEvent.VK_DOWN) acoes.andar(id, 1);
            if(k == KeyEvent.VK_LEFT) acoes.andar(id, 2);
            if(k == KeyEvent.VK_UP) acoes.andar(id, 3);
            if(k == KeyEvent.VK_Z) acoes.atacar(id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public void keyReleased(int k){
        try{
            if(k == KeyEvent.VK_RIGHT) acoes.parar(id, 0);
            if(k == KeyEvent.VK_DOWN) acoes.parar(id, 1);
            if(k == KeyEvent.VK_LEFT) acoes.parar(id, 2);
            if(k == KeyEvent.VK_UP) acoes.parar(id, 3);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void desenharPainel(Graphics2D g){
        
    }
    
    private void desenharObjetos(Graphics2D g){
        for(int i=0;i<objetos.size();i++){
            if(objetos.get(i).getTipo()==0){
                g.setFont(new Font("Arial", Font.PLAIN, 8));
                g.setColor(Color.RED);
                g.drawString("Id "+objetos.get(i).getID(), (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()-10));
                g.drawString("HP : "+objetos.get(i).getVida()+"/"+objetos.get(i).getVidaAtual(), (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()-2));
                if(objetos.get(i).getStatus()==1){
                    g.drawImage(spritesatacar.get(objetos.get(i).getDir())[0], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                }
                else{
                    if(objetos.get(i).getDX()==0 && objetos.get(i).getDY()==0) g.drawImage(sprites.get(objetos.get(i).getDir())[1], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                    else g.drawImage(sprites.get(objetos.get(i).getDir())[animacaoAtual], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                    objetos.get(i).incrAnimacao();
                }
            }
            else if(objetos.get(i).getTipo()==1){
                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.setColor(Color.RED);
                g.drawString("HP : "+objetos.get(i).getVida()+"/"+objetos.get(i).getVidaAtual(), (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()-3));
                g.drawImage(spritesmonstro.get(objetos.get(i).getDir())[animacaoAtualMonstro], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
            }
            else if(objetos.get(i).getTipo()==2){
                if(objetos.get(i).getDir()==2) g.drawImage(spritesflechas.get(3)[0], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                else if(objetos.get(i).getDir()==3 ) g.drawImage(spritesflechas.get(2)[0], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                else g.drawImage(spritesflechas.get(objetos.get(i).getDir())[0], (int)(objetos.get(i).getX()), (int)(objetos.get(i).getY()), null);
                
                    
                }
            }
        }
    
    
    private void desenharMapa(Graphics2D g){
        
    }

    @Override
    public void run() {
        while(true){
            animacaoAtualMonstro++;
            animacaoAtual++;
            if(animacaoAtual>2) animacaoAtual = 0;
            if(animacaoAtualMonstro>1) animacaoAtualMonstro = 0;
            try{
                Thread.sleep(150);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
}
