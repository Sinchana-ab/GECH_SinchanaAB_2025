import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { enrollmentAPI, quizAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const MyCourses = () => {
  const [enrollments, setEnrollments] = useState([]);
  const [courseQuizzes, setCourseQuizzes] = useState({});
  const [courseProgress, setCourseProgress] = useState({});
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchEnrollments();
  }, []);

  const fetchEnrollments = async () => {
    try {
      const response = await enrollmentAPI.getStudentEnrollments(user.id);
      if (response.data.success) {
        const enrollmentData = response.data.data;
        setEnrollments(enrollmentData);
        
        // Fetch quizzes and progress for each course
        enrollmentData.forEach(enrollment => {
          fetchCourseQuizzes(enrollment.courseId);
          fetchCourseProgress(enrollment.courseId);
        });
      }
    } catch (err) {
      console.error('Failed to load enrollments', err);
    } finally {
      setLoading(false);
    }
  };

  const fetchCourseQuizzes = async (courseId) => {
    try {
      const response = await fetch(`/api/quizzes/course/${courseId}`);
      if (response.ok) {
        const data = await response.json();
        setCourseQuizzes(prev => ({
          ...prev,
          [courseId]: data.data || []
        }));
      }
    } catch (err) {
      console.error('Failed to load quizzes for course', courseId, err);
    }
  };

  const fetchCourseProgress = async (courseId) => {
    try {
      const response = await fetch(`/api/progress/student/${user.id}/course/${courseId}`);
      if (response.ok) {
        const data = await response.json();
        setCourseProgress(prev => ({
          ...prev,
          [courseId]: data.data || { materialsViewed: 0, totalMaterials: 0, quizzesCompleted: 0, totalQuizzes: 0 }
        }));
      }
    } catch (err) {
      console.error('Failed to load progress for course', courseId, err);
    }
  };

  const calculateProgress = (courseId) => {
    const progress = courseProgress[courseId];
    if (!progress) return 0;
    
    const materialProgress = progress.totalMaterials > 0 
      ? (progress.materialsViewed / progress.totalMaterials) * 50 
      : 0;
    const quizProgress = progress.totalQuizzes > 0 
      ? (progress.quizzesCompleted / progress.totalQuizzes) * 50 
      : 0;
    
    return Math.round(materialProgress + quizProgress);
  };

  const handleContinueLearning = (enrollment) => {
    navigate(`/courses/${enrollment.courseId}/materials`);
  };

  const handleViewProgress = (enrollment) => {
    navigate(`/student/progress/${enrollment.courseId}`);
  };

  const handleTakeQuiz = (quiz) => {
    navigate(`/quiz/${quiz.id}`);
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary"></div>
        <p className="mt-3">Loading your courses...</p>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2 className="mb-4">My Courses</h2>
      
      {enrollments.length === 0 ? (
        <div className="alert alert-info">
          <h5>No courses enrolled yet</h5>
          <p>Browse available courses and start learning today!</p>
          <button className="btn btn-primary" onClick={() => navigate('/courses')}>
            Browse Courses
          </button>
        </div>
      ) : (
        <div className="row">
          {enrollments.map(enrollment => {
            const progress = calculateProgress(enrollment.courseId);
            const quizzes = courseQuizzes[enrollment.courseId] || [];
            const progressData = courseProgress[enrollment.courseId] || {};
            
            return (
              <div key={enrollment.id} className="col-md-6 col-lg-4 mb-4">
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title">{enrollment.courseName}</h5>
                    <p className="text-muted small">{enrollment.instructorName}</p>
                    
                    {/* Progress Bar */}
                    <div className="mb-3">
                      <div className="d-flex justify-content-between mb-1">
                        <span className="small">Progress</span>
                        <span className="small font-weight-bold">{progress}%</span>
                      </div>
                      <div className="progress" style={{ height: '8px' }}>
                        <div 
                          className="progress-bar bg-success" 
                          role="progressbar" 
                          style={{ width: `${progress}%` }}
                          aria-valuenow={progress} 
                          aria-valuemin="0" 
                          aria-valuemax="100"
                        ></div>
                      </div>
                    </div>
                    
                    {/* Stats */}
                    <div className="d-flex justify-content-between mb-3 small text-muted">
                      <span>
                        📚 Materials: {progressData.materialsViewed || 0}/{progressData.totalMaterials || 0}
                      </span>
                      <span>
                        📝 Quizzes: {progressData.quizzesCompleted || 0}/{progressData.totalQuizzes || 0}
                      </span>
                    </div>
                    
                    {/* Enrollment Date */}
                    <p className="small text-muted">
                      Enrolled: {new Date(enrollment.enrollmentDate).toLocaleDateString()}
                    </p>
                    
                    {/* Action Buttons */}
                    <div className="d-grid gap-2">
                      <button 
                        className="btn btn-primary btn-sm"
                        onClick={() => handleContinueLearning(enrollment)}
                      >
                        Continue Learning
                      </button>
                      <button 
                        className="btn btn-outline-secondary btn-sm"
                        onClick={() => handleViewProgress(enrollment)}
                      >
                        View Detailed Progress
                      </button>
                    </div>
                    
                    {/* Available Quizzes */}
                    {quizzes.length > 0 && (
                      <div className="mt-3">
                        <h6 className="small text-muted">Available Quizzes:</h6>
                        <div className="list-group">
                          {quizzes.slice(0, 3).map(quiz => (
                            <button
                              key={quiz.id}
                              className="list-group-item list-group-item-action small py-2"
                              onClick={() => handleTakeQuiz(quiz)}
                            >
                              {quiz.title}
                            </button>
                          ))}
                          {quizzes.length > 3 && (
                            <small className="text-muted ps-2">
                              +{quizzes.length - 3} more
                            </small>
                          )}
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default MyCourses;