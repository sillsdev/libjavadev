/**
 * Copyright (c) 2024 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sil.utility.view.JavaFXThreadingRule;

/**
 * @author Andy Black
 *
 */
public class MacOSXKeyboardHandlerTest {
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	MacOSXKeyboardHandler macHandler = null;

	@Before
	public void setUp() throws Exception {
		macHandler = new MacOSXKeyboardHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	protected void checkKeyboardInfo(KeyboardInfo info, int langId, String localeLanguage, String description, Boolean localeExists, int macIndex) {
		assertNotNull(info);
		if (info.getLocale() != null) {
			assertEquals(localeLanguage, info.getLocale().getLanguage());
		} else {
			assertEquals(false, localeExists);
		}
		assertEquals(langId, info.getWindowsLangID());
		assertEquals(description, info.getMacDescription());
		assertEquals(macIndex, info.getMacKeyboardIndex());
	}
	
	@Test
	public void getKeyboardInfoFromKeyboardNameAndIndexTest() {
		//com.apple.keylayout.US
		//com.apple.keylayout.Spanish
		//com.apple.CharacterPaletteIM
		//com.apple.KeyboardViewer
		//org.unknown.keylayout.IPAUnicode51v14MAC
		//keyman.inputmethod.Keyman
		String imName = "com.apple.keylayout.US";
		KeyboardInfo info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 0);
		checkKeyboardInfo(info, 0, "en", "US", true, 0);
		imName = "com.apple.keylayout.Spanish";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 1);
		checkKeyboardInfo(info, 0, "en", "Spanish", true, 1);
		imName = "com.apple.CharacterPaletteIM";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 2);
		assertNull(info);
		imName = "com.apple.KeyboardViewer";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 3);
		assertNull(info);
		imName = "org.unknown.keylayout.IPAUnicode51v14MAC";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 4);
		checkKeyboardInfo(info, 0, "en", "IPAUnicode51v14MAC", true, 4);
		imName = "keyman.inputmethod.Keyman";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, 5);
		assertNull(info);
//		checkKeyboardInfo(info, 0, "en", "Keyman keyboard", true, 5);
		imName = null;
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, -1);
		assertNull(info);
		imName = "";
		info = macHandler.getKeyboardInfoFromKeyboardNameAndIndex(imName, -1);
		checkKeyboardInfo(info, 0, "en", "", false, -1);
	}

}
