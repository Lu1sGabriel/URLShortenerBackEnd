package luis.goes.urlshortener.core.shared.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class NameFormatter {

    private NameFormatter() {
    }

    public static String format(String name) {
        return Arrays.stream(name.trim().split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public static String removeAccents(String name) {
        String nfdNormalizedString = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");

    }

}