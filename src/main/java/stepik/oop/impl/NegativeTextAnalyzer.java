package stepik.oop.impl;

import stepik.oop.Label;


public class NegativeTextAnalyzer extends KeywordAnalyzer {
final String[] keywords = new String[]{":(", "=(", ":|"};
private Label label = Label.OK;
    public NegativeTextAnalyzer() {
    }

    @Override
    public Label processText(String text) {

        for (String s:keywords) {
            if (text.contains(s)){
                return label=Label.NEGATIVE_TEXT;
            }
        }
        return label = Label.OK;
    }

    @Override
    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public Label getLabel() {
        return label;
    }
}
