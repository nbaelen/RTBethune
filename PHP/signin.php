<?php include ('include/header.php') ?>

<?php
$error = array (
    "mailSyntax" => false,
    "mailInDB" => false,
    "password" => false
);

if (isset($_POST['mail'], $_POST['password'], $_POST['passwordConfirm'])) {
    if (!preg_match("#^[a-z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$#", $_POST['mail'])) {
        $error['mailSyntax'] = true;
    } else {
        include ('include/database.php');

        $queryMail = $bdd->prepare('SELECT COUNT(*) AS exist FROM member WHERE mail = ?');
        $queryMail->execute(array($_POST['mail']));
        $data = $queryMail->fetch();

        if ($data['exist'] != 0) {
            $error['mailInDB'] = true;
        }
    }

    if ($_POST['password'] != $_POST['passwordConfirm']) {
        $error['password'] = true;
    }

    if (!in_array(true, $error)) {
        include ('include/database.php');

        $passwordHash = password_hash($_POST['password'],PASSWORD_DEFAULT);
        echo "<p>$passwordHash ".$_POST['mail']."</p>";
        $querySignin = $bdd->prepare('INSERT INTO member(mail, password) VALUES (?, ?)');
        $querySignin->execute(array($_POST['mail'],$passwordHash));

        $querySignin->closeCursor();
    }
}
?>

<form action="signin.php" method="post">
    <fieldset>
        <legend>Formulaire d'inscription</legend>
        <label for="mail">Adresse mail : </label>
        <input type="text" name="mail" id="mail" required/><br/>
        <?php if ($error['mailSyntax'] == true) { echo '<p class="alert">L\'adresse mail n\'est pas au bon format xx@xx.xx</p>'; } ?>
        <?php if ($error['mailInDB'] == true) { echo '<p class="alert">Un compte est déjà créé pour cette adresse mail !</p>'; } ?>
        <label for="password">Mot de passe : </label>
        <input type="password" name="password" id="password" required/>
        <label for="passwordConfirm">Confirmation de mot de passe : </label>
        <input type="password" name="passwordConfirm" id="passwordConfirm" required/>
        <?php if ($error['password'] == true) { echo '<p class="alert">Les deux mots de passe ne correspondent pas !</p>'; } ?>
        <input type="submit" value="Soumettre">
    </fieldset>
</form>

<?php include ('include/footer.php') ?>

