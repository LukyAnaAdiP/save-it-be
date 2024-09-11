package com.enigma.group5.save_it_backend.constant;

import java.util.List;

public class PaymentMethod {
    public static final String OTHER_QRIS = "other_qris";
    public static final String GOPAY = "gopay";
    public static final String SHOPEEPAY = "shopeepay";
    public static final String PAYPAL = "paypal";
    public static final String BCA_VA = "bca_va";
    public static final String BNI_VA = "bni_va";
    public static final String BRI_VA = "bri_va";

    public static final List<String> PAYMENT_METHODS = List.of(OTHER_QRIS, GOPAY, SHOPEEPAY, PAYPAL, BCA_VA, BNI_VA, BRI_VA);

}
