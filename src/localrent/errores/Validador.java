package localrent.errores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase de validaciones y control de errores del sistema LocalRent.
 *
 * ╔══════════════════════════════════════════════════════════════╗
 * ║           GESTIÓN DE ERRORES Y VALIDACIONES                  ║
 * ║                                                              ║
 * ║  Centraliza todas las validaciones de entrada del usuario.   ║
 * ║  Usa try-catch-finally para garantizar que el sistema        ║
 * ║  nunca se detenga abruptamente ante datos erróneos.          ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Validador {

    // ─── Validar texto no vacío ───────────────────────────────────────────────
    /**
     * Valida que un texto no sea nulo ni vacío.
     *
     * @param valor     Valor a validar
     * @param nombreCampo Nombre del campo (para el mensaje de error)
     * @return El valor limpio (sin espacios extra)
     * @throws ValidacionException Si el valor es nulo o vacío
     */
    public static String validarTexto(String valor, String nombreCampo)
            throws ValidacionException {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new ValidacionException(
                    "❌ El campo '" + nombreCampo + "' no puede estar vacío.");
            }
            return valor.trim();
        } catch (ValidacionException e) {
            throw e;  // Re-lanzar para que el llamador la maneje
        } finally {
            // El bloque finally siempre se ejecuta, ideal para logging
            System.out.println("   [Validación] Campo '" + nombreCampo + "' procesado.");
        }
    }

    // ─── Validar número positivo ──────────────────────────────────────────────
    /**
     * Valida que un valor numérico sea estrictamente positivo.
     *
     * @param valor       Valor a validar
     * @param nombreCampo Nombre del campo
     * @return El valor validado
     * @throws ValidacionException Si el valor es cero o negativo
     */
    public static double validarPositivo(double valor, String nombreCampo)
            throws ValidacionException {
        try {
            if (valor <= 0) {
                throw new ValidacionException(
                    "❌ El campo '" + nombreCampo + "' debe ser un valor positivo. " +
                    "Valor recibido: " + valor);
            }
            return valor;
        } finally {
            System.out.println("   [Validación] Número '" + nombreCampo + "' verificado.");
        }
    }

    // ─── Validar correo electrónico ───────────────────────────────────────────
    /**
     * Valida que el correo tenga un formato básico válido.
     *
     * @param correo Correo a validar
     * @return El correo en minúsculas si es válido
     * @throws ValidacionException Si el formato es incorrecto
     */
    public static String validarCorreo(String correo) throws ValidacionException {
        try {
            if (correo == null || !correo.contains("@") || !correo.contains(".")) {
                throw new ValidacionException(
                    "❌ El correo '" + correo + "' no tiene un formato válido. " +
                    "Ejemplo correcto: usuario@correo.com");
            }
            return correo.toLowerCase().trim();
        } finally {
            System.out.println("   [Validación] Correo electrónico verificado.");
        }
    }

    // ─── Validar fecha ────────────────────────────────────────────────────────
    /**
     * Convierte y valida una fecha en formato "yyyy-MM-dd".
     *
     * @param fechaTexto Texto con la fecha (ej: "2025-04-01")
     * @return LocalDate si es válida
     * @throws ValidacionException Si el formato no es correcto
     */
    public static LocalDate validarFecha(String fechaTexto) throws ValidacionException {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaTexto.trim(), fmt);
        } catch (DateTimeParseException e) {
            throw new ValidacionException(
                "❌ La fecha '" + fechaTexto + "' no es válida. " +
                "Use el formato: YYYY-MM-DD (ejemplo: 2025-06-01)");
        } finally {
            System.out.println("   [Validación] Fecha procesada.");
        }
    }

    // ─── Validar rango de fechas ──────────────────────────────────────────────
    /**
     * Valida que la fecha de fin sea posterior a la de inicio.
     *
     * @param inicio Fecha de inicio del contrato
     * @param fin    Fecha de fin del contrato
     * @throws ValidacionException Si el rango es inválido
     */
    public static void validarRangoFechas(LocalDate inicio, LocalDate fin)
            throws ValidacionException {
        try {
            if (!fin.isAfter(inicio)) {
                throw new ValidacionException(
                    "❌ La fecha de fin (" + fin + ") debe ser posterior " +
                    "a la fecha de inicio (" + inicio + ").");
            }
        } finally {
            System.out.println("   [Validación] Rango de fechas verificado.");
        }
    }

    // ─── Validar tipo de inmueble ─────────────────────────────────────────────
    /**
     * Valida que el tipo de inmueble sea uno de los aceptados.
     *
     * @param tipo Tipo ingresado por el usuario
     * @return El tipo en mayúsculas si es válido
     * @throws ValidacionException Si el tipo no es reconocido
     */
    public static String validarTipoInmueble(String tipo) throws ValidacionException {
        try {
            String tipoUpper = tipo.toUpperCase().trim();
            if (!tipoUpper.equals("LOCAL") && !tipoUpper.equals("APARTAMENTO")) {
                throw new ValidacionException(
                    "❌ Tipo de inmueble no válido: '" + tipo + "'. " +
                    "Tipos aceptados: LOCAL, APARTAMENTO");
            }
            return tipoUpper;
        } finally {
            System.out.println("   [Validación] Tipo de inmueble verificado.");
        }
    }

    // ─── Parsear número de entrada segura ─────────────────────────────────────
    /**
     * Convierte un texto a double de forma segura.
     * Si falla, lanza ValidacionException con mensaje amigable.
     *
     * @param texto       Texto a convertir
     * @param nombreCampo Nombre del campo para el mensaje de error
     * @return Valor numérico
     * @throws ValidacionException Si no se puede convertir
     */
    public static double parsearDouble(String texto, String nombreCampo)
            throws ValidacionException {
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException(
                "❌ El campo '" + nombreCampo + "' debe ser un número válido. " +
                "Valor recibido: '" + texto + "'");
        } finally {
            System.out.println("   [Validación] Conversión numérica de '" + nombreCampo + "' completada.");
        }
    }
}
