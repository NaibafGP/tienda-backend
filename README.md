# API REST de Gestión de Tienda (Spring Boot)

Este es un proyecto backend de ejemplo estructurado bajo buenas prácticas de diseño de software y arquitectura en capas. Desarrollado con **Java**, **Spring Boot**, **Spring Data JPA** y base de datos en memoria **H2** para facilitar el despliegue inmediato.

## 🛠️ Tecnologías Utilizadas

*   **Java 17**
*   **Spring Boot 3.2.x** (Spring Web, Spring Data JPA)
*   **Base de datos H2** (Base de datos relacional SQL en memoria)
*   **JUnit 5** & **Mockito** (Para pruebas unitarias de lógica de negocio)
*   **Maven** (Gestor de dependencias)

---

## 🏗️ Arquitectura del Proyecto

El código está organizado en capas para asegurar la separación de responsabilidades y la facilidad de mantenimiento:

1.  **Model (`com.tienda.model`):** Contiene la entidad de base de datos `Producto`.
2.  **Repository (`com.tienda.repository`):** Interfaz que extiende de `JpaRepository` para la comunicación con la base de datos SQL.
3.  **Service (`com.tienda.service`):** Capa lógica de negocio. Contiene las validaciones y reglas operativas, como la regla de impedir ventas si el stock es insuficiente.
4.  **Controller (`com.tienda.controller`):** Expone las rutas REST y mapea las peticiones HTTP (`GET`, `POST`, `DELETE`).

---

## 📡 Endpoints del API REST

La base del API se encuentra en: `http://localhost:8080/api/productos`

| Método | Ruta | Descripción | Cuerpo / Parámetros |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/productos` | Obtiene la lista de todos los productos en stock. | Ninguno |
| **GET** | `/api/productos/{id}` | Obtiene el detalle de un producto por ID. | Ninguno |
| **POST** | `/api/productos` | Registra un nuevo producto. | JSON con `nombre`, `precio`, `stock` |
| **DELETE** | `/api/productos/{id}` | Elimina un producto por ID. | Ninguno |
| **POST** | `/api/productos/{id}/vender` | Simula la venta de un producto (resta stock). | Parámetro `cantidad` en URL |

---

## 🧪 Pruebas Unitarias (JUnit & Mockito)

El proyecto incluye pruebas automatizadas para la regla de negocio crítica (`realizarVenta`) ubicada en la capa de servicios.
Las pruebas simulan la comunicación de la base de datos a través de **Mockito**, validando:
1.  **Escenario Exitoso:** Descontar stock correctamente cuando hay existencias suficientes.
2.  **Escenario Fallido:** Lanzar una excepción de tipo `RuntimeException` e impedir la transacción si el stock solicitado es mayor al disponible.

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Requisitos Previos
*   Tener instalado un Kit de Desarrollo de Java (**JDK 17** o superior).
*   Un editor o IDE como **IntelliJ IDEA** o **Visual Studio Code**.

### 2. Pasos para Correr la Aplicación
1.  Abre el proyecto en tu editor/IDE preferido.
2.  Importa el proyecto como un proyecto de **Maven** (esto descargará las dependencias necesarias automáticamente).
3.  Ejecuta la clase principal `TiendaApplication.java`.
4.  El servidor iniciará en el puerto `8080`.
5.  Puedes ingresar a la consola de la base de datos H2 en el navegador mediante: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:tiendadb`, usuario `sa` y contraseña vacía).

### 3. Ejecutar las Pruebas
Puedes correr las pruebas del proyecto ejecutando el siguiente comando en la terminal:
```bash
./mvnw test
```
O directamente haciendo clic derecho sobre la clase `ProductoServiceTest` en tu IDE y seleccionando **"Run 'ProductoServiceTest'"**.
