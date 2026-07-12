package Caixas_E_Itens;

import Jogador_Mapa_Outros.Entidade;

/**
 * Caixa espalhada pelo mapa que pode conter um item (Kit Médico, Bastão
 * Elétrico, Lança-Dardos) ou um dinossauro surpresa (Compsognato).
 *
 * @author Cliente
 */
public class CaixaDeSuprimentos extends Entidade {

    private static final long serialVersionUID = 1L;

    private ConteudoCaixa conteudo;

    public CaixaDeSuprimentos(ConteudoCaixa conteudo) {
        this.conteudo = conteudo;
    }

    public ConteudoCaixa getConteudo() {
        return conteudo;
    }

    public boolean foiAberta() {
        return (conteudo == null);
    }

    public void abrir() {
        conteudo = null;
    }

    @Override
    public char getCaractere() {
        return 'X';
    }
}
