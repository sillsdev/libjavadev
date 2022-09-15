/**
 * Copyright (c) 2022 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import com.sun.jna.Pointer;

import javafx.stage.Stage;

import java.lang.reflect.Method;

public class FXWinUtil {

	// Following code comes from 
	// https://stackoverflow.com/questions/15034407/how-can-i-get-the-window-handle-hwnd-for-a-stage-in-javafx
	// Andy changed it to use Stage instead of Window
	public static Pointer getNativeHandleForStage(Stage stage) {
		Pointer retval = null;
		try {
			Method getPeer = stage.getClass().getMethod("impl_getPeer");
			final Object tkStage = getPeer.invoke(stage);
			Method getPlatformWindow = tkStage.getClass().getDeclaredMethod("getPlatformWindow");
			getPlatformWindow.setAccessible(true);
			final Object platformWindow = getPlatformWindow.invoke(tkStage);
			Method getNativeHandle = platformWindow.getClass().getMethod("getNativeHandle");
			retval = new Pointer((Long) getNativeHandle.invoke(platformWindow));
		} catch (Exception ex) {
			System.err.println("Unable to determine native handle for window");
			return null;
		}
		return retval;
	}
}