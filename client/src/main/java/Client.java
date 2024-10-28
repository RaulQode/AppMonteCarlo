import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Conectar con el servidor
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");
            Demo.MasterPrx printer = Demo.MasterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Que modo de uso desea?");
            System.out.println("1. Modo Singular");
            System.out.println("2. Modo test");
            System.out.print("Ingrese el número de la opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            boolean flag = true;
            int numero = 0;
            int count = 0;

            switch (option){
                case 1:
                    while (flag) {
                        System.out.print("Ingrese un número de puntos para enviar al servidor: ");
                        numero = scanner.nextInt();
                        scanner.nextLine();
                        printer.sendValue(numero); // Método que envía el valor al servidor
                        long startTime = System.currentTimeMillis();
                        double pi = printer.estimatePi(numero);
                        System.out.println("Valor estimado de pi: " + pi);
                        long endTime = System.currentTimeMillis();
                        System.out.println("Tiempo total: " + (endTime - startTime) + "ms");
                        if (pi == 0) {
                            communicator.waitForShutdown();
                        }
                        System.out.println("Desea continuar? (s/n)");
                        String continuar = scanner.nextLine();
                        if (!continuar.equals("s")){
                            flag = false;
                        }
                    }
                    break;
                case 2:
                    PrintStream out = new PrintStream(new FileOutputStream("output.txt",true));
                    PrintStream consoleOut = System.out;
                    while (flag){

                        System.out.print("Ingrese un número de puntos para enviar al servidor: ");
                        numero = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Ingrese el numero de cantidad de veces que desea enviar el numero de puntos al servidor: ");
                        count = scanner.nextInt();
                        scanner.nextLine();

                        System.setOut(out);

                        System.out.println("Begin - " + numero + " points");

                        for (int i = 0; i < count; i++) {
                            printer.sendValue(numero);

                            long startTime = System.currentTimeMillis();
                            double pi = printer.estimatePi(numero);

                            System.out.println("Valor estimado de pi: " + pi);
                            long endTime = System.currentTimeMillis();
                            System.out.println("Tiempo total: " + (endTime - startTime) + "ms");

                            if (pi == 0) {
                                communicator.waitForShutdown();
                            }
                        }
                        System.out.println();

                        System.setOut(consoleOut);

                        System.out.println("Desea continuar? (s/n)");
                        String continuar = scanner.nextLine();
                        if (!continuar.equals("s")){
                            flag = false;
                        }
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
                    return;
            }

            communicator.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}