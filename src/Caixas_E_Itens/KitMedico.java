package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

/**
 * Item de cura: restaura totalmente a saúde do jogador quando usado.
 *
 * @author Cliente
 */
public class KitMedico extends Item {

    private static final long serialVersionUID = 1L;

    public KitMedico() {
        super("Kit Médico");
    }

    /**
     * Usa o kit, restaurando a saúde do jogador ao máximo.
     *
     * @return mensagem descritiva do efeito.
     */
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
