/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Regular expression pointcut based on the <code>java.util.regex</code> package.
 * Supports the following JavaBean properties:
 * <ul>
 * <li>pattern: regular expression for the fully-qualified method names to match
 * <li>patterns: alternative property taking a String array of patterns. The result will
 * be the union of these patterns.
 * </ul>
 *
 * <p>Note: the regular expressions must be a match. For example,
 * <code>.*get.*</code> will match com.mycom.Foo.getBar().
 * <code>get.*</code> will not.
 *
 * @author Dmitriy Kopylenko
 * @author Rob Harrop
 * @since 1.1
 */
public class JdkRegexpMethodPointcut extends AbstractRegexpMethodPointcut {
	
	/** 
	 * Compiled form of the patterns.
	 */
	private transient Pattern[] compiledPatterns = new Pattern[0];

	/** 
	 * Compiled form of the exclusion patterns.
	 */
	private transient Pattern[] compiledExclusionPatterns = new Pattern[0];


	/**
	 * Initialize {@link Pattern Patterns} from the supplied <code>String[]</code>.
	 */
	protected void initPatternRepresentation(String[] patterns) throws PatternSyntaxException {
		this.compiledPatterns = compilePatterns(patterns);
	}

	/**
	 * Returns <code>true</code> if the {@link Pattern} at index <code>patternIndex</code>
	 * matches the supplied candidate <code>String</code>.
	 */
	protected boolean matches(String pattern, int patternIndex) {
		Matcher matcher = this.compiledPatterns[patternIndex].matcher(pattern);
		return matcher.matches();
	}

	/**
	 * Initialize exclusion {@link Pattern Patterns} from the supplied <code>String[]</code>.
	 */
	protected void initExcludedPatternRepresentation(String[] excludedPatterns) throws IllegalArgumentException {
		this.compiledExclusionPatterns = compilePatterns(excludedPatterns);
	}

	/**
	 * Returns <code>true</code> if the exclusion {@link Pattern} at index <code>patternIndex</code>
	 * matches the supplied candidate <code>String</code>.
	 */
	protected boolean matchesExclusion(String candidate, int patternIndex) {
		Matcher matcher = this.compiledExclusionPatterns[patternIndex].matcher(candidate);
		return matcher.matches();
	}

	/**
	 * Compiles the supplied <code>String[]</code> into an array of
	 * {@link Pattern} objects and returns that array.
	 */
	private Pattern[] compilePatterns(String[] source) {
		Pattern[] destination = new Pattern[source.length];
		for (int i = 0; i < source.length; i++) {
			destination[i] = Pattern.compile(source[i]);
		}
		return destination;
	}

}
