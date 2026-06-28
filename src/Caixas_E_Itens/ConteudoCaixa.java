package Caixas_E_Itens;

import Jogador_Mapa_Outros.*;
import java.util.Scanner;

/**
 * Interface para qualquer coisa que pode estar dentro de uma Caixa de Suprimentos.
 * Descreve "o que faz" (ser conteúdo de caixa), não "o que é".
 */
public interface ConteudoCaixa {
    // Marca algo como possível conteúdo de caixa de suprimentos.
    // Pode ser um Item ou um Compsognato surpresa.
    void aoSerEncontrado(Jogador jogador, Mapa mapa, Scanner leitor);
}
