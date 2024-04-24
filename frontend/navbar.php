<!--PHP Start-->
<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
include "0/<>/connection.php";
$success = false; // Initialize the success flag

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['login-btn'])) {
    $login_username = $_POST['login_username'];
    $login_password = $_POST['login_password'];

    $sql = "SELECT * FROM user WHERE username='$login_username'";
    $result = $conn->query($sql);

    if ($result->num_rows == 1) {
        $row = $result->fetch_assoc();
    }

    if (empty($login_username) || empty($login_password)) {
        echo "<script type='text/javascript'> window.onload = function () { alert('Username or password field is empty. Try again.'); window.location.href = 'index.php'; }</script>";
        exit();
    } else {
        // Use prepared statements to prevent SQL injection
        $stmt = $conn->prepare("SELECT * FROM user WHERE username=? AND password=PASSWORD(?)");
        $stmt->bind_param("ss", $login_username, $login_password);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows == 1) {
                $row = $result->fetch_assoc();
                
            $_SESSION['userID'] = $row['userID'];
            $_SESSION['logged_in'] = true;
            $success = true; // Set the success flag to true

            $session_userID=$_SESSION['userID'];

            $sql = "SELECT * FROM user WHERE userID='$session_userID'";
            $result = $conn->query($sql);

            if ($result->num_rows == 1) {
                $row = $result->fetch_assoc();

                $_SESSION['avatar'] = $row['avatar'];
            }

        }
        
        else {
            echo "<script type='text/javascript'> window.onload = function () { alert('Incorrect username or password.'); window.location.href = 'index.php'; }</script>";
            exit();
        }

        $stmt->close();
    }
    $conn->close();
}

if ($success) {
    // Display a welcome alert on successful login
    echo "<script type='text/javascript'> window.onload = function () { alert('Welcome back, $login_username!'); window.location.href = 'index.php?userID=$session_userID'; }</script>";
    exit(); 
    // Exit the script to prevent further execution
}

if (isset($_SESSION['userID'])) {
    $session_userID = $_SESSION['userID'];
    $sql = "SELECT * FROM user WHERE userID='$session_userID'";
    $result = $conn->query($sql);

    if ($result->num_rows == 1) {
        $row = $result->fetch_assoc();
    }
}

?>

<!--PHP End-->


<nav class="navbar navbar-expand-lg fixed-top px-0 mx-0" style="background: #222222; opacity:97.5%; z-index:30;">
    <div class="container-fluid p-auto m-0">
            <a class="navbar-brand" href="index.php"><img class="p-0 m-0 img-fluid" style="width: 5rem;" src="assets/logo/logo.png"></a>
                        <form class="p-1 mx-2 container d-flex my-lg-0 bg-body m-0 border border-dark input-group rounded-pill overflow-hidden" style="width:45vw;" action="item-list.php" method="post">
                                <input name="search_query" autocomplete="off" class="m-0 form-control rounded-end rounded-pill" type="text" placeholder="Search for a product..." style="border:none; font-size: .75rem">
                                <button name="search-btn" class="btn p-auto m-0 rounded-start rounded-pill" type="submit"><img class="img-fluid no-decor" src="assets/buttons/search.png"></button>
                        </form>
            <button class="navbar-toggler d-lg-none btn p-2 rounded-pill" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false" aria-label="Toggle navigation">
                <img class="img-fluid" style="height:20px;" src="assets/buttons/menu-light.png">
            </button>
            <div class="collapse navbar-collapse mt-4 my-lg-0 justify-content-end" id="collapsibleNavId">
                        <div class="p-0 m-0 d-flex float-end">
                <?php
                //NAVBAR IN
                if (isset($_SESSION['logged_in'])) { ?>
                                <button class="no-decor btn my-sm-0 text-light py-auto" type="button" onclick="window.location.href = 'account.php';" style="font-size:0.75em">
                                    <div class="d-flex p-0 m-0">
                                        <div class="rounded-pill border border-light overflow-hidden p-0 m-0 me-2 d-flex align-items-center justify-content-center" style="height:1.5em; width:1.5em;">
                                            <img class="img-fluid m-0 p-0" style="height:100%; object-fit:cover;" src="data:image/jpeg;base64,<?php echo base64_encode($row['avatar']);?>" onerror="this.src='assets/avatar/no-image-user.png';">
                                        </div>
                                        <?php echo $row['username']; ?>
                                    </div>
                                </button>
                            <div class="border border-start-1 border-light mx-2 bg-light"></div>
                                <button class="no-decor btn my-sm-0 text-light" type="button" onclick="window.location.href = 'shop.php';" style="font-size:0.75em">Shop</button>
                            <div class="border border-start-1 border-light mx-2 bg-light"></div>
                                <button type="button" class="no-decor btn my-sm-0 text-light" data-bs-toggle="modal" data-bs-target="#logoutmodal" style="font-size:0.75em">Log Out</button>
                <?php }
                //NAVBAR OUT
                else if (!isset($_SESSION['logged_in'])) { ?>
                                <button class="no-decor btn my-sm-0 text-light" type="button" onclick="window.location.href = 'signup.php';" style="font-size:0.75rem">Sign Up</button>
                            <div class="border border-start-1 border-light mx-2 bg-light"></div>
                                <button type="button" class="no-decor btn my-sm-0 text-light" data-bs-toggle="modal" data-bs-target="#loginPopup" style="font-size:0.75em">Log In</button>
                <?php }?>
                    </div>
            </div>
    </div>
</nav>

<!--LOGOUT START-->
<div class="modal fade border-1 border-dark shadow" id="logoutmodal" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="logoutmodalID" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header border-0">
                <p class="modal-title h5" id="logoutmodalID">Log Out</p>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body border-0">
                Do you want to log out?
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="m-1 btn btn-outline-success fw-bold" data-bs-dismiss="modal">No</button>
                <button type="button" class="m-1 btn btn-outline-secondary fw-bold" onclick="window.location.href = '0/<>/logout-db.php';">Yes</button>
            </div>
        </div>
    </div>
</div>
<!--LOGOUT END-->

<!--LOGIN START-->
<div class="modal fade" id="loginPopup" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="loginPopupTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header border-0">
                <h5 class="modal-title" id="loginPopupTitle"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                    <form class="modal-body border-0 bg-body container-fluid pt-3" action="" method="post">
                        <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                            <input class="p-2 ps-3 no-decor container-fluid" autocomplete="off" type="text" maxlength="50" name="login_username" placeholder="Username / E-mail Address">
                        </div>
                        <div class="my-2 mx-2 border border-dark d-flex rounded-pill">
                            <input class="m-1 p-auto form-control rounded-end rounded-pill" autocomplete="off" style="border:none;" type="password" maxlength="20" id="login_password" name="login_password" placeholder="Password">
                            <button class="btn rounded-start rounded-pill" type="button" id="check_login_password">
                                <img class="img-fluid" id="login_eye" src="assets/buttons/hide.png">
                            </button>
                        </div>
                    
                        <div class="p-2 pt-3 m-2 text-center">
                            <button id="login-btn" name="login-btn" class="m-1 btn btn-outline-success fw-bold" type="submit">Log In</button>
                            <p class="mt-3">Don't have an account yet? <a  class="text-success no-decor" href = 'signup.php'>Create one.</a></p>
                        </div>
                    </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    document.getElementById('check_login_password').addEventListener('click', function() {
    var inp = document.getElementById('login_password');
    var eye = document.getElementById('login_eye');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
    });
</script>

<!--LOGIN END-->
