package com.shuatixiaocheng.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class QuizStorage {
    private static final String PREFS = "quiz_progress";
    private static final String ANSWERED = "answered";
    private static final String CORRECT = "correct";
    private static final String WRONG = "wrong";
    private static final String STREAK = "streak";

    private final SharedPreferences prefs;

    QuizStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    void record(Question question, boolean isCorrect) {
        Set<String> answered = getSet(ANSWERED);
        Set<String> correct = getSet(CORRECT);
        Set<String> wrong = getSet(WRONG);
        String id = String.valueOf(question.id);

        answered.add(id);
        if (isCorrect) {
            correct.add(id);
            wrong.remove(id);
            prefs.edit().putInt(STREAK, streak() + 1).apply();
        } else {
            wrong.add(id);
            prefs.edit().putInt(STREAK, 0).apply();
        }

        prefs.edit()
                .putStringSet(ANSWERED, answered)
                .putStringSet(CORRECT, correct)
                .putStringSet(WRONG, wrong)
                .apply();
    }

    int answeredCount() {
        return getSet(ANSWERED).size();
    }

    int correctCount() {
        return getSet(CORRECT).size();
    }

    int streak() {
        return prefs.getInt(STREAK, 0);
    }

    List<Question> wrongQuestions(List<Question> allQuestions) {
        Set<String> wrong = getSet(WRONG);
        List<Question> result = new ArrayList<>();
        for (Question question : allQuestions) {
            if (wrong.contains(String.valueOf(question.id))) {
                result.add(question);
            }
        }
        return result;
    }

    private Set<String> getSet(String key) {
        return new HashSet<>(prefs.getStringSet(key, new HashSet<String>()));
    }
}
