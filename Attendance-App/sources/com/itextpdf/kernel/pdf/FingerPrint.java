package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.ProductInfo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FingerPrint implements Serializable {
    private static final long serialVersionUID = 1378019250639368423L;
    private Set<ProductInfo> productInfoSet = new HashSet();

    public boolean registerProduct(ProductInfo productInfo) {
        int initialSize = this.productInfoSet.size();
        this.productInfoSet.add(productInfo);
        return initialSize != this.productInfoSet.size();
    }

    public Collection<ProductInfo> getProducts() {
        return this.productInfoSet;
    }
}
