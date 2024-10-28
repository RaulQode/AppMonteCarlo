module Demo {
    interface Worker {
        // Método que calcula los puntos en el círculo y retorna el conteo
        int calculatePointsInCircle(int points);
    }

    interface Master {
        // Calcula y retorna el valor estimado de Pi basado en puntos proporcionados
        double estimatePi(int points);

        // Prueba de conexión, imprime una cadena recibida
        void printString(string s);

        // Método para enviar un valor
        void sendValue(int value);

        void addWorker(Worker* worker);
    }

    interface Client {
        // Envía una petición al maestro para calcular Pi con el total de puntos especificado
        void requestPiEstimation(int totalPoints);
    }
}