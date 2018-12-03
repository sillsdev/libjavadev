// Copyright (c) 2016-2018 SIL International 
// This software is licensed under the LGPL, version 2.1 or later 
// (http://www.gnu.org/licenses/lgpl-2.1.html) 
package org.sil.utility;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.controlsfx.control.StatusBar;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sil.utility.test.MainApp;
import org.sil.utility.view.ControllerUtilities;

public class ControllerUtilitiesTest {
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	ResourceBundle resources;
	MainApp mainApp;
	Locale locale;
	final String sPropertiesPath = "org.sil.utility.resources.Utility";
	final String kTitle = "title";

	@Before
	public void setUp() throws Exception {
		locale = new Locale("en");
		resources = ResourceBundle.getBundle(sPropertiesPath, locale);
		mainApp = new MainApp();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createToolbarButtonWithImageTest() {
		Tooltip tooltipToolbarFileNew = new Tooltip("");
		Button buttonToolbarFileNew = new Button(kTitle);
		tooltipToolbarFileNew = ControllerUtilities.createToolbarButtonWithImage("newAction.png",
				buttonToolbarFileNew, tooltipToolbarFileNew, resources.getString("tooltip.new"),
				"test/org/sil/utility/");
		assertNotNull(tooltipToolbarFileNew);
		assertEquals("New Tree   Ctrl+N", tooltipToolbarFileNew.getText());
		assertNotNull(buttonToolbarFileNew.getGraphic());
		assertNotNull(buttonToolbarFileNew.getTooltip());
	}

	@Test
	public void getLoaderTest() throws IOException {
		URL location = ControllerUtilitiesTest.class.getResource("fxml/QuickReferenceGuide.fxml");
		Stage dialogStage = new Stage();
		assertNull(dialogStage.getTitle());
		FXMLLoader loader = ControllerUtilities.getLoader(mainApp, locale, dialogStage, kTitle,
				location, sPropertiesPath);
		assertNotNull(loader);
		assertEquals(kTitle, dialogStage.getTitle());
		Scene scene = dialogStage.getScene();
		assertNotNull(scene);
	}

	@Test
	public void getTextInputDialogTest() {
		final String kContent = "contentText";
		TextInputDialog tid = ControllerUtilities.getTextInputDialog(mainApp, kTitle, kContent,
				resources);
		assertNotNull(tid);
		String s = tid.getTitle();
		assertEquals(kTitle, s);
		s = tid.getContentText();
		assertEquals(kContent, s);
		s = tid.getHeaderText();
		assertEquals("", s);
		Node graphic = tid.getGraphic();
		assertNull(graphic);
		Button buttonOK = (Button) tid.getDialogPane().lookupButton(ButtonType.OK);
		s = buttonOK.getText();
		assertEquals("OkieDokie", s);
		Button buttonCancel = (Button) tid.getDialogPane().lookupButton(ButtonType.CANCEL);
		s = buttonCancel.getText();
		assertEquals("Forget it!", s);
		Stage stage = (Stage) tid.getDialogPane().getScene().getWindow();
		Image icon = stage.getIcons().get(0);
		assertNotNull(icon);
	}

	@Test
	public void initFileChooserTest() {
		final String kInitialFileName = "name";
		final String sFileChooserFilterDescription = "XYZ files (*.xyz)";
		final String sFileExtensions = "*.xyz";
		FileChooser chooser = ControllerUtilities.initFileChooser(mainApp, kInitialFileName,
				sFileChooserFilterDescription, sFileExtensions);
		assertEquals(kInitialFileName, chooser.getInitialFileName());
		assertEquals(sFileChooserFilterDescription, chooser.getExtensionFilters().get(0).getDescription());
	}

	@Test
	public void setDateInStatusBarTest() {
		StatusBar bar = new StatusBar();
		assertEquals("OK", bar.getText());
		ControllerUtilities.setDateInStatusBar(bar, resources);
		assertNotEquals("OK", bar.getText());
	}
}
