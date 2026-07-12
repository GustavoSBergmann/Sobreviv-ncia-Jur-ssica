package Jogador_Mapa_Outros;

/**
 * Obstáculo intransponível do mapa.
 *
 * @author Cliente
 */
public class Parede extends Entidade {

    private static final long serialVersionUID = 1L;

    @Override
    public char getCaractere() {
        return '#';
    }
}
