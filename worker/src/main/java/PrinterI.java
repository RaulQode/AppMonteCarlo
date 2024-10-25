import com.zeroc.Ice.Current;
public class PrinterI implements  Demo.Printer{
    @Override
    public void collectResults(int pointsInCircle, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collectResults'");
    }

    @Override
    public double calculatePi(int points, Current current) {
        int cont = 0;

        for(int i = 0; i < points; i++){
            double x = Math.random();
            double y = Math.random();
            if(x*x + y*y <= 1){
                cont++;
                // collectResults(1, current);
            }
        }

        collectResults(cont, current);

        return cont;
    }

    @Override
    public void printString(String s, Current current){

    }

    @Override
    public void registerWorker(String workerID, Current current){
        
    }
}
