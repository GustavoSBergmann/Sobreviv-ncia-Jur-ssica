package Jogador_Mapa_Outros;

import Dinossauros.*;
import Caixas_E_Itens.*;

import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class SobrevivenciaJurassica {

    private static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {

        exibirBoasVindas();

        boolean continuar = true;
        while (continuar) {
            int opcao = menuPrincipal();
            if (opcao == 1) {
                int percepcao = menuDificuldade();
                jogar(percepcao);
            } else {
                System.out.println("\n  Até a próxima! Sobreviva sempre... 🦕");
                continuar = false;
            }
        }
        leitor.close();

    }

    //  MENUS
    private static void exibirBoasVindas() {
        limparTela();

        System.out.println("  ====================================================  ");
        System.out.println("                 SOBREVIVÊNCIA JURÁSSICA                ");
        System.out.println("                                                        ");
        System.out.println("     As instalações do parque foram invadidas por       ");
        System.out.println("     dinossauros! Colete armas, derrote os inimigos     ");
        System.out.println("     e salve sua pele antes que seja tarde demais!      ");
        System.out.println("  ====================================================  ");
        System.out.println();
    }

    private static int menuPrincipal() {
        System.out.println("  ---------------------------  ");
        System.out.println("          MENU PRINCIPAL       ");
        System.out.println("  ---------------------------  ");
        System.out.println("     1 - Jogar                 ");
        System.out.println("     2 - Sair                  ");
        System.out.println("  ---------------------------  ");
        System.out.print("  Escolha: ");
        return leitor.nextInt();
    }

    private static int menuDificuldade() {
        System.out.println();
        System.out.println("  ----------------------------------------------------  ");
        System.out.println("                 ESCOLHA A DIFICULDADE                  ");
        System.out.println("  ----------------------------------------------------  ");
        System.out.println("     1 - Fácil   (Percepção 3 — esquiva mais fácil)    ");
        System.out.println("     2 - Médio   (Percepção 2)                          ");
        System.out.println("     3 - Difícil (Percepção 1 — esquiva difícil)       ");
        System.out.println("  ----------------------------------------------------  ");
        System.out.print("  Escolha: ");
        int escolha = leitor.nextInt();
        int percepcao;
        switch (escolha) {
            case 1:
                percepcao = 3;
                break;
            case 3:
                percepcao = 1;
                break;
            default:
                percepcao = 2;
                break;
        }
        return percepcao;
    }

    public static void limparTela() {
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //  LOOP PRINCIPAL DO JOGO
    private static void jogar(int percepcao) {
        Mapa mapa = new Mapa(10);
        Jogador jogador = new Jogador(0, 0, percepcao);
        mapa.setJogador(jogador);
        mapa.gerarMapaAleatorio();

        boolean jogando = true;

        while (jogando) {
            mapa.imprimirMapa();
            System.out.println("  " + jogador.getStatusString());
            System.out.println();

            int opcaoMenu = menuJogo(jogador);

            switch (opcaoMenu) {
                case 1:
                    processarMovimento(jogador, mapa);
                    if (!jogador.estaVivo()) {
                        break;
                    }
                    break;

                case 2:
                    if (jogador.getInventario().possuiKitMedico()) {
                        jogador.usarKitMedico();
                    } else {
                        System.out.println("  Você não possui Kit Médico.");
                    }
                    break;

                case 3:
                    break;

                case 4:
                    jogando = false;
                    break;

                default:
                    break;
            }
        }
        mapa.imprimirMapa();
    }

    private static void processarMovimento(Jogador jogador, Mapa mapa) {
        System.out.println();
        System.out.println("  Destino (ex: B3 ou b3). Posições adjacentes válidas:");
        exibirPossiveisMovimentos(jogador, mapa);
        System.out.println("  Destino: ");
        String entrada = leitor.nextLine();

        if (entrada.length() < 2) {
            System.out.println("  Entrada inválida. Use o formato LetraNumero (ex: B3).");
            return;
        }

        char letra = entrada.charAt(0);
        String numStr = entrada.substring(1);
        int num = Integer.parseInt(numStr);

        int novaLinha = mapa.letraParaLinha(letra);
        int novaColuna = mapa.numeroParaColuna(num);

        if (novaLinha < 0 || novaLinha >= mapa.getTamanho()
                || novaColuna < 0 || novaColuna >= mapa.getTamanho()) {
            System.out.println("  Posição fora do mapa!");
            return;
        }

        Dinossauro dinoEncontrado = mapa.moverJogador(novaLinha, novaColuna);

        if (dinoEncontrado != null) {
            boolean venceu = true; // = new Combate();
            if (!venceu) {
                return;
            }

            mapa.removerDinossauro(dinoEncontrado);
            mapa.jogadorAvancaParaPosicaoDino(novaLinha, novaColuna);
        }

        processarCaixa(jogador, mapa, jogador.getLinha(), jogador.getColuna());
    }

    public static void processarCaixa(Jogador jogador, Mapa mapa, int linha, int coluna) {
        CaixaDeSuprimentos caixa = mapa.getCaixaEm(linha, coluna);
        if (caixa == null || caixa.foiAberta()) {
            return;
        }
        System.out.println("\n  Você encontrou uma caixa de suprimentos");
        ConteudoCaixa conteudo = caixa.getConteudo();
        caixa.abrir();
        mapa.removerCaixa(linha, coluna);

        conteudo.aoSerEncontrado(jogador, mapa, leitor);
    }

    public static void exibirPossiveisMovimentos(Jogador jogador, Mapa mapa) {
        int linhaAtual = jogador.getLinha();
        int colunaAtual = jogador.getColuna();

        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        System.out.print("  ");
        for (int[] d : direcoes) {
            int possLinha = linhaAtual + d[0];
            int possColuna = colunaAtual + d[1];

            if (possLinha >= 0 && possLinha < mapa.getTamanho()
                    && possColuna >= 0 && possColuna < mapa.getTamanho()) {
                System.out.println(mapa.linhaParaLetra(possLinha) + (possColuna + 1) + "   ");
            }
        }
        System.out.println();
    }

    private static int menuJogo(Jogador jogador) {
        System.out.println("  ------------------------------  ");
        System.out.println("              AÇÕES               ");
        System.out.println("  ------------------------------  ");
        System.out.println("     1 - Movimentar               ");
        System.out.println("     2 - Usar Kit Médico"
                + (jogador.getInventario().possuiKitMedico() ? " " : " (sem kit)"));
        System.out.println("     3 - DEBUG (revelar mapa)     ");
        System.out.println("     4 - Sair do jogo             ");
        System.out.println("  ------------------------------  ");
        System.out.print("  Escolha: ");
        return leitor.nextInt();
    }
}
