package localrent.errores;

/**
 * Excepción personalizada del sistema LocalRent.
 * Se lanza cuando los datos ingresados por el usuario no son válidos.
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class ValidacionException extends Exception {

    /**
     * Crea una nueva excepción de validación con un mensaje descriptivo.
     *
     * @param mensaje Descripción del error de validación
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
