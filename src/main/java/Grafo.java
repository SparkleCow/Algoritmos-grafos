import java.util.*;
import java.util.stream.Collectors;

public class Grafo {
    final Double cons = 1500.0/7;

    private Map<String, Ciudad> ciudades;

    public Grafo() {
        this.ciudades = new HashMap<>();
    }

    public void agregarCiudad(String nombreCiudad, Double x, Double y) {
        ciudades.put(nombreCiudad, new Ciudad(nombreCiudad, x, y));
    }

    public void agregarConexion(String nombreCiudadOrigen, String nombreCiudadDestino, Double distancia) {
        Ciudad ciudadOrigen = ciudades.get(nombreCiudadOrigen);
        Ciudad ciudadDestino = ciudades.get(nombreCiudadDestino);

        if(ciudadOrigen != null && ciudadDestino != null) {
            ciudadOrigen.agregarConexion(ciudadDestino, distancia);
        }
    }

    public List<Conexion> encontrarRutaMinimaDijkstra(String nombreCiudadOrigen, String nombreCiudadDestino) {

        Ciudad ciudadOrigen = ciudades.get(nombreCiudadOrigen);
        Ciudad ciudadDestino = ciudades.get(nombreCiudadDestino);

        if (ciudadOrigen != null && ciudadDestino != null) {
            Map<Ciudad, Double> distancias = new HashMap<>();
            Map<Ciudad, Ciudad> rutaAnterior = new HashMap<>();
            PriorityQueue<Ciudad> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));

            for (Ciudad ciudad : ciudades.values()) {
                distancias.put(ciudad, ciudad.equals(ciudadOrigen) ? 0 : Double.MAX_VALUE);
                rutaAnterior.put(ciudad, null);
                colaPrioridad.add(ciudad);
            }

            while (!colaPrioridad.isEmpty()) {
                Ciudad actual = colaPrioridad.poll();

                for (Conexion conexion : actual.getConexiones()) {
                    Double nuevaDistancia = distancias.get(actual) + conexion.getDistancia();
                    if (nuevaDistancia < distancias.get(conexion.getCiudad())) {
                        distancias.put(conexion.getCiudad(), nuevaDistancia);
                        rutaAnterior.put(conexion.getCiudad(), actual);
                        colaPrioridad.add(conexion.getCiudad());
                    }
                }
            }

            // Reconstruir la ruta
            List<Conexion> ruta = new ArrayList<>();
            Ciudad ciudadActual = ciudadDestino;
            while (ciudadActual != null) {
                Ciudad ciudadAnterior = rutaAnterior.get(ciudadActual);
                if (ciudadAnterior != null) {
                    Double distancia = distancias.get(ciudadActual) - distancias.get(ciudadAnterior);
                    ruta.add(0, new Conexion(ciudadAnterior, distancia));
                }
                ciudadActual = ciudadAnterior;
            }
            return ruta;
        }
        return Collections.emptyList();
    }

    //Metodo para hallar la distancia heuristica (Distancia lineal) entre dos ciudades
    public Double distanciaHeuristica(Ciudad ciudadOrigen, Ciudad ciudadDestino){
        return Math.sqrt(Math.pow(ciudadDestino.getCoordenadaX()-ciudadOrigen.getCoordenadaX(),2 )
                +Math.pow(ciudadDestino.getCoordenadaY()-ciudadOrigen.getCoordenadaY(),2 ));
    }

    public List<Conexion> encontrarRutaAStar(String nombreCiudadOrigen, String nombreCiudadDestino) {
        Ciudad ciudadOrigen = ciudades.get(nombreCiudadOrigen);
        Ciudad ciudadDestino = ciudades.get(nombreCiudadDestino);

        if (ciudadOrigen != null && ciudadDestino != null) {
            Map<Ciudad, Double> costosAcumulados = new HashMap<>();
            Map<Ciudad, Double> estimadosRestantes = new HashMap<>();
            Map<Ciudad, Ciudad> rutaAnterior = new HashMap<>();
            PriorityQueue<Ciudad> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(x -> {
                Double distanciaEstimada = estimadosRestantes.get(x);
                return distanciaEstimada != null ? -distanciaEstimada : Double.POSITIVE_INFINITY;
            }));

            for (Ciudad ciudad : ciudades.values()) {
                costosAcumulados.put(ciudad, ciudad.equals(ciudadOrigen) ? 0.0 : Double.MAX_VALUE);
                estimadosRestantes.put(ciudad, distanciaHeuristica(ciudad, ciudadDestino));
                rutaAnterior.put(ciudad, null);
                colaPrioridad.add(ciudad);
            }

            while (!colaPrioridad.isEmpty()) {

                Ciudad actual = colaPrioridad.poll();
                if (actual.equals(ciudadDestino)) {
                    // Se ha alcanzado el destino, reconstruir la ruta
                    return reconstruirRuta(ciudadDestino, rutaAnterior);
                }
                for (Conexion conexion : actual.getConexiones()) {
                    double nuevoCosto = costosAcumulados.get(actual) + conexion.getDistancia();
                    if (nuevoCosto < costosAcumulados.get(conexion.getCiudad())) {
                        costosAcumulados.put(conexion.getCiudad(), nuevoCosto);
                        estimadosRestantes.put(conexion.getCiudad(), nuevoCosto + distanciaHeuristica(conexion.getCiudad(), ciudadDestino));
                        rutaAnterior.put(conexion.getCiudad(), actual);
                        colaPrioridad.add(conexion.getCiudad());
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Conexion> reconstruirRuta(Ciudad destino, Map<Ciudad, Ciudad> rutaAnterior) {
        List<Conexion> ruta = new ArrayList<>();
        Ciudad ciudadActual = destino;

        while (rutaAnterior.get(ciudadActual) != null) {
            Ciudad ciudadAnterior = rutaAnterior.get(ciudadActual);
            for (Conexion conexion : ciudadAnterior.getConexiones()) {
                if (conexion.getCiudad().equals(ciudadActual)) {
                    ruta.add(0, new Conexion(ciudadAnterior, conexion.getDistancia()));
                    break;
                }
            }
            ciudadActual = ciudadAnterior;
        }
        return ruta;
    }

    public Ciudad getCiudad(String nombreCiudad) {
        return ciudades.get(nombreCiudad);
    }
}


