package com.ecom.entities;

import com.ecom.entities.master_entity.MasSerialPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mas_serial_no")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MasSerialPK.class)
public class MasSerial {
    @Id
    private Integer year;
    @Id
    @Column(name = "serial_type",length = 10)
    private String serialType;

    @Column(name = "serial_desc",nullable = false)
    private String serialDesc;

    @Column(name = "serial_no",nullable = false)
    private Integer SerialNo;

    @Column(name = "entry_by",nullable = false)
    private String EntryBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date EntryDate;

    @Column(nullable = false)
    private String status;
}
