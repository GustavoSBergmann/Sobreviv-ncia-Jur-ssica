package Caixas_E_Itens;
import Jogador_Mapa_Outros.Entidade;

/**
 *
 * @author Cliente
 */
public class CaixaDeSuprimentos extends Entidade{
    private ConteudoCaixa conteudo;
    
    public CaixaDeSuprimentos(int linha, int coluna){
        super(linha, coluna);
    }
    
    @Override
    public char getSimbolo() {
        return 'X';
    }
}
