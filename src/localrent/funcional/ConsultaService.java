package localrent.funcional;

import localrent.modelo.*;
import localrent.patrones.GestorLocalRent;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de consultas funcionales del sistema LocalRent.
 *
 * ╔══════════════════════════════════════════════════════════════╗
 * ║           PROGRAMACIÓN FUNCIONAL                             ║
 * ║                                                              ║
 * ║  Este módulo usa Streams y expresiones lambda para:          ║
 * ║  • Filtrar inmuebles por ciudad, tipo, precio                ║
 * ║  • Calcular promedios de canon                               ║
 * ║  • Detectar contratos con mora                               ║
 * ║  • Agrupar inmuebles por tipo                                ║
 * ║  • Ordenar y rankear                                         ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class ConsultaService {

    private final GestorLocalRent gestor;

    public ConsultaService() {
        this.gestor = GestorLocalRent.getInstancia();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 1. FILTRAR inmuebles disponibles
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Retorna todos los inmuebles que están disponibles para arrendar.
     * Usa: filter() + collect()
     */
    public List<Inmueble> inmuebleDisponibles() {
        return gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)          // lambda: i -> i.isDisponible()
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. FILTRAR por ciudad
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Retorna inmuebles disponibles en una ciudad específica.
     * Usa: filter() encadenado
     *
     * @param ciudad Ciudad a buscar (no distingue mayúsculas)
     */
    public List<Inmueble> inmueblesPorCiudad(String ciudad) {
        return gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)
                .filter(i -> i.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. FILTRAR por precio máximo
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Retorna inmuebles disponibles con canon menor o igual al máximo indicado.
     * Usa: filter() + sorted()
     *
     * @param precioMaximo Canon máximo mensual en COP
     */
    public List<Inmueble> inmueblesPorPrecioMaximo(double precioMaximo) {
        return gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)
                .filter(i -> i.getCanonMensual() <= precioMaximo)
                .sorted(Comparator.comparingDouble(Inmueble::getCanonMensual))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. CALCULAR promedio de canon por tipo
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Calcula el canon promedio de un tipo de inmueble (LOCAL o APARTAMENTO).
     * Usa: filter() + mapToDouble() + average()
     *
     * @param tipo "LOCAL" o "APARTAMENTO"
     * @return Promedio del canon mensual, 0 si no hay registros
     */
    public double promedioCanonPorTipo(String tipo) {
        return gestor.getInmuebles().stream()
                .filter(i -> i.getTipo().equalsIgnoreCase(tipo))
                .mapToDouble(Inmueble::getCanonMensual)
                .average()
                .orElse(0.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. DETECTAR contratos con mora
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Retorna todos los contratos activos que tienen mora (más de 35 días sin pago).
     * Usa: filter() encadenado
     */
    public List<Contrato> contratosConMora() {
        return gestor.getContratos().stream()
                .filter(Contrato::isActivo)
                .filter(Contrato::tieneMora)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. AGRUPAR inmuebles por tipo
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Agrupa todos los inmuebles por su tipo.
     * Usa: collect(Collectors.groupingBy())
     *
     * @return Mapa con tipo como clave y lista de inmuebles como valor
     */
    public Map<String, List<Inmueble>> agruparPorTipo() {
        return gestor.getInmuebles().stream()
                .collect(Collectors.groupingBy(Inmueble::getTipo));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 7. TOP 3 inmuebles más económicos disponibles
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Retorna los 3 inmuebles disponibles con el canon más bajo.
     * Usa: filter() + sorted() + limit()
     */
    public List<Inmueble> top3MasEconomicos() {
        return gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)
                .sorted(Comparator.comparingDouble(Inmueble::getCanonMensual))
                .limit(3)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 8. IMPRIMIR reporte completo usando forEach
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Imprime en consola todos los inmuebles disponibles.
     * Usa: filter() + forEach()
     */
    public void reporteInmueblesDisponibles() {
        System.out.println("\n📋 INMUEBLES DISPONIBLES:");
        System.out.println("─".repeat(80));
        gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)
                .forEach(i -> System.out.println("  → " + i));
        System.out.println("─".repeat(80));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 9. TOTAL recaudado por contratos activos
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Suma todos los pagos registrados en contratos activos.
     * Usa: flatMap() + mapToDouble() + sum()
     */
    public double totalRecaudado() {
        return gestor.getContratos().stream()
                .filter(Contrato::isActivo)
                .flatMap(c -> c.getPagos().stream())
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 10. BUSCAR apartamentos que permitan mascotas
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Filtra apartamentos disponibles que permiten mascotas.
     * Usa: filter() con instanceof y cast
     */
    public List<Inmueble> apartamentosConMascotas() {
        return gestor.getInmuebles().stream()
                .filter(Inmueble::isDisponible)
                .filter(i -> i instanceof Apartamento)
                .filter(i -> ((Apartamento) i).isPermiteMascotas())
                .collect(Collectors.toList());
    }
}
