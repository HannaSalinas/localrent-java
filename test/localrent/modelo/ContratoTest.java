package localrent.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import localrent.patrones.InmuebleFactory;

class ContratoTest {

    private Arrendatario arrendatario;
    private Inmueble inmueble;

    @BeforeEach
    void setUp() {
        arrendatario = new Arrendatario("U1", "Ana Pérez", "ana@correo.com", "3000000000",
                "CC", "123", 5_000_000);
        inmueble = InmuebleFactory.crearInmueble("LOCAL", "L1", "Cra 1", "Medellín",
                40, 2_000_000, "Local", "Centro", true, 20);
    }

    @Test
    void crearContratoOcupaElInmueble() {
        new Contrato("C1", arrendatario, inmueble, LocalDate.now(), LocalDate.now().plusYears(1), 2_000_000);
        assertFalse(inmueble.isDisponible());
    }

    @Test
    void finalizarContratoLiberaElInmueble() {
        Contrato contrato = new Contrato("C1", arrendatario, inmueble,
                LocalDate.now(), LocalDate.now().plusYears(1), 2_000_000);
        contrato.finalizarContrato();
        assertTrue(inmueble.isDisponible());
        assertFalse(contrato.isActivo());
    }

    @Test
    void sinPagosDespuesDe35DiasHayMora() {
        Contrato contrato = new Contrato("C1", arrendatario, inmueble,
                LocalDate.now().minusDays(40), LocalDate.now().plusYears(1), 2_000_000);
        assertTrue(contrato.tieneMora());
    }

    @Test
    void pagoRecienteEvitaLaMora() {
        Contrato contrato = new Contrato("C1", arrendatario, inmueble,
                LocalDate.now().minusDays(60), LocalDate.now().plusYears(1), 2_000_000);
        contrato.registrarPago(new Pago("P1", "C1", 2_000_000, LocalDate.now().minusDays(5),
                "2025-06", "Transferencia"));
        assertFalse(contrato.tieneMora());
    }
}
