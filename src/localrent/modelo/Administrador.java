package localrent.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclase de Usuario que representa al dueño o administrador
 * de uno o más inmuebles dentro del sistema LocalRent.
 *
 * Relación de AGREGACIÓN con Inmueble:
 * el Administrador gestiona una lista de inmuebles propios.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Administrador extends Usuario {

    // ─── Atributos propios ───────────────────────────────────────────────────
    private String empresaNombre;                  // Nombre de la empresa o razón social
    private List<Inmueble> inmuebles;              // Inmuebles que administra (agregación)

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Administrador.
     *
     * @param id            ID único
     * @param nombre        Nombre completo
     * @param correo        Correo electrónico
     * @param telefono      Teléfono de contacto
     * @param empresaNombre Nombre de la empresa o persona natural
     */
    public Administrador(String id, String nombre, String correo,
                         String telefono, String empresaNombre) {
        super(id, nombre, correo, telefono);
        this.empresaNombre = empresaNombre;
        this.inmuebles     = new ArrayList<>();
    }

    // ─── Rol ─────────────────────────────────────────────────────────────────
    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }

    // ─── Gestión de inmuebles ────────────────────────────────────────────────
    /**
     * Agrega un inmueble a la lista de propiedades administradas.
     *
     * @param inmueble Inmueble a agregar
     */
    public void agregarInmueble(Inmueble inmueble) {
        inmuebles.add(inmueble);
    }

    /**
     * Retorna la lista completa de inmuebles del administrador.
     *
     * @return Lista de inmuebles
     */
    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public String getEmpresaNombre()             { return empresaNombre; }
    public void setEmpresaNombre(String empresa) { this.empresaNombre = empresa; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + String.format(" | Empresa: %s | Inmuebles: %d",
                empresaNombre, inmuebles.size());
    }
}
