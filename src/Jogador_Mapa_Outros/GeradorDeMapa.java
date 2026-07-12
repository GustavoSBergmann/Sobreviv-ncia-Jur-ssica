package Jogador_Mapa_Outros;

import Caixas_E_Itens.BastaoEletrico;
import Caixas_E_Itens.CaixaDeSuprimentos;
import Caixas_E_Itens.ConteudoCaixa;
import Caixas_E_Itens.KitMedico;
import Caixas_E_Itens.LancaDardos;
import Dinossauros.Compsognato;
import Dinossauros.Dinossauro;
import Dinossauros.TiranossauroRex;
import Dinossauros.Troodonte;
import Dinossauros.Velociraptor;

import java.util.Random;

/**
 * Responsável apenas por POVOAR um {@link Mapa} recém-criado: posiciona
 * o jogador, os dinossauros, as paredes e as caixas de suprimentos.
 * Depois de gerado, todas as consultas e movimentações do jogo em si são
 * feitas diretamente pela classe {@link Mapa} — esta classe só existe
 * para a etapa única de "sortear o cenário inicial".
 *
 * @author Cliente
 */
public class GeradorDeMapa {

    private final Random rand = new Random();

    /** Gera um novo tabuleiro aleatório para a partida, com o jogador na posição (0,0). */
    public void gerar(Mapa mapa, Jogador jogador) {
        mapa.limpar();

        jogador.setPosicao(0, 0);
        jogador.setSaude(jogador.getSaudeMaxima());
        mapa.colocarEntidade(0, 0, jogador);

        // Tiranossauro Rex na extremidade oposta ao jogador
        int tamanho = mapa.getTamanho();
        TiranossauroRex trex = new TiranossauroRex(tamanho - 1, tamanho - 1);
        mapa.colocarEntidade(tamanho - 1, tamanho - 1, trex);
        mapa.adicionarDinossauro(trex);

        gerarParedes(mapa);

        // 2 Compsognatos, 2 Velociraptors, 5 Troodontes
        posicionarDinossauro(mapa, new Compsognato(-1, -1));
        posicionarDinossauro(mapa, new Compsognato(-1, -1));
        posicionarDinossauro(mapa, new Velociraptor(-1, -1));
        posicionarDinossauro(mapa, new Velociraptor(-1, -1));
        posicionarDinossauro(mapa, new Troodonte(-1, -1));
        posicionarDinossauro(mapa, new Troodonte(-1, -1));
        posicionarDinossauro(mapa, new Troodonte(-1, -1));
        posicionarDinossauro(mapa, new Troodonte(-1, -1));
        posicionarDinossauro(mapa, new Troodonte(-1, -1));

        // 4 Caixas de suprimentos
        posicionarCaixa(mapa, new KitMedico());
        posicionarCaixa(mapa, new BastaoEletrico());
        posicionarCaixa(mapa, new LancaDardos());
        posicionarCaixa(mapa, new LancaDardos());
        posicionarCaixa(mapa, new Compsognato(-1, -1));
    }

    private void gerarParedes(Mapa mapa) {
        int tamanho = mapa.getTamanho();
        int total = tamanho * tamanho;
        int numParedes = (int) (total * 0.20);
        int colocadas = 0;
        for (int tentativa = 0; tentativa < numParedes * 4 && colocadas < numParedes; tentativa++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);
            if (mapa.celulaLivre(lin, col)) {
                mapa.colocarEntidade(lin, col, new Parede());
                colocadas++;
            }
        }
    }

    private void posicionarDinossauro(Mapa mapa, Dinossauro dino) {
        int tamanho = mapa.getTamanho();

        for (int tentativa = 0; tentativa < tamanho * tamanho; tentativa++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);
            if (mapa.celulaLivre(lin, col) && (lin + col) > 2) {
                dino.setPosicao(lin, col);
                mapa.colocarEntidade(lin, col, dino);
                mapa.adicionarDinossauro(dino);
                return;
            }
        }
        // Fallback determinístico, caso o sorteio aleatório não encontre espaço.
        for (int lin = 0; lin < tamanho; lin++) {
            for (int col = 0; col < tamanho; col++) {
                if (mapa.celulaLivre(lin, col) && (lin + col) > 1) {
                    dino.setPosicao(lin, col);
                    mapa.colocarEntidade(lin, col, dino);
                    mapa.adicionarDinossauro(dino);
                    return;
                }
            }
        }
    }

    private void posicionarCaixa(Mapa mapa, ConteudoCaixa conteudo) {
        int tamanho = mapa.getTamanho();
        for (int tentativa = 0; tentativa < tamanho * tamanho; tentativa++) {
            int lin = rand.nextInt(tamanho);
            int col = rand.nextInt(tamanho);
            if (!(lin == 0 && col == 0) && mapa.podeColocarCaixa(lin, col)) {
                if(conteudo instanceof Compsognato){
                    ((Compsognato) conteudo).setPosicao(lin, col);
                }
                mapa.colocarCaixa(lin, col, new CaixaDeSuprimentos(conteudo));
                return;
            }
        }
    }
}
