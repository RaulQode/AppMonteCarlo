import java.util.Scanner;

public class Worker {

    public static void main(String[] args)
    {
        // Inicializa el worker ID (puede ser dinámico o estático)
        String workerID = "Worker" + java.util.UUID.randomUUID().toString(); // Worker con ID único

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {

            // Conectarse al servidor (Master) a través del proxy
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            // Enviar un mensaje de registro al servidor (Master)
            printer.registerWorker(workerID);  // Registro del worker con su ID
            System.out.println("Worker registrado con ID: " + workerID);

            // Crear un Scanner para leer mensajes del usuario
            Scanner scanner = new Scanner(System.in);
            String message = "";

            // Mantener la conexión abierta y permitir que el worker envíe múltiples mensajes
            while (true) {
                System.out.print("Escribe un mensaje ('exit' para salir): ");
                message = scanner.nextLine();  // Leer el mensaje del usuario

                // Si el usuario escribe 'exit', terminar la conexión
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Desconectando...");
                    break;
                }

                // Enviar el mensaje al servidor (Master) usando printString
                printer.printString(workerID + ": " + message);
            }

            // Al salir del bucle, el worker se desconecta
            System.out.println("Worker desconectado.");

        } catch (Exception e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
