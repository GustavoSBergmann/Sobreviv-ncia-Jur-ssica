package GUI;

import javax.swing.*;
import java.awt.*;

public class PainelDificuldade extends JPanel {

    public PainelDificuldade(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 40, 25));

        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Escolha a Dificuldade");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton facil = new JButton("Fácil (Percepção 3)");
        JButton medio = new JButton("Médio (Percepção 2)");
        JButton dificil = new JButton("Difícil (Percepção 1)");
        JButton voltar = new JButton("Voltar");

        for (JButton b : new JButton[]{facil, medio, dificil, voltar}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(240, 36));
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        facil.addActionListener(e -> janela.iniciarNovoJogo(3));
        medio.addActionListener(e -> janela.iniciarNovoJogo(2));
        dificil.addActionListener(e -> janela.iniciarNovoJogo(1));
        voltar.addActionListener(e -> janela.mostrarTelaBoasVindas());

        conteudo.add(titulo);
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(facil);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(medio);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(dificil);
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(voltar);

        add(conteudo);
    }
}
