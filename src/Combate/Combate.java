package Combate;

import Caixas_E_Itens.BastaoEletrico;
import Caixas_E_Itens.LancaDardos;
import Dinossauros.Dinossauro;
import Dinossauros.Troodonte;
import Exceptions.SemMunicaoException;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.util.Random;

public class Combate {

    private final Jogador jogador;
    private final Dinossauro dino;
    private final Mapa mapa;
    private final Random rand = new Random();

    public Combate(Jogador jogador, Dinossauro dino, Mapa mapa) {
        this.jogador = jogador;
        this.dino = dino;
        this.mapa = mapa;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Dinossauro getDino() {
        return dino;
    }

    public Mapa getMapa() {
        return mapa;
    }

    /**
     * Ataque corpo a corpo — com o Bastão Elétrico, se equipado, ou com
     * as mãos nuas caso contrário.
     */
    public String atacarComMaosOuBastao() {
        boolean temBastao = jogador.getInventario().possuiBastaoEletrico();

        if (!dino.vulneravelMaosNuas() && !temBastao) {
            return "Você não pode ferir o " + dino.getClass().getSimpleName()
                    + " sem uma arma! Seu ataque não causou dano.";
        }

        int dano;
        String msg;

        if (temBastao) {
            BastaoEletrico bastao = jogador.getInventario().getBastaoEletrico();
            dano = bastao.calcularDano(rand);
            msg = bastao.getUltimaMensagem();
        } else {
            int dado = rand.nextInt(6) + 1;
            if (dado == 6) {
                dano = 2;
                msg = "[Mãos Nuas] Dado: " + dado + " — GOLPE CRÍTICO! (+2 de dano)";
            } else if (dado < 3) {
                dano = 0;
                msg = "[Mãos Nuas] Dado: " + dado + " — Você errou o golpe!";
            } else if (dino instanceof Troodonte) {
                dano = 0;
                msg = "[Mãos Nuas] Dado: " + dado
                        + " — Sem força suficiente contra o Troodonte (só críticos causam dano)!";
            } else {
                dano = 1;
                msg = "[Mãos Nuas] Dado: " + dado + " — Acertou! (+1 de dano)";
            }
        }

        if (dano > 0) {
            dino.receberDano(dano);
        }
        return msg;
    }

    /**
     * Ataque com o Lança-Dardos. Sempre crítico, mas gasta uma munição.
     *
     * @throws SemMunicaoException caso o jogador não possua a arma ou
     * esteja sem munição.
     */
    public String atacarComDardos() throws SemMunicaoException {
        if (!jogador.getInventario().possuiLancaDardos()) {
            throw new SemMunicaoException("Você não possui um Lança-Dardos.");
        }
        LancaDardos lanca = jogador.getInventario().getLancaDardos();
        if (!lanca.temMunicao()) {
            throw new SemMunicaoException("Sem munição no Lança-Dardos!");
        }
        if (!dino.vulneravelDardos()) {
            return "O " + dino.getClass().getSimpleName()
                    + " é ágil/resistente demais! O dardo não faz efeito contra ele.";
        }
        int dano = lanca.calcularDano(rand);
        if (dano > 0) {
            dino.receberDano(dano);
        }
        return lanca.getUltimaMensagem();
    }

    /** Executa o contra-ataque do dinossauro, incluindo o teste de esquiva do jogador. */
    public String turnoDinoContraAtaca() {
        int dado3 = rand.nextInt(3) + 1;
        StringBuilder sb = new StringBuilder();
        sb.append("O ").append(dino.getClass().getSimpleName())
                .append(" ataca! Teste de esquiva (dado 3 lados): ").append(dado3)
                .append(" vs percepção ").append(jogador.getPercepcao()).append(". ");

        if (dado3 <= jogador.getPercepcao()) {
            sb.append("Você desviou do ataque!");
        } else {
            int dano = dino.getDanoAtaque();
            jogador.receberDano(dano);
            sb.append("Você levou ").append(dano).append(" de dano! Saúde: ")
                    .append(jogador.getSaude()).append("/").append(jogador.getSaudeMaxima());
        }
        return sb.toString();
    }

    /** Tenta fugir para uma posição adjacente livre. */
    public boolean tentarFugir(int linha, int coluna) {
        return mapa.fugir(linha, coluna);
    }
}
