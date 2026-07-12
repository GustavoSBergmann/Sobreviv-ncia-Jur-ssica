package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.io.Serializable;

/**
 * Define "o que faz" algo que pode estar dentro de uma caixa de
 * suprimentos ao ser encontrado pelo jogador (um item, ou até mesmo um
 * dinossauro surpresa, como o Compsognato).
 *
 * @author Cliente
 */
public interface ConteudoCaixa extends Serializable {

    /**
     * Executado quando o jogador encontra este conteúdo. Retorna um
     * {@link ResultadoCaixa} com a mensagem a ser exibida na interface e,
     * caso aplicável, a "surpresa" (dinossauro) que deve iniciar um
     * combate.
     */
    ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa);
}
