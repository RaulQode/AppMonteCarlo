import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Demo.WorkerPrx;

public class Master {

    public static void main(String[] args) {
        // Crear un ExecutorService con un pool de 10 threads para manejar workers
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Crear el adaptador para el servidor
            com.zeroc.Ice.ObjectAdapter adapter =
                    communicator.createObjectAdapterWithEndpoints("MasterAdapter", "default -p 10000");

            // Crear instancia de la clase MasterI (servicio principal)
            MasterI masterService = new MasterI();

            // Asociar el objeto con el adaptador
            adapter.add(masterService, com.zeroc.Ice.Util.stringToIdentity("Master"));
            adapter.activate();

            System.out.println("Master listo y escuchando en el puerto 10000...");

            // Ejecutar en un hilo independiente para mantener el servidor activo
            executor.submit(() -> {
                // Mantener el servidor activo
                while (true) {
                    try {
                        // Mantener el hilo en espera, el trabajo se maneja a través de métodos remotos
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });

            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}