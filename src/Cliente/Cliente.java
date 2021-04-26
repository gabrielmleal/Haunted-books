/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Sara
 */
public class Cliente {
    public static final int LARGURA = 400, ALTURA = 280, ESCALA = 2;
    
    public static void main(String[] args) {
        
        JFrame janela = new JFrame("The game");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setContentPane(new JogoPanel());
        janela.setResizable(false);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}

class JogoPanel extends JPanel implements Runnable, KeyListener {
    
    private static final int LARGURA = 400, ALTURA = 280, ESCALA = 2;
    private Thread thread;
    private GerenciadorDeTelas gt;
    private BufferedImage imagem;
    private JTextArea ta1;
    private JScrollPane sp1;
    private Graphics2D g2;
    private boolean possuiChat;
    
    public JogoPanel(){
        setPreferredSize(new Dimension(LARGURA*ESCALA,ALTURA*ESCALA));
        setFocusable(true);
        possuiChat = false;
        ta1 = new JTextArea(11,19);
        ta1.setLineWrap(true);
        ta1.setEditable(false);
        ta1.setForeground(Color.black);
        
        sp1 = new JScrollPane(ta1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread==null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    @Override
    public void run() {
        imagem = new BufferedImage(LARGURA, ALTURA, TYPE_INT_RGB);
        g2 = (Graphics2D) imagem.getGraphics();
        
        gt = new GerenciadorDeTelas();
        while(true){
            gt.atualiza();
            gt.desenha(g2);
            desenhaNaTela();
            try{
                Thread.sleep(1000/60);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void desenhaNaTela(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(imagem, 0, 0, LARGURA*ESCALA, ALTURA*ESCALA, null);
        g2.dispose();
    }
    
    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k){
        gt.keyPressed(k.getKeyCode());
        
    }
    
    @Override
    public void keyReleased(KeyEvent k){
        gt.keyReleased(k.getKeyCode());
    }
}
