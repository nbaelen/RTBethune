<?php include('include/header.php') ?>

<?php
    include('include/database.php');

    //Récupération du paramètre auteur + contrôle
    if (!isset($_GET['author']) or !is_numeric($_GET['author'])) {
        $author = 0;
    } else {
        $author = $_GET['author'];
    }

    //Aucun auteur à l'ID 0, on sélectionne donc tous les auteurs
    if ($author == 0) {
        $galleryQuery = $bdd->query('SELECT * FROM video ORDER BY date_published DESC');
    } else {
        $galleryQuery = $bdd->prepare('SELECT * FROM video WHERE author = ? ORDER BY date_published DESC');
        $galleryQuery->execute(array($author));
    }

    //Chaque vidéo sélectionnée sera intégrée dans un tableau pour organiser les données
    while ($gallery = $galleryQuery->fetch()) {

        //Requête pour obtenir les informations sur l'auteur de la vidéo
        $informationQuery = $bdd->prepare('SELECT name, surname FROM author WHERE id = ?');
        $informationQuery->execute(array($gallery['author']));
        $information = $informationQuery->fetch();

        //Création du tableau (protection des textes renvoyés avec htmlspecialchars pour éviter le XSS
        echo "<table>";
        echo "<caption><em>\"" .htmlspecialchars($gallery['name']). "\"</em> par ".htmlspecialchars($information['surname'])." ".htmlspecialchars($information['name'])."</caption>";
        echo '<tr><td><iframe src="https://player.vimeo.com/video/' .$gallery['vimeoref'].'" width="960" height="440" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe></td></tr>';
        echo "<tr><td>" .htmlspecialchars($gallery['description'])."</td></tr>";
        echo "</table><br/>";
    }


?>

<?php include('include/footer.php');

