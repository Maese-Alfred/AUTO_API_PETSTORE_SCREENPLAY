# PetStore API Automation - Screenplay

Proyecto de automatización de pruebas API para **PetStore Swagger**, construido con **Serenity BDD**, **Cucumber** y el patrón **Screenplay** sobre **Serenity Screenplay REST**.

---

## Tecnologías

| Herramienta | Versión |
|---|---|
| Java | 17+ |
| Gradle (Kotlin DSL) | 8.x |
| Serenity BDD | 4.2.34 |
| Serenity Screenplay REST | 4.2.34 |
| Cucumber | 7.15.0 |
| JUnit Platform | 5.10.0 |
| Hamcrest | 2.2 |

---

## Prerrequisitos

- JDK 17 o superior instalado y configurado en `JAVA_HOME`
- Acceso a internet para consumir la API: `https://petstore.swagger.io/v2`
- No se requiere instalar Gradle: el proyecto incluye el wrapper `gradlew`

---

## Estructura del Proyecto

```
src/
└── test/
    ├── java/org/auto/screenplay/
    │   ├── models/                    # POJOs de datos de la API
    │   │   ├── Pet.java
    │   │   ├── Category.java
    │   │   └── Tag.java
    │   ├── tasks/                     # Tareas de negocio (una responsabilidad por clase)
    │   │   ├── CreatePet.java         → POST /pet
    │   │   ├── GetPet.java            → GET  /pet/{petId}
    │   │   ├── UpdatePet.java         → PUT  /pet
    │   │   └── DeletePet.java         → DELETE /pet/{petId}
    │   ├── questions/                 # Validaciones sobre la respuesta HTTP
    │   │   ├── ResponseCode.java      → Código de estado HTTP
    │   │   └── ResponseBody.java      → Campo del cuerpo JSON
    │   ├── utils/                     # Constantes de endpoints
    │   │   └── Endpoints.java
    │   ├── stepdefinitions/           # Glue entre Gherkin y Screenplay
    │   │   └── PetStepDefinitions.java
    │   └── runners/                   # Runner de Cucumber + JUnit Platform
    │       └── PetCrudRunner.java
    └── resources/
        ├── features/
        │   └── pet_crud.feature       # Escenario CRUD en Gherkin
        ├── serenity.conf              # URL base y configuración de Serenity
        └── serenity.properties        # Propiedades complementarias
```

---

## Patrón Screenplay — Capas

| Capa | Responsabilidad |
|---|---|
| **Tasks** | Encapsulan una operación HTTP con semántica de negocio |
| **Questions** | Consultan el estado de la última respuesta REST |
| **Models** | POJOs que representan los recursos de la API |
| **Utils / Endpoints** | Centraliza las rutas de la API para evitar strings dispersos |
| **StepDefinitions** | Orquesta actores y tareas; no contiene lógica de negocio |
| **Runners** | Configuración del motor Cucumber sobre JUnit Platform |

### Flujo CRUD del escenario

```
Actor: "el usuario"
  │
  ├── 1. CreatePet   → POST   /pet              → 200 OK
  ├── 2. GetPet      → GET    /pet/{petId}       → 200 OK + nombre validado
  ├── 3. UpdatePet   → PUT    /pet               → 200 OK
  ├── 4. DeletePet   → DELETE /pet/{petId}       → 200 OK
  └── 5. GetPet      → GET    /pet/{petId}       → 404 Not Found (confirmación)
```

---

## Configuración de la URL Base

La URL base **no está hardcodeada** en el código. Se lee desde `src/test/resources/serenity.conf`:

```hocon
serenity {
  project.name = "Petstore API Screenplay"

  rest {
    base.url = "https://petstore.swagger.io/v2"
  }

  outputDirectory = "target/site/serenity"
  take.screenshots = "DISABLED"
  report.encoding = "UTF-8"
}
```

Para apuntar a un entorno diferente basta con cambiar `rest.base.url` en este archivo.

---

## Ejecución de las pruebas

### Opción 1 — Ejecutar pruebas y generar reporte en un solo comando

```bash
./gradlew clean test
```

> El reporte Serenity se genera automáticamente al finalizar gracias a la tarea `aggregate` configurada en `build.gradle.kts`.

### Opción 2 — Ejecutar pruebas y luego generar reporte por separado

```bash
./gradlew clean test
./gradlew aggregate
```

### En Windows (PowerShell)

```powershell
.\gradlew.bat clean test
```

---

## Visualizar el reporte Serenity

Al finalizar la ejecución, abrir en el navegador:

```
target/site/serenity/index.html
```

El reporte muestra:
- Detalle de cada petición y respuesta REST
- Código de estado validado en cada paso
- Narrativa del escenario en lenguaje de negocio
- Resultados con trazabilidad completa por actor y tarea

---

## Buenas prácticas aplicadas

- ✅ Sin URL hardcodeada — la URL base viene de `serenity.conf`
- ✅ Sin `ObjectMapper` manual — la serialización la gestiona Serenity Screenplay REST
- ✅ `SerenityRest.lastResponse()` en las `Questions` para visibilidad en reportes
- ✅ Un único paquete `models/` sin duplicados
- ✅ `screenshots` deshabilitados (no aplican a pruebas de API)
- ✅ Cada `Task` tiene una única responsabilidad (Principio SRP)
- ✅ `StepDefinitions` sin lógica de negocio embebida
