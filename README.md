# API REST - Empresa XYZ (Camiones y Conductores)

Proyecto generado con **Spring Initializr** (Spring Boot 4.1.0, Java 21) al que se le agregó
la lógica de negocio solicitada.

- Registro de **camiones** (placa, tipo de vehículo).
- Registro de **conductores**.
- Asociación de conductores a camiones.
- Dos roles: **ADMIN** y **SUPERVISOR**.
  - ADMIN: puede registrar camiones y conductores.
  - SUPERVISOR: solo puede asociar conductores a camiones.
- API REST segura con **Basic Auth** (Spring Security 7). No hay endpoints públicos: toda petición pasa por el `filter chain` y requiere autenticación.
- Base de datos **H2** en memoria.

## Notas sobre Spring Boot 4 (importante)

Como este proyecto usa Spring Boot 4.1.0, hay un par de cosas que cambiaron respecto a versiones 3.x
y que ya están resueltas en el `pom.xml`:

- `spring-boot-starter-web` fue reemplazado por `spring-boot-starter-webmvc` (ya lo trae tu proyecto).
- La consola H2 ya no viene incluida por defecto: se necesita el módulo `spring-boot-h2console` (ya lo trae tu proyecto).
- La validación (`@Valid`, `@NotBlank`, etc.) ya no viene incluida en el starter web: se agregó `spring-boot-starter-validation`.
- CSRF ahora viene habilitado por defecto también para APIs REST. Como esta API es *stateless* (Basic Auth, sin sesión), se desactiva explícitamente en `SecurityConfig`.

## Cómo ejecutar

Requisitos: Java 21+.

```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

## Usuarios de prueba (Basic Auth)

| Usuario     | Clave       | Rol         |
|-------------|-------------|-------------|
| admin       | admin123    | ADMIN       |
| supervisor  | super123    | SUPERVISOR  |

## Endpoints

Todos requieren cabecera `Authorization: Basic <usuario:clave en base64>`.

| Método | Endpoint                                             | Rol permitido        | Descripción                    |
|--------|-------------------------------------------------------|-----------------------|---------------------------------|
| POST   | `/api/camiones`                                       | ADMIN                 | Registrar camión                |
| GET    | `/api/camiones`                                       | ADMIN, SUPERVISOR     | Listar camiones                 |
| GET    | `/api/camiones/{id}`                                  | ADMIN, SUPERVISOR     | Ver un camión                   |
| PUT    | `/api/camiones/{id}/asociar-conductor/{conductorId}`  | ADMIN, SUPERVISOR     | Asociar conductor a camión      |
| POST   | `/api/conductores`                                    | ADMIN                 | Registrar conductor             |
| GET    | `/api/conductores`                                    | ADMIN, SUPERVISOR     | Listar conductores              |
| GET    | `/api/conductores/{id}`                               | ADMIN, SUPERVISOR     | Ver un conductor                |

## Ejemplos con curl

Registrar un camión (solo ADMIN):
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/camiones \
  -H "Content-Type: application/json" \
  -d '{"placa":"ABC123","tipoVehiculo":"Camion refrigerado"}'
```

Registrar un conductor (solo ADMIN):
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/conductores \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Perez","documento":"123456789"}'
```

Asociar conductor a camión (ADMIN o SUPERVISOR):
```bash
curl -u supervisor:super123 -X PUT http://localhost:8080/api/camiones/1/asociar-conductor/1
```

Listar camiones:
```bash
curl -u supervisor:super123 http://localhost:8080/api/camiones
```

## Consola H2 (opcional, solo ADMIN)

`http://localhost:8080/h2-console` — JDBC URL: `jdbc:h2:mem:xyzdb`, usuario `sa`, sin clave.

## Estructura agregada

```
src/main/java/com/empresa/xyz/
 ├── XyzApplication.java               
 ├── config/SecurityConfig.java         
 ├── model/Camion.java
 ├── model/Conductor.java
 ├── repository/CamionRepository.java
 ├── repository/ConductorRepository.java
 └── controller/CamionController.java
 └── controller/ConductorController.java
src/main/resources/application.properties  
```
