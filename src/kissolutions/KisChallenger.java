package kissolutions;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KisChallenger {
	private static final String NO_SPECIAL_CHARS_REGEX = "[^a-zA-Z0-9\\s+]";
	private static final String VOWEL_REGEX = "[aeiouy]";
	private static final String CONSONANTS_REGEX = "[^aeiouy]";


	private String generateSuffix(String in) {
		Pattern pattern = Pattern.compile(CONSONANTS_REGEX, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(in);
	    
	    if(matcher.find()) {
	    	return "ay";	    	
	    }
	    return "yay";
	}
	
	private String getPrefix(String wordIn) {
		String[] split = wordIn.split(VOWEL_REGEX);
		if(split.length == 0) {
			return wordIn;
		}
		if(wordIn.matches(CONSONANTS_REGEX)) {
			return "";
		}
		return split[0];
	}
	
	private String getStem(String prefix, String word) {
		
		if((word.equals(prefix)) && Character.isUpperCase(word.charAt(0))) {
			return prefix;
		}
		
		String stem = word.replace(prefix, "");
		
		if(prefix.length() > 0  && Character.isUpperCase(prefix.charAt(0))) {
			return stem.substring(0,1).toUpperCase().concat(stem.substring(1));
		}
		
		return stem;
	}
	
	private String translateWord(String in) {
		
		if(in == null || in.length() == 0 || isNumber(in)) {
			return in+" ";
		}
		String prefix = getPrefix(in);
		String stem = getStem(prefix, in);
		
		StringBuilder translated = new StringBuilder();
		return movePuntuaction(translated.append(stem).append(prefix.toLowerCase()).append(generateSuffix(in)).toString()).concat(" ");

	}
	
	private boolean isNumber (String in) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	    if (in == null) {
	        return false; 
	    }
	    return pattern.matcher(in).matches();
	}
	
	private String movePuntuaction(String in) {
	    Pattern pattern = Pattern.compile(NO_SPECIAL_CHARS_REGEX, Pattern.CASE_INSENSITIVE );
	    Matcher matcher = pattern.matcher(in);
	    StringBuffer sb = new StringBuffer();
	    StringBuilder puntuaction = new StringBuilder();
	    while (matcher.find()) {
	         puntuaction.append(puntuaction+matcher.group());
	         matcher.appendReplacement(sb, "");
	      }
        matcher.appendTail(sb);

		return sb.toString()+puntuaction;
	}
	
	public String translate(String prhase) {
		List<String> words = Arrays.asList(prhase.split(" "));
		StringBuilder builder = new StringBuilder();
		for (String string : words) {
			builder.append(translateWord(string));
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {		
		String in = "You and I";
		/**
		 * 21st century
		 * You and I
		 * I hurt my eye
		 */
		KisChallenger challenger = new KisChallenger();		
		System.out.println(challenger.translate(in));
	}
	
}
