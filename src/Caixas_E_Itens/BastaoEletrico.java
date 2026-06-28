package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class BastaoEletrico extends Item {

    public BastaoEletrico() {
        super("Bastão Elétrico");
    }

    @Override
    public void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor) {
        jogador.coletarItem(this);
        System.out.println("  Bastão Elétrico Equipado");
    }

    public void usar(Jogador jogador) {
        //Só sout
    }
}
