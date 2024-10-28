import com.zeroc.Ice.Current;
import com.zeroc.Ice.Exception;

import Demo.WorkerPrx;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        if (connectedWorkers.isEmpty()) {
            throw new RuntimeException("No hay workers conectados");
        }

        int[] dividePoints = divideWork(points);
        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        // Crear una tarea asíncrona para cada worker
        for (int i = 0; i < connectedWorkers.size(); i++) {
            final int workerIndex = i;
            final WorkerPrx worker = connectedWorkers.get(i);
            
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("Enviando " + dividePoints[workerIndex] + " puntos al worker " + workerIndex);
                    return worker.calculatePointsInCircle(dividePoints[workerIndex]);
                } catch (Exception e) {
                    System.err.println("Error en worker " + workerIndex + ": " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });
            
            futures.add(future);
        }

        try {
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
            );

            allFutures.get();

            int sumPoints = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("Error al obtener resultado del worker", e);
                    }
                })
                .mapToInt(Integer::intValue)
                .sum();

            return sumPoints * 4.0 / points;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al procesar los resultados de los workers", e);
        }
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