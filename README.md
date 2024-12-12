# FinancyWise API

![Logo](./images/logo.png)

## Idea
FinancyWise es una aplicación para gestionar finanzas personales. Su propósito es ayudar a los usuarios a rastrear ingresos y gastos, analizar su comportamiento financiero y tomar decisiones más informadas para mejorar su bienestar económico.

## Justificación de la Idea

### 1. **Problema Detectado**
La falta de control sobre los ingresos y gastos dificulta la planificación financiera, llevando a decisiones económicas poco efectivas. Muchas personas no cuentan con herramientas claras para visualizar sus hábitos de consumo.

### 2. **Solución Propuesta**
FinancyWise ofrece:
- Una plataforma intuitiva para registrar y organizar transacciones.
- Visualizaciones detalladas de los patrones de gasto e ingreso.
- Insights personalizados que fomenten el ahorro y mejoren el uso de los recursos.
## Tablas

### 1. Tabla `usuarios`
Contiene la información de los usuarios.

| Campo            | Tipo            | Detalles                      |
|------------------|-----------------|-------------------------------|
| `id_usuario`     | BIGINT          | PK, autoincremental           |
| `username`       | VARCHAR(100)    | No nulo                       |
| `email`          | VARCHAR(150)    | No nulo, único                |
| `password`       | VARCHAR(255)    | No nulo (almacenada de forma segura) |
| `fecha_creacion` | TIMESTAMP     | No nulo, valor por defecto ahora() |

---

### 2. Tabla `gastos`
Contiene los gastos realizados por los usuarios.

| Campo          | Tipo         | Detalles                      |
|----------------|--------------|-------------------------------|
| `id_gasto`     | BIGINT       | PK, autoincremental           |
| `id_usuario`   | BIGINT       | FK, referencia a `usuarios.id_usuario` |
| `categoria`    | VARCHAR(50)  | No nulo (ej.: comida, transporte) |
| `descripcion`  | VARCHAR(255) | Opcional                      |
| `monto`        | DECIMAL(10,2)| No nulo                       |
| `fecha`        | DATE         | No nulo                       |

---

### 3. Tabla `ingresos`
Contiene los ingresos registrados por los usuarios.

| Campo          | Tipo         | Detalles                      |
|----------------|--------------|-------------------------------|
| `id_ingreso`   | BIGINT       | PK, autoincremental           |
| `id_usuario`   | BIGINT       | FK, referencia a `usuarios.id_usuario` |
| `categoria`    | VARCHAR(50)  | No nulo (ej.: sueldo, inversión) |
| `descripcion`  | VARCHAR(255) | Opcional                      |
| `monto`        | DECIMAL(10,2)| No nulo                       |
| `fecha`        | DATE         | No nulo                       |

---

# Modelo Entidad - Relación

![ER](./images/ER.png)

## Endpoints

### 1. **Usuarios**
- **GET** `/usuarios`: Obtiene la lista de todos los usuarios.  
  **Respuesta**:
    - **200 OK**: Lista de usuarios en formato JSON.
    - **404 Not Found**: No se encuentran usuarios registrados.

- **GET** `/usuarios/{id}`: Obtiene los detalles de un usuario específico por su ID.  
  **Respuesta**:
    - **200 OK**: Detalles del usuario en formato JSON.
    - **404 Not Found**: "Usuario no encontrado" si el ID no existe.

- **POST** `/usuarios`: Crea un nuevo usuario. Se espera que el cuerpo de la solicitud contenga los detalles del usuario en formato JSON.  
  **Respuesta**:
    - **201 Created**: Usuario creado exitosamente.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **PUT** `/usuarios/{id}`: Actualiza los detalles de un usuario existente. Se espera que el cuerpo de la solicitud contenga los nuevos datos del usuario.  
  **Respuesta**:
    - **200 OK**: Usuario actualizado exitosamente.
    - **404 Not Found**: "Usuario no encontrado" si el ID no existe.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **DELETE** `/usuarios/{id}`: Elimina un usuario específico del sistema.  
  **Respuesta**:
    - **200 OK**: Usuario eliminado exitosamente.
    - **404 Not Found**: "Usuario no encontrado" si el ID no existe.

---

### 2. **Gastos**
- **GET** `/gastos`: Obtiene la lista de todos los gastos.  
  **Respuesta**:
    - **200 OK**: Lista de gastos en formato JSON.
    - **404 Not Found**: No se encuentran gastos registrados.

- **GET** `/gastos/{id}`: Obtiene los detalles de un gasto específico por su ID.  
  **Respuesta**:
    - **200 OK**: Detalles del gasto en formato JSON.
    - **404 Not Found**: "Gasto no encontrado" si el ID no existe.

- **POST** `/gastos`: Crea un nuevo gasto. Se espera que el cuerpo de la solicitud contenga los detalles del gasto en formato JSON.  
  **Respuesta**:
    - **201 Created**: Gasto creado exitosamente.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **PUT** `/gastos/{id}`: Actualiza un gasto existente. Se espera que el cuerpo de la solicitud contenga los nuevos detalles del gasto.  
  **Respuesta**:
    - **200 OK**: Gasto actualizado exitosamente.
    - **404 Not Found**: "Gasto no encontrado" si el ID no existe.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **DELETE** `/gastos/{id}`: Elimina un gasto específico del sistema.  
  **Respuesta**:
    - **200 OK**: Gasto eliminado exitosamente.
    - **404 Not Found**: "Gasto no encontrado" si el ID no existe.

---

### 3. **Ingresos**
- **GET** `/ingresos`: Obtiene la lista de todos los ingresos.  
  **Respuesta**:
    - **200 OK**: Lista de ingresos en formato JSON.
    - **404 Not Found**: No se encuentran ingresos registrados.

- **GET** `/ingresos/{id}`: Obtiene los detalles de un ingreso específico por su ID.  
  **Respuesta**:
    - **200 OK**: Detalles del ingreso en formato JSON.
    - **404 Not Found**: "Ingreso no encontrado" si el ID no existe.

- **POST** `/ingresos`: Crea un nuevo ingreso. Se espera que el cuerpo de la solicitud contenga los detalles del ingreso en formato JSON.  
  **Respuesta**:
    - **201 Created**: Ingreso creado exitosamente.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **PUT** `/ingresos/{id}`: Actualiza un ingreso existente. Se espera que el cuerpo de la solicitud contenga los nuevos detalles del ingreso.  
  **Respuesta**:
    - **200 OK**: Ingreso actualizado exitosamente.
    - **404 Not Found**: "Ingreso no encontrado" si el ID no existe.
    - **400 Bad Request**: Si los datos del cuerpo de la solicitud son inválidos.

- **DELETE** `/ingresos/{id}`: Elimina un ingreso específico del sistema.  
  **Respuesta**:
    - **200 OK**: Ingreso eliminado exitosamente.
    - **404 Not Found**: "Ingreso no encontrado" si el ID no existe.

---

## Lógica de Negocio

La aplicación **MoneyTracker** tiene como objetivo principal gestionar los ingresos y los gastos de los usuarios para ayudarles a tener un mejor control de sus finanzas. La lógica de negocio está diseñada de la siguiente manera:

- **Gestión de usuarios**: Los usuarios pueden registrarse, actualizar su información y eliminar su cuenta. Esto está relacionado con la autenticación y la gestión de la sesión del usuario.

- **Gestión de gastos e ingresos**: Los usuarios pueden agregar, actualizar y eliminar tanto sus gastos como sus ingresos. La aplicación realiza un seguimiento de las categorías para identificar dónde se concentra el gasto y cuánto dinero se está generando a través de los ingresos.

- **Análisis de datos**: A medida que el usuario agrega más transacciones, la aplicación debe generar informes que muestren un resumen de los ingresos y gastos. Estos informes ayudan al usuario a identificar áreas donde puede ahorrar y mejorar su situación financiera.

---

## Excepciones y Códigos de Estado

La aplicación manejará las siguientes excepciones y devolverá los códigos de estado correspondientes:

1. **Usuario no encontrado**:
    - **Código de estado**: 404 Not Found
    - **Mensaje**: "Usuario no encontrado"

2. **Gasto no encontrado**:
    - **Código de estado**: 404 Not Found
    - **Mensaje**: "Gasto no encontrado"

3. **Ingreso no encontrado**:
    - **Código de estado**: 404 Not Found
    - **Mensaje**: "Ingreso no encontrado"

4. **Validación de datos**:
    - **Código de estado**: 400 Bad Request
    - **Mensaje**: "Datos de solicitud no válidos"

5. **Error en el servidor**:
    - **Código de estado**: 500 Internal Server Error
    - **Mensaje**: "Ha ocurrido un error en el servidor"

