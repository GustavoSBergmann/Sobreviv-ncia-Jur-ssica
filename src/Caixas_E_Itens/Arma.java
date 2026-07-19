package Caixas_E_Itens;

import java.util.Random;

public abstract class Arma extends Item {

    private static final long serialVersionUID = 1L;

    public Arma(String nome) {
        super(nome);
    }

    public abstract int calcularDano(Random rand);

    public abstract String getUltimaMensagem();
}
