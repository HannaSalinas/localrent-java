package localrent.modelo;

/**
 * Subclase de Usuario que representa a la persona que arrienda
 * un inmueble (local o apartamento) dentro del sistema LocalRent.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Arrendatario extends Usuario {

    // ─── Atributos propios ───────────────────────────────────────────────────
    private String tipoDocumento;    // CC, NIT, CE, Pasaporte
    private String numeroDocumento;
    private double ingresosMensuales;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Arrendatario.
     *
     * @param id                 ID único
     * @param nombre             Nombre completo
     * @param correo             Correo electrónico
     * @param telefono           Teléfono de contacto
     * @param tipoDocumento      Tipo de documento (CC, NIT, etc.)
     * @param numeroDocumento    Número de documento de identidad
     * @param ingresosMensuales  Ingresos mensuales declarados
     */
    public Arrendatario(String id, String nombre, String correo, String telefono,
                        String tipoDocumento, String numeroDocumento,
                        double ingresosMensuales) {
        super(id, nombre, correo, telefono);
        this.tipoDocumento     = tipoDocumento;
        this.numeroDocumento   = numeroDocumento;
        this.ingresosMensuales = ingresosMensuales;
    }

    // ─── Rol ─────────────────────────────────────────────────────────────────
    @Override
    public String getRol() {
        return "ARRENDATARIO";
    }

    // ─── Validación de capacidad de pago ─────────────────────────────────────
    /**
     * Verifica si el arrendatario puede costear un canon dado.
     * Regla estándar: el canon no debe superar el 30% de los ingresos.
     *
     * @param canonMensual Valor del arriendo mensual
     * @return true si puede pagar, false si no
     */
    public boolean puedeCostearse(double canonMensual) {
        return canonMensual <= (ingresosMensuales * 0.30);
    }

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public String getTipoDocumento()                       { return tipoDocumento; }
    public String getNumeroDocumento()                     { return numeroDocumento; }
    public double getIngresosMensuales()                   { return ingresosMensuales; }
    public void setIngresosMensuales(double ingresos)      { this.ingresosMensuales = ingresos; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + String.format(" | Doc: %s %s | Ingresos: $%.0f",
                tipoDocumento, numeroDocumento, ingresosMensuales);
    }
}
