package de.tandem.psv6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.identityconnectors.common.security.GuardedString;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Entry implements Serializable {

    private GuardedString name;
    private GuardedString login;
    private GuardedString password;

}
