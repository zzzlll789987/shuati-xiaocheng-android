const questions = [
  {
    id: 1,
    category: '综合能力',
    stem: '做选择题时，最适合先处理哪一类选项？',
    choices: ['明显错误项', '最长的选项', '看起来熟悉的选项', '最后一个选项'],
    answerIndex: 0,
    explanation: '先排除明显错误项，可以快速缩小范围，提高正确率。'
  },
  {
    id: 2,
    category: '综合能力',
    stem: '如果一道题完全没有思路，比较稳妥的第一步是？',
    choices: ['立刻放弃', '圈出关键词', '直接看答案', '随机选一个'],
    answerIndex: 1,
    explanation: '关键词能帮助你判断题型、限制条件和隐藏陷阱。'
  },
  {
    id: 3,
    category: '数学基础',
    stem: '12 × 8 的结果是？',
    choices: ['86', '92', '96', '108'],
    answerIndex: 2,
    explanation: '12 × 8 = 96。'
  },
  {
    id: 4,
    category: '数学基础',
    stem: '一个数的 25% 是 15，这个数是？',
    choices: ['30', '45', '60', '75'],
    answerIndex: 2,
    explanation: '25% 等于四分之一，所以原数是 15 × 4 = 60。'
  },
  {
    id: 5,
    category: '英语词汇',
    stem: '单词 improve 的中文意思最接近？',
    choices: ['改善', '包含', '拒绝', '比较'],
    answerIndex: 0,
    explanation: 'improve 表示改善、提高。'
  },
  {
    id: 6,
    category: '英语词汇',
    stem: "Which word means '快速的'?",
    choices: ['slow', 'rapid', 'quiet', 'empty'],
    answerIndex: 1,
    explanation: 'rapid 的意思是快速的、迅速的。'
  },
  {
    id: 7,
    category: '常识判断',
    stem: '水在标准大气压下的沸点约为？',
    choices: ['0°C', '37°C', '60°C', '100°C'],
    answerIndex: 3,
    explanation: '标准大气压下，水的沸点约为 100°C。'
  },
  {
    id: 8,
    category: '常识判断',
    stem: '中国古代四大发明不包括哪一项？',
    choices: ['造纸术', '指南针', '蒸汽机', '火药'],
    answerIndex: 2,
    explanation: '蒸汽机不是中国古代四大发明之一。'
  }
]

function getCategories() {
  return [...new Set(questions.map((question) => question.category))]
}

function getQuestionsByMode(mode, category, wrongIds = []) {
  if (mode === 'wrong') {
    return questions.filter((question) => wrongIds.includes(question.id))
  }
  if (category) {
    return questions.filter((question) => question.category === category)
  }
  return questions
}

module.exports = {
  questions,
  getCategories,
  getQuestionsByMode
}
