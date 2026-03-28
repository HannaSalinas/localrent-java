# LocalRent 🏠
### Sistema de Gestión de Arriendos — Locales y Apartamentos

Proyecto desarrollado en Java para la materia **Programación Orientada a Objetos**  
Institución Universitaria Digital de Antioquia — IUDigital  
Profesor: Ramiro A. Giraldo Escobar

---

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
```

## Compilar y ejecutar

```bash
# Compilar
javac -d out -sourcepath src src/localrent/main/Main.java

# Ejecutar
java -cp out localrent.main.Main
```

## Patrones de diseño implementados
- **Singleton** — `GestorLocalRent`: única instancia de la base de datos simulada
- **Factory Method** — `InmuebleFactory`: creación flexible de Local o Apartamento

## Programación funcional
- 10 métodos de consulta usando `Stream`, `filter`, `map`, `flatMap`, `sorted`, `limit`, `collect`, `groupingBy`, `average` y `sum`

## Gestión de errores
- Clase `ValidacionException` personalizada
- Validaciones con `try-catch-finally` para todos los campos de entrada
