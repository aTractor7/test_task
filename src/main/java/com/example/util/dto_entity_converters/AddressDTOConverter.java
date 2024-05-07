package com.example.util.dto_entity_converters;

import com.example.dto.AddressDTO;
import com.example.dto.UserDTO;
import com.example.entity.Address;
import com.example.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public AddressDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Address convertToAddress(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public AddressDTO convertToAddressDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}
