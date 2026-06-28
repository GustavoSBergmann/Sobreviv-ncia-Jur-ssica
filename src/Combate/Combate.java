package Combate;

import Caixas_E_Itens.LancaDardos;
import Dinossauros.Dinossauro;
import Dinossauros.TiranossauroRex;
import Dinossauros.Troodonte;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class Combate {

    private final Jogador jogador;
    private final Dinossauro dino;
    private final Mapa mapa;
    private final Scanner leitor;
    private final Random rand = new Random();

    public Combate(Jogador jogador, Dinossauro dino, Mapa mapa, Scanner leitor) {
        this.jogador = jogador;
        this.dino = dino;
        this.mapa = mapa;
        this.leitor = leitor;
    }
    
    public int rolarDados(int lados){
        return (rand.nextInt(lados) + 1);
    }
    
    public String nomeAtaqueCorpo(){
        return jogador.getInventario().possuiBastaoEletrico() ? "Bastão Elétrico" : "Mãos Nuas";
    }
}
