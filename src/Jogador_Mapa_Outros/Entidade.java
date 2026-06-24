package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
// Qualquer coisa que esteja presente no mapa
public abstract class Entidade {

    private int linha;
    private int coluna;

    public Entidade(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public abstract char getSimbolo();
}
