/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Gabriel
 */
public class TelaMenu extends Tela {
    
    //declara um vetor de strings que apresenta as opções do menu
    private String[] opcoes = {"Jogar","Ajuda","Sair"};
    //declara o valor da escolha atual
    private int escolhaAtual;
    //declara as fontes que serão escritas os dados do menu
    private Font fonteTitulo, fonteOpcoes;
    //declara as cores dos elementos
    private Color corTitulo, corOpcao, corSelecionado;
    
    //construtor, que é construído a partir de um gerenciador de estados
    public TelaMenu(GerenciadorDeTelas gt){
        this.gt=gt;
        inicializa();
    }
    
    //método herdado, que não precisa ser implementado
    public void inicializa(){
        try{
            
            //define as fontes
            fonteTitulo = new Font("Arial",Font.PLAIN,26);
            fonteOpcoes = new Font("Arial",Font.PLAIN,14);

            //define as cores
            corTitulo = Color.RED.darker();
            corOpcao = Color.BLACK;
            corSelecionado = Color.RED;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //método que atualiza a tela do menu
    public void atualiza(){
        //somente as coordenadas do fundo devem ser atualizadas pelo thread
    }
    
    //método que desenha o estado
    public void desenha(Graphics2D g){
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, Cliente.LARGURA, Cliente.ALTURA);
        //define a cor e a fonte do título
        g.setColor(corTitulo);
        g.setFont(fonteTitulo);
        //escreve o título na tela
        g.drawString("O jogo", 225-"O jogo".length()/2*fonteTitulo.getSize(), 100);
        
        //muda a fonte para fonte de opções
        g.setFont(fonteOpcoes);
        //enquanto i for menor que o número da array da string de opções
        for(int i=0;i<opcoes.length;i++){
            if(escolhaAtual==i){/*se a string for a opção atual, então desenhar ela com uma cor diferenciada
                e desenhar um triângulo que aponte para a opção
                */
                g.setColor(corSelecionado);
                int[] x = {150,150,155};
                int[] y = {150+14*i-10,150+14*i,150+14*i-5};
                g.fillPolygon(x,y,3);
            }
            else{
                //caso contrário, simplemente dar a cor padrão de opções
                g.setColor(corOpcao);
            }
            //desenha a string da opção
            g.drawString(opcoes[i], 200-(opcoes[i].length()/2*fonteOpcoes.getSize()), 150+14*i);
        }
    }
    
    
    //método auxiliar que seleciona a opção
    public void seleciona(){
        if(escolhaAtual==0){
            gt.mudarTela(GerenciadorDeTelas.TELA_JOGO);
        }
        else{
            System.exit(0);
        }
    }
    
    
    //sistema de key listener
    public void keyPressed(int k){
        if(k==KeyEvent.VK_UP){
            //caso a tecla pressionada seja cima, então decrementar escolha atual
            escolhaAtual--;
            if(escolhaAtual<0){//caso fique menor que 0, então a opção atual será a última
                escolhaAtual=opcoes.length-1;
            }
        }
        if(k==KeyEvent.VK_DOWN){
            //caso a tecla pressionada seja baixo, então incrementar escolha atual
            escolhaAtual++;
            if(escolhaAtual==opcoes.length){//caso fique maior que todas as opções, então escolha atual será a primeira
                escolhaAtual=0;
            }
        }
        if(k==KeyEvent.VK_ENTER){
            //caso seja enter, usa o método auxiliar de selecionar
            seleciona();
        }
    }
    
    public void keyReleased(int k){
        
    }
}
