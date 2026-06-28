package Jogador_Mapa_Outros;

import Caixas_E_Itens.*;
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
    
    public Inventario getInventario(){
        return inventario;
    }
    
    public String getStatusString(){
        return "\n  Saúde: " + getSaude() + "/" + getSaudeMaxima() + 
               "\n  Percepção: " + getPercepcao() + "\n";
    }
    
    public void coletarItem(Item item){
        if(item instanceof LancaDardos && inventario.possuiLancaDardos()){
            inventario.getLancaDardos().adicionarMunicao(1);
            System.out.println("  Munição adicionada ao Lança-Dardos! Total: " + 
                    inventario.getLancaDardos().getMunicao());
        } else{
            inventario.adicionar(item);
            System.out.println("  Item coletado: " + item.getNome());
        }
    }
    
    public void usarKitMedico(){
        KitMedico kit = inventario.getKitMedico();
        
        if(kit != null){
            kit.usar(this);
            inventario.remover(kit);
        } else{
            System.out.println("  Você não possui Kit Médico.");
        }
    }

    @Override
    public char getSimbolo() {
        return 'J';
    }

}
