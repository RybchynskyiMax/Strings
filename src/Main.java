
import java.text.BreakIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String[] ABBREVIATIONS = {
            "Dr." , "Prof." , "Mr." , "Mrs." , "Ms." , "Jr." , "Ph.D."
    };
    public static void main(String[] args) {
        System.out.println("Please enter some text:");
        Scanner scannerForText = new Scanner(System.in);
        String stringText = scannerForText.nextLine();
        StringBuffer text = new StringBuffer(stringText.subSequence(0, stringText.length()));
        System.out.println("First unique word: "+firstUniqWord(text));
        substitute(text);
        numberOfWords(text);
    }

    public static StringBuffer firstUniqWord(StringBuffer text){
        ArrayList<String > allWords = new ArrayList<String>();
        StringBuffer firstUniqWord = new StringBuffer();
        ArrayList<String> sentences=  breakSentence(text.toString());
        for (String sentence : sentences) {
            String[] words = sentence.split("[,;:\\s]+");
            allWords.addAll(Arrays.asList(words));
        }
        for (String word : allWords) {
            Iterator<String> forShearchingUnicWord = allWords.iterator();
            int counter = 0;
            while (forShearchingUnicWord.hasNext()) {
                if (word.equalsIgnoreCase(forShearchingUnicWord.next())) {
                    counter++;
                }
            }
            if (counter == 1) {
                firstUniqWord.append(word);
                break;
            }
        }
        return firstUniqWord;
    }

    public static void substitute(StringBuffer text){
        StringBuffer result = new StringBuffer();
        ArrayList<String> sentences=  breakSentence(text.toString());
        for (String sentence : sentences) {
            String[] words = sentence.split("[,;:\\s]+");
            String curMin = words[0];
            String curMax = "";
            for (String word : words) {
                if (word.length() > curMax.length()) {
                    curMax = word;
                }
                if (word.length() < curMin.length()) {
                    curMin = word;
                }

            }
            Pattern pattern = Pattern.compile(curMin);
            Matcher matcher =pattern.matcher(sentence);
            while (matcher.find()){
                String s = matcher.replaceAll(curMax);
                result.append(s).append(".");
            }

        }

        System.out.println("Text with substitute:");
        System.out.println(result);
    }
    public static void numberOfWords(StringBuffer text){
        Map<String,Integer> numberOfWords  = new HashMap<String, Integer>();
        ArrayList<String> sentences=  breakSentence(text.toString());
        for (String sentence : sentences) {
            String[] words = sentence.split("[,;:\\s]+");
            for (String word : words) {
                if(numberOfWords.containsKey(word)){
                    numberOfWords.put(word,numberOfWords.get(word)+1);
                }else {
                    numberOfWords.put(word,1);
                }
            }
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : numberOfWords.entrySet()) {
            System.out.println(stringIntegerEntry);
        }
    }
    public static ArrayList<String> breakSentence(String text) {

        ArrayList<String> sentenceList = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
        breakIterator.setText(text);
        int start = breakIterator.first();
        int end = breakIterator.next();
        int temp = start;
        while (end != BreakIterator.DONE) {
            String sentence = text.substring(start, end);
            if (! hasAbbreviation(sentence)) {
                sentence = text.substring(temp, end);
                temp = end;
                sentenceList.add(sentence);
            }
            start = end;
            end = breakIterator.next();
        }
        return sentenceList;
    }

    private static boolean hasAbbreviation(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return false;
        }
        for (String s : ABBREVIATIONS) {
            if (sentence.contains(s)) {
                return true;
            }
        }
        return false;
    }
}




