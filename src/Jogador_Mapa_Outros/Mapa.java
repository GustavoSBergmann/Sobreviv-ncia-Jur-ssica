package Jogador_Mapa_Outros;

import Dinossauros.*;
import Caixas_E_Itens.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Cliente
 */
public class Mapa {

    private Entidade[][] mapa;
    private Jogador jogador;
    private List<Dinossauro> dinossauros;
    private CaixaDeSuprimentos[][] camadaCaixas;
    private Random rand = new Random();

    private int tamanho;

    public Mapa(int tamanho) {
        this.tamanho = tamanho;
        mapa = new Entidade[tamanho][tamanho];
        dinossauros = new ArrayList<>();
        camadaCaixas = new CaixaDeSuprimentos[tamanho][tamanho];
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getTamanho() {
        return tamanho;
    }

    // -----------------
    // MAPA
    // -----------------
    public void gerarMapaAleatorio() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mapa[i][j] = null;
            }
        }
        dinossauros.clear();

        //Insere objeto Jogador no mapa
        jogador.setPosicao(0, 0);
        jogador.setSaude(jogador.getSaudeMaxima());
        jogador.recuperaSaude();
        mapa[0][0] = jogador;

        //Insere objeto TiranossauroRex no mapa
        TiranossauroRex trex = new TiranossauroRex(tamanho - 1, tamanho - 1);
        mapa[tamanho - 1][tamanho - 1] = trex;
        dinossauros.add(trex);

        //Insere objetos Parede no mapa
        insereParedes();

        //Insere objetos Dinossauro
        //2 Compsognatos, 2 Velociraptors, 5 Troodontes
        insereDinossauro(new Compsognato(-1, -1));
        insereDinossauro(new Compsognato(-1, -1));
        insereDinossauro(new Velociraptor(-1, -1));
        insereDinossauro(new Velociraptor(-1, -1));
        insereDinossauro(new Troodonte(-1, -1));
        insereDinossauro(new Troodonte(-1, -1));
        insereDinossauro(new Troodonte(-1, -1));
        insereDinossauro(new Troodonte(-1, -1));
        insereDinossauro(new Troodonte(-1, -1));

        //Insere objetos CaixaDeSuprimentos
        insereCaixa(new KitMedico());
        insereCaixa(new BastaoEletrico());
        insereCaixa(new LancaDardos());
        insereCaixa(new LancaDardos());
        insereCaixa(new Compsognato(-1, -1));
    }

    private void insereParedes() {
        int total = tamanho * tamanho;
        int numParedes = (int) (total * 0.20);
        int colocadas = 0;
        for (int p = 0; p < numParedes * 2 && colocadas < numParedes; p++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);

            if (mapa[lin][col] == null) {
                mapa[lin][col] = new Parede();
                colocadas++;
            }
        }
    }

    private void insereDinossauro(Dinossauro dino) {
        //Procura células livres aleatórias e com uma distância de 2 casas do jogador
        for (int i = 0; i < tamanho * tamanho; i++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);

            if (mapa[lin][col] == null && (lin + col) > 2) {
                dino.setPosicao(lin, col);
                mapa[lin][col] = dino;
                dinossauros.add(dino);
                return;
            }
        }

        //Se não encontrar procura a primeira célula livre
        for (int lin = 0; lin < tamanho; lin++) {
            for (int col = 0; col < tamanho; col++) {
                if (mapa[lin][col] == null && (lin + col) > 1) {
                    dino.setPosicao(lin, col);
                    mapa[lin][col] = dino;
                    dinossauros.add(dino);
                    return;
                }
            }
        }
    }

    private void insereCaixa(ConteudoCaixa conteudo) {
        for (int i = 0; i < tamanho * tamanho; i++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);

            if (!(lin == 0 && col == 0) && camadaCaixas[lin][col] == null
                    && !(mapa[lin][col] instanceof Parede)) {
                camadaCaixas[lin][col] = new CaixaDeSuprimentos(conteudo);
                return;
            }
        }
    }

    public void imprimirMapa() {
        System.out.println();
        // Cabeçalho com números de coluna
        System.out.print("    ");
        for (int j = 0; j < tamanho; j++) {
            System.out.printf("%2d", j + 1);
        }
        System.out.println();

        System.out.print("   +");
        for (int j = 0; j < tamanho; j++) {
            System.out.print("--");
        }
        System.out.println("+");

        try {

            for (int i = 0; i < tamanho; i++) {
                System.out.print(" " + (char) ('A' + i) + "  ");
                for (int j = 0; j < tamanho; j++) {
                    if (mapa[i][j] == null && camadaCaixas[i][j] == null) {
                        System.out.print(" .");
                    } else if (camadaCaixas[i][j] == null) {
                        System.out.print(" " + mapa[i][j].getSimbolo());
                    } else {
                        System.out.print(" " + camadaCaixas[i][j].getSimbolo());
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("   +");
        for (int j = 0; j < tamanho; j++) {
            System.out.print("--");
        }
        System.out.println("+");
        System.out.println();
    }

    // -----------------
    // JOGADOR
    // -----------------
    public Dinossauro moverJogador(int linha, int coluna) {
        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        int difLinha = Math.abs(linhaAtual - linha);
        int difColuna = Math.abs(colunaAtual - coluna);

        if ((difLinha + difColuna) > 1) {
            System.out.println("  Movimento Inválido! O jogador move apenas uma casa horizontal ou verticalmente");
            return null;
        }
        if (linha < 0 || coluna < 0 || linha >= tamanho || coluna >= tamanho) {
            System.out.println("  Posição fora do mapa!");
            return null;
        }
        if (mapa[linha][coluna] instanceof Parede) {
            System.out.println("  Tem uma parede ali! Caminho bloqueado");
            return null;
        }
        if (mapa[linha][coluna] instanceof Dinossauro) {
            return (Dinossauro) mapa[linha][coluna];
        }
        mapa[jogador.getLinha()][jogador.getColuna()] = null;
        jogador.setPosicao(linha, coluna);
        mapa[linha][coluna] = jogador;

        return null;
    }

    public void jogadorAvancaParaPosicaoDino(int linha, int coluna) {
        mapa[jogador.getLinha()][jogador.getColuna()] = null;
        mapa[linha][coluna] = jogador;
        jogador.setPosicao(linha, coluna);
    }

    public boolean fugir(int linha, int coluna) {
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

    // -----------------
    // DINOSSAURO
    // -----------------
    public List<Dinossauro> moverDinossauros() {
        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Dinossauro> atacantes = new ArrayList<>();

        for (Dinossauro dino : new ArrayList<>(dinossauros)) {
            if (!dino.seMove() || !dino.estaVivo()) {
                continue;
            }
            int casas = dino.getCasasDeMovimento();
            boolean encontrouJogador = false;

            for (int passos = 0; passos < casas && !encontrouJogador; passos++) {
                int[] ordem = embaralhar(new int[]{0, 1, 2, 3});
                boolean moveu = false;

                for (int indice : ordem) {
                    int novaLinha = dino.getLinha() + direcoes[indice][0];
                    int novaColuna = dino.getColuna() + direcoes[indice][1];

                    if (novaLinha < 0 || novaLinha >= tamanho
                            || novaColuna < 0 || novaColuna >= tamanho) {
                        continue;
                    }

                    Entidade destino = mapa[novaLinha][novaColuna];

                    if (destino == jogador) {
                        atacantes.add(dino);
                        encontrouJogador = true;
                        moveu = true;
                        break;
                    }

                    if (destino == null) {
                        mapa[dino.getLinha()][dino.getColuna()] = null;
                        dino.setPosicao(novaLinha, novaColuna);
                        mapa[novaLinha][novaColuna] = dino;
                        moveu = true;
                        break;
                    }
                }
                if (!moveu) {
                    break;
                }
            }
        }
        return atacantes;
    }

    public int[] embaralhar(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    public void removerDinossauro(Dinossauro dino) {
        if (mapa[dino.getLinha()][dino.getColuna()] == dino) {
            mapa[dino.getLinha()][dino.getColuna()] = null;
        }
        dinossauros.remove(dino);
    }

    public boolean todosDerrotados() {
        return dinossauros.isEmpty();
    }

    // -----------------
    // CAIXA
    // -----------------
    public CaixaDeSuprimentos getCaixaEm(int linha, int coluna) {
        return camadaCaixas[linha][coluna];
    }

    public void removerCaixa(int linha, int coluna) {
        camadaCaixas[linha][coluna] = null;
    }

    public List<int[]> posicoesParaFuga() {
        List<int[]> livres = new ArrayList<>();

        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] d : direcoes) {
            int novaLinha = linhaAtual + d[0];
            int novaColuna = colunaAtual + d[1];
            if (novaLinha < 0 || novaLinha >= tamanho
                    || novaColuna < 0 || novaColuna >= tamanho) {
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
