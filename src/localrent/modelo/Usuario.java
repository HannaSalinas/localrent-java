package localrent.modelo;

/**
 * Clase base abstracta que representa un usuario del sistema LocalRent.
 * Es la superclase de Administrador y Arrendatario.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public abstract class Usuario {

    // ─── Atributos ───────────────────────────────────────────────────────────
    protected String id;
    protected String nombre;
    protected String correo;
    protected String telefono;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo usuario con sus datos básicos.
     *
     * @param id       Identificador único del usuario
     * @param nombre   Nombre completo
     * @param correo   Correo electrónico
     * @param telefono Número de teléfono
     */
    public Usuario(String id, String nombre, String correo, String telefono) {
        this.id       = id;
        this.nombre   = nombre;
        this.correo   = correo;
        this.telefono = telefono;
    }

    // ─── Método abstracto ────────────────────────────────────────────────────
    /**
     * Cada subclase define su propio rol dentro del sistema.
     *
     * @return Descripción del rol del usuario
     */
    public abstract String getRol();

    // ─── Getters y Setters ───────────────────────────────────────────────────
    public String getId()        { return id; }
    public String getNombre()    { return nombre; }
    public String getCorreo()    { return correo; }
    public String getTelefono()  { return telefono; }

    public void setNombre(String nombre)     { this.nombre   = nombre; }
    public void setCorreo(String correo)     { this.correo   = correo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Tel: %s", getRol(), nombre, correo, telefono);
    }
}
