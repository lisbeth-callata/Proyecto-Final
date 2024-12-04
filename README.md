# **Proyecto Final: Microservicios**

---

## Descripción
Este es el proyecto final del **NTTDATA Bootcamp** en el contexto de **Microservicios**, basado en un sistema bancario. 
El proyecto extiende las funcionalidades del sistema que se desarrollaron en los proyectos anteriores, con nuevas características, mejoras de diseño y optimización en el código. Se utiliza una arquitectura de microservicios para gestionar operaciones bancarias, como la creación de cuentas, la gestión de clientes y la realización de transacciones (depósitos, retiros y transferencias).

---

## Tecnologías utilizadas

- **Spring Boot**: Framework principal para el desarrollo de microservicios
- **MongoDB**: Base de datos NoSQL utilizada para almacenar las cuentas y transacciones.
- **JUnit y Mockito**: Para pruebas unitarias y simulación de componentes (mocks) en los servicios.
- **SonarLint/Jacoco**: Herramienta de calidad de código que verifica errores y mejora el estilo de programación.
- **Checkstyle**: Para mantener el estilo de codificación coherente.
- **SOLID**: Principios de diseño orientados a objetos aplicados en las clases del proyecto.
- **Patrones de Diseño**: Se aplicaron patrones como el *Factory* y el *Singleton* para mejorar la escalabilidad y la gestión del código.
- **Redis y Kafka**: (Teoría) Se explica el uso de estas tecnologías en el contexto del sistema bancario y sus aplicaciones hipotéticas.

---

## Funcionalidades implementadas

Este sistema gestiona varias operaciones bancarias a través de tres microservicios principales: Cuentas - Clientes - Transacciones

1. **Pruebas unitarias**: Se implementaron pruebas unitarias usando JUnit para todas las clases principales del sistema.
   - Para aquellos métodos que interactúan con dependencias externas (por ejemplo, base de datos), se utilizaron **mocks** a través de Mockito para simular el comportamiento esperado.
     ![mock](https://github.com/user-attachments/assets/fba3d129-edd5-48d7-851c-7fbc2082dc12)

   
2. **Cobertura de código**: Se generó un informe de cobertura de código usando **Jacoco** y **SonarLint**, con el objetivo de asegurar que se ha alcanzado un nivel adecuado de pruebas en el código.
    ![jacoco](https://github.com/user-attachments/assets/df960a39-1c55-478a-8652-ddb73361bbcd)


3. **Verificación de calidad de código**: Se aplicó **Checkstyle** para asegurar que el código sigue las mejores prácticas de estilo y convenciones establecidas.
   ![check](https://github.com/user-attachments/assets/997967bd-39a5-4a3b-a5bb-363669e6499b)

4. **Documentación de la API (OpenAPI)**:
   La documentación de la API está disponible en formato **OpenAPI** y describe todas las rutas de la API, los parámetros y las respuestas.
    Para mostrar el contrato se debe ejecutar el proyecto e ingresar al siguiente link:
    - http://localhost:8085/v3/api-docs
    - http://localhost:8085/v3/api-docs.yaml
   ![yml](https://github.com/user-attachments/assets/b72946b7-2a2f-41d9-a1cd-b3a46de7303d)

6. **Aplicación de SOLID**: El código fue evaluado para aplicar los principios de **SOLID**, buscando que cada componente fuera modular, reutilizable y mantenible.

7. **Patrones de diseño**: Se emplearon patrones de diseño adecuados en la solución, con el fin de mejorar la estructura y la extensibilidad del código.

---

## Aporte Redis-Kafka

Un documento llamado **Aporte Redis-Kafka.docx** detalla un caso de uso real y un caso hipotético de cómo **Redis** y **Kafka** pueden ser utilizados en este sistema bancario:

- **Redis**: Como almacenamiento en caché para las cuentas más consultadas, reduciendo la carga en la base de datos.
- **Kafka**: Para la mensajería entre microservicios, especialmente útil para manejar grandes volúmenes de transacciones en tiempo real.

---

## Instalación

Sigue estos pasos para instalar y ejecutar el proyecto en tu máquina local:

1. **Clona el repositorio**:
   *git clone https://github.com/lisbeth-callata/Proyecto-4.git*
2. **Navega al directorio del proyecto:**
   *cd Proyecto-4*
3. **Dependencias:**
   Asegúrate de tener las dependencias necesarias para ejecutar el proyecto. Si se emplea Maven, instalar todas las dependencias con el comando: *mvn install*

---

## **Autor**

- [Lisbeth Callata](https://github.com/lisbeth-callata)
