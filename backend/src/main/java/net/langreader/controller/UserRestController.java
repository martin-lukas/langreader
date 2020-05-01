package net.langreader.controller;

import net.langreader.payload.request.SimpleRequest;
import net.langreader.dao.UserRepository;
import net.langreader.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAllByOrderByUsername();
        List<User> strippedUsers = new ArrayList<>();
        for (User strippedUser : users) {
            strippedUser.setWords(null);
            strippedUser.setTexts(null);
            strippedUser.setLangs(null);
            strippedUser.setChosenLang(null);
            strippedUsers.add(strippedUser);
        }
        return new ResponseEntity<>(strippedUsers, HttpStatus.OK);
    }

    // TODO: refactor from simpleReq to User object in the body
    @DeleteMapping
    public ResponseEntity<?> removeUserByUsername(
            @Valid @RequestBody SimpleRequest simpleReq) {
        Optional<User> foundUser = userRepository.findByUsername(simpleReq.getParam());
        if (foundUser.isPresent()) {
            userRepository.delete(foundUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
