const { getQuestionsByMode } = require('../../utils/questionBank')
const { getWrongIds, recordAnswer } = require('../../utils/storage')

Page({
  data: {
    questions: [],
    currentIndex: 0,
    question: null,
    answered: false,
    selectedIndex: null,
    isCorrect: false,
    isLast: false
  },

  onLoad(options) {
    const wrongIds = getWrongIds()
    const category = options.category ? decodeURIComponent(options.category) : ''
    const questions = getQuestionsByMode(options.mode, category, wrongIds)

    if (questions.length === 0) {
      wx.showToast({ title: '没有可练习的题目', icon: 'none' })
      wx.navigateBack()
      return
    }

    this.setData({ questions }, () => this.showQuestion(0))
  },

  showQuestion(index) {
    this.setData({
      currentIndex: index,
      question: this.data.questions[index],
      answered: false,
      selectedIndex: null,
      isCorrect: false,
      isLast: index === this.data.questions.length - 1
    })
  },

  chooseAnswer(event) {
    if (this.data.answered) return

    const selectedIndex = Number(event.currentTarget.dataset.index)
    const isCorrect = selectedIndex === this.data.question.answerIndex
    recordAnswer(this.data.question.id, isCorrect)

    this.setData({
      answered: true,
      selectedIndex,
      isCorrect
    })
  },

  nextQuestion() {
    if (this.data.isLast) {
      wx.navigateBack()
      return
    }
    this.showQuestion(this.data.currentIndex + 1)
  },

  goHome() {
    wx.navigateBack()
  }
})
