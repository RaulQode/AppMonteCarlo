import com.zeroc.Ice.Current;

import Demo.WorkerPrx;
import java.util.List;
import java.util.ArrayList;

public class MasterI implements Demo.Master {
    private final List<WorkerPrx> connectedWorkers = new ArrayList<>();
    private int receivedValue;

    public synchronized void addWorker(WorkerPrx worker, Current current) {
        WorkerPrx workerPrx = WorkerPrx.checkedCast(worker);
        if (workerPrx != null && !connectedWorkers.contains(workerPrx)) {
            connectedWorkers.add(workerPrx);
            System.out.println("Worker registrado: " + workerPrx);
            System.out.println("Número total de workers conectados: " + connectedWorkers.size());
        } else {
            System.out.println("Error al registrar el worker: " + worker);
        }
    }

    @Override
    public void sendValue(int value, Current current) {
        receivedValue = value;
        System.out.println("Valor recibido desde el cliente: " + receivedValue);
    }

    @Override
    public double estimatePi(int points, Current current) {
        int[] dividePoints = divideWork(points);
        int sumPoints = 0;

        for (int i = 0; i < connectedWorkers.size(); i++) {
            sumPoints += connectedWorkers.get(i).calculatePointsInCircle(dividePoints[i]);
        }

        return sumPoints * 4.0 / points;
    }

    @Override
    public void printString(String s, Current current) {
        System.out.println(s);
    }

    public synchronized int[] divideWork(int points) {
        int workers = connectedWorkers.size();
        int pointsPerWorker = points / workers;
        int remainingPoints = points % workers;
        int[] totalPoints = new int[workers];

        for (int i = 0; i < workers; i++) {
            totalPoints[i] = pointsPerWorker + (remainingPoints-- > 0 ? 1 : 0);
        }
        return totalPoints;
    }
}