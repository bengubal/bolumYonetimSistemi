package com.project.bolumYonetim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // Tüm kullanıcıları listele
    }

    public void saveUser(User user) {
        userRepository.save(user); // Kullanıcıyı veritabanına kaydet
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Kullanıcıyı sil
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
