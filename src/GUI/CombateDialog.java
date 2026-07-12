package GUI;

import Combate.Combate;
import Exceptions.SemMunicaoException;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Janela modal (JDialog) que conduz um combate entre o jogador e um dinossauro,
 * substituindo a antiga interação via console/Scanner por botões e caixas de
 * diálogo do Swing.
 *
 * @author Cliente
 */
public class CombateDialog extends JDialog {

    private final Combate combate;
    private final boolean dinoAtacaPrimeiro;

    private final JTextArea log;
    private final JLabel statusJogador;
    private final JLabel statusDino;
    private final JButton btnCorpoACorpo;
    private final JButton btnDardos;
    private final JButton btnFugir;

    private boolean jogadorSobreviveu = true;
    private boolean fugiu = false;

    public CombateDialog(Frame owner, Combate combate, boolean dinoAtacaPrimeiro) {
        super(owner, "Combate!", true);
        this.combate = combate;
        this.dinoAtacaPrimeiro = dinoAtacaPrimeiro;

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setSize(480, 420);
        setLocationRelativeTo(owner);
        setResizable(false);

        statusJogador = new JLabel();
        statusDino = new JLabel();
        JPanel topo = new JPanel(new GridLayout(2, 1));
        topo.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        topo.add(statusJogador);
        topo.add(statusDino);
        add(topo, BorderLayout.NORTH);

        log = new JTextArea();
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(log);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(scroll, BorderLayout.CENTER);

        btnCorpoACorpo = new JButton();
        btnDardos = new JButton("Atacar com Lança-Dardos");
        btnFugir = new JButton("Fugir");
        JPanel botoes = new JPanel(new FlowLayout());
        botoes.add(btnCorpoACorpo);
        botoes.add(btnDardos);
        botoes.add(btnFugir);
        add(botoes, BorderLayout.SOUTH);

        btnCorpoACorpo.setText("Atacar com "
                + (combate.getJogador().getInventario().possuiBastaoEletrico() ? "Bastão Elétrico" : "Mãos Nuas"));
        btnDardos.setEnabled(combate.getJogador().getInventario().possuiLancaDardos());

        btnCorpoACorpo.addActionListener(e -> turnoJogador(false));
        btnDardos.addActionListener(e -> turnoJogador(true));
        btnFugir.addActionListener(e -> tentarFuga());

        atualizarStatus();
        escrever("Você encontrou um " + combate.getDino() + "!");

        if (dinoAtacaPrimeiro) {
            escrever(combate.getDino().getClass().getSimpleName() + " te pegou de surpresa e ataca primeiro!");
            String resultado = combate.turnoDinoContraAtaca();
            escrever(resultado);
            atualizarStatus();

            if (!combate.getJogador().estaVivo()) {
                finalizar(false);
            }
        }
    }

    private void turnoJogador(boolean comDardos) {
        try {
            String resultado = comDardos ? combate.atacarComDardos() : combate.atacarComMaosOuBastao();
            escrever(resultado);
        } catch (SemMunicaoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Sem munição", JOptionPane.WARNING_MESSAGE);
            return;
        }
        atualizarStatus();

        if (!combate.getDino().estaVivo()) {
            escrever("Você derrotou o " + combate.getDino().getClass().getSimpleName() + "!");
            int linha = combate.getDino().getLinha();
            int coluna = combate.getDino().getColuna();
            if (!dinoAtacaPrimeiro) {
                combate.getMapa().jogadorAvancaParaPosicaoDino(linha, coluna);
            }
            finalizar(true);
            return;
        }

        String contraAtaque = combate.turnoDinoContraAtaca();
        escrever(contraAtaque);
        atualizarStatus();

        if (!combate.getJogador().estaVivo()) {
            escrever("Você foi derrotado...");
            finalizar(false);
        }
    }

    private void tentarFuga() {
        List<int[]> opcoes = combate.getMapa().posicoesParaFuga();
        if (opcoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há para onde fugir! Você está encurralado!");
            return;
        }
        String[] labels = new String[opcoes.size()];
        for (int i = 0; i < opcoes.size(); i++) {
            int[] p = opcoes.get(i);
            labels[i] = combate.getMapa().linhaParaLetra(p[0]) + (p[1] + 1);
        }
        String escolha = (String) JOptionPane.showInputDialog(this, "Para onde você quer fugir?",
                "Fuga", JOptionPane.QUESTION_MESSAGE, null, labels, labels[0]);
        if (escolha == null) {
            return;
        }
        int idx = Arrays.asList(labels).indexOf(escolha);
        int[] destino = opcoes.get(idx);
        boolean ok = combate.tentarFugir(destino[0], destino[1]);
        if (ok) {
            escrever("Você conseguiu fugir!");
            fugiu = true;
            jogadorSobreviveu = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível fugir para essa posição!");
        }
    }

    private void finalizar(boolean venceuCombate) {
        jogadorSobreviveu = combate.getJogador().estaVivo();
        btnCorpoACorpo.setEnabled(false);
        btnDardos.setEnabled(false);
        btnFugir.setEnabled(false);

        JButton continuar = new JButton("Continuar");
        continuar.addActionListener(e -> dispose());
        JPanel painelContinuar = new JPanel(new FlowLayout());
        painelContinuar.add(continuar);
        add(painelContinuar, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void atualizarStatus() {
        statusJogador.setText("Você — Saúde: " + combate.getJogador().getSaude()
                + "/" + combate.getJogador().getSaudeMaxima());
        statusDino.setText(combate.getDino().getClass().getSimpleName() + " — Saúde: "
                + combate.getDino().getSaude() + "/" + combate.getDino().getSaudeMaxima());
    }

    private void escrever(String texto) {
        log.append(texto + "\n\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    public boolean isJogadorSobreviveu() {
        return jogadorSobreviveu;
    }

    public boolean isFugiu() {
        return fugiu;
    }
}
