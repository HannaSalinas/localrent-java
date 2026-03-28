package localrent.modelo;

/**
 * Subclase de Inmueble que representa un local comercial arrendable.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Local extends Inmueble {

    // ─── Atributos propios ───────────────────────────────────────────────────
    private String  ubicacionEnCentro;   // Ej: "Piso 1", "Pasillo A", "Esquina"
    private boolean tieneVitrina;
    private int     aforoMaximo;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Local comercial.
     *
     * @param id                 ID único
     * @param direccion          Dirección del centro comercial o calle
     * @param ciudad             Ciudad
     * @param areaM2             Área en metros cuadrados
     * @param canonMensual       Canon mensual en COP
     * @param descripcion        Descripción del local
     * @param ubicacionEnCentro  Ubicación dentro del edificio/centro
     * @param tieneVitrina       Indica si tiene vitrina al público
     * @param aforoMaximo        Aforo máximo permitido
     */
    public Local(String id, String direccion, String ciudad,
                 double areaM2, double canonMensual, String descripcion,
                 String ubicacionEnCentro, boolean tieneVitrina, int aforoMaximo) {
        super(id, direccion, ciudad, areaM2, canonMensual, descripcion);
        this.ubicacionEnCentro = ubicacionEnCentro;
        this.tieneVitrina      = tieneVitrina;
        this.aforoMaximo       = aforoMaximo;
    }

    // ─── Tipo ─────────────────────────────────────────────────────────────────
    @Override
    public String getTipo() {
        return "LOCAL";
    }

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public String  getUbicacionEnCentro()              { return ubicacionEnCentro; }
    public boolean isTieneVitrina()                    { return tieneVitrina; }
    public int     getAforoMaximo()                    { return aforoMaximo; }
    public void    setTieneVitrina(boolean vitrina)    { this.tieneVitrina = vitrina; }
    public void    setAforoMaximo(int aforo)           { this.aforoMaximo  = aforo; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Ubicación: %s | Vitrina: %s | Aforo: %d personas",
                ubicacionEnCentro,
                tieneVitrina ? "Sí" : "No",
                aforoMaximo);
    }
}
