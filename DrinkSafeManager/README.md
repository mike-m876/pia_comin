# DrinkSafe Manager 🥃
**Aplicación Android – Panel de gestión del sistema detector de alcohol adulterado**

## Descripción del Proyecto

DrinkSafe Manager es la aplicación móvil de administración del sistema DrinkSafe, un detector de alcohol adulterado que utiliza una Raspberry Pi 4 con sensores especializados.

La app **NO** realiza el análisis de bebidas (eso lo hace la Raspberry Pi con su pantalla de 7"), sino que sirve como **herramienta de administración** para:
- Registrar nuevas bebidas de referencia
- Consultar la base de datos de bebidas
- Monitorear el estado de conexión con el dispositivo

---

## Stack Tecnológico

| Tecnología | Uso |
|---|---|
| **Kotlin** | Lenguaje principal |
| **Material Design 3** | Sistema de diseño UI |
| **MVVM** | Arquitectura |
| **Navigation Component** | Navegación entre pantallas |
| **Room Database** | Persistencia local |
| **Coroutines + Flow** | Asincronía reactiva |
| **ViewBinding** | Enlace de vistas |
| **RecyclerView + ListAdapter** | Lista de bebidas |

---

## Estructura del Proyecto

```
app/src/main/
├── java/com/drinksafe/manager/
│   ├── DrinkSafeApplication.kt       # Application class
│   ├── data/
│   │   ├── database/
│   │   │   ├── DrinkSafeDatabase.kt  # Room Database
│   │   │   └── BebidaDao.kt          # Data Access Object
│   │   ├── models/
│   │   │   └── Bebida.kt             # Entidad Room
│   │   └── repository/
│   │       └── BebidaRepository.kt   # Repositorio
│   ├── ui/
│   │   ├── MainActivity.kt           # Host de Navigation
│   │   ├── splash/
│   │   │   └── SplashFragment.kt     # Pantalla 1: Splash
│   │   ├── dashboard/
│   │   │   └── DashboardFragment.kt  # Pantalla 2: Dashboard
│   │   ├── register/
│   │   │   └── RegistrarBebidaFragment.kt  # Pantalla 3: Registro
│   │   ├── database/
│   │   │   ├── ListaBebidasFragment.kt     # Pantalla 4: Lista
│   │   │   └── BebidaAdapter.kt            # Adapter RecyclerView
│   │   └── detail/
│   │       └── DetalleBebidaFragment.kt    # Pantalla 5: Detalle
│   ├── viewmodel/
│   │   └── BebidaViewModel.kt        # ViewModel central
│   └── utils/
│       ├── SensorSimulator.kt        # Simulador de sensores
│       └── Extensions.kt             # Funciones de extensión
└── res/
    ├── layout/                       # 6 layouts XML
    ├── navigation/nav_graph.xml      # Grafo de navegación
    ├── drawable/                     # Íconos y fondos vectoriales
    ├── values/                       # Colors, strings, themes
    └── anim/                         # Animaciones de transición
```

---

## Pantallas de la Aplicación

### 1. Splash Screen
- Logo animado del sistema DrinkSafe
- Duración: 2.5 segundos
- Animaciones de escala + fade
- Navegación automática al Dashboard

### 2. Dashboard Principal
- Estado de conexión con la Raspberry Pi (🟢/🔴)
- Botón de refresh para verificar conexión
- Tarjetas de acceso rápido con animación escalonada
- Contador total de bebidas registradas

### 3. Registrar Nueva Bebida
- Formulario con validación:
  - Nombre de la bebida
  - Marca
  - Tipo (Vodka / Tequila blanco / Otro)
  - Notas opcionales
- Simulación de lectura de sensores (2.5 seg)
- Panel visual de sensores activos durante análisis
- Confirmación con Snackbar verde

### 4. Base de Datos de Bebidas
- RecyclerView con ListAdapter + DiffUtil
- Búsqueda en tiempo real
- Estado vacío con ilustración
- FAB para agregar nueva bebida
- Eliminar bebidas con confirmación

### 5. Detalle de Bebida
- Header con nombre, marca, tipo
- Datos de análisis: alcohol, conductividad, temperatura
- Tabla de datos espectrales del sensor AS7262 (6 canales)
- Fecha de registro

---

## Cómo abrir en Android Studio

1. **Clonar o descomprimir** el proyecto
2. Abrir **Android Studio** → `File > Open` → seleccionar la carpeta `DrinkSafeManager`
3. Esperar a que Gradle sincronice (primera vez tarda ~2-3 minutos)
4. Conectar dispositivo Android o usar emulador (API 26+)
5. Presionar **Run ▶**

### Requisitos mínimos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK 34
- Dispositivo/emulador con Android 8.0 (API 26) o superior

---

## Integración futura con Raspberry Pi

Para conectar con la Raspberry Pi real, el `SensorSimulator.kt` debe reemplazarse por llamadas HTTP al API REST del dispositivo:

```kotlin
// Ejemplo de llamada futura a la Raspberry Pi
val response = RetrofitClient.api.leerSensores()
// La Raspberry Pi expone: POST /api/read_sensors
// Response: { espectral, conductividad, temperatura }
```

---

## Licencia
Proyecto universitario – Uso educativo
