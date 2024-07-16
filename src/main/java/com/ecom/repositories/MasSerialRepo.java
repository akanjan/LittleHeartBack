package com.ecom.repositories;

import com.ecom.entities.MasSerial;
import com.ecom.entities.master_entity.MasSerialPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MasSerialRepo extends JpaRepository<MasSerial, MasSerialPK> {
    @Query(value = "SELECT serial_no FROM mas_serial_no e where e.serial_type =:serialType AND e.year =:year",
            nativeQuery = true)
    public int getSerialNo(@Param("serialType") String serial_type, @Param("year") Integer year);
}
