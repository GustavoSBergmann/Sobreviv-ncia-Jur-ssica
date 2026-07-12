package Dinossauros;

import Jogador_Mapa_Outros.Mapa;

/**
 * Tipo comum de dinossauro. Possui um comportamento de movimentação mais
 * estratégico: desloca-se em direção à posição do jogador. A resistência
 * dele contra golpes de mãos nuas não críticos é tratada diretamente em
 * {@code Combate}, já que é uma regra específica de uma única espécie.
 *
 * @author Cliente
 */
public class Troodonte extends Dinossauro {

    private static final long serialVersionUID = 1L;

    public Troodonte(int linha, int coluna) {
        super(linha, coluna, 2);
    }

    @Override
    public int getDanoAtaque() {
        return 1;
    }

    @Override
    public int getCasasDeMovimento() {
        return 1;
    }

    @Override
    public boolean vulneravelMaosNuas() {
        return true;
    }

    @Override
    public boolean vulneravelDardos() {
        return true;
    }

    @Override
    public boolean mover(Mapa mapa) {
        // Comportamento estratégico: a cada passo, escolhe a direção que
        // mais aproxima do jogador (com desempate aleatório) e pede ao
        // Mapa para executar apenas ESSE passo.
        for (int passo = 0; passo < getCasasDeMovimento(); passo++) {
            boolean moveu = false;
            for (int[] direcao : direcoesOrdenadasPorProximidade(mapa)) {
                Mapa.ResultadoPasso resultado = mapa.tentarMoverEntidade(
                        this, getLinha() + direcao[0], getColuna() + direcao[1]);
                if (resultado == Mapa.ResultadoPasso.ENCONTROU_JOGADOR) {
                    return true;
                }
                if (resultado == Mapa.ResultadoPasso.MOVEU) {
                    moveu = true;
                    break;
                }
            }
            if (!moveu) {
                break; // cercado por paredes/outros dinossauros: para de tentar
            }
        }
        return false;
    }

    @Override
    public char getCaractere() {
        return 'T';
    }

    @Override
    public String toString() {
        return "Troodonte (saúde: " + getSaude() + ")";
    }
}
