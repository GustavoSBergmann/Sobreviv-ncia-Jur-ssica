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

    protected static final int[][] DIRECOES = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private static final Random RAND = new Random();

    public Dinossauro(int linha, int coluna, int saude) {
        super(linha, coluna, saude);
    }

    public abstract int getDanoAtaque();

    public abstract int getCasasDeMovimento();

    public abstract boolean vulneravelMaosNuas();

    public abstract boolean vulneravelDardos();

    // ------------------------------------------------------------
    //  ESTRATÉGIAS DE DIREÇÃO (reaproveitáveis pelas subclasses)
    // ------------------------------------------------------------
    protected List<int[]> direcoesEmbaralhadas() {
        List<int[]> direcoes = new ArrayList<>(Arrays.asList(DIRECOES));
        Collections.shuffle(direcoes, RAND);
        return direcoes;
    }

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
