# Usa una imagen base que tenga Maven para ejecutar los tests
FROM maven:3.9.4-eclipse-temurin-17

# Establece el directorio de trabajo
WORKDIR /app

# Copia el contenido de la carpeta functional-test desde la máquina host
COPY ./functional-test /app/functional-test

# Ejecuta los tests funcionales especificados
CMD ["mvn", "-f", "functional-test/pom.xml", "test", "-Dtest=InventarioChromeFunctionalTest"]