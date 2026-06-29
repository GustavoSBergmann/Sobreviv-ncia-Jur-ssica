package Caixas_E_Itens;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class LancaDardos extends Item {

    private int municao;

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
        return (municao > 0);
    }

    public int atirar() {
        if (municao <= 0) {
            return 0;
        }
        municao--;
        return 2;
    }
    
    @Override
    public void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor){
        jogador.coletarItem(this);
    }
}
