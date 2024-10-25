import com.zeroc.Ice.Current;
import java.util.HashSet;
import java.util.Set;

public class PrinterI implements Demo.Printer {

    // Estructura para almacenar los workers conectados
    private Set<String> connectedWorkers = new HashSet<>();

    // Método para registrar workers
    @Override
    public synchronized void registerWorker(String workerID, Current current) {
        if (!connectedWorkers.contains(workerID)) {
            connectedWorkers.add(workerID);
            System.out.println("Worker registrado: " + workerID);
            System.out.println("Número total de workers conectados: " + connectedWorkers.size());
        }
    }

    // Método para calcular Pi
    @Override
    public double calculatePi(int points, Current current) {
        // Dividir el trabajo para los workers conectados
        divideWork(points);
        
        // Recolectar la información de los workers -> Puntos en el circulo
        int sumPoints = 0; // Suma de los puntos en el círculo
        
        // Calcular Pi
        double pi = sumPoints * 4.0 / points; 
        
        return pi;
    }

    // Método para manejar los resultados
    @Override
    public void collectResults(int pointsInCircle, Current current) {
        // Lógica para recolectar resultados de los workers
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
