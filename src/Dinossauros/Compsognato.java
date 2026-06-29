package Dinossauros;

import Caixas_E_Itens.ConteudoCaixa;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import Combate.Combate;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Cliente
 */
public class Compsognato extends Dinossauro implements ConteudoCaixa {

    public Compsognato(int linha, int coluna) {
        super(linha, coluna, 1);
    }

    public int getDanoAtaque() {
        return 1;
    }

    public int getCasasDeMovimento() {
        return 1;
    }

    public boolean vulneravelMaosNuas() {
        return true;
    }

    public boolean vulneravelDardos() {
        return true;
    }

    public boolean seMove() {
        return false;
    }
        
    @Override
    public void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor) {
        System.out.println("  SURPRESA! Um Compsognato estava escondido na caixa!");

        int dado = new Random().nextInt(3) + 1;
        System.out.println("  Teste de percepção: dado " + dado + " vs percepção " + jogador.getPercepcao());
        if (dado <= jogador.getPercepcao()) {
            System.out.println("  Você percebeu o Compsognato a tempo e se esquivou!");
        } else {
            jogador.receberDano(1);
            System.out.println("  O Compsognato te mordeu! Saúde: " + jogador.getSaude());
        }

        new Combate(jogador, this, mapa, leitor).executar(false);
    }
    
    @Override
    public char getCaractere(){
        return 'C';
    }
    
    @Override
    public String toString(){
        return "Compsognato (saúde: " + getSaude() + ")";
    }
}
