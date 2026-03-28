package localrent.modelo;

/**
 * Subclase de Inmueble que representa un apartamento residencial arrendable.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Apartamento extends Inmueble {

    // ─── Atributos propios ───────────────────────────────────────────────────
    private int     numeroPiso;
    private int     habitaciones;
    private int     banos;
    private boolean tieneParqueadero;
    private boolean permiteMascotas;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Apartamento residencial.
     *
     * @param id                ID único
     * @param direccion         Dirección completa
     * @param ciudad            Ciudad
     * @param areaM2            Área en metros cuadrados
     * @param canonMensual      Canon mensual en COP
     * @param descripcion       Descripción del apartamento
     * @param numeroPiso        Número de piso
     * @param habitaciones      Número de habitaciones
     * @param banos             Número de baños
     * @param tieneParqueadero  Indica si incluye parqueadero
     * @param permiteMascotas   Indica si se permiten mascotas
     */
    public Apartamento(String id, String direccion, String ciudad,
                       double areaM2, double canonMensual, String descripcion,
                       int numeroPiso, int habitaciones, int banos,
                       boolean tieneParqueadero, boolean permiteMascotas) {
        super(id, direccion, ciudad, areaM2, canonMensual, descripcion);
        this.numeroPiso        = numeroPiso;
        this.habitaciones      = habitaciones;
        this.banos             = banos;
        this.tieneParqueadero  = tieneParqueadero;
        this.permiteMascotas   = permiteMascotas;
    }

    // ─── Tipo ─────────────────────────────────────────────────────────────────
    @Override
    public String getTipo() {
        return "APARTAMENTO";
    }

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public int     getNumeroPiso()                          { return numeroPiso; }
    public int     getHabitaciones()                        { return habitaciones; }
    public int     getBanos()                               { return banos; }
    public boolean isTieneParqueadero()                     { return tieneParqueadero; }
    public boolean isPermiteMascotas()                      { return permiteMascotas; }
    public void    setTieneParqueadero(boolean parqueadero) { this.tieneParqueadero = parqueadero; }
    public void    setPermiteMascotas(boolean mascotas)     { this.permiteMascotas  = mascotas; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Piso: %d | Hab: %d | Baños: %d | Parqueadero: %s | Mascotas: %s",
                numeroPiso, habitaciones, banos,
                tieneParqueadero ? "Sí" : "No",
                permiteMascotas  ? "Sí" : "No");
    }
}
