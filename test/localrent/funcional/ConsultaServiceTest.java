package localrent.funcional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import localrent.modelo.Arrendatario;
import localrent.modelo.Contrato;
import localrent.modelo.Inmueble;
import localrent.modelo.Pago;
import localrent.patrones.GestorLocalRent;
import localrent.patrones.InmuebleFactory;

class ConsultaServiceTest {

    private final GestorLocalRent gestor = GestorLocalRent.getInstancia();
    private final ConsultaService consulta = new ConsultaService();

    // El gestor es un Singleton: se limpia su estado antes de cada prueba
    @BeforeEach
    void setUp() {
        gestor.getInmuebles().clear();
        gestor.getContratos().clear();
        gestor.getPagos().clear();
        gestor.getUsuarios().clear();

        gestor.agregarInmueble(InmuebleFactory.crearInmueble("LOCAL", "L1", "Cra 1", "Medellín",
                40, 2_500_000, "Local", "Centro", true, 20));
        gestor.agregarInmueble(InmuebleFactory.crearInmueble("LOCAL", "L2", "Cra 2", "Bello",
                30, 1_500_000, "Local", "Barrio", false, 10));
        gestor.agregarInmueble(InmuebleFactory.crearInmueble("APARTAMENTO", "A1", "Cl 1", "Medellín",
                60, 1_800_000, "Apto", 2, 2, 1, true, true));
        gestor.agregarInmueble(InmuebleFactory.crearInmueble("APARTAMENTO", "A2", "Cl 2", "Envigado",
                80, 3_000_000, "Apto", 5, 3, 2, true, false));
    }

    @Test
    void filtraPorCiudadSinImportarMayusculas() {
        assertEquals(2, consulta.inmueblesPorCiudad("medellín").size());
    }

    @Test
    void filtraPorPrecioMaximoOrdenadoDeMenorAMayor() {
        List<Inmueble> resultado = consulta.inmueblesPorPrecioMaximo(2_000_000);
        assertEquals(List.of("L2", "A1"), resultado.stream().map(Inmueble::getId).toList());
    }

    @Test
    void calculaPromedioDeCanonPorTipo() {
        assertEquals(2_000_000, consulta.promedioCanonPorTipo("LOCAL"));
        assertEquals(0.0, consulta.promedioCanonPorTipo("BODEGA"));
    }

    @Test
    void top3MasEconomicosExcluyeOcupados() {
        Arrendatario ana = new Arrendatario("U1", "Ana", "ana@correo.com", "300", "CC", "1", 5_000_000);
        new Contrato("C1", ana, gestor.buscarInmueblePorId("L2"),
                LocalDate.now(), LocalDate.now().plusYears(1), 1_500_000);

        List<String> ids = consulta.top3MasEconomicos().stream().map(Inmueble::getId).toList();
        assertEquals(List.of("A1", "L1", "A2"), ids);
    }

    @Test
    void agrupaPorTipo() {
        Map<String, List<Inmueble>> grupos = consulta.agruparPorTipo();
        assertEquals(2, grupos.get("LOCAL").size());
        assertEquals(2, grupos.get("APARTAMENTO").size());
    }

    @Test
    void soloApartamentosQuePermitenMascotas() {
        List<Inmueble> resultado = consulta.apartamentosConMascotas();
        assertEquals(1, resultado.size());
        assertEquals("A1", resultado.get(0).getId());
    }

    @Test
    void totalRecaudadoSumaPagosDeContratosActivos() {
        Arrendatario ana = new Arrendatario("U1", "Ana", "ana@correo.com", "300", "CC", "1", 5_000_000);
        Contrato contrato = new Contrato("C1", ana, gestor.buscarInmueblePorId("A1"),
                LocalDate.now(), LocalDate.now().plusYears(1), 1_800_000);
        contrato.registrarPago(new Pago("P1", "C1", 1_800_000, LocalDate.now(), "2025-06", "Efectivo"));
        contrato.registrarPago(new Pago("P2", "C1", 1_800_000, LocalDate.now(), "2025-07", "Efectivo"));
        gestor.agregarContrato(contrato);

        assertEquals(3_600_000, consulta.totalRecaudado());
        contrato.finalizarContrato();
        assertEquals(0, consulta.totalRecaudado());
        assertTrue(consulta.contratosConMora().isEmpty());
    }
}
