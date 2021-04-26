/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Sara
 */
public class Monstro extends Criatura {
    
    public Monstro(int x, int y){
        this.x = x;
        this.y = y;
        tipo = 1;
        vel = 1;
        vida = vidaAtual = 100;
    }
}
