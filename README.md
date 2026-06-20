<h1>Gestion de Bibliotheque en Ligne</h1>
<h2>-Backend</h2>
Application backend de gestion des bibliotheque permettant la gestion des livres, des membres et des emprunts.
Projet realise dans le cadre du module <b>Developpement Web Fulstack.</b>
<h2>Objectif</h2>
Developper une API REST securisee mettant en oeuvre les operations CRUD, les relation entre entites JPA et une gestion des acces par roles(membre/bibliothecaire).
<h2>Fonctionnalites</h2>
<h3>Gestion des livres</h3>
<ul>
  <li>Ajout, modification, suppression d'un livre</li>
  <li>Recherche par titre, auteur ou categorie</li>
  <li>Suivi de la disponibilite (nombre d'exemplaires)</li>
</ul>
<h3>Gestion des membres</h3>
<ul>
  <li>Inscription et modification de compte</li>
  <li>Suppression de compte</li>
  <li>Gestion des roles: <b>Membre</b> et <b>Admin</b>(bibliothecaire) </li>
</ul>
<h3>Gestion des emprunts</h3>
<ul>
  <li>Emprunt d'un livre (si disponible)</li>
  <li>Retour d'un livre</li>
  <li>Prolongation d'un emprunt</li>
  <li>Calcul authomatique des penalites de retard</li>
</ul>
<h3>Authentificaation et autorisation</h3>
<ul>
  <li><b>Membre</b>: consultation du catalogue, gestion de leurs propre emprunts</li>
  <li><b>Bibliothecaires (ADMIN)</b>: gestion complete des livres et des membres</li>
</ul>
<h3>Tableau de bord</h3>
<ul>
  <li>Statistique sur les livres les plus empruntes</li>
  
</ul>
