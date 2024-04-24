<div id="" class="col-lg-3 col-md-4 col-sm-6 col-6 p-2">
    <a class="no-decor text-body" href="view-item.php?id=<?php echo $data['productID'] ?>">
        <div class="position-relative">
            <div class="border bg-body overflow-hidden" style="border-radius: 5pt; position: relative;">
                <div class="card-img-top shadow-sm position-relative" style="height: 140pt; overflow: hidden;">
                    <img class="card-img-top shadow-sm img-fluid" style="object-fit: cover; height: 100%;" alt="<?php echo $data['productName']?>" src="data:image/jpeg;base64,<?php echo base64_encode($data["displayImage"]);?>" onerror="this.src='assets/product/no-image.jpeg';">
                </div>
                <div class="">
                    <div class="p-2 overflow-hidden shadow shadow-sm" style="height: 2em; line-height: 1.5rem;">
                        <p class="m-0 h6 fw-bolder text-dark" style="font-size: .5em;"><?php echo $data['productName'] ?></p>
                    </div>  
                    <div class="p-lg-2 p-0 pt-2 pb-3">
                        <div class="row m-0 p-0">
                            <div class="col-12 m-0 p-0 row" style="font-size: 1em;">
                                <?php
                                $final_price;
                                if (empty($data['discount'])) {
                                    $final_price = $data['price'];
                                    ?>
                                    <p class="col-lg-4 col-12 text-danger fw-bolder"><?php echo '₱'.$final_price ?></p>
                                    <p class="col-lg-3 col-12"></p>
                                <?php
                                } else {
                                    $discountedPrice = $data['price'] - (($data['discount'] * 0.01) * $data['price']);
                                    $final_price = $discountedPrice; ?>
                                    <s class="text-secondary col-lg-3 col-12 py-lg-1 py-0" style="font-size: .65em;">₱<?php echo $data['price']; ?></s>
                                    <p class="col-lg-4 col-12 text-danger fw-bolder"><?php echo ' '.'₱'.$final_price; ?></p>
                                <?php
                                }
                                ?>
                            </div>
                        </div>
                        <div class="row pt-2 m-0">
                            <p class="col-12 item-txt m-0" style="font-size: .5em;"><?php echo $data['sold'] ?> sold</p>
                            <p class="col-12 item-txt m-0" style="font-size: .5em;"><?php echo $data['stock']?> left</p>
                        </div>
                        <?php if (!empty($data['discount'])) : ?>
                        <div class="sale-badge position-absolute top-0 start-0 bg-transparent text-center" style="width:5em; height:5em;">
                                <img class="img img-fluid" src="assets/logo/sale-logo.png" alt="Sale" style="max-width: 100%; height: auto;">
                                <p class="text-light fw-bolder m-0" style="font-size:.5em; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);"><?php echo $data['discount']."% OFF" ?></p>
                            </div>
                        <?php endif; ?>
                    </div>
                </div>
            </div>
        </div>
    </a>
</div>
