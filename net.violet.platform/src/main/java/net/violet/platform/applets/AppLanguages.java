package net.violet.platform.applets;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum AppLanguages {
	JAVASCRIPT(Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("this", "prototype", "__proto__", "__count__", "__parent__")))),
	RUBY(Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("alias", "and", "begin", "break", "case", "class", "def", "defined", "do", "else", "elsif", "end", "ensure", "false", "for", "if", "in", "module", "next", "nil", "not", "or", "redo", "rescue", "retry", "return", "self", "super", "then", "true", "undef", "unless", "until", "when", "while", "yield"))));

	private static final Map<String, AppLanguages> LANGUAGE;

	static {
		final Map<String, AppLanguages> language = new HashMap<String, AppLanguages>();

		for (final AppLanguages aLanguage : AppLanguages.values()) {
			language.put(aLanguage.name().toUpperCase(), aLanguage);
		}
		LANGUAGE = Collections.unmodifiableMap(language);
	}

	public static AppLanguages findByLabel(String strLang) {
		return (strLang != null) ? AppLanguages.LANGUAGE.get(strLang.toUpperCase()) : null;
	}

	private final Set<String> mReservedKeywords;

	private AppLanguages(Set<String> inReservedWords) {
		this.mReservedKeywords = inReservedWords;
	}

	public boolean isReserved(String inWord) {
		return this.mReservedKeywords.contains(inWord);
	}

}
