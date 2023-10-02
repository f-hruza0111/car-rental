package com.hruza.carrental.entity.token;

import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.view.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Token {

    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )

    @JsonView(View.AuthenticationResponse.class)
    private Long id;


    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean expired;
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}
