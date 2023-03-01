package com.scaler.blogapi.profiles;

import com.scaler.blogapi.profiles.dtos.ProfileResponseDTO;
import com.scaler.blogapi.users.UsersService;
import com.scaler.blogapi.users.dtos.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfilesController {
    public final UsersService usersService;

    public ProfilesController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileResponseDTO>> getProfiles(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        var profiles = usersService.getUsers(pageNumber, pageSize);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable String username) {
        var profile = usersService.getUserByName(username);
        return ResponseEntity.ok(profile);
    }
}
