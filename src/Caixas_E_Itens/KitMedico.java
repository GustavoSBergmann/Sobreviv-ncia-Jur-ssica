package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

public class KitMedico extends Item {

    private static final long serialVersionUID = 1L;

    public KitMedico() {
        super("Kit Médico");
    }

    public String usar(Jogador jogador) {
        jogador.recuperaSaude();
        return "[Kit Médico] Saúde totalmente restaurada para " + jogador.getSaudeMaxima() + "!";
    }

    @Override
    public ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa) {
        String msg = jogador.coletarItem(this);
        return new ResultadoCaixa(msg, null);
    }
}
