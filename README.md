# Proyecto: Aplicación de Carrito de la Compra

Este proyecto es una aplicación básica de carrito de la compra desarrollada utilizando Spring Boot, Thymeleaf y una base de datos H2.

## Tecnologías Utilizadas

- **Spring Boot**: Framework para el desarrollo de aplicaciones Java.
- **Thymeleaf**: Motor de plantillas para la generación de vistas HTML.
- **H2 Database**: Base de datos en memoria para el almacenamiento de datos.
- **Lombok**: Biblioteca para reducir el código boilerplate en Java.

## Endpoints

### Endpoints Públicos

- **`GET /`**: Página de inicio de la aplicación.
- **`GET /login`**: Página de inicio de sesión.

### Endpoints de Usuario

- **`GET /products`**: Listado de productos disponibles.
- **`POST /cart/add/{productId}`**: Añadir un producto al carrito.
- **`GET /cart`**: Ver el contenido del carrito.
- **`POST /cart/remove/{productId}`**: Eliminar un producto del carrito.
- **`GET /cart/checkout`**: Realizar la compra de los productos en el carrito.

### Endpoints de Administrador

- **`GET /admin`**: Página de administración de productos.
- **`GET /admin/download-db-sql`**: Descargar la base de datos en formato SQL.
- **`GET /admin/formulario-producto`**: Formulario para agregar o editar un producto.
- **`POST /admin/add`**: Añadir un nuevo producto.
- **`POST /admin/edit/{id}`**: Editar un producto existente.
- **`POST /admin/delete/{id}`**: Eliminar un producto.

### Endpoints de API

- **`GET /api/products`**: Obtener todos los productos.
- **`GET /api/products-by-id`**: Obtener productos por ID.
- **`GET /api/products/add`**: Añadir un producto vía API.
- **`GET /api/products/edit`**: Editar un producto vía API.
- **`GET /api/products/delete`**: Eliminar un producto vía API.
- **`GET /api/cart`**: Ver el contenido del carrito vía API.
- **`GET /api/cart/checkout`**: Realizar la compra de los productos en el carrito vía API.
- **`GET /api/login`**: Iniciar sesión vía API.
- **`GET /api/checkPrivileges`**: Verificar privilegios vía API.

## Credenciales

Para acceder a las funcionalidades de la aplicación, se pueden utilizar las siguientes credenciales de ejemplo:

### Usuario

- **Usuario**: $(Cualquier usuario)
- **Contraseña**: password

### Administrador

- **Usuario**: admin
- **Contraseña**: admin


## Funcionalidades Adicionales

- **Creación de Usuarios**: Los usuarios pueden registrarse y crear sus propias cuentas.
- **Persistencia del Carrito**: El contenido del carrito se guarda en cookies para mantener la persistencia en el navegador.
- **Descarga de Ticket Personalizado**: Los usuarios pueden descargar un ticket personalizado después de realizar una compra.

