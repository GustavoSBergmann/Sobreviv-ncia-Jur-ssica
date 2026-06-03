package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
// Qualquer coisa que esteja presente no mapa
public abstract class Entidade {
    private Posicao posicao;
    
    public Entidade(int linha, int coluna){
        posicao = new Posicao(linha, coluna);
    }
}
