/**
 * Copyright (c) 2022 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @see http://stackoverflow.com/a/32324060/14731
 * @author Gili Tzabari
 */
public final class Locales
{
    /**
     * Maps a Microsoft LCID to a Java Locale.
     */
    private final Map<Integer, Locale> lcidToLocale = new HashMap<>(LcidToLocaleMapping.NUM_LOCALES);

    public Locales()
    {
        // Try loading the mapping from cache
        File file = new File("resources/Keyboards/Windows/lcid-to-locale.properties");
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(file))
        {
            properties.load(in);
            for (Object key: properties.keySet())
            {
                String keyString = key.toString();
                Integer lcid = Integer.parseInt(keyString);
                String languageTag = properties.getProperty(keyString);
                lcidToLocale.put(lcid, Locale.forLanguageTag(languageTag));
            }
            return;
        }
        catch (IOException unused)
        {
            // Cache does not exist or is invalid, regenerate...
            lcidToLocale.clear();
        }

        LcidToLocaleMapping mapping;
        try
        {
            mapping = new LcidToLocaleMapping();
        }
        catch (IOException e)
        {
            // Unrecoverable runtime failure
            throw new AssertionError(e);
        }
        for (Locale locale: Locale.getAvailableLocales())
        {
            if (locale == Locale.ROOT)
            {
                // Special case that doesn't map to a real locale
                continue;
            }
            String language = locale.getDisplayLanguage(Locale.ENGLISH);
            String country = locale.getDisplayCountry(Locale.ENGLISH);
            country = mapping.getCountryAlias(country);
            String script = locale.getDisplayScript();
            for (Integer lcid: mapping.listLcidFor(language, country, script))
            {
                lcidToLocale.put(lcid, locale);
                properties.put(lcid.toString(), locale.toLanguageTag());
            }
        }

        // Cache the mapping
        try (FileOutputStream out = new FileOutputStream(file))
        {
            properties.store(out, "LCID to Locale mapping");
        }
        catch (IOException e)
        {
            // Unrecoverable runtime failure
            throw new AssertionError(e);
        }
    }

    /**
     * @param lcid a Microsoft LCID code
     * @return a Java locale
     * @see https://msdn.microsoft.com/en-us/library/cc223140.aspx
     */
    public Locale fromLcid(int lcid)
    {
        return lcidToLocale.get(lcid);
    }
}

