package com.ecom.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private int prodId;
    private String prodTitle;
    private String prodDesc;
    private String prodPhoto;
    private String prodPrice;
    private int prodDiscount;
    private int prodQuantity;
    private boolean prodActive;
    private Date entryDate;

    private CategoryDto category;
    private UserDto user;
}
