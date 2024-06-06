# epsi-atelier-test-automatise-exo-5
**Ce projet Java contient des tests d'intégration et utilise une base de données PostgreSQL.**

Il a été développé dans le cadre d'un atelier à l'EPSI sur les tests automatisés par les étudiants :
- Manon BOISSON
- Raphaël BAILHET
- Antonin SIMON
- Maxime BAUDOIN

## Prérequis
Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

- Java Development Kit (JDK) (version 11 ou supérieure)
- Apache Maven
- PostgreSQL

## Installation

1. Clonez le dépôt :

```bash
git clone https://github.com/votre-utilisateur/epsi-atelier-test-automatise-exo-5.git
cd epsi-atelier-test-automatise-exo-5
```

2. Configurez la base de données PostgreSQL :

- Créez une nouvelle base de données PostgreSQL.
- Créez la table `products` dans la base de données avec ce script SQL :
```sql
CREATE TABLE "products" (
  "id" uuid PRIMARY KEY,
  "created_at" timestamp,
  "name" varchar(255),
  "price" float,
  "description" text,
  "color" varchar(100),
  "stock" int
);
```
- Modifiez les paramètres de connexion à la base de données dans le fichier src/main/resources/application.properties.

Exemple de `application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/votre_base_de_donnees
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Installez les dépendances Maven :

```bash
mvn clean install
```

## Utilisation
### Exécution des tests
Pour exécuter les tests d'intégration, utilisez la commande suivante :

```bash
mvn test
```

### Démarrer l'application
Pour démarrer l'application, utilisez la commande suivante :

```bash
mvn spring-boot:run
```

### Structure du projet
- `src/main/java` : Contient le code source de l'application.
- `src/test/java` : Contient les tests d'intégration.
- `src/main/resources` : Contient les fichiers de configuration.

## Licence
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
