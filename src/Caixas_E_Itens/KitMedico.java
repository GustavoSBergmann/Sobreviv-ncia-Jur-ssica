package Caixas_E_Itens;
import Jogador_Mapa_Outros.*;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class KitMedico extends Item {

    public KitMedico() {
        super("Kit Médico");
    }
    
    public void usar(Jogador jogador){
        jogador.recuperaSaude();
    }
    
    @Override
    public void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor){
        jogador.coletarItem(this);
    }
}

