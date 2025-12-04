import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

// ====================================
// ADMIN EXAM MANAGEMENT COMPONENT
// ====================================
const AdminExamManagement = ({ user }) => {
  const navigate = useNavigate();
  const [exams, setExams] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterCourse, setFilterCourse] = useState('all');
  const [stats, setStats] = useState({
    totalExams: 0,
    publishedExams: 0,
    totalQuestions: 0
  });

  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const authHeader = {
        auth: {
          username: user.email,
          password: user.password
        },
        withCredentials: true
      };

      // Fetch all courses
      const coursesRes = await axios.get(`${API_URL}/admin/courses`, authHeader);
      if (coursesRes.data.success) {
        setCourses(coursesRes.data.data);
      }

      // Fetch all exams (you'll need to create this endpoint)
      const examsRes = await axios.get(`${API_URL}/admin/exams`, authHeader);
      if (examsRes.data.success) {
        const examData = examsRes.data.data;
        setExams(examData);
        
        setStats({
          totalExams: examData.length,
          publishedExams: examData.filter(e => e.exam.published).length,
          totalQuestions: examData.reduce((sum, e) => sum + e.questionCount, 0)
        });
      }
    } catch (err) {
      console.error('Failed to load exams:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteExam = async (examId) => {
    if (!window.confirm('Delete this exam? This cannot be undone.')) return;

    try {
      await axios.delete(`${API_URL}/admin/exams/${examId}`, {
        auth: { username: user.email, password: user.password }
      });
      
      alert('Exam deleted successfully');
      fetchData();
    } catch (err) {
      alert('Failed to delete exam');
    }
  };

  const filteredExams = filterCourse === 'all' 
    ? exams 
    : exams.filter(e => e.exam.courseId === parseInt(filterCourse));

  if (loading) {
    return <div className="text-center py-5"><div className="spinner-border"></div></div>;
  }

  return (
    <div className="admin-exam-management">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h4><i className="bi bi-clipboard-check me-2"></i>Exam Management</h4>
          <p className="text-muted mb-0">Manage exams across all courses</p>
        </div>
        <div className="dropdown">
          <button
            className="btn btn-gradient dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
          >
            <i className="bi bi-plus-circle me-2"></i>
            Create Exam
          </button>
          <ul className="dropdown-menu">
            {courses.map(course => (
              <li key={course.id}>
                <button
                  className="dropdown-item"
                  onClick={() => navigate(`/admin/exams/create/${course.id}`)}
                >
                  {course.title}
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="row g-4 mb-4">
        <div className="col-md-4">
          <div className="card text-center">
            <div className="card-body">
              <i className="bi bi-clipboard-check text-primary" style={{ fontSize: '2rem' }}></i>
              <h3 className="mt-2 mb-0">{stats.totalExams}</h3>
              <p className="text-muted mb-0">Total Exams</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center">
            <div className="card-body">
              <i className="bi bi-check-circle text-success" style={{ fontSize: '2rem' }}></i>
              <h3 className="mt-2 mb-0">{stats.publishedExams}</h3>
              <p className="text-muted mb-0">Published</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center">
            <div className="card-body">
              <i className="bi bi-question-circle text-info" style={{ fontSize: '2rem' }}></i>
              <h3 className="mt-2 mb-0">{stats.totalQuestions}</h3>
              <p className="text-muted mb-0">Total Questions</p>
            </div>
          </div>
        </div>
      </div>

      {/* Filter */}
      <div className="card mb-4">
        <div className="card-body">
          <div className="row">
            <div className="col-md-6">
              <label className="form-label">Filter by Course</label>
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
          </div>
        </div>
      </div>

      {/* Exams Table */}
      <div className="card">
        <div className="card-body">
          <div className="table-responsive">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th>Exam Title</th>
                  <th>Course</th>
                  <th>Questions</th>
                  <th>Time Limit</th>
                  <th>Passing Score</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredExams.length === 0 ? (
                  <tr>
                    <td colSpan="7" className="text-center py-4 text-muted">
                      No exams found
                    </td>
                  </tr>
                ) : (
                  filteredExams.map(({ exam, questionCount }) => (
                    <tr key={exam.id}>
                      <td><strong>{exam.title}</strong></td>
                      <td>{exam.courseTitle}</td>
                      <td>{questionCount}</td>
                      <td>{exam.timeLimitMinutes} min</td>
                      <td>{exam.passingScore}%</td>
                      <td>
                        <span className={`badge ${exam.published ? 'bg-success' : 'bg-warning'}`}>
                          {exam.published ? 'Published' : 'Draft'}
                        </span>
                      </td>
                      <td>
                        <div className="btn-group btn-group-sm">
                          <button
                            className="btn btn-outline-primary"
                            onClick={() => navigate(`/admin/exams/edit/${exam.id}`)}
                            title="Edit"
                          >
                            <i className="bi bi-pencil"></i>
                          </button>
                          <button
                            className="btn btn-outline-danger"
                            onClick={() => handleDeleteExam(exam.id)}
                            title="Delete"
                          >
                            <i className="bi bi-trash"></i>
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

// ====================================
// INSTRUCTOR EXAM MANAGEMENT COMPONENT
// ====================================
const InstructorExamManagement = ({ user }) => {
  const navigate = useNavigate();
  const [exams, setExams] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);

  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const authHeader = {
        auth: {
          username: user.email,
          password: user.password
        },
        withCredentials: true
      };

      // Fetch instructor's courses
      const coursesRes = await axios.get(`${API_URL}/instructor/courses`, {
        params: { instructorId: user.id },
        ...authHeader
      });
      
      if (coursesRes.data.success) {
        setCourses(coursesRes.data.data);
      }

      // Fetch instructor's exams
      const examsRes = await axios.get(`${API_URL}/instructor/exams`, {
        params: { instructorId: user.id },
        ...authHeader
      });
      
      if (examsRes.data.success) {
        setExams(examsRes.data.data);
      }
    } catch (err) {
      console.error('Failed to load exams:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center py-5"><div className="spinner-border"></div></div>;
  }

  return (
    <div className="instructor-exam-management">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h4><i className="bi bi-clipboard-check me-2"></i>My Exams</h4>
        <div className="dropdown">
          <button
            className="btn btn-gradient dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
          >
            <i className="bi bi-plus-circle me-2"></i>
            Create Exam
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

      {exams.length === 0 ? (
        <div className="card">
          <div className="card-body text-center py-5">
            <i className="bi bi-inbox" style={{ fontSize: '4rem', color: '#ccc' }}></i>
            <h4 className="mt-3">No Exams Yet</h4>
            <p className="text-muted mb-4">Create your first exam to assess student learning</p>
            {courses.length > 0 && (
              <button
                className="btn btn-gradient"
                onClick={() => navigate(`/instructor/exams/create/${courses[0].id}`)}
              >
                <i className="bi bi-plus-circle me-2"></i>
                Create First Exam
              </button>
            )}
          </div>
        </div>
      ) : (
        <div className="row g-4">
          {exams.map(({ exam, questionCount }) => (
            <div key={exam.id} className="col-md-6 col-lg-4">
              <div className="card h-100">
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

                  <p className="card-text text-muted small mb-3">{exam.description}</p>

                  <div className="d-flex justify-content-between mb-3 small">
                    <span><i className="bi bi-question-circle me-1"></i>{questionCount} Questions</span>
                    <span><i className="bi bi-clock me-1"></i>{exam.timeLimitMinutes} min</span>
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
                      disabled={questionCount === 0}
                      title={questionCount === 0 ? 'Add questions first' : ''}
                    >
                      <i className="bi bi-eye me-1"></i>
                      {exam.published ? 'Unpublish' : 'Publish'}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

// ====================================
// EXPORT COMPONENTS
// ====================================
export { AdminExamManagement, InstructorExamManagement };