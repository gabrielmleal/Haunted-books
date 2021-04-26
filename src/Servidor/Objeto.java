/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sara
 */
public class Objeto implements Serializable {
    
    public static final int JOGADOR=0, MONSTRO=1, FLECHA=2;
    protected int id;
    protected int x, y;
    protected int dx, dy, vel;
    protected int largura,altura,clargura,caltura;
    protected int tipo;
    protected int dir;
    protected int animacaoAtual;
    protected int status;
    protected int vida, vidaAtual;
    
    public Objeto (){
        x = y = 0;
        dx = dy = 0;
        tipo = 2;
        vel = 2;
        largura = altura = 30;
        caltura = 28;
        clargura = 18;
        animacaoAtual = 0;
        dir = 0;
    }
    
    public boolean colide(Objeto c){
        Rectangle r1 = delimitador();
        Rectangle r2 = c.delimitador();
        return r1.intersects(r2);
    }
    
    private Rectangle delimitador(){
        return new Rectangle((int)x-clargura, (int)y-caltura, clargura, caltura);
    }
    
    public void andar (int direcao) {
        if(direcao==0){
            dir = 0;
            dx = vel;
            dy = 0;
        }
        if(direcao==1){
            if(tipo==1) dir = 3;
            else dir = 2;
            dx = 0;
            dy = vel;
        }
        if(direcao==2){
            dir = 1;
            dx = -vel;
            dy = 0;
        }
        if(direcao==3){
            if(tipo==1) dir = 2;
            else dir = 3;
            dx = 0;
            dy = -vel;
        }
        if(direcao==4){
            dx = 0;
        }
        if(direcao==5){
            dy = 0;
        }
    }
    
    public int getX(){return x;}
    public int getDX(){return dx;}
    public int getY(){return y;}
    public int getDY(){return dy;}
    public int getTipo(){return tipo;}
    public int getID(){return id;}
    public int getDir(){return dir;}
    public int getAnim(){return animacaoAtual;}
    public void incrAnimacao(){
        animacaoAtual++;
        if(animacaoAtual>2) animacaoAtual = 0;
    }
    public void atualizar(ArrayList<Objeto> criaturas){

    int tempx = x, tempy = y;
    x += dx;
    y += dy;
    for(int i=0;i<criaturas.size();i++){
        if(this.colide(criaturas.get(i)) 
                && ((getTipo()==criaturas.get(i).getTipo() && getID()!=criaturas.get(i).getID()) || getTipo()!=criaturas.get(i).getTipo()) 
                || ((criaturas.get(i).getTipo()==0 || criaturas.get(i).getTipo()==1)
                && (criaturas.get(i).getX()>400-clargura 
                || criaturas.get(i).getY()>280-caltura 
                || criaturas.get(i).getX()< 0 
                || criaturas.get(i).getY()< 0 ))){
            if(tipo==1) randomizarMovimento();
            if(tipo==1 && criaturas.get(i).getTipo()==0) criaturas.get(i).sofreDano(20);
            if(tipo==2){
                sofreDano(1);
                criaturas.get(i).sofreDano(10);
            }
            x = tempx;
            y = tempy;
            break;
        }
    }
    }
    
    public void randomizarMovimento(){
        Random r = new Random();
        
        int i = r.nextInt(4);
        
        if(i==0){
            andar(0);
        }
        else if(i==1){
            andar(2);
        }
        else if(i==2){
            andar(1);
        }
        else if(i==3){
            andar(3);
        }
    }
    public void setStatus(int status){this.status = status;}
    public int getStatus(){return status;}
    public int getVida() {return vida;}
    public int getVidaAtual() {return vidaAtual;}
    public void sofreDano(int i) {vidaAtual-=i;}
    
}
