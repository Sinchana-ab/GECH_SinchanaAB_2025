// pages/instructor/ExamCreator.jsx
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import axios from 'axios';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import './ExamCreator.css';

const ExamCreator = () => {
  const { courseId, examId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const [examData, setExamData] = useState({
    title: '',
    description: '',
    timeLimitMinutes: 60,
    passingScore: 50,
    maxAttempts: 3
  });
  
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState({
    questionText: '',
    questionType: 'MULTIPLE_CHOICE',
    options: ['', '', '', ''],
    correctAnswer: '',
    marks: 10,
    explanation: ''
  });
  
  const [editingQuestionId, setEditingQuestionId] = useState(null);
  const [showPreview, setShowPreview] = useState(false);
  const [saving, setSaving] = useState(false);
  const [activeTab, setActiveTab] = useState('details');
  
  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    if (examId) {
      loadExam();
    }
  }, [examId]);

  const loadExam = async () => {
    try {
      const response = await axios.get(`${API_URL}/instructor/exams/${examId}`, {
        auth: { username: user.email, password: user.password }
      });
      
      if (response.data.success) {
        const { exam, questions: loadedQuestions } = response.data.data;
        setExamData({
          title: exam.title,
          description: exam.description,
          timeLimitMinutes: exam.timeLimitMinutes,
          passingScore: exam.passingScore,
          maxAttempts: exam.maxAttempts
        });
        setQuestions(loadedQuestions);
      }
    } catch (err) {
      console.error('Failed to load exam:', err);
      alert('Failed to load exam details');
    }
  };

  const handleSaveExam = async () => {
    if (!examData.title || !examData.description) {
      alert('Please fill in exam title and description');
      return;
    }

    setSaving(true);
    try {
      const payload = {
        ...examData,
        courseId,
        instructorId: user.id
      };

      let response;
      if (examId) {
        response = await axios.put(`${API_URL}/instructor/exams/${examId}`, payload, {
          auth: { username: user.email, password: user.password }
        });
      } else {
        response = await axios.post(`${API_URL}/instructor/exams`, payload, {
          auth: { username: user.email, password: user.password }
        });
      }

      if (response.data.success) {
        const savedExamId = response.data.data.id;
        alert('Exam details saved successfully!');
        
        if (!examId) {
          navigate(`/instructor/exams/edit/${savedExamId}`);
        }
      }
    } catch (err) {
      alert('Failed to save exam: ' + (err.response?.data?.message || err.message));
    } finally {
      setSaving(false);
    }
  };
// --- Required State Variables (Ensure these exist in your component) ---
// const [examId, setExamId] = useState(null); // Set after saving exam details
// const [questions, setQuestions] = useState([]); // List of questions for the exam
// const [currentQuestion, setCurrentQuestion] = useState({ 
//   questionText: '', 
//   questionType: 'MULTIPLE_CHOICE', 
//   options: ['', '', '', ''], 
//   correctAnswer: '', 
//   marks: 1, 
//   explanation: ''
// });
// const API_URL = 'http://localhost:8080/api';
// const { user } = useAuth(); // For authentication header

// --- CORRECTED handleAddQuestion Function ---

const handleAddQuestion = async () => {
    // 1. Basic Validation
    if (!currentQuestion.questionText || currentQuestion.questionText.trim() === '') {
        alert('Please enter a question text.');
        return;
    }
    
    // Check if exam details have been saved (i.e., we have an ID to associate the question with)
    if (!examId) {
        alert('Please save the exam details first before adding questions.');
        return;
    }
    
    // Option and Answer Validation based on type
    if (currentQuestion.questionType === 'MULTIPLE_CHOICE' || currentQuestion.questionType === 'TRUE_FALSE') {
        // Ensure options exist and a correct answer is selected
        if (!currentQuestion.options.every(opt => opt.trim() !== '') || !currentQuestion.correctAnswer) {
             alert('Please fill out all options and select a correct answer.');
             return;
        }
    } else if (currentQuestion.questionType === 'SHORT_ANSWER') {
        // Ensure short answer has a correct answer (key)
        if (!currentQuestion.correctAnswer || currentQuestion.correctAnswer.trim() === '') {
            alert('Please provide the correct answer for the short answer question.');
            return;
        }
    }

    try {
        // 2. API Call to Save Question
        // NOTE: The request must include the authentication header
        const authHeader = { auth: { username: user.email, password: user.password } };
        
        const payload = {
            ...currentQuestion,
            // Ensure question is linked to the exam
            examId: examId,
            // Calculate orderIndex as the next available index
            orderIndex: questions.length + 1
        };

        // Use the existing question ID for editing, otherwise it's a new question
        const isEditing = currentQuestion.id;
        let response;
        
        if (isEditing) {
            // EDITING existing question
            response = await axios.put(
                `${API_URL}/instructor/exams/${examId}/questions/${currentQuestion.id}`, 
                payload, 
                authHeader
            );
        } else {
            // ADDING new question
            response = await axios.post(
                `${API_URL}/instructor/exams/${examId}/questions`, 
                payload, 
                authHeader
            );
        }

        if (response.data.success) {
            const savedQuestion = response.data.data;
            
            // 3. Update the local questions state
            if (isEditing) {
                // Replace the old version with the new one
                setQuestions(questions.map(q => q.id === savedQuestion.id ? savedQuestion : q));
                alert('Question updated successfully!');
            } else {
                // Add the new question to the list
                setQuestions([...questions, savedQuestion]);
                alert('Question added successfully!');
            }

            // 4. Reset form for next question
            setCurrentQuestion({
                id: null, // Reset editing ID
                questionText: '',
                questionType: 'MULTIPLE_CHOICE',
                options: ['', '', '', ''],
                correctAnswer: '',
                marks: 1,
                explanation: ''
            });
            setEditingQuestionId(null); // Assuming you have this state
        }
        
    } catch (err) {
        console.error('Failed to save question:', err);
        alert('Failed to save question: ' + (err.response?.data?.message || err.message));
    }
};
  // const handleAddQuestion = async () => {
  //   if (!currentQuestion.questionText) {
  //     alert('Please enter a question');
  //     return;
  //   }

  //   if (!currentQuestion.correctAnswer) {
  //     alert('Please specify the correct answer');
  //     return;
  //   }

  //   try {
  //     const payload = {
  //       ...currentQuestion,
  //       options: currentQuestion.questionType !== 'SHORT_ANSWER' 
  //         ? JSON.stringify(currentQuestion.options.filter(o => o.trim())) 
  //         : null,
  //       orderIndex: questions.length + 1,
  //       examId: examId
  //     };

  //     let response;
  //     if (editingQuestionId) {
  //       response = await axios.put(
  //         `${API_URL}/instructor/exams/questions/${editingQuestionId}`, 
  //         payload,
  //         { auth: { username: user.email, password: user.password } }
  //       );
  //     } else {
  //       response = await axios.post(
  //         `${API_URL}/instructor/exams/${examId}/questions`, 
  //         payload,
  //         { auth: { username: user.email, password: user.password } }
  //       );
  //     }

  //     if (response.data.success) {
  //       if (editingQuestionId) {
  //         setQuestions(questions.map(q => 
  //           q.id === editingQuestionId ? response.data.data : q
  //         ));
  //       } else {
  //         setQuestions([...questions, response.data.data]);
  //       }
        
  //       resetQuestionForm();
  //       alert(editingQuestionId ? 'Question updated!' : 'Question added!');
  //     }
  //   } catch (err) {
  //     alert('Failed to save question: ' + (err.response?.data?.message || err.message));
  //   }
  // };

  const handleEditQuestion = (question) => {
    setCurrentQuestion({
      questionText: question.questionText,
      questionType: question.questionType,
      options: question.options ? JSON.parse(question.options) : ['', '', '', ''],
      correctAnswer: question.correctAnswer,
      marks: question.marks,
      explanation: question.explanation || ''
    });
    setEditingQuestionId(question.id);
    setActiveTab('questions');
  };

  const handleDeleteQuestion = async (questionId) => {
    if (!window.confirm('Delete this question?')) return;

    try {
      await axios.delete(
        `${API_URL}/instructor/exams/questions/${questionId}`,
        { auth: { username: user.email, password: user.password } }
      );
      
      setQuestions(questions.filter(q => q.id !== questionId));
      alert('Question deleted');
    } catch (err) {
      alert('Failed to delete question');
    }
  };

  const handlePublish = async () => {
    if (questions.length === 0) {
      alert('Please add at least one question before publishing');
      return;
    }

    try {
      await axios.put(
        `${API_URL}/instructor/exams/${examId}/publish`,
        { published: true },
        { auth: { username: user.email, password: user.password } }
      );
      
      alert('Exam published successfully!');
      navigate('/instructor/exams');
    } catch (err) {
      alert('Failed to publish exam');
    }
  };

  const resetQuestionForm = () => {
    setCurrentQuestion({
      questionText: '',
      questionType: 'MULTIPLE_CHOICE',
      options: ['', '', '', ''],
      correctAnswer: '',
      marks: 10,
      explanation: ''
    });
    setEditingQuestionId(null);
  };

  const handleOptionChange = (index, value) => {
    const newOptions = [...currentQuestion.options];
    newOptions[index] = value;
    setCurrentQuestion({ ...currentQuestion, options: newOptions });
  };

  const addOption = () => {
    setCurrentQuestion({
      ...currentQuestion,
      options: [...currentQuestion.options, '']
    });
  };

  const removeOption = (index) => {
    const newOptions = currentQuestion.options.filter((_, i) => i !== index);
    setCurrentQuestion({ ...currentQuestion, options: newOptions });
  };

  const getTotalMarks = () => {
    return questions.reduce((sum, q) => sum + (q.marks || 0), 0);
  };

  const onDragEnd = (result) => {
    if (!result.destination) return;

    const items = Array.from(questions);
    const [reorderedItem] = items.splice(result.source.index, 1);
    items.splice(result.destination.index, 0, reorderedItem);

    setQuestions(items);
  };

  return (
    <div className="exam-creator-container">
      <div className="exam-creator-header">
        <button 
          className="btn btn-outline-secondary"
          onClick={() => navigate(-1)}
        >
          <i className="bi bi-arrow-left me-2"></i>
          Back
        </button>
        <h2>
          <i className="bi bi-pencil-square me-2"></i>
          {examId ? 'Edit Exam' : 'Create New Exam'}
        </h2>
        <div className="d-flex gap-2">
          {examId && questions.length > 0 && (
            <button 
              className="btn btn-success"
              onClick={handlePublish}
            >
              <i className="bi bi-check-circle me-2"></i>
              Publish Exam
            </button>
          )}
        </div>
      </div>

      {/* Tabs */}
      <div className="exam-creator-tabs">
        <button
          className={`tab-button ${activeTab === 'details' ? 'active' : ''}`}
          onClick={() => setActiveTab('details')}
        >
          <i className="bi bi-info-circle me-2"></i>
          Exam Details
        </button>
        <button
          className={`tab-button ${activeTab === 'questions' ? 'active' : ''}`}
          onClick={() => setActiveTab('questions')}
          disabled={!examId}
        >
          <i className="bi bi-question-circle me-2"></i>
          Questions ({questions.length})
        </button>
        <button
          className={`tab-button ${activeTab === 'preview' ? 'active' : ''}`}
          onClick={() => setActiveTab('preview')}
          disabled={questions.length === 0}
        >
          <i className="bi bi-eye me-2"></i>
          Preview
        </button>
      </div>

      <div className="exam-creator-content">
        {/* EXAM DETAILS TAB */}
        {activeTab === 'details' && (
          <div className="card">
            <div className="card-body">
              <h5 className="mb-4">Exam Configuration</h5>
              
              <div className="mb-3">
                <label className="form-label">Exam Title *</label>
                <input
                  type="text"
                  className="form-control"
                  value={examData.title}
                  onChange={(e) => setExamData({ ...examData, title: e.target.value })}
                  placeholder="e.g., Java Programming Final Exam"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Description *</label>
                <textarea
                  className="form-control"
                  rows="3"
                  value={examData.description}
                  onChange={(e) => setExamData({ ...examData, description: e.target.value })}
                  placeholder="Brief description of the exam content..."
                />
              </div>

              <div className="row">
                <div className="col-md-4 mb-3">
                  <label className="form-label">Time Limit (minutes)</label>
                  <input
                    type="number"
                    className="form-control"
                    value={examData.timeLimitMinutes}
                    onChange={(e) => setExamData({ ...examData, timeLimitMinutes: parseInt(e.target.value) })}
                    min="10"
                  />
                </div>

                <div className="col-md-4 mb-3">
                  <label className="form-label">Passing Score (%)</label>
                  <input
                    type="number"
                    className="form-control"
                    value={examData.passingScore}
                    onChange={(e) => setExamData({ ...examData, passingScore: parseInt(e.target.value) })}
                    min="0"
                    max="100"
                  />
                </div>

                <div className="col-md-4 mb-3">
                  <label className="form-label">Max Attempts</label>
                  <input
                    type="number"
                    className="form-control"
                    value={examData.maxAttempts}
                    onChange={(e) => setExamData({ ...examData, maxAttempts: parseInt(e.target.value) })}
                    min="1"
                  />
                </div>
              </div>

              <button
                className="btn btn-primary"
                onClick={handleSaveExam}
                disabled={saving}
              >
                {saving ? 'Saving...' : examId ? 'Update Exam Details' : 'Create Exam & Add Questions'}
              </button>
            </div>
          </div>
        )}

        {/* QUESTIONS TAB */}
        {activeTab === 'questions' && (
          <div className="row">
            <div className="col-lg-5">
              <div className="card mb-3">
                <div className="card-body">
                  <h5 className="mb-3">
                    {editingQuestionId ? 'Edit Question' : 'Add Question'}
                  </h5>

                  <div className="mb-3">
                    <label className="form-label">Question Type</label>
                    <select
                      className="form-select"
                      value={currentQuestion.questionType}
                      onChange={(e) => setCurrentQuestion({ 
                        ...currentQuestion, 
                        questionType: e.target.value,
                        options: e.target.value === 'TRUE_FALSE' ? ['True', 'False'] : ['', '', '', ''],
                        correctAnswer: ''
                      })}
                    >
                      <option value="MULTIPLE_CHOICE">Multiple Choice</option>
                      <option value="TRUE_FALSE">True/False</option>
                      <option value="SHORT_ANSWER">Short Answer</option>
                    </select>
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Question Text *</label>
                    <textarea
                      className="form-control"
                      rows="3"
                      value={currentQuestion.questionText}
                      onChange={(e) => setCurrentQuestion({ ...currentQuestion, questionText: e.target.value })}
                      placeholder="Enter your question here..."
                    />
                  </div>

                  {currentQuestion.questionType !== 'SHORT_ANSWER' && (
                    <div className="mb-3">
                      <label className="form-label">Options</label>
                      {currentQuestion.options.map((option, index) => (
                        <div key={index} className="input-group mb-2">
                          <input
                            type="text"
                            className="form-control"
                            value={option}
                            onChange={(e) => handleOptionChange(index, e.target.value)}
                            placeholder={`Option ${index + 1}`}
                            disabled={currentQuestion.questionType === 'TRUE_FALSE'}
                          />
                          {currentQuestion.questionType !== 'TRUE_FALSE' && currentQuestion.options.length > 2 && (
                            <button
                              className="btn btn-outline-danger"
                              onClick={() => removeOption(index)}
                            >
                              <i className="bi bi-x"></i>
                            </button>
                          )}
                        </div>
                      ))}
                      {currentQuestion.questionType !== 'TRUE_FALSE' && (
                        <button
                          className="btn btn-sm btn-outline-primary"
                          onClick={addOption}
                        >
                          <i className="bi bi-plus-circle me-2"></i>
                          Add Option
                        </button>
                      )}
                    </div>
                  )}

                  <div className="mb-3">
                    <label className="form-label">Correct Answer *</label>
                    {currentQuestion.questionType === 'SHORT_ANSWER' ? (
                      <input
                        type="text"
                        className="form-control"
                        value={currentQuestion.correctAnswer}
                        onChange={(e) => setCurrentQuestion({ ...currentQuestion, correctAnswer: e.target.value })}
                        placeholder="Enter the correct answer"
                      />
                    ) : (
                      <select
                        className="form-select"
                        value={currentQuestion.correctAnswer}
                        onChange={(e) => setCurrentQuestion({ ...currentQuestion, correctAnswer: e.target.value })}
                      >
                        <option value="">Select correct answer</option>
                        {currentQuestion.options.filter(o => o.trim()).map((option, index) => (
                          <option key={index} value={option}>
                            {option}
                          </option>
                        ))}
                      </select>
                    )}
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Marks</label>
                    <input
                      type="number"
                      className="form-control"
                      value={currentQuestion.marks}
                      onChange={(e) => setCurrentQuestion({ ...currentQuestion, marks: parseInt(e.target.value) })}
                      min="1"
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Explanation (Optional)</label>
                    <textarea
                      className="form-control"
                      rows="2"
                      value={currentQuestion.explanation}
                      onChange={(e) => setCurrentQuestion({ ...currentQuestion, explanation: e.target.value })}
                      placeholder="Provide an explanation for the correct answer..."
                    />
                  </div>

                  <div className="d-flex gap-2">
                    <button
                      className="btn btn-primary flex-grow-1"
                      onClick={handleAddQuestion}
                    >
                      <i className={`bi ${editingQuestionId ? 'bi-check-circle' : 'bi-plus-circle'} me-2`}></i>
                      {editingQuestionId ? 'Update Question' : 'Add Question'}
                    </button>
                    {editingQuestionId && (
                      <button
                        className="btn btn-outline-secondary"
                        onClick={resetQuestionForm}
                      >
                        Cancel
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-7">
              <div className="card">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-center mb-3">
                    <h5 className="mb-0">
                      Questions List ({questions.length})
                    </h5>
                    <div className="badge bg-info">
                      Total Marks: {getTotalMarks()}
                    </div>
                  </div>

                  {questions.length === 0 ? (
                    <div className="text-center py-5 text-muted">
                      <i className="bi bi-inbox display-4"></i>
                      <p className="mt-3">No questions added yet</p>
                    </div>
                  ) : (
                    <DragDropContext onDragEnd={onDragEnd}>
                      <Droppable droppableId="questions">
                        {(provided) => (
                          <div
                            {...provided.droppableProps}
                            ref={provided.innerRef}
                            className="questions-list"
                          >
                            {questions.map((question, index) => (
                              <Draggable
                                key={question.id}
                                draggableId={question.id.toString()}
                                index={index}
                              >
                                {(provided) => (
                                  <div
                                    ref={provided.innerRef}
                                    {...provided.draggableProps}
                                    {...provided.dragHandleProps}
                                    className="question-item mb-3 p-3 border rounded bg-light"
                                  >
                                    <div className="d-flex justify-content-between align-items-start">
                                      <div className="flex-grow-1">
                                        <div className="d-flex align-items-center gap-2 mb-2">
                                          <i className="bi bi-grip-vertical text-muted"></i>
                                          <span className="badge bg-secondary">Q{index + 1}</span>
                                          <span className="badge bg-primary">{question.questionType}</span>
                                          <span className="badge bg-success">{question.marks} marks</span>
                                        </div>
                                        <p className="mb-2 fw-bold">{question.questionText}</p>
                                        {question.options && (
                                          <ul className="small text-muted mb-0">
                                            {JSON.parse(question.options).map((opt, i) => (
                                              <li key={i} className={opt === question.correctAnswer ? 'text-success fw-bold' : ''}>
                                                {opt}
                                                {opt === question.correctAnswer && ' ✓'}
                                              </li>
                                            ))}
                                          </ul>
                                        )}
                                        {question.questionType === 'SHORT_ANSWER' && (
                                          <p className="small text-success mb-0">
                                            Answer: {question.correctAnswer}
                                          </p>
                                        )}
                                      </div>
                                      <div className="d-flex gap-2">
                                        <button
                                          className="btn btn-sm btn-outline-primary"
                                          onClick={() => handleEditQuestion(question)}
                                        >
                                          <i className="bi bi-pencil"></i>
                                        </button>
                                        <button
                                          className="btn btn-sm btn-outline-danger"
                                          onClick={() => handleDeleteQuestion(question.id)}
                                        >
                                          <i className="bi bi-trash"></i>
                                        </button>
                                      </div>
                                    </div>
                                  </div>
                                )}
                              </Draggable>
                            ))}
                            {provided.placeholder}
                          </div>
                        )}
                      </Droppable>
                    </DragDropContext>
                  )}
                </div>
              </div>
            </div>
          </div>
        )}

        {/* PREVIEW TAB */}
        {activeTab === 'preview' && (
          <div className="card">
            <div className="card-body">
              <div className="exam-preview">
                <div className="text-center mb-4">
                  <h3>{examData.title}</h3>
                  <p className="text-muted">{examData.description}</p>
                  <div className="d-flex justify-content-center gap-4 mt-3">
                    <div>
                      <i className="bi bi-clock me-2"></i>
                      {examData.timeLimitMinutes} minutes
                    </div>
                    <div>
                      <i className="bi bi-star me-2"></i>
                      Total Marks: {getTotalMarks()}
                    </div>
                    <div>
                      <i className="bi bi-check-circle me-2"></i>
                      Passing: {examData.passingScore}%
                    </div>
                  </div>
                </div>

                <hr />

                {questions.map((question, index) => (
                  <div key={question.id} className="preview-question mb-4 p-3 border rounded">
                    <div className="d-flex justify-content-between align-items-start mb-3">
                      <h5>Question {index + 1}</h5>
                      <span className="badge bg-primary">{question.marks} marks</span>
                    </div>
                    
                    <p className="fw-bold mb-3">{question.questionText}</p>

                    {question.questionType === 'MULTIPLE_CHOICE' && (
                      <div className="options-preview">
                        {JSON.parse(question.options).map((option, i) => (
                          <div key={i} className="form-check mb-2">
                            <input
                              className="form-check-input"
                              type="radio"
                              name={`question-${question.id}`}
                              disabled
                            />
                            <label className="form-check-label">
                              {option}
                            </label>
                          </div>
                        ))}
                      </div>
                    )}

                    {question.questionType === 'TRUE_FALSE' && (
                      <div className="options-preview">
                        <div className="form-check mb-2">
                          <input className="form-check-input" type="radio" disabled />
                          <label className="form-check-label">True</label>
                        </div>
                        <div className="form-check mb-2">
                          <input className="form-check-input" type="radio" disabled />
                          <label className="form-check-label">False</label>
                        </div>
                      </div>
                    )}

                    {question.questionType === 'SHORT_ANSWER' && (
                      <textarea
                        className="form-control"
                        rows="3"
                        placeholder="Student will type their answer here..."
                        disabled
                      />
                    )}

                    {question.explanation && (
                      <div className="alert alert-info mt-3 mb-0">
                        <strong>Explanation:</strong> {question.explanation}
                      </div>
                    )}
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ExamCreator;