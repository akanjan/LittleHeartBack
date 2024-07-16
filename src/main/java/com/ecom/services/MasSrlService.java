package com.ecom.services;

import com.ecom.entities.master_entity.MasSerialPK;
import com.ecom.payloads.MasSerialDto;

public interface MasSrlService {
    MasSerialDto updateSrlNo (MasSerialDto masSerial, MasSerialPK masSerialPK);
    int getSrlNo(MasSerialPK masSerialPK);
}
