// Copyright (c) 2016-2024 SIL International 
// This software is licensed under the LGPL, version 2.1 or later 
// (http://www.gnu.org/licenses/lgpl-2.1.html) 
package org.sil.utility.view;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class ObservableResourceFactoryTest {

	@Test
	public void instancesTest() {
		ObservableResourceFactory orf = ObservableResourceFactory.getInstance();
		assertNotNull(orf);
		ResourceBundle resources = orf.getResources();
		assertNull(resources);
		resources = ResourceBundle.getBundle("org.sil.utility.resources.Utility", new Locale("en"));
		orf.setResources(resources);
		resources = orf.getResources();
		assertNotNull(resources);
		String s = orf.getStringBinding("menu.file").get();
		assertEquals("_File", s);
		s = orf.getStringBinding("spacingdialog.title").get();
		assertEquals("Tree Spacing Parameters", s);
	}

}
