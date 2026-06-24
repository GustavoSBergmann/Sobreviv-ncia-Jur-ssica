package Jogador_Mapa_Outros;

import java.util.Scanner;

/**
 *
 * @author Cliente
 */
public class SobrevivenciaJurassica {

    public static void main(String[] args) {

        int dificuldade = menu();;
        
        Mapa jogo = new Mapa(10, dificuldade);

        jogo.inicializaMapa();

        jogo.imprimirMapa();
        jogo.encerraLeitor();
    }

    public static int menu() {
        Scanner leitor = new Scanner(System.in);
        int opcao, dificuldade;

        System.out.println("Seja bem vindo! Você entrou no jogo \"Sobrevivência Jurássica\"");
        System.out.println("Opções:");
        System.out.println("1 - Jogar");
        System.out.println("2 - Sair");
        System.out.print("Faça sua escolha: ");
        opcao = leitor.nextInt();

        if (opcao == 1) {
            System.out.println("Níveis de dificuldade:");
            System.out.println("1: Difícil");
            System.out.println("2: Médio");
            System.out.println("3: Fácil");
            System.out.print("Faça sua escolha: ");
            dificuldade = leitor.nextInt();

            return dificuldade;
        } else {
            System.exit(0);
        }
        
        return 0;
    }

}
