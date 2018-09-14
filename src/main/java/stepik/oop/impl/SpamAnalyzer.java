package stepik.oop.impl;

import stepik.oop.Label;

public class SpamAnalyzer extends KeywordAnalyzer {
    final private String[] keywords;
    private Label label = Label.OK;
    private String name = "Spam";

    public SpamAnalyzer(String[] arr) {
        this.keywords = arr;
    }

    @Override
    public Label processText(String text) {
        for (String s:keywords) {
            if (text.contains(s)) return label = Label.SPAM;
        }
        return label=Label.OK;
    }

    @Override
    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }
}
