# riskRealUbu :)

- Pasos para generar una imagen de docker desde el proyecto de Spring:

1. Incluir el plugin de maven spring boot como dependencia y en la sección de build:

<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<configuration>
                		<image>
                    			<name>riskrealapp</name>
                		</image>
            		</configuration>
		</plugin>
	</plugins>
</build>

2. Ejecutar desde Spring Tools Suite 4: Botón derecho del ratón en el nombre del proyecto --> Run as... --> Maven build.. --> Escribimos spring-boot:build-image
3. Una vez termine ya tenemos la imagen cargada en docker --> Se puede comprobar con "docker images" desde el CMD o en la aplicación de Docker Desktop.

- Pasos para subir la imagen a nuestro repositorio de GitHub Containers:

1. Renombramos la imagen de docker siguiendo el patrón: ghcr.io/<username de github>/<repositorio>/<nombre de imagen>:<version> (esto último es opcional)
   - ejemplo: docker image tag riskrealapp:latest ghcr.io/daniel-fernandez-ubu/riskrealubu/riskrealapp
2. Crear un "Access Token (classic) en nuestro perfíl de GitHub --> Settings --> Developer Settings (Está abajo del todo)
3. Iniciar sesión en la terminal (o línea de comandos) desde donde vayamos a hacer el push con el comando:
   - echo <token> | docker login ghcr.io -u USERNAME --password-stdin
4. Hacer pull de nuestra imagen al repositorio:
   - docker push ghcr.io/daniel-fernandez-ubu/riskrealubu/riskrealapp (al no poner tag utiliza latest)
5. Si hemos vinculado el repositorio de "package" con nuestro repositorio, ya al acceder vemos que hay 2 imágenes.

