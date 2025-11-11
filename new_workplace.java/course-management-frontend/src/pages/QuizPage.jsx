// pages/QuizPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { quizAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const QuizPage = () => {
  const { quizId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [answers, setAnswers] = useState({});
  const [timeRemaining, setTimeRemaining] = useState(0);
  const [attemptId, setAttemptId] = useState(null);
  const [quizStarted, setQuizStarted] = useState(false);
  const [quizCompleted, setQuizCompleted] = useState(false);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);

  // Sample quiz data (In real app, fetch from backend)
  useEffect(() => {
    loadQuizData();
  }, [quizId]);

  useEffect(() => {
    if (quizStarted && timeRemaining > 0) {
      const timer = setInterval(() => {
        setTimeRemaining((prev) => {
          if (prev <= 1) {
            handleSubmit();
            return 0;
          }
          return prev - 1;
        });
      }, 1000);

      return () => clearInterval(timer);
    }
  }, [quizStarted, timeRemaining]);

  const loadQuizData = async () => {
    // In real app, fetch from backend
    // For demo, using sample data
    const sampleQuiz = {
      id: quizId,
      title: 'Java Fundamentals Quiz',
      description: 'Test your knowledge of Java basics',
      timeLimitMinutes: 30,
      passingScore: 70,
      maxAttempts: 3,
      questions: [
        {
          id: 1,
          questionText: 'What is the size of int data type in Java?',
          questionType: 'MULTIPLE_CHOICE',
          options: ['8 bits', '16 bits', '32 bits', '64 bits'],
          correctAnswer: '32 bits',
          points: 10
        },
        {
          id: 2,
          questionText: 'Is Java platform independent?',
          questionType: 'TRUE_FALSE',
          options: ['True', 'False'],
          correctAnswer: 'True',
          points: 10
        },
        {
          id: 3,
          questionText: 'Which keyword is used for inheritance in Java?',
          questionType: 'MULTIPLE_CHOICE',
          options: ['inherit', 'extends', 'implements', 'super'],
          correctAnswer: 'extends',
          points: 10
        },
        {
          id: 4,
          questionText: 'What is the default value of boolean variable in Java?',
          questionType: 'MULTIPLE_CHOICE',
          options: ['true', 'false', 'null', '0'],
          correctAnswer: 'false',
          points: 10
        },
        {
          id: 5,
          questionText: 'Which method is the entry point of a Java program?',
          questionType: 'SHORT_ANSWER',
          correctAnswer: 'main',
          points: 20
        }
      ]
    };

    setQuiz(sampleQuiz);
    setQuestions(sampleQuiz.questions);
    setTimeRemaining(sampleQuiz.timeLimitMinutes * 60);
    setLoading(false);
  };

  const startQuiz = async () => {
    try {
      const response = await quizAPI.startQuiz(quizId, user.id);
      if (response.data.success) {
        setAttemptId(response.data.data.id);
        setQuizStarted(true);
      }
    } catch (err) {
      alert('Failed to start quiz: ' + (err.response?.data?.message || 'Error'));
    }
  };

  const handleAnswerChange = (questionId, answer) => {
    setAnswers({
      ...answers,
      [questionId]: answer
    });
  };

  const calculateScore = () => {
    let score = 0;
    let totalPoints = 0;

    questions.forEach(question => {
      totalPoints += question.points;
      const userAnswer = answers[question.id];
      
      if (question.questionType === 'SHORT_ANSWER') {
        // Case insensitive comparison for short answers
        if (userAnswer?.toLowerCase().trim() === question.correctAnswer.toLowerCase().trim()) {
          score += question.points;
        }
      } else {
        if (userAnswer === question.correctAnswer) {
          score += question.points;
        }
      }
    });

    return { score, totalPoints };
  };

  const handleSubmit = async () => {
    if (!attemptId) {
      alert('Quiz not started properly');
      return;
    }

    const { score, totalPoints } = calculateScore();
    const percentage = (score / totalPoints) * 100;

    try {
      const response = await quizAPI.submitQuiz(
        attemptId,
        JSON.stringify(answers),
        score,
        totalPoints
      );

      if (response.data.success) {
        setResult({
          score,
          totalPoints,
          percentage,
          passed: percentage >= quiz.passingScore
        });
        setQuizCompleted(true);
      }
    } catch (err) {
      alert('Failed to submit quiz');
    }
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  };

  if (loading) {
    return <div className="text-center mt-5"><div className="spinner-border"></div></div>;
  }

  if (!quizStarted) {
    return (
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <div className="card shadow">
              <div className="card-body p-5">
                <h2 className="card-title mb-4">{quiz.title}</h2>
                <p className="lead">{quiz.description}</p>
                
                <div className="alert alert-info">
                  <h5>Quiz Instructions:</h5>
                  <ul>
                    <li>Total Questions: {questions.length}</li>
                    <li>Time Limit: {quiz.timeLimitMinutes} minutes</li>
                    <li>Passing Score: {quiz.passingScore}%</li>
                    <li>Maximum Attempts: {quiz.maxAttempts}</li>
                  </ul>
                </div>

                <div className="alert alert-warning">
                  <i className="bi bi-exclamation-triangle-fill me-2"></i>
                  <strong>Important:</strong> Once you start the quiz, the timer will begin. 
                  You cannot pause or restart the quiz.
                </div>

                <button 
                  className="btn btn-primary btn-lg w-100"
                  onClick={startQuiz}
                >
                  <i className="bi bi-play-circle me-2"></i>
                  Start Quiz
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (quizCompleted) {
    return (
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <div className="card shadow">
              <div className="card-body p-5 text-center">
                <div className={`display-1 mb-4 ${result.passed ? 'text-success' : 'text-danger'}`}>
                  <i className={`bi ${result.passed ? 'bi-check-circle-fill' : 'bi-x-circle-fill'}`}></i>
                </div>
                
                <h2 className="mb-4">
                  {result.passed ? 'Congratulations! You Passed!' : 'Quiz Completed'}
                </h2>
                
                <div className="row mb-4">
                  <div className="col-md-4">
                    <h4>{result.score}</h4>
                    <p className="text-muted">Your Score</p>
                  </div>
                  <div className="col-md-4">
                    <h4>{result.totalPoints}</h4>
                    <p className="text-muted">Total Points</p>
                  </div>
                  <div className="col-md-4">
                    <h4>{result.percentage.toFixed(1)}%</h4>
                    <p className="text-muted">Percentage</p>
                  </div>
                </div>

                <div className="progress mb-4" style={{ height: '30px' }}>
                  <div 
                    className={`progress-bar ${result.passed ? 'bg-success' : 'bg-danger'}`}
                    style={{ width: `${result.percentage}%` }}
                  >
                    {result.percentage.toFixed(1)}%
                  </div>
                </div>

                {result.passed && (
                  <div className="alert alert-success">
                    <i className="bi bi-trophy-fill me-2"></i>
                    You have successfully completed this quiz! 
                    Your certificate will be available soon.
                  </div>
                )}

                <div className="d-flex gap-3 justify-content-center">
                  <button 
                    className="btn btn-primary"
                    onClick={() => navigate('/my-courses')}
                  >
                    Back to My Courses
                  </button>
                  <button 
                    className="btn btn-outline-secondary"
                    onClick={() => navigate('/certificates')}
                  >
                    View Certificates
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  const currentQ = questions[currentQuestion];

  return (
    <div className="container mt-4">
      {/* Timer Bar */}
      <div className="card mb-4 border-primary">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h5 className="mb-0">{quiz.title}</h5>
              <small className="text-muted">
                Question {currentQuestion + 1} of {questions.length}
              </small>
            </div>
            <div className="text-end">
              <h3 className={`mb-0 ${timeRemaining < 300 ? 'text-danger' : 'text-primary'}`}>
                <i className="bi bi-clock me-2"></i>
                {formatTime(timeRemaining)}
              </h3>
              <small className="text-muted">Time Remaining</small>
            </div>
          </div>
          
          <div className="progress mt-3" style={{ height: '10px' }}>
            <div 
              className="progress-bar"
              style={{ width: `${((currentQuestion + 1) / questions.length) * 100}%` }}
            ></div>
          </div>
        </div>
      </div>

      {/* Question Card */}
      <div className="card shadow">
        <div className="card-body p-5">
          <div className="mb-4">
            <span className="badge bg-primary mb-3">
              {currentQ.points} Points
            </span>
            <h4 className="mb-4">{currentQ.questionText}</h4>
          </div>

          {/* Answer Options */}
          {currentQ.questionType === 'MULTIPLE_CHOICE' || currentQ.questionType === 'TRUE_FALSE' ? (
            <div className="list-group">
              {currentQ.options.map((option, index) => (
                <label 
                  key={index}
                  className={`list-group-item list-group-item-action ${
                    answers[currentQ.id] === option ? 'active' : ''
                  }`}
                  style={{ cursor: 'pointer' }}
                >
                  <input
                    type="radio"
                    name={`question-${currentQ.id}`}
                    value={option}
                    checked={answers[currentQ.id] === option}
                    onChange={(e) => handleAnswerChange(currentQ.id, e.target.value)}
                    className="form-check-input me-3"
                  />
                  {option}
                </label>
              ))}
            </div>
          ) : (
            <div className="mb-3">
              <textarea
                className="form-control"
                rows="4"
                placeholder="Type your answer here..."
                value={answers[currentQ.id] || ''}
                onChange={(e) => handleAnswerChange(currentQ.id, e.target.value)}
              ></textarea>
            </div>
          )}

          {/* Navigation Buttons */}
          <div className="d-flex justify-content-between mt-4">
            <button
              className="btn btn-outline-secondary"
              onClick={() => setCurrentQuestion(Math.max(0, currentQuestion - 1))}
              disabled={currentQuestion === 0}
            >
              <i className="bi bi-arrow-left me-2"></i>
              Previous
            </button>

            {currentQuestion === questions.length - 1 ? (
              <button
                className="btn btn-success btn-lg"
                onClick={handleSubmit}
              >
                <i className="bi bi-check-circle me-2"></i>
                Submit Quiz
              </button>
            ) : (
              <button
                className="btn btn-primary"
                onClick={() => setCurrentQuestion(Math.min(questions.length - 1, currentQuestion + 1))}
              >
                Next
                <i className="bi bi-arrow-right ms-2"></i>
              </button>
            )}
          </div>

          {/* Question Navigator */}
          <div className="mt-4 pt-4 border-top">
            <h6 className="mb-3">Question Navigator:</h6>
            <div className="d-flex flex-wrap gap-2">
              {questions.map((q, index) => (
                <button
                  key={q.id}
                  className={`btn btn-sm ${
                    index === currentQuestion 
                      ? 'btn-primary' 
                      : answers[q.id] 
                        ? 'btn-success' 
                        : 'btn-outline-secondary'
                  }`}
                  onClick={() => setCurrentQuestion(index)}
                  style={{ width: '40px', height: '40px' }}
                >
                  {index + 1}
                </button>
              ))}
            </div>
            <small className="text-muted mt-2 d-block">
              <span className="badge bg-success me-2"></span> Answered
              <span className="badge bg-outline-secondary ms-3 me-2"></span> Not Answered
            </small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default QuizPage;