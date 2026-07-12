package GUI;

import Persistencia.GerenciadorPersistencia;

import javax.swing.*;
import java.awt.*;

/**
 * Tela inicial de boas-vindas com as opções "Jogar", "Carregar Jogo" e
 * "Sair".
 *
 * @author Cliente
 */
public class PainelBoasVindas extends JPanel {

    public PainelBoasVindas(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 40, 25));

        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("SOBREVIVÊNCIA JURÁSSICA");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setForeground(new Color(210, 190, 60));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("As instalações do parque foram invadidas! Sobreviva...");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(Color.LIGHT_GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnJogar = new JButton("Jogar");
        JButton btnCarregar = new JButton("Carregar Jogo");
        JButton btnSair = new JButton("Sair");

        for (JButton b : new JButton[]{btnJogar, btnCarregar, btnSair}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(220, 36));
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        btnCarregar.setEnabled(GerenciadorPersistencia.existeSave());

        btnJogar.addActionListener(e -> janela.mostrarTelaDificuldade());
        btnCarregar.addActionListener(e -> janela.carregarJogoSalvo());
        btnSair.addActionListener(e -> System.exit(0));

        conteudo.add(titulo);
        conteudo.add(Box.createVerticalStrut(6));
        conteudo.add(subtitulo);
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(btnJogar);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(btnCarregar);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(btnSair);

        add(conteudo);
    }

    /** Atualiza a disponibilidade do botão "Carregar Jogo". */
    public void atualizar() {
        for (Component c : getComponents()) {
            atualizarRecursivo(c);
        }
    }

    private void atualizarRecursivo(Component c) {
        if (c instanceof JButton && ((JButton) c).getText().equals("Carregar Jogo")) {
            ((JButton) c).setEnabled(GerenciadorPersistencia.existeSave());
        }
        if (c instanceof Container) {
            for (Component filho : ((Container) c).getComponents()) {
                atualizarRecursivo(filho);
            }
        }
    }
}
