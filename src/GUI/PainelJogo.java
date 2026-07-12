package GUI;

import Caixas_E_Itens.CaixaDeSuprimentos;
import Caixas_E_Itens.ConteudoCaixa;
import Caixas_E_Itens.ResultadoCaixa;
import Dinossauros.Dinossauro;
import Exceptions.MovimentoInvalidoException;
import Jogador_Mapa_Outros.Jogador;
import Jogador_Mapa_Outros.Mapa;
import Combate.Combate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Painel principal da jogabilidade: exibe o tabuleiro como uma grade de botões,
 * o status do jogador e as ações disponíveis (Cura, Debug, Salvar, Sair).
 * Também gerencia o ciclo de vida da {@link ThreadDinossauros}, única thread
 * responsável por movimentar os dinossauros do mapa.
 *
 * @author Cliente
 */
public class PainelJogo extends JPanel {

    /**
     * Tamanho fixo (em pixels) de cada ícone do tabuleiro. Usar um valor
     * fixo — em vez de perguntar ao próprio JButton seu getWidth()/
     * getHeight() — evita depender do LayoutManager já ter posicionado
     * os componentes na tela (o que só acontece depois que a janela é
     * exibida pela primeira vez).
     */
    private static final int TAMANHO_ICONE = 90;

    private final JanelaPrincipal janela;

    private Mapa mapa;
    private Jogador jogador;

    private JButton[][] celulas;
    private JPanel painelGrade;
    private JLabel labelStatus;
    private JButton btnCura;

    /** Cache dos ícones já carregados e redimensionados, para não reler o disco a cada atualização. */
    private final Map<Character, ImageIcon> cacheIcones = new HashMap<>();

    private ThreadDinossauros threadDinossauros;
    private boolean combateEmAndamento = false;
    private boolean jogoEncerrado = false;

    public PainelJogo(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(6, 6));
        setBackground(new Color(235, 235, 225));

        montarTopo();
        painelGrade = new JPanel();
        add(painelGrade, BorderLayout.CENTER);
    }

    private void montarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(50, 60, 40));

        labelStatus = new JLabel(" ");
        labelStatus.setForeground(Color.WHITE);
        labelStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelStatus.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
        topo.add(labelStatus, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.setOpaque(false);

        btnCura = new JButton("Usar Kit Médico");
        JButton btnDebug = new JButton("DEBUG (revelar mapa)");
        JButton btnSalvar = new JButton("Salvar Jogo");
        JButton btnSair = new JButton("Sair do Jogo");

        btnCura.addActionListener(e -> usarKitMedico());
        btnDebug.addActionListener(e -> alternarDebug());
        btnSalvar.addActionListener(e -> janela.salvarJogoAtual());
        btnSair.addActionListener(e -> sairDoJogo());

        botoes.add(btnCura);
        botoes.add(btnDebug);
        botoes.add(btnSalvar);
        botoes.add(btnSair);
        topo.add(botoes, BorderLayout.EAST);

        add(topo, BorderLayout.NORTH);
    }

    /**
     * Inicia (ou retoma, no caso de jogo carregado) a exibição de uma partida.
     */
    public void iniciarJogo(Mapa mapa, Jogador jogador) {
        pararThread();
        this.mapa = mapa;
        this.jogador = jogador;
        this.jogoEncerrado = false;
        this.combateEmAndamento = false;

        construirGrade();
        //SwingUtilities.invokeLater(() -> atualizarGrade());
        atualizarGrade();
        atualizarStatus();

        threadDinossauros = new ThreadDinossauros(mapa, this);
        threadDinossauros.start();
    }

    private void construirGrade() {
        int tamanho = mapa.getTamanho();
        painelGrade.removeAll();
        painelGrade.setLayout(new GridLayout(tamanho + 1, tamanho + 1, 1, 1));
        painelGrade.setBackground(new Color(235, 235, 225));

        celulas = new JButton[tamanho][tamanho];

        painelGrade.add(new JLabel(""));
        for (int col = 0; col < tamanho; col++) {
            JLabel lbl = new JLabel(String.valueOf(col + 1), SwingConstants.CENTER);
            lbl.setFont(new Font("Monospaced", Font.BOLD, 12));
            painelGrade.add(lbl);
        }

        for (int lin = 0; lin < tamanho; lin++) {
            JLabel lblLinha = new JLabel(mapa.linhaParaLetra(lin), SwingConstants.CENTER);
            lblLinha.setFont(new Font("Monospaced", Font.BOLD, 12));
            painelGrade.add(lblLinha);

            for (int col = 0; col < tamanho; col++) {
                final int linha = lin;
                final int coluna = col;
                JButton celula = new JButton();
                //celula.setFont(new Font("Monospaced", Font.BOLD, 14));
                celula.setMargin(new Insets(2, 2, 2, 2));
                celula.setPreferredSize(new Dimension(TAMANHO_ICONE, TAMANHO_ICONE));
                

                celula.addActionListener(e -> moverJogadorGUI(linha, coluna));
                celulas[lin][col] = celula;
                painelGrade.add(celula);
            }
        }

        revalidate();
        repaint();
    }

    // ------------------------------------------------------------
    //  ATUALIZAÇÃO VISUAL
    // ------------------------------------------------------------
    void atualizarGrade() {
        int tamanho = mapa.getTamanho();
        for (int lin = 0; lin < tamanho; lin++) {
            for (int col = 0; col < tamanho; col++) {
                char simbolo = mapa.getCelulaVisivel(lin, col);
                JButton celula = celulas[lin][col];
                celula.setIcon(imagemParaSimbolo(simbolo));
                celula.setOpaque(true);
                celula.setBorderPainted(false);
            }
        }
    }

    /**
     * Retorna o ícone correspondente ao símbolo, já redimensionado. Usa
     * um cache para carregar e redimensionar cada imagem apenas uma vez
     * — sem isso, releríamos e reescalaríamos o PNG do disco a cada
     * atualização da grade (que acontece a cada movimento de qualquer
     * dinossauro).
     */
    private ImageIcon imagemParaSimbolo(char simbolo) {
        return cacheIcones.computeIfAbsent(simbolo, this::carregarIcone);
    }

    private ImageIcon carregarIcone(char simbolo) {
        String arquivo;
        switch (simbolo) {
            case 'J':
                arquivo = "Jogador.png";
                break;
            case '#':
                arquivo = "Parede.png";
                break;
            case 'X':
                arquivo = "Caixa.png";
                break;
            case 'C':
                arquivo = "Compsognato.png";
                break;
            case 'T':
                arquivo = "Troodonte.png";
                break;
            case 'V':
                arquivo = "Velociraptor.png";
                break;
            case 'R':
                arquivo = "TiranossauroRex.png";
                break;
            default:
                arquivo = "Grama.png";
                break;
        }

        ImageIcon iconeOriginal = new ImageIcon(arquivo);

        // Tamanho FIXO — não depende de nenhum componente já ter sido
        // desenhado na tela, então funciona mesmo na primeira chamada,
        // antes da janela aparecer.
        Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(
                TAMANHO_ICONE, TAMANHO_ICONE, java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(imagemRedimensionada);
    }

    private void atualizarStatus() {
        labelStatus.setText(jogador.getStatusString()
                + "   |   Dinossauros restantes: " + mapa.getDinossauros().size());
        btnCura.setEnabled(jogador.getInventario().possuiKitMedico());
    }

    private void alternarDebug() {
        mapa.setResetDebug();
        atualizarGrade();
    }

    // ------------------------------------------------------------
    //  AÇÕES DO JOGADOR
    // ------------------------------------------------------------
    private void usarKitMedico() {
        if (jogoEncerrado || combateEmAndamento) {
            return;
        }
        String msg = jogador.usarKitMedico();
        JOptionPane.showMessageDialog(this, msg, "Kit Médico", JOptionPane.INFORMATION_MESSAGE);
        atualizarStatus();
    }

    private void moverJogadorGUI(int linha, int coluna) {
        if (jogoEncerrado || combateEmAndamento) {
            return;
        }
        threadDinossauros.pausar();
        try {
            Dinossauro dino = mapa.moverJogador(linha, coluna);
            if (dino != null) {
                iniciarCombate(dino, false);
                return;
            }

            processarCaixaSePresente(jogador.getLinha(), jogador.getColuna());

            atualizarGrade();
            atualizarStatus();
            threadDinossauros.retomar();
        } catch (MovimentoInvalidoException ex) {
            //JOptionPane.showMessageDialog(this, ex.getMessage(), "Movimento inválido", JOptionPane.WARNING_MESSAGE);
            threadDinossauros.retomar();
        }
    }

    /**
     * Verifica e processa uma caixa de suprimentos na posição indicada, se
     * houver.
     */
    private void processarCaixaSePresente(int linha, int coluna) {
        CaixaDeSuprimentos caixa = mapa.getCaixaEm(linha, coluna);
        if (caixa == null || caixa.foiAberta()) {
            return;
        }
        ConteudoCaixa conteudo = caixa.getConteudo();
        caixa.abrir();
        mapa.removerCaixa(linha, coluna);

        ResultadoCaixa resultado = conteudo.aoSerEncontrado(jogador, mapa);
        JOptionPane.showMessageDialog(this, resultado.getMensagem(),
                "Caixa de Suprimentos", JOptionPane.INFORMATION_MESSAGE);

        if (resultado.temSurpresa() && resultado.getSurpresa() instanceof Dinossauro) {
            Dinossauro surpresa = (Dinossauro) resultado.getSurpresa();
            if (surpresa.estaVivo() && jogador.estaVivo()) {
                iniciarCombate(surpresa, false);
            }
        }
    }

    // ------------------------------------------------------------
    //  COMBATE
    // ------------------------------------------------------------
    /**
     * Chamado pela {@link ThreadDinossauros} (via {@code invokeLater}, portanto
     * já na Event Dispatch Thread) quando um dinossauro encontra o jogador
     * durante sua movimentação autônoma.
     */
    public void aoDinossauroEncontrarJogador(Dinossauro dino) {
        if (jogoEncerrado || combateEmAndamento || !jogador.estaVivo() || !dino.estaVivo()) {
            threadDinossauros.retomar();
            return;
        }
        iniciarCombate(dino, true);
    }

    private void iniciarCombate(Dinossauro dino, boolean dinoAtacaPrimeiro) {
        combateEmAndamento = true;
        threadDinossauros.pausar();

        Combate combate = new Combate(jogador, dino, mapa);
        CombateDialog dialogo = new CombateDialog(janela, combate, dinoAtacaPrimeiro);
        dialogo.setVisible(true); // modal — bloqueia até o combate terminar

        if (!dino.estaVivo()) {
            mapa.removerDinossauro(dino);
        }

        combateEmAndamento = false;
        atualizarGrade();
        atualizarStatus();

        if (!jogador.estaVivo()) {
            finalizarJogo(false);
        } else if (mapa.todosDerrotados()) {
            finalizarJogo(true);
        } else {
            threadDinossauros.retomar();
        }
    }

    private void sairDoJogo() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair? Você pode salvar o jogo antes de sair.",
                "Sair do Jogo", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            pararThread();
            janela.mostrarTelaBoasVindas();
        }
    }

    private void finalizarJogo(boolean vitoria) {
        jogoEncerrado = true;
        pararThread();
        janela.mostrarResultado(vitoria);
    }

    // ------------------------------------------------------------
    //  THREAD DE MOVIMENTAÇÃO DOS DINOSSAUROS
    // ------------------------------------------------------------
    private void pararThread() {
        if (threadDinossauros != null) {
            threadDinossauros.encerrar();
            threadDinossauros = null;
        }
    }
}
