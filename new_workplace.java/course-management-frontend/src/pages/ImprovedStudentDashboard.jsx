
import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { enrollmentAPI, certificateAPI, courseAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import StudentSidebar from '../components/StudentSidebar';
import './StudentDashboard.css';

const ImprovedStudentDashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const getActiveTab = () => {
    const path = location.pathname;
    if (path === '/student' || path === '/student/' || path === '/dashboard') return 'overview';
    if (path.includes('/my-courses')) return 'my-courses';
    if (path.includes('/browse')) return 'browse';
    if (path.includes('/certificates')) return 'certificates';
    if (path.includes('/progress')) return 'progress';
    if (path.includes('/settings')) return 'settings';
    return 'overview';
  };

  const [activeTab, setActiveTab] = useState(getActiveTab());
  const [enrollments, setEnrollments] = useState([]);
  const [certificates, setCertificates] = useState([]);
  const [availableCourses, setAvailableCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalEnrolled: 0,
    completed: 0,
    inProgress: 0,
    certificates: 0
  });

  useEffect(() => {
    setActiveTab(getActiveTab());
  }, [location.pathname]);

  useEffect(() => {
    if (user) {
      fetchData();
    }
  }, [user]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [enrollmentsRes, coursesRes] = await Promise.all([
        enrollmentAPI.getStudentEnrollments(user.id),
        courseAPI.getAllPublished()
      ]);

      if (enrollmentsRes.data.success) {
        const enrollmentData = enrollmentsRes.data.data;
        setEnrollments(enrollmentData);
        
        setStats({
          totalEnrolled: enrollmentData.length,
          completed: enrollmentData.filter(e => e.status === 'COMPLETED').length,
          inProgress: enrollmentData.filter(e => e.status === 'IN_PROGRESS').length,
          certificates: enrollmentData.filter(e => e.certificateIssued).length
        });
      }

      if (coursesRes.data.success) {
        setAvailableCourses(coursesRes.data.data);
      }
    } catch (err) {
      console.error('Failed to fetch data', err);
    } finally {
      setLoading(false);
    }
  };

  const handleContinueLearning = (enrollment) => {
    navigate(`/courses/${enrollment.courseId}/materials`);
  };

  const handleViewCourse = (courseId) => {
    navigate(`/courses/${courseId}`);
  };

  const handleEnroll = async (courseId) => {
    try {
      const response = await enrollmentAPI.enrollInCourse(user.id, courseId);
      if (response.data.success) {
        alert('Successfully enrolled!');
        fetchData();
        navigate('/student/my-courses');
      }
    } catch (err) {
      alert(err.response?.data?.message || 'Enrollment failed');
    }
  };

  if (loading) {
    return (
      <div className="student-layout">
        <StudentSidebar />
        <div className="student-content">
          <div className="text-center py-5">
            <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }}></div>
            <p className="mt-3">Loading dashboard...</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="student-layout">
      <StudentSidebar />
      
      <div className="student-content">
        <div className="page-header mb-4">
          <h2 className="page-title">
            <i className="bi bi-mortarboard me-2"></i>
            Student Dashboard
          </h2>
          <p className="text-muted">Welcome back, {user?.name}!</p>
        </div>

        {/* ===== OVERVIEW TAB ===== */}
        {activeTab === 'overview' && (
          <div className="fade-in">
            {/* Stats Cards */}
            <div className="row g-4 mb-4">
              <div className="col-md-3">
                <div className="stats-card gradient-primary">
                  <div className="stats-icon">
                    <i className="bi bi-book-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalEnrolled}</div>
                    <div className="stats-label">Enrolled Courses</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-info">
                  <div className="stats-icon">
                    <i className="bi bi-clock-history"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.inProgress}</div>
                    <div className="stats-label">In Progress</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-success">
                  <div className="stats-icon">
                    <i className="bi bi-check-circle-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.completed}</div>
                    <div className="stats-label">Completed</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-warning">
                  <div className="stats-icon">
                    <i className="bi bi-award-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.certificates}</div>
                    <div className="stats-label">Certificates</div>
                  </div>
                </div>
              </div>
            </div>

            {/* Continue Learning Section */}
            {enrollments.filter(e => e.status === 'IN_PROGRESS' || e.status === 'ENROLLED').length > 0 && (
              <div className="modern-card mb-4">
                <h5 className="mb-3">
                  <i className="bi bi-play-circle me-2"></i>
                  Continue Learning
                </h5>
                <div className="row g-3">
                  {enrollments
                    .filter(e => e.status === 'IN_PROGRESS' || e.status === 'ENROLLED')
                    .slice(0, 3)
                    .map(enrollment => (
                      <div key={enrollment.id} className="col-md-4">
                        <div className="course-card-mini">
                          <h6 className="mb-2">{enrollment.courseTitle}</h6>
                          <div className="mb-2">
                            <div className="d-flex justify-content-between mb-1">
                              <small className="text-muted">Progress</small>
                              <small className="fw-bold">{enrollment.progress}%</small>
                            </div>
                            <div className="modern-progress">
                              <div 
                                className="modern-progress-bar" 
                                style={{ width: `${enrollment.progress}%` }}
                              ></div>
                            </div>
                          </div>
                          <button
                            className="btn btn-gradient btn-sm w-100"
                            onClick={() => handleContinueLearning(enrollment)}
                          >
                            <i className="bi bi-play-fill me-1"></i>
                            Continue Learning
                          </button>
                        </div>
                      </div>
                    ))}
                </div>
              </div>
            )}

            {/* Recent Achievements */}
            {stats.certificates > 0 && (
              <div className="modern-card mb-4">
                <h5 className="mb-3">
                  <i className="bi bi-trophy me-2"></i>
                  Recent Achievements
                </h5>
                <div className="d-flex gap-3">
                  {enrollments
                    .filter(e => e.certificateIssued)
                    .slice(0, 3)
                    .map(enrollment => (
                      <div key={enrollment.id} className="achievement-badge">
                        <i className="bi bi-award-fill text-warning"></i>
                        <small>{enrollment.courseTitle}</small>
                      </div>
                    ))}
                </div>
              </div>
            )}

            {/* Quick Actions */}
            <div className="modern-card">
              <h5 className="mb-3">
                <i className="bi bi-lightning-fill text-warning me-2"></i>
                Quick Actions
              </h5>
              <div className="d-flex flex-wrap gap-3">
                <button className="btn btn-gradient" onClick={() => navigate('/courses')}>
                  <i className="bi bi-search me-2"></i>
                  Browse Courses
                </button>
                <button className="btn btn-outline-primary" onClick={() => navigate('/student/my-courses')}>
                  <i className="bi bi-book me-2"></i>
                  My Courses
                </button>
                <button className="btn btn-outline-success" onClick={() => navigate('/certificates')}>
                  <i className="bi bi-award me-2"></i>
                  View Certificates
                </button>
              </div>
            </div>
          </div>
        )}

        {/* ===== MY COURSES TAB ===== */}
        {activeTab === 'my-courses' && (
          <div className="fade-in">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h4>My Enrolled Courses</h4>
              <button className="btn btn-gradient" onClick={() => navigate('/courses')}>
                <i className="bi bi-plus-circle me-2"></i>
                Browse More Courses
              </button>
            </div>

            {enrollments.length === 0 ? (
              <div className="modern-card text-center py-5">
                <i className="bi bi-inbox" style={{ fontSize: '4rem', color: '#ccc' }}></i>
                <h4 className="mt-3">No Courses Yet</h4>
                <p className="text-muted mb-4">Start learning by enrolling in a course</p>
                <button className="btn btn-gradient btn-lg" onClick={() => navigate('/courses')}>
                  <i className="bi bi-search me-2"></i>
                  Explore Courses
                </button>
              </div>
            ) : (
              <div className="row g-4">
                {enrollments.map(enrollment => (
                  <div key={enrollment.id} className="col-md-6 col-lg-4">
                    <div className="course-card">
                      <div className="course-image">
                        <img 
                          src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=200&fit=crop" 
                          alt={enrollment.courseTitle}
                        />
                      </div>
                      <div className="p-4">
                        <div className="d-flex gap-2 mb-3">
                          <span className={`badge ${
                            enrollment.status === 'COMPLETED' ? 'bg-success' : 
                            enrollment.status === 'IN_PROGRESS' ? 'bg-primary' : 'bg-info'
                          }`}>
                            {enrollment.status}
                          </span>
                          {enrollment.certificateIssued && (
                            <span className="badge bg-warning">
                              <i className="bi bi-award me-1"></i>
                              Certified
                            </span>
                          )}
                        </div>
                        
                        <h5 className="mb-2">{enrollment.courseTitle}</h5>
                        <p className="text-muted small mb-3">
                          Enrolled: {new Date(enrollment.enrolledAt).toLocaleDateString()}
                        </p>
                        
                        <div className="mb-3">
                          <div className="d-flex justify-content-between mb-1">
                            <small className="text-muted">Progress</small>
                            <small className="fw-bold">{enrollment.progress}%</small>
                          </div>
                          <div className="modern-progress">
                            <div 
                              className="modern-progress-bar" 
                              style={{ width: `${enrollment.progress}%` }}
                            ></div>
                          </div>
                        </div>
                        
                        <button
                          className="btn btn-gradient w-100"
                          onClick={() => handleContinueLearning(enrollment)}
                        >
                          <i className="bi bi-play-fill me-1"></i>
                          {enrollment.status === 'COMPLETED' ? 'Review Course' : 'Continue Learning'}
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {/* ===== BROWSE COURSES TAB ===== */}
        {activeTab === 'browse' && (
          <div className="fade-in">
            <h4 className="mb-3">Browse Available Courses</h4>
            <div className="row g-4">
              {availableCourses
                .filter(course => !enrollments.find(e => e.courseId === course.id))
                .map(course => (
                  <div key={course.id} className="col-md-6 col-lg-4">
                    <div className="course-card">
                      <div className="course-image">
                        <img 
                          src={course.thumbnail || "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=200&fit=crop"} 
                          alt={course.title}
                        />
                      </div>
                      <div className="p-4">
                        <div className="d-flex gap-2 mb-3">
                          <span className="badge-gradient">{course.category}</span>
                          <span className="badge bg-secondary">{course.level}</span>
                        </div>
                        
                        <h5 className="mb-2">{course.title}</h5>
                        <p className="text-muted small mb-3">
                          {course.description?.substring(0, 100)}...
                        </p>
                        
                        <div className="d-flex justify-content-between align-items-center mb-3">
                          <span className="text-muted small">
                            <i className="bi bi-clock me-1"></i>
                            {course.durationHours} hours
                          </span>
                          <h5 className="text-success mb-0">${course.price}</h5>
                        </div>
                        
                        <div className="d-flex gap-2">
                          <button
                            className="btn btn-outline-primary btn-sm flex-grow-1"
                            onClick={() => handleViewCourse(course.id)}
                          >
                            Details
                          </button>
                          <button
                            className="btn btn-gradient btn-sm flex-grow-1"
                            onClick={() => handleEnroll(course.id)}
                          >
                            Enroll Now
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
            </div>
          </div>
        )}

        {/* ===== CERTIFICATES TAB ===== */}
        {activeTab === 'certificates' && (
          <div className="fade-in">
            <h4 className="mb-3">My Certificates</h4>
            {enrollments.filter(e => e.certificateIssued).length === 0 ? (
              <div className="modern-card text-center py-5">
                <i className="bi bi-award" style={{ fontSize: '4rem', color: '#ccc' }}></i>
                <h4 className="mt-3">No Certificates Yet</h4>
                <p className="text-muted">Complete courses to earn certificates</p>
              </div>
            ) : (
              <div className="row g-4">
                {enrollments
                  .filter(e => e.certificateIssued)
                  .map(enrollment => (
                    <div key={enrollment.id} className="col-md-6">
                      <div className="modern-card">
                        <div className="d-flex align-items-center">
                          <div className="certificate-icon me-3">
                            <i className="bi bi-award-fill text-warning"></i>
                          </div>
                          <div className="flex-grow-1">
                            <h6 className="mb-1">{enrollment.courseTitle}</h6>
                            <small className="text-muted">
                              Completed: {enrollment.completedAt ? new Date(enrollment.completedAt).toLocaleDateString() : 'N/A'}
                            </small>
                          </div>
                          <button className="btn btn-outline-primary btn-sm">
                            <i className="bi bi-download"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  ))}
              </div>
            )}
          </div>
        )}

        {/* ===== PROGRESS TAB ===== */}
        {activeTab === 'progress' && (
          <div className="fade-in">
            <h4 className="mb-3">Learning Progress</h4>
            <div className="modern-card">
              <div className="row g-4">
                <div className="col-md-6">
                  <h6>Overall Progress</h6>
                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-1">
                      <span>Courses Completed</span>
                      <span>{stats.completed} / {stats.totalEnrolled}</span>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(stats.completed / stats.totalEnrolled) * 100}%` }}
                      ></div>
                    </div>
                  </div>
                </div>
                <div className="col-md-6">
                  <h6>Certificates Earned</h6>
                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-1">
                      <span>Total Certificates</span>
                      <span>{stats.certificates}</span>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(stats.certificates / stats.totalEnrolled) * 100}%`, background: 'var(--gradient-warning)' }}
                      ></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ===== SETTINGS TAB ===== */}
        {activeTab === 'settings' && (
          <div className="fade-in">
            <h4 className="mb-3">Profile Settings</h4>
            <div className="modern-card">
              <form>
                <div className="row g-3">
                  <div className="col-md-6">
                    <label className="form-label">Full Name</label>
                    <input 
                      type="text" 
                      className="form-control modern-input" 
                      defaultValue={user?.name}
                    />
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Email</label>
                    <input 
                      type="email" 
                      className="form-control modern-input" 
                      defaultValue={user?.email}
                      disabled
                    />
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Phone</label>
                    <input 
                      type="tel" 
                      className="form-control modern-input" 
                    />
                  </div>
                  <div className="col-12">
                    <button type="submit" className="btn btn-gradient">
                      <i className="bi bi-check-circle me-2"></i>
                      Save Changes
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ImprovedStudentDashboard;