package Dinossauros;

/**
 *
 * @author Cliente
 */
public class Troodonte extends Dinossauro {

    public Troodonte(int linha, int coluna) {
        super(linha, coluna, 2);
    }

    public int getDanoAtaque() {
        return 1;
    }

    public int getCasasDeMovimento() {
        return 1;
    }

    public boolean vulneravelMaosNuas() {
        return true;
    }

    public boolean vulneravelDardos() {
        return true;
    }

    public boolean seMove() {
        return true;
    }

    @Override
    public char getSimbolo() {
        return 'T';
    }
    
    @Override
    public String toString(){
        return "Troodonte (saúde: " + getSaude() + ")";
    }
}
