/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Graphics2D;

/**
 *
 * @author Gabriel
 */
public abstract class Tela {
    
    protected GerenciadorDeTelas gt;
    
    public void inicializa(){}
    public void atualiza(){}
    public void desenha(Graphics2D g){}
    public void keyPressed(int k){}
    public void keyReleased(int k){}
}
