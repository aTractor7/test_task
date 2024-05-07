package com.example.util.dto_entity_converters;

import com.example.dto.AddressDTO;
import com.example.dto.UserDTO;
import com.example.entity.Address;
import com.example.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    private final ModelMapper modelMapper;
    private final AddressDTOConverter addressDTOConverter;

    @Autowired
    public UserDTOConverter(ModelMapper modelMapper, AddressDTOConverter addressDTOConverter) {
        this.modelMapper = modelMapper;
        this.addressDTOConverter = addressDTOConverter;
    }

    public User convertToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        if(userDTO.getAddressDTO() != null) {
            Address address = addressDTOConverter.convertToAddress(userDTO.getAddressDTO());
            user.setAddress(address);
            address.setUser(user);
        }
        return user;
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        if(user.getAddress() != null) {
            AddressDTO addressDTO = addressDTOConverter.convertToAddressDTO(user.getAddress());
            userDTO.setAddressDTO(addressDTO);
        }
        return userDTO;
    }
}
