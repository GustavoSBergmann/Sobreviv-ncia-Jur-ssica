package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.util.Random;

public class BastaoEletrico extends Arma {

    private static final long serialVersionUID = 1L;

    private String ultimaMensagem = "";

    public BastaoEletrico() {
        super("Bastão Elétrico");
    }

    @Override
    public int calcularDano(Random rand) {
        int dado = rand.nextInt(6) + 1;
        int dano;

        if (dado > 5) {
            dano = 2;
            ultimaMensagem = "[Bastão Elétrico] Dado: " + dado + " — GOLPE CRÍTICO! (+2 de dano)";
        } else if (dado == 1) {
            dano = 0;
            ultimaMensagem = "[Bastão Elétrico] Dado: " + dado + " — Você errou o golpe!";
        } else {
            dano = 1;
            ultimaMensagem = "[Bastão Elétrico] Dado: " + dado + " — Acertou! (+1 de dano)";
        }
        return dano;
    }

    @Override
    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    @Override
    public ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa) {
        String msg = jogador.coletarItem(this);
        return new ResultadoCaixa(msg + "\nO Bastão Elétrico substitui o ataque com as mãos nuas.", null);
    }
}
