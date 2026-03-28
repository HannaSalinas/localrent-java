package localrent.modelo;

/**
 * Clase abstracta que representa un inmueble arrendable en LocalRent.
 * Superclase de Local y Apartamento.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public abstract class Inmueble {

    // ─── Atributos ───────────────────────────────────────────────────────────
    protected String  id;
    protected String  direccion;
    protected String  ciudad;
    protected double  areaM2;
    protected double  canonMensual;
    protected boolean disponible;
    protected String  descripcion;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Inmueble.
     *
     * @param id           ID único del inmueble
     * @param direccion    Dirección completa
     * @param ciudad       Ciudad donde se ubica
     * @param areaM2       Área en metros cuadrados
     * @param canonMensual Valor del arriendo mensual en pesos COP
     * @param descripcion  Descripción del inmueble
     */
    public Inmueble(String id, String direccion, String ciudad,
                    double areaM2, double canonMensual, String descripcion) {
        this.id           = id;
        this.direccion    = direccion;
        this.ciudad       = ciudad;
        this.areaM2       = areaM2;
        this.canonMensual = canonMensual;
        this.descripcion  = descripcion;
        this.disponible   = true;  // Por defecto disponible
    }

    // ─── Método abstracto ────────────────────────────────────────────────────
    /**
     * Cada subclase define su tipo específico de inmueble.
     *
     * @return Tipo de inmueble (LOCAL o APARTAMENTO)
     */
    public abstract String getTipo();

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public String  getId()           { return id; }
    public String  getDireccion()    { return direccion; }
    public String  getCiudad()       { return ciudad; }
    public double  getAreaM2()       { return areaM2; }
    public double  getCanonMensual() { return canonMensual; }
    public boolean isDisponible()    { return disponible; }
    public String  getDescripcion()  { return descripcion; }

    public void setDisponible(boolean disponible)    { this.disponible   = disponible; }
    public void setCanonMensual(double canonMensual) { this.canonMensual = canonMensual; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("[%s] ID: %s | %s, %s | %.1f m² | $%.0f/mes | %s",
                getTipo(), id, direccion, ciudad, areaM2, canonMensual,
                disponible ? "DISPONIBLE" : "OCUPADO");
    }
}
