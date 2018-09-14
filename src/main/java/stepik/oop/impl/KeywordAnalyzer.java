package stepik.oop.impl;

import stepik.oop.Label;
import stepik.oop.TextAnalyzer;

public abstract class KeywordAnalyzer implements TextAnalyzer {
    private String[] keywords;
    protected abstract String[] getKeywords();
    protected abstract Label getLabel();

    public Label processText(String text) {

        return Label.OK;
    }
}
