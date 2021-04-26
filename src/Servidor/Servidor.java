/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Acoes.Acoes;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Sara
 */
public class Servidor extends UnicastRemoteObject implements Acoes, Runnable {

    private int idJogadores=0;
    private ArrayList<Criatura> criaturas;
    private ArrayList<Objeto> objetos;
    private Thread thread;
    private Mapa mapa;
    private String conversa;
    private int direcionador;
    
    
    public Servidor() throws RemoteException{
        super();
        conversa = "";
        criaturas = new ArrayList<>();
        direcionador = 0;
        objetos = new ArrayList<>();
        mapa = new Mapa();
        if(thread==null){
            thread = new Thread(this);
            thread.start();
        }
        
        objetos.add(new Monstro(50, 50));
        objetos.add(new Monstro(200, 50));
        objetos.add(new Monstro(50, 200));
        objetos.add(new Monstro(200, 200));
    }
    
    public static void main(String[] args) {
        try{
            String s = JOptionPane.showInputDialog(null,"Digite o IP local do filosofo servidor","IP do servidor", JOptionPane.INFORMATION_MESSAGE);
            System.setProperty("java.rmi.server.hostname",s);
            Servidor sv = new Servidor();
            LocateRegistry.createRegistry(1234);
            Naming.bind("//localhost:1234/Jogo", sv);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getID() throws RemoteException {
        Jogador j = new Jogador(idJogadores);
        criaturas.add(j);
        objetos.add(j);
        System.out.println("Novo jogador de id "+idJogadores);
        return idJogadores++;
    }

    @Override
    public boolean andar(int id, int direcao) throws RemoteException {
        boolean b = true;
        for(int i=0;i<objetos.size();i++){
            if(objetos.get(i).getID()==id && objetos.get(i).getTipo()==0){
                for(int j=0;j<criaturas.size();j++){
                    if(objetos.get(i).colide(criaturas.get(j)) && objetos.get(i).getID()!=criaturas.get(j).getID()){
                        b = false;
                        break;
                    }
                }
                if(b){
                    objetos.get(i).andar(direcao);
                    objetos.get(i).setStatus(0);
                }
                break;
            }
        }
        return b;
    }

    @Override
    public boolean atacar(int id) throws RemoteException {
        for(int i=0;i<criaturas.size();i++){
            if(criaturas.get(i).getTipo()==0 && criaturas.get(i).getID()== id){
                objetos.add(new Flecha((Jogador)criaturas.get(i)));
                criaturas.get(i).setStatus(1);
                break;
            }
        }
        return true;
    }

    @Override
    public int[][] getMapa() throws RemoteException {
        return mapa.getMapa();
    }

    @Override
    public ArrayList getObjetos() throws RemoteException {
        return objetos;
    }

    @Override
    public void run() {
        while(true){
            direcionador++;
            for(int i=0;i<objetos.size();i++){
                if(direcionador==200 && objetos.get(i).getTipo()==1){
                    objetos.get(i).randomizarMovimento();
                }
                objetos.get(i).atualizar(objetos);
                if(objetos.get(i).getVidaAtual()<=0) objetos.remove(objetos.get(i));
            }
            
            if(direcionador==300) direcionador = 0;
            try{
                Thread.sleep(1000/60);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean parar(int id, int direcao) throws RemoteException {
        boolean b = true;
        for(int i=0;i<criaturas.size();i++){
            if(criaturas.get(i).getTipo()==0 && criaturas.get(i).getID()==id){
                if(direcao==0 || direcao==2) criaturas.get(i).andar(4);
                else if(direcao==1 || direcao==3) criaturas.get(i).andar(5);
            }
        }
        return b;
    }

    @Override
    public void enviarMensagem(int id, String msg) throws RemoteException {
        conversa += "ID "+id+" : "+ msg + '\n';
    }

    @Override
    public String getChat() throws RemoteException {
        return conversa;
    }
    
}
