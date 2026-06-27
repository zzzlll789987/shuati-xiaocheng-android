package com.shuatixiaocheng.app;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class QuestionBank {
    private final List<Question> questions = new ArrayList<>();

    QuestionBank() {
        questions.add(new Question(1, "综合能力", "做选择题时，最适合先处理哪一类选项？",
                new String[]{"明显错误项", "最长的选项", "看起来熟悉的选项", "最后一个选项"}, 0,
                "先排除明显错误项，可以快速缩小范围，提高正确率。"));
        questions.add(new Question(2, "综合能力", "如果一道题完全没有思路，比较稳妥的第一步是？",
                new String[]{"立刻放弃", "圈出关键词", "直接看答案", "随机选一个"}, 1,
                "关键词能帮助你判断题型、限制条件和隐藏陷阱。"));
        questions.add(new Question(3, "数学基础", "12 × 8 的结果是？",
                new String[]{"86", "92", "96", "108"}, 2,
                "12 × 8 = 96。"));
        questions.add(new Question(4, "数学基础", "一个数的 25% 是 15，这个数是？",
                new String[]{"30", "45", "60", "75"}, 2,
                "25% 等于四分之一，所以原数是 15 × 4 = 60。"));
        questions.add(new Question(5, "英语词汇", "单词 improve 的中文意思最接近？",
                new String[]{"改善", "包含", "拒绝", "比较"}, 0,
                "improve 表示改善、提高。"));
        questions.add(new Question(6, "英语词汇", "Which word means '快速的'?",
                new String[]{"slow", "rapid", "quiet", "empty"}, 1,
                "rapid 的意思是快速的、迅速的。"));
        questions.add(new Question(7, "常识判断", "水在标准大气压下的沸点约为？",
                new String[]{"0°C", "37°C", "60°C", "100°C"}, 3,
                "标准大气压下，水的沸点约为 100°C。"));
        questions.add(new Question(8, "常识判断", "中国古代四大发明不包括哪一项？",
                new String[]{"造纸术", "指南针", "蒸汽机", "火药"}, 2,
                "蒸汽机不是中国古代四大发明之一。"));
    }

    List<Question> all() {
        return new ArrayList<>(questions);
    }

    List<String> categories() {
        Set<String> names = new LinkedHashSet<>();
        for (Question question : questions) {
            names.add(question.category);
        }
        return new ArrayList<>(names);
    }

    List<Question> byCategory(String category) {
        List<Question> result = new ArrayList<>();
        for (Question question : questions) {
            if (question.category.equals(category)) {
                result.add(question);
            }
        }
        return result;
    }
}
