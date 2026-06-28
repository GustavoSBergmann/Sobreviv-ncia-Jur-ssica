package Dinossauros;

/**
 *
 * @author Cliente
 */
public class Velociraptor extends Dinossauro{
    
    public Velociraptor(int linha, int coluna){
        super(linha, coluna, 2);
    }
    
    public int getDanoAtaque() {
        return 1;
    }

    public int getCasasDeMovimento() {
        return 2;
    }

    public boolean vulneravelMaosNuas() {
        return true;
    }

    public boolean vulneravelDardos() {
        return false;
    }

    public boolean seMove() {
        return true;
    }
    
    @Override
    public char getSimbolo() {
        return 'V';
    }
    
    @Override
    public String toString(){
        return "Velociraptor (saúde: " + getSaude() + ")";
    }
}
