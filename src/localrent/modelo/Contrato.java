package localrent.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un contrato de arriendo en LocalRent.
 *
 * Implementa COMPOSICIÓN con Pago (un contrato "posee" sus pagos).
 * Implementa ASOCIACIÓN con Arrendatario e Inmueble.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Contrato {

    // ─── Atributos ───────────────────────────────────────────────────────────
    private String       id;
    private Arrendatario arrendatario;    // Asociación
    private Inmueble     inmueble;        // Asociación
    private LocalDate    fechaInicio;
    private LocalDate    fechaFin;
    private double       canonMensual;
    private boolean      activo;
    private List<Pago>   pagos;           // Composición: los pagos pertenecen al contrato

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo Contrato de arriendo.
     *
     * @param id            ID único del contrato
     * @param arrendatario  Persona que arrienda
     * @param inmueble      Inmueble arrendado
     * @param fechaInicio   Fecha de inicio del contrato
     * @param fechaFin      Fecha de finalización del contrato
     * @param canonMensual  Valor mensual pactado en COP
     */
    public Contrato(String id, Arrendatario arrendatario, Inmueble inmueble,
                    LocalDate fechaInicio, LocalDate fechaFin, double canonMensual) {
        this.id            = id;
        this.arrendatario  = arrendatario;
        this.inmueble      = inmueble;
        this.fechaInicio   = fechaInicio;
        this.fechaFin      = fechaFin;
        this.canonMensual  = canonMensual;
        this.activo        = true;
        this.pagos         = new ArrayList<>();

        // Al crear el contrato, el inmueble pasa a estar ocupado
        inmueble.setDisponible(false);
    }

    // ─── Gestión de pagos ────────────────────────────────────────────────────
    /**
     * Registra un nuevo pago dentro del contrato.
     *
     * @param pago Objeto Pago a registrar
     */
    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }

    /**
     * Verifica si el contrato tiene mora.
     * Hay mora si el último pago registrado tiene más de 35 días
     * (30 días del mes + 5 días hábiles de gracia).
     *
     * @return true si hay mora, false si está al día
     */
    public boolean tieneMora() {
        if (pagos.isEmpty()) {
            // Sin ningún pago y el contrato lleva más de 35 días activo
            long diasDesdeInicio = ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());
            return diasDesdeInicio > 35;
        }
        // Verificar el último pago registrado
        Pago ultimoPago = pagos.get(pagos.size() - 1);
        long diasDesdeUltimoPago = ChronoUnit.DAYS.between(
                ultimoPago.getFechaPago(), LocalDate.now());
        return diasDesdeUltimoPago > 35;
    }

    /**
     * Genera una alerta de mora si aplica.
     *
     * @return Mensaje de alerta o mensaje de pago al día
     */
    public String generarAlertaMora() {
        if (tieneMora()) {
            return String.format(
                "⚠️  ALERTA MORA — Contrato %s | Arrendatario: %s | Inmueble: %s | Canon: $%.0f",
                id, arrendatario.getNombre(), inmueble.getDireccion(), canonMensual);
        }
        return String.format("✅  Al día — Contrato %s | %s", id, arrendatario.getNombre());
    }

    /**
     * Finaliza el contrato y libera el inmueble.
     */
    public void finalizarContrato() {
        this.activo = false;
        inmueble.setDisponible(true);
    }

    // ─── Getters ─────────────────────────────────────────────────────────────
    public String       getId()            { return id; }
    public Arrendatario getArrendatario()  { return arrendatario; }
    public Inmueble     getInmueble()      { return inmueble; }
    public LocalDate    getFechaInicio()   { return fechaInicio; }
    public LocalDate    getFechaFin()      { return fechaFin; }
    public double       getCanonMensual()  { return canonMensual; }
    public boolean      isActivo()         { return activo; }
    public List<Pago>   getPagos()         { return pagos; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "Contrato[%s] | %s → %s | Inmueble: %s | Canon: $%.0f | %s",
            id, fechaInicio, fechaFin,
            inmueble.getDireccion(),
            canonMensual,
            activo ? "ACTIVO" : "FINALIZADO");
    }
}
