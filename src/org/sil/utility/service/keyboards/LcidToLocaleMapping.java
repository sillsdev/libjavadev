/**
 * Copyright (c) 2022 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.utility.service.keyboards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//import org.bitbucket.cowwoc.preconditions.Preconditions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import spals.shaded.com.google.common.collect.HashMultimap;
import spals.shaded.com.google.common.collect.ImmutableList;
import spals.shaded.com.google.common.collect.ImmutableMap;
import spals.shaded.com.google.common.collect.SetMultimap;
import spals.shaded.com.google.common.collect.Sets;

/**
 * Generates a mapping between Microsoft LCIDs and Java Locales.
 * <p>
 * @see http://stackoverflow.com/a/32324060/14731
 * @author Gili Tzabari
 */
final class LcidToLocaleMapping
{
    private static final int NUM_COUNTRIES = 194;
    private static final int NUM_LANGUAGES = 13;
    private static final int NUM_SCRIPTS = 5;
    /**
     * The number of locales we are expecting. This value is only used for performance optimization.
     */
    public static final int NUM_LOCALES = 238;
    private static final List<String> EXPECTED_HEADERS = ImmutableList.of("lcid", "language", "location");
    // [language] - [comment] ([script])
    private static final Pattern languagePattern = Pattern.compile("^(.+?)(?: - (.*?))?(?: \\((.+)\\))?$");
    /**
     * Maps a country to a list of entries.
     */
    private static final SetMultimap<String, Mapping> COUNTRY_TO_ENTRIES = HashMultimap.create(NUM_COUNTRIES,
        NUM_LOCALES / NUM_COUNTRIES);
    /**
     * Maps a language to a list of entries.
     */
    private static final SetMultimap<String, Mapping> LANGUAGE_TO_ENTRIES = HashMultimap.create(NUM_LANGUAGES,
        NUM_LOCALES / NUM_LANGUAGES);
    /**
     * Maps a language script to a list of entries.
     */
    private static final SetMultimap<String, Mapping> SCRIPT_TO_ENTRIES = HashMultimap.create(NUM_SCRIPTS,
        NUM_LOCALES / NUM_SCRIPTS);
    /**
     * Maps a Locale country name to a LCID country name.
     */
    private static final Map<String, String> countryAlias = ImmutableMap.<String, String>builder().
        put("United Arab Emirates", "U.A.E.").
        build();

    /**
     * A mapping between a country, language, script and LCID.
     */
    private static final class Mapping
    {
        public final String country;
        public final String language;
        public final String script;
        public final int lcid;

        Mapping(String country, String language, String script, int lcid)
        {
//            Preconditions.requireThat(country, "country").isNotNull();
//            Preconditions.requireThat(language, "language").isNotNull().isNotEmpty();
//            Preconditions.requireThat(script, "script").isNotNull();
            this.country = country;
            this.language = language;
            this.script = script;
            this.lcid = lcid;
        }

        @Override
        public int hashCode()
        {
            return country.hashCode() + language.hashCode() + script.hashCode() + lcid;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof Locales))
                return false;
            Mapping other = (Mapping) obj;
            return country.equals(other.country) && language.equals(other.language) && script.equals(other.script) &&
                lcid == other.lcid;
        }
    }

    /**
     * Creates a new LCID to Locale mapping.
     * <p>
     * @throws IOException if an I/O error occurs while reading the LCID table
     */
    LcidToLocaleMapping() throws IOException
    {
        Document doc = Jsoup.connect("https://msdn.microsoft.com/en-us/library/cc223140.aspx").get();
        System.out.println("ere");
        Elements elements = doc.getElementsByTag("table");
        assert (elements.size() == 1): elements;
        for (Element table: elements)
        {
            boolean firstRow = true;
            for (Element row: table.select("tr"))
            {
                if (firstRow)
                {
                    // Make sure that columns are ordered as expected
                    List<String> headers = new ArrayList<>(3);
                    Elements columns = row.select("th");
                    for (Element column: columns)
                        headers.add(column.text().toLowerCase());
                    assert (headers.equals(EXPECTED_HEADERS)): headers;
                    firstRow = false;
                    continue;
                }
                Elements columns = row.select("td");
                assert (columns.size() == 3): columns;
                Integer lcid = Integer.parseInt(columns.get(0).text(), 16);
                Matcher languageMatcher = languagePattern.matcher(columns.get(1).text());
                if (!languageMatcher.find())
                    throw new AssertionError();
                String language = languageMatcher.group(1);
                String script = languageMatcher.group(2);
                if (script == null)
                    script = "";
                String country = columns.get(2).text();
                Mapping mapping = new Mapping(country, language, script, lcid);
                COUNTRY_TO_ENTRIES.put(country, mapping);
                LANGUAGE_TO_ENTRIES.put(language, mapping);
                if (!script.isEmpty())
                    SCRIPT_TO_ENTRIES.put(script, mapping);
            }
        }
    }

    /**
     * Returns the LCID codes associated with a [country, language, script] combination.
     * <p>
     * @param language a language
     * @param country  a country (empty string if any country should match)
     * @param script   a language script (empty string if any script should match)
     * @return an empty list if no matches are found
     * @throws NullPointerException     if any of the arguments are null
     * @throws IllegalArgumentException if language is empty
     */
    public Collection<Integer> listLcidFor(String language, String country, String script)
        throws NullPointerException, IllegalArgumentException
    {
//        Preconditions.requireThat(language, "language").isNotNull().isNotEmpty();
//        Preconditions.requireThat(country, "country").isNotNull();
//        Preconditions.requireThat(script, "script").isNotNull();
        Set<Mapping> result = LANGUAGE_TO_ENTRIES.get(language);
        if (result == null)
        {
            System.out.println("Language '" + language + "' had no corresponding LCID");
            return Collections.emptyList();
        }
        if (!country.isEmpty())
        {
            Set<Mapping> entries = COUNTRY_TO_ENTRIES.get(country);
            result = Sets.intersection(result, entries);
        }

        if (!script.isEmpty())
        {
            Set<Mapping> entries = SCRIPT_TO_ENTRIES.get(script);
            result = Sets.intersection(result, entries);
        }
        return result.stream().map(entry -> entry.lcid).collect(Collectors.toList());
    }

    /**
     * @param name the locale country name
     * @return the LCID country name
     */
    public String getCountryAlias(String name)
    {
        String result = countryAlias.get(name);
        if (result == null)
            return name;
        return result;
    }
}
