package com.example.services;

import com.example.entity.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    @Autowired
    public UserService(UserRepository userRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    public List<User> findAll() {return userRepository.findAll();}

    public List<User> findAllByBirthDateBetween(LocalDate start, LocalDate end) {
        return userRepository.findByBirthDateBetween(start, end);
    }

    public User findOne(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user with such id: " + id));
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(int id, User user) {
        userRepository.findById(id).ifPresentOrElse(u -> {

            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setBirthDate(user.getBirthDate());
            u.setPhoneNumber(user.getPhoneNumber());

            addressService.update(u.getAddress().getId(), user.getAddress());

        }, () -> {
            throw new NoSuchElementException("No user with id: " + id);
        });
        return user;
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
