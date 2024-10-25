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
        double pi = 0;
        // Implementa la lógica de cálculo de Pi aquí
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

    // Método opcional para eliminar workers si se desconectan
    public synchronized void removeWorker(String workerID) {
        connectedWorkers.remove(workerID);
        System.out.println("Worker desconectado: " + workerID);
    }
}
