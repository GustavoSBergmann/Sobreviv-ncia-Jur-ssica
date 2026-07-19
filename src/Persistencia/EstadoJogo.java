package Persistencia;

import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;

import java.io.*;
import java.io.Serializable;

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
