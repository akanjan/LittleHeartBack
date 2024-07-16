package com.ecom.services.impl;

import com.ecom.entities.MasSerial;
import com.ecom.entities.master_entity.MasSerialPK;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.payloads.MasSerialDto;
import com.ecom.repositories.MasSerialRepo;
import com.ecom.services.MasSrlService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasSrlServiceImpl implements MasSrlService {
    @Autowired
    private MasSerialRepo masSerialRepo;

    @Autowired
    private ModelMapper modelMapper;

    public MasSerial dtoToMasSrl(MasSerialDto masSerialDto) {
        MasSerial masSerial = this.modelMapper.map(masSerialDto, MasSerial.class);
        return masSerial;
    }

    public MasSerialDto masSerialToDto(MasSerial masSerial) {
        MasSerialDto masSerialDto = this.modelMapper.map(masSerial, MasSerialDto.class);
        return masSerialDto;
    }

    @Override
    public MasSerialDto updateSrlNo(MasSerialDto masSerial, MasSerialPK masSerialPK) {

        MasSerial serial = this.masSerialRepo.findById(masSerialPK).orElseThrow(() ->
                new ResourceNotFoundException("Master Serial", "Id", masSerialPK));
        Integer serialNo = serial.getSerialNo();
        serialNo=serialNo+1;
        serial.setSerialNo(serialNo);
        MasSerial updateSerial = this.masSerialRepo.save(serial);
        MasSerialDto masSerialDto = this.masSerialToDto(updateSerial);
        return masSerialDto;
    }

    @Override
    public int getSrlNo(MasSerialPK masSerialPK) {
        MasSerial masSerial = this.masSerialRepo.findById(masSerialPK).orElseThrow(() ->
                new ResourceNotFoundException("Master Serial", "Id", masSerialPK));
        Integer serialNo = masSerial.getSerialNo();
        return serialNo;
    }


}
