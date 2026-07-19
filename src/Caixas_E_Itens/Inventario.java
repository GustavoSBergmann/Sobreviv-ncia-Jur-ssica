package Caixas_E_Itens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Item> itens;

    public Inventario() {
        itens = new ArrayList<>();
    }

    public void adicionar(Item item) {
        itens.add(item);
    }

    public void remover(Item item) {
        itens.remove(item);
    }

    public List<Item> getItens() {
        return itens;
    }

    public boolean possuiKitMedico() {
        for (Item item : itens) {
            if (item instanceof KitMedico) {
                return true;
            }
        }
        return false;
    }

    public KitMedico getKitMedico() {
        for (Item item : itens) {
            if (item instanceof KitMedico) {
                return (KitMedico) item;
            }
        }
        return null;
    }

    public boolean possuiBastaoEletrico() {
        for (Item item : itens) {
            if (item instanceof BastaoEletrico) {
                return true;
            }
        }
        return false;
    }

    public BastaoEletrico getBastaoEletrico() {
        for (Item item : itens) {
            if (item instanceof BastaoEletrico) {
                return (BastaoEletrico) item;
            }
        }
        return null;
    }

    public boolean possuiLancaDardos() {
        for (Item item : itens) {
            if (item instanceof LancaDardos) {
                return true;
            }
        }
        return false;
    }

    public LancaDardos getLancaDardos() {
        for (Item item : itens) {
            if (item instanceof LancaDardos) {
                return (LancaDardos) item;
            }
        }
        return null;
    }
}
