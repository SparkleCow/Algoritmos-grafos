public class Conexion {
    private Ciudad ciudad;
    private Double distancia;

    public Conexion(Ciudad ciudad, Double distancia) {
        this.ciudad = ciudad;
        this.distancia = distancia;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public Double getDistancia() {
        return distancia;
    }

    @Override
    public String toString() {
        return ciudad.getNombre() + " (" + distancia + " km)";
    }
}
