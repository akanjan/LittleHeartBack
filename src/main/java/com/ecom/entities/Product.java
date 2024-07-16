package com.ecom.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "Prod_Id", nullable = false)
    private int prodId;

    @Column(name = "Prod_Title", nullable = false)
    private String prodTitle;

    @Column(name = "Prod_Desc", nullable = false)
    private String prodDesc;

    @Column(name = "Prod_Photo")
    private String prodPhoto;

    @Column(name = "Prod_Price", nullable = false)
    private String prodPrice;

    @Column(name = "Prod_Discount")
    private int prodDiscount;

    @Column(name = "Prod_Quantity")
    private int prodQuantity;

    @Column(name = "Prod_Active", nullable = false)
    private boolean prodActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Entry_Date",nullable = false)
    private Date entryDate;

    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @ManyToOne
    private User user;
}
