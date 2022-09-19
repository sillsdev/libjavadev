// GetProfles.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
/**
* Copyright(c) 2022 SIL International
* This software is licensed under the LGPL, version 2.1 or later
* (http://www.gnu.org/licenses/lgpl-2.1.html)
*/
// Marc Durdin and Andy Black

#include <iostream>
#include "msctf.h"

int main()
{
	ITfInputProcessorProfiles *profiles;
	ITfInputProcessorProfileMgr *pmgr;
	LANGID *langids;
	ULONG count;

	CoInitializeEx(NULL, COINIT_APARTMENTTHREADED);
	CoCreateInstance(CLSID_TF_InputProcessorProfiles, NULL, CLSCTX_INPROC_SERVER, IID_ITfInputProcessorProfiles, (PVOID *)&profiles);
	profiles->QueryInterface(IID_ITfInputProcessorProfileMgr, (PVOID *)&pmgr);

	profiles->GetLanguageList(&langids, &count);

	for (ULONG i = 0; i < count; i++) {
		IEnumTfInputProcessorProfiles *ppEnum;
		pmgr->EnumProfiles(langids[i], &ppEnum);

		TF_INPUTPROCESSORPROFILE pp;
		while (ppEnum->Next(1, &pp, NULL) == S_OK) {
			if (pp.dwProfileType == TF_PROFILETYPE_KEYBOARDLAYOUT) {
				// Following based on Example 2 from https://cpp.hotexamples.com/examples/-/-/LCIDToLocaleName/cpp-lcidtolocalename-function-examples.html Accessed 2022.08.31
				int len;
				LCID lcid = langids[i];
				len = LCIDToLocaleName(lcid, NULL, 0, 0);
				if (!len) {
					printf("LCIDToLocaleName failed getting length: %u\n", GetLastError());
					return E_FAIL;
				}

				BSTR ret = SysAllocStringLen(NULL, len - 1);
				if (!ret) {
					printf("LCIDToLocaleName failed running out of memory: %u\n", GetLastError());
					return E_OUTOFMEMORY;
				}

				len = LCIDToLocaleName(lcid, ret, len, 0);
				if (!len) {
					printf("LCIDToLocaleName failed: %u\n", GetLastError());
					SysFreeString(ret);
					return E_FAIL;
				}
				printf("%d\t'%S'\n", langids[i], ret);
				SysFreeString(ret);
			}
		}
	}
	CoTaskMemFree(langids);
	return 0;
}

