/**
 * Copyright (c) 2022-2024 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import java.util.List;

import javafx.stage.Stage;

/**
 * @author Andy Black
 *
 */
public class LinuxKeyboardHandler extends KeyboardHandler {

	@Override
	public boolean changeToKeyboard(KeyboardInfo keyboard, Stage stage, Class<?extends Object> classOfMainApp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<KeyboardInfo> getAvailableKeyboards(Class<?extends Object> classOfMainApp) {
		// TODO Auto-generated method stub
		return super.getAvailableKeyboards(classOfMainApp);
	}

	@Override
	public String getExceptionContentMessage() {
		// TODO Auto-generated method stub
		return super.getExceptionContentMessage();
	}

	@Override
	public void setEmailSupportAddress(String emailAddress) {
		// TODO Auto-generated method stub
	}

}
