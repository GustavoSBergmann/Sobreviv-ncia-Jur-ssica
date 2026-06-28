package Dinossauros;
import Jogador_Mapa_Outros.Personagem;

/**
 *
 * @author Cliente
 */
public abstract class Dinossauro extends Personagem{
    
    public Dinossauro(int linha, int coluna, int saude){
        super(linha, coluna, saude);
    }
    
    /**
     * Retorna o dano que este dinossauro causa por ataque.
     * Inimigos comuns: 1 ponto; especial (T-Rex): 2 pontos.
     */
    public abstract int getDanoAtaque();

    /**
     * Retorna quantas casas o dinossauro se move por turno.
     */
    public abstract int getCasasDeMovimento();

    /**
     * Indica se este dinossauro pode ser morto com as mãos nuas.
     */
    public abstract boolean vulneravelMaosNuas();

    /**
     * Indica se este dinossauro pode ser atingido por dardos.
     */
    public abstract boolean vulneravelDardos();

    /**
     * Indica se este dinossauro se move no mapa.
     */
    public abstract boolean seMove();
}
