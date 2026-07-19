package Caixas_E_Itens;

import java.io.Serializable;

public class ResultadoCaixa implements Serializable {

    private final String mensagem;
    private final Object surpresa;

    public ResultadoCaixa(String mensagem, Object surpresa) {
        this.mensagem = mensagem;
        this.surpresa = surpresa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Object getSurpresa() {
        return surpresa;
    }

    public boolean temSurpresa() {
        return surpresa != null;
    }
}
