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
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append("/resources/Keyboards/MacOSX/xkbswitch -se ");
		sb.append(keyboard.getMacDescription());

		final String command = sb.toString();
		return invokeTerminalCommand(command);
	}

	@Override
	public List<KeyboardInfo> getAvailableKeyboards() {
		List<KeyboardInfo> results = new ArrayList<KeyboardInfo>();
		String[] sLangIDs = new String[100];
		int iCount = getCurrentMacOSXKeyboardIDs(sLangIDs);
		for (int i = 0; i < iCount; i++) {
			KeyboardInfo info = getKeyboardInfoFromKeyboardName(sLangIDs[i]);
			if (info != null) {
				results.add(info);
			}
		}
		return results;
	}

	public KeyboardInfo getKeyboardInfoFromKeyboardName(String imName) {
		if (imName == null)
			return null;
		int idEndIndex = imName.indexOf("|||");
		if (idEndIndex == -1) {
			return null;
		}
		String macDescription = imName.substring(0, idEndIndex);
		if (macDescription.equals("com.apple.CharacterPaletteIM") || macDescription.equals("com.apple.KeyboardViewer")) {
			return null;
		}
		String description = "";
		if (macDescription.equals("keyman.inputmethod.Keyman")) {
			// TODO: handle Keyman situation, whatever that is...
			description = "Keyman keyboard";
			// Keyman on Mac OSX does not work with Java (yet)
			return null;
		} else {
			description = imName.substring(idEndIndex+3);
		}

		KeyboardInfo info = new KeyboardInfo(macDescription, description);
		System.out.println(macDescription + " = '" + description + "'");
		return info;
	}

	@Override
	public String getExceptionContentMessage() {
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
