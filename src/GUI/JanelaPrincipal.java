package GUI;

import Exceptions.PersistenciaException;
import Jogador_Mapa_Outros.GeradorDeMapa;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import Persistencia.EstadoJogo;
import Persistencia.GerenciadorPersistencia;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Janela principal da aplicação. Utiliza um {@link CardLayout} para
 * alternar entre as telas de boas-vindas, escolha de dificuldade, jogo e
 * resultado, e concentra a lógica de navegação e de criação/persistência
 * do estado da partida.
 *
 * @author Cliente
 */
public class JanelaPrincipal extends JFrame {

    private static final int TAMANHO_MAPA = 10;
    private static final String CARTAO_BOAS_VINDAS = "boasVindas";
    private static final String CARTAO_DIFICULDADE = "dificuldade";
    private static final String CARTAO_JOGO = "jogo";
    private static final String CARTAO_RESULTADO = "resultado";

    private final CardLayout cardLayout;
    private final JPanel painelCartoes;

    private final PainelBoasVindas painelBoasVindas;
    private final PainelDificuldade painelDificuldade;
    private final PainelJogo painelJogo;
    private final PainelResultado painelResultado;

    /** Guarda o estado logo após a geração do mapa, para a opção "Reiniciar Jogo". */
    private EstadoJogo estadoInicialDaRodada;

    public JanelaPrincipal() {
        super("Sobrevivência Jurássica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(750, 600));

        cardLayout = new CardLayout();
        painelCartoes = new JPanel(cardLayout);

        painelBoasVindas = new PainelBoasVindas(this);
        painelDificuldade = new PainelDificuldade(this);
        painelJogo = new PainelJogo(this);
        painelResultado = new PainelResultado(this);

        painelCartoes.add(painelBoasVindas, CARTAO_BOAS_VINDAS);
        painelCartoes.add(painelDificuldade, CARTAO_DIFICULDADE);
        painelCartoes.add(painelJogo, CARTAO_JOGO);
        painelCartoes.add(painelResultado, CARTAO_RESULTADO);

        setContentPane(painelCartoes);
        cardLayout.show(painelCartoes, CARTAO_BOAS_VINDAS);
    }

    // ------------------------------------------------------------
    //  NAVEGAÇÃO
    // ------------------------------------------------------------
    public void mostrarTelaBoasVindas() {
        painelBoasVindas.atualizar();
        cardLayout.show(painelCartoes, CARTAO_BOAS_VINDAS);
    }

    public void mostrarTelaDificuldade() {
        cardLayout.show(painelCartoes, CARTAO_DIFICULDADE);
    }

    public void mostrarResultado(boolean vitoria) {
        painelResultado.setResultado(vitoria);
        cardLayout.show(painelCartoes, CARTAO_RESULTADO);
    }

    // ------------------------------------------------------------
    //  CICLO DE VIDA DA PARTIDA
    // ------------------------------------------------------------
    public void iniciarNovoJogo(int percepcao) {
        Mapa mapa = new Mapa(TAMANHO_MAPA);
        Jogador jogador = new Jogador(percepcao);
        mapa.setJogador(jogador);
        new GeradorDeMapa().gerar(mapa, jogador);

        guardarEstadoInicial(mapa, jogador, percepcao);
        registrarMapaEmJogo(mapa);

        cardLayout.show(painelCartoes, CARTAO_JOGO);
        painelJogo.iniciarJogo(mapa, jogador);
    }

    /** Reinicia com o mesmo mapa e mesma dificuldade da rodada atual. */
    public void reiniciarJogo() {
        if (estadoInicialDaRodada == null) {
            mostrarTelaBoasVindas();
            return;
        }
        try {
            EstadoJogo copia = estadoInicialDaRodada.copiaProfunda();
            copia.getMapa().setJogador(copia.getJogador());
            registrarMapaEmJogo(copia.getMapa());
            cardLayout.show(painelCartoes, CARTAO_JOGO);
            painelJogo.iniciarJogo(copia.getMapa(), copia.getJogador());
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível reiniciar o jogo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            mostrarTelaBoasVindas();
        }
    }

    private void guardarEstadoInicial(Mapa mapa, Jogador jogador, int percepcao) {
        try {
            EstadoJogo estadoAtual = new EstadoJogo(mapa, jogador, percepcao);
            estadoInicialDaRodada = estadoAtual.copiaProfunda();
        } catch (IOException | ClassNotFoundException e) {
            // Caso a cópia falhe, "Reiniciar Jogo" simplesmente não ficará
            // disponível com o mapa exato; o jogo em si não é afetado.
            estadoInicialDaRodada = null;
        }
    }

    // ------------------------------------------------------------
    //  PERSISTÊNCIA EM ARQUIVO
    // ------------------------------------------------------------
    public void salvarJogoAtual() {
        Mapa mapa = mapaEmJogo;
        if (mapa == null) {
            return;
        }
        try {
            EstadoJogo estado = new EstadoJogo(mapa, mapa.getJogador(), mapa.getJogador().getPercepcao());
            GerenciadorPersistencia.salvar(estado);
            JOptionPane.showMessageDialog(this, "Jogo salvo com sucesso!", "Salvar Jogo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao salvar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarJogoSalvo() {
        try {
            EstadoJogo estado = GerenciadorPersistencia.carregar();
            estado.getMapa().setJogador(estado.getJogador());
            guardarEstadoInicial(estado.getMapa(), estado.getJogador(), estado.getPercepcao());
            registrarMapaEmJogo(estado.getMapa());
            cardLayout.show(painelCartoes, CARTAO_JOGO);
            painelJogo.iniciarJogo(estado.getMapa(), estado.getJogador());
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao carregar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Mapa mapaEmJogo;

    /** Chamado internamente sempre que uma partida é (re)iniciada, para permitir salvar. */
    void registrarMapaEmJogo(Mapa mapa) {
        this.mapaEmJogo = mapa;
    }

    // ------------------------------------------------------------
    //  MAIN
    // ------------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Mantém o look and feel padrão caso o do sistema não esteja disponível.
        }
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}
