package localrent.errores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ValidadorTest {

    @Test
    void validarTextoRecortaEspacios() throws ValidacionException {
        assertEquals("Medellín", Validador.validarTexto("  Medellín  ", "ciudad"));
    }

    @Test
    void validarTextoRechazaVacio() {
        assertThrows(ValidacionException.class, () -> Validador.validarTexto("   ", "nombre"));
        assertThrows(ValidacionException.class, () -> Validador.validarTexto(null, "nombre"));
    }

    @Test
    void validarPositivoRechazaCeroYNegativos() {
        assertThrows(ValidacionException.class, () -> Validador.validarPositivo(0, "canon"));
        assertThrows(ValidacionException.class, () -> Validador.validarPositivo(-1, "canon"));
    }

    @Test
    void validarCorreoNormalizaAMinusculas() throws ValidacionException {
        assertEquals("ana@correo.com", Validador.validarCorreo("Ana@Correo.com"));
    }

    @Test
    void validarCorreoRechazaFormatoInvalido() {
        assertThrows(ValidacionException.class, () -> Validador.validarCorreo("ana.correo.com"));
    }

    @Test
    void validarFechaAceptaFormatoIso() throws ValidacionException {
        assertEquals(LocalDate.of(2025, 6, 1), Validador.validarFecha("2025-06-01"));
    }

    @Test
    void validarFechaRechazaOtroFormato() {
        assertThrows(ValidacionException.class, () -> Validador.validarFecha("01/06/2025"));
    }

    @Test
    void validarRangoFechasExigeFinPosterior() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        assertThrows(ValidacionException.class, () -> Validador.validarRangoFechas(inicio, inicio));
    }

    @Test
    void validarTipoInmuebleNormalizaAMayusculas() throws ValidacionException {
        assertEquals("APARTAMENTO", Validador.validarTipoInmueble(" apartamento "));
        assertThrows(ValidacionException.class, () -> Validador.validarTipoInmueble("casa"));
    }

    @Test
    void parsearDoubleRechazaTextoNoNumerico() {
        assertThrows(ValidacionException.class, () -> Validador.parsearDouble("mil", "canon"));
    }
}
