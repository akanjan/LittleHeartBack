package com.ecom.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private int catId;
    private String catTitle;
    private String catDesc;
    private int userId;
}
