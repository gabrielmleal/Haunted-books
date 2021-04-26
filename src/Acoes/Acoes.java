/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acoes;

import Servidor.Jogador;
import Servidor.Objeto;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public interface Acoes extends Remote {
    public int getID() throws RemoteException;
    public boolean andar(int id, int direcao) throws RemoteException;
    public boolean parar(int id, int direcao) throws RemoteException;
    public boolean atacar(int id) throws RemoteException;
    public int[][] getMapa() throws RemoteException;
    public ArrayList getObjetos() throws RemoteException;
    public void enviarMensagem(int id, String msg) throws RemoteException;
    public String getChat() throws RemoteException;
}
