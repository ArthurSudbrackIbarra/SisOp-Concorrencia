package EPC3;

import java.util.concurrent.Semaphore;

/*
    The unisex problem, solucao com starvation.
*/

public class UnisexBathroomProblem {

    public static void main(String[] args) {

        Semaphore empty = new Semaphore(1);
        Lightswitch maleSwitch = new Lightswitch();
        Lightswitch femaleSwitch = new Lightswitch();
        Semaphore maleMutex = new Semaphore(3);
        Semaphore femaleMutex = new Semaphore(3);

        // 4 mulheres e 4 homens
        for (int i = 0; i < 4; i++) {
            new Man(i + 1, maleSwitch, maleMutex, empty).start();
        }
        for (int i = 0; i < 4; i++) {
            new Woman(i + 1, femaleSwitch, femaleMutex, empty).start();
        }

    }

}
