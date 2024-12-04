#Build stage

FROM gradle:latest AS buildstage
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Package stage

# Imagem base com JRE 21
FROM eclipse-temurin:21-jre

ENV JAR_NAME=app.jar
ENV APP_HOME=/usr/app/

# Define o diretório de trabalho no container
WORKDIR $APP_HOME

# Copia o arquivo JAR já gerado para o container
COPY --from=buildstage $APP_HOME .

# Exposição da porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME