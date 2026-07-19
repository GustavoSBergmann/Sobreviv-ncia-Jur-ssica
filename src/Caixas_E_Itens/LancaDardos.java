package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.util.Random;

public class LancaDardos extends Arma {

    private static final long serialVersionUID = 1L;

    private int municao;
    private String ultimaMensagem = "";

    public LancaDardos() {
        super("Lança-Dardos");
        this.municao = 1;
    }

    public int getMunicao() {
        return municao;
    }

    public void adicionarMunicao(int quantidade) {
        this.municao += quantidade;
    }

    public boolean temMunicao() {
        return municao > 0;
    }

    @Override
    public int calcularDano(Random rand) {
        if (municao <= 0) {
            ultimaMensagem = "[Lança-Dardos] Sem munição!";
            return 0;
        }
        municao--;
        ultimaMensagem = "[Lança-Dardos] Disparo certeiro — sempre crítico! (+2 de dano). "
                + "Munição restante: " + municao;
        return 2;
    }

    @Override
    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    @Override
    public ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa) {
        String msg = jogador.coletarItem(this);
        return new ResultadoCaixa(msg, null);
    }
}
