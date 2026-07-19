package Caixas_E_Itens;

import Jogador_Mapa_Outros.Entidade;

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
