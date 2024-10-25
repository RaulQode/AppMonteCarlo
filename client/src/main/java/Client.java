import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Conectar con el servidor
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            ExecutorService executor = Executors.newFixedThreadPool(2);

            // Hilo para enviar datos al servidor
            executor.submit(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Ingrese un número para enviar al servidor: ");
                    int numero = scanner.nextInt();
                    printer.sendValue(numero); // Método que envía el valor al servidor
                    System.out.println("Número enviado: " + numero);
                }
            });
            
            // Aqui recibe el cliente la respuesta del valor de pi
            executor.submit(() -> {
                while (true) {
                
                }
            });

            // Mantener el cliente activo
            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}