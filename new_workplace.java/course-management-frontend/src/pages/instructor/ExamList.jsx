// pages/instructor/ExamList.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import axios from 'axios';
import './ExamList.css';

const ExamList = () => {
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const [exams, setExams] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterCourse, setFilterCourse] = useState('all');
  const [filterStatus, setFilterStatus] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');
  
  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      // Load courses
      const coursesResponse = await axios.get(`${API_URL}/instructor/courses`, {
        params: { instructorId: user.id },
        auth: { username: user.email, password: user.password }
      });
      
      if (coursesResponse.data.success) {
        setCourses(coursesResponse.data.data);
      }

      // Load exams
      const examsResponse = await axios.get(`${API_URL}/instructor/exams`, {
        params: { instructorId: user.id },
        auth: { username: user.email, password: user.password }
      });
      
      if (examsResponse.data.success) {
        setExams(examsResponse.data.data);
      }
    } catch (err) {
      console.error('Failed to load data:', err);
      alert('Failed to load exams');
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteExam = async (examId) => {
    if (!window.confirm('Are you sure you want to delete this exam? This action cannot be undone.')) {
      return;
    }

    try {
      await axios.delete(`${API_URL}/instructor/exams/${examId}`, {
        auth: { username: user.email, password: user.password }
      });
      
      alert('Exam deleted successfully');
      loadData();
    } catch (err) {
      alert('Failed to delete exam: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleTogglePublish = async (examId, currentStatus) => {
    try {
      await axios.put(
        `${API_URL}/instructor/exams/${examId}/publish`,
        { published: !currentStatus },
        { auth: { username: user.email, password: user.password } }
      );
      
      alert(currentStatus ? 'Exam unpublished' : 'Exam published successfully');
      loadData();
    } catch (err) {
      alert('Failed to update exam: ' + (err.response?.data?.message || err.message));
    }
  };

  const getFilteredExams = () => {
    return exams.filter(examData => {
      const exam = examData.exam;
      
      // Course filter
      if (filterCourse !== 'all' && exam.courseId !== parseInt(filterCourse)) {
        return false;
      }
      
      // Status filter
      if (filterStatus === 'published' && !exam.published) {
        return false;
      }
      if (filterStatus === 'draft' && exam.published) {
        return false;
      }
      
      // Search filter
      if (searchTerm) {
        const searchLower = searchTerm.toLowerCase();
        return exam.title.toLowerCase().includes(searchLower) ||
               exam.description.toLowerCase().includes(searchLower);
      }
      
      return true;
    });
  };

  const filteredExams = getFilteredExams();

  if (loading) {
    return (
      <div className="exam-list-container">
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3">Loading exams...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="exam-list-container">
      <div className="exam-list-header">
        <div>
          <h2>
            <i className="bi bi-clipboard-check me-2"></i>
            Exam Management
          </h2>
          <p className="text-muted mb-0">Create and manage course exams</p>
        </div>
        
        <div className="dropdown">
          <button
            className="btn btn-primary dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
          >
            <i className="bi bi-plus-circle me-2"></i>
            Create New Exam
          </button>
          <ul className="dropdown-menu">
            {courses.length === 0 ? (
              <li className="dropdown-item text-muted">No courses available</li>
            ) : (
              courses.map(course => (
                <li key={course.id}>
                  <button
                    className="dropdown-item"
                    onClick={() => navigate(`/instructor/exams/create/${course.id}`)}
                  >
                    {course.title}
                  </button>
                </li>
              ))
            )}
          </ul>
        </div>
      </div>

      {/* Filters */}
      <div className="exam-filters card mb-4">
        <div className="card-body">
          <div className="row g-3">
            <div className="col-md-4">
              <label className="form-label">Search</label>
              <input
                type="text"
                className="form-control"
                placeholder="Search exams..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            
            <div className="col-md-4">
              <label className="form-label">Course</label>
              <select
                className="form-select"
                value={filterCourse}
                onChange={(e) => setFilterCourse(e.target.value)}
              >
                <option value="all">All Courses</option>
                {courses.map(course => (
                  <option key={course.id} value={course.id}>
                    {course.title}
                  </option>
                ))}
              </select>
            </div>
            
            <div className="col-md-4">
              <label className="form-label">Status</label>
              <select
                className="form-select"
                value={filterStatus}
                onChange={(e) => setFilterStatus(e.target.value)}
              >
                <option value="all">All Status</option>
                <option value="published">Published</option>
                <option value="draft">Draft</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      {/* Stats */}
      <div className="row mb-4">
        <div className="col-md-3">
          <div className="stat-card card text-center">
            <div className="card-body">
              <h3 className="mb-0">{exams.length}</h3>
              <p className="text-muted mb-0">Total Exams</p>
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="stat-card card text-center">
            <div className="card-body">
              <h3 className="mb-0 text-success">
                {exams.filter(e => e.exam.published).length}
              </h3>
              <p className="text-muted mb-0">Published</p>
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="stat-card card text-center">
            <div className="card-body">
              <h3 className="mb-0 text-warning">
                {exams.filter(e => !e.exam.published).length}
              </h3>
              <p className="text-muted mb-0">Drafts</p>
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="stat-card card text-center">
            <div className="card-body">
              <h3 className="mb-0 text-info">
                {exams.reduce((sum, e) => sum + e.questionCount, 0)}
              </h3>
              <p className="text-muted mb-0">Total Questions</p>
            </div>
          </div>
        </div>
      </div>

      {/* Exams Grid */}
      {filteredExams.length === 0 ? (
        <div className="card">
          <div className="card-body text-center py-5">
            <i className="bi bi-inbox display-1 text-muted"></i>
            <h4 className="mt-3">No exams found</h4>
            <p className="text-muted">
              {exams.length === 0 
                ? 'Create your first exam to get started'
                : 'Try adjusting your filters'}
            </p>
          </div>
        </div>
      ) : (
        <div className="exams-grid">
          {filteredExams.map(({ exam, questionCount }) => (
            <div key={exam.id} className="exam-card card">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-start mb-3">
                  <div>
                    <h5 className="card-title mb-1">{exam.title}</h5>
                    <small className="text-muted">{exam.courseTitle}</small>
                  </div>
                  <span className={`badge ${exam.published ? 'bg-success' : 'bg-warning'}`}>
                    {exam.published ? 'Published' : 'Draft'}
                  </span>
                </div>

                <p className="card-text text-muted small mb-3">
                  {exam.description}
                </p>

                <div className="exam-stats mb-3">
                  <div className="stat-item">
                    <i className="bi bi-question-circle text-primary"></i>
                    <span>{questionCount} Questions</span>
                  </div>
                  <div className="stat-item">
                    <i className="bi bi-star text-warning"></i>
                    <span>{exam.totalMarks || 0} Marks</span>
                  </div>
                  <div className="stat-item">
                    <i className="bi bi-clock text-info"></i>
                    <span>{exam.timeLimitMinutes} min</span>
                  </div>
                  <div className="stat-item">
                    <i className="bi bi-check-circle text-success"></i>
                    <span>{exam.passingScore}% Pass</span>
                  </div>
                </div>

                <div className="d-flex gap-2">
                  <button
                    className="btn btn-sm btn-outline-primary flex-grow-1"
                    onClick={() => navigate(`/instructor/exams/edit/${exam.id}`)}
                  >
                    <i className="bi bi-pencil me-1"></i>
                    Edit
                  </button>
                  
                  <button
                    className={`btn btn-sm ${exam.published ? 'btn-outline-warning' : 'btn-outline-success'}`}
                    onClick={() => handleTogglePublish(exam.id, exam.published)}
                    disabled={questionCount === 0}
                    title={questionCount === 0 ? 'Add questions before publishing' : ''}
                  >
                    <i className={`bi ${exam.published ? 'bi-eye-slash' : 'bi-check-circle'} me-1`}></i>
                    {exam.published ? 'Unpublish' : 'Publish'}
                  </button>
                  
                  <button
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDeleteExam(exam.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ExamList;