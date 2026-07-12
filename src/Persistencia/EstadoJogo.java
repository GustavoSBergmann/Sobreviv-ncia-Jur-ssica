package Persistencia;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.io.*;
import java.io.Serializable;

/**
 * Representa um "retrato" completo de uma partida em andamento (mapa,
 * jogador e dificuldade), usado tanto para salvar/carregar em arquivo
 * quanto para implementar a opção "Reiniciar Jogo" (que precisa voltar
 * exatamente ao estado inicial gerado).
 *
 * @author Cliente
 */
public class EstadoJogo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Mapa mapa;
    private final Jogador jogador;
    private final int percepcao;

    public EstadoJogo(Mapa mapa, Jogador jogador, int percepcao) {
        this.mapa = mapa;
        this.jogador = jogador;
        this.percepcao = percepcao;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public int getPercepcao() {
        return percepcao;
    }

    /**
     * Cria uma cópia profunda deste estado (via serialização em
     * memória), útil para preservar um "estado inicial" intacto que
     * possa ser restaurado múltiplas vezes pela opção Reiniciar Jogo.
     */
    public EstadoJogo copiaProfunda() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
            return (EstadoJogo) in.readObject();
        }
    }
}
