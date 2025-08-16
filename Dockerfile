# =========================================================================
# ESTÁGIO 1: Build da Aplicação com Maven e JDK
# Usamos uma imagem oficial que já contém Maven e o JDK 21.
# =========================================================================
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de camadas do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte da aplicação
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes.
# Os testes devem ser executados em um pipeline de CI/CD separado.
RUN mvn package -DskipTests

# =========================================================================
# ESTÁGIO 2: Execução da Aplicação com JRE
# Usamos uma imagem leve, contendo apenas o Java Runtime Environment (JRE).
# =========================================================================
FROM eclipse-temurin:21-jre-jammy

# Cria um usuário e grupo 'app' para não executar como root (boa prática de segurança)
RUN groupadd --gid 1000 app && \
    useradd --uid 1000 --gid 1000 --shell /bin/sh -m app

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio 'builder' para a imagem final
COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# Garante que o usuário 'app' seja o dono do arquivo
RUN chown app:app app.jar

# Muda para o usuário não-root
USER app

# Expõe a porta 8080, que é a padrão do Spring Boot
EXPOSE 4000

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]