
import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { courseAPI, enrollmentAPI, materialAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import InstructorSidebar from '../components/InstructorSidebar';
import FileUpload from '../components/FileUpload';
import './InstructorDashboard.css';

const CompleteInstructorDashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const getActiveTab = () => {
    const path = location.pathname;
    if (path === '/instructor' || path === '/instructor/') return 'overview';
    if (path.includes('/courses')) return 'courses';
    if (path.includes('/students')) return 'students';
    if (path.includes('/analytics')) return 'analytics';
    if (path.includes('/settings')) return 'settings';
    return 'overview';
  };

  const [activeTab, setActiveTab] = useState(getActiveTab());
  const [courses, setCourses] = useState([]);
  const [allEnrollments, setAllEnrollments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCourseModal, setShowCourseModal] = useState(false);
  const [showMaterialModal, setShowMaterialModal] = useState(false);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [editingCourse, setEditingCourse] = useState(null);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    category: '',
    level: 'BEGINNER',
    durationHours: '',
    price: '',
    published: false
  });
  const [stats, setStats] = useState({
    totalCourses: 0,
    publishedCourses: 0,
    totalStudents: 0,
    totalRevenue: 0
  });

  useEffect(() => {
    setActiveTab(getActiveTab());
  }, [location.pathname]);

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      setLoading(true);
      const response = await courseAPI.getInstructorCourses(user.id);
      if (response.data.success) {
        const coursesData = response.data.data;
        setCourses(coursesData);
        
        // Fetch enrollments for all courses
        const enrollmentPromises = coursesData.map(course => 
          enrollmentAPI.getCourseEnrollments(course.id)
        );
        const enrollmentResponses = await Promise.all(enrollmentPromises);
        
        const allEnrollmentsData = enrollmentResponses
          .filter(res => res.data.success)
          .flatMap(res => res.data.data);
        
        setAllEnrollments(allEnrollmentsData);
        
        const totalRevenue = coursesData.reduce((sum, course) => 
          sum + (course.price * (course.enrollmentCount || 0)), 0
        );
        
        setStats({
          totalCourses: coursesData.length,
          publishedCourses: coursesData.filter(c => c.published).length,
          totalStudents: allEnrollmentsData.length,
          totalRevenue: totalRevenue
        });
      }
    } catch (err) {
      console.error('Failed to load courses', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const courseData = {
      ...formData,
      instructorId: user.id,
      durationHours: parseInt(formData.durationHours),
      price: parseFloat(formData.price)
    };

    try {
      if (editingCourse) {
        await courseAPI.updateCourse(editingCourse.id, courseData);
        alert('Course updated successfully!');
      } else {
        const response = await courseAPI.createCourse(courseData);
        if (response.data.success) {
          alert('Course created! Now add materials to your course.');
          setSelectedCourse(response.data.data);
          setShowMaterialModal(true);
        }
      }
      
      setShowCourseModal(false);
      setEditingCourse(null);
      resetForm();
      fetchCourses();
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to save course');
    }
  };

  const handleAddMaterials = (course) => {
    setSelectedCourse(course);
    setShowMaterialModal(true);
  };

  const handleEdit = (course) => {
    setEditingCourse(course);
    setFormData({
      title: course.title,
      description: course.description,
      category: course.category,
      level: course.level,
      durationHours: course.durationHours,
      price: course.price,
      published: course.published
    });
    setShowCourseModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Delete this course? This cannot be undone.')) {
      try {
        await courseAPI.deleteCourse(id);
        fetchCourses();
        alert('Course deleted successfully');
      } catch (err) {
        alert('Failed to delete course');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      category: '',
      level: 'BEGINNER',
      durationHours: '',
      price: '',
      published: false
    });
  };

  const handleNewCourse = () => {
    setEditingCourse(null);
    resetForm();
    setShowCourseModal(true);
  };

  if (loading) {
    return (
      <div className="instructor-layout">
        <InstructorSidebar />
        <div className="instructor-content">
          <div className="text-center py-5">
            <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }}></div>
            <p className="mt-3">Loading dashboard...</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="instructor-layout">
      <InstructorSidebar />
      
      <div className="instructor-content">
        <div className="page-header mb-4">
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2 className="page-title">
                <i className="bi bi-person-video3 me-2"></i>
                Instructor Dashboard
              </h2>
              <p className="text-muted">Manage your courses and students</p>
            </div>
            <button className="btn btn-gradient btn-lg" onClick={handleNewCourse}>
              <i className="bi bi-plus-circle me-2"></i>
              Create New Course
            </button>
          </div>
        </div>

        {/* ===== OVERVIEW TAB ===== */}
        {activeTab === 'overview' && (
          <div className="fade-in">
            {/* Stats */}
            <div className="row g-4 mb-4">
              <div className="col-md-3">
                <div className="stats-card gradient-primary">
                  <div className="stats-icon">
                    <i className="bi bi-book-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalCourses}</div>
                    <div className="stats-label">Total Courses</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-success">
                  <div className="stats-icon">
                    <i className="bi bi-check-circle-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.publishedCourses}</div>
                    <div className="stats-label">Published</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-info">
                  <div className="stats-icon">
                    <i className="bi bi-people-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalStudents}</div>
                    <div className="stats-label">Total Students</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-warning">
                  <div className="stats-icon">
                    <i className="bi bi-currency-dollar"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">${stats.totalRevenue}</div>
                    <div className="stats-label">Total Revenue</div>
                  </div>
                </div>
              </div>
            </div>

            {/* Quick Actions */}
            <div className="modern-card mb-4">
              <h5 className="mb-3">
                <i className="bi bi-lightning-fill text-warning me-2"></i>
                Quick Actions
              </h5>
              <div className="d-flex flex-wrap gap-3">
                <button className="btn btn-gradient" onClick={handleNewCourse}>
                  <i className="bi bi-plus-circle me-2"></i>
                  Create Course
                </button>
                <button className="btn btn-outline-primary" onClick={() => navigate('/instructor/courses')}>
                  <i className="bi bi-book me-2"></i>
                  Manage Courses
                </button>
                <button className="btn btn-outline-success" onClick={() => navigate('/instructor/students')}>
                  <i className="bi bi-people me-2"></i>
                  View Students
                </button>
              </div>
            </div>

            {/* Recent Courses */}
            <div className="modern-card">
              <h5 className="mb-3">
                <i className="bi bi-clock-history me-2"></i>
                Recent Courses
              </h5>
              <div className="table-responsive">
                <table className="modern-table">
                  <thead>
                    <tr>
                      <th>Course</th>
                      <th>Students</th>
                      <th>Status</th>
                      <th>Price</th>
                    </tr>
                  </thead>
                  <tbody>
                    {courses.slice(0, 5).map(course => (
                      <tr key={course.id}>
                        <td><strong>{course.title}</strong></td>
                        <td>{course.enrollmentCount || 0}</td>
                        <td>
                          <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'}`}>
                            {course.published ? 'Published' : 'Draft'}
                          </span>
                        </td>
                        <td className="text-success fw-bold">${course.price}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {/* ===== COURSES TAB ===== */}
        {activeTab === 'courses' && (
          <div className="fade-in">
            {courses.length === 0 ? (
              <div className="text-center py-5">
                <div className="modern-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
                  <i className="bi bi-inbox" style={{ fontSize: '5rem', color: '#ccc' }}></i>
                  <h3 className="mt-4">No Courses Yet</h3>
                  <p className="text-muted mb-4">Start creating your first course and share your knowledge</p>
                  <button className="btn btn-gradient btn-lg" onClick={handleNewCourse}>
                    <i className="bi bi-plus-circle me-2"></i>
                    Create Your First Course
                  </button>
                </div>
              </div>
            ) : (
              <div className="row g-4">
                {courses.map(course => (
                  <div key={course.id} className="col-md-6 col-lg-4 fade-in">
                    <div className="course-card">
                      <div className="course-image">
                        <img 
                          src={course.thumbnail || 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=200&fit=crop'} 
                          alt={course.title}
                        />
                      </div>
                      
                      <div className="p-4">
                        <div className="d-flex gap-2 mb-3">
                          <span className="badge-gradient">{course.category}</span>
                          <span className="badge bg-secondary">{course.level}</span>
                          <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'}`}>
                            {course.published ? 'Published' : 'Draft'}
                          </span>
                        </div>
                        
                        <h5 className="mb-2">{course.title}</h5>
                        <p className="text-muted small mb-3">
                          {course.description?.substring(0, 100)}
                          {course.description?.length > 100 ? '...' : ''}
                        </p>
                        
                        <div className="d-flex justify-content-between align-items-center mb-3">
                          <div>
                            <small className="text-muted d-block">
                              <i className="bi bi-clock me-1"></i>
                              {course.durationHours} hours
                            </small>
                            <small className="text-muted">
                              <i className="bi bi-people me-1"></i>
                              {course.enrollmentCount || 0} students
                            </small>
                          </div>
                          <h4 className="text-success mb-0">${course.price}</h4>
                        </div>
                        
                        <div className="d-flex gap-2">
                          <button
                            className="btn btn-outline-primary btn-sm flex-grow-1"
                            onClick={() => handleAddMaterials(course)}
                          >
                            <i className="bi bi-folder-plus me-1"></i>
                            Materials
                          </button>
                          <button
                            className="btn btn-outline-secondary btn-sm"
                            onClick={() => handleEdit(course)}
                          >
                            <i className="bi bi-pencil"></i>
                          </button>
                          <button
                            className="btn btn-outline-danger btn-sm"
                            onClick={() => handleDelete(course.id)}
                          >
                            <i className="bi bi-trash"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {/* ===== STUDENTS TAB ===== */}
        {activeTab === 'students' && (
          <div className="fade-in">
            <h4 className="mb-3">Enrolled Students</h4>
            <div className="modern-card">
              <div className="table-responsive">
                <table className="modern-table">
                  <thead>
                    <tr>
                      <th>Student Name</th>
                      <th>Course</th>
                      <th>Enrolled Date</th>
                      <th>Progress</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {allEnrollments.map(enrollment => (
                      <tr key={enrollment.id}>
                        <td>
                          <div className="d-flex align-items-center">
                            <div className="user-avatar-small me-2">
                              <i className="bi bi-person-circle"></i>
                            </div>
                            {enrollment.studentName}
                          </div>
                        </td>
                        <td>{enrollment.courseTitle}</td>
                        <td>{new Date(enrollment.enrolledAt).toLocaleDateString()}</td>
                        <td>
                          <div style={{ width: '100px' }}>
                            <div className="modern-progress">
                              <div 
                                className="modern-progress-bar" 
                                style={{ width: `${enrollment.progress}%` }}
                              ></div>
                            </div>
                            <small className="text-muted">{enrollment.progress}%</small>
                          </div>
                        </td>
                        <td>
                          <span className={`badge ${
                            enrollment.status === 'COMPLETED' ? 'bg-success' : 
                            enrollment.status === 'IN_PROGRESS' ? 'bg-primary' : 'bg-info'
                          }`}>
                            {enrollment.status}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {/* ===== ANALYTICS TAB ===== */}
        {activeTab === 'analytics' && (
          <div className="fade-in">
            <h4 className="mb-3">Course Analytics</h4>
            <div className="row g-4">
              <div className="col-md-6">
                <div className="modern-card">
                  <h6 className="mb-3">Course Performance</h6>
                  {courses.map(course => (
                    <div key={course.id} className="mb-3">
                      <div className="d-flex justify-content-between mb-1">
                        <span className="small">{course.title}</span>
                        <span className="small fw-bold">{course.enrollmentCount || 0} students</span>
                      </div>
                      <div className="modern-progress">
                        <div 
                          className="modern-progress-bar" 
                          style={{ width: `${((course.enrollmentCount || 0) / stats.totalStudents) * 100}%` }}
                        ></div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
              
              <div className="col-md-6">
                <div className="modern-card">
                  <h6 className="mb-3">Revenue Breakdown</h6>
                  {courses.map(course => (
                    <div key={course.id} className="mb-3">
                      <div className="d-flex justify-content-between mb-1">
                        <span className="small">{course.title}</span>
                        <span className="small fw-bold text-success">
                          ${(course.price * (course.enrollmentCount || 0)).toFixed(2)}
                        </span>
                      </div>
                      <div className="modern-progress">
                        <div 
                          className="modern-progress-bar" 
                          style={{ 
                            width: `${((course.price * (course.enrollmentCount || 0)) / stats.totalRevenue) * 100}%`,
                            background: 'var(--gradient-success)'
                          }}
                        ></div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ===== SETTINGS TAB ===== */}
        {activeTab === 'settings' && (
          <div className="fade-in">
            <h4 className="mb-3">Instructor Settings</h4>
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
                  <div className="col-12">
                    <label className="form-label">Bio</label>
                    <textarea 
                      className="form-control modern-input" 
                      rows="4"
                      placeholder="Tell students about yourself..."
                    ></textarea>
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

        {/* Course Modal */}
        {showCourseModal && (
          <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
            <div className="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
              <div className="modal-content modern-card">
                <div className="modal-header border-0">
                  <h5 className="modal-title">
                    <i className="bi bi-book me-2"></i>
                    {editingCourse ? 'Edit Course' : 'Create New Course'}
                  </h5>
                  <button
                    type="button"
                    className="btn-close"
                    onClick={() => setShowCourseModal(false)}
                  ></button>
                </div>
                <div className="modal-body">
                  <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                      <label className="form-label fw-bold">Course Title *</label>
                      <input
                        type="text"
                        className="form-control modern-input"
                        value={formData.title}
                        onChange={(e) => setFormData({...formData, title: e.target.value})}
                        placeholder="e.g., Complete Web Development Bootcamp"
                        required
                      />
                    </div>
                    
                    <div className="mb-3">
                      <label className="form-label fw-bold">Description *</label>
                      <textarea
                        className="form-control modern-input"
                        rows="4"
                        value={formData.description}
                        onChange={(e) => setFormData({...formData, description: e.target.value})}
                        placeholder="Describe what students will learn..."
                        required
                      ></textarea>
                    </div>
                    
                    <div className="row">
                      <div className="col-md-6 mb-3">
                        <label className="form-label fw-bold">Category *</label>
                        <input
                          type="text"
                          className="form-control modern-input"
                          value={formData.category}
                          onChange={(e) => setFormData({...formData, category: e.target.value})}
                          placeholder="e.g., Programming, Business"
                          required
                        />
                      </div>
                      
                      <div className="col-md-6 mb-3">
                        <label className="form-label fw-bold">Level *</label>
                        <select
                          className="form-select modern-input"
                          value={formData.level}
                          onChange={(e) => setFormData({...formData, level: e.target.value})}
                        >
                          <option value="BEGINNER">Beginner</option>
                          <option value="INTERMEDIATE">Intermediate</option>
                          <option value="ADVANCED">Advanced</option>
                        </select>
                      </div>
                    </div>
                    
                    <div className="row">
                      <div className="col-md-6 mb-3">
                        <label className="form-label fw-bold">Duration (hours) *</label>
                        <input
                          type="number"
                          className="form-control modern-input"
                          value={formData.durationHours}
                          onChange={(e) => setFormData({...formData, durationHours: e.target.value})}
                          min="1"
                          required
                        />
                      </div>
                      
                      <div className="col-md-6 mb-3">
                        <label className="form-label fw-bold">Price ($) *</label>
                        <input
                          type="number"
                          step="0.01"
                          className="form-control modern-input"
                          value={formData.price}
                          onChange={(e) => setFormData({...formData, price: e.target.value})}
                          min="0"
                          required
                        />
                      </div>
                    </div>
                    
                    <div className="form-check mb-4">
                      <input
                        type="checkbox"
                        className="form-check-input"
                        id="published"
                        checked={formData.published}
                        onChange={(e) => setFormData({...formData, published: e.target.checked})}
                      />
                      <label className="form-check-label" htmlFor="published">
                        Publish course (make it visible to students)
                      </label>
                    </div>
                    
                    <div className="d-flex gap-2">
                      <button type="submit" className="btn btn-gradient flex-grow-1">
                        <i className="bi bi-check-circle me-2"></i>
                        {editingCourse ? 'Update Course' : 'Create Course'}
                      </button>
                      <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={() => setShowCourseModal(false)}
                      >
                        Cancel
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Material Upload Modal */}
        {showMaterialModal && selectedCourse && (
          <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
            <div className="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">
                    <i className="bi bi-folder-plus me-2"></i>
                    Add Materials to {selectedCourse.title}
                  </h5>
                  <button
                    type="button"
                    className="btn-close"
                    onClick={() => setShowMaterialModal(false)}
                  ></button>
                </div>
                <div className="modal-body">
                  <FileUpload
                    courseId={selectedCourse.id}
                    onUploadSuccess={() => {
                      alert('Material uploaded successfully!');
                    }}
                  />
                  
                  <div className="text-center mt-4">
                    <button
                      className="btn btn-outline-primary me-2"
                      onClick={() => {
                        setShowMaterialModal(false);
                        navigate(`/courses/${selectedCourse.id}/materials`);
                      }}
                    >
                      <i className="bi bi-eye me-2"></i>
                      View All Materials
                    </button>
                    <button
                      className="btn btn-outline-secondary"
                      onClick={() => setShowMaterialModal(false)}
                    >
                      Close
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CompleteInstructorDashboard;