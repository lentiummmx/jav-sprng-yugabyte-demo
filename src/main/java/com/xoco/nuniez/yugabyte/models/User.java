package com.xoco.nuniez.yugabyte.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AuditDate {
    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    //@NotBlank
    private String email;

    //@NotBlank
    private String address;

    //@NotBlank
    private String city;

    //@NotBlank
    private String state;

    //@NotBlank
    private String zip;

    //@NotBlank
    private String birth_date;

    private Double latitude;

    private Double longitude;

    //@NotBlank
    private String password;

    //@NotBlank
    private String source;

    //@Column(columnDefinition = "TIMESTAMP")
    //private LocalDateTime created_at;
}
