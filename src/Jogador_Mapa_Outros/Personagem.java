package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
public abstract class Personagem extends Entidade {

    private int saude;

    public Personagem(int linha, int coluna, int saude) {
        super(linha, coluna);
        this.saude = saude;
    }

    public void recuperaSaude() {
        saude = 5;
    }

}
