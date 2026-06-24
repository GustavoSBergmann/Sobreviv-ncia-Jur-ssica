package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
public class Jogador extends Personagem {

    private int percepcao;
    private Inventario inventario;

    public Jogador(int linha, int coluna, int percepcao) {
        super(linha, coluna, 5);
        this.percepcao = percepcao;
        inventario = new Inventario();
    }

    public int getPercepcao() {
        return percepcao;
    }

    @Override
    public char getSimbolo() {
        return 'J';
    }

}
