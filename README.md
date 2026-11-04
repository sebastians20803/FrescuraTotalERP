# ERP para la Bodega de Alimentos "Frescura Total"

## Descripción

Frescura Total es un centro de distribución que compra alimentos perecederos a productores agrícolas y los vende a restaurantes, supermercados y tiendas. La gestión manual con hojas de cálculo provocaba pérdidas por vencimiento, falta de trazabilidad de los lotes, ventas de productos sin stock real y facturación lenta y con errores. Este proyecto es un ERP de consola desarrollado en Java con MySQL que conecta la recepción de mercancía por lotes, el control de calidad, las ventas con rotación FEFO y descuentos automáticos, la facturación con CUFE y código QR simulados, y la logística de entregas, para que la información esté centralizada y las decisiones se basen en datos reales.

## Autores

- Sebastián *(completar con nombre completo)*

## Requerimientos previos

| Software | Versión usada |
|---|---|
| Java JDK | 21 (Eclipse Temurin 21) |
| Apache Maven | 3.x (incluido en el IDE) |
| MySQL Server | 8.x |
| IDE | IntelliJ IDEA (el proyecto es Maven estándar y también abre en Apache NetBeans 12+) |
| Cliente SQL (opcional) | DBeaver o MySQL Workbench |

Dependencias que Maven descarga automáticamente (`pom.xml`):

- `com.mysql:mysql-connector-j`: conector JDBC para MySQL.
- `com.google.zxing:core` y `com.google.zxing:javase` 3.5.3: generación del código QR de la factura.

## Configuración de la base de datos

1. Inicia el servidor MySQL en tu equipo (puerto `3306` por defecto).
2. Abre DBeaver, MySQL Workbench o la terminal de MySQL con un usuario que pueda crear bases de datos.
3. Ejecuta completo el script **`docs/database.sql`**. El script:
   - elimina y crea de nuevo la base `erp_frescura_total`,
   - crea todas las tablas (sección DDL),
   - inserta los catálogos y los datos de prueba (sección DML).

   Desde la terminal:

   ```bash
   mysql -u root -p < docs/database.sql
   ```

4. Configura las credenciales en la clase
   `src/main/java/com/frescuratotal/erp/common/DatabaseConnection.java`:

   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/erp_frescura_total";
   private static final String USER = "root";
   private static final String PASSWORD = "tu_contraseña";
   ```

   Cambia `USER` y `PASSWORD` por los de tu instalación de MySQL. Si tu servidor usa otro puerto, cámbialo en la `URL`.

## Instalación y ejecución

### Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd FrescuraTotalERP
```

### Desde IntelliJ IDEA

1. **File → Open** y selecciona la carpeta del proyecto (la que contiene `pom.xml`).
2. Espera a que Maven descargue las dependencias. Si no lo hace solo, abre la pestaña **Maven** y pulsa **Reload All Maven Projects**.
3. Compila con **Build → Build Project** (`Ctrl + F9`).
4. Ejecuta la clase `com.frescuratotal.erp.Main` (clic derecho → **Run 'Main.main()'**, o `Shift + F10`).

### Desde Apache NetBeans

1. **File → Open Project** y selecciona la carpeta del proyecto.
2. **Run → Clean and Build Project** (`Shift + F11`).
3. **Run → Run Project** (`F6`).

### Desde la terminal

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.frescuratotal.erp.Main
```

Las facturas generadas se guardan como imágenes PNG en la carpeta `facturas/`, en la raíz del proyecto.

## Funcionalidades

### Proveedores
- Registrar, listar, actualizar y desactivar proveedores.
- Validación de NIT (`123456789-0`), correo y teléfono. El NIT es único.
- Los proveedores se desactivan en lugar de borrarse, para conservar la trazabilidad de los lotes que entregaron.

### Inventario y calidad
- Productos con categoría y unidad de medida.
- **Recepción de mercancía**: cada ingreso crea un lote con número único generado por el sistema (`LOT-yyyyMMddHHmmss`), proveedor, cantidad, precios, ubicación (pasillo y estante) y fecha de vencimiento.
- **Estados del lote**: `DISPONIBLE`, `PROXIMO_A_VENCER`, `VENCIDO`, `EN_CUARENTENA`.
- **Revisión diaria de vencimientos** (proceso que se ejecuta desde el menú): los lotes con 7 días o menos pasan a próximo a vencer y los que superaron su fecha pasan a vencidos. Los lotes en cuarentena no se modifican.
- **Cuarentena**: bloquea un lote para la venta y registra el motivo y quién lo reportó.
- **Inspección de calidad**: el inspector aprueba (el lote recupera el estado que le corresponde por fecha) o rechaza (lo que queda del lote se registra como merma y su cantidad queda en 0).

### Ventas y facturación
- Clientes mayoristas y minoristas.
- **Venta con estrategia FEFO** (*First to Expire, First Out*): al pedir un producto, el sistema toma la cantidad de los lotes disponibles o próximos a vencer, empezando por el que vence primero. Si un lote no alcanza, continúa con el siguiente.
- **Promoción automática**: las líneas que salen de un lote próximo a vencer reciben 50 % de descuento.
- IVA del 19 % calculado sobre el valor después del descuento.
- La venta y el descuento de inventario se guardan en una **transacción**: si un lote no tiene stock suficiente, no se guarda nada.
- **Factura electrónica (representación gráfica en PNG)** con los datos de Frescura Total, el cliente, el detalle de productos, los impuestos, un **CUFE simulado** (hash SHA-384) y un **código QR**.

### Logística
- Registro de conductores y vehículos.
- **Panel de despachos**: las ventas pasan de `REGISTRADA` a `EN_PREPARACION`.
- **Rutas de entrega**: se asignan un vehículo activo, un conductor activo y una o varias ventas en preparación.
- Seguimiento: al iniciar la ruta, sus ventas pasan a `EN_TRANSITO`; al completarla, quedan `ENTREGADA` y se registran las horas reales de salida y llegada.

## Estructura del proyecto

El código sigue una arquitectura **MVC por componentes**: cada módulo del negocio tiene sus propios paquetes `model`, `view` y `controller`.

```
src/main/java/com/frescuratotal/erp/
├── Main.java                  // Punto de entrada: menú principal
├── common/                    // Conexión a BD, utilidades de consola, datos de la empresa
├── suppliers/                 // Proveedores
│   ├── controller/
│   ├── model/                 // Proveedor, ProveedorDAO
│   └── view/
├── inventory/                 // Inventario y calidad
│   ├── controller/            // Producto, Lote, Cuarentena, Inspección
│   ├── model/                 // Producto, Lote, EstadoLote, UnidadMedida, mermas, inspecciones...
│   └── view/
├── sales/                     // Clientes, ventas y facturación
│   ├── controller/
│   ├── model/                 // Cliente, Venta, DetalleVenta, Factura, EstadoVenta...
│   └── view/                  // Menús de consola y FacturaImagen (PNG)
└── logistics/                 // Despachos y entregas
    ├── controller/
    ├── model/                 // Conductor, Vehiculo, RutaEntrega, EstadoRuta...
    └── view/

docs/
├── database.sql               // DDL + datos de prueba
├── diagrama_er.png            // Diagrama entidad-relación
└── diagrama_clases.png        // Diagrama de clases UML
```

- **model**: las entidades del negocio y sus DAO, que son las únicas clases que acceden a MySQL.
- **controller**: valida los datos, aplica las reglas del negocio y coordina los DAO. No imprime ni lee de la consola.
- **view**: menús de consola. Lee los datos del usuario, llama al controller y muestra el resultado. Nunca accede directamente a un DAO.

## Principios y patrones de diseño

### MVC (Modelo-Vista-Controlador)
Cada módulo separa la presentación, la lógica y los datos. Por ejemplo, `VentaVista` pide el cliente y los productos por consola, `VentaController` aplica la estrategia FEFO y el descuento, y `VentaDAO` guarda la venta. Gracias a esta separación, el controller no sabe que existe una consola: si el sistema pasara a una interfaz gráfica, solo cambiarían las vistas.

### DAO (Data Access Object)
Toda la comunicación con MySQL está en clases `*DAO` (`ProveedorDAO`, `LoteDAO`, `VentaDAO`, etc.). Usan `PreparedStatement` con parámetros `?`, lo que evita inyección SQL, y lanzan `SQLException` hacia el controller, que decide qué mensaje mostrar al usuario. Cada DAO tiene un método privado `convertirFila(ResultSet)` que convierte una fila en un objeto, para no repetir ese código en cada consulta.

### Principio de responsabilidad única (SRP, de SOLID)
Cada clase tiene una sola razón para cambiar: `DatabaseConnection` solo entrega conexiones, `ScannerUtils` solo lee datos de la consola, `FacturaImagen` solo dibuja la factura y cada DAO solo accede a su tabla. Por ejemplo, el cálculo del CUFE está en `FacturaController`, mientras que el dibujo del PNG está aparte, en `FacturaImagen`.

### Transacciones (todo o nada)
Las operaciones que modifican varias tablas se ejecutan en una sola conexión con `setAutoCommit(false)`, `commit()` y `rollback()`:
- `VentaDAO.registrarVenta`: guarda la venta, sus detalles y descuenta la cantidad de cada lote. El descuento usa `cantidad = cantidad - ? WHERE cantidad >= ?`; si un lote no alcanza, se deshace toda la venta.
- `RutaDAO.crearRuta`: guarda la ruta, la asignación de vehículo y conductor y las ventas incluidas.
- `RutaDAO.iniciarRuta` / `completarRuta`: cambian el estado de la ruta y de todas sus ventas.

Así nunca queda una venta guardada sin descontar el inventario, ni una ruta a medias.

### Enums con comportamiento y métodos de fábrica estáticos
Los estados son `enum` (`EstadoLote`, `EstadoVenta`, `TipoCliente`, `EstadoVehiculo`, `EstadoRuta`) en lugar de números o textos sueltos, así que el compilador impide usar un estado inexistente. Cada enum sabe convertirse al valor de la base (`getId()`, `getTexto()`) y crearse a partir de él (`desdeId()`, `desdeTexto()`), de modo que esa conversión vive en un solo lugar. Además, `EstadoLote.calcularEstadoPorFecha()` contiene la regla de los 7 días y la usan tanto la recepción de lotes como la revisión diaria y la inspección.

### Encapsulamiento de las reglas del negocio en el modelo
`DetalleVenta` calcula su subtotal, descuento, IVA y total en el constructor, y `Venta.agregarDetalle()` es la única forma de añadir líneas, actualizando los totales a la vez. De esta forma, los totales de una venta siempre coinciden con la suma de sus líneas. De igual manera, `Factura` copia sus montos directamente de la `Venta`.

### Decisiones de diseño
- **Dinero como `long` (pesos enteros)**: los precios en Colombia no usan centavos, y `long` evita los errores de redondeo de `double` (por ejemplo, `0.1 + 0.2`). Las cantidades sí usan `double` porque pueden ser fraccionarias (12,5 kg).
- **Desactivar en lugar de borrar** proveedores: conserva la trazabilidad de los lotes ya recibidos.
- **El carrito de la venta vive en memoria** hasta que se confirma: si el vendedor cancela, no hay nada que deshacer en la base de datos.

## Simplificaciones

- El IVA es una tasa única del 19 % definida como constante en `VentaController`.
- El descuento para lotes próximos a vencer (50 %) está definido en el controller.
- El CUFE y el QR son simulados: el CUFE se calcula con SHA-384 sobre el número de factura, la fecha, el total y el NIT, y el QR apunta al formato de consulta de la DIAN.
