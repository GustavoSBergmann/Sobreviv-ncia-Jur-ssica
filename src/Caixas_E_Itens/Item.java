package Caixas_E_Itens;

import Jogador_Mapa_Outros.*;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public abstract class Item implements ConteudoCaixa {

    private String nome;

    public Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public abstract void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor);

    @Override
    public String toString() {
        return nome;
    }
}
