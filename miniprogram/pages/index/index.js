const { questions, getCategories } = require('../../utils/questionBank')
const { getStats, getWrongIds } = require('../../utils/storage')

Page({
  data: {
    stats: {},
    categories: []
  },

  onShow() {
    this.setData({
      stats: getStats(questions.length),
      categories: getCategories()
    })
  },

  startAll() {
    wx.navigateTo({ url: '/pages/quiz/quiz?mode=all' })
  },

  startCategory(event) {
    const category = event.currentTarget.dataset.category
    wx.navigateTo({ url: `/pages/quiz/quiz?mode=category&category=${encodeURIComponent(category)}` })
  },

  startWrong() {
    if (getWrongIds().length === 0) {
      wx.showToast({ title: '当前没有错题', icon: 'none' })
      return
    }
    wx.navigateTo({ url: '/pages/quiz/quiz?mode=wrong' })
  }
})
