# Imagem base com JRE 21
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo JAR já gerado localmente para o container
COPY build/libs/*.jar app.jar

# Exposição da porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
