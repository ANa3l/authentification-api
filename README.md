# Authentification API

Une API REST simple et sécurisée d'authentification par JWT, développée avec Spring Boot, Spring Security, et une base de données MariaDB. Elle permet d'enregistrer des utilisateurs, de se connecter, de se déconnecter, et de changer de mot de passe via des endpoints sécurisés.

## Fonctionnalités

- Inscription d’un utilisateur (`/auth/register`)
- Connexion avec génération de JWT (`/auth/login`)
- Déconnexion (révocation du token) (`/auth/logout`)
- Changement de mot de passe sécurisé (`/auth/register`)
- Documentation Swagger pour tester l’API
- Spécification complète OpenAPI disponible (`authentification_api.yaml`)
- Tests unitaires avec JUnit pour les services et filtres
- Configuration Docker avec MariaDB et PhpMyAdmin

## Lancement rapide

### Prérequis

- Java 17 ou plus de préférence Java 21
- Docker & Docker Compose
- Maven

### Lancement avec Docker

```bash
docker-compose up --build
```

Cela démarre :

- l’API sur `http://localhost:8081`
- la base de données MariaDB sur `localhost:3308`
- PhpMyAdmin sur `http://localhost:8082` (login: `userAPI` / `userAPI123`)

## Test de l'API

### Swagger

Documentation interactive disponible à l'adresse :

```
http://localhost:8081/swagger-ui.html
```

### Fichier OpenAPI

La description complète de l'API est disponible dans le fichier `authentification_api.yaml`, conforme à la spécification OpenAPI 3.0.3.

### Collection Postman

Une collection Postman est fournie dans le projet : `Authentification-API.postman_collection.json`

## Endpoints principaux

| Méthode | URL              | Description                               |
| ------- | ---------------- | ----------------------------------------- |
| POST    | `/auth/register` | Enregistre un nouvel utilisateur          |
| POST    | `/auth/login`    | Authentifie et retourne un token JWT      |
| DELETE  | `/auth/logout`   | Révoque le token JWT actuel               |
| PUT     | `/auth/register` | Met à jour le mot de passe (token requis) |

## Structure du projet

- `controllers/` : Contrôleurs REST
- `services/` : Logique métier (login, register, update password)
- `config/` : Configuration sécurité, Swagger, JWT
- `dto/` : Objets de transfert de données
- `entities/` : Entité `Compte` mappée à la base de données
- `repositories/` : Accès à la base de données
- `mapper/` : Conversion entité ↔ DTO
- `test/` : Tests unitaires JUnit

## Configuration

Fichier principal : `application.yaml`

- Port par défaut : `8081`
- Accès base MariaDB via Docker : `jdbc:mariadb://db:3306/compte_db`
- Utilisateur : `userAPI`
- Mot de passe : `userAPI123`

## Sécurité JWT

- Les tokens sont signés avec une clé secrète en HMAC SHA256.
- Durée de validité : 1 heure.
- Les tokens révoqués sont conservés temporairement en mémoire (thread-safe).

## Tests unitaires

Tests avec JUnit pour :

- `JwtUtil` : génération, extraction, validation de JWT
- `JwtFilter` : filtrage et vérification des tokens
- `LoginService`, `RegisterService`, `UpdatePasswordService`

## Auteur

Projet développé par **Naël ABASY et Aïcha AMINE**.

