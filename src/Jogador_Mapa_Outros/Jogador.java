package Jogador_Mapa_Outros;

import Caixas_E_Itens.Inventario;
import Caixas_E_Itens.Item;
import Caixas_E_Itens.KitMedico;
import Caixas_E_Itens.LancaDardos;

public class Jogador extends Personagem {

    private static final long serialVersionUID = 1L;

    private int percepcao;
    private Inventario inventario;

    public Jogador(int percepcao) {
        super(0, 0, 5);
        this.percepcao = percepcao;
        inventario = new Inventario();
    }

    public int getPercepcao() {
        return percepcao;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public String getStatusString() {
        return "Saúde: " + getSaude() + "/" + getSaudeMaxima()
                + "   |   Percepção: " + getPercepcao();
    }

    public String coletarItem(Item item) {
        if (item instanceof LancaDardos && inventario.possuiLancaDardos()) {
            inventario.getLancaDardos().adicionarMunicao(1);
            return "Munição adicionada ao Lança-Dardos! Total: "
                    + inventario.getLancaDardos().getMunicao();
        } else {
            inventario.adicionar(item);
            return "Item coletado: " + item.getNome();
        }
    }

    public String usarKitMedico() {
        KitMedico kit = inventario.getKitMedico();
        if (kit != null) {
            String msg = kit.usar(this);
            inventario.remover(kit);
            return msg;
        }
        return "Você não possui Kit Médico.";
    }

    @Override
    public char getCaractere() {
        return 'J';
    }
}
