/**
 * Copyright (c) 2020-2024 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Andy Black
 *
 */
public class KeyboardInfo {

	Locale locale;
	String sLocale;
	String description;
	int windowsLangID = 0;
	
	public KeyboardInfo() {
		sLocale = "en";
		locale = new Locale(sLocale);
		description = "English";
		windowsLangID = 0;
	}

	public KeyboardInfo(Locale locale, String description) {
		super();
		this.locale = locale;
		this.sLocale = locale.getLanguage();
		this.description = description;
	}
	
	public KeyboardInfo(Locale locale, String description, int windowsLangID) {
		super();
		this.locale = locale;
		if (locale != null) {
			this.sLocale = locale.getLanguage();
		}
		this.description = description;
		this.windowsLangID = windowsLangID;
	}

	public KeyboardInfo(String sLocale, String description, int windowsLangID) {
		this.sLocale = sLocale;
		this.locale = new Locale(sLocale);
		this.description = description;
		this.windowsLangID = windowsLangID;
	}

	public String getSLocale() {
		return sLocale;
	}

	public void setSLocale(String sLocale) {
		this.sLocale = sLocale;
	}

	@XmlTransient
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWindowsLangID() {
		return windowsLangID;
	}

	public void setWindowsLangID(int windowsLangID) {
		this.windowsLangID = windowsLangID;
	}
	
	@Override
	public String toString() {
		return getDescription() + "; " + getLocale();
	}
}
