package com.shuatixiaocheng.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    private static final int ORANGE = Color.rgb(249, 115, 22);
    private static final int ORANGE_LIGHT = Color.rgb(255, 247, 237);
    private static final int INK = Color.rgb(17, 24, 39);
    private static final int MUTED = Color.rgb(107, 114, 128);
    private static final int GREEN = Color.rgb(22, 163, 74);
    private static final int RED = Color.rgb(220, 38, 38);

    private QuestionBank questionBank;
    private QuizStorage storage;
    private LinearLayout root;
    private List<Question> activeQuestions;
    private int currentIndex;
    private boolean answeredCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionBank = new QuestionBank();
        storage = new QuizStorage(this);
        showHome();
    }

    private void baseScreen() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(28));
        root.setBackgroundColor(Color.WHITE);
        scrollView.addView(root);
        setContentView(scrollView);
    }

    private void showHome() {
        baseScreen();
        addTitle("刷题小橙");
        addSubtitle("今天也稳稳拿分");

        LinearLayout stats = row();
        stats.addView(statBox("总题数", String.valueOf(questionBank.all().size())));
        stats.addView(statBox("已完成", String.valueOf(storage.answeredCount())));
        root.addView(stats);

        LinearLayout statsTwo = row();
        int correct = storage.correctCount();
        int answered = storage.answeredCount();
        String accuracy = answered == 0 ? "0%" : Math.round(correct * 100f / answered) + "%";
        statsTwo.addView(statBox("正确率", accuracy));
        statsTwo.addView(statBox("连对", String.valueOf(storage.streak())));
        root.addView(statsTwo);

        addSection("选择练习");
        addPrimaryButton("全部题目", view -> startQuiz(questionBank.all()));
        for (String category : questionBank.categories()) {
            addSecondaryButton(category, view -> startQuiz(questionBank.byCategory(((Button) view).getText().toString())));
        }
        addSecondaryButton("错题本", view -> {
            List<Question> wrong = storage.wrongQuestions(questionBank.all());
            if (wrong.isEmpty()) {
                Toast.makeText(this, "当前没有错题，状态不错。", Toast.LENGTH_SHORT).show();
            } else {
                startQuiz(wrong);
            }
        });
    }

    private void startQuiz(List<Question> questions) {
        activeQuestions = questions;
        currentIndex = 0;
        showQuestion();
    }

    private void showQuestion() {
        baseScreen();
        answeredCurrent = false;
        Question question = activeQuestions.get(currentIndex);

        addTopBar("第 " + (currentIndex + 1) + " / " + activeQuestions.size() + " 题", "返回首页", view -> showHome());
        addBadge(question.category);
        addQuestionStem(question.stem);

        for (int index = 0; index < question.choices.length; index++) {
            final int choiceIndex = index;
            addChoiceButton(question.choices[index], view -> checkAnswer(question, choiceIndex));
        }
    }

    private void checkAnswer(Question question, int choiceIndex) {
        if (answeredCurrent) {
            return;
        }
        answeredCurrent = true;
        boolean isCorrect = choiceIndex == question.answerIndex;
        storage.record(question, isCorrect);

        TextView result = text(isCorrect ? "回答正确" : "回答错误", 22, isCorrect ? GREEN : RED, true);
        result.setPadding(0, dp(18), 0, dp(6));
        root.addView(result);

        String correctText = "正确答案：" + question.choices[question.answerIndex];
        root.addView(text(correctText, 16, INK, true));
        TextView explanation = text(question.explanation, 16, MUTED, false);
        explanation.setPadding(0, dp(8), 0, dp(18));
        root.addView(explanation);

        if (currentIndex < activeQuestions.size() - 1) {
            addPrimaryButton("下一题", view -> {
                currentIndex++;
                showQuestion();
            });
        } else {
            addPrimaryButton("完成并返回首页", view -> showHome());
        }
    }

    private void addTitle(String value) {
        TextView title = text(value, 34, INK, true);
        title.setPadding(0, dp(12), 0, dp(4));
        root.addView(title);
    }

    private void addSubtitle(String value) {
        TextView subtitle = text(value, 17, MUTED, false);
        subtitle.setPadding(0, 0, 0, dp(20));
        root.addView(subtitle);
    }

    private void addSection(String value) {
        TextView section = text(value, 18, INK, true);
        section.setPadding(0, dp(24), 0, dp(10));
        root.addView(section);
    }

    private void addQuestionStem(String value) {
        TextView stem = text(value, 24, INK, true);
        stem.setLineSpacing(dp(3), 1.0f);
        stem.setPadding(0, dp(16), 0, dp(20));
        root.addView(stem);
    }

    private void addBadge(String value) {
        TextView badge = text(value, 14, ORANGE, true);
        badge.setBackgroundColor(ORANGE_LIGHT);
        badge.setGravity(Gravity.CENTER);
        badge.setPadding(dp(12), dp(7), dp(12), dp(7));
        root.addView(badge, wrapContent());
    }

    private void addTopBar(String left, String right, View.OnClickListener listener) {
        LinearLayout bar = row();
        TextView label = text(left, 15, MUTED, true);
        Button button = button(right, Color.WHITE, ORANGE);
        button.setOnClickListener(listener);
        bar.addView(label, weight());
        bar.addView(button, wrapContent());
        root.addView(bar);
    }

    private void addPrimaryButton(String label, View.OnClickListener listener) {
        Button button = button(label, ORANGE, Color.WHITE);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = matchWidth();
        params.setMargins(0, dp(8), 0, dp(4));
        root.addView(button, params);
    }

    private void addSecondaryButton(String label, View.OnClickListener listener) {
        Button button = button(label, ORANGE_LIGHT, INK);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = matchWidth();
        params.setMargins(0, dp(8), 0, dp(4));
        root.addView(button, params);
    }

    private void addChoiceButton(String label, View.OnClickListener listener) {
        Button button = button(label, Color.rgb(243, 244, 246), INK);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = matchWidth();
        params.setMargins(0, dp(8), 0, dp(4));
        root.addView(button, params);
    }

    private LinearLayout statBox(String label, String value) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setBackgroundColor(ORANGE_LIGHT);
        box.setPadding(dp(14), dp(14), dp(14), dp(14));
        box.addView(text(value, 26, INK, true));
        box.addView(text(label, 13, MUTED, false));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(0, 0, dp(8), dp(8));
        box.setLayoutParams(params);
        return box;
    }

    private LinearLayout row() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        return layout;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(color);
        if (bold) {
            view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }
        return view;
    }

    private Button button(String label, int background, int textColor) {
        Button button = new Button(this);
        button.setText(label);
        button.setTextSize(16);
        button.setTextColor(textColor);
        button.setBackgroundColor(background);
        button.setMinHeight(dp(48));
        button.setPadding(dp(14), dp(8), dp(14), dp(8));
        return button;
    }

    private LinearLayout.LayoutParams matchWidth() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams wrapContent() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams weight() {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
