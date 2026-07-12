package Caixas_E_Itens;

import java.util.Random;

/**
 * Representa qualquer arma que o jogador possa equipar para atacar um
 * dinossauro em combate. É uma subclasse de {@link Item} (toda arma é um
 * item que pode ser encontrado no mapa) e superclasse de
 * {@link BastaoEletrico} e {@link LancaDardos}.
 *
 * <p>
 * A lógica de dano (rolagem de dado, crítico, chance de erro, consumo de
 * munição, etc.) fica encapsulada dentro de cada arma através do método
 * {@link #calcularDano(Random)}, que é sobrescrito por cada subclasse
 * concreta — cada arma "sabe" causar o seu próprio dano, evitando que a
 * classe de Combate precise conhecer as regras específicas de cada
 * armamento (polimorfismo / Template Method).</p>
 *
 * Hierarquia: Item {@literal --|>} Arma {@literal --|>} BastaoEletrico e LancaDardos
 *
 * @author Cliente
 */
public abstract class Arma extends Item {

    private static final long serialVersionUID = 1L;

    public Arma(String nome) {
        super(nome);
    }

    /**
     * Calcula (e aplica os efeitos colaterais de, como gastar munição) o
     * dano causado por um ataque com esta arma. Cada arma sobrescreve
     * este método com sua própria regra de dado.
     *
     * @param rand gerador de números aleatórios utilizado para "rolar o dado"
     * @return o dano causado (0, 1 ou 2 pontos de saúde)
     */
    public abstract int calcularDano(Random rand);

    /**
     * Mensagem descritiva do resultado do último ataque realizado com
     * esta arma (útil para exibição na interface gráfica).
     */
    public abstract String getUltimaMensagem();
}
