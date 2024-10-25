module Demo {

    // Interfaz para calcular PI
    interface Printer {        
        // Recoge los resultados reportados por los trabajadores
        void collectResults(int pointsInCircle);
        
        // Calcula y retorna el valor estimado de Pi
        double calculatePi(int points);
    }
    
    // Interfaz para el Cliente
    interface Client {
        // Envía una petición al maestro para calcular Pi
        double requestPiEstimation(int totalPoints);
    }
}