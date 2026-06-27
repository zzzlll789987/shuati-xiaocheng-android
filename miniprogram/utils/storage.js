const ANSWERED_KEY = 'answeredIds'
const CORRECT_KEY = 'correctIds'
const WRONG_KEY = 'wrongIds'
const STREAK_KEY = 'streak'

function readIds(key) {
  return wx.getStorageSync(key) || []
}

function writeIds(key, ids) {
  wx.setStorageSync(key, [...new Set(ids)])
}

function getStats(total) {
  const answered = readIds(ANSWERED_KEY)
  const correct = readIds(CORRECT_KEY)
  const wrong = readIds(WRONG_KEY)
  const streak = wx.getStorageSync(STREAK_KEY) || 0
  const accuracy = answered.length === 0 ? 0 : Math.round((correct.length / answered.length) * 100)

  return {
    total,
    answered: answered.length,
    correct: correct.length,
    wrong: wrong.length,
    streak,
    accuracy
  }
}

function getWrongIds() {
  return readIds(WRONG_KEY)
}

function recordAnswer(questionId, isCorrect) {
  const answered = readIds(ANSWERED_KEY)
  const correct = readIds(CORRECT_KEY)
  const wrong = readIds(WRONG_KEY)

  writeIds(ANSWERED_KEY, answered.concat(questionId))

  if (isCorrect) {
    writeIds(CORRECT_KEY, correct.concat(questionId))
    writeIds(WRONG_KEY, wrong.filter((id) => id !== questionId))
    wx.setStorageSync(STREAK_KEY, (wx.getStorageSync(STREAK_KEY) || 0) + 1)
  } else {
    writeIds(WRONG_KEY, wrong.concat(questionId))
    wx.setStorageSync(STREAK_KEY, 0)
  }
}

module.exports = {
  getStats,
  getWrongIds,
  recordAnswer
}
