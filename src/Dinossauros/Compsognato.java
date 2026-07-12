package Dinossauros;

import Caixas_E_Itens.ConteudoCaixa;
import Caixas_E_Itens.ResultadoCaixa;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.util.Random;

/**
 * O tipo mais simples de inimigo. Não se move pelo mapa, mas pode aparecer
 * escondido dentro de uma caixa de suprimentos, pegando o jogador de surpresa.
 *
 * @author Cliente
 */
public class Compsognato extends Dinossauro implements ConteudoCaixa {

    private static final long serialVersionUID = 1L;

    public Compsognato(int linha, int coluna) {
        super(linha, coluna, 1);
    }

    @Override
    public int getDanoAtaque() {
        return 1;
    }

    @Override
    public int getCasasDeMovimento() {
        return 1;
    }

    @Override
    public boolean vulneravelMaosNuas() {
        return true;
    }

    @Override
    public boolean vulneravelDardos() {
        return true;
    }

    @Override
    public boolean mover(Mapa mapa) {
        // Movimento aleatório e rápido: a cada passo sorteia a ordem das
        // direções e pede ao Mapa para executar apenas o passo escolhido.
        for (int passo = 0; passo < getCasasDeMovimento(); passo++) {
            boolean moveu = false;
            for (int[] direcao : direcoesEmbaralhadas()) {
                Mapa.ResultadoPasso resultado = mapa.tentarMoverEntidade(
                        this, getLinha() + direcao[0], getColuna() + direcao[1]);
                if (resultado == Mapa.ResultadoPasso.ENCONTROU_JOGADOR) {
                    return true; // pode parar na primeira casa, se encontrar o jogador
                }
                if (resultado == Mapa.ResultadoPasso.MOVEU) {
                    moveu = true;
                    break;
                }
            }
            if (!moveu) {
                break;
            }
        }
        return false;
    }

    @Override
    public ResultadoCaixa aoSerEncontrado(Jogador jogador, Mapa mapa) {
        StringBuilder sb = new StringBuilder();
        sb.append("SURPRESA! Um Compsognato estava escondido na caixa!\n");

        int dado = new Random().nextInt(3) + 1;
        sb.append("Teste de percepção: dado ").append(dado)
                .append(" vs percepção ").append(jogador.getPercepcao()).append(".\n");

        if (dado <= jogador.getPercepcao()) {
            sb.append("Você percebeu o Compsognato a tempo e se esquivou!");
        } else {
            jogador.receberDano(1);
            sb.append("O Compsognato te mordeu! Saúde: ").append(jogador.getSaude());
        }

        return new ResultadoCaixa(sb.toString(), this);
    }

    @Override
    public char getCaractere() {
        return 'C';
    }

    @Override
    public String toString() {
        return "Compsognato (saúde: " + getSaude() + ")";
    }
}
