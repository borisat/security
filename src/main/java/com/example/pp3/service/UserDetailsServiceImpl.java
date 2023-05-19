package com.example.pp3.service;

import com.example.pp3.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;



    @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            return userDAO.getUserByUsername(s);
        }


}