module PasswordSaver {
    requires static lombok;
    requires javafx.controls;
    requires connector.framework;
    requires org.apache.commons.codec;
    requires java.desktop;

    exports de.tandem.psv6;
}
