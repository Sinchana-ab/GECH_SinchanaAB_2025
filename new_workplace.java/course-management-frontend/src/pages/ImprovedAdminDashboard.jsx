import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { courseAPI, userAPI, certificateAPI, materialAPI } from '../services/api';
import AdminSidebar from '../components/AdminSidebar';
import FileUpload from '../components/FileUpload';
import './AdminDashboard.css';

const ImprovedAdminDashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();
  
  // Determine active tab from URL
  const getActiveTab = () => {
    const path = location.pathname;
    if (path === '/admin' || path === '/admin/') return 'overview';
    if (path.includes('/users')) return 'users';
    if (path.includes('/instructors')) return 'instructors';
    if (path.includes('/courses')) return 'courses';
    if (path.includes('/certificates')) return 'certificates';
    if (path.includes('/reports')) return 'reports';
    if (path.includes('/settings')) return 'settings';
    return 'overview';
  };

  const [activeTab, setActiveTab] = useState(getActiveTab());
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalCourses: 0,
    totalInstructors: 0,
    totalStudents: 0,
    totalCertificates: 0
  });
  
  const [users, setUsers] = useState([]);
  const [instructors, setInstructors] = useState([]);
  const [courses, setCourses] = useState([]);
  const [certificates, setCertificates] = useState([]);
  const [loading, setLoading] = useState(true);
  
  // Modal states
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState('');
  const [editingItem, setEditingItem] = useState(null);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [showMaterialModal, setShowMaterialModal] = useState(false);
  
  // Form data
  const [formData, setFormData] = useState({});

  useEffect(() => {
    setActiveTab(getActiveTab());
  }, [location.pathname]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [usersRes, coursesRes] = await Promise.all([
        userAPI.getAllUsers(),
        courseAPI.getAllPublished()
      ]);

      if (usersRes.data.success) {
        const userData = usersRes.data.data;
        setUsers(userData);
        const instructorList = userData.filter(u => u.role === 'ROLE_INSTRUCTOR');
        setInstructors(instructorList);
        
        setStats({
          totalUsers: userData.length,
          totalInstructors: instructorList.length,
          totalStudents: userData.filter(u => u.role === 'ROLE_STUDENT').length,
          totalCourses: coursesRes.data.success ? coursesRes.data.data.length : 0,
          totalCertificates: 0
        });
      }

      if (coursesRes.data.success) {
        setCourses(coursesRes.data.data);
      }
    } catch (err) {
      console.error('Failed to fetch data', err);
    } finally {
      setLoading(false);
    }
  };

  // ===== USER MANAGEMENT =====
  const handleDeleteUser = async (userId) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await userAPI.deleteUser(userId);
        fetchData();
        alert('User deleted successfully');
      } catch (err) {
        alert('Failed to delete user');
      }
    }
  };

  // ===== INSTRUCTOR MANAGEMENT =====
  const handleAddInstructor = () => {
    setEditingItem(null);
    setFormData({
      name: '',
      email: '',
      phone: '',
      bio: '',
      password: ''
    });
    setModalType('instructor');
    setShowModal(true);
  };

  const handleEditInstructor = (instructor) => {
    setEditingItem(instructor);
    setFormData({
      name: instructor.name,
      email: instructor.email,
      phone: instructor.phone || '',
      bio: instructor.bio || ''
    });
    setModalType('instructor');
    setShowModal(true);
  };

  const handleSaveInstructor = async (e) => {
    e.preventDefault();
    try {
      if (editingItem) {
        await userAPI.updateUser(editingItem.id, {
          ...formData,
          role: 'ROLE_INSTRUCTOR'
        });
        alert('Instructor updated successfully!');
      } else {
        const response = await fetch('http://localhost:8080/api/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            ...formData,
            role: 'ROLE_INSTRUCTOR'
          })
        });
        if (!response.ok) throw new Error('Registration failed');
        alert('Instructor created successfully!');
      }
      setShowModal(false);
      fetchData();
    } catch (err) {
      alert('Failed to save instructor');
    }
  };

  const handleDeleteInstructor = async (instructorId) => {
    if (window.confirm('Delete this instructor? All their courses will be affected.')) {
      try {
        await userAPI.deleteUser(instructorId);
        fetchData();
        alert('Instructor deleted successfully');
      } catch (err) {
        alert('Failed to delete instructor');
      }
    }
  };

  // ===== COURSE MANAGEMENT (ADMIN CAN ADD COURSES) =====
  const handleAddCourse = () => {
    setEditingItem(null);
    setFormData({
      title: '',
      description: '',
      instructorId: '',
      category: '',
      level: 'BEGINNER',
      durationHours: '',
      price: '',
      published: false
    });
    setModalType('course');
    setShowModal(true);
  };

  const handleEditCourse = (course) => {
    setEditingItem(course);
    setFormData({
      title: course.title,
      description: course.description,
      instructorId: course.instructorId,
      category: course.category,
      level: course.level,
      durationHours: course.durationHours,
      price: course.price,
      published: course.published
    });
    setModalType('course');
    setShowModal(true);
  };

  const handleSaveCourse = async (e) => {
    e.preventDefault();
    
    const courseData = {
      ...formData,
      instructorId: parseInt(formData.instructorId),
      durationHours: parseInt(formData.durationHours),
      price: parseFloat(formData.price)
    };

    try {
      if (editingItem) {
        await courseAPI.updateCourse(editingItem.id, courseData);
        alert('Course updated successfully!');
      } else {
        const response = await courseAPI.createCourse(courseData);
        if (response.data.success) {
          alert('Course created! Now add materials.');
          setSelectedCourse(response.data.data);
          setShowMaterialModal(true);
        }
      }
      setShowModal(false);
      fetchData();
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to save course');
    }
  };

  const handleDeleteCourse = async (courseId) => {
    if (window.confirm('Are you sure you want to delete this course?')) {
      try {
        await courseAPI.deleteCourse(courseId);
        fetchData();
        alert('Course deleted successfully');
      } catch (err) {
        alert('Failed to delete course');
      }
    }
  };

  const handleAddMaterials = (course) => {
    setSelectedCourse(course);
    setShowMaterialModal(true);
  };

  const handleViewMaterials = (course) => {
    navigate(`/courses/${course.id}/materials`);
  };

  // ===== CERTIFICATE MANAGEMENT =====
  const fetchCertificates = async () => {
    try {
      // Implement certificate fetching if needed
      // const response = await certificateAPI.getAllCertificates();
      // setCertificates(response.data.data);
    } catch (err) {
      console.error('Failed to fetch certificates', err);
    }
  };

  if (loading) {
    return (
      <div className="admin-layout">
        <AdminSidebar />
        <div className="admin-content">
          <div className="text-center py-5">
            <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }}></div>
            <p className="mt-3">Loading dashboard...</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="admin-layout">
      <AdminSidebar />
      
      <div className="admin-content">
        <div className="page-header mb-4">
          <h2 className="page-title">
            <i className="bi bi-speedometer2 me-2"></i>
            Admin Dashboard
          </h2>
          <p className="text-muted">Manage your platform from here</p>
        </div>

        {/* ===== OVERVIEW TAB ===== */}
        {activeTab === 'overview' && (
          <div className="fade-in">
            {/* Stats Cards */}
            <div className="row g-4 mb-4">
              <div className="col-md-3">
                <div className="stats-card gradient-primary">
                  <div className="stats-icon">
                    <i className="bi bi-people-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalUsers}</div>
                    <div className="stats-label">Total Users</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-success">
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
                <div className="stats-card gradient-info">
                  <div className="stats-icon">
                    <i className="bi bi-person-badge-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalInstructors}</div>
                    <div className="stats-label">Instructors</div>
                  </div>
                </div>
              </div>
              
              <div className="col-md-3">
                <div className="stats-card gradient-warning">
                  <div className="stats-icon">
                    <i className="bi bi-mortarboard-fill"></i>
                  </div>
                  <div className="stats-content">
                    <div className="stats-number">{stats.totalStudents}</div>
                    <div className="stats-label">Students</div>
                  </div>
                </div>
              </div>
            </div>

            {/* Quick Actions */}
            <div className="row g-4 mb-4">
              <div className="col-md-12">
                <div className="modern-card">
                  <h5 className="mb-3">
                    <i className="bi bi-lightning-fill text-warning me-2"></i>
                    Quick Actions
                  </h5>
                  <div className="d-flex flex-wrap gap-3">
                    <button className="btn btn-gradient" onClick={handleAddInstructor}>
                      <i className="bi bi-person-plus me-2"></i>
                      Add Instructor
                    </button>
                    <button className="btn btn-gradient" onClick={handleAddCourse}>
                      <i className="bi bi-plus-circle me-2"></i>
                      Add Course
                    </button>
                    <button className="btn btn-outline-primary" onClick={() => navigate('/admin/users')}>
                      <i className="bi bi-people me-2"></i>
                      Manage Users
                    </button>
                    <button className="btn btn-outline-success" onClick={() => navigate('/admin/reports')}>
                      <i className="bi bi-graph-up me-2"></i>
                      View Reports
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {/* Recent Activity */}
            <div className="row g-4">
              <div className="col-lg-6">
                <div className="modern-card">
                  <div className="card-header-custom">
                    <h5 className="mb-0">
                      <i className="bi bi-clock-history me-2"></i>
                      Recent Users
                    </h5>
                  </div>
                  <div className="list-group list-group-flush">
                    {users.slice(0, 5).map(user => (
                      <div key={user.id} className="list-group-item d-flex justify-content-between align-items-center">
                        <div className="d-flex align-items-center">
                          <div className="user-avatar-small me-3">
                            <i className="bi bi-person-circle"></i>
                          </div>
                          <div>
                            <h6 className="mb-0">{user.name}</h6>
                            <small className="text-muted">{user.email}</small>
                          </div>
                        </div>
                        <span className="badge-gradient">
                          {user.role.replace('ROLE_', '')}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              <div className="col-lg-6">
                <div className="modern-card">
                  <div className="card-header-custom">
                    <h5 className="mb-0">
                      <i className="bi bi-book me-2"></i>
                      Recent Courses
                    </h5>
                  </div>
                  <div className="list-group list-group-flush">
                    {courses.slice(0, 5).map(course => (
                      <div key={course.id} className="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                          <h6 className="mb-1">{course.title}</h6>
                          <small className="text-muted">
                            <i className="bi bi-person me-1"></i>
                            {course.instructorName}
                          </small>
                        </div>
                        <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'}`}>
                          {course.published ? 'Published' : 'Draft'}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ===== USERS TAB ===== */}
        {activeTab === 'users' && (
          <div className="fade-in">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h4>User Management</h4>
            </div>
            <div className="modern-card">
              <div className="table-responsive">
                <table className="modern-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Phone</th>
                      <th>Role</th>
                      <th>Status</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {users.map(user => (
                      <tr key={user.id}>
                        <td>#{user.id}</td>
                        <td>
                          <div className="d-flex align-items-center">
                            <div className="user-avatar-small me-2">
                              <i className="bi bi-person-circle"></i>
                            </div>
                            {user.name}
                          </div>
                        </td>
                        <td>{user.email}</td>
                        <td>{user.phone || 'N/A'}</td>
                        <td>
                          <span className="badge-gradient">
                            {user.role.replace('ROLE_', '')}
                          </span>
                        </td>
                        <td>
                          <span className="badge bg-success">Active</span>
                        </td>
                        <td>
                          <button
                            className="btn btn-sm btn-outline-danger"
                            onClick={() => handleDeleteUser(user.id)}
                          >
                            <i className="bi bi-trash"></i>
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {/* ===== INSTRUCTORS TAB ===== */}
        {activeTab === 'instructors' && (
          <div className="fade-in">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h4>Instructor Management</h4>
              <button className="btn btn-gradient" onClick={handleAddInstructor}>
                <i className="bi bi-plus-circle me-2"></i>
                Add Instructor
              </button>
            </div>

            <div className="row g-4">
              {instructors.map(instructor => (
                <div key={instructor.id} className="col-md-6 col-lg-4">
                  <div className="modern-card instructor-card">
                    <div className="text-center mb-3">
                      <div className="instructor-avatar mx-auto mb-3">
                        <i className="bi bi-person-circle"></i>
                      </div>
                      <h5 className="mb-1">{instructor.name}</h5>
                      <p className="text-muted mb-2">{instructor.email}</p>
                      {instructor.phone && (
                        <p className="text-muted small">
                          <i className="bi bi-telephone me-1"></i>
                          {instructor.phone}
                        </p>
                      )}
                      {instructor.bio && (
                        <p className="text-muted small">{instructor.bio.substring(0, 100)}...</p>
                      )}
                    </div>
                    <div className="d-flex gap-2">
                      <button
                        className="btn btn-outline-primary btn-sm flex-grow-1"
                        onClick={() => handleEditInstructor(instructor)}
                      >
                        <i className="bi bi-pencil me-1"></i>
                        Edit
                      </button>
                      <button
                        className="btn btn-outline-danger btn-sm"
                        onClick={() => handleDeleteInstructor(instructor.id)}
                      >
                        <i className="bi bi-trash"></i>
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* ===== COURSES TAB ===== */}
        {activeTab === 'courses' && (
          <div className="fade-in">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h4>Course Management</h4>
              <button className="btn btn-gradient" onClick={handleAddCourse}>
                <i className="bi bi-plus-circle me-2"></i>
                Add Course
              </button>
            </div>

            <div className="modern-card">
              <div className="table-responsive">
                <table className="modern-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Title</th>
                      <th>Instructor</th>
                      <th>Category</th>
                      <th>Level</th>
                      <th>Students</th>
                      <th>Price</th>
                      <th>Status</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {courses.map(course => (
                      <tr key={course.id}>
                        <td>#{course.id}</td>
                        <td><strong>{course.title}</strong></td>
                        <td>{course.instructorName}</td>
                        <td>
                          <span className="badge bg-secondary">{course.category}</span>
                        </td>
                        <td>
                          <span className="badge bg-info">{course.level}</span>
                        </td>
                        <td>{course.enrollmentCount || 0}</td>
                        <td className="text-success fw-bold">${course.price}</td>
                        <td>
                          <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'}`}>
                            {course.published ? 'Published' : 'Draft'}
                          </span>
                        </td>
                        <td>
                          <div className="btn-group">
                            <button
                              className="btn btn-sm btn-outline-success"
                              onClick={() => handleAddMaterials(course)}
                              title="Add Materials"
                            >
                              <i className="bi bi-folder-plus"></i>
                            </button>
                            <button
                              className="btn btn-sm btn-outline-info"
                              onClick={() => handleViewMaterials(course)}
                              title="View Materials"
                            >
                              <i className="bi bi-folder"></i>
                            </button>
                            <button
                              className="btn btn-sm btn-outline-primary"
                              onClick={() => handleEditCourse(course)}
                            >
                              <i className="bi bi-pencil"></i>
                            </button>
                            <button
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleDeleteCourse(course.id)}
                            >
                              <i className="bi bi-trash"></i>
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {/* ===== CERTIFICATES TAB ===== */}
        {activeTab === 'certificates' && (
          <div className="fade-in">
            <div className="modern-card">
              <div className="text-center py-5">
                <i className="bi bi-award" style={{ fontSize: '4rem', color: '#ccc' }}></i>
                <h4 className="mt-3">Certificate Management</h4>
                <p className="text-muted">View and manage all certificates issued</p>
                <button className="btn btn-outline-primary mt-3">
                  <i className="bi bi-download me-2"></i>
                  Export Certificates
                </button>
              </div>
            </div>
          </div>
        )}

        {/* ===== REPORTS TAB ===== */}
        {activeTab === 'reports' && (
          <div className="fade-in">
            <div className="row g-4">
              <div className="col-md-6">
                <div className="modern-card">
                  <h5 className="mb-3">
                    <i className="bi bi-graph-up me-2"></i>
                    User Distribution
                  </h5>
                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-2">
                      <span>Students</span>
                      <strong>{stats.totalStudents}</strong>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(stats.totalStudents / stats.totalUsers) * 100}%` }}
                      ></div>
                    </div>
                  </div>

                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-2">
                      <span>Instructors</span>
                      <strong>{stats.totalInstructors}</strong>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(stats.totalInstructors / stats.totalUsers) * 100}%`, background: 'var(--gradient-info)' }}
                      ></div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="col-md-6">
                <div className="modern-card">
                  <h5 className="mb-3">
                    <i className="bi bi-bar-chart me-2"></i>
                    Course Statistics
                  </h5>
                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-2">
                      <span>Published Courses</span>
                      <strong>{courses.filter(c => c.published).length}</strong>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(courses.filter(c => c.published).length / stats.totalCourses) * 100}%` }}
                      ></div>
                    </div>
                  </div>

                  <div className="mb-3">
                    <div className="d-flex justify-content-between mb-2">
                      <span>Draft Courses</span>
                      <strong>{courses.filter(c => !c.published).length}</strong>
                    </div>
                    <div className="modern-progress">
                      <div 
                        className="modern-progress-bar" 
                        style={{ width: `${(courses.filter(c => !c.published).length / stats.totalCourses) * 100}%`, background: 'var(--gradient-warning)' }}
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
            <div className="modern-card">
              <h5 className="mb-4">
                <i className="bi bi-gear me-2"></i>
                Platform Settings
              </h5>
              
              <div className="mb-4">
                <h6>General Settings</h6>
                <div className="form-check form-switch mb-2">
                  <input className="form-check-input" type="checkbox" id="maintenance" />
                  <label className="form-check-label" htmlFor="maintenance">
                    Maintenance Mode
                  </label>
                </div>
                <div className="form-check form-switch mb-2">
                  <input className="form-check-input" type="checkbox" id="registration" defaultChecked />
                  <label className="form-check-label" htmlFor="registration">
                    Allow New Registrations
                  </label>
                </div>
                <div className="form-check form-switch">
                  <input className="form-check-input" type="checkbox" id="notifications" defaultChecked />
                  <label className="form-check-label" htmlFor="notifications">
                    Email Notifications
                  </label>
                </div>
              </div>

              <div className="mb-4">
                <h6>Course Settings</h6>
                <div className="row g-3">
                  <div className="col-md-6">
                    <label className="form-label">Max File Upload Size (MB)</label>
                    <input type="number" className="form-control modern-input" defaultValue="100" />
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Default Course Duration (hours)</label>
                    <input type="number" className="form-control modern-input" defaultValue="40" />
                  </div>
                </div>
              </div>

              <button className="btn btn-gradient">
                <i className="bi bi-check-circle me-2"></i>
                Save Settings
              </button>
            </div>
          </div>
        )}

        {/* ===== INSTRUCTOR MODAL ===== */}
        {showModal && modalType === 'instructor' && (
          <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
            <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content modern-card">
                <div className="modal-header border-0">
                  <h5 className="modal-title">
                    {editingItem ? 'Edit Instructor' : 'Add New Instructor'}
                  </h5>
                  <button
                    type="button"
                    className="btn-close"
                    onClick={() => setShowModal(false)}
                  ></button>
                </div>
                <div className="modal-body">
                  <form onSubmit={handleSaveInstructor}>
                    <div className="mb-3">
                      <label className="form-label">Full Name *</label>
                      <input
                        type="text"
                        className="form-control modern-input"
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        required
                      />
                    </div>

                    <div className="mb-3">
                      <label className="form-label">Email *</label>
                      <input
                        type="email"
                        className="form-control modern-input"
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                        required
                        disabled={!!editingItem}
                      />
                    </div>

                    <div className="mb-3">
                      <label className="form-label">Phone</label>
                      <input
                        type="tel"
                        className="form-control modern-input"
                        value={formData.phone}
                        onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                      />
                    </div>

                    {!editingItem && (
                      <div className="mb-3">
                        <label className="form-label">Password *</label>
                        <input
                          type="password"
                          className="form-control modern-input"
                          value={formData.password || ''}
                          onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                          required
                        />
                      </div>
                    )}

                    <div className="mb-3">
                      <label className="form-label">Bio</label>
                      <textarea
                        className="form-control modern-input"
                        rows="3"
                        value={formData.bio}
                        onChange={(e) => setFormData({ ...formData, bio: e.target.value })}
                      ></textarea>
                    </div>

                    <div className="d-flex gap-2">
                      <button type="submit" className="btn btn-gradient flex-grow-1">
                        <i className="bi bi-check-circle me-2"></i>
                        Save Instructor
                      </button>
                      <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={() => setShowModal(false)}
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

        {/* ===== COURSE MODAL ===== */}
        {showModal && modalType === 'course' && (
          <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
            <div className="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
              <div className="modal-content modern-card">
                <div className="modal-header border-0">
                  <h5 className="modal-title">
                    <i className="bi bi-book me-2"></i>
                    {editingItem ? 'Edit Course' : 'Add New Course'}
                  </h5>
                  <button
                    type="button"
                    className="btn-close"
                    onClick={() => setShowModal(false)}
                  ></button>
                </div>
                <div className="modal-body">
                  <form onSubmit={handleSaveCourse}>
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

                    <div className="mb-3">
                      <label className="form-label fw-bold">Select Instructor *</label>
                      <select
                        className="form-select modern-input"
                        value={formData.instructorId}
                        onChange={(e) => setFormData({...formData, instructorId: e.target.value})}
                        required
                      >
                        <option value="">-- Select Instructor --</option>
                        {instructors.map(instructor => (
                          <option key={instructor.id} value={instructor.id}>
                            {instructor.name} ({instructor.email})
                          </option>
                        ))}
                      </select>
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
                        {editingItem ? 'Update Course' : 'Create Course'}
                      </button>
                      <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={() => setShowModal(false)}
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

        {/* ===== MATERIAL UPLOAD MODAL ===== */}
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

export default ImprovedAdminDashboard;