package localrent.modelo;

import java.time.LocalDate;

/**
 * Clase que representa un pago mensual realizado dentro de un contrato.
 * Es parte de la composición con Contrato.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Pago {

    // ─── Tipos de estado de pago ─────────────────────────────────────────────
    public enum EstadoPago {
        PAGADO, PENDIENTE, EN_MORA
    }

    // ─── Atributos ───────────────────────────────────────────────────────────
    private String     id;
    private String     contratoId;
    private double     monto;
    private LocalDate  fechaPago;
    private String     mesPagado;    // Ej: "Enero 2025"
    private EstadoPago estado;
    private String     metodoPago;   // Ej: "Transferencia", "Efectivo", "PSE"

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Crea un nuevo registro de pago.
     *
     * @param id          ID único del pago
     * @param contratoId  ID del contrato al que pertenece
     * @param monto       Valor pagado en COP
     * @param fechaPago   Fecha en que se realizó el pago
     * @param mesPagado   Mes y año que cubre el pago
     * @param metodoPago  Método de pago utilizado
     */
    public Pago(String id, String contratoId, double monto,
                LocalDate fechaPago, String mesPagado, String metodoPago) {
        this.id          = id;
        this.contratoId  = contratoId;
        this.monto       = monto;
        this.fechaPago   = fechaPago;
        this.mesPagado   = mesPagado;
        this.metodoPago  = metodoPago;
        this.estado      = EstadoPago.PAGADO;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────
    public String     getId()         { return id; }
    public String     getContratoId() { return contratoId; }
    public double     getMonto()      { return monto; }
    public LocalDate  getFechaPago()  { return fechaPago; }
    public String     getMesPagado()  { return mesPagado; }
    public EstadoPago getEstado()     { return estado; }
    public String     getMetodoPago() { return metodoPago; }
    public void       setEstado(EstadoPago estado) { this.estado = estado; }

    // ─── toString ────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "Pago[%s] | Contrato: %s | Mes: %s | $%.0f | %s | %s",
            id, contratoId, mesPagado, monto, metodoPago, estado);
    }
}
