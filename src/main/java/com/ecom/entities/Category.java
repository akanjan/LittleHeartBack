package com.ecom.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    private int catId;
    @Column(name = "Cat_Title",nullable = false)
    private String catTitle;
    @Column(name = "Cat_Desc",nullable = false)
    private String catDesc;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
