import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ciudad{
    private String nombre;
    private List<Conexion> conexiones;
    private Double coordenadaX;
    private Double coordenadaY;


    public Ciudad(String nombre, Double x, Double y) {
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.nombre = nombre;
        this.conexiones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public Double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(Double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public Double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(Double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public void agregarConexion(Ciudad ciudad, Double distancia) {
        Conexion conexion = new Conexion(ciudad, distancia);
        conexiones.add(conexion);
        ciudad.getConexiones().add(new Conexion(this, distancia));
    }

    public Double getDistanciaDirecta(Ciudad otraCiudad) {
        for (Conexion conexion : conexiones) {
            if (conexion.getCiudad().equals(otraCiudad)) {
                return conexion.getDistancia();
            }
        }
        return Double.MAX_VALUE; // Si no hay conexión directa, retornar un valor alto
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(getNombre(), ciudad.getNombre()) && Objects.equals(getConexiones(), ciudad.getConexiones()) && Objects.equals(getCoordenadaX(), ciudad.getCoordenadaX()) && Objects.equals(getCoordenadaY(), ciudad.getCoordenadaY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getConexiones(), getCoordenadaX(), getCoordenadaY());
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "nombre='" + nombre + '\'' +
                ", coordenadaX=" + coordenadaX +
                ", coordenadaY=" + coordenadaY +
                '}';
    }
}
