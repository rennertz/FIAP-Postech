#Build stage

FROM maven:3 AS buildstage
WORKDIR /usr/app/
COPY . .
RUN mvn install

# Package stage

# Imagem base com JRE 21
FROM eclipse-temurin:21-jre

ENV APP_HOME=/usr/app

# Define o diretório de trabalho no container
WORKDIR $APP_HOME

# Copia o arquivo JAR já gerado para o container
COPY --from=buildstage $APP_HOME .
RUN mv "$APP_HOME"/target/*.jar "$APP_HOME"/target/app.jar

# Exposição da porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "target/app.jar"]