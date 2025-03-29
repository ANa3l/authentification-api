# Utilise une image légère avec Java 21 préinstallé
FROM eclipse-temurin:21-jdk-alpine

# Crée et positionne le dossier de travail à l’intérieur du conteneur
WORKDIR /app

# Copie le fichier .jar généré par Maven dans le conteneur
COPY target/authentification-0.0.1-SNAPSHOT.jar app.jar

# Expose le port 8081 du conteneur (doit correspondre à ton application Spring Boot)
EXPOSE 8081

# Définit la commande de démarrage du conteneur :
ENTRYPOINT ["java", "-jar", "app.jar"]
