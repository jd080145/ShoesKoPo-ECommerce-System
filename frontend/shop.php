<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

include "0/<>/connection.php";

// Check if the user is logged in
if (!isset($_SESSION['userID'])) {
    header('Location: index.php');
    exit(); // Always exit after a header redirect
}

$session_userID = $_SESSION['userID'];

// Show Shopping Cart Items Start
$cartItemsQuery = "SELECT cart.*, inventory.productName, inventory.price, inventory.discount, inventory.displayImage, inventory.stock, inventory.sold
                   FROM cart
                   INNER JOIN inventory ON cart.productID = inventory.productID
                   WHERE cart.userID = '$session_userID'";
$cartResult = $conn->query($cartItemsQuery);

$cartItems = [];
while ($cartItem = $cartResult->fetch_assoc()) {
    $cartItems[] = $cartItem;
}

// Update Shopping Cart Item Quantity Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['updateCartItem-btn'])) {
    $newqty = $_POST['cartqtyUpdate'];
    $prodID = $_POST['prodID'];

    $updateqty = "UPDATE cart SET noOfItems='$newqty' WHERE userID='$session_userID' AND productID='$prodID'";

    try {
        if ($conn->query($updateqty) === TRUE) {
            echo "<script>alert('Quantity updated!'); window.location.href = 'shop.php';</script>";
            exit();
        } else {
            throw new Exception("Error updating quantity: " . $conn->error);
        }
    } catch (Exception $e) {
        // Handle the exception, if needed
        echo "<script>alert('Error updating quantity.'); window.location.href = 'shop.php';</script>";
        exit();
    }
}
// Update Shopping Cart Item Quantity End


// Checkout Items Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['checkOut-btn'])) {
    $selectedProducts = isset($_POST['checkoutItems']) ? $_POST['checkoutItems'] : [];

    if (empty($selectedProducts)) {
        echo "<script>alert('Please select at least one item to checkout.');</script>";
    } else {
        $selectedProductsString = implode(',', $selectedProducts);
        $subtotalPrice = 0;

        foreach ($cartItems as $cartItem) {
            if (in_array($cartItem['productID'], $selectedProducts)) {
                $discountedPrice = $cartItem['price'] - (($cartItem['discount'] * 0.01) * $cartItem['price']);

                $stockUpdate = $cartItem['stock'] - $cartItem['noOfItems'];
                $soldUpdate = $cartItem['sold'] + $cartItem['noOfItems'];

                $updateInventory = "UPDATE inventory SET stock='$stockUpdate', sold='$soldUpdate' WHERE productID = '{$cartItem['productID']}'";

                if (empty($cartItem['discount'])) {
                    $subtotalPrice += $cartItem['price'] * $cartItem['noOfItems'];
                } else {
                    $subtotalPrice += $discountedPrice * $cartItem['noOfItems'];
                }

                // Execute the inventory update query for the current product
                $conn->query($updateInventory);
            }
        }

        $insertOrderQuery = "INSERT INTO orders (userID, productID, noOfItems, date, time, subtotalPrice)
                            SELECT userID, productID, noOfItems, NOW(), NOW(), '$subtotalPrice'
                            FROM cart
                            WHERE userID = '$session_userID' AND productID IN ($selectedProductsString)";
        $clearCartQuery = "DELETE FROM cart WHERE userID = '$session_userID' AND productID IN ($selectedProductsString)";

        try {
            $conn->query($insertOrderQuery);
            $conn->query($clearCartQuery);

            echo "<script>alert('Checkout successful!'); window.location.href = 'shop.php';</script>";
            exit();
        } catch (Exception $e) {
            echo "<script>alert('Error during checkout.'); window.location.href = 'shop.php';</script>";
        }
    }
}
//Checkout Items End

// Show Order Items Start
$orderItemsQuery = "SELECT orders.*, inventory.productName, inventory.price, inventory.discount, inventory.displayImage
                   FROM orders
                   INNER JOIN inventory ON orders.productID = inventory.productID
                   WHERE orders.userID = '$session_userID'";
$orderResult = $conn->query($orderItemsQuery);

$orderItems = [];
while ($orderItem = $orderResult->fetch_assoc()) {
    $orderItems[] = $orderItem;
}
// Show Order Items End

// Confirm Items Received Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['recieved-btn'])) {
    // Retrieve only the checked items
    $selectedProducts = isset($_POST['checkoutItems']) ? $_POST['checkoutItems'] : [];

    if (empty($selectedProducts)) {
        echo "<script>alert('Please select at least one item you received.');</script>";
    } else {
        $selectedProductsString3 = implode(',', $selectedProducts);

        $insertOrderCompletedQuery = "INSERT INTO completed_orders (userID, productID, noOfItems, date, time, subtotalPrice)
                            SELECT userID, productID, noOfItems, NOW(), NOW(), subtotalPrice
                            FROM orders
                            WHERE userID = '$session_userID' AND productID IN ($selectedProductsString3)";
        $clearOrderQuery = "DELETE FROM orders WHERE userID = '$session_userID' AND productID IN ($selectedProductsString3)";

        try {
            $conn->query($insertOrderCompletedQuery);
            $conn->query($clearOrderQuery);

            echo "<script>alert('Thank you! We hope to see you again!'); window.location.href = 'shop.php';</script>";
            exit();
        } catch (Exception $e) {
            echo "<script>alert('Error during confirmation.'); window.location.href = 'shop.php';</script>";
        }
    }
}
// Confirm Items Received End


//Remove Items from Cart Start
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['removeItem-btn'])) {
    // Retrieve only the checked items
    $selectedProducts2 = isset($_POST['checkoutItems']) ? $_POST['checkoutItems'] : [];

    if (empty($selectedProducts2)) {
        echo "<script>alert('Please select at least one item to checkout.');</script>";
    } else {
        $selectedProductsString2 = implode(',', $selectedProducts2);

        $clearCartQuery2 = "DELETE FROM cart WHERE userID = '$session_userID' AND productID IN ($selectedProductsString2)";

        try {
            $conn->query($clearCartQuery2);

            echo "<script>alert('Item(s) removed from the cart.'); window.location.href = 'shop.php';</script>";
            exit();
        } catch (Exception $e) {
            echo "<script>alert('Error removing the item from the cart.'); window.location.href = 'shop.php';</script>";
        }
    }
}
//Remove Items from Cart
?>
<!-- PHP End -->

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
<body class="mt-5 pt-5 h-100">
    <?php include "navbar.php";?>
    <div class="h-100">
        <div class="row m-1 bg-body">
            
            <div class="p-2 col-sm-12">
                <form action="" method="post">
                    <div class="shadow p-0 rounded overflow-hidden">
                        <div class="bg-dark p-0">
                            <p class="fw-bolder text-light m-0 p-3">Shopping Cart</p>
                        </div>
                        <div class="p-4">
                            <?php
                            if (empty($cartItems)) {
                                echo '<p>There are no items in your shopping cart.</p>';
                            } 
                            else {
                            foreach ($cartItems as $cartItem) {
                                // Display each item in the shopping cart
                                $prodID = $cartItem['productID'];
                                include 'cart-item.php';
                                }
                            }
                            ?>
                        </div>
                        <div class="p-3 text-end">
                            
                            <button id="confirmremoveItem-btn" name="confirmremoveItem-btn" class="m-1 btn btn-secondary fw-bold border-dark shadow" type="button" data-bs-toggle="modal" data-bs-target="#ConfirmRemovePopup">Remove Item(s)</button>
                            <!--Confirm Items Remove-->
                            <div class="modal fade text-start" id="ConfirmRemovePopup" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="ConfirmRemoveTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
                                    <div class="modal-content border border-danger">
                                        <div class="modal-header border-0">
                                            <h5 class="modal-title" id="ConfirmRemoveTitle">Remove From Cart</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Do you want to remove these item(s) from the cart?</p>
                                        </div>
                                        <div class="modal-footer border-0">
                                            <button type="button" class="m-1 btn fw-bold btn-outline-danger" data-bs-dismiss="modal">Cancel</button>
                                            <button id="removeItem-btn" name="removeItem-btn" type="submit" class="m-1 btn fw-bold btn-outline-secondary">Remove</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--Confirm Items Remove-->

                            <button id="confirmcheckOut-btn" name="confirmcheckOut-btn" class="m-1 btn btn-danger fw-bold border-dark shadow" type="button" data-bs-toggle="modal" data-bs-target="#ConfirmCheckoutPopup">Checkout Items</button>
                            <!--Confirm Items Checkout-->
                            <div class="modal fade text-start" id="ConfirmCheckoutPopup" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="ConfirmCheckoutTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
                                    <div class="modal-content border border-danger">
                                        <div class="modal-header border-0">
                                            <h5 class="modal-title" id="ConfirmCheckoutTitle">Confirm Purchase</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Do you want to purchase these item(s)?</p>
                                        </div>
                                        <div class="modal-footer border-0">
                                            <button type="button" class="m-1 btn fw-bold btn-outline-danger" data-bs-dismiss="modal">Cancel</button>
                                            <button id="checkOut-btn" name="checkOut-btn" type="submit" class="m-1 btn fw-bold btn-outline-secondary">Purchase</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--Confirm Items Checkout-->
                        </div>
                    </div>
                </form>
            </div>

            <div class="p-2 col-sm-12">
                <form action="" method="post">
                <div class="shadow p-0 rounded overflow-hidden">
                    <div class="bg-dark p-0">
                        <p class="fw-bolder text-light m-0 p-3">Pending Orders</p>
                    </div>
                    <div class="p-4">
                        <?php
                        if (empty($orderItems)) {
                            echo '<p>You have no pending orders.</p>';
                        } 
                        else {
                        foreach ($orderItems as $orderItem) {
                            // Display each item in the shopping cart
                            $prodID2 = $orderItem['productID'];
                            include 'order-item.php';
                            }
                        }
                        ?>
                    </div>
                    
                    <div class="p-3 text-end">
                            <button id="confirmRecieve-btn" name="confirmRecieve-btn" class="m-1 btn btn-danger fw-bold border-dark shadow" type="button" data-bs-toggle="modal" data-bs-target="#ConfirmRecievedPopup">Items Recieved</button>
                    </div>

                    <!--Confirm Items Recieved-->
                    <div class="modal fade" id="ConfirmRecievedPopup" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="ConfirmRecievedTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
                            <div class="modal-content border border-danger">
                                <div class="modal-header border-0">
                                    <h5 class="modal-title" id="ConfirmRecievedTitle">Item Recieved?</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <h6 class="text-danger fw-bold">Reminder:<h6>
                                    <p>Please make sure that you recieved the items before proceeding.</p>
                                </div>
                                <div class="modal-footer border-0">
                                    <button type="button" class="m-1 btn fw-bold btn-outline-danger" data-bs-dismiss="modal">Cancel</button>
                                    <button name="recieved-btn" type="submit" class="m-1 btn fw-bold btn-outline-secondary">Confirm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Confirm Items Recieved-->

                </div>
                </form>
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
        if (input.value > <?php echo $cartItem['stock']?>) {
            input.value = <?php echo $cartItem['stock']?>;
        }
        if (input.value < 0) {
            input.value = '';
        }
    }
</script>
</html>
