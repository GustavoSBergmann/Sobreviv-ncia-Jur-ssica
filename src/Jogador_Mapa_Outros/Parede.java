/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
public class Parede extends Entidade{
    //Serve apenas como obstáculo
    
    @Override
    public char getSimbolo() {
        return '#';
    }
}
