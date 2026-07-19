package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.io.Serializable;

public interface ConteudoCaixa extends Serializable {

    ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa);
}
