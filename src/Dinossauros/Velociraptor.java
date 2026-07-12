package Dinossauros;

import Jogador_Mapa_Outros.Mapa;

/**
 * Dinossauro ágil: move-se aleatoriamente por até duas casas a cada
 * passo, podendo parar na primeira caso encontre o jogador. É muito
 * difícil de acertar com arma de disparo.
 *
 * @author Cliente
 */
public class Velociraptor extends Dinossauro {

    private static final long serialVersionUID = 1L;

    public Velociraptor(int linha, int coluna) {
        super(linha, coluna, 2);
    }

    @Override
    public int getDanoAtaque() {
        return 1;
    }

    @Override
    public int getCasasDeMovimento() {
        return 2;
    }

    @Override
    public boolean vulneravelMaosNuas() {
        return true;
    }

    @Override
    public boolean vulneravelDardos() {
        return false;
    }

    @Override
    public boolean mover(Mapa mapa) {
        // Movimento aleatório e rápido: a cada passo sorteia a ordem das
        // direções e pede ao Mapa para executar apenas o passo escolhido.
        for (int passo = 0; passo < getCasasDeMovimento(); passo++) {
            boolean moveu = false;
            for (int[] direcao : direcoesEmbaralhadas()) {
                Mapa.ResultadoPasso resultado = mapa.tentarMoverEntidade(
                        this, getLinha() + direcao[0], getColuna() + direcao[1]);
                if (resultado == Mapa.ResultadoPasso.ENCONTROU_JOGADOR) {
                    return true; // pode parar na primeira casa, se encontrar o jogador
                }
                if (resultado == Mapa.ResultadoPasso.MOVEU) {
                    moveu = true;
                    break;
                }
            }
            if (!moveu) {
                break;
            }
        }
        return false;
    }

    @Override
    public char getCaractere() {
        return 'V';
    }

    @Override
    public String toString() {
        return "Velociraptor (saúde: " + getSaude() + ")";
    }
}
