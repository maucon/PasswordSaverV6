module PasswordSaver {
    requires static lombok;
//    requires javafx.base;
    requires javafx.controls;
    requires connector.framework;
    requires org.apache.commons.codec;

    exports de.tandem.psv6;
}
