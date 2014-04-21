/********************************************************************\
|**************************** BATTLESHIP ****************************|
\********************************************************************/

Réalisé par Samy Chayem, Tristan Néret et Christophe Phan
(avec Netbeans 7.4 et java 7, nécessite la librairie jdom-2.0.5.jar)

|||||||||| EXECUTION DE L’ARCHIVE

Le répertoire du projet contient les éléments suivants:
- Battleship.jar // l’archive de l’application
- lib            // contient la librairie utilisée pour XML
- stockage       // contient les informations de configuration du jeu
- users          // contient les sauvegardes des profils des joueurs
- src     // contient les sources du projet

Pour exécuter l’application, il suffit de double-cliquer sur le .jar 
(ou de lancer la commande java -jar Battleship.jar) en s’assurant que 
les dossiers « lib » et « stockage » sont bien présents autour de 
l’archive.	

|||||||||| CHOIX DE PROGRAMMATION

- Nous avons décidé de mettre une option permettant au joueur de 
  choisir si un bateau coulé a toujours le droit de tirer ou non
  (dans le cas où l’on choisit non, il arrive très souvent d’avoir
  égalité c’est pourquoi cette restriction ne nous paraissait pas 
  pertinente mais nous l’avons tout de même implémentée).
- De plus, comme il s’agit d’un choix personnel du joueur Humain, 
  cette règle ne s'applique qu'à lui, l'IA ne subit pas ce désavantage.

|||||||||| INFORMATIONS

Nous avons remarqué des différences suivant les OS:
    - Mac: Tout se passe bien, aucun soucis.
    - Windows: Sur certaines configurations, la fermeture des popups 
      (via la croix) fait passer la fenêtre principale en arrière plan.
    - Linux: Avec certaines configurations, nous avons observés quelques 
      problèmes graphiques concernant la croix de fermeture des popups ou
      le dimensionnement de l’interface du jeu.


