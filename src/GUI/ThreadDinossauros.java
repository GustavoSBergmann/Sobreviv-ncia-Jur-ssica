package GUI;

import Dinossauros.Dinossauro;
import Dinossauros.Movivel;
import Jogador_Mapa_Outros.Mapa;

import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class ThreadDinossauros extends Thread {

    private static final long INTERVALO_MS = 1000L;

    private final Mapa mapa;
    private final PainelJogo painelJogo;

    private volatile boolean pausada = false;
    private volatile boolean ativa = true;

    public ThreadDinossauros(Mapa mapa, PainelJogo painelJogo) {
        super("ThreadDinossauros");
        this.mapa = mapa;
        this.painelJogo = painelJogo;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (ativa) {
            try {
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e) {
                break;
            }
            if (!ativa || pausada) {
                continue;
            }

            boolean encontrouJogador = moverTodosOsDinossauros();
            if (!encontrouJogador) {
                SwingUtilities.invokeLater(painelJogo::atualizarGrade);
            }
        }
    }

    /**
     * Move, em sequência, cada dinossauro vivo que se move. Para assim
     * que um deles encontra o jogador (o restante tentará se mover no
     * próximo ciclo, depois que o combate terminar).
     */
    private boolean moverTodosOsDinossauros() {
        for (Dinossauro dino : new ArrayList<>(mapa.getDinossauros())) {
            if (!(dino instanceof Movivel) || !dino.estaVivo()) {
                continue;
            }
            boolean encontrouJogador = dino.mover(mapa);
            if (encontrouJogador) {
                pausada = true;
                SwingUtilities.invokeLater(() -> painelJogo.aoDinossauroEncontrarJogador(dino));
                return true;
            }
        }
        return false;
    }

    public void pausar() {
        pausada = true;
    }

    public void retomar() {
        pausada = false;
    }

    public void encerrar() {
        ativa = false;
        this.interrupt();
    }
}
