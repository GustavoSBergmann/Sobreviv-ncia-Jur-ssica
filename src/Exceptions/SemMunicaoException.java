package Exceptions;

/**
 * Lançada quando o jogador tenta atirar com o Lança-Dardos sem possuir
 * munição (ou sem possuir a arma).
 *
 * @author Cliente
 */
public class SemMunicaoException extends Exception {

    public SemMunicaoException(String mensagem) {
        super(mensagem);
    }
}
