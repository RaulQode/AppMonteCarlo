import com.zeroc.Ice.Current;


public class WorkerI implements Demo.Worker {
    @Override
    public int calculatePointsInCircle(int points, Current current) {
        int cont = 0;

        for(int i = 0; i < points; i++){
            double x = Math.random();
            double y = Math.random();
            if(x*x + y*y <= 1){
                cont++;
            }
        }
        return cont;
    }
}
