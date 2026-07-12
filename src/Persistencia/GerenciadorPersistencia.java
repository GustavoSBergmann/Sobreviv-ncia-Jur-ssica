package Persistencia;

import Exceptions.PersistenciaException;

import java.io.*;

/**
 * Responsável por gravar e ler o progresso do jogo em arquivo, usando
 * serialização de objetos Java.
 *
 * @author Cliente
 */
public class GerenciadorPersistencia {

    private static final String ARQUIVO_PADRAO = "sobrevivencia_save.dat";

    private GerenciadorPersistencia() {
    }

    public static void salvar(EstadoJogo estado) throws PersistenciaException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_PADRAO))) {
            out.writeObject(estado);
        } catch (IOException e) {
            throw new PersistenciaException("Não foi possível salvar o jogo: " + e.getMessage(), e);
        }
    }

    public static EstadoJogo carregar() throws PersistenciaException {
        File f = new File(ARQUIVO_PADRAO);
        if (!f.exists()) {
            throw new PersistenciaException("Nenhum jogo salvo foi encontrado.");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (EstadoJogo) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Não foi possível carregar o jogo: " + e.getMessage(), e);
        }
    }

    public static boolean existeSave() {
        return new File(ARQUIVO_PADRAO).exists();
    }
}
