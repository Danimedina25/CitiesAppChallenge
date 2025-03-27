# Cities App Challenge

## Descripción

La aplicación permite visualizar una lista de ciudades, mostrar un mapa con la ubicación de cada una y ver detalles de la ciudad seleccionada. Además, implementa una funcionalidad de búsqueda que permite filtrar las ciudades por nombre.

## Enfoque para Resolver el Problema de Búsqueda

La funcionalidad principal de búsqueda permite a los usuarios filtrar las ciudades por nombre de manera insensible a mayúsculas/minúsculas y ordenarlas alfabéticamente. Esto se logró mediante:

1. **Normalización de Nombres**: Se convierte el nombre de las ciudades a minúsculas para hacer la búsqueda insensible a mayúsculas y minúsculas.

2. **Filtrado**: Se utiliza `startsWith` con el parámetro `ignoreCase = true` para filtrar las ciudades que comienzan con el texto de búsqueda.

3. **Actualización del Estado**: Después de filtrar, se actualiza el estado usando `StateFlow`, lo que permite que la UI reaccione automáticamente a los cambios.

El código para filtrar las ciudades es el siguiente:

```kotlin
fun filterCities() {
    val cities = _state.value.cities
    val searchQuery = _state.value.searchQuery.text
    viewModelScope.launch {
        val filteredList = cities.filter { city ->
            normalizeCityName(city.name).startsWith(searchQuery, ignoreCase = true)
        }.sortedBy { it.name }
        _state.update { it.copy(filteredCities = filteredList) }
    }
}
```
## Principales Decisiones y Asunciones

- **Optimización**: El filtrado funciona bien con un número moderado de ciudades. Para más de 1000 ciudades, sería necesario optimizar la búsqueda, por ejemplo, utilizando un servicio backend.

- **Persistencia de Datos**: En este proyecto no se implementó persistencia de datos para simplificar el ejemplo. En un proyecto real, se debería agregar una capa de persistencia.

- **UI con Jetpack Compose**: La UI se construyó con Jetpack Compose, aprovechando su enfoque declarativo para actualizar la vista de manera eficiente.

## Funcionalidades Principales

- **Lista de Ciudades**: Muestra una lista de ciudades filtrable por nombre.
- **Búsqueda por Nombre**: Permite buscar ciudades insensible a mayúsculas/minúsculas.
- **Mapa**: Muestra las ubicaciones geográficas de las ciudades.
- **Detalles de la Ciudad**: Al seleccionar una ciudad, se muestra más información sobre ella.

## Estructura del Proyecto

La aplicación sigue la arquitectura MVVM (Modelo-Vista-ViewModel):

- **Modelos**: Definen la estructura de los datos de las ciudades.
- **Vista**: Se implementa usando Jetpack Compose para mostrar la UI.
- **ViewModel**: Contiene la lógica para filtrar las ciudades y actualizar el estado.
- **Repositorio**: Gestiona la obtención de los datos de las ciudades.

## Decisiones Técnicas

- **Jetpack Compose**: Usado para crear una UI declarativa y mantenerla en sincronía con el estado.
- **MVI con ViewModel**: El ViewModel maneja el estado de la UI, mientras que la vista observa los cambios.
- **StateFlow**: Para manejar el estado y actualizar la UI cuando cambian los datos.

## Ejecución del Proyecto

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu-usuario/cities-app.git
