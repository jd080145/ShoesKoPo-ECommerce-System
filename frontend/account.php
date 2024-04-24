<!--PHP Start-->
<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

include "0/<>/connection.php";
$session_userID = $_SESSION['userID'];

if (!isset($_SESSION['userID'])) {
    header('Location: index.php');
}

$account_deleted=false;

//Show User Data Start
$sql = "SELECT * FROM user WHERE userID='$session_userID'";
$result = $conn->query($sql);

if ($result->num_rows == 1) {
    $row = $result->fetch_assoc();
}
//Show User Data End

// Update Profile to Database Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['updateProfile-btn'])) {
    $first_name_update = $_POST['fname_update'];
    $last_name_update = $_POST['lname_update'];
    $email_update = $_POST['email_update'];
    $phone_update = $_POST['phone_update'];
    $gender_update = $_POST['gender_update'];
    $birthday_update = $_POST['birthday_update'];

    if (empty($first_name_update) || empty($last_name_update) || empty($email_update) || empty($phone_update) || empty($gender_update) || empty($birthday_update)) {
        echo "<script>alert('Some fields are left empty. Try Again.'); window.location.href = 'account.php';</script>";
        exit();
    }
    
    else {
            // Update the profile
            $sql = "UPDATE user SET firstName='$first_name_update', lastName='$last_name_update', email='$email_update', phone='$phone_update', gender='$gender_update', bday='$birthday_update' WHERE userID='$session_userID'";

            try {
                if ($conn->query($sql) === TRUE) {
                    echo "<script>alert('Profile updated successfully!'); window.location.href = 'account.php';</script>";
                    exit();
                } else {
                    throw new Exception("Error updating profile: " . $conn->error);
                }
            } catch (Exception $e) {
                
                if (strpos($e->getMessage(), "Duplicate entry") !== false && strpos($e->getMessage(), "'$phone_update' for key 'phone'") !== false) {
                    echo "<script>alert('The phone number you provided is already used by another account.'); window.location.href = 'account.php';</script>";
                } else if (strpos($e->getMessage(), "Duplicate entry") !== false && strpos($e->getMessage(), "'$email_update' for key 'email'") !== false) {
                    echo "<script>alert('The e-mail address you provided is already used by another account.'); window.location.href = 'account.php';</script>";
                } else {
                    exit();
                }
            }
        }
}
// Update Profile to Database End

// Update Profile Picture to Database Start
else if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['uploadAvatar-btn'])) {

    if ($_FILES['avatar_update']['error'] == UPLOAD_ERR_OK) {
        $tmp_name = $_FILES['avatar_update']['tmp_name'];
        $img_data = file_get_contents($tmp_name);

        // Check if the file is an image
        $allowed_types = ['image/jpeg', 'image/png', 'image/gif'];
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $mime_type = finfo_file($finfo, $tmp_name);
        finfo_close($finfo);

        if (!in_array($mime_type, $allowed_types)) {
            echo "<script>alert('Invalid file type. Try again.'); window.location.href = 'account.php';</script>";
            exit();
        }

        // Check if the file size is within the limit (16 MB)
        $max_size = 1 * 1024 * 1024;
        if ($_FILES['avatar_update']['size'] > $max_size) {
            echo "<script>alert('The image exceeds the maximum file size limit. (Maximum file size limit: 1 MB.)'); window.location.href = 'account.php';</script>";
            exit();
        }

        // Prepare and execute the SQL query to update the avatar column
        $sql = "UPDATE user SET avatar = ? WHERE userID = ?";
        $stmt = $conn->prepare($sql);
        
        // Bind the parameters
        $stmt->bind_param('ss', $img_data, $session_userID);
        
        // Execute the query
        if ($stmt->execute()) {
            echo "<script>alert('Profile picture updated successfully.'); window.location.href = 'account.php';</script>";
        } else {
            echo "<script>alert('Error updating profile picture. Try again.'); window.location.href = 'account.php';</script>";
        }

        // Close the statement
        $stmt->close();
    } else {
        echo "<script>alert('Error uploading the image. Try again.'); window.location.href = 'account.php';</script>";
    }
}
// Update Profile Picture to Database End

//Update Password to Database Start
else if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['updatePassword-btn'])) {
$old_password = $_POST['old_password'];
$new_password = $_POST['new_password'];
$new_password2 = $_POST['new_password2'];

    if (empty($old_password) || empty($new_password) || empty($new_password2)){
        echo "<script type='text/javascript'> window.onload = function () { alert('Some fields are left empty. Try again.'); window.location.href = 'account.php'; }</script>";
    }

    else {
        $sql = "SELECT * FROM user WHERE userID='$session_userID'";
        $result = $conn->query($sql);

        if ($result->num_rows == 1) {
            $row = $result->fetch_assoc();

            $query = "SELECT * FROM user WHERE userID='$session_userID' AND password = PASSWORD('$old_password')";
            $result = $conn->query($query);

            if ($result->num_rows == 0) {
                echo "<script type='text/javascript'> window.onload = function () { alert('Incorrect password. Try Again.'); window.location.href = 'account.php'; }</script>";
            }

            else{
                if ($new_password != $new_password2) {
                    echo "<script type='text/javascript'> window.onload = function () { alert('New passwords do not match. Try Again.'); window.location.href = 'account.php'; }</script>";
                }
                
                else {
                    $sql = "UPDATE user SET password = PASSWORD('$new_password') WHERE userID = '$session_userID'";
                
                    if ($conn->query($sql) === TRUE) {
                        echo "<script type='text/javascript'> window.onload = function () { alert('Password updated successfully!'); window.location.href = 'account.php'; }</script>";
                        exit();
                    } else {
                        echo "<script type='text/javascript'> window.onload = function () { alert('There is an error in updating your password. Try Again.'); window.location.href = 'account.php'; }</script>";
                    }
                }
            }
        } 
        else {
            echo "<script type='text/javascript'> window.onload = function () { alert('New passwords do not match. Try Again.'); window.location.href = 'account.php'; }</script>";
            }
    }
}
//Update Password to Database End

//Update Address to Database Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['updateAddress-btn'])) {

    $region_update = $_POST['region'];
    $province_update = $_POST['province'];
    $city_update = $_POST['city'];
    $brgy_update = $_POST['brgy'];
    $street_update = $_POST['street'];
    $zip_update = $_POST['zip'];
    $landmark_update = $_POST['landmark'];
    
    
        if (empty($region_update) || empty($province_update) || empty($city_update) || empty($brgy_update) || empty($street_update) || empty($zip_update)){
            echo "<script type='text/javascript'> window.onload = function () { alert('Some important fields are left empty. Try again.'); window.location.href = 'account.php'; }</script>";
        }
    
        else {
                $sql = "UPDATE user SET street='$street_update', brgy='$brgy_update', city='$city_update', province='$province_update', region='$region_update', zip='$zip_update', landmark='$landmark_update' WHERE userID='$session_userID'";
    
                if ($conn->query($sql) === TRUE) {
                    echo "<script type='text/javascript'> window.onload = function () { alert('Address updated successfully!'); window.location.href = 'account.php'; }</script>";
                    exit();
                } else {
                    echo "<script type='text/javascript'> window.onload = function () { alert('There is an error in updating your address. Try Again.'); window.location.href = 'account.php'; }</script>";
                }
        }
    }
//Update Address to Database End

//Update Card Details Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['updateCard-btn'])) {

    $card_number_update = $_POST['card_update'];
    $expiration_update = $_POST['expiration_update'];
    $cvv_update = $_POST['cvv_update'];
    $cardholder_update = $_POST['card_name_update'];

    if (empty($card_number_update) || empty($expiration_update)  || empty($cvv_update) || empty($cardholder_update)) {
        echo "<script type='text/javascript'> window.onload = function () { alert('Some important fields are left empty. Try again.'); window.location.href = 'account.php'; }</script>";
    }

    else {
        $sql = "UPDATE user SET cardNo='$card_number_update',cardExp='$expiration_update', cardCVV='$cvv_update', cardholder='$cardholder_update' WHERE userID='$session_userID'";

        if ($conn->query($sql) === TRUE) {
            echo "<script type='text/javascript'> window.onload = function () { alert('Card payment updated successfully!'); window.location.href = 'account.php'; }</script>";
            exit();
        } else {
            echo "<script type='text/javascript'> window.onload = function () { alert('There is an error in updating your card details. Try Again.'); window.location.href = 'account.php'; }</script>";
        }
    }

}
//Update Card Details End

// Delete Account Start
else if (isset($_POST['delete-btn'])) {
    // SQL query to delete the user account
    $sql = "DELETE FROM user WHERE userID='$session_userID'";

    if ($conn->query($sql) === TRUE) {
        $account_deleted = true;
    } else {
        echo "<script type='text/javascript'> window.onload = function () { alert('Error deleting account: $conn->error'); window.location.href = 'index.php'; }</script>";
    }
}
// Delete Account End

if ($account_deleted) {
    echo "<script type='text/javascript'>
            alert('Account deleted successfully. We hope to see you again!');
            window.location.href = 'index.php';
          </script>";
    session_destroy();
    exit();
}
?>

<!--PHP End-->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SHOESKOPO - Account</title>
    <link rel="icon" href="assets/logo/icon.png" type="image/png">

    <link rel="stylesheet" href="files/style.css">
    <link rel="stylesheet" href="bootstrap-5.0.2-dist/css/bootstrap.min.css">
    
</head>
<body class="mt-5 pt-5">
<?php include "navbar.php";?>
<div>
    <div class="container p-0">
                <!--Update Profile Start-->
                <div class="card shadow rounded my-5 p-0 overflow-hidden">
                    <div class="bg-dark p-0 mb-3">
                        <h6 class="fw-bolder text-light m-0 p-3"><?php echo $row['username']; ?>'s  Profile</h6>
                    </div>

                    <div class="container-fluid m-0 row py-3">
                    <div class="col-lg-6 col-12 p-2 py-4 m-0">
                        <div class="d-flex justify-content-center p-3">
                            <img class="img-fluid border border-dark border-2 shadow rounded-pill" onclick="" style="height:20vh; width: 20vh; object-fit:cover;" src="data:image/*;base64,<?php echo base64_encode($row['avatar']); ?>" onerror="this.src='assets/avatar/no-image-user.png';"  alt="profile_picture <?php echo $session_userID; ?>">
                        </div>
                        <form class="p-0 m-0" action="" method="post" enctype="multipart/form-data">
                            <div class="m-0 py-3 row">
                                <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Change Profile Picture</p>
                                <div class="p-0 m-0 d-flex border border-dark overflow-hidden rounded-pill">
                                        <input class="px-2 m-1 p-auto form-control rounded-end rounded-pill" style="border:none;" type="file" id="avatar_update" accept="image/*" name="avatar_update">
                                        <button class="btn p-auto rounded-start rounded-pill" type="submit" id="uploadAvatar-btn" name="uploadAvatar-btn">
                                            <img class="img-fluid" src="assets/buttons/upload.png">
                                        </button>
                                </div>
                                <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.40rem;">* Please use JPEG, PNG, or GIF photos not exceeding 1 MB in size.</p>
                            </div>
                        </form>
                    </div>

                    <div class="col-lg-6 col-12 p-0 m-0">
                        <form class="" action="account.php" method="post">
                        <div class="p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">First Name</p>
                            <div class="border border-dark d-flex rounded-pill">
                                    <input class="p-2 ps-3 no-decor container-fluid" type="text" maxlength="20" name="fname_update" autocomplete="off" placeholder="First Name" value="<?php echo $row['firstName']; ?>">
                        </div></div>
                        <div class="col-12 p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Surname</p>
                            <div class="col-12 border border-dark d-flex rounded-pill">
                                    <input class="p-2 ps-3 no-decor container-fluid" type="text" maxlength="20" name="lname_update" autocomplete="off" placeholder="Surname" value="<?php echo $row['lastName']; ?>">
                        </div></div>
                        <div class="col-12 p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">E-mail Address</p>
                            <div class="col-12 border border-dark d-flex rounded-pill">
                                    <input class="p-2 ps-3 no-decor container-fluid" type="email" maxlength="50" name="email_update" autocomplete="off" placeholder="E-mail Address" value="<?php echo $row['email']; ?>">
                        </div></div>
                        <div class="col-12 p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Phone Number</p>
                            <div class="col-12 border border-dark d-flex rounded-pill">
                                    <input class="p-2 ps-3 no-decor container-fluid" title="Please enter a valid phone number (e.g., 09123456789)" pattern="[0-9]{11}" oninput="validateNumber(this)" type="text" maxlength="11" name="phone_update" autocomplete="off" placeholder="Phone Number" value="<?php echo $row['phone']; ?>">
                            </div>
                        </div>
                        <div class="col-12 p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Gender</p>
                            <div class="col-12 border border-dark d-flex rounded-pill">
                                    <select class="p-2 ps-3 no-decor container-fluid" name="gender_update" placeholder="Gender">
                                        <option <?php if ($row['gender'] == 'Prefer not to say') echo 'selected'; ?>>Prefer not to say</option>
                                        <option <?php if ($row['gender'] == 'Male') echo 'selected'; ?>>Male</option>
                                        <option <?php if ($row['gender'] == 'Female') echo 'selected'; ?>>Female</option>
                                    </select>
                            </div>
                        </div>
                        
                        <div class="col-12 p-2 m-0 row">
                            <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Date of Birth</p>
                            <div class="col-12 border border-dark d-flex rounded-pill">
                                    <input class="p-2 ps-3 no-decor container-fluid" type="date" id="birthday" name="birthday_update" placeholder="Date of Birth" value="<?php echo $row['bday']; ?>">
                            </div>
                        </div>

                        <div class="pt-3 p-2 m-2 text-end">
                            <button id="updateProfile-btn" name="updateProfile-btn" class="m-1 btn btn-outline-dark fw-bold" type="submit">Update Profile</button>
                        </div>

                        </form>
                    </div>
                </div></div>
                    
                <!--Update Profile End-->

                <!--Update Billing Start-->
                <div class="card shadow rounded my-5 p-0 overflow-hidden">
                    <div class="bg-dark p-0 mb-3">
                        <h6 class="fw-bolder text-light m-0 p-3">Card Payment</h6>
                    </div>
                <form class="container-fluid m-0 row py-3" action="account.php" method="post">

                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Card Number</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" oninput="validateNumber(this)" type="text" autocomplete="off" maxlength="20" name="card_update" placeholder="0000 0000 0000 0000" value="<?php echo $row['cardNo']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Expiration Date</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" pattern="[0-9]{2}/[0-9]{2}" title="Please enter a valid expiration date (e.g., 10/30)"  type="text" autocomplete="off" maxlength="5" name="expiration_update" placeholder="MM/YY" value="<?php echo $row['cardExp']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">CVV</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" oninput="validateNumber(this)" type="text" autocomplete="off" maxlength="3" name="cvv_update" placeholder="123" value="<?php echo $row['cardCVV']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Cardholder's Name</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="card_name_update" placeholder="<?php echo $row['firstName'].' '.$row['lastName']; ?>" value="<?php echo $row['cardholder']; ?>">
                    </div></div>


                    <div class="pt-3 p-2 m-2 text-end">
                        <button id="updateCard-btn" name="updateCard-btn" class="m-1 btn btn-outline-dark fw-bold" type="submit">Update Card Details</button>
                    </div>
                </form></div>
                <!--Update Billing End-->

                <!--Update Address Start-->
                <div class="card shadow rounded my-5 p-0 overflow-hidden">
                    <div class="bg-dark p-0 mb-3">
                        <h6 class="fw-bolder text-light m-0 p-3">Shipping Address</h6>
                    </div>
                <form class="container-fluid m-0 row py-3" action="account.php" method="post">

                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Region</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="region" placeholder="Region" value="<?php echo $row['region']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Province</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="province" placeholder="Province" value="<?php echo $row['province']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">City / Municipality</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="city" placeholder="City / Municipality" value="<?php echo $row['city']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Barangay</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="brgy" placeholder="Barangay" value="<?php echo $row['brgy']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Street / Lot No.</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" type="text" autocomplete="off" maxlength="30" name="street" placeholder="Street / Lot No." value="<?php echo $row['street']; ?>">
                    </div></div>
                    <div class="col-lg-6 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Zip Code</p>
                        <div class="col-12 border border-dark d-flex rounded-pill">
                                <input class="p-2 ps-3 no-decor container-fluid" oninput="validateNumber(this)" type="text" autocomplete="off" maxlength="30" name="zip" placeholder="Zip Code" value="<?php echo $row['zip']; ?>">
                    </div></div>
                    <div class="col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Landmarks / Additional Information</p>
                        <div class="col-12 border border-dark d-flex rounded p-2">
                                <textarea class="p-2 ps-3 no-decor container-fluid" name="landmark" autocomplete="off" maxlength="150" placeholder="Landmarks / Additional Information"><?php echo $row['landmark']; ?></textarea>
                    </div></div>

                    <div class="pt-3 p-2 m-2 text-end">
                        <button id="updateAddress-btn" name="updateAddress-btn" class="m-1 btn btn-outline-dark fw-bold" type="submit">Update Address</button>
                    </div>
                </form></div>
                <!--Update Address End-->

                <!--Update Password Start-->
                <div class="card shadow rounded my-5 p-0 overflow-hidden">
                    <div class="bg-dark p-0 mb-3">
                        <h6 class="fw-bolder text-light m-0 p-3">Change Password</h6>
                    </div>
                <form class="container-fluid m-0 row py-3"  action="account.php" method="post">
    
                    <div class="col-lg-4 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Password</p>
                        <div class="px-0 container d-flex m-0 border border-dark input-group overflow-hidden rounded-pill">
                        <input class="px-2 m-1 p-auto form-control rounded-end rounded-pill" style="border:none;" type="password" autocomplete="off" maxlength="20" id="old_password" name="old_password" placeholder="Password">
                        <button class="btn rounded-start rounded-pill" type="button" id="check_old_password">
                            <img class="img-fluid" id="old_password_eye" src="assets/buttons/hide.png">
                        </button>
                    </div></div>
                    <div class="col-lg-4 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">New Password</p>
                        <div class="px-0 container d-flex m-0 border border-dark input-group overflow-hidden rounded-pill">
                        <input class="px-2 m-1 p-auto form-control rounded-end rounded-pill" style="border:none;" type="password" autocomplete="off" maxlength="20" id="new_password" name="new_password" placeholder="New Password">
                        <button class="btn rounded-start rounded-pill" type="button" id="check_new_password">
                            <img class="img-fluid" id="new_password_eye" src="assets/buttons/hide.png">
                        </button>
                    </div></div>
                    <div class="col-lg-4 col-12 p-2 m-0 row">
                        <p class="col-12 m-0 py-1 fw-bolder" style="font-size:.5rem;">Repeat New Password</p>
                        <div class="px-0 container d-flex m-0 border border-dark input-group overflow-hidden rounded-pill">
                        <input class="px-2 m-1 p-auto form-control rounded-end rounded-pill" style="border:none;" type="password" autocomplete="off" maxlength="20" id="new_password2" name="new_password2" placeholder="Repeat New Password">
                        <button class="btn rounded-start rounded-pill" type="button" id="check_new_password2">
                            <img class="img-fluid" id="new_password_eye2" src="assets/buttons/hide.png">
                        </button>
                    </div></div>

                    <div class="pt-3 p-2 m-2 text-end">
                        <button id="updatePassword-btn" name="updatePassword-btn" class="m-1 btn btn-outline-dark fw-bold" type="submit">Update Password</button>
                    </div>
                </form></div>
                <!--Update Password End-->

                <!--Delete Account Start-->
                <div class="pt-3 my-5 text-end">
                    <button id="deleteAcc-btn" name="deleteAcc-btn" class="m-1 btn btn-secondary fw-bold border-dark shadow" type="button" data-bs-toggle="modal" data-bs-target="#deleteAccPopup">Delete Account</button>
                </div>

                <div class="modal fade" id="deleteAccPopup" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="deleteAccTitle" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content border border-danger">
                        <form action="" method="post">
                            <div class="modal-header border-0">
                                <h5 class="modal-title" id="deleteAccTitle">Account Deletion</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <h6 class="text-danger fw-bold">Warning:<h6>
                                <p>Once you delete your account, it is irreversable and data will be gone forever. Are you sure that you want to delete your account?</p>
                            </div>
                            <div class="modal-footer border-0">
                                <button type="button" class="m-1 btn fw-bold btn-outline-danger" data-bs-dismiss="modal">Cancel</button>
                                <button name="delete-btn" type="submit" class="m-1 btn fw-bold btn-outline-secondary">Delete Account</button>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!--Delete Account End-->
    </div>
</div>

<?php include "footer.php";?>
</body>
<script src="files/jquery-v3.6.3.js"></script>
<script src="bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">
    document.getElementById('check_old_password').addEventListener('click', function() {
    var inp = document.getElementById('old_password');
    var eye = document.getElementById('old_password_eye');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});

document.getElementById('check_new_password').addEventListener('click', function() {
    var inp = document.getElementById('new_password');
    var eye = document.getElementById('new_password_eye');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});

document.getElementById('check_new_password2').addEventListener('click', function() {
    var inp = document.getElementById('new_password2');
    var eye = document.getElementById('new_password_eye2');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});

function validateNumber(input) {
    input.value = input.value.replace(/[^0-9]/g, '');
}

</script>

</html>