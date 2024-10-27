import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Conectar con el servidor
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");
            Demo.MasterPrx printer = Demo.MasterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            // ExecutorService executor = Executors.newFixedThreadPool(2);
            boolean coninuity = true;
            while (coninuity) {

                Scanner scanner = new Scanner(System.in);
                System.out.print("Ingrese un número de puntos para enviar al servidor: ");
                int numero = scanner.nextInt();
                scanner.nextLine();
                printer.sendValue(numero); // Método que envía el valor al servidor
                long startTime = System.currentTimeMillis();
                double pi = printer.estimatePi(numero);
                System.out.println("Valor estimado de pi: " + pi);
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo total: " + (endTime - startTime) + "ms");
                System.out.print("Desea continuar? (s/n): ");
                String continuar = scanner.nextLine();
                if (continuar.equals("n")) {
                    coninuity = false;
                }
                if (pi == 0) {
                    communicator.waitForShutdown();
                }
            }

            communicator.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}