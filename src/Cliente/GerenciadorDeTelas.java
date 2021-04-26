/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class GerenciadorDeTelas {
    
    public static final int TELA_MENU = 0;
    public static final int TELA_AJUDA = 1;
    public static final int TELA_JOGO = 2;
    
    private ArrayList<Tela> telas;
    private int telaAtual;
    
    public GerenciadorDeTelas(){
        telaAtual = TELA_MENU;
        telas = new ArrayList<>();
        
        telas.add(new TelaMenu(this));
        telas.add(new TelaAjuda(this));
        telas.add(new TelaJogo(this));
    }
    
    public void mudarTela(int tela){
        telaAtual = tela;
        telas.get(telaAtual).inicializa();
    }
    public void atualiza(){
        telas.get(telaAtual).atualiza();
        
    }
    public void desenha(Graphics2D g){
        telas.get(telaAtual).desenha(g);
    }
    public void keyPressed(int k){
        telas.get(telaAtual).keyPressed(k);
    }
    public void keyReleased(int k){
        telas.get(telaAtual).keyReleased(k);
    }
    
    public int getTela(){return telaAtual;}
    
}
