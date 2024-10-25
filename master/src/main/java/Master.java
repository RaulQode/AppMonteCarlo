import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Master {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {

            // Crear el adaptador para el servidor
            com.zeroc.Ice.ObjectAdapter adapter =
                communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10000");

            // Instancia del servicio PrinterI que manejará a los workers conectados
            PrinterI printerServant = new PrinterI();

            // Asociar el objeto con el adaptador
            adapter.add(printerServant, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));

            // Activar el adaptador
            adapter.activate();
            System.out.println("Master listo y escuchando en el puerto 10000...");

            executor.submit(() -> {
                while (true) {
                    try {
                        Thread.sleep(5000); // Esperar 5 segundos
                        int valorRecibido = printerServant.getReceivedValue();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break; // Salir del bucle si el hilo es interrumpido
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
