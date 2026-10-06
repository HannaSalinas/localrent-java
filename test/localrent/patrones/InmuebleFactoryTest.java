package localrent.patrones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import localrent.modelo.Apartamento;
import localrent.modelo.Inmueble;
import localrent.modelo.Local;

class InmuebleFactoryTest {

    @Test
    void creaLocal() {
        Inmueble local = InmuebleFactory.crearInmueble("local", "L1", "Cra 1", "Medellín",
                40, 2_000_000, "Local comercial", "Centro", true, 20);
        assertInstanceOf(Local.class, local);
        assertEquals("LOCAL", local.getTipo());
    }

    @Test
    void creaApartamento() {
        Inmueble apto = InmuebleFactory.crearInmueble("APARTAMENTO", "A1", "Cl 10", "Bello",
                65, 1_500_000, "Apartamento", 3, 2, 1, false, true);
        assertInstanceOf(Apartamento.class, apto);
        assertTrue(((Apartamento) apto).isPermiteMascotas());
    }

    @Test
    void rechazaTipoDesconocido() {
        assertThrows(IllegalArgumentException.class, () -> InmuebleFactory.crearInmueble(
                "CASA", "C1", "Cl 1", "Medellín", 90, 3_000_000, "Casa"));
    }

    @Test
    void gestorEsSingleton() {
        assertSame(GestorLocalRent.getInstancia(), GestorLocalRent.getInstancia());
    }
}
