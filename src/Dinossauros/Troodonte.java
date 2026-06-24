/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dinossauros;

/**
 *
 * @author Cliente
 */
public class Troodonte extends Dinossauro{
    
    public Troodonte(int linha, int coluna){
        super(linha, coluna, 2);
    }
    
    @Override
    public char getSimbolo() {
        return 'C';
    }
}
