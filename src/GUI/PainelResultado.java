package GUI;

import javax.swing.*;
import java.awt.*;

public class PainelResultado extends JPanel {

    private final JLabel mensagem;

    public PainelResultado(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());
        setBackground(new Color(20, 20, 20));

        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        mensagem = new JLabel("");
        mensagem.setFont(new Font("SansSerif", Font.BOLD, 26));
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton reiniciar = new JButton("Reiniciar Jogo (mesmo mapa)");
        JButton novoJogo = new JButton("Novo Jogo");

        for (JButton b : new JButton[]{reiniciar, novoJogo}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(260, 36));
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        reiniciar.addActionListener(e -> janela.reiniciarJogo());
        novoJogo.addActionListener(e -> janela.mostrarTelaBoasVindas());

        conteudo.add(mensagem);
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(reiniciar);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(novoJogo);

        add(conteudo);
    }

    public void setResultado(boolean vitoria) {
        if (vitoria) {
            mensagem.setText("<html><center>PARABÉNS! VOCÊ SOBREVIVEU!<br>"
                    + "Todos os dinossauros foram derrotados.</center></html>");
            mensagem.setForeground(new Color(90, 220, 90));
        } else {
            mensagem.setText("<html><center>VOCÊ MORREU! FIM DE JOGO!<br>"
                    + "Os dinossauros dominaram o parque.</center></html>");
            mensagem.setForeground(new Color(220, 70, 70));
        }
    }
}
