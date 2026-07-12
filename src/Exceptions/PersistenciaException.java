package Exceptions;

/**
 * Lançada quando ocorre um problema ao salvar ou carregar o progresso
 * do jogo em arquivo.
 *
 * @author Cliente
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
