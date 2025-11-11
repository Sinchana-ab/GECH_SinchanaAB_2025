import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const DetailedProgress = () => {
  const { courseId } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();
  
  const [progress, setProgress] = useState(null);
  const [materials, setMaterials] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const [viewedMaterials, setViewedMaterials] = useState(new Set());
  const [quizAttempts, setQuizAttempts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAllData();
  }, [courseId]);

  const fetchAllData = async () => {
    try {
      await Promise.all([
        fetchProgress(),
        fetchMaterials(),
        fetchQuizzes(),
        fetchViewedMaterials(),
        fetchQuizAttempts()
      ]);
    } finally {
      setLoading(false);
    }
  };

  const fetchProgress = async () => {
    try {
      const response = await fetch(
        `/api/progress/student/${user.id}/course/${courseId}`
      );
      const data = await response.json();
      if (data.success) {
        setProgress(data.data);
      }
    } catch (err) {
      console.error('Failed to fetch progress', err);
    }
  };

  const fetchMaterials = async () => {
    try {
      const response = await fetch(`/api/materials/course/${courseId}`);
      const data = await response.json();
      if (data.success) {
        setMaterials(data.data || []);
      }
    } catch (err) {
      console.error('Failed to fetch materials', err);
    }
  };

  const fetchQuizzes = async () => {
    try {
      const response = await fetch(`/api/quizzes/course/${courseId}`);
      const data = await response.json();
      if (data.success) {
        setQuizzes(data.data || []);
      }
    } catch (err) {
      console.error('Failed to fetch quizzes', err);
    }
  };

  const fetchViewedMaterials = async () => {
    try {
      const response = await fetch(
        `/api/progress/student/${user.id}/course/${courseId}/materials`
      );
      const data = await response.json();
      if (data.success) {
        setViewedMaterials(new Set(data.data));
      }
    } catch (err) {
      console.error('Failed to fetch viewed materials', err);
    }
  };

  const fetchQuizAttempts = async () => {
    try {
      const response = await fetch(`/api/quiz-attempts/student/${user.id}`);
      const data = await response.json();
      if (data.success) {
        // Filter attempts for this course
        const courseAttempts = data.data.filter(
          attempt => attempt.quiz.courseId === parseInt(courseId)
        );
        setQuizAttempts(courseAttempts);
      }
    } catch (err) {
      console.error('Failed to fetch quiz attempts', err);
    }
  };

  const getQuizStatus = (quiz) => {
    const attempts = quizAttempts.filter(a => a.quizId === quiz.id);
    if (attempts.length === 0) return { status: 'not-started', color: 'secondary', text: 'Not Started' };
    
    const bestScore = Math.max(...attempts.map(a => a.score));
    const passed = bestScore >= quiz.passingScore;
    
    return passed 
      ? { status: 'passed', color: 'success', text: `Passed (${bestScore}%)` }
      : { status: 'failed', color: 'warning', text: `Best: ${bestScore}%` };
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary"></div>
        <p className="mt-3">Loading progress...</p>
      </div>
    );
  }

  if (!progress) {
    return (
      <div className="container mt-4">
        <div className="alert alert-warning">Failed to load progress data.</div>
      </div>
    );
  }

  const overallProgress = progress.totalMaterials + progress.totalQuizzes > 0
    ? Math.round(
        ((progress.materialsViewed + progress.quizzesCompleted) / 
        (progress.totalMaterials + progress.totalQuizzes)) * 100
      )
    : 0;

  return (
    <div className="container mt-4">
      <button 
        className="btn btn-outline-secondary mb-3"
        onClick={() => navigate('/student/courses')}
      >
        ← Back to My Courses
      </button>

      <h2 className="mb-4">Course Progress</h2>

      {/* Overall Progress Card */}
      <div className="card shadow-sm mb-4">
        <div className="card-body">
          <h5 className="card-title">Overall Progress</h5>
          <div className="row">
            <div className="col-md-8">
              <div className="progress mb-3" style={{ height: '30px' }}>
                <div
                  className="progress-bar bg-success"
                  style={{ width: `${overallProgress}%` }}
                >
                  <strong>{overallProgress}%</strong>
                </div>
              </div>
            </div>
            <div className="col-md-4 text-end">
              {progress.lastActivityDate && (
                <small className="text-muted">
                  Last activity: {new Date(progress.lastActivityDate).toLocaleDateString()}
                </small>
              )}
            </div>
          </div>

          <div className="row mt-3">
            <div className="col-md-3">
              <div className="text-center p-3 border rounded">
                <h3 className="text-primary">{progress.materialsViewed}/{progress.totalMaterials}</h3>
                <small className="text-muted">Materials Completed</small>
              </div>
            </div>
            <div className="col-md-3">
              <div className="text-center p-3 border rounded">
                <h3 className="text-success">{progress.quizzesCompleted}/{progress.totalQuizzes}</h3>
                <small className="text-muted">Quizzes Passed</small>
              </div>
            </div>
            <div className="col-md-3">
              <div className="text-center p-3 border rounded">
                <h3 className="text-info">{progress.averageQuizScore.toFixed(1)}%</h3>
                <small className="text-muted">Average Quiz Score</small>
              </div>
            </div>
            <div className="col-md-3">
              <div className="text-center p-3 border rounded">
                <h3 className="text-warning">{quizAttempts.length}</h3>
                <small className="text-muted">Total Attempts</small>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Materials Progress */}
      <div className="card shadow-sm mb-4">
        <div className="card-header bg-primary text-white">
          <h5 className="mb-0">📚 Course Materials</h5>
        </div>
        <div className="card-body">
          {materials.length === 0 ? (
            <p className="text-muted">No materials available</p>
          ) : (
            <div className="list-group">
              {materials.map((material, index) => (
                <div
                  key={material.id}
                  className="list-group-item d-flex justify-content-between align-items-center"
                >
                  <div>
                    <strong>{index + 1}. {material.title}</strong>
                    <br />
                    <small className="text-muted">{material.type}</small>
                  </div>
                  {viewedMaterials.has(material.id) ? (
                    <span className="badge bg-success">✓ Completed</span>
                  ) : (
                    <span className="badge bg-secondary">Not Viewed</span>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* Quiz Progress */}
      <div className="card shadow-sm mb-4">
        <div className="card-header bg-success text-white">
          <h5 className="mb-0">📝 Quizzes</h5>
        </div>
        <div className="card-body">
          {quizzes.length === 0 ? (
            <p className="text-muted">No quizzes available</p>
          ) : (
            <div className="list-group">
              {quizzes.map((quiz, index) => {
                const status = getQuizStatus(quiz);
                const attempts = quizAttempts.filter(a => a.quizId === quiz.id);
                
                return (
                  <div
                    key={quiz.id}
                    className="list-group-item"
                  >
                    <div className="d-flex justify-content-between align-items-start">
                      <div className="flex-grow-1">
                        <strong>{index + 1}. {quiz.title}</strong>
                        <br />
                        <small className="text-muted">
                          Passing Score: {quiz.passingScore}% | 
                          Attempts: {attempts.length}
                        </small>
                      </div>
                      <span className={`badge bg-${status.color}`}>
                        {status.text}
                      </span>
                    </div>
                    
                    {attempts.length > 0 && (
                      <div className="mt-2">
                        <small className="text-muted">Recent attempts:</small>
                        <div className="d-flex gap-2 mt-1">
                          {attempts.slice(-3).map((attempt, i) => (
                            <span
                              key={i}
                              className={`badge ${
                                attempt.score >= quiz.passingScore
                                  ? 'bg-success'
                                  : 'bg-danger'
                              }`}
                            >
                              {attempt.score}%
                            </span>
                          ))}
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default DetailedProgress;