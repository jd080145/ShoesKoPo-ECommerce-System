<!--PHP Start--->
<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include "0/<>/connection.php";
$session_userID = $_SESSION['userID'];

if (!isset($_SESSION['userID'])) {
    header('Location: index.php');
}

else {
    $viewitemid = isset($_GET['id']) ? $_GET['id'] : '';

    $viewitem = "SELECT * FROM inventory WHERE productID='$viewitemid'";
    $resultitem = $conn->query($viewitem);

    if ($resultitem->num_rows == 1) {
        $data = $resultitem->fetch_assoc();
    }
}

// Add to Cart Button Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['addToCart-btn'])) {
        $noOfItems = $_POST['cartNumberItems'];
        $sqla = "SELECT * FROM `cart` WHERE userID='$session_userID' AND productID='$viewitemid'";
        $result1 = $conn->query($sqla);

        if ($result1->num_rows > 0) {
            $sqlb = "UPDATE cart
                    SET noOfItems = noOfItems + ?
                    WHERE userID = ? AND productID = ?";
            
            $stmt1 = $conn->prepare($sqlb);
        
            // Bind values to placeholders
            $stmt1->bind_param('iii', $noOfItems, $session_userID, $viewitemid);
        
            // Execute the statement
            try {
                if ($stmt1->execute()) {
                    echo "<script type='text/javascript'> window.onload = function () { alert('Item added to cart!'); window.location.href = 'view-item.php?id=$viewitemid'; }</script>";
                    exit();
                } else {
                    throw new Exception("Error adding the item to cart." . $conn->error);
                }
            } catch (Exception $e) {
                echo "<script>alert('Error adding the item to cart.'); window.location.href = 'view-item.php?id=$viewitemid';</script>";
            }
        
            // Close the statement
            $stmt1->close();
        }        
    
        else if ($result1->num_rows == 0){
        $sql2 = "INSERT INTO `cart`( `productID`, `userID`,`noOfItems`) VALUES ('$viewitemid','$session_userID','$noOfItems') ON DUPLICATE KEY UPDATE noOfItems = noOfItems + $noOfItems";
        try {
            if ($conn->query($sql2) === TRUE) {
                echo "<script type='text/javascript'> window.onload = function () { alert('Item added to cart!'); window.location.href = 'view-item.php?id=$viewitemid'; }</script>";
                exit(); 
            } else {
                throw new Exception("Error adding the item to cart." . $conn->error);
            }
        } catch (Exception $e) {
            echo "<script>alert('Error adding the item to cart.'); window.location.href = 'view-item.php?id=$viewitemid';</script>";
        }           
        }
}
// Add to Cart Button End

?>
<!--PHP End-->


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SHOESKOPO - Home</title>
    <link rel="icon" href="assets/logo/icon.png" type="image/png">

    <link rel="stylesheet" href="files/style.css">
    <link rel="stylesheet" href="bootstrap-5.0.2-dist/css/bootstrap.min.css">
    
</head>

<body class="mt-5 pt-5 bg-body">
    <div class="row container-fluid m-0 p-2 h-100">
        <?php include "navbar.php";?>
        
        <div class="row container-fluid m-0 px-1 py-3">
            <div class="col-lg-6 col-md-12 m-0 p-2 d-flex justify-content-center overflow-hidden ">
                    <img class="img-fluid" alt="<?php echo $data['productName']?>" src="data:image/jpeg;base64,<?php echo base64_encode($data["displayImage"]);?>" onerror="this.src='assets/product/no-image.jpeg';">
            </div>
            <div class="col-lg-6 col-sm-12 m-0 p-2">
                    <?php if (!empty($data['discount'])) : ?>
                        <div class="sale-badge start-0 bg-transparent" style="width:10em; height:10em;">
                            <img class="img img-fluid" src="assets/logo/sale-logo.png" alt="Sale" style="max-width: 100%; height: auto;">
                            <p class="text-light fw-bolder m-0" style="font-size:1em; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);"><?php echo $data['discount']."% OFF" ?></p>
                        </div>
                    <?php endif; ?>
                        <div class="p-2 overflow-hidden" style="height: 4em; line-height: 3rem;">
                            <p class="m-0 h6 fw-bolder text-dark" style="font-size: 1.5em;"><?php echo $data['productName'] ?></p>
                        </div>
                        
                        <div class="row ps-2 m-0 ">
                            <p class="col-12 m-0 p-0 row" style="font-size:1em;">
                                <?php
                                $final_price;
                                if (empty($data['discount'])) {
                                    $final_price=$data['price'];
                                    ?>
                                        <p class="text-danger fw-bolder px-0 m-0" style="font-size:1.5em"><?php echo '₱'.$final_price ?></p>
                                        <p class="px-0 m-0"></p>
                                    <?php
                                }
                                    
                                else {
                                    $discountedPrice=$data['price']-(($data['discount']*0.01)*$data['price']);
                                    $final_price=$discountedPrice; ?>
                                        <s class="text-secondary px-0 m-0" style="font-size:.65em;">₱<?php echo $data['price']; ?></s>
                                        <p class="text-danger fw-bolder px-0 m-0" style="font-size:1.5em"><?php echo ' '.'₱'.$final_price; ?></p>
                                    <?php
                                    }
                                
                                ?>
                            </p>
                        </div>
                        
                        <div class="row pt-2 m-0 ">
                            <p class="col-12 item-txt m-0" style="font-size:1em;"><?php echo $data['sold'] ?> sold</p>
                            <p class="col-12 item-txt m-0" style="font-size:1em;"><?php echo $data['stock']?> left</p>
                        </div>
                        <div class="row pt-2 m-0 ">
                            <p class="col-12 item-txt m-0 fw-bolder" style="font-size:1em;">Description:</p>
                            <p class="col-12 item-txt m-0" style="font-size:1em;"><?php echo $data['description'] ?></p>
                        </div>

                    </div>
                        
                        <div class="p-3 col-12 container-fluid d-flex justify-content-end">
                            <button type="button p-5" class="mx-3 py-1 px-3 btn btn-danger fw-bold" style="font-size:1.5em;" data-bs-toggle="modal" data-bs-target="#buyItem">
                            Purchase Item
                            </button>
                        </div>

                    <div class="modal" id="buyItem" tabindex="-1" data-bs-backdrop="relative" data-bs-keyboard="false" role="dialog" aria-labelledby="buyItemID" aria-hidden="true">
                                    <div class="modal-dialog modal-lg" role="document">
                                        <div class="modal-content bg-transparent">
                                        <div class="row container-fluid m-0 px-1 py-3">
                                            <form action="" method="post" class="container-fluid fixed-bottom bg-body row pt-3 p-2 m-0 text-end d-flex justify-content-end border border-start-0 border-end-0 border-danger border-2" style="z-index:5;">
                                                <div class="px-2 d-flex justify-content-start">
                                                    <button type="button px-3" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="col-lg-3 col-md-12 m-0 p-4 d-flex justify-content-end">
                                                    <div class="px-2 my-auto d-flex">
                                                        Number of Items:
                                                    </div>
                                                    <div class="d-flex p-0 m-0">
                                                        <input class="no-decor py-1 pe-3 border border-dark rounded-pill text-center" type="number" oninput="validateInput(this)" pattern="[1-9]{2}" min="1" max="<?php echo $data['stock']?>" id="cartNumberItems" name="cartNumberItems" style="width:7.5em;" value="1">
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-md-12 d-flex justify-content-end">
                                                    <button id="addToCart-btn" name="addToCart-btn" class="mx-3 py-1 px-3 btn btn-danger fw-bold" type="submit" style="font-size:1.5em;" data-bs-dismiss="modal">Add to Cart</button>
                                                <div>
                                            </form> 
                                        </div>
                                        </div>
                                    </div>
                                </div>
        </div>
        
    </div>
    <?php include "footer.php";?>
    
</body>
<script src="files/jquery-v3.6.3.js"></script>
<script src="bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js"></script>

<script>
    function validateInput(input) {
        input.value = input.value.replace(/[^0-9]/g, '');
        if (input.value > <?php echo $data['stock']?>) {
            input.value = <?php echo $data['stock']?>;
        }
        if (input.value < 0) {
            input.value = '';
        }
    }
</script>

</html>