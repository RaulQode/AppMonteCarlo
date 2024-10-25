import com.zeroc.Ice.Current;
import java.util.Scanner;

public class PrinterI implements Demo.Printer{

    @Override
    public void sendValue(int value, com.zeroc.Ice.Current current) {
    
    }

    @Override
    public void collectResults(int pointsInCircle, Current current) {
        
    }


    @Override
    public double calculatePi(int points, Current current) {
        throw new UnsupportedOperationException("Unimplemented method 'calculatePi'");
    }

    @Override
    public void printString(String s, Current current){

    }

    @Override
    public void registerWorker(String workerID, Current current){
        
    }

    @Override
    public int requestPiEstimation(Current current){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el valor para la estimación de Pi: ");
        int estimation = scanner.nextInt();
        scanner.close();
        System.out.println("Valor ingresado por el cliente: " + estimation);
        return estimation;
    }
    
}