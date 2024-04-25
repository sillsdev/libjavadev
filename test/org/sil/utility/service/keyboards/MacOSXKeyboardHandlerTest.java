/**
 * Copyright (c) 2024 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import static org.junit.Assert.*;

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

	protected void checkKeyboardInfo(KeyboardInfo info, int langId, String localeLanguage, String description, Boolean localeExists, String macDescription) {
		assertNotNull(info);
		if (info.getLocale() != null) {
			assertEquals(localeLanguage, info.getLocale().getLanguage());
		} else {
			assertEquals(false, localeExists);
		}
		assertEquals(langId, info.getWindowsLangID());
		assertEquals(description, info.getDescription());
		assertEquals(macDescription, info.getMacDescription());
	}
	
	@Test
	public void getKeyboardInfoFromKeyboardNameAndIndexTest() {
		//com.apple.keylayout.US
		//com.apple.keylayout.Spanish
		//com.apple.CharacterPaletteIM
		//com.apple.KeyboardViewer
		//org.unknown.keylayout.IPAUnicode51v14MAC
		//keyman.inputmethod.Keyman
		String imName = "com.apple.keylayout.US|||US";
		KeyboardInfo info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		checkKeyboardInfo(info, -1, "en", "US", true, "com.apple.keylayout.US");
		imName = "com.apple.keylayout.Spanish|||Spanish";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		checkKeyboardInfo(info, -1, "en", "Spanish", true, "com.apple.keylayout.Spanish");
		imName = "com.apple.CharacterPaletteIM|||Emoji & Symbols";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		assertNull(info);
		imName = "com.apple.KeyboardViewer|||Keyboard Viewer";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		assertNull(info);
		imName = "org.unknown.keylayout.IPAUnicode51v14MAC|||IPA Unicode 5.1(v1.4) MAC";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		checkKeyboardInfo(info, -1, "en", "IPA Unicode 5.1(v1.4) MAC", true, "org.unknown.keylayout.IPAUnicode51v14MAC");
		imName = "keyman.inputmethod.Keyman|||Keyman";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		assertNull(info);
		imName = null;
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		assertNull(info);
		imName = "";
		info = macHandler.getKeyboardInfoFromKeyboardName(imName);
		assertNull(info);
	}

}
