module rs.ac.metropolitan.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.fasterxml.jackson.databind;


    opens rs.ac.metropolitan.project to javafx.fxml;
    opens rs.ac.metropolitan.project.model to org.hibernate.orm.core, javafx.base;
    exports rs.ac.metropolitan.project;
}