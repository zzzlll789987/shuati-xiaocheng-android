package com.shuatixiaocheng.app;

final class Question {
    final int id;
    final String category;
    final String stem;
    final String[] choices;
    final int answerIndex;
    final String explanation;

    Question(int id, String category, String stem, String[] choices, int answerIndex, String explanation) {
        this.id = id;
        this.category = category;
        this.stem = stem;
        this.choices = choices;
        this.answerIndex = answerIndex;
        this.explanation = explanation;
    }
}
