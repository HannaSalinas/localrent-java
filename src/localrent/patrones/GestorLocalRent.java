package localrent.patrones;

import localrent.modelo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║           PATRÓN DE DISEÑO: SINGLETON                        ║
 * ║                                                              ║
 * ║  GestorLocalRent es la base de datos simulada del sistema.   ║
 * ║  Solo puede existir UNA instancia en toda la aplicación.     ║
 * ║                                                              ║
 * ║  ¿Por qué Singleton aquí?                                    ║
 * ║  Porque todos los módulos del sistema deben compartir        ║
 * ║  la misma fuente de datos. Dos instancias causarían          ║
 * ║  inconsistencias (datos duplicados o perdidos).              ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class GestorLocalRent {

    // ─── Única instancia (Singleton) ─────────────────────────────────────────
    private static GestorLocalRent instancia;

    // ─── Repositorios en memoria ─────────────────────────────────────────────
    private List<Usuario>        usuarios;
    private List<Inmueble>       inmuebles;
    private List<Contrato>       contratos;
    private List<Pago>           pagos;

    // ─── Constructor PRIVADO (nadie puede hacer new GestorLocalRent()) ────────
    private GestorLocalRent() {
        usuarios   = new ArrayList<>();
        inmuebles  = new ArrayList<>();
        contratos  = new ArrayList<>();
        pagos      = new ArrayList<>();
    }

    // ─── Punto de acceso global ───────────────────────────────────────────────
    /**
     * Retorna la única instancia del gestor.
     * Si no existe, la crea (lazy initialization).
     *
     * @return Instancia única de GestorLocalRent
     */
    public static GestorLocalRent getInstancia() {
        if (instancia == null) {
            instancia = new GestorLocalRent();
        }
        return instancia;
    }

    // ─── CRUD Usuarios ────────────────────────────────────────────────────────
    public void agregarUsuario(Usuario u)         { usuarios.add(u); }
    public List<Usuario> getUsuarios()            { return usuarios; }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ─── CRUD Inmuebles ───────────────────────────────────────────────────────
    public void agregarInmueble(Inmueble i)        { inmuebles.add(i); }
    public List<Inmueble> getInmuebles()           { return inmuebles; }

    public Inmueble buscarInmueblePorId(String id) {
        return inmuebles.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ─── CRUD Contratos ───────────────────────────────────────────────────────
    public void agregarContrato(Contrato c)        { contratos.add(c); }
    public List<Contrato> getContratos()           { return contratos; }

    public Contrato buscarContratoPorId(String id) {
        return contratos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ─── CRUD Pagos ───────────────────────────────────────────────────────────
    public void agregarPago(Pago p)                { pagos.add(p); }
    public List<Pago> getPagos()                   { return pagos; }

    // ─── Resumen del sistema ──────────────────────────────────────────────────
    public void mostrarResumen() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     RESUMEN DEL SISTEMA LocalRent    ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Usuarios registrados  : %-12d║%n", usuarios.size());
        System.out.printf( "║  Inmuebles en sistema  : %-12d║%n", inmuebles.size());
        System.out.printf( "║  Contratos activos     : %-12d║%n",
                contratos.stream().filter(Contrato::isActivo).count());
        System.out.printf( "║  Pagos registrados     : %-12d║%n", pagos.size());
        System.out.println("╚══════════════════════════════════════╝");
    }
}
