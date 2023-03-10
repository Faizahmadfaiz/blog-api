package com.scaler.blogapi.users;

import com.scaler.blogapi.profiles.dtos.ProfileResponseDTO;
import com.scaler.blogapi.security.authtokens.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import com.scaler.blogapi.users.dtos.CreateUserDTO;
import com.scaler.blogapi.users.dtos.LoginUserDTO;
import com.scaler.blogapi.users.dtos.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class UsersService  {
     private final UsersRepository usersRepository;
     private final ModelMapper modelMapper;
     private final PasswordEncoder passwordEncoder;
     private final JWTService jwtService;
     private  final AuthTokenService authTokenService;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTService jwtService, AuthTokenService authTokenService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }

    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        // TODO: Validate email
        // TODO: Check if username already exists
        var newUserEntity = modelMapper.map(createUserDTO, UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        var savedUser = usersRepository.save(newUserEntity);
        var userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        userResponseDTO.setToken(jwtService.createJWT(savedUser.getId()));
        return userResponseDTO;
    }

     public UserResponseDTO loginUser(LoginUserDTO loginUserDTO, AuthType authType) {
        var userEntity = usersRepository.findByUsername(loginUserDTO.getUsername());
         if (userEntity == null) {
             throw new UserNotFoundException(loginUserDTO.getUsername());
         }

         var passMatch = passwordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword());
         if (!passMatch) {
               throw new IllegalArgumentException("Incorrect Password");
         }

         var userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
         switch (authType) {
             case JWT:
                 userResponseDTO.setToken(jwtService.createJWT(userEntity.getId()));
                 break;
             case AUTH_TOKEN:
                 userResponseDTO.setToken(authTokenService.createAuthToken(userEntity).toString());
                 break;
         }

         return userResponseDTO;
     }

     public UserEntity getUserById(Integer userId) {
        var userEntity = usersRepository.getById(userId);
        return userEntity;
     }

     public ProfileResponseDTO getUserByName(String username) {
        var userEntity = usersRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UserNotFoundException(username);
        }
        var profile = modelMapper.map(userEntity, ProfileResponseDTO.class);
        return profile;
     }

     public List<ProfileResponseDTO> getUsers(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        var users = usersRepository.findAll(paging);
        return users.stream().map(userEntity -> modelMapper.map(userEntity, ProfileResponseDTO.class)).toList();
     }

     public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Integer id) {
            super("User with id " + id + " not found");
        }

         public UserNotFoundException(String username) {
             super("User with username " + username + " not found");
         }
     }

     public static class IncorrectPasswordException extends IllegalArgumentException {
        public IncorrectPasswordException() {
            super("Incorrect password");
        }
     }

    static enum AuthType {
        JWT,
        AUTH_TOKEN
    }
}
