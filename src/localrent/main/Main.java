package localrent.main;

import localrent.errores.ValidacionException;
import localrent.errores.Validador;
import localrent.funcional.ConsultaService;
import localrent.modelo.*;
import localrent.patrones.GestorLocalRent;
import localrent.patrones.InmuebleFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║                                                                      ║
 * ║        L O C A L R E N T                                             ║
 * ║        Sistema de Gestión de Arriendos                               ║
 * ║        Versión 1.0                                                   ║
 * ║                                                                      ║
 * ║  Clase principal del sistema. Inicializa datos de prueba             ║
 * ║  y despliega el menú interactivo en consola.                         ║
 * ║                                                                      ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class Main {

    // ─── Recursos globales ────────────────────────────────────────────────────
    private static final GestorLocalRent gestor  = GestorLocalRent.getInstancia();
    private static final ConsultaService consulta = new ConsultaService();
    private static final Scanner scanner          = new Scanner(System.in);

    // ─────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        mostrarBienvenida();
        cargarDatosDePrueba();
        menuPrincipal();
    }

    // ─── Bienvenida ───────────────────────────────────────────────────────────
    private static void mostrarBienvenida() {
        System.out.println("\n");
        System.out.println("  ██╗      ██████╗  ██████╗ █████╗ ██╗     ██████╗ ███████╗███╗   ██╗████████╗");
        System.out.println("  ██║     ██╔═══██╗██╔════╝██╔══██╗██║     ██╔══██╗██╔════╝████╗  ██║╚══██╔══╝");
        System.out.println("  ██║     ██║   ██║██║     ███████║██║     ██████╔╝█████╗  ██╔██╗ ██║   ██║   ");
        System.out.println("  ██║     ██║   ██║██║     ██╔══██║██║     ██╔══██╗██╔══╝  ██║╚██╗██║   ██║   ");
        System.out.println("  ███████╗╚██████╔╝╚██████╗██║  ██║███████╗██║  ██║███████╗██║ ╚████║   ██║   ");
        System.out.println("  ╚══════╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   ");
        System.out.println("\n           Sistema de Gestión de Arriendos — Locales y Apartamentos");
        System.out.println("  ─────────────────────────────────────────────────────────────────────────");
    }

    // ─── Menú Principal ───────────────────────────────────────────────────────
    private static void menuPrincipal() {
        int opcion = -1;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║           MENÚ PRINCIPAL             ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Usuarios              ║");
            System.out.println("║  2. Gestión de Inmuebles             ║");
            System.out.println("║  3. Gestión de Contratos             ║");
            System.out.println("║  4. Consultas y Reportes             ║");
            System.out.println("║  5. Resumen del Sistema              ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                switch (opcion) {
                    case 1: menuUsuarios();   break;
                    case 2: menuInmuebles();  break;
                    case 3: menuContratos();  break;
                    case 4: menuConsultas();  break;
                    case 5: gestor.mostrarResumen(); break;
                    case 0: System.out.println("\n  👋 Hasta pronto — LocalRent.\n"); break;
                    default: System.out.println("  ⚠️  Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  ❌ Error: Ingrese un número entero válido.");
            }
        } while (opcion != 0);
    }

    // ─── Menú Usuarios ────────────────────────────────────────────────────────
    private static void menuUsuarios() {
        System.out.println("\n── GESTIÓN DE USUARIOS ──");
        System.out.println("  1. Registrar Administrador");
        System.out.println("  2. Registrar Arrendatario");
        System.out.println("  3. Listar todos los usuarios");
        System.out.print("  Opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine().trim());
            switch (op) {
                case 1: registrarAdministrador(); break;
                case 2: registrarArrendatario();  break;
                case 3: listarUsuarios();          break;
                default: System.out.println("  ⚠️  Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Error: Ingrese un número válido.");
        }
    }

    private static void registrarAdministrador() {
        System.out.println("\n── NUEVO ADMINISTRADOR ──");
        try {
            System.out.print("  ID único       : ");
            String id = Validador.validarTexto(scanner.nextLine(), "ID");

            System.out.print("  Nombre completo: ");
            String nombre = Validador.validarTexto(scanner.nextLine(), "Nombre");

            System.out.print("  Correo         : ");
            String correo = Validador.validarCorreo(scanner.nextLine());

            System.out.print("  Teléfono       : ");
            String telefono = Validador.validarTexto(scanner.nextLine(), "Teléfono");

            System.out.print("  Empresa/Razón  : ");
            String empresa = Validador.validarTexto(scanner.nextLine(), "Empresa");

            Administrador admin = new Administrador(id, nombre, correo, telefono, empresa);
            gestor.agregarUsuario(admin);
            System.out.println("\n  ✅ Administrador '" + nombre + "' registrado exitosamente.");

        } catch (ValidacionException e) {
            System.out.println("\n  " + e.getMessage());
            System.out.println("  ℹ️  Operación cancelada. Intente de nuevo.");
        }
    }

    private static void registrarArrendatario() {
        System.out.println("\n── NUEVO ARRENDATARIO ──");
        try {
            System.out.print("  ID único          : ");
            String id = Validador.validarTexto(scanner.nextLine(), "ID");

            System.out.print("  Nombre completo   : ");
            String nombre = Validador.validarTexto(scanner.nextLine(), "Nombre");

            System.out.print("  Correo            : ");
            String correo = Validador.validarCorreo(scanner.nextLine());

            System.out.print("  Teléfono          : ");
            String telefono = Validador.validarTexto(scanner.nextLine(), "Teléfono");

            System.out.print("  Tipo documento    : ");
            String tipoDoc = Validador.validarTexto(scanner.nextLine(), "Tipo documento");

            System.out.print("  Número documento  : ");
            String numDoc = Validador.validarTexto(scanner.nextLine(), "Número documento");

            System.out.print("  Ingresos mensuales: ");
            double ingresos = Validador.parsearDouble(scanner.nextLine(), "Ingresos");
            Validador.validarPositivo(ingresos, "Ingresos");

            Arrendatario arr = new Arrendatario(id, nombre, correo, telefono,
                    tipoDoc, numDoc, ingresos);
            gestor.agregarUsuario(arr);
            System.out.println("\n  ✅ Arrendatario '" + nombre + "' registrado exitosamente.");

        } catch (ValidacionException e) {
            System.out.println("\n  " + e.getMessage());
            System.out.println("  ℹ️  Operación cancelada. Intente de nuevo.");
        }
    }

    private static void listarUsuarios() {
        List<Usuario> lista = gestor.getUsuarios();
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No hay usuarios registrados.");
            return;
        }
        System.out.println("\n── USUARIOS REGISTRADOS ──");
        lista.forEach(u -> System.out.println("  • " + u));
    }

    // ─── Menú Inmuebles ───────────────────────────────────────────────────────
    private static void menuInmuebles() {
        System.out.println("\n── GESTIÓN DE INMUEBLES ──");
        System.out.println("  1. Registrar nuevo inmueble");
        System.out.println("  2. Listar todos los inmuebles");
        System.out.print("  Opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine().trim());
            switch (op) {
                case 1: registrarInmueble(); break;
                case 2: listarInmuebles();   break;
                default: System.out.println("  ⚠️  Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Ingrese un número válido.");
        }
    }

    private static void registrarInmueble() {
        System.out.println("\n── NUEVO INMUEBLE ──");
        try {
            System.out.print("  Tipo (LOCAL / APARTAMENTO): ");
            String tipo = Validador.validarTipoInmueble(scanner.nextLine());

            System.out.print("  ID único   : ");
            String id = Validador.validarTexto(scanner.nextLine(), "ID");

            System.out.print("  Dirección  : ");
            String dir = Validador.validarTexto(scanner.nextLine(), "Dirección");

            System.out.print("  Ciudad     : ");
            String ciudad = Validador.validarTexto(scanner.nextLine(), "Ciudad");

            System.out.print("  Área (m²)  : ");
            double area = Validador.parsearDouble(scanner.nextLine(), "Área");
            Validador.validarPositivo(area, "Área");

            System.out.print("  Canon/mes  : ");
            double canon = Validador.parsearDouble(scanner.nextLine(), "Canon");
            Validador.validarPositivo(canon, "Canon");

            System.out.print("  Descripción: ");
            String desc = Validador.validarTexto(scanner.nextLine(), "Descripción");

            Inmueble inmueble;

            if (tipo.equals("LOCAL")) {
                System.out.print("  Ubicación en centro: ");
                String ubic = Validador.validarTexto(scanner.nextLine(), "Ubicación");
                System.out.print("  ¿Tiene vitrina? (s/n): ");
                boolean vitrina = scanner.nextLine().trim().equalsIgnoreCase("s");
                System.out.print("  Aforo máximo: ");
                int aforo = (int) Validador.parsearDouble(scanner.nextLine(), "Aforo");
                inmueble = InmuebleFactory.crearInmueble(tipo, id, dir, ciudad,
                        area, canon, desc, ubic, vitrina, aforo);
            } else {
                System.out.print("  Número de piso      : ");
                int piso = (int) Validador.parsearDouble(scanner.nextLine(), "Piso");
                System.out.print("  Habitaciones        : ");
                int hab  = (int) Validador.parsearDouble(scanner.nextLine(), "Habitaciones");
                System.out.print("  Baños               : ");
                int ban  = (int) Validador.parsearDouble(scanner.nextLine(), "Baños");
                System.out.print("  ¿Parqueadero? (s/n) : ");
                boolean parq = scanner.nextLine().trim().equalsIgnoreCase("s");
                System.out.print("  ¿Mascotas?    (s/n) : ");
                boolean masc = scanner.nextLine().trim().equalsIgnoreCase("s");
                inmueble = InmuebleFactory.crearInmueble(tipo, id, dir, ciudad,
                        area, canon, desc, piso, hab, ban, parq, masc);
            }

            gestor.agregarInmueble(inmueble);
            System.out.println("\n  ✅ Inmueble registrado exitosamente.");

        } catch (ValidacionException e) {
            System.out.println("\n  " + e.getMessage());
            System.out.println("  ℹ️  Operación cancelada.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n  " + e.getMessage());
        }
    }

    private static void listarInmuebles() {
        List<Inmueble> lista = gestor.getInmuebles();
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No hay inmuebles registrados.");
            return;
        }
        System.out.println("\n── INMUEBLES EN SISTEMA ──");
        lista.forEach(i -> System.out.println("  • " + i));
    }

    // ─── Menú Contratos ───────────────────────────────────────────────────────
    private static void menuContratos() {
        System.out.println("\n── GESTIÓN DE CONTRATOS ──");
        System.out.println("  1. Crear nuevo contrato");
        System.out.println("  2. Registrar pago");
        System.out.println("  3. Ver alertas de mora");
        System.out.println("  4. Listar contratos activos");
        System.out.print("  Opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine().trim());
            switch (op) {
                case 1: crearContrato();      break;
                case 2: registrarPago();      break;
                case 3: verAlertas();         break;
                case 4: listarContratos();    break;
                default: System.out.println("  ⚠️  Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Ingrese un número válido.");
        }
    }

    private static void crearContrato() {
        System.out.println("\n── NUEVO CONTRATO ──");
        try {
            System.out.print("  ID contrato         : ");
            String cid = Validador.validarTexto(scanner.nextLine(), "ID contrato");

            System.out.print("  ID arrendatario     : ");
            String aid = Validador.validarTexto(scanner.nextLine(), "ID arrendatario");
            Usuario u = gestor.buscarUsuarioPorId(aid);
            if (!(u instanceof Arrendatario)) {
                System.out.println("  ❌ Arrendatario no encontrado con ID: " + aid);
                return;
            }

            System.out.print("  ID inmueble         : ");
            String iid = Validador.validarTexto(scanner.nextLine(), "ID inmueble");
            Inmueble inm = gestor.buscarInmueblePorId(iid);
            if (inm == null) {
                System.out.println("  ❌ Inmueble no encontrado con ID: " + iid);
                return;
            }
            if (!inm.isDisponible()) {
                System.out.println("  ❌ El inmueble '" + iid + "' ya está ocupado.");
                return;
            }

            System.out.print("  Fecha inicio (YYYY-MM-DD): ");
            LocalDate inicio = Validador.validarFecha(scanner.nextLine());
            System.out.print("  Fecha fin    (YYYY-MM-DD): ");
            LocalDate fin = Validador.validarFecha(scanner.nextLine());
            Validador.validarRangoFechas(inicio, fin);

            System.out.print("  Canon mensual pactado    : ");
            double canon = Validador.parsearDouble(scanner.nextLine(), "Canon");
            Validador.validarPositivo(canon, "Canon");

            Contrato contrato = new Contrato(cid, (Arrendatario) u, inm, inicio, fin, canon);
            gestor.agregarContrato(contrato);
            System.out.println("\n  ✅ Contrato '" + cid + "' creado exitosamente.");

        } catch (ValidacionException e) {
            System.out.println("\n  " + e.getMessage());
        }
    }

    private static void registrarPago() {
        System.out.println("\n── REGISTRAR PAGO ──");
        try {
            System.out.print("  ID del contrato : ");
            String cid = Validador.validarTexto(scanner.nextLine(), "ID contrato");
            Contrato contrato = gestor.buscarContratoPorId(cid);
            if (contrato == null) {
                System.out.println("  ❌ Contrato no encontrado.");
                return;
            }

            System.out.print("  ID del pago     : ");
            String pid = Validador.validarTexto(scanner.nextLine(), "ID pago");

            System.out.print("  Monto pagado    : ");
            double monto = Validador.parsearDouble(scanner.nextLine(), "Monto");
            Validador.validarPositivo(monto, "Monto");

            System.out.print("  Mes que cubre   : ");
            String mes = Validador.validarTexto(scanner.nextLine(), "Mes");

            System.out.print("  Método de pago  : ");
            String metodo = Validador.validarTexto(scanner.nextLine(), "Método");

            Pago pago = new Pago(pid, cid, monto, LocalDate.now(), mes, metodo);
            contrato.registrarPago(pago);
            gestor.agregarPago(pago);
            System.out.println("\n  ✅ Pago registrado exitosamente para el mes: " + mes);

        } catch (ValidacionException e) {
            System.out.println("\n  " + e.getMessage());
        }
    }

    private static void verAlertas() {
        System.out.println("\n── ALERTAS DE MORA ──");
        List<Contrato> mora = consulta.contratosConMora();
        if (mora.isEmpty()) {
            System.out.println("  ✅ Todos los contratos están al día.");
        } else {
            mora.forEach(c -> System.out.println("  " + c.generarAlertaMora()));
        }
    }

    private static void listarContratos() {
        System.out.println("\n── CONTRATOS ACTIVOS ──");
        gestor.getContratos().stream()
                .filter(Contrato::isActivo)
                .forEach(c -> System.out.println("  • " + c));
    }

    // ─── Menú Consultas ───────────────────────────────────────────────────────
    private static void menuConsultas() {
        System.out.println("\n── CONSULTAS Y REPORTES ──");
        System.out.println("  1. Inmuebles disponibles");
        System.out.println("  2. Buscar por ciudad");
        System.out.println("  3. Buscar por precio máximo");
        System.out.println("  4. Promedio canon por tipo");
        System.out.println("  5. Top 3 más económicos");
        System.out.println("  6. Apartamentos que permiten mascotas");
        System.out.println("  7. Inmuebles agrupados por tipo");
        System.out.println("  8. Total recaudado");
        System.out.print("  Opción: ");

        try {
            int op = Integer.parseInt(scanner.nextLine().trim());
            switch (op) {
                case 1:
                    consulta.reporteInmueblesDisponibles();
                    break;
                case 2:
                    System.out.print("  Ciudad: ");
                    String ciudad = scanner.nextLine();
                    List<Inmueble> porCiudad = consulta.inmueblesPorCiudad(ciudad);
                    System.out.println("\n  📍 Inmuebles en " + ciudad + ":");
                    porCiudad.forEach(i -> System.out.println("     → " + i));
                    break;
                case 3:
                    System.out.print("  Precio máximo: ");
                    try {
                        double max = Validador.parsearDouble(scanner.nextLine(), "Precio máximo");
                        consulta.inmueblesPorPrecioMaximo(max)
                                .forEach(i -> System.out.println("     → " + i));
                    } catch (ValidacionException e) {
                        System.out.println("  " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.printf("  Promedio LOCAL       : $%.0f%n",
                            consulta.promedioCanonPorTipo("LOCAL"));
                    System.out.printf("  Promedio APARTAMENTO : $%.0f%n",
                            consulta.promedioCanonPorTipo("APARTAMENTO"));
                    break;
                case 5:
                    System.out.println("\n  🏆 Top 3 más económicos:");
                    consulta.top3MasEconomicos()
                            .forEach(i -> System.out.println("     → " + i));
                    break;
                case 6:
                    System.out.println("\n  🐾 Apartamentos que permiten mascotas:");
                    consulta.apartamentosConMascotas()
                            .forEach(i -> System.out.println("     → " + i));
                    break;
                case 7:
                    System.out.println("\n  📂 Agrupados por tipo:");
                    Map<String, List<Inmueble>> grupos = consulta.agruparPorTipo();
                    grupos.forEach((tipo, lista) -> {
                        System.out.println("  [" + tipo + "]");
                        lista.forEach(i -> System.out.println("     → " + i));
                    });
                    break;
                case 8:
                    System.out.printf("%n  💰 Total recaudado en pagos: $%.0f%n",
                            consulta.totalRecaudado());
                    break;
                default:
                    System.out.println("  ⚠️  Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Ingrese un número válido.");
        }
    }

    // ─── Datos de prueba ─────────────────────────────────────────────────────
    /**
     * Carga datos iniciales para demostración del sistema.
     * Usa InmuebleFactory (Factory Method) para crear inmuebles.
     */
    private static void cargarDatosDePrueba() {
        System.out.println("\n  ⏳ Cargando datos de demostración...");

        // ── Usuarios ──────────────────────────────────────────────────────────
        Administrador admin1 = new Administrador("A01", "María Fernanda Torres",
                "mftorres@localrent.co", "3001234567", "Inmobiliaria Torres");
        Arrendatario arr1 = new Arrendatario("R01", "Carlos Andrés López",
                "calopez@gmail.com", "3109876543", "CC", "1045678901", 4500000);
        Arrendatario arr2 = new Arrendatario("R02", "Valentina Ríos",
                "vrios@hotmail.com", "3207654321", "CC", "1023456789", 3200000);
        gestor.agregarUsuario(admin1);
        gestor.agregarUsuario(arr1);
        gestor.agregarUsuario(arr2);

        // ── Inmuebles (usando Factory Method) ─────────────────────────────────
        Inmueble local1 = InmuebleFactory.crearInmueble("LOCAL",
                "L01", "Cra 65 #34-20, CC Unicentro", "Medellín",
                45.0, 2800000, "Local comercial esquinero con vitrina doble",
                "Piso 1, Pasillo A", true, 30);

        Inmueble local2 = InmuebleFactory.crearInmueble("LOCAL",
                "L02", "Cl 10 #43-150, CC Oviedo", "Medellín",
                28.0, 1900000, "Local interior ideal para servicios",
                "Piso 2, Pasillo B", false, 15);

        Inmueble apto1 = InmuebleFactory.crearInmueble("APARTAMENTO",
                "AP01", "Cra 43A #1-50, El Poblado", "Medellín",
                72.0, 2200000, "Apartamento moderno con vista a la ciudad",
                8, 3, 2, true, false);

        Inmueble apto2 = InmuebleFactory.crearInmueble("APARTAMENTO",
                "AP02", "Cl 30 #74-80, Laureles", "Medellín",
                55.0, 1600000, "Apartamento acogedor en zona residencial tranquila",
                3, 2, 1, false, true);

        Inmueble apto3 = InmuebleFactory.crearInmueble("APARTAMENTO",
                "AP03", "Av El Dorado #68-15", "Bogotá",
                80.0, 2500000, "Amplio apartamento cerca a Corferias",
                5, 3, 2, true, true);

        gestor.agregarInmueble(local1);
        gestor.agregarInmueble(local2);
        gestor.agregarInmueble(apto1);
        gestor.agregarInmueble(apto2);
        gestor.agregarInmueble(apto3);
        admin1.agregarInmueble(local1);
        admin1.agregarInmueble(apto1);

        // ── Contrato activo ───────────────────────────────────────────────────
        Contrato c1 = new Contrato("C001", arr1, local1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                2800000);
        gestor.agregarContrato(c1);

        // Pago registrado (reciente, sin mora)
        Pago p1 = new Pago("P001", "C001", 2800000,
                LocalDate.now().minusDays(5), "Marzo 2025", "PSE");
        c1.registrarPago(p1);
        gestor.agregarPago(p1);

        System.out.println("  ✅ Datos cargados: 3 usuarios, 5 inmuebles, 1 contrato activo.\n");
    }
}
