import React, { useState, useEffect } from 'react';
import { courseAPI, userAPI } from '../services/api';
import AdminSidebar from '../components/AdminSidebar';
import './AdminDashboard.css';

const ImprovedAdminDashboard = () => {
  const [activeTab, setActiveTab] = useState('overview');
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalCourses: 0,
    totalInstructors: 0,
    totalStudents: 0
  });
  const [users, setUsers] = useState([]);
  const [instructors, setInstructors] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState('');
  const [editingItem, setEditingItem] = useState(null);
  const [formData, setFormData] = useState({});

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
          totalCourses: coursesRes.data.success ? coursesRes.data.data.length : 0
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

  const handleDeleteUser = async (userId) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await userAPI.deleteUser(userId);
        fetchData();
      } catch (err) {
        alert('Failed to delete user');
      }
    }
  };

  const handleDeleteInstructor = async (instructorId) => {
    if (window.confirm('Delete this instructor? All their courses will be affected.')) {
      try {
        await userAPI.deleteUser(instructorId);
        fetchData();
      } catch (err) {
        alert('Failed to delete instructor');
      }
    }
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

  const handleSaveInstructor = async (e) => {
    e.preventDefault();
    try {
      if (editingItem) {
        await userAPI.updateUser(editingItem.id, {
          ...formData,
          role: 'ROLE_INSTRUCTOR'
        });
      } else {
        // Create new instructor via register endpoint
        const response = await fetch('http://localhost:8080/api/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            ...formData,
            role: 'ROLE_INSTRUCTOR'
          })
        });
        if (!response.ok) throw new Error('Registration failed');
      }
      setShowModal(false);
      fetchData();
      alert('Instructor saved successfully!');
    } catch (err) {
      alert('Failed to save instructor');
    }
  };

  const handleDeleteCourse = async (courseId) => {
    if (window.confirm('Are you sure you want to delete this course?')) {
      try {
        await courseAPI.deleteCourse(courseId);
        fetchData();
      } catch (err) {
        alert('Failed to delete course');
      }
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

        {/* Navigation Tabs */}
        <div className="admin-tabs mb-4">
          <button
            className={`admin-tab ${activeTab === 'overview' ? 'active' : ''}`}
            onClick={() => setActiveTab('overview')}
          >
            <i className="bi bi-house me-2"></i>
            Overview
          </button>
          <button
            className={`admin-tab ${activeTab === 'users' ? 'active' : ''}`}
            onClick={() => setActiveTab('users')}
          >
            <i className="bi bi-people me-2"></i>
            Users
          </button>
          <button
            className={`admin-tab ${activeTab === 'instructors' ? 'active' : ''}`}
            onClick={() => setActiveTab('instructors')}
          >
            <i className="bi bi-person-video3 me-2"></i>
            Instructors
          </button>
          <button
            className={`admin-tab ${activeTab === 'courses' ? 'active' : ''}`}
            onClick={() => setActiveTab('courses')}
          >
            <i className="bi bi-book me-2"></i>
            Courses
          </button>
        </div>

        {/* Overview Tab */}
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

        {/* Users Tab */}
        {activeTab === 'users' && (
          <div className="fade-in">
            <div className="modern-card">
              <div className="card-header-custom">
                <h5 className="mb-0">User Management</h5>
              </div>
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

        {/* Instructors Tab */}
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

        {/* Courses Tab */}
        {activeTab === 'courses' && (
          <div className="fade-in">
            <div className="modern-card">
              <div className="card-header-custom">
                <h5 className="mb-0">Course Management</h5>
              </div>
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
                        <td>
                          <strong>{course.title}</strong>
                        </td>
                        <td>{course.instructorName}</td>
                        <td>
                          <span className="badge bg-secondary">
                            {course.category}
                          </span>
                        </td>
                        <td>
                          <span className="badge bg-info">
                            {course.level}
                          </span>
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
                              className="btn btn-sm btn-outline-primary"
                              onClick={() => window.location.href = `/courses/${course.id}`}
                            >
                              <i className="bi bi-eye"></i>
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

        {/* Instructor Modal */}
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
      </div>
    </div>
  );
};

export default ImprovedAdminDashboard;
