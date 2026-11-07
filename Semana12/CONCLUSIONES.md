# Conclusiones y preparación para la sustentación

**Estudiante:** De La Cruz Zavala, Noemi · **Laboratorio 07 – Sistema de Atención Veterinaria**

## Cinco conclusiones técnicas

1. **Separar por responsabilidad de negocio facilita el mantenimiento.**
   Propietario, Mascota y Consulta cambian por razones distintas; al ser servicios independientes (puertos 8081, 8082 y 8083) puedo modificar, probar o desplegar uno sin tocar los demás.

2. **Database per service: cada microservicio es dueño de sus datos.**
   Cada servicio usa su propia base (`bd_propietario`, `bd_mascota`, `bd_consulta`) y nunca lee las tablas de otro. Las relaciones se representan con IDs (`idPropietario`, `idMascota`), lo que reduce el acoplamiento aunque se pierdan las llaves foráneas.

3. **Spring Data JPA reduce el código de persistencia.**
   Con `@Entity` y `JpaRepository` obtuve el CRUD sin escribir SQL, y con métodos derivados (`existsByDni`, `findByIdPropietario`) resolví consultas del negocio. Con `ddl-auto: update` en dev y `validate` en prod evito que Hibernate altere la base real.

4. **Los DTO y las validaciones protegen la API.**
   Usar DTO (`Request`/`Response`) evita exponer la entidad, y `@Valid` con anotaciones (`@NotBlank`, `@Pattern`, `@DecimalMin`) rechaza datos inválidos, como un costo negativo, antes de llegar a la base de datos.

5. **El manejo centralizado de errores y los perfiles hacen la API predecible.**
   Un `@RestControllerAdvice` convierte cada fallo en una respuesta JSON uniforme con el código HTTP correcto (400, 404, 409, 422, 503). Los perfiles `dev`/`prod` y las variables de entorno permiten cambiar de ambiente sin credenciales en el repositorio.

## Dificultad encontrada y solución

**Dificultad:** al tener bases de datos separadas no puedo usar una llave foránea, así que era posible registrar una mascota con un `idPropietario` inexistente, o una consulta para una mascota que no existe, dejando datos huérfanos.

**Solución:** creé un cliente HTTP liviano (`PropietarioClient` en mascota y `MascotaClient` en consulta, con `RestTemplate` y timeouts) que verifica por GET que el ID exista antes de guardar. Si no existe, responde **422** con un mensaje claro; si el otro servicio está caído, responde **503**. La URL del otro servicio y la opción de validar se configuran en `application.yml`, sin necesidad de una plataforma de descubrimiento.

## Respuesta modelo a la pregunta de sustentación

> *Explique por qué separó el sistema en estos servicios, cómo persiste los datos y qué ocurre cuando la API recibe información inválida.*

**¿Por qué estos servicios?** Porque Propietario, Mascota y Consulta son tres conceptos del negocio con ciclos de cambio distintos. Cada uno tiene su propio puerto y su propia base de datos, así se desarrollan, prueban y escalan por separado, y una falla en uno no tumba a los demás.

**¿Cómo persiste los datos?** Con Spring Data JPA y MySQL. Cada servicio tiene su entidad (`@Entity`), su `JpaRepository` y una capa de servicio transaccional (`@Transactional`) que usa los DTO. La conexión se configura en `application.yml` con perfiles `dev` y `prod`. Entre servicios solo guardo IDs y los valido por HTTP.

**¿Qué pasa con información inválida?** El DTO se valida con `@Valid` antes de entrar a la lógica. Si falla, el `GlobalExceptionHandler` responde **400** con un JSON que indica campo por campo qué está mal (por ejemplo, `"costo": "El costo no puede ser negativo"`). Un recurso inexistente devuelve **404**, un DNI repetido **409**, un ID que no existe en otro servicio **422**, y los errores no previstos devuelven un **500** genérico sin exponer detalles internos.

## Posibles cambios pequeños en vivo (para practicar)

- Agregar un campo (por ejemplo `raza` en Mascota): entidad + DTO + columna en la BD.
- Agregar una validación (por ejemplo, costo máximo): anotación en `ConsultaRequest`.
- Agregar un endpoint de búsqueda (por ejemplo `GET /api/propietarios/dni/{dni}`): método en repository + service + controller.
- Cambiar el puerto o el perfil: `SERVER_PORT` / `SPRING_PROFILES_ACTIVE`.
