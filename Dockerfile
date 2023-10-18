# Usa una imagen base con OpenJDK 21
FROM openjdk:21-jdk

# Descarga e instala Tomcat
RUN mkdir -p /usr/local/tomcat \
    && curl -SL "https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.41/bin/apache-tomcat-9.0.41.tar.gz" \
    | tar -xzC /usr/local/tomcat --strip-components=1 \
    && rm -rf /usr/local/tomcat/webapps/*

# Copia el archivo WAR de tu aplicación al contenedor en la raíz de webapps
COPY dist/lab06DAW.war /usr/local/tomcat/webapps/ROOT.war

# Expone el puerto 8080
EXPOSE 8080

# Inicia Tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
