package EPC1.ProblemaBarreira;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class CellProcessor extends Thread {

    // A matriz.
    private final int[][] matrix;
    // Posição (i, j) da matriz que a thread está responsável (célula).
    private final int i;
    private final int j;
    // Semáforo para operações de read e write na matriz.
    private final Semaphore matrixMutex;
    // Objeto barreira.
    private final Barrier barrier;
    // Iterações.
    private final int iteractions;

    public CellProcessor(int[][] matrix, int i, int j, Semaphore matrixMutex, Barrier barrier, int iteractions){
        this.matrix = matrix;
        this.i = i;
        this.j = j;
        this.matrixMutex = matrixMutex;
        this.barrier = barrier;
        this.iteractions = iteractions;
    }

    // Método responsável por calcular a média de acordo com os vizinhos.
    public int calculateAverage() {

        // Lista com os valores dos vizinhos.
        ArrayList<Integer> sides = new ArrayList<>();

        // Ponto crítico.
        try {
            matrixMutex.acquire();
        } catch (InterruptedException ignored) {}

        // Esquerda.
        if (j >= 1) {
            sides.add(matrix[i][j - 1]);
        }
        // Direita.
        if (j < matrix.length - 1) {
            sides.add(matrix[i][j + 1]);
        }
        // Cima.
        if (i >= 1) {
            sides.add(matrix[i - 1][j]);
        }
        // Baixo.
        if (i < matrix.length - 1) {
            sides.add(matrix[i + 1][j]);;
        }

        // Fim ponto crítico.
        matrixMutex.release();

        // Soma dos vizinhos.
        int sum = 0;
        for (Integer side : sides) {
            sum += side;
        }

        // Média.
        return sum/sides.size();
    }

    @Override
    public void run() {
        // de 0 até N iterações.
        for (int k = 0; k < iteractions; k++) {
            // Calcula a média.
            int average = calculateAverage();
            // Indica que a thread terminou a fase de leitura.
            barrier.readPhase();
            // Ponto crítico.
            try {
                matrixMutex.acquire();
            } catch (InterruptedException ignored) {}
            // Escreve na matriz a média.
            matrix[i][j] = average;
            // Fim ponto crítico.
            matrixMutex.release();
            // Indica que a thread terminou a fase de escrita.
            barrier.writePhase();
        }
    }

}
