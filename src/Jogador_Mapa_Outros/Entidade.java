package Jogador_Mapa_Outros;

import java.io.Serializable;

/**
 * Qualquer coisa que possa ser posicionada e desenhada no mapa
 * (paredes, personagens, dinossauros, etc.).
 *
 * @author Cliente
 */
public abstract class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract char getCaractere();
}
