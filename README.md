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

