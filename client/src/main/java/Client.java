import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Conectar con el servidor
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.MasterPrx printer = Demo.MasterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            // ExecutorService executor = Executors.newFixedThreadPool(2);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese un número para enviar al servidor: ");
            int numero = scanner.nextInt();
            printer.sendValue(numero); // Método que envía el valor al servidor
            System.out.println("Valor estimado de pi: " + printer.estimatePi(numero));

            // Mantener el cliente activo
            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}