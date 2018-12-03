// Copyright (c) 2017-2018 SIL International
// This software is licensed under the LGPL, version 2.1 or later
// (http://www.gnu.org/licenses/lgpl-2.1.html)
package org.sil.utility.test;

import org.sil.utility.ApplicationPreferencesUtilities;

/**
 * @author Andy Black
 *
 */
public class ApplicationPreferences extends ApplicationPreferencesUtilities {
	
	@Override
	public String getLastOpenedDirectoryPath() {
		return "LAST_OPENED_DIRECTORY_PATH";
	}

	@Override
	public void setLastOpenedDirectoryPath(String path) {
		// TODO Auto-generated method stub
		return;
	}


}
