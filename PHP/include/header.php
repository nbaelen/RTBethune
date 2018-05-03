<?php
    session_start();
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="assets/css/nav.css"/>
    <link rel="stylesheet" href="assets/css/form.css"/>
    <link rel="stylesheet" href="assets/css/gallery.css"/>
    <link rel="stylesheet" href="assets/css/global.css"/>
    <link href="https://fonts.googleapis.com/css?family=Lato:300" rel="stylesheet">
    <title>Portail</title>
</head>
<body>

<div id='cssmenu'>
    <ul>
        <li><a href='#'><span>Home</span></a></li>
        <li class='has-sub'><a href='gallery.php?author=0'><span>Vidéos</span></a>
            <ul>
                <li><a href='gallery.php?author=0'><span>Toutes</span></a></li>
                <li><a href='gallery.php?top=5'><span>Les plus vues</span></a></li>
                <li class='has-sub'><a href='#'><span>Uniquement celles de</span></a>
                    <ul>
                        <?php
                            include('include/database.php');
                            $queryAuthor = $bdd->query('SELECT name, surname, id FROM author');

                            while ($author = $queryAuthor->fetch()) {
                                echo "<li><a href='gallery.php?author=".$author['id']."'><span>".htmlspecialchars($author['surname'])." ".htmlspecialchars($author['name'])."</span></a></li>";
                            };
                        ?>
                    </ul>
                </li>
            </ul>
        </li>
        <li><a href='contact.php'><span>Contact</span></a></li>
        <li class='has-sub'><a href="<?php if (isset($_SESSION['login'])) { echo "dashboard.php"; } else { echo "login.php"; } ?>">Espace membre</a>
            <ul>
                <?php
                    if (isset($_SESSION['pseudo'])) {
                        echo "<li><a href='dashboard'>Tableau de bord</a></li>";
                    } else {
                        echo "<li><a href='login.php'>Connexion</a></li>";
                    }
                ?>
                <li><a href='signin.php'>Inscription</a></li>
            </ul>
        </li>
    </ul>
</div>

<br/>