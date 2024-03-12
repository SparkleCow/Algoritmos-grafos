import java.util.List;

public class Main {

    public static void main(String[] args) {

        final Double cons = 1500.0/7;

        Grafo grafo = new Grafo();

        // Agregar ciudades
        grafo.agregarCiudad("Bogota",3.0*cons,4.25*cons);
        grafo.agregarCiudad("Medellin",2.25*cons,3.25*cons);
        grafo.agregarCiudad("Cali",1.75*cons,4.75*cons);
        grafo.agregarCiudad("Barranquilla",2.61*cons,1.0*cons);
        grafo.agregarCiudad("Girardot",2.75*cons,4.35*cons);
        grafo.agregarCiudad("Anapoima",2.8*cons,4.3*cons);
        grafo.agregarCiudad("Villavicencio",3.3*cons,4.6*cons);
        grafo.agregarCiudad("Armenia",2.2*cons,4.3*cons);
        grafo.agregarCiudad("Santa Marta",2.95*cons,0.95*cons);

        // Agregar conexiones con distancias a las ciudades
        grafo.agregarConexion("Bogota", "Medellin", 1.96504*cons);
        grafo.agregarConexion("Bogota", "Villavicencio", 0.55286*cons);
        grafo.agregarConexion("Bogota", "Anapoima", 0.43456*cons);
        grafo.agregarConexion("Medellin", "Cali", 2.07494*cons);
        grafo.agregarConexion("Medellin", "Barranquilla", 3.27166*cons);
        grafo.agregarConexion("Barranquilla", "Santa Marta", 0.45943*cons);
        grafo.agregarConexion("Cali", "Santa Marta", 5.76991*cons);
        grafo.agregarConexion("Villavicencio", "Santa Marta", 5.01531*cons);
        grafo.agregarConexion("Anapoima", "Santa Marta", 4.56446*cons);

        //Hallar ruta entre ciudades
        String ciudadOrigen = "Villavicencio";
        String ciudadDestino = "Santa Marta";

        //Ruta minima con algoritmo Djisktra y A*
        List<Conexion> rutaAEstrella = grafo.encontrarRutaAStar(ciudadOrigen, ciudadDestino);
        List<Conexion> rutaDjisktra = grafo.encontrarRutaMinimaDijkstra(ciudadOrigen, ciudadDestino);

        System.out.println("====================================");
        System.out.println("Algoritmo Dijsktra");
        if (!rutaDjisktra.isEmpty()) {
            System.out.println("Distancia total: " + rutaDjisktra.stream().mapToDouble(Conexion::getDistancia).sum() + " km");
            System.out.println("Ruta: " + rutaDjisktra+" - Destino: "+ciudadDestino);
        } else {
            System.out.println("No se encontró una ruta entre " + ciudadOrigen + " y " + ciudadDestino);
        }

        System.out.println("====================================");
        System.out.println("Algoritmo A*");
        if (!rutaAEstrella.isEmpty()) {
            System.out.println("Distancia total: "+ rutaAEstrella.stream().mapToDouble(Conexion::getDistancia).sum() + " km");
            System.out.println("Ruta: " + rutaAEstrella+" - Destino: "+ciudadDestino);
        } else {
            System.out.println("No se encontró una ruta entre " + ciudadOrigen + " y " + ciudadDestino);
        }
    }
}

