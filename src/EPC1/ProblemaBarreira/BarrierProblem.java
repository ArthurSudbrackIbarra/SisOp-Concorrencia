package EPC1.ProblemaBarreira;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class BarrierProblem {

    public static void main(String[] args) {

        // Iterações.
        int iteractions = 1;
        // Lado N da matriz.
        int matrixSize = 10;
        // Quantidade de células da matriz.
        int cellsCount = matrixSize * matrixSize;

        // A matriz.
        int[][] matrix = new int[matrixSize][matrixSize];
        // Semáforo para operações de read e write na matriz.
        Semaphore matrixMutex = new Semaphore(1);
        // Preenchendo e exibindo matriz original.
        fillMatrix(matrix);
        printMatrix(matrix);

        // Objeto barreira.
        Barrier barrier = new Barrier(cellsCount);

        // Lista com todas as threads.
        ArrayList<Thread> threads = new ArrayList<>();

        // Cria uma thread para cada célula da matriz e as executa.
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                CellProcessor cp = new CellProcessor(matrix, i, j, matrixMutex, barrier, iteractions);
                threads.add(cp);
                cp.start();
            }
        }

        // Espera todas as threads terminarem.
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        }

        // Printa matriz após execução das threads.
        printMatrix(matrix);

    }

    // Método para preencher a matriz com valores aleatórios de 1 a 99.
    public static void fillMatrix(int[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = random.nextInt(99) + 1;
            }
        }
    }

    // Método para exibir a matriz na tela.
    public static void printMatrix(int[][] matrix) {
        System.out.println("\n");
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                int number = ints[j];
                if (number < 10) {
                    System.out.print("0" + number + " ");
                } else {
                    System.out.print(number + " ");
                }
            }
            System.out.println();
        }
        System.out.println("\n");
    }

}
