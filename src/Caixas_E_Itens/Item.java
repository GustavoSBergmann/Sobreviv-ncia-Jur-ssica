/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caixas_E_Itens;
import Jogador_Mapa_Outros.Jogador;

/**
 *
 * @author Cliente
 */
public abstract class Item implements ConteudoCaixa{
    private String nome;
    
    public Item(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return nome;
    }
    
    public abstract void usar(Jogador jogador);
}
