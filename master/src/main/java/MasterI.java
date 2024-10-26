import com.zeroc.Ice.Current;
import java.util.HashSet;
import java.util.Set;

public class MasterI implements Demo.Master {

    // Estructura para almacenar los workers conectados
    private Set<String> connectedWorkers = new HashSet<>();
    private int receivedValue; // Variable para almacenar el valor recibido

    // Método para registrar workers
    @Override
    public synchronized void registerWorker(String workerId, Current current) {
        if (!connectedWorkers.contains(workerId)) {
            connectedWorkers.add(workerId);
            System.out.println("Worker registrado: " + workerId);
            System.out.println("Número total de workers conectados: " + connectedWorkers.size());
        }
    }

    @Override
    public void sendValue(int value, Current current) {
        receivedValue = value;
        System.out.println("Valor recibido desde el cliente: " + receivedValue);
    }

    // Método para calcular Pi
    @Override
    public double estimatePi(int points, Current current) {
        // Dividir el trabajo para los workers conectados
        divideWork(points);
        
        // Recolectar la información de los workers -> Puntos en el circulo
        int sumPoints = 0; // Suma de los puntos en el círculo
        
        // Calcular Pi
        double pi = sumPoints * 4.0 / points; 
        
        return pi;
    }

    // Método para imprimir un string
    @Override
    public void printString(String s, Current current) {
        System.out.println(s);
    }

    // Método para contar cuántos workers están conectados
    public synchronized int getWorkerCount() {
        return connectedWorkers.size();
    }

    public int getReceivedValue() {
        return receivedValue;
    }

    // Método para dividir el trabajo de los workers conectados y enviarles puntos a calcular
    public synchronized int[] divideWork(int points) {
        int workers = getWorkerCount();
        int pointsPerWorker = points / workers;
        int remainingPoints = points % workers;
        int[] totalPoints = new int[workers];

        for (String workerID : connectedWorkers) {
            int pointsToSend = pointsPerWorker;
            if (remainingPoints > 0) {
                pointsToSend++;
                remainingPoints--;
            }
        }
        return totalPoints;
    }

    // Método opcional para eliminar workers si se desconectan
    public synchronized void removeWorker(String workerID) {
        connectedWorkers.remove(workerID);
        System.out.println("Worker desconectado: " + workerID);
    }
}
