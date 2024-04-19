/**
 * Copyright (c) 2022-2024 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

/**
 * @author Andy Black
 *
 */
public class MacOSXKeyboardHandler extends KeyboardHandler {

	@Override
	public boolean changeToKeyboard(KeyboardInfo keyboard, Stage stage) {
		if (keyboard.getMacKeyboardIndex() >= getAvailableKeyboards().size()) {
			return false;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append("/resources/Keyboards/MacOSX/xkbswitch -s ");
		sb.append(Integer.toString(keyboard.getMacKeyboardIndex()));

		final String command = sb.toString();
		return invokeTerminalCommand(command);
	}

	@Override
	public List<KeyboardInfo> getAvailableKeyboards() {
		List<KeyboardInfo> results = new ArrayList<KeyboardInfo>();
		String[] sLangIDs = new String[100];
		int iCount = getCurrentMacOSXKeyboardIDs(sLangIDs);
		for (int i = 0; i < iCount; i++) {
			KeyboardInfo info = getKeyboardInfoFromKeyboardNameAndIndex(sLangIDs[i], i);
			if (info != null) {
				results.add(info);
			}
		}
		return results;
	}

	public KeyboardInfo getKeyboardInfoFromKeyboardNameAndIndex(String imName, int index) {
		if (imName == null)
			return null;
		int idEndIndex = imName.indexOf("|||");
		if (idEndIndex == -1) {
			return null;
		}
		String sId = imName.substring(0, idEndIndex);
		if (sId.equals("com.apple.CharacterPaletteIM") || sId.equals("com.apple.KeyboardViewer")) {
			return null;
		}
		String description = "";
		if (sId.equals("keyman.inputmethod.Keyman")) {
			// TODO: handle Keyman situation, whatever that is...
			description = "Keyman keyboard";
			// Keyman on Mac OSX does not work with Java (yet)
			return null;
		} else {
			description = imName.substring(idEndIndex+3);
		}

		KeyboardInfo info = new KeyboardInfo(description, index);
		System.out.println(index + " = '" + description + "'");
		return info;
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

	protected int getCurrentMacOSXKeyboardIDs(String[] sLangIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append("/resources/Keyboards/MacOSX/xkbswitch -l");

		final String command = sb.toString();
		return getCurrentEnabledKeyboardIDs(command, sLangIDs);
	}


}
