# 🎧 MusAPI

API para una plataforma de música donde los usuarios escuchan canciones, arman listas de reproducción y siguen a sus artistas, y donde los artistas pueden subir su propia música para darse a conocer.

## ✨ Funcionalidades

- **Usuarios y artistas:** registro, inicio de sesión con JWT y perfiles de artista.
- **Música:** subida de canciones y álbumes, categorías musicales y búsqueda de álbumes y artistas.
- **Listas de reproducción** y contenido guardado (canciones, álbumes y artistas favoritos).
- **Escuchas y estadísticas:** canciones y artistas más escuchados, y estadísticas personales.
- **Recomendaciones** según lo que escucha cada usuario.
- **Evaluaciones** de artistas, con calificación y comentario.
- **Colaboraciones:** un artista puede invitar a otro a colaborar en una canción.
- **Notificaciones** y **chat en tiempo real** con WebSockets.
- **Servicios gRPC** para álbumes y categorías musicales.
- **Administración:** eliminar usuarios y contenido que no cumpla las reglas.
- **Correos automáticos** (bienvenida, avisos de cuenta).

## 🧰 Tecnologías

| Área | Herramientas |
|---|---|
| Lenguaje y framework | Java 17, Spring Boot 3.4 |
| Seguridad | Spring Security, JWT, BCrypt |
| Base de datos | SQL Server, Spring Data JPA |
| Comunicación | REST, gRPC (Protocol Buffers) y WebSockets |
| Infraestructura | Docker, Docker Compose y Nginx como proxy inverso |
| CI | GitHub Actions |
| Pruebas | JUnit 5, MockMvc y Spring Security Test |

## 🏗️ Arquitectura

```
Cliente ──► Nginx (puerto 80) ──► API Spring Boot (8080) ──► SQL Server
                                   ├── REST  /api/...
                                   ├── WebSocket /ws/...
                                   └── gRPC (9090)
```

```
src/main/java/com/musapi
├── controller/   # Endpoints REST
├── service/      # Lógica de negocio
├── repository/   # Acceso a datos
├── model/        # Entidades JPA
├── dto/          # Objetos de entrada y salida
├── grpc/         # Servicios gRPC (src/main/proto)
├── ws/           # Chat por WebSocket
├── security/     # Filtro y utilidades de JWT
├── excepciones/  # Manejo global de errores
└── config/       # Seguridad, CORS y WebSockets
```

## ⚙️ Cómo correrlo

### 1. Clonar el repositorio

```bash
git clone https://github.com/AxelLuna04/API_MusAPI.git
cd API_MusAPI
```

### 2. Configurar las variables de entorno

Las contraseñas y claves **no están en el código**. Copia el archivo de ejemplo y llena los valores:

```bash
cp .env.example .env
```

| Variable | Para qué sirve |
|---|---|
| `DB_PASSWORD` | Contraseña de `musapi_user` (al correr la API en local, sin Docker) |
| `DB_SA_PASSWORD` | Contraseña del usuario `SA` de SQL Server (con Docker Compose) |
| `JWT_SECRET` | Clave para firmar los tokens (genera una con `openssl rand -hex 32`) |
| `MAIL_PASSWORD` | [Contraseña de aplicación de Gmail](https://myaccount.google.com/apppasswords) para enviar correos |

El archivo `.env` está en `.gitignore`, así que nunca se sube a GitHub.

### 3a. Con Docker (recomendado)

```bash
docker compose up --build
```

Esto levanta SQL Server, crea la base de datos con `sql/sqlScript.sql`, construye la API y la publica detrás de Nginx en `http://localhost`.

### 3b. En local, sin Docker

Necesitas Java 17, Maven y un SQL Server con la base `MusAPI_DB` creada a partir de `sql/sqlScript.sql`.

```bash
mvn spring-boot:run
```

La API queda en `http://localhost:8080` y gRPC en el puerto `9090`.

## 🧪 Pruebas

Las pruebas de controladores levantan el contexto completo de Spring, así que necesitan la base de datos corriendo y el archivo `.env` configurado:

```bash
mvn test
```

## 🔌 Endpoints principales

| Ruta base | Descripción |
|---|---|
| `/api/usuarios` | Registro, login y perfil |
| `/api/canciones` | Subir, buscar y reproducir canciones |
| `/api/albumes` | Álbumes de los artistas |
| `/api/categoriasMusicales` | Géneros musicales |
| `/api/listasDeReproduccion` | Listas de reproducción |
| `/api/contenidoGuardado` | Contenido guardado del usuario |
| `/api/escucha`, `/api/estadisticas` | Registro de escuchas y estadísticas |
| `/api/recomendaciones` | Recomendaciones personalizadas |
| `/api/evaluaciones` | Evaluaciones de artistas |
| `/api/solicitudesDeColaboracion` | Colaboraciones entre artistas |
| `/api/notificaciones` | Notificaciones del usuario |
| `/api/admin` | Funciones de administrador |

<!--
## 👤 Mi participación
Este fue un proyecto en equipo. Describe aquí qué partes desarrollaste tú,
por ejemplo: "Desarrollé el chat por WebSockets y los servicios gRPC."
-->
