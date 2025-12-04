// pages/ExamPage.jsx
import React, { useState, useEffect, useRef, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const ExamPage = () => {
    const { courseId, enrollmentId } = useParams();
    const navigate = useNavigate();
    const { user } = useAuth();
    
    // ... (state variables remain the same)
    const [exam, setExam] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [currentQuestion, setCurrentQuestion] = useState(0);
    const [answers, setAnswers] = useState({});
    const [timeRemaining, setTimeRemaining] = useState(0);
    const [attemptId, setAttemptId] = useState(null);
    const [examStarted, setExamStarted] = useState(false);
    const [examCompleted, setExamCompleted] = useState(false);
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(true);
    const [previousAttempts, setPreviousAttempts] = useState([]);
    
    const timerRef = useRef(null);
    const API_URL = 'http://localhost:8080/api';

    const getAuthHeader = () => ({
        headers: {
            Authorization: `Bearer ${user.token}`, 
        },
        withCredentials: true
    });

    const handleSubmit = useCallback(async () => {
        if (!examStarted) return; 
        
        if (timeRemaining > 1 && !window.confirm('Are you sure you want to submit? You cannot change your answers after submission.')) {
            return;
        }

        if (timerRef.current) {
            clearInterval(timerRef.current);
            timerRef.current = null;
        }

        try {
            const response = await axios.post(
                `${API_URL}/student/exams/submit/${attemptId}`, 
                {
                    answers: JSON.stringify(answers) 
                },
                getAuthHeader()
            );

            if (response.data.success) {
                const attemptData = response.data.data;
                setResult({
                    score: attemptData.score,
                    totalMarks: attemptData.totalMarks,
                    percentage: attemptData.percentage,
                    passed: attemptData.passed
                });
                setExamCompleted(true);
                setExamStarted(false);
            }
        } catch (err) {
            alert('Failed to submit exam: ' + (err.response?.data?.message || 'Unknown error'));
        }
    }, [examStarted, timeRemaining, attemptId, answers, API_URL, user.token, setResult, setExamCompleted, setExamStarted]);


    useEffect(() => {
        loadExamData();
        return () => {
            if (timerRef.current) {
                clearInterval(timerRef.current);
            }
        };
    }, [courseId, user.token, navigate]);

    useEffect(() => {
        if (examStarted && timeRemaining > 0) {
            timerRef.current = setInterval(() => {
                setTimeRemaining((prev) => {
                    if (prev <= 1) {
                        handleSubmit();
                        return 0;
                    }
                    return prev - 1;
                });
            }, 1000);

            return () => clearInterval(timerRef.current);
        }
    }, [examStarted, timeRemaining, handleSubmit]);

    const loadExamData = async () => {
        try {
            setLoading(true);
            
            const authHeader = getAuthHeader();
            
            console.log('Loading exam for course:', courseId);
            
            // Get exam details
            const examRes = await axios.get(
                `${API_URL}/public/courses/${courseId}/exam`, 
                authHeader
            );
            
            console.log('Exam response:', examRes.data);
            
            if (examRes.data.success && examRes.data.data) {
                const examData = examRes.data.data;
                setExam(examData);
                setTimeRemaining(examData.timeLimitMinutes * 60);
                
                // Load exam questions from backend
                try {
                    const questionsRes = await axios.get(
                        `${API_URL}/public/exams/${examData.id}/questions`,
                        authHeader
                    );
                    
                    if (questionsRes.data.success) {
                        // Keep parsing logic as is, but advise on backend fix
                        const parsedQuestions = questionsRes.data.data.map(q => {
                            if (q.questionType !== 'SHORT_ANSWER' && typeof q.options === 'string') {
                                try {
                                    q.options = JSON.parse(q.options);
                                } catch (e) {
                                    // Log the error but continue
                                    console.error(`Error parsing options for QID ${q.id}. Setting to empty array.`, e);
                                    q.options = []; 
                                }
                            }
                            return q;
                        });

                        setQuestions(parsedQuestions);
                    } else {
                        // Fallback to sample questions if API not ready
                        loadSampleQuestions(examData);
                    }
                } catch (qErr) {
                    console.warn('Questions API not available or failed, using samples:', qErr.message);
                    loadSampleQuestions(examData);
                }
                
                // Load previous attempts
                try {
                    const attemptsRes = await axios.get(
                        `${API_URL}/student/exams/attempts`, 
                        {
                            params: { studentId: user.id, examId: examData.id },
                            ...authHeader 
                        }
                    );
                    
                    if (attemptsRes.data.success) {
                        setPreviousAttempts(attemptsRes.data.data);
                    }
                } catch (aErr) {
                    console.warn('Could not load attempts:', aErr);
                    setPreviousAttempts([]);
                }
                
            } else {
                throw new Error('No exam data found');
            }
            
        } catch (err) {
            console.error('Failed to load exam:', err);
            
            if (err.response?.status === 404) {
                alert('No final exam has been set up for this course yet.');
            } else if (err.response?.status === 401) {
                alert('Session expired. Please login again.');
                navigate('/login');
            } else {
                alert('Failed to load exam: ' + (err.response?.data?.message || err.message));
            }
        } finally {
            setLoading(false);
        }
    };

    const loadSampleQuestions = (examData) => {
        // ... (sample data remains the same)
        const sampleQuestions = [
            {
                id: 1,
                questionText: 'What is polymorphism in Java?',
                questionType: 'MULTIPLE_CHOICE',
                options: [
                    'Method overloading',
                    'Method overriding', 
                    'Both A and B',
                    'None of the above'
                ],
                correctAnswer: 'Both A and B',
                marks: 10,
                orderIndex: 1
            },
            {
                id: 2,
                questionText: 'Java is platform independent.',
                questionType: 'TRUE_FALSE',
                options: ['True', 'False'], 
                correctAnswer: 'True',
                marks: 5,
                orderIndex: 2
            },
            {
                id: 3,
                questionText: 'What is the purpose of the final keyword?',
                questionType: 'SHORT_ANSWER',
                correctAnswer: 'prevent modification',
                marks: 10,
                orderIndex: 3
            },
            {
                id: 4,
                questionText: 'Which is not a Java primitive type?',
                questionType: 'MULTIPLE_CHOICE',
                options: ['int', 'boolean', 'String', 'double'], 
                correctAnswer: 'String',
                marks: 10,
                orderIndex: 4
            },
            {
                id: 5,
                questionText: 'What does JVM stand for?',
                questionType: 'SHORT_ANSWER',
                correctAnswer: 'Java Virtual Machine',
                marks: 15,
                orderIndex: 5
            }
        ];
        
        setQuestions(sampleQuestions);
    };

    const startExam = async () => {
        // FIX: Ensure enrollmentId is valid before proceeding
        const enrollmentIdInt = parseInt(enrollmentId);
        
        if (isNaN(enrollmentIdInt)) {
            alert('Error: Missing or invalid enrollment ID. Cannot start exam.');
            console.error("Invalid enrollmentId:", enrollmentId);
            return;
        }

        try {
            const response = await axios.post(
                `${API_URL}/student/exams/start`, 
                {
                    examId: exam.id,
                    studentId: user.id,
                    enrollmentId: enrollmentIdInt // Use the validated integer
                },
                getAuthHeader()
            );
            
            if (response.data.success) {
                setAttemptId(response.data.data.id);
                setExamStarted(true);
            } else {
                 // Handle explicit failure response from backend
                 alert(response.data?.message || 'Failed to start exam due to unknown reason.');
            }
        } catch (err) {
            alert(err.response?.data?.message || 'Failed to start exam. Check console for details.');
            console.error("Start exam error:", err.response?.data || err);
        }
    };

    const handleAnswerChange = (questionId, answer) => {
        setAnswers({
            ...answers,
            [questionId]: answer
        });
    };

    const formatTime = (seconds) => {
        const mins = Math.floor(seconds / 60);
        const secs = seconds % 60;
        return `${mins}:${secs.toString().padStart(2, '0')}`;
    };

    if (loading) {
        return (
            <div className="text-center mt-5">
                <div className="spinner-border text-primary"></div>
            </div>
        );
    }

    if (!exam) {
        return (
            <div className="container mt-5">
                <div className="alert alert-warning">
                    No final exam found for this course.
                </div>
            </div>
        );
    }

    // ... (Rest of the component JSX remains the same)
    // Pre-exam screen
    if (!examStarted) {
        return (
            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-8">
                        <div className="card shadow">
                            <div className="card-body p-5">
                                <h2 className="card-title mb-4">{exam.title}</h2>
                                <p className="lead">{exam.description}</p>
                                
                                <div className="alert alert-info">
                                    <h5>Exam Instructions:</h5>
                                    <ul>
                                        <li>Total Questions: {questions.length}</li>
                                        <li>Time Limit: {exam.timeLimitMinutes} minutes</li>
                                        <li>Total Marks: {exam.totalMarks}</li>
                                        <li>Passing Score: {exam.passingScore}%</li>
                                        <li>Maximum Attempts: {exam.maxAttempts}</li>
                                        <li>Current Attempts: {previousAttempts.length}</li>
                                    </ul>
                                </div>

                                {previousAttempts.length > 0 && (
                                    <div className="mb-4">
                                        <h5>Previous Attempts:</h5>
                                        <div className="table-responsive">
                                            <table className="table table-sm">
                                                <thead>
                                                    <tr>
                                                        <th>Attempt</th>
                                                        <th>Score</th>
                                                        <th>Percentage</th>
                                                        <th>Result</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {previousAttempts.map((attempt, i) => (
                                                        <tr key={i}>
                                                            <td>#{attempt.attemptNumber}</td>
                                                            <td>{attempt.score}/{attempt.totalMarks}</td>
                                                            <td>{attempt.percentage?.toFixed(1)}%</td>
                                                            <td>
                                                                <span className={`badge ${attempt.passed ? 'bg-success' : 'bg-danger'}`}>
                                                                    {attempt.passed ? 'Passed' : 'Failed'}
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    ))}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                )}

                                <div className="alert alert-warning">
                                    <i className="bi bi-exclamation-triangle-fill me-2"></i>
                                    <strong>Important:</strong> Once you start, the timer will begin and you cannot pause. Make sure you're ready!
                                </div>

                                {previousAttempts.length >= exam.maxAttempts ? (
                                    <div className="alert alert-danger">
                                        You have exhausted all {exam.maxAttempts} attempts for this exam.
                                    </div>
                                ) : (
                                    <button 
                                        className="btn btn-primary btn-lg w-100"
                                        onClick={startExam}
                                    >
                                        <i className="bi bi-play-circle me-2"></i>
                                        Start Exam
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    // Results screen
    if (examCompleted) {
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
                                    {result.passed ? '🎉 Congratulations! You Passed!' : 'Exam Completed'}
                                </h2>
                                
                                <div className="row mb-4">
                                    <div className="col-md-4">
                                        <h4>{result.score}</h4>
                                        <p className="text-muted">Your Score</p>
                                    </div>
                                    <div className="col-md-4">
                                        <h4>{result.totalMarks}</h4>
                                        <p className="text-muted">Total Marks</p>
                                    </div>
                                    <div className="col-md-4">
                                        <h4>{result.percentage?.toFixed(1)}%</h4>
                                        <p className="text-muted">Percentage</p>
                                    </div>
                                </div>

                                <div className="progress mb-4" style={{ height: '30px' }}>
                                    <div 
                                        className={`progress-bar ${result.passed ? 'bg-success' : 'bg-danger'}`}
                                        style={{ width: `${result.percentage}%` }}
                                    >
                                        {result.percentage?.toFixed(1)}%
                                    </div>
                                </div>

                                {result.passed && (
                                    <div className="alert alert-success">
                                        <i className="bi bi-trophy-fill me-2"></i>
                                        Great job! You've passed the final exam. You can now check your certificate eligibility!
                                    </div>
                                )}

                                {!result.passed && (
                                    <div className="alert alert-info">
                                        Don't give up! You can retake the exam. Review the course materials and try again.
                                    </div>
                                )}

                                <div className="d-flex gap-3 justify-content-center mt-4">
                                    <button 
                                        className="btn btn-primary"
                                        onClick={() => navigate(`/courses/${courseId}/materials`)}
                                    >
                                        Back to Course
                                    </button>
                                    
                                    {result.passed && (
                                        <button 
                                            className="btn btn-success"
                                            onClick={() => navigate('/certificates')}
                                        >
                                            Check Certificate
                                        </button>
                                    )}
                                    
                                    {!result.passed && previousAttempts.length < exam.maxAttempts && (
                                        <button 
                                            className="btn btn-warning"
                                            onClick={() => window.location.reload()}
                                        >
                                            Try Again
                                        </button>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    // Exam in progress
    const currentQ = questions[currentQuestion];

    return (
        <div className="container mt-4">
            {/* Timer Bar */}
            <div className="card mb-4 border-primary">
                <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center">
                        <div>
                            <h5 className="mb-0">{exam.title}</h5>
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
                            {currentQ.marks} Marks
                        </span>
                        <h4 className="mb-4">{currentQ.questionText}</h4>
                    </div>

                    {/* Answer Options */}
                    {(currentQ.questionType === 'MULTIPLE_CHOICE' || currentQ.questionType === 'TRUE_FALSE') ? (
                        <div className="list-group">
                            {currentQ.options?.map((option, index) => (
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
                        // Short answer remains the same
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

                    {/* Navigation */}
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
                                Submit Exam
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
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ExamPage;