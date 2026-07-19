package Jogador_Mapa_Outros;

import Dinossauros.*;
import Caixas_E_Itens.*;
import Exceptions.MovimentoInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Mapa implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int[][] DIRECOES = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private Entidade[][] mapa;
    private Jogador jogador;
    private List<Dinossauro> dinossauros;
    private CaixaDeSuprimentos[][] camadaCaixas;

    private int tamanho;
    private boolean debugAtivo = false;

    public Mapa(int tamanho) {
        this.tamanho = tamanho;
        mapa = new Entidade[tamanho][tamanho];
        dinossauros = new ArrayList<>();
        camadaCaixas = new CaixaDeSuprimentos[tamanho][tamanho];
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<Dinossauro> getDinossauros() {
        return dinossauros;
    }

    public void limpar() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mapa[i][j] = null;
            }
        }
        dinossauros.clear();
    }

    public void colocarEntidade(int linha, int coluna, Entidade entidade) {
        mapa[linha][coluna] = entidade;
    }

    public void adicionarDinossauro(Dinossauro dino) {
        dinossauros.add(dino);
    }

    public boolean podeColocarCaixa(int linha, int coluna) {
        return camadaCaixas[linha][coluna] == null && !(mapa[linha][coluna] instanceof Parede);
    }

    public void colocarCaixa(int linha, int coluna, CaixaDeSuprimentos caixa) {
        camadaCaixas[linha][coluna] = caixa;
    }

    // -----------------
    // VISUALIZAÇÃO (linha de visão)
    // -----------------
    public char getCelulaVisivel(int linha, int coluna) {
        if (debugAtivo) {
            return getSimbolo(linha, coluna);
        }

        int linJogador = jogador.getLinha();
        int colJogador = jogador.getColuna();

        if (linha == linJogador && coluna == colJogador) {
            return 'J';
        }
        if (linha != linJogador && coluna != colJogador) {
            return '.';
        }
        if (temObstaculoEntre(linJogador, colJogador, linha, coluna)) {
            return '.';
        }
        return getSimbolo(linha, coluna);
    }

    private boolean temObstaculoEntre(int linJogador, int colJogador, int linha, int coluna) {
        if (linJogador == linha) {
            int inicio = Math.min(colJogador, coluna) + 1;
            int fim = Math.max(colJogador, coluna);
            for (int c = inicio; c < fim; c++) {
                if (eObstaculo(linJogador, c)) {
                    return true;
                }
            }
        } else {
            int inicio = Math.min(linJogador, linha) + 1;
            int fim = Math.max(linJogador, linha);
            for (int l = inicio; l < fim; l++) {
                if (eObstaculo(l, colJogador)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean eObstaculo(int l, int c) {
        return (mapa[l][c] instanceof Parede
                || mapa[l][c] instanceof Dinossauro
                || camadaCaixas[l][c] != null);
    }

    private char getSimbolo(int linha, int coluna) {
        Entidade entidade = mapa[linha][coluna];
        CaixaDeSuprimentos caixa = camadaCaixas[linha][coluna];
        if (entidade != null) {
            return entidade.getCaractere();
        } else if (caixa != null) {
            return caixa.getCaractere();
        }
        return '.';
    }

    // -----------------
    // JOGADOR
    // -----------------
    public synchronized Dinossauro moverJogador(int linha, int coluna) throws MovimentoInvalidoException {
        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        int difLinha = Math.abs(linhaAtual - linha);
        int difColuna = Math.abs(colunaAtual - coluna);

        if ((difLinha + difColuna) != 1) {
            throw new MovimentoInvalidoException("O jogador só pode se mover uma casa na horizontal ou na vertical.");
        }
        if (linha < 0 || coluna < 0 || linha >= tamanho || coluna >= tamanho) {
            throw new MovimentoInvalidoException("Posição fora do mapa!");
        }
        if (mapa[linha][coluna] instanceof Parede) {
            throw new MovimentoInvalidoException("Tem uma parede ali! Caminho bloqueado.");
        }
        if (mapa[linha][coluna] instanceof Dinossauro) {
            return (Dinossauro) mapa[linha][coluna];
        }

        mapa[jogador.getLinha()][jogador.getColuna()] = null;
        jogador.setPosicao(linha, coluna);
        mapa[linha][coluna] = jogador;
        return null;
    }

    public synchronized void jogadorAvancaParaPosicaoDino(int linha, int coluna) {
        mapa[jogador.getLinha()][jogador.getColuna()] = null;
        mapa[linha][coluna] = jogador;
        jogador.setPosicao(linha, coluna);
    }

    public synchronized boolean fugir(int linha, int coluna) {
        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        int difLinha = Math.abs(linha - linhaAtual);
        int difColuna = Math.abs(coluna - colunaAtual);

        if ((difLinha + difColuna) > 1) {
            return false;
        }
        if (linha < 0 || coluna < 0 || linha >= tamanho || coluna >= tamanho) {
            return false;
        }
        if (mapa[linha][coluna] instanceof Parede || mapa[linha][coluna] instanceof Dinossauro) {
            return false;
        }

        mapa[linhaAtual][colunaAtual] = null;
        jogador.setPosicao(linha, coluna);
        mapa[linha][coluna] = jogador;
        return true;
    }

    public synchronized List<int[]> posicoesParaFuga() {
        List<int[]> livres = new ArrayList<>();
        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        for (int[] d : DIRECOES) {
            int novaLinha = linhaAtual + d[0];
            int novaColuna = colunaAtual + d[1];
            if (novaLinha < 0 || novaLinha >= tamanho || novaColuna < 0 || novaColuna >= tamanho) {
                continue;
            }
            if (mapa[novaLinha][novaColuna] instanceof Parede) {
                continue;
            }
            if (mapa[novaLinha][novaColuna] instanceof Dinossauro) {
                continue;
            }
            livres.add(new int[]{novaLinha, novaColuna});
        }
        return livres;
    }

    // -----------------
    // DINOSSAUROS
    // -----------------
    /**
     * Resultado de uma tentativa de mover uma entidade uma casa no mapa.
     */
    public enum ResultadoPasso {
        MOVEU,
        BLOQUEADO,
        ENCONTROU_JOGADOR
    }

    public synchronized boolean dentroDoMapa(int linha, int coluna) {
        return linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho;
    }

    public synchronized boolean celulaLivre(int linha, int coluna) {
        return dentroDoMapa(linha, coluna) && mapa[linha][coluna] == null;
    }

    public synchronized int getLinhaJogador() {
        return jogador.getLinha();
    }

    public synchronized int getColunaJogador() {
        return jogador.getColuna();
    }

    public synchronized ResultadoPasso tentarMoverEntidade(Dinossauro dino, int novaLinha, int novaColuna) {
        if (!dentroDoMapa(novaLinha, novaColuna)) {
            return ResultadoPasso.BLOQUEADO;
        }
        Entidade destino = mapa[novaLinha][novaColuna];
        if (destino == jogador) {
            return ResultadoPasso.ENCONTROU_JOGADOR;
        }
        if (destino != null) {
            return ResultadoPasso.BLOQUEADO;
        }
        mapa[dino.getLinha()][dino.getColuna()] = null;
        dino.setPosicao(novaLinha, novaColuna);
        mapa[novaLinha][novaColuna] = dino;
        return ResultadoPasso.MOVEU;
    }

    public synchronized void removerDinossauro(Dinossauro dino) {
        if (mapa[dino.getLinha()][dino.getColuna()] == dino) {
            mapa[dino.getLinha()][dino.getColuna()] = null;
        }
        dinossauros.remove(dino);
    }

    public synchronized boolean todosDerrotados() {
        return dinossauros.isEmpty();
    }

    // -----------------
    // CAIXA
    // -----------------
    public synchronized CaixaDeSuprimentos getCaixaEm(int linha, int coluna) {
        return camadaCaixas[linha][coluna];
    }

    public synchronized void removerCaixa(int linha, int coluna) {
        camadaCaixas[linha][coluna] = null;
    }

    // -----------------
    // OUTROS
    // -----------------
    public void setResetDebug() {
        debugAtivo = !debugAtivo;
    }

    public boolean isDebugAtivo() {
        return debugAtivo;
    }

    public int letraParaLinha(char letra) {
        return Character.toUpperCase(letra) - 'A';
    }

    public int numeroParaColuna(int num) {
        return num - 1;
    }

    public String linhaParaLetra(int linha) {
        return String.valueOf((char) ('A' + linha));
    }
}
