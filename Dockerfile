# Usa una imagen base con OpenJDK 21
FROM openjdk:21-jdk

# Define variables de entorno para Tomcat
ENV TOMCAT_MAJOR 9
ENV TOMCAT_VERSION 9.0.41
ENV TOMCAT_HOME /usr/local/tomcat
ENV CATALINA_HOME $TOMCAT_HOME
ENV PATH $CATALINA_HOME/bin:$PATH

# Descarga e instala Tomcat
RUN mkdir -p "$TOMCAT_HOME" \
    && curl -SL "https://archive.apache.org/dist/tomcat/tomcat-$TOMCAT_MAJOR/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz" \
    | tar -xzC "$TOMCAT_HOME" --strip-components=1 \
    && rm -rf "$TOMCAT_HOME/webapps"/*

# Copia el archivo WAR de tu aplicación al contenedor en la raíz de webapps
COPY dist/lab06DAW.war $TOMCAT_HOME/webapps/ROOT.war

# Expone el puerto 8080
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]
