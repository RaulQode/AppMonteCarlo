import com.zeroc.Ice.Current;
public class PrinterI implements  Demo.Printer{



    @Override
    public void collectResults(int pointsInCircle, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collectResults'");
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
}
