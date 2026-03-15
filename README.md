# Ridesharing API (Backend en Java) 

Este repositorio contiene una Prueba de Concepto (PoC) académica para el desarrollo de una API backend orientada a la gestión de viajes compartidos (estilo BlaBlaCar). 

El objetivo principal de este proyecto fue asentar las bases de la **Programación Orientada a Objetos (OOP)** en Java y el diseño lógico de un sistema de reservas, centrándose en la estructuración de las entidades principales (Usuarios, Viajes, Vehículos y Reservas).

## Características principales
* **Gestión de Usuarios:** Lógica para perfiles de conductores y pasajeros, con posibilidad de darse de baja.
* **Publicación de Viajes:** Creación de rutas con origen, destino, fecha y plazas disponibles. Además, estos viajes pueden incluir descuentos y/o se les puede añadir un seguro de viaje.
* **Sistema de Reservas:** Lógica transaccional para ocupar asientos en un coche y actualizar el estado del viaje.
* **Gestión de Monedero Virtual:** Implementación de un sistema de saldos interno para gestionar la lógica de pagos y cobros entre pasajeros y conductores.
* **Notificaciones:** Simulación de un servicio de envío de emails automáticos para la confirmación de las reservas.

## Tecnologías empleadas
* **Lenguaje:** Java
* * **Persistencia:** Base de datos local para el almacenamiento de usuarios, rutas y transacciones.
* **Enfoque:** Programación Orientada a Objetos y lógica de negocio.

> **Nota sobre el estado del proyecto:** Este repositorio es un proyecto de carácter puramente académico. Representa una iteración temprana enfocada en el aprendizaje de la arquitectura de software en Java. Cuenta con persistencia de datos en entorno local, pero no está adaptado para despliegues en la nube ni cuenta con estándares de ciberseguridad para producción.
