<div class="m-0 p-2 ">
    <div class="row border border-dark rounded overflow-hidden">
        <div class="bg-dark container fluid p-1 px-2 m-0 d-flex justify-content-end">
            <label class="text-light px-2" name="">Select Item</label>
            <input class="px-2 m-0 no-decor" type="checkbox" name="checkoutItems[]" value="<?php echo $orderItem['productID']; ?>">
        </div>
            <div class="p-2 col-lg-2 col-md-3 col-sm-6 col-6 d-flex justify-content-center">
                <img class="img-fluid border border-dark border-1 rounded" style="height:20vh; width: 20vh; object-fit:cover;" src="data:image/*;base64,<?php echo base64_encode($orderItem['displayImage']); ?>" onerror="this.src='assets/product/no-image.jpeg';"  alt="profile_picture <?php echo $orderItem['productName']; ?>">
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-6 py-4">
                <div class="p-2 overflow-hidden" style="height:2.35em; line-height:1.5rem;">
                    <p class="m-0 h6 fw-bolder text-dark text-center" style="font-size:.75em;"><?php echo $orderItem['productName'] ?></p>
                </div>  
                <div class="p-2 pb-3">
                <p class="col-12 m-0 text-danger fw-bolder text-center" style="font-size:1em;"><?php

                                    $discountedPrice=$orderItem['price']-(($orderItem['discount']*0.01)*$orderItem['price']);

                                    if (empty($orderItem['discount'])) {
                                        echo '₱'.$orderItem['price'];
                                    }
                                    else {
                                        ?>
                                        <s class="text-secondary" style="font-size:.65em;">₱<?php echo $orderItem['price']; ?></s>
                                        <?php echo ' '.'₱'.$discountedPrice; ?>
                                    <?php }
                                    
                                    ?></p>
                               
                </div>
            </div>
            <div class="col-lg-2 col-md-3 col-sm-6 col-6 py-5 container-fluid">
                <div class="row">
                    <div class="col-12 px- my-auto d-flex justify-content-center" style="font-size:.65em;">
                        Number of Items:
                    </div>
                    <div class="col-12 d-flex px-2 m-0 row d-flex justify-content-center">
                        <?php echo $orderItem['noOfItems'] ?>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-6 py-5">
                <div class="row">
                    <div class="col-12 my-auto d-flex justify-content-center" style="font-size:.65em;">
                        Price of <?php echo $orderItem['noOfItems'] ?> Items:
                    </div>
                    <div class="p-2 pb-3">
                <p class="col-12 m-0 text-danger fw-bolder d-flex justify-content-center" style="font-size:1em;"><?php
                                    $partialprice=0;
                                    $discountedPrice=$orderItem['price']-(($orderItem['discount']*0.01)*$orderItem['price']);

                                    if (empty($orderItem['discount'])) {
                                        $partialprice = $orderItem['price'] * $orderItem['noOfItems'];
                                    }
                                    else if (!empty($orderItem['discount'])) {
                                        $partialprice = $discountedPrice * $orderItem['noOfItems'];
                                    }
                                    
                                    echo '₱'.$partialprice;

                                    ?>
                    </p>     
                </div>
                </div>
                
            </div>
    </div>
</div>