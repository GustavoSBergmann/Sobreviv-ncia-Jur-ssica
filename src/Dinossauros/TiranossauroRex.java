package Dinossauros;

import Jogador_Mapa_Outros.Mapa;

/**
 * Tipo especial e colossal. Não é possível matá-lo com as mãos nuas, e
 * por ser muito grande ele não se move pelo cenário.
 *
 * @author Cliente
 */
public class TiranossauroRex extends Dinossauro {

    private static final long serialVersionUID = 1L;

    public TiranossauroRex(int linha, int coluna) {
        super(linha, coluna, 3);
    }

    @Override
    public int getDanoAtaque() {
        return 2;
    }

    @Override
    public int getCasasDeMovimento() {
        return 0;
    }

    @Override
    public boolean vulneravelMaosNuas() {
        return false;
    }

    @Override
    public boolean vulneravelDardos() {
        return true;
    }

    @Override
    public boolean mover(Mapa mapa) {
        // O Tiranossauro Rex não se move pelo cenário.
        return false;
    }

    @Override
    public char getCaractere() {
        return 'R';
    }

    @Override
    public String toString() {
        return "Tiranossauro Rex (saúde: " + getSaude() + ")";
    }
}
