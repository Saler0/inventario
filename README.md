# Inventario DevOps

Este Proyecto fue creado para fines academicos para el diplomado de DevOps de Kibernum IT Academy. El proyecto se trata de una página web de un registro de inventario de una bodega, esta permite listar, registrar, eliminar y modificar productos del inventario. El proyecto tiene aplica tecnologias usadas en los ambientes DevOps y hacer el ciclo de CI/CD automaticamente.

## Índice

1. [Instalación](#instalación)
2. [Uso](#uso)
3. [Características](#características)
6. [Créditos](#créditos)

## Instalación

Es necesario para instarlar en tu ambiente lo siguiente:

1) Clona el repositorio
```bash
# Clona el repositorio
git clone git@github.com:Saler0/inventario.git

```
2) Jenkins
    -   Instalar Jenkins: https://www.jenkins.io/
    -   Configurar plugins necesarios
    -   Configurar Tools necesarios
    -   Configurar Credeciales necesarias

     ![Credeciales.](/image/credentialsJenkins.png)
3) Docker
    - Instalar Docker: https://www.docker.com/
4) SonarQube
    -   Levantar SonarQube
    ```bash
    cd inventario
    cd SonarQube
    docker-compose up
    ```
5) Servidor Selenium
    -   Levantar Selenium
    ```bash
    cd inventario
    cd selenium_server
    java -jar selenium-server-4.25.0.jar standalone
    ```
6) Ngork
    - Instalar Ngork: https://ngrok.com/download
    - Iniciar un tunel con Ngork al puerto donde se configuro jenkis
    ```bash 
    ngrok http 8080
    ```
7) Crearse una cuenta en Slack
    - Crear un canal en Slack y asociarlo con jenkins.

## Uso
Una ves instalado  y configurado todo se debe hacer los siguiente:
*   Crear un Webhooks en github de tu repositorio:
    ![webhook1.](/image/webhook1.png)
    ![webhook2.](/image/webhook2.png)

*   Crear un Proyecto Pipeline en jenkins:
    ![ProyectoPipeline.](/image/crearproyectopipeline.png)

*   En la seccion de pipeline de jenkins configurar el repositorio, branch y la direccion del jenkinsfile en el proyecto.
    ![pipeline1.](/image/pipeline1.png)
    ![piepeline2.](/image/piepeline2.png)

*   Una ves se listo lo anterior al momento de hacer un push al repositorio development el Webhook lo detectara y jenkins empezara a ejecutar el pipeline automaticamente pasando por todos los stages en los que usara SonarQube, Selenium y notificara en Slack los avances de los diferentes stages del pipeline. Finalmente en los stage de deploy levantara la aplicacion con el docker-compose automaticamente.
![Slack Notificaciones.](/image/slacknotificaciones.png)

## Caracteristicas
Las tecnologias usadas fueron:
 * Backend:
    - Java SE 17
    - Spring Boot
    - Maven
 * Frontend:
    - Node.js
    - React
* Base de datos:
    - MySQL
* Tecnologias o tecnicas DevOps:
    - Jenkins
    - Slack
    - SonarQube
    - Ngrok para Webhook
    - Git
    - Docker


## Creditos
Agradecimientos a las personas que hicieron posible este proyecto:

* Felipe Cardenas (https://github.com/pipecm)
* Milenko Espinoza (https://github.com/Saler0)
* Cesar Marin (https://github.com/ceomarin)
* Camila Collao (https://github.com/camicollao)
