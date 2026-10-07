# LocalRent 🏠
### Sistema de gestión de arriendos de locales y apartamentos

[![CI](https://github.com/HannaSalinas/localrent-java/actions/workflows/ci.yml/badge.svg)](https://github.com/HannaSalinas/localrent-java/actions/workflows/ci.yml)

Aplicación de consola en Java para administrar usuarios (administradores y arrendatarios), inmuebles, contratos y pagos, con detección de mora y reportes. Proyecto académico de **Programación Orientada a Objetos** (IU Digital de Antioquia), pensado para practicar herencia, polimorfismo, patrones de diseño, programación funcional y manejo de errores.

Los datos viven en memoria: al iniciar se cargan datos de prueba y no hay persistencia en base de datos.

## Estructura del proyecto

```
src/localrent/
├── modelo/          → Clases y subclases del dominio
│   ├── Usuario.java           (abstracta)
│   ├── Administrador.java     (hereda de Usuario)
│   ├── Arrendatario.java      (hereda de Usuario)
│   ├── Inmueble.java          (abstracta)
│   ├── Local.java             (hereda de Inmueble)
│   ├── Apartamento.java       (hereda de Inmueble)
│   ├── Contrato.java
│   └── Pago.java
├── patrones/        → Patrones de diseño
│   ├── GestorLocalRent.java   (Singleton)
│   └── InmuebleFactory.java   (Factory Method)
├── funcional/       → Programación funcional
│   └── ConsultaService.java   (Streams y lambdas)
├── errores/         → Gestión de errores
│   ├── Validador.java         (try-catch-finally)
│   └── ValidacionException.java
└── main/
    └── Main.java              (punto de entrada)
test/localrent/      → Pruebas JUnit 5
```

## Requisitos

- Java 17 o superior (probado con Java 21)
- Maven no es necesario: el proyecto incluye Maven Wrapper (`./mvnw`)

## Compilar y ejecutar

Con Maven Wrapper:

```bash
./mvnw package                        # compila, ejecuta las pruebas y genera el JAR
java -jar target/localrent-1.0.0.jar
```

O solo con el JDK:

```bash
javac -d out -sourcepath src src/localrent/main/Main.java
java -cp out localrent.main.Main
```

## Pruebas

```bash
./mvnw test
```

25 pruebas JUnit 5 cubren las validaciones (`Validador`), la fábrica de inmuebles, el Singleton, la detección de mora en `Contrato` y las consultas con Streams de `ConsultaService`. GitHub Actions las ejecuta en cada push.

## Ejemplo de ejecución

Menú principal → **5. Resumen del sistema** (con los datos de prueba):

```
╔══════════════════════════════════════╗
║     RESUMEN DEL SISTEMA LocalRent    ║
╠══════════════════════════════════════╣
║  Usuarios registrados  : 3           ║
║  Inmuebles en sistema  : 5           ║
║  Contratos activos     : 1           ║
║  Pagos registrados     : 1           ║
╚══════════════════════════════════════╝
```

Menú principal → **4. Consultas y reportes** → **5. Top 3 más económicos**:

```
  🏆 Top 3 más económicos:
     → [APARTAMENTO] ID: AP02 | Cl 30 #74-80, Laureles, Medellín | 55.0 m² | $1600000/mes | DISPONIBLE | Piso: 3 | Hab: 2 | Baños: 1 | Parqueadero: No | Mascotas: Sí
     → [LOCAL] ID: L02 | Cl 10 #43-150, CC Oviedo, Medellín | 28.0 m² | $1900000/mes | DISPONIBLE | Ubicación: Piso 2, Pasillo B | Vitrina: No | Aforo: 15 personas
     → [APARTAMENTO] ID: AP01 | Cra 43A #1-50, El Poblado, Medellín | 72.0 m² | $2200000/mes | DISPONIBLE | Piso: 8 | Hab: 3 | Baños: 2 | Parqueadero: Sí | Mascotas: No
```

## Patrones de diseño implementados
- **Singleton** — `GestorLocalRent`: única instancia de la base de datos simulada
- **Factory Method** — `InmuebleFactory`: creación flexible de Local o Apartamento

## Programación funcional
- 10 métodos de consulta usando `Stream`, `filter`, `map`, `flatMap`, `sorted`, `limit`, `collect`, `groupingBy`, `average` y `sum`

## Gestión de errores
- Clase `ValidacionException` personalizada
- Validaciones con `try-catch-finally` para todos los campos de entrada

## Decisiones técnicas

- **Clases abstractas para el dominio.** `Usuario` e `Inmueble` definen lo común y cada subclase agrega sus atributos (aforo y vitrina en `Local`; piso, habitaciones y mascotas en `Apartamento`).
- **Singleton para el almacenamiento en memoria.** `GestorLocalRent` es el único punto de acceso a los datos; en las pruebas se limpia su estado antes de cada caso.
- **Factory Method** para crear inmuebles a partir del tipo que escribe el usuario, sin `if` repartidos por el menú.
- **Excepción propia** (`ValidacionException`) para separar los errores de entrada del usuario de los errores del programa.

## Licencia

[MIT](LICENSE)
