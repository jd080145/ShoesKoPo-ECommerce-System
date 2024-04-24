<!--PHP Start-->
<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
include "0/<>/connection.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['signup-btn'])) {
    

    // Retrieve form data
    $signup_first_name = $_POST['signup_first_name'];
    $signup_last_name = $_POST['signup_last_name'];
    $signup_username = $_POST['signup_username'];
    $signup_email = $_POST['signup_email'];
    $signup_password = $_POST['signup_password'];
    $signup_repassword = $_POST['signup_password2'];

    
    if (empty ($signup_first_name) || empty ($signup_last_name) || empty ($signup_username) || empty ($signup_email) || empty ($signup_password) || empty ($signup_repassword)) {
        echo "<script type='text/javascript'> window.onload = function () { alert('Some fields are left empty. Try again.'); window.location.href = 'signup.php'; }</script>";
        exit();
    } 
    else {
        if ($signup_repassword !== $signup_password) {
            echo "<script type='text/javascript'> window.onload = function () { alert('Passwords don\'t match'); window.location.href = 'signup.php'; }</script>";
            exit();
        } 
        else if ($signup_repassword === $signup_password) {
                    $sql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES ('$signup_username',PASSWORD('$signup_password'),'$signup_first_name','$signup_last_name','$signup_email')";
                    
                    try {
                        if ($conn->query($sql) === TRUE) {
                            echo "<script type='text/javascript'> window.onload = function () { alert('Account created successfully! Please log in to continue.'); window.location.href = 'index.php'; }</script>";
                            exit(); 
                        } else {
                            throw new Exception("Error updating profile: " . $conn->error);
                        } 
                    } catch (Exception $e) {
                        
                        if (strpos($e->getMessage(), "Duplicate entry") !== false && strpos($e->getMessage(), "'$signup_username' for key 'username'") !== false) {
                            echo "<script>alert('This username is already used by another account.'); window.location.href = 'signup.php';</script>";
                        } 
                        else if (strpos($e->getMessage(), "Duplicate entry") !== false && strpos($e->getMessage(), "'$signup_email' for key 'email'") !== false) {
                            echo "<script>alert('The e-mail address you provided is already used by another account.'); window.location.href = 'signup.php';</script>";
                        }
                        else {
                        }
                    }              
                }
            }
    $conn->close();
}

?>

<!--PHP End-->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SHOESKOPO - Sign Up</title>
    <link rel="icon" href="assets/logo/icon.png" type="image/png">

    <link rel="stylesheet" href="files/style.css">
    <link rel="stylesheet" href="bootstrap-5.0.2-dist/css/bootstrap.min.css">

</head>
<body class="mt-5 pt-5 h-100">
<?php include "navbar.php";?>
<div class="h-100 px-3">
    <div class="row m-3 p-0 shadow rounded overflow-hidden bg-body">
            <div class="p-0 m-0 col-lg-6 col-12 shadow d-flex justify-content-center overflow-hidden">
                <img class="p-0 m-0 img img-fluid" style="object-fit:cover; width:100%; height:100%;" src="assets/bg/shoes_bg.png">
            </div>

            <div class="col-lg-6 col-12 p-0 m-0 container-fluid">
                <div class="bg-dark p-0 mb-3">
                        <h6 class="fw-bolder text-light m-0 p-3">Create an Account</h6>
                </div>

                <form class="px-3 py-5" id="divsignup" action="signup.php" method="post">

                <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                            <input autocomplete="off" class="p-2 ps-3 no-decor container-fluid" type="text" maxlength="20" name="signup_first_name" placeholder="First Name">
                            <input autocomplete="off" class="p-2 ps-3 no-decor container-fluid" type="text" maxlength="20" name="signup_last_name" placeholder="Surname">
                </div>
                <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                            <input autocomplete="off" class="p-2 ps-3 no-decor container-fluid" type="text" maxlength="15" name="signup_username" placeholder="Username">
                </div>
                <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                            <input autocomplete="off" class="p-2 ps-3 no-decor container-fluid" type="email" maxlength="50" name="signup_email" placeholder="E-mail Address">
                </div>
                <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                    <input autocomplete="off" class="p-2 ps-3 m-1 form-control rounded-end rounded-pill" style="border:none;"
                     type="password" id="signup_password" maxlength="20" name="signup_password" placeholder="Password">
                    <button class="btn rounded-start rounded-pill" type="button" id="check_signup_password">
                        <img class="img-fluid" id="signup_eye" src="assets/buttons/hide.png">
                    </button>
                </div>
                <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                    <input autocomplete="off" class="p-2 ps-3 m-1 form-control rounded-end rounded-pill" style="border:none;" 
                    type="password" id="signup_password2" maxlength="20" name="signup_password2" placeholder="Repeat Password">
                    <button class="btn rounded-start rounded-pill" type="button" id="check_signup_password2">
                        <img class="img-fluid" id="signup_eye2" src="assets/buttons/hide.png">
                    </button>
                </div>

                <div class="pt-5 p-2 m-2 text-center">
                    <button id="signup-btn" name="signup-btn" class="m-1 btn btn-outline-success fw-bold" type="submit">Sign Up</button>
                    <p class="mt-3">Already have an account? <a id="login" class="text-success no-decor" data-bs-toggle="modal" data-bs-target="#loginPopup">Log in.</a></p>
                </div>
            </form>
        </div>
    </div>
</div>

<?php include "footer.php";?>
</body>

<script src="files/jquery-v3.6.3.js"></script>
<script src="bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">
    document.getElementById('check_signup_password').addEventListener('click', function() {
    var inp = document.getElementById('signup_password');
    var eye = document.getElementById('signup_eye');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});

document.getElementById('check_signup_password2').addEventListener('click', function() {
    var inp = document.getElementById('signup_password2');
    var eye = document.getElementById('signup_eye2');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});
</script>

</html>