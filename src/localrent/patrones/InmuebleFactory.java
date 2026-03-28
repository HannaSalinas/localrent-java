package localrent.patrones;

import localrent.modelo.*;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║           PATRÓN DE DISEÑO: FACTORY METHOD                   ║
 * ║                                                              ║
 * ║  InmuebleFactory centraliza la creación de inmuebles.        ║
 * ║  El código cliente no necesita saber qué subclase            ║
 * ║  instanciar: solo indica el TIPO y la fábrica decide.        ║
 * ║                                                              ║
 * ║  ¿Por qué Factory Method aquí?                               ║
 * ║  Porque el sistema puede crecer con nuevos tipos de          ║
 * ║  inmueble (bodega, consultorio, finca) sin cambiar           ║
 * ║  el código que los usa.                                      ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * @author LocalRent Team
 * @version 1.0
 */
public class InmuebleFactory {

    // ─── Tipos disponibles ───────────────────────────────────────────────────
    public static final String TIPO_LOCAL       = "LOCAL";
    public static final String TIPO_APARTAMENTO = "APARTAMENTO";

    /**
     * Crea y retorna un inmueble del tipo especificado.
     *
     * @param tipo           Tipo: "LOCAL" o "APARTAMENTO"
     * @param id             ID único
     * @param direccion      Dirección completa
     * @param ciudad         Ciudad
     * @param areaM2         Área en metros cuadrados
     * @param canonMensual   Canon mensual en COP
     * @param descripcion    Descripción general
     * @param extras         Parámetros adicionales según tipo:
     *                       LOCAL       → [ubicacion(String), vitrina(boolean), aforo(int)]
     *                       APARTAMENTO → [piso(int), habitaciones(int), banos(int),
     *                                      parqueadero(boolean), mascotas(boolean)]
     * @return Objeto Inmueble creado (Local o Apartamento)
     * @throws IllegalArgumentException Si el tipo no es reconocido
     */
    public static Inmueble crearInmueble(String tipo, String id, String direccion,
                                          String ciudad, double areaM2,
                                          double canonMensual, String descripcion,
                                          Object... extras) {

        switch (tipo.toUpperCase()) {

            case TIPO_LOCAL:
                // extras: [0]=ubicacion, [1]=tieneVitrina, [2]=aforoMaximo
                String  ubicacion    = (String)  extras[0];
                boolean tieneVitrina = (boolean) extras[1];
                int     aforo        = (int)     extras[2];
                return new Local(id, direccion, ciudad, areaM2, canonMensual,
                                 descripcion, ubicacion, tieneVitrina, aforo);

            case TIPO_APARTAMENTO:
                // extras: [0]=piso, [1]=habitaciones, [2]=banos,
                //         [3]=tieneParqueadero, [4]=permiteMascotas
                int     piso          = (int)     extras[0];
                int     habitaciones  = (int)     extras[1];
                int     banos         = (int)     extras[2];
                boolean parqueadero   = (boolean) extras[3];
                boolean mascotas      = (boolean) extras[4];
                return new Apartamento(id, direccion, ciudad, areaM2, canonMensual,
                                       descripcion, piso, habitaciones, banos,
                                       parqueadero, mascotas);

            default:
                throw new IllegalArgumentException(
                    "❌ Tipo de inmueble no reconocido: '" + tipo +
                    "'. Use LOCAL o APARTAMENTO.");
        }
    }
}
