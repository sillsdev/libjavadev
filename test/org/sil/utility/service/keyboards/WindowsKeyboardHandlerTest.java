/**
 * Copyright (c) 2022 SIL International
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
public class WindowsKeyboardHandlerTest {
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	WindowsKeyboardHandler handler = null;
	@Before
	public void setUp() throws Exception {
		handler = new WindowsKeyboardHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void exceptionContentMessageTest() {
		assertEquals("Please copy the exception information below, email it to Language_Software_Support@sil.org along with a description of what you were doing.", handler.getExceptionContentMessage());
		handler.setEmailSupportAddress("asheninka_support_lsdev@sil.org");
		assertEquals("Please copy the exception information below, email it to asheninka_support_lsdev@sil.org along with a description of what you were doing.", handler.getExceptionContentMessage());
	}

	@Test
	public void getKeyboardInfoFromLangIdTest() {
		int langId = 0x0409;
		KeyboardInfo info = handler.getKeyboardInfoFromLangId(langId);
		checkKeyboardInfo(info, langId, "en", "en; English", true);
		langId = 0x080A;
		info = handler.getKeyboardInfoFromLangId(langId);
		checkKeyboardInfo(info, langId, "es", "es_MX; Spanish (Mexico)", true);
		langId = 0x0000;
		info = handler.getKeyboardInfoFromLangId(langId);
		checkKeyboardInfo(info, langId, "??", "??", false);
	}

	protected void checkKeyboardInfo(KeyboardInfo info, int langId, String localeLanguage, String description, Boolean localeExists) {
		assertNotNull(info);
		if (info.getLocale() != null) {
			assertEquals(true, localeExists);
			assertEquals(localeLanguage, info.getLocale().getLanguage());
		} else {
			assertEquals(false, localeExists);
		}
		assertEquals(langId, info.getWindowsLangID());
		assertEquals(description, info.getDescription());
	}
	
	@Test
	public void getAvailableKeyboardsTest() {
		// N.B. This will only work on a computer with four keyboards available:
		// English, Mexico Spanish, Keyman IPA, and Keyman Hebrew
		List<KeyboardInfo> availableKeyboards = handler.getAvailableKeyboards();
		assertEquals(4, availableKeyboards.size());
		KeyboardInfo info = availableKeyboards.get(0);  // English
		checkKeyboardInfo(info, 0x0409, "en", "en; English", true);
		
		info = availableKeyboards.get(1);  // Spanish (Mexico)
		checkKeyboardInfo(info, 0x080A, "es", "es_MX; Spanish (Mexico)", true);
		
		info = availableKeyboards.get(2);  // Hebrew
		checkKeyboardInfo(info, 0x2000, "", "Ancient Hebrew (hbo-Hebr-IL) Hebrew (SIL)", false);

		info = availableKeyboards.get(3);  // IPA
		checkKeyboardInfo(info, 0x2400, "", "und-Latn (und-Latn) SIL IPA", false);
	}
	
// Does not get a real stage; would have to extend an Application and launch it (I think)
//	@Test
//	public void changeToKeyboardTest() {
//		MainApp mainApp = new MainApp();
//		Stage stage = mainApp.getPrimaryStage();
//		KeyboardInfo info = new KeyboardInfo(new Locale("en"), "en; English", 0x0409);
//		boolean result = handler.changeToKeyboard(info, stage);
//		assertEquals(true, result);
//		info.setWindowsLangID(0xaaaa);
//		result = handler.changeToKeyboard(info, stage);
//		assertEquals(false, result);
//	}
}
