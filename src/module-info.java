module org.sil.utility {
	// Exports
	exports org.sil.utility;
	exports org.sil.utility.service;
	exports org.sil.utility.service.keyboards;
	exports org.sil.utility.view;
	exports org.sil.utility.xml;
	exports org.sil.utility.test;

	opens org.sil.utility.view to javafx.fxml;
	opens org.sil.utility.test to javafx.fxml;

	// Java
	requires java.desktop;
	requires java.prefs;

	// JavaFX
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires javafx.graphics;
	requires javafx.swing;
	requires javafx.web;

	// JAXB
	requires jakarta.xml.bind;
	requires jakarta.activation;

	// JNA
	requires transitive com.sun.jna;
	requires com.sun.jna.platform;
	
	//JSON
	requires json.simple;
//	requires org.json.simple;
	
	// JUnit
	requires junit;

	// Other modules/libraries
	requires transitive org.controlsfx.controls;
	requires javafx.base;
	requires javafx.media;
	requires java.base;
}
