package Jogador_Mapa_Outros;

public abstract class Personagem extends Entidade {

    private static final long serialVersionUID = 1L;

    private int saude, saudeMaxima;
    private int linha, coluna;

    public Personagem(int linha, int coluna, int saude) {
        this.linha = linha;
        this.coluna = coluna;
        this.saude = saude;
        this.saudeMaxima = saude;
    }

    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public int getSaude() {
        return saude;
    }

    public int getSaudeMaxima() {
        return saudeMaxima;
    }

    public void setSaude(int saude) {
        this.saude = saude;
    }

    public void recuperaSaude() {
        saude = saudeMaxima;
    }

    public void receberDano(int dano) {
        saude -= dano;
        if (saude < 0) {
            saude = 0;
        }
    }

    public boolean estaVivo() {
        return saude > 0;
    }
}
