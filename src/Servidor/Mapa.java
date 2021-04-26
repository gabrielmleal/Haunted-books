/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Sara
 */
public class Mapa {
    
    private int mapa[][];
    private int tamanhoBloco;
    private int numCols, numLinhas;
    private int larguraMapa, alturaMapa;
    
    public Mapa (){
        tamanhoBloco = 30;
    }
    
    public void importarMapa(String s){
        try{
            //Carrega o arquivo
            InputStream in = getClass().getResourceAsStream(s);
            //Cria um leitor de arquivo
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            //A primeira linha será o número de colunas
            numCols = Integer.parseInt(br.readLine());
            //A segunda linha seráo número de linhas
            numLinhas = Integer.parseInt(br.readLine());
            
            //Define a quantidade de elementos da matriz de mapa, a largura e a altura
            mapa = new int[numLinhas][numCols];
            larguraMapa = numCols*tamanhoBloco;
            alturaMapa = numLinhas*tamanhoBloco;
            
            
            //cria uma string auxiliar para ler os dados do mapa
            String divisor = "\\s+";
            for(int linha = 0; linha < numLinhas; linha++) {//enquanto não tiver lido todas as linhas
		String Linha = br.readLine();//lê a linha atual
		String[] partes = Linha.split(divisor);//divide a linha em strings diferentes, onde o divisor é a tecla espaço
		for(int coluna = 0; coluna < numCols; coluna++) {//enquanto não tiver lido todas as colunas
			mapa[linha][coluna] = Integer.parseInt(partes[coluna]);//grava na matriz mapa os dados do mapa
		}
            }
        }
        
        catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public int[][] getMapa(){ return mapa;}
    
}
