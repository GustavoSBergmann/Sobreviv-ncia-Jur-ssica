/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
public class Jogador extends Personagem{
    private int percepcao;
    
    public Jogador(int linha, int coluna, int percepcao){
        super(linha, coluna, 5);
        this.percepcao = percepcao;
    }
    
}
