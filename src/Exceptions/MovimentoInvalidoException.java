package Exceptions;

/**
 * Lançada quando o jogador tenta se mover para uma posição inválida
 * (fora do mapa, muito distante, ou bloqueada por uma parede).
 *
 * @author Cliente
 */
public class MovimentoInvalidoException extends Exception {

    public MovimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
