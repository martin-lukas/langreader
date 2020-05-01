package net.langreader.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;

//    private Set<String> roles;

    @NotBlank
    private String password;
}