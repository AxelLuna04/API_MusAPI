USE [master]
GO
/****** Object:  Database [MusAPI_DB]    Script Date: 29/05/2025 06:41:29 p. m. ******/
CREATE DATABASE [MusAPI_DB]
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MusAPI_DB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MusAPI_DB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MusAPI_DB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MusAPI_DB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MusAPI_DB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MusAPI_DB] SET ARITHABORT OFF 
GO
ALTER DATABASE [MusAPI_DB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MusAPI_DB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MusAPI_DB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MusAPI_DB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MusAPI_DB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MusAPI_DB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MusAPI_DB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MusAPI_DB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MusAPI_DB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MusAPI_DB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MusAPI_DB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MusAPI_DB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MusAPI_DB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MusAPI_DB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MusAPI_DB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MusAPI_DB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MusAPI_DB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MusAPI_DB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [MusAPI_DB] SET  MULTI_USER 
GO
ALTER DATABASE [MusAPI_DB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MusAPI_DB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MusAPI_DB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MusAPI_DB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MusAPI_DB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [MusAPI_DB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [MusAPI_DB] SET QUERY_STORE = ON
GO
ALTER DATABASE [MusAPI_DB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [MusAPI_DB]
GO
/****** Object:  User [musapi_user]    Script Date: 29/05/2025 06:41:29 p. m. ******/
CREATE USER [musapi_user] FOR LOGIN [musapi_user] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [AdminMusAPI]    Script Date: 29/05/2025 06:41:29 p. m. ******/
CREATE USER [AdminMusAPI] FOR LOGIN [AdminMusAPI] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [musapi_user]
GO
ALTER ROLE [db_owner] ADD MEMBER [AdminMusAPI]
GO
/****** Object:  Table [dbo].[Album]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Album](
	[idAlbum] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[fechaPublicacion] [date] NOT NULL,
	[urlFoto] [varchar](300) NOT NULL,
	[idPerfilArtista] [int] NOT NULL,
	[estado] [varchar](15) NOT NULL,
 CONSTRAINT [PK_Album] PRIMARY KEY CLUSTERED 
(
	[idAlbum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cancion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cancion](
	[idCancion] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[urlArchivo] [varchar](300) NOT NULL,
	[duracion] [time](0) NOT NULL,
	[fechaPublicacion] [date] NOT NULL,
	[urlFoto] [varchar](300) NOT NULL,
	[idCategoriaMusical] [int] NOT NULL,
	[idAlbum] [int] NULL,
	[posicionEnAlbum] [int] NULL,
	[estado] [varchar](15) NOT NULL,
 CONSTRAINT [PK_Cancion] PRIMARY KEY CLUSTERED 
(
	[idCancion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CategoriaMusical]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CategoriaMusical](
	[idCategoriaMusical] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[descripcion] [varchar](300) NOT NULL,
 CONSTRAINT [PK_CategoriaMusical] PRIMARY KEY CLUSTERED 
(
	[idCategoriaMusical] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContenidoGuardado]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContenidoGuardado](
	[idRelacion] [int] IDENTITY(1,1) NOT NULL,
	[idUsuario] [int] NOT NULL,
	[idCancion] [int] NULL,
	[idAlbum] [int] NULL,
	[idListaDeReproduccion] [int] NULL,
	[idPerfilArtista] [int] NULL,
 CONSTRAINT [PK_ContenidoGuardado] PRIMARY KEY CLUSTERED 
(
	[idRelacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Escucha]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Escucha](
	[idEscucha] [int] IDENTITY(1,1) NOT NULL,
	[idCancion] [int] NOT NULL,
	[idUsuario] [int] NOT NULL,
	[tiempoEscucha] [time](0) NULL,
	[fechaEscucha] [date] NOT NULL,
 CONSTRAINT [PK_Escucha] PRIMARY KEY CLUSTERED 
(
	[idEscucha] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Evaluacion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Evaluacion](
	[idEvaluacion] [int] IDENTITY(1,1) NOT NULL,
	[calificacion] [int] NOT NULL,
	[comentario] [varchar](300) NULL,
	[idUsuario] [int] NOT NULL,
	[idPerfilArtista] [int] NOT NULL,
 CONSTRAINT [PK_Evaluacion] PRIMARY KEY CLUSTERED 
(
	[idEvaluacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ListaDeReproduccion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ListaDeReproduccion](
	[idListaDeReproduccion] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[descripcion] [varchar](300) NOT NULL,
	[urlFoto] [varchar](300) NULL,
	[idUsuario] [int] NOT NULL,
 CONSTRAINT [PK_ListaDeReproduccion] PRIMARY KEY CLUSTERED 
(
	[idListaDeReproduccion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ListaDeReproduccion_Cancion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ListaDeReproduccion_Cancion](
	[idRelacion] [int] IDENTITY(1,1) NOT NULL,
	[posicionCancion] [int] NOT NULL,
	[idListaDeReproduccion] [int] NOT NULL,
	[idCancion] [int] NOT NULL,
 CONSTRAINT [PK_ListaDeReproduccion_Cancion] PRIMARY KEY CLUSTERED 
(
	[idRelacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Notificacion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Notificacion](
	[idNotificacion] [int] IDENTITY(1,1) NOT NULL,
	[mensaje] [varchar](300) NOT NULL,
	[fechaEnvio] [date] NOT NULL,
	[fueLeida] [bit] NOT NULL,
	[idUsuario] [int] NOT NULL,
 CONSTRAINT [PK_Notificacion] PRIMARY KEY CLUSTERED 
(
	[idNotificacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PerfilArtista]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PerfilArtista](
	[idPerfilArtista] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](300) NULL,
	[urlFoto] [varchar](300) NULL,
	[idUsuario] [int] NOT NULL,
 CONSTRAINT [PK_PerfilArtista] PRIMARY KEY CLUSTERED 
(
	[idPerfilArtista] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PerfilArtista_Cancion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PerfilArtista_Cancion](
	[idRelacion] [int] IDENTITY(1,1) NOT NULL,
	[idPerfilArtista] [int] NOT NULL,
	[idCancion] [int] NOT NULL,
 CONSTRAINT [PK_PerfilArtista_Cancion] PRIMARY KEY CLUSTERED 
(
	[idRelacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SolicitudColaboracion]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SolicitudColaboracion](
	[idNotificacion] [int] NOT NULL,
	[idCancion] [int] NOT NULL,
	[estado] [varchar](15) NOT NULL,
 CONSTRAINT [PK__Solicitu__AFE1D7E4112D824A] PRIMARY KEY CLUSTERED 
(
	[idNotificacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 29/05/2025 06:41:29 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[idUsuario] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NULL,
	[correo] [varchar](320) NOT NULL,
	[nombreUsuario] [varchar](30) NOT NULL,
	[pais] [varchar](100) NOT NULL,
	[esAdmin] [bit] NOT NULL,
	[esArtista] [bit] NOT NULL,
	[contrasenia] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[idUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[CategoriaMusical] ON 

INSERT [dbo].[CategoriaMusical] ([idCategoriaMusical], [nombre], [descripcion]) VALUES (1, N'Metal', N'Sonidos pesados e industriales')
INSERT [dbo].[CategoriaMusical] ([idCategoriaMusical], [nombre], [descripcion]) VALUES (2, N'Jazz', N'Ejemplo de actualizar una categoria musical')
INSERT [dbo].[CategoriaMusical] ([idCategoriaMusical], [nombre], [descripcion]) VALUES (3, N'Rock', N'Sonido rítmico reconocido por la predominancia de la guitarra eléctrica, los compases de 4/4 y una estructura verso-estribillo ')
INSERT [dbo].[CategoriaMusical] ([idCategoriaMusical], [nombre], [descripcion]) VALUES (4, N'Pop', N'Sonidos de gusto popular en 4/4 y estribillos pegajosos')
SET IDENTITY_INSERT [dbo].[CategoriaMusical] OFF
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON 

INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (1, N'Jarly Hernández', N'jarly@example.com', N'jarly99', N'México', 0, 1, N'Temporal123')
INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (2, N'Axel de Jesus Luna Hernandez', N'axel.lu04@gmail.com', N'AxelLuna', N'Mexico', 1, 0, N'Temporal123')
INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (4, N'Actualizado', N'jarly@example.com', N'jarly99', N'Perú', 0, 1, N'Temporal123')
INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (5, N'Jarly', N'jarly@example.com', N'jarly99', N'México', 0, 1, N'$2a$10$429DxcAOP4O4KZBEzykHfeTVAVJLAQfhevJaU7A7aLBR3sdNnBN6m')
INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (6, N'Ejemplo', N'ejemplo@example.com', N'ejemplito', N'México', 0, 1, N'$2a$10$Xd8gVwHvbFI4UCzBevJt3OvmhPNlPXX2TnCVsswg.CBk0DGTkN2SO')
INSERT [dbo].[Usuario] ([idUsuario], [nombre], [correo], [nombreUsuario], [pais], [esAdmin], [esArtista], [contrasenia]) VALUES (7, N'Usuario prueba2', N'Usuario prueba2', N'Usuario prueba2', N'Usuario prueba2', 0, 1, N'$2a$10$HobLROubcNXGpAvLvWoASufYZIqDYkWUifEU9LYk6m8.0LbfQqR1W')
SET IDENTITY_INSERT [dbo].[Usuario] OFF
GO
ALTER TABLE [dbo].[SolicitudColaboracion] ADD  CONSTRAINT [DF__Solicitud__estad__17F790F9]  DEFAULT ((0)) FOR [estado]
GO
ALTER TABLE [dbo].[Usuario] ADD  DEFAULT ('Temporal123') FOR [contrasenia]
GO
ALTER TABLE [dbo].[Album]  WITH CHECK ADD  CONSTRAINT [FK_Album_PerfilArtista] FOREIGN KEY([idPerfilArtista])
REFERENCES [dbo].[PerfilArtista] ([idPerfilArtista])
GO
ALTER TABLE [dbo].[Album] CHECK CONSTRAINT [FK_Album_PerfilArtista]
GO
ALTER TABLE [dbo].[Cancion]  WITH CHECK ADD  CONSTRAINT [FK_Cancion_Album] FOREIGN KEY([idAlbum])
REFERENCES [dbo].[Album] ([idAlbum])
GO
ALTER TABLE [dbo].[Cancion] CHECK CONSTRAINT [FK_Cancion_Album]
GO
ALTER TABLE [dbo].[Cancion]  WITH CHECK ADD  CONSTRAINT [FK_Cancion_CategoriaMusical] FOREIGN KEY([idCategoriaMusical])
REFERENCES [dbo].[CategoriaMusical] ([idCategoriaMusical])
GO
ALTER TABLE [dbo].[Cancion] CHECK CONSTRAINT [FK_Cancion_CategoriaMusical]
GO
ALTER TABLE [dbo].[ContenidoGuardado]  WITH CHECK ADD  CONSTRAINT [FK_ContenidoGuardado_Album] FOREIGN KEY([idAlbum])
REFERENCES [dbo].[Album] ([idAlbum])
GO
ALTER TABLE [dbo].[ContenidoGuardado] CHECK CONSTRAINT [FK_ContenidoGuardado_Album]
GO
ALTER TABLE [dbo].[ContenidoGuardado]  WITH CHECK ADD  CONSTRAINT [FK_ContenidoGuardado_Cancion] FOREIGN KEY([idCancion])
REFERENCES [dbo].[Cancion] ([idCancion])
GO
ALTER TABLE [dbo].[ContenidoGuardado] CHECK CONSTRAINT [FK_ContenidoGuardado_Cancion]
GO
ALTER TABLE [dbo].[ContenidoGuardado]  WITH CHECK ADD  CONSTRAINT [FK_ContenidoGuardado_ListaDeReproduccion] FOREIGN KEY([idListaDeReproduccion])
REFERENCES [dbo].[ListaDeReproduccion] ([idListaDeReproduccion])
GO
ALTER TABLE [dbo].[ContenidoGuardado] CHECK CONSTRAINT [FK_ContenidoGuardado_ListaDeReproduccion]
GO
ALTER TABLE [dbo].[ContenidoGuardado]  WITH CHECK ADD  CONSTRAINT [FK_ContenidoGuardado_PerfilArtista] FOREIGN KEY([idPerfilArtista])
REFERENCES [dbo].[PerfilArtista] ([idPerfilArtista])
GO
ALTER TABLE [dbo].[ContenidoGuardado] CHECK CONSTRAINT [FK_ContenidoGuardado_PerfilArtista]
GO
ALTER TABLE [dbo].[ContenidoGuardado]  WITH CHECK ADD  CONSTRAINT [FK_ContenidoGuardado_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[ContenidoGuardado] CHECK CONSTRAINT [FK_ContenidoGuardado_Usuario]
GO
ALTER TABLE [dbo].[Escucha]  WITH CHECK ADD  CONSTRAINT [FK_Escucha_Cancion] FOREIGN KEY([idCancion])
REFERENCES [dbo].[Cancion] ([idCancion])
GO
ALTER TABLE [dbo].[Escucha] CHECK CONSTRAINT [FK_Escucha_Cancion]
GO
ALTER TABLE [dbo].[Escucha]  WITH CHECK ADD  CONSTRAINT [FK_Escucha_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Escucha] CHECK CONSTRAINT [FK_Escucha_Usuario]
GO
ALTER TABLE [dbo].[Evaluacion]  WITH CHECK ADD  CONSTRAINT [FK_Evaluacion_PerfilArtista] FOREIGN KEY([idPerfilArtista])
REFERENCES [dbo].[PerfilArtista] ([idPerfilArtista])
GO
ALTER TABLE [dbo].[Evaluacion] CHECK CONSTRAINT [FK_Evaluacion_PerfilArtista]
GO
ALTER TABLE [dbo].[Evaluacion]  WITH CHECK ADD  CONSTRAINT [FK_Evaluacion_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Evaluacion] CHECK CONSTRAINT [FK_Evaluacion_Usuario]
GO
ALTER TABLE [dbo].[ListaDeReproduccion]  WITH CHECK ADD  CONSTRAINT [FK_ListaDeReproduccion_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[ListaDeReproduccion] CHECK CONSTRAINT [FK_ListaDeReproduccion_Usuario]
GO
ALTER TABLE [dbo].[ListaDeReproduccion_Cancion]  WITH CHECK ADD  CONSTRAINT [FK_ListaDeReproduccion_Cancion_Cancion] FOREIGN KEY([idCancion])
REFERENCES [dbo].[Cancion] ([idCancion])
GO
ALTER TABLE [dbo].[ListaDeReproduccion_Cancion] CHECK CONSTRAINT [FK_ListaDeReproduccion_Cancion_Cancion]
GO
ALTER TABLE [dbo].[ListaDeReproduccion_Cancion]  WITH CHECK ADD  CONSTRAINT [FK_ListaDeReproduccion_Cancion_ListaDeReproduccion] FOREIGN KEY([idListaDeReproduccion])
REFERENCES [dbo].[ListaDeReproduccion] ([idListaDeReproduccion])
GO
ALTER TABLE [dbo].[ListaDeReproduccion_Cancion] CHECK CONSTRAINT [FK_ListaDeReproduccion_Cancion_ListaDeReproduccion]
GO
ALTER TABLE [dbo].[Notificacion]  WITH CHECK ADD  CONSTRAINT [FK_Notificacion_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Notificacion] CHECK CONSTRAINT [FK_Notificacion_Usuario]
GO
ALTER TABLE [dbo].[PerfilArtista_Cancion]  WITH CHECK ADD  CONSTRAINT [FK_PerfilArtista_Cancion_Cancion] FOREIGN KEY([idCancion])
REFERENCES [dbo].[Cancion] ([idCancion])
GO
ALTER TABLE [dbo].[PerfilArtista_Cancion] CHECK CONSTRAINT [FK_PerfilArtista_Cancion_Cancion]
GO
ALTER TABLE [dbo].[PerfilArtista_Cancion]  WITH CHECK ADD  CONSTRAINT [FK_PerfilArtista_Cancion_PerfilArtista] FOREIGN KEY([idPerfilArtista])
REFERENCES [dbo].[PerfilArtista] ([idPerfilArtista])
GO
ALTER TABLE [dbo].[PerfilArtista_Cancion] CHECK CONSTRAINT [FK_PerfilArtista_Cancion_PerfilArtista]
GO
ALTER TABLE [dbo].[SolicitudColaboracion]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_Cancion] FOREIGN KEY([idCancion])
REFERENCES [dbo].[Cancion] ([idCancion])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SolicitudColaboracion] CHECK CONSTRAINT [FK_Solicitud_Cancion]
GO
ALTER TABLE [dbo].[SolicitudColaboracion]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_Notificacion] FOREIGN KEY([idNotificacion])
REFERENCES [dbo].[Notificacion] ([idNotificacion])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SolicitudColaboracion] CHECK CONSTRAINT [FK_Solicitud_Notificacion]
GO
USE [master]
GO
ALTER DATABASE [MusAPI_DB] SET  READ_WRITE 
GO
