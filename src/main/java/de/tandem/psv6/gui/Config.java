package de.tandem.psv6.gui;

import de.tandem.psv6.entity.User;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Config {
    private final HashMap<String, String> settings;
    @Setter
    private String configPath;

    public Config(User user) {
        configPath = user.getUsername() + "\\config.cfg";
        settings = new HashMap<String, String>();
        try {
            parseSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseSettings() throws IOException {
        var file = new File(configPath);
        if (file.exists()) {
            //read
        } else initConfigFile();
    }

    private void initConfigFile() {

    }
}
