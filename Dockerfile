# Usar a JDK 21 como base para rodar a aplicação
FROM eclipse-temurin:21-jdk

# Criar diretório da aplicação dentro do container
WORKDIR /app
#ENV MAPS_API_KEY=AIzaSyC3GUEkNJMlRX8K27sjqHe7nDowP-VSTNQ

# Copiar o jar para dentro do container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 1603 para fora do container
EXPOSE 1603

# Comando para rodar a aplicação na porta 1602
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=1603"]