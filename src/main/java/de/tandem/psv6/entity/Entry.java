package de.tandem.psv6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Entry implements Serializable {

    private String name;
    private String login;
    private String password;

}
