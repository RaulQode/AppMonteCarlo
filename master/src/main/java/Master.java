import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Master
{
    public static void main(String[] args)
    {
        // Crear un pool de hilos para manejar las conexiones de los workers
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Inicializar el comunicador de ICE
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {

            // Crear el adaptador para el servidor
            com.zeroc.Ice.ObjectAdapter adapter =
                communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10000");

            // Instancia del servicio PrinterI que manejará a los workers conectados
            PrinterI printerServant = new PrinterI();

            // Asociar el objeto con el adaptador
            adapter.add(printerServant, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));

            // Activar el adaptador para que empiece a escuchar conexiones
            adapter.activate();

            System.out.println(printerServant.getWorkerCount());

            // Loop principal del servidor: escucha y maneja workers
            // Aquí podrías tener lógica adicional para monitorear workers
            // mientras el servidor está corriendo
            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
