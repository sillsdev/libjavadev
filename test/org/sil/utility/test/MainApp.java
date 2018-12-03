// Copyright (c) 2016-2018 SIL International 
// This software is licensed under the LGPL, version 2.1 or later 
// (http://www.gnu.org/licenses/lgpl-2.1.html) 
package org.sil.utility.test;

import java.io.File;

import org.sil.utility.ApplicationPreferencesUtilities;
import org.sil.utility.MainAppUtilities;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Andy Black
 *
 */
public class MainApp implements MainAppUtilities {

	public ApplicationPreferencesUtilities getApplicationPreferences() {
		return new ApplicationPreferences();
	}

	public Image getNewMainIconImage() {
		Image img = new Image("file:resources/images/SILLogo.png");
		return img;
	}

	public Stage getPrimaryStage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveFile(File file) {
		// TODO Auto-generated method stub

	}

	public void updateStageTitle(File file) {
		// TODO Auto-generated method stub

	}

}
