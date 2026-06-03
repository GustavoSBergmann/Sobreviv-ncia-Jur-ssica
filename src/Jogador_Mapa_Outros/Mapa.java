package Jogador_Mapa_Outros;

/**
 *
 * @author Cliente
 */
public class Mapa {
    private Entidade[][] mapa;
    
    public Mapa(int tamanhoDoMapa){
        mapa = new Entidade[tamanhoDoMapa][tamanhoDoMapa];
    }
}
