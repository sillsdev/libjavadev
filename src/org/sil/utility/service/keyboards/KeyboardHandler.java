/**
 * Copyright (c) 2022 SIL International
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
public class KeyboardHandler {

	protected final String sTitle = "Error Found!";
	protected final String sHeader = "A serious error happened.";
	protected final String sContent1 = "Please copy the exception information below, email it to ";
	protected final String sContent2 = " along with a description of what you were doing.";
	protected final String sLabel = "The exception stacktrace was:";
	String sEmailSupportAddress = "Language_Software_Support@sil.org";
	protected String sContent = sContent1 + sEmailSupportAddress + sContent2;

	public boolean changeToKeyboard(KeyboardInfo keyboard, Stage stage) {
		return false;
	}

	public List<KeyboardInfo> getAvailableKeyboards() {
		return new ArrayList<KeyboardInfo>();
	}
	
	public String getExceptionContentMessage() {
		return sContent;
	}
	
	public void setEmailSupportAddress(String emailAddress) {
		sContent = emailAddress;
	}

}
