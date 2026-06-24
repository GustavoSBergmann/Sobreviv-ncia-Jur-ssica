package Jogador_Mapa_Outros;

import Dinossauros.*;
import Caixas_E_Itens.CaixaDeSuprimentos;

import java.io.FileReader;
import java.io.IOException;

/*
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
 */
/**
 *
 * @author Cliente
 */
public class Mapa {

    private Entidade[][] mapa;
    private FileReader arquivo;
    private int tamanhoDoMapa, percepcao;

    public Mapa(int tamanhoDoMapa, int dificuldade) {
        this.tamanhoDoMapa = tamanhoDoMapa;
        mapa = new Entidade[tamanhoDoMapa][tamanhoDoMapa];
        percepcao = dificuldade;
    }

    public void inicializaMapa() {
        int caractere;

        try {
            //Cria o leitor de arquivo
            arquivo = new FileReader("src\\Jogador_Mapa_Outros\\mapa.txt");

            int linha = 0;
            int coluna = 0;

            // Enquanto tiver o que ler e for menor que o tamanho do mapa
            while ((caractere = arquivo.read()) != -1 && linha < tamanhoDoMapa) {

                char simbolo = (char) caractere;

                // Ignora quebras de linha
                if (simbolo == '\n' || simbolo == '\r') {
                    continue;
                }

                mapa[linha][coluna] = criarEntidade(simbolo, linha, coluna);

                coluna++;

                if (coluna == tamanhoDoMapa) {
                    coluna = 0;
                    linha++;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro de leitura de arquivo");
            e.printStackTrace();
        }
    }

    private Entidade criarEntidade(char simbolo, int linha, int coluna) {

        switch (simbolo) {

            case 'P':
                return new Jogador(linha, coluna, percepcao);

            case '#':
                return new Parede(linha, coluna);

            case 'C':
                return new Compsognato(linha, coluna);

            case 'T':
                return new Troodonte(linha, coluna);

            case 'V':
                return new Velociraptor(linha, coluna);

            case 'R':
                return new TiranossauroRex(linha, coluna);

            case 'X':
                return new CaixaDeSuprimentos(linha, coluna);

            default:
                return null;
        }
    }

    public void encerraLeitor() {

        if (arquivo != null) {

            try {
                arquivo.close();
            } catch (IOException e) {
                System.out.println("Erro de fechamento de arquivo");
            }

        }
    }

    public void imprimirMapa() {
        char letra = 'A';
        try {
            // Impressão dos números de referência
            System.out.print(" ");
            for (int i = 0; i < tamanhoDoMapa; i++) {
                System.out.print((i + 1));
            }
            System.out.println();

            for (int i = 0; i < tamanhoDoMapa; i++) {
                System.out.print((char) (letra + i));
                for (int j = 0; j < tamanhoDoMapa; j++) {
                    if(mapa[i][j] == null){
                        System.out.print(" ");
                    } else{
                        System.out.print(mapa[i][j].getSimbolo());
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
