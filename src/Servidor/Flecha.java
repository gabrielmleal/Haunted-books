/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Gabriel
 */
public class Flecha extends Objeto {
    
    public Flecha(Jogador j){
        vida = vidaAtual = 1;
        id = j.getID();
        vel = 5;
        dx = dy = 0;
        if(j.getDir()==0){
            x = j.getX()+30;
            y = j.getY();
            dx = vel;
            dir = 0;
        }
        else if(j.getDir()==1){
            x = j.getX()-30;
            y = j.getY();
            dx = -vel;
            dir = 1;
        }
        else if(j.getDir()==2){
            x = j.getX();
            y = j.getY()+30;
            dy = vel;
            dir = 2;
        }
        else if(j.getDir()==3){
            x = j.getX();
            y = j.getY()-30;
            dy = -vel;
            dir = 3;
        }
    }
            
}
