module Demo {
    interface Master {
        // Calcula y retorna el valor estimado de Pi
        double estimatePi(int points);

        //Para probar conexione
        void printString(string s);

        // Registrar cuando un worker se conectar 
        void registerWorker(string workerId);

        void sendValue(int value);
    }

    interface Worker {
        int calculatePointsInCircle(int points);
    }
    
    interface Client {
        // Envía una petición al maestro para calcular Pi
        void requestPiEstimation(int totalPoints);
    }
}