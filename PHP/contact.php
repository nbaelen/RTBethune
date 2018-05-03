<?php include ('include/header.php'); ?>

<?php
    if (isset($_POST['pseudo'], $_POST['mail'], $_POST['message'])) {
        if (preg_match("#^[a-z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$#", $_POST['mail'])) {
            include ('include/database.php');

            $insertQuery = $bdd->prepare('INSERT INTO contact(pseudo, mail, message) VALUES (?, ?, ?)');
            $insertQuery->execute(array($_POST['pseudo'], $_POST['mail'], $_POST['message']));

            echo '<p>Votre message a bien été envoyé !</p><p><a href="gallery.php">Retourner à l\'accueil</a></p>';
            die();
        }
    }
    ?>
    
    <form action="contact.php" method="post">
        <fieldset>
            <legend>Formulaire de contact</legend>
            <label for="pseudo">Pseudo : </label>
            <input type="text" name="pseudo" id="pseudo" value="<?php if (isset($_POST['pseudo'])) { echo $_POST['pseudo']; } ?>"required/><br/>
            <label for="mail">Adresse mail : </label>
            <input type="text" name="mail" id="mail" value="<?php if (isset($_POST['mail'])) { echo $_POST['mail']; } ?>" required/><br/>
            <?php if (isset($_POST['mail'])) { echo "<p>Le format de l'adresse email est incorrect !</p>"; } ?><hr/>
            <label for="message">Message : </label><br/>
            <textarea cols="20" name="message" id="message"><?php if (isset($_POST['message'])) { echo $_POST['message']; } ?></textarea><hr/>
            <input type="submit" value="Envoyer">
        </fieldset>
    </form>

<?php include ('include/footer.php'); ?>