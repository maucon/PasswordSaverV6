package de.tandem.psv6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Entry implements Serializable {

    // BANNED CHARS: ´§²³€µäÄüÜöÖ
    private String name;
    private String login;
    private String password;
    private String description;

}
