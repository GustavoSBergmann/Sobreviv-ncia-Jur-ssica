package Caixas_E_Itens;

import java.io.Serializable;

/**
 * Resultado da abertura de uma caixa de suprimentos: a mensagem que deve
 * ser mostrada ao jogador na interface gráfica e, opcionalmente, um
 * objeto "surpresa" (por exemplo, um Compsognato escondido) que deve
 * disparar um combate imediatamente.
 *
 * @author Cliente
 */
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
