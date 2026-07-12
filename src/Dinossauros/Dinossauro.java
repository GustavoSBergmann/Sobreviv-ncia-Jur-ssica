package Dinossauros;

import Jogador_Mapa_Outros.Mapa;
import Jogador_Mapa_Outros.Personagem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Dinossauro extends Personagem implements Movivel {

    private static final long serialVersionUID = 1L;

    /**
     * As quatro direções possíveis de movimento (sem diagonais).
     */
    protected static final int[][] DIRECOES = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private static final Random RAND = new Random();

    public Dinossauro(int linha, int coluna, int saude) {
        super(linha, coluna, saude);
    }

    /**
     * Retorna o dano que este dinossauro causa por ataque. Inimigos comuns: 1
     * ponto; especial (T-Rex): 2 pontos.
     */
    public abstract int getDanoAtaque();

    /**
     * Retorna quantas casas o dinossauro se move por "passo" da thread de
     * movimentação.
     */
    public abstract int getCasasDeMovimento();

    /**
     * Indica se este dinossauro pode ser ferido com as mãos nuas (ou bastão
     * elétrico).
     */
    public abstract boolean vulneravelMaosNuas();

    /**
     * Indica se este dinossauro pode ser atingido por dardos.
     */
    public abstract boolean vulneravelDardos();

    // ------------------------------------------------------------
    //  ESTRATÉGIAS DE DIREÇÃO (reaproveitáveis pelas subclasses)
    // ------------------------------------------------------------
    /**
     * As quatro direções em ordem aleatória — usado por dinossauros sem
     * comportamento estratégico (ex.: Velociraptor).
     */
    protected List<int[]> direcoesEmbaralhadas() {
        List<int[]> direcoes = new ArrayList<>(Arrays.asList(DIRECOES));
        Collections.shuffle(direcoes, RAND);
        return direcoes;
    }

    /**
     * As quatro direções ordenadas da que mais aproxima até a que mais afasta o
     * dinossauro do jogador (com desempate aleatório) — usado por dinossauros
     * que perseguem o jogador (ex.: Troodonte).
     */
    protected List<int[]> direcoesOrdenadasPorProximidade(Mapa mapa) {
        List<int[]> direcoes = direcoesEmbaralhadas();
        direcoes.sort((d1, d2) -> Integer.compare(
                distanciaAoJogadorApos(mapa, d1), distanciaAoJogadorApos(mapa, d2)));
        return direcoes;
    }

    private int distanciaAoJogadorApos(Mapa mapa, int[] direcao) {
        int novaLinha = getLinha() + direcao[0];
        int novaColuna = getColuna() + direcao[1];
        return Math.abs(novaLinha - mapa.getLinhaJogador()) + Math.abs(novaColuna - mapa.getColunaJogador());
    }
}
