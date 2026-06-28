/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dinossauros;

/**
 *
 * @author Cliente
 */
public class TiranossauroRex extends Dinossauro {

    public TiranossauroRex(int linha, int coluna) {
        super(linha, coluna, 3);
    }

    public int getDanoAtaque() {
        return 2;
    }

    public int getCasasDeMovimento() {
        return 0;
    }

    public boolean vulneravelMaosNuas() {
        return false;
    }

    public boolean vulneravelDardos() {
        return true;
    }

    public boolean seMove() {
        return false;
    }

    @Override
    public char getSimbolo() {
        return 'R';
    }
    
    @Override
    public String toString(){
        return "Tiranossauro Rex (saúde: " + getSaude() + ")";
    }
}
