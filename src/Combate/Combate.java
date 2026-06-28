package Combate;

import Caixas_E_Itens.LancaDardos;
import Dinossauros.Dinossauro;
import Dinossauros.TiranossauroRex;
import Dinossauros.Troodonte;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class Combate {

    private final Jogador jogador;
    private final Dinossauro dino;
    private final Mapa mapa;
    private final Scanner leitor;
    private final Random rand = new Random();

    public Combate(Jogador jogador, Dinossauro dino, Mapa mapa, Scanner leitor) {
        this.jogador = jogador;
        this.dino = dino;
        this.mapa = mapa;
        this.leitor = leitor;
    }

    public boolean executar(boolean dinoPrimeiroAtaque) {
        System.out.println();
        System.out.println("  -----------------------------------  ");
        System.out.println("             COMBATE INICIADO          ");
        System.out.println("  -----------------------------------  ");
        System.out.println("  Você encontrou um " + dino + "!");
        System.out.println();

        if (dinoPrimeiroAtaque) {
            System.out.println("  O " + dino.getClass().getSimpleName() + " ataca primeiro por ter te surpreendido");
            if (!turnoAtaqueDino()) {
                return false;
            }
            //if(!dino.estaVivo()) return true;
        }

        while (!jogador.estaVivo() && !dino.estaVivo()) {
            System.out.println("  ──────────────────────────────────────");
            System.out.println("  Seu estado: Saúde " + jogador.getSaude() + "/" + jogador.getSaudeMaxima());
            System.out.println("  " + dino.getClass().getSimpleName() + " estado: Saúde " + dino.getSaude() + "/" + dino.getSaudeMaxima());
            System.out.println();

            System.out.println("  O que você quer fazer?");
            System.out.println("  1 - Atacar com " + nomeAtaqueCorpo());

            boolean temDardos = jogador.getInventario().possuiLancaDardos();

            if (temDardos && dino.vulneravelDardos()) {
                System.out.println("  2 - Atacar com Lança-Dardos (munição "
                        + jogador.getInventario().getLancaDardos().getMunicao() + ")");
            } else if (temDardos && !dino.vulneravelDardos()) {
                System.out.println("  2 - Atacar com Lança-Dardos "
                        + "(ineficaz contra " + dino.getClass().getSimpleName() + ")");
            }

            System.out.println("  3 - Fugir");
            System.out.println("  Escolha: ");
            int opcao = lerOpcaoInt();

            if (opcao == 3) {
                if (tentarFugir()) {
                    System.out.println("  Você conseguiu fugir!");
                    return true;
                } else {
                    System.out.println("  Não há para onde fugir! Você está encurralado!");
                }
            }
            if (opcao == 2) {
                if (!temDardos) {
                    System.out.println(" Opção Inválida");
                    continue;
                }

                if (!dino.vulneravelDardos()) {
                    System.out.println("  O " + dino.getClass().getSimpleName()
                            + " é ágil demais! O dardo não acertou. Escolha outra opção.");
                    continue;
                }
                atacarComDardos();
            }
            if (opcao == 1) {
                atacarCorpoACorpo();
            }

            if (!dino.estaVivo()) {
                break;
            }

        }

        if (jogador.estaVivo()) {
            System.out.println();
            System.out.println("  Você derrotou o " + dino.getClass().getSimpleName() + "!");
            System.out.println();
            return true;
        }
        return false;
    }

    private void atacarCorpoACorpo() {
        boolean temBastao = jogador.getInventario().possuiBastaoEletrico();

        if (!dino.vulneravelMaosNuas() && !temBastao) {
            System.out.println("  Você não pode ferir o " + dino.getClass().getSimpleName()
                    + " sem uma arma! Seu ataque não causou dano.");
            return;
        }

        int dado = rolarDado(6);
        System.out.println("  " + (temBastao ? "[Bastão Elétrico]" : "[Mãos Nuas]")
                + " Dado: " + dado);

        int dano = 0;

        if (temBastao) {
            if (dado > 5) {
                dano = 2;
                System.out.println("  GOLPE CRÍTICO com o bastão! +" + dano + " de dano");
            } else if (dado == 1) {
                System.out.println("  Você errou o golpe com o bastão!");
            } else {
                dano = 1;
                System.out.println("  Golpe com o bastão: +" + dano + " de dano");
            }
        } else {
            if (dado == 6) {
                dano = 2;
                System.out.println("  GOLPE CRÍTICO! +" + dano + " de dano");
            } else if (dado < 3) {
                System.out.println("  Você errou o golpe!");
            } else {
                if (dino instanceof Troodonte) {
                    System.out.println("  Seu golpe não teve força suficiente contra o Troodonte. "
                            + "Apenas críticos funcionam!");
                } else {
                    dano = 1;
                    System.out.println("  Golpe: " + dano + " de dano.");
                }
            }
        }

        if (dano > 0) {
            dino.receberDano(dano);
        }
    }

    private void atacarComDardos() {
        LancaDardos lancaDardos = jogador.getInventario().getLancaDardos();
        int dano = lancaDardos.atirar();
        System.out.println("  [Lança-Dardos] Disparo! Sempre crítico: " + dano + " de dano.");
        System.out.println("  Munição restante: " + lancaDardos.getMunicao());
        if (dano > 0) {
            dino.receberDano(dano);
        }
    }

    private boolean turnoAtaqueDino() {
        int dado3 = rolarDado(3);
        System.out.println("  O " + dino.getClass().getSimpleName()
                + " ataca! Teste de esquiva (dado 3 lados): " + dado3
                + " vs percepção " + jogador.getPercepcao());

        if (dado3 <= jogador.getPercepcao()) {
            System.out.println("  Você desviou do ataque!");
        } else {
            int dano = dino.getDanoAtaque();
            jogador.receberDano(dano);

            System.out.println("  Você levou " + dano + " de dano! Saúde: "
                    + jogador.getSaude() + "/" + jogador.getSaudeMaxima());

            if (!jogador.estaVivo()) {
                System.out.println("  Você foi derrotado pelo " + dino.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    private boolean tentarFugir() {
        List<int[]> opcoes = mapa.posicoesParaFuga();

        if (opcoes.isEmpty()) {
            return false;
        }
        System.out.println("  Para onde você quer fugir? ");
        for (int i = 0; i < opcoes.size(); i++) {
            int[] posicao = opcoes.get(i);
            System.out.println(" " + (i + 1) + " - " + mapa.linhaParaLetra(posicao[0]) + (posicao[1] + 1));
        }

        System.out.println("  Escolha");
        int escolha = leitor.nextInt();
        if (escolha < 1 || escolha > opcoes.size()) {
            System.out.println("  Opção Inválida!");
            return false;
        }

        int[] destino = opcoes.get(escolha - 1);

        return mapa.fugir(destino[0], destino[1]);
    }

    private int rolarDado(int lados) {
        return (rand.nextInt(lados) + 1);
    }

    private String nomeAtaqueCorpo() {
        return jogador.getInventario().possuiBastaoEletrico() ? "Bastão Elétrico" : "Mãos Nuas";
    }
    
    private int lerOpcaoInt() {
        try {
            String linha = leitor.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (Exception e) {
            return -1;
        }
    }

}
