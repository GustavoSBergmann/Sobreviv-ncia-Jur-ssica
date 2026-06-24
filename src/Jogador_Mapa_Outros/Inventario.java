package Jogador_Mapa_Outros;

import Caixas_E_Itens.Item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class Inventario {
    
    private List<Item> itens;

    public Inventario() {
        itens = new ArrayList<>();
    }

    public void adicionar(Item item) {
        itens.add(item);
    }
    
    public void remover(Item item){
        itens.remove(item);
    }
}
