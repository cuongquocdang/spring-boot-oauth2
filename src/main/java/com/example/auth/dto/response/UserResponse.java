package com.example.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("first_name")
    private String lastName;

    private String photos;

    private boolean enabled;
}
