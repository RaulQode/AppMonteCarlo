import com.zeroc.Ice.Current;
import java.util.Scanner;

public class ClientI implements Demo.Client{

    @Override
    public void requestPiEstimation(int totalPoints, Current current){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el valor para la estimación de Pi: ");
        int estimation = scanner.nextInt();
        scanner.close();
        System.out.println("Valor ingresado por el cliente: " + estimation);
    }
}
