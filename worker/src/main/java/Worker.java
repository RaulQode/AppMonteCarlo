import com.zeroc.Ice.Current;
import Demo.MasterPrx;
import Demo.WorkerPrx;

public class Worker {

    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.cfg")) {
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("WorkerAdapter");
            WorkerI worker = new WorkerI();
            com.zeroc.Ice.ObjectPrx proxy = adapter.add(worker, com.zeroc.Ice.Util.stringToIdentity("Worker" + java.util.UUID.randomUUID()));
            WorkerPrx workerPrx = WorkerPrx.checkedCast(proxy);
            adapter.activate();
            System.out.println("Worker activado y listo para recibir tareas.");

            MasterPrx master = MasterPrx.checkedCast(communicator.stringToProxy("Master:default -p 10000"));
            if (master == null) {
                System.err.println("No se pudo conectar con el master.");
                return;
            }

            master.addWorker(workerPrx);

            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}