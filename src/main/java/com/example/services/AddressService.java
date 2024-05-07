package com.example.services;

import com.example.entity.Address;
import com.example.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {return addressRepository.findAll();}

    public Address findOne(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No address with such id: " + id));
    }

    @Transactional
    public Address save(Address user) {
        return addressRepository.save(user);
    }

    @Transactional
    public Address update(int id, Address address) {
        addressRepository.findById(id).ifPresentOrElse(a -> {

            a.setCountry(address.getCountry());
            a.setCity(address.getCity());
            a.setStreet(address.getStreet());
            a.setHouse(address.getHouse());
            a.setPostIndex(address.getPostIndex());
        }, () -> {
            throw new NoSuchElementException("No address with id: " + id);
        });
        return address;
    }

    @Transactional
    public void delete(int id) {
        addressRepository.deleteById(id);
    }
}
