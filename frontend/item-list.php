<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
include "0/<>/connection.php";
$show_query="";

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['search-btn'])) {
    $search_query = $_POST['search_query'];
    
        
        if (empty($search_query)){
            echo "<script type='text/javascript'> window.onload = function () { alert('Search query invalid.'); window.location.href = 'index.php'; }</script>";
        }
        else {
            $sql = "SELECT * FROM inventory WHERE productName LIKE '%$search_query%' AND stock > 0";
            $result = $conn->query($sql);
            
            if ($result->num_rows > 0) {
                $_SESSION['search_results'] = $result->fetch_all(MYSQLI_ASSOC);
            }
            else {
                $_SESSION['search_results'] = [];
            }
        }
        
        header('Location: index.php?search='.$search_query); // Redirect to index.html after search

        $getcategory = isset($_GET['search']) ? $_GET['search'] : '';
        $show_query=$getcategory;
    }

    else if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        if (isset($_POST['clear-btn'])) {
            // Clear the session variable
            unset($_SESSION['search_results']);
            header('Location: index.php'); // Redirect to index.html
            exit();
        }
    }

    else {
        if (isset($_GET['search'])) {
            $getsearch = isset($_GET['search']) ? $_GET['search'] : '';
            $show_query = $getsearch;
        }
        else if (isset($_GET['people'])) {
            $getpeople = isset($_GET['people']) ? $_GET['people'] : '';
            $sql = "SELECT * FROM inventory WHERE people='$getpeople' AND stock > 0";
            $result = $conn->query($sql);
            $show_query = $getpeople;

            if ($result->num_rows > 0) {
                
                $_SESSION['search_results'] = $result->fetch_all(MYSQLI_ASSOC);
            } else {
                $_SESSION['search_results'] = [];
            }
        }
        else if (isset($_GET['uses'])) {
            $getuse = isset($_GET['uses']) ? $_GET['uses'] : '';
            $sql = "SELECT * FROM inventory WHERE used_for='$getuse' AND stock > 0";
            $result = $conn->query($sql);
            $show_query = $getuse;

            if ($result->num_rows > 0) {
                $_SESSION['search_results'] = $result->fetch_all(MYSQLI_ASSOC);
            } else {
                $_SESSION['search_results'] = [];
            }
        }
        else if (isset($_GET['brand'])) {
            $getbrand = isset($_GET['brand']) ? $_GET['brand'] : '';
            $sql = "SELECT * FROM inventory WHERE brand='$getbrand' AND stock > 0";
            $result = $conn->query($sql);
            $show_query = $getbrand;

            if ($result->num_rows > 0) {
                $_SESSION['search_results'] = $result->fetch_all(MYSQLI_ASSOC);
            } else {
                $_SESSION['search_results'] = [];
            }
        }
    }

    ?>

<div class="col-lg-9 col-sm-12  order-lg-0 order-sm-1 order-1 row p-2 m-0">
<?php if(isset($_SESSION['search_results']) && !empty($_SESSION['search_results'])) {?>
    <div class="d-flex justify-content-start container-fluid"> 
        <div>   
            <p style="font-size:.5em"; class="py-2">Showing <?php echo count($_SESSION['search_results'])?> results for "<?php echo $show_query?>"</p>
        </div>
        <div class="ps-5">
            <form class="p-0 m-0" action="item-list.php" method="post"><button style="font-size:.5em" name="clear-btn" class="no-decor m-0 p-0 text-danger" type="submit"><img class="img-fluid no-decor" src="assets/buttons/clear_search.png" style="height:10px;"> Clear search results.</button></form>
        </div>
    </div>
<?php foreach($_SESSION['search_results'] as $data) { include "item.php";}}
    else if(isset($_SESSION['search_results']) && empty($_SESSION['search_results'])) {?>
    <div class="d-flex justify-content-start container-fluid">
        <div>   
            <p style="font-size:.5em;" class="py-2">No results for "<?php echo $show_query?>"</p>
        </div>
        <div class="ps-5">
            <form class="p-0 m-0" action="item-list.php" method="post"><button style="font-size:.5em" name="clear-btn" class="no-decor m-0 p-0 text-danger" type="submit"><img class="img-fluid no-decor" src="assets/buttons/clear_search.png" style="height:10px;"> Clear search results.</button></form>
        </div>
    </div>
<?php
}




else {
    // Retrieve data from the database
    $sql = "SELECT * FROM inventory WHERE stock > 0";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        while($data = $result->fetch_assoc()) {
            include "item.php";
        }
    } 
    else {
        echo "No items matched with the query.";
    }

    $conn->close();
}
?>

</div>

<div class="col-lg-3 col-sm-12 order-lg-1 order-sm-0 order-0 p-2 m-0">
<form class="m-0 p-0" action="" method="post">
<p class="fw-bold px-2 py-0 m-0">Categories</p>
<nav class="navbar navbar-expand navbar-light bg-transparent m-0 p-0 container-fluid d-lg-none d-md-block d-sm-block d-block">
        <div class="container-fluid m-0 p-1">
            <div class="collapse navbar-collapse container-fluid p-2 px-0 mx-0" id="categoriesCollapse">
                <ul class="navbar-nav mx-auto container-fluid p-0 m-0">
                    <li class="nav-item dropdown px-1">
                    <button class="btn container-fluid text-end text-light" style="background-color:#222222; font-size:0.75em" type="button" data-bs-toggle="dropdown" data-bs-target="#physical_based" aria-expanded="true" aria-controls="physical_based" >People</button>
                        <div class="dropdown-menu" aria-labelledby="physical_based">
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=men';" type="button" style="font-size:0.75em">Men</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=women';" type="button" style="font-size:0.75em">Women</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=kids';" type="button" style="font-size:0.75em">Kids</button>
                        </div>
                    </li>
                    <li class="nav-item dropdown px-1">
                    <button class="btn container-fluid text-end text-light" style="background-color:#222222; font-size:0.75em" type="button" data-bs-toggle="dropdown" data-bs-target="#hobbies_based" aria-expanded="true" aria-controls="hobbies_based">Activities</button>
                        <div class="dropdown-menu" aria-labelledby="hobbies_based">
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=casual';" type="button" style="font-size:0.75em">Casual</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=school';" type="button" style="font-size:0.75em">School</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=sports';" type="button" style="font-size:0.75em">Sports</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=travel';" type="button" style="font-size:0.75em">Travel</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=work';" type="button" style="font-size:0.75em">Work</button>
                        </div>
                    </li>
                    <li class="nav-item dropdown px-1">
                    <button class="btn container-fluid text-end text-light" style="background-color:#222222; font-size:0.75em" type="button" data-bs-toggle="dropdown" data-bs-target="#brand_based" aria-expanded="true" aria-controls="brand_based">Brands</button>
                        <div class="dropdown-menu" aria-labelledby="brand_based">
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=adidas';" type="button" style="font-size:0.75em">Adidas</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=converse';" type="button" style="font-size:0.75em">Converse</button>
                            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=nike';" type="button" style="font-size:0.75em">Nike</button>
                        </div>
                    </li>
                </ul>
            </div>
      </div>
    </nav>

    <div class="d-lg-block d-md-none d-sm-none d-none">
    <div class="accordion" id="category">

        <div class="">
        <h2 class="accordion-header " id="physical-btn">
          <button class="btn container-fluid text-end text-light" style="background-color:#222222;" type="button" data-bs-toggle="collapse" data-bs-target="#physical_based" aria-expanded="true" aria-controls="physical_based">
            People
          </button>
        </h2>
        <div id="physical_based" class="accordion-collapse collapse" aria-labelledby="physical-btn" data-bs-parent="#category">
          <div class="accordion-body p-0 py-1 ">
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=men';" type="button">Men</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=women';" type="button">Women</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?people=kids';" type="button">Kids</button>
        </div></div></div>

        <h2 class="accordion-header" id="hobbies-btn">
          <button class="btn container-fluid text-end text-light" style="background-color:#222222;" type="button" data-bs-toggle="collapse" data-bs-target="#hobbies_based" aria-expanded="true" aria-controls="hobbies_based">
          Activities
          </button>
        </h2>
        <div id="hobbies_based" class="accordion-collapse collapse" aria-labelledby="hobbies-btn" data-bs-parent="#category">
          <div class="accordion-body p-0 py-1">
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=casual';" type="button">Casual</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=school';" type="button">School</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=sports';" type="button">Sports</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=travel';" type="button">Travel</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?uses=work';" type="button">Work</button>
        </div></div></div>

        <h2 class="accordion-header" id="brand-btn">
          <button class="btn container-fluid text-end text-light" style="background-color:#222222;" type="button" data-bs-toggle="collapse" data-bs-target="#brand_based" aria-expanded="true" aria-controls="brand_based">
          Brands
          </button>
        </h2>
        <div id="brand_based" class="accordion-collapse collapse" aria-labelledby="brand-btn" data-bs-parent="#category">
          <div class="accordion-body p-0 py-1">
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=adidas';" type="button">Adidas</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=converse';" type="button">Converse</button>
            <button class="btn container-fluid btn-outline-dark border border-light" onclick="window.location.href = 'index.php?brand=nike';" type="button">Nike</button>
        </div></div></div>
    
    </div>
    </div>

</form>


</div>