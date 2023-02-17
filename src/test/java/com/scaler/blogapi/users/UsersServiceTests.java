package com.scaler.blogapi.users;

import com.scaler.blogapi.users.dtos.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class UsersServiceTests {
    @Autowired private UsersRepository usersRepository;
    private UsersService usersService;

    private UsersService getUsersService() {
        if (usersService == null) {
            var modelMapper = new ModelMapper();
            usersService =  new UsersService(usersRepository, modelMapper) ;
        }
        return usersService;
    }
    @Test
    public void testCreateUser() {
        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("faiz.ahmad@email.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("faiz123");
        var savedUser = getUsersService().createUser(newUserDTO);
        assertNotNull(savedUser);
    }
}
