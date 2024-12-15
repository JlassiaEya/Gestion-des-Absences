# Gestion des Absences et Retards

Une solution complète pour la gestion des absences et des retards des employés, automatisant la collecte et l'analyse des données de pointage.

## 1. Contexte du projet

### 1.1. Description de l'existant

Actuellement, la gestion des absences et des retards se fait manuellement via des feuilles de pointage papier ou des fichiers CSV. Cette méthode est sujette aux erreurs humaines, prend beaucoup de temps et ne permet pas une analyse approfondie des tendances d'absentéisme. De plus, les solutions logicielles existantes sont souvent soit trop complexes, soit trop limitées pour les petites entreprises.

### 1.2. Critique de l'existant

La gestion manuelle présente plusieurs inconvénients :
- Erreurs humaines dans la saisie des données et les calculs.
- Processus long et nécessitant beaucoup de ressources.
- Manque d'analyse des tendances d'absentéisme et de retard.

Le besoin de mettre en place une solution automatisée est essentiel pour améliorer l'efficacité opérationnelle de l'entreprise et faciliter la gestion des absences et des retards.

### 1.3. Solution proposée

Le projet consiste à développer un système informatisé pour détecter automatiquement les absences et les retards à partir des données de pointage des employés. Le système générera des rapports détaillés et permettra à l'entreprise de gagner du temps tout en évitant les erreurs humaines. L'analyse des absences et retards aidera également à prendre des décisions plus éclairées et à optimiser la gestion des ressources humaines.

## 2. Spécification des besoins

### 2.1. Besoins fonctionnels

1. **Gestion des données de pointage**
   - Importer les fichiers de pointage mensuel des employés.
   - Stocker les données dans une base de données.

2. **Détection des absences**
   - Identifier les absences en comparant les données de pointage aux horaires de travail.
   - Générer des alertes ou des rapports pour signaler les absences.

3. **Détection des retards**
   - Identifier les retards en comparant l'heure d'arrivée avec l'heure prévue.
   - Générer des alertes ou des rapports pour signaler les retards.

4. **Génération de rapports**
   - Produire des rapports sur les absences et retards des employés.
   - Inclure des informations détaillées sur chaque employé (jours d'absence, heures de retard).

5. **Interface utilisateur**
   - Fournir une interface conviviale pour l'importation des fichiers de pointage et l'affichage des rapports.

6. **Sécurité des données**
   - Mettre en place des mesures de sécurité pour protéger les données sensibles des employés.
   - Limiter l'accès aux fonctionnalités sensibles aux utilisateurs autorisés.

7. **Flexibilité et évolutivité**
   - Permettre l'ajout de nouvelles fonctionnalités à l'avenir.
   - Assurer la compatibilité avec différents formats de fichiers de pointage.

### 2.2. Besoins non fonctionnels

- **Clarté du code** : Le code doit être structuré de manière à faciliter les évolutions futures.
- **Ergonomie** : L'application doit offrir une interface simple et intuitive.
- **Intégrité des données** : Garantir l'intégrité et la cohérence des données à chaque insertion.
- **Sécurité** : Assurer un niveau minimal de sécurité pour protéger les informations des employés.

## 3. Mise en œuvre

### 3.1. Environnement technique

- **Technologies utilisées** : Node.js, Express.js, MongoDB pour le backend. Angular pour le frontend.
- **Authentification** : JSON Web Tokens (JWT) pour sécuriser l'accès à l'application.

### 3.2. Installation

#### Cloner le repository

```bash
git clone https://github.com/JlassiaEya/Gestion-des-Absences.git
