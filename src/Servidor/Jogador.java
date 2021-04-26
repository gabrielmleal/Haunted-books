/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Rectangle;

/**
 *
 * @author Sara
 */
public class Jogador extends Criatura {
    
    
    public static final int PASSIVO = 0, ATIRANDO = 1;
    
    public Jogador(int id){
        this.id = id;
        tipo = 0;
        vida = vidaAtual = 200;
    }
    

}
