package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

/**
 * Superclasse de todo objeto que pode ser coletado pelo jogador e
 * guardado em seu inventário. É também um {@link ConteudoCaixa}, pois
 * todo item pode estar dentro de uma caixa de suprimentos.
 *
 * @author Cliente
 */
public abstract class Item implements ConteudoCaixa {

    private static final long serialVersionUID = 1L;

    private String nome;

    public Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public abstract ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa);

    @Override
    public String toString() {
        return nome;
    }
}
