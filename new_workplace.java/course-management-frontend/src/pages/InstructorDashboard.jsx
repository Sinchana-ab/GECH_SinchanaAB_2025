// pages/InstructorDashboard.jsx
import React, { useState, useEffect } from 'react';
import { courseAPI, enrollmentAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const InstructorDashboard = () => {
  const { user } = useAuth();
  const [courses, setCourses] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCourse, setEditingCourse] = useState(null);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [enrollments, setEnrollments] = useState([]);
  const [showEnrollmentModal, setShowEnrollmentModal] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    category: '',
    level: 'BEGINNER',
    durationHours: '',
    price: '',
    published: false
  });

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await courseAPI.getInstructorCourses(user.id);
      if (response.data.success) {
        setCourses(response.data.data);
      }
    } catch (err) {
      console.error('Failed to load courses', err);
    }
  };

  const fetchCourseEnrollments = async (courseId) => {
    try {
      const response = await enrollmentAPI.getCourseEnrollments(courseId);
      if (response.data.success) {
        setEnrollments(response.data.data);
        setShowEnrollmentModal(true);
      }
    } catch (err) {
      console.error('Failed to load enrollments', err);
      alert('Failed to load enrollments');
    }
  };

  const handleViewEnrollments = (course) => {
    setSelectedCourse(course);
    fetchCourseEnrollments(course.id);
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
      } else {
        await courseAPI.createCourse(courseData);
      }
      
      setShowModal(false);
      setEditingCourse(null);
      resetForm();
      fetchCourses();
    } catch (err) {
      console.error('Failed to save course', err);
    }
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
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this course?')) {
      try {
        await courseAPI.deleteCourse(id);
        fetchCourses();
      } catch (err) {
        console.error('Failed to delete course', err);
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
    setShowModal(true);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Instructor Dashboard</h2>
        <button className="btn btn-primary" onClick={handleNewCourse}>
          <i className="bi bi-plus-circle me-2"></i>
          Create New Course
        </button>
      </div>

      <div className="row mb-4">
        <div className="col-md-4">
          <div className="card bg-primary text-white">
            <div className="card-body">
              <h5 className="card-title">Total Courses</h5>
              <h2>{courses.length}</h2>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card bg-success text-white">
            <div className="card-body">
              <h5 className="card-title">Published Courses</h5>
              <h2>{courses.filter(c => c.published).length}</h2>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card bg-info text-white">
            <div className="card-body">
              <h5 className="card-title">Total Students</h5>
              <h2>{courses.reduce((sum, c) => sum + (c.enrollmentCount || 0), 0)}</h2>
            </div>
          </div>
        </div>
      </div>

      <div className="table-responsive">
        <table className="table table-hover">
          <thead>
            <tr>
              <th>Title</th>
              <th>Category</th>
              <th>Level</th>
              <th>Students</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {courses.map(course => (
              <tr key={course.id}>
                <td>{course.title}</td>
                <td>{course.category}</td>
                <td><span className="badge bg-secondary">{course.level}</span></td>
                <td>{course.enrollmentCount}</td>
                <td>
                  <span className={`badge bg-${course.published ? 'success' : 'warning'}`}>
                    {course.published ? 'Published' : 'Draft'}
                  </span>
                </td>
                <td>
                  <button 
                    className="btn btn-sm btn-outline-info me-2"
                    onClick={() => handleViewEnrollments(course)}
                    title="View Enrollments"
                  >
                    <i className="bi bi-people"></i>
                  </button>
                  <button 
                    className="btn btn-sm btn-outline-primary me-2"
                    onClick={() => handleEdit(course)}
                  >
                    <i className="bi bi-pencil"></i>
                  </button>
                  <button 
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDelete(course.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                  <button
                      className="btn btn-sm btn-outline-success me-2"
                      onClick={() => navigate(`/courses/${course.id}/materials`)}
                      title="Manage Materials"
                    >
                      <i className="bi bi-folder"></i>
                    </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Course Modal */}
      {showModal && (
        <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-lg">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  {editingCourse ? 'Edit Course' : 'Create New Course'}
                </h5>
                <button 
                  type="button" 
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label className="form-label">Title</label>
                    <input
                      type="text"
                      className="form-control"
                      value={formData.title}
                      onChange={(e) => setFormData({...formData, title: e.target.value})}
                      required
                    />
                  </div>
                  
                  <div className="mb-3">
                    <label className="form-label">Description</label>
                    <textarea
                      className="form-control"
                      rows="4"
                      value={formData.description}
                      onChange={(e) => setFormData({...formData, description: e.target.value})}
                      required
                    ></textarea>
                  </div>
                  
                  <div className="row">
                    <div className="col-md-6 mb-3">
                      <label className="form-label">Category</label>
                      <input
                        type="text"
                        className="form-control"
                        value={formData.category}
                        onChange={(e) => setFormData({...formData, category: e.target.value})}
                        required
                      />
                    </div>
                    
                    <div className="col-md-6 mb-3">
                      <label className="form-label">Level</label>
                      <select
                        className="form-select"
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
                      <label className="form-label">Duration (hours)</label>
                      <input
                        type="number"
                        className="form-control"
                        value={formData.durationHours}
                        onChange={(e) => setFormData({...formData, durationHours: e.target.value})}
                        required
                      />
                    </div>
                    
                    <div className="col-md-6 mb-3">
                      <label className="form-label">Price ($)</label>
                      <input
                        type="number"
                        step="0.01"
                        className="form-control"
                        value={formData.price}
                        onChange={(e) => setFormData({...formData, price: e.target.value})}
                        required
                      />
                    </div>
                  </div>
                  
                  <div className="form-check mb-3">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      id="published"
                      checked={formData.published}
                      onChange={(e) => setFormData({...formData, published: e.target.checked})}
                    />
                    <label className="form-check-label" htmlFor="published">
                      Publish course
                    </label>
                  </div>
                  
                  <div className="modal-footer">
                    <button 
                      type="button" 
                      className="btn btn-secondary"
                      onClick={() => setShowModal(false)}
                    >
                      Cancel
                    </button>
                    <button type="submit" className="btn btn-primary">
                      {editingCourse ? 'Update' : 'Create'} Course
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Enrollment Modal */}
      {showEnrollmentModal && (
        <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-lg">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  <i className="bi bi-people me-2"></i>
                  Enrolled Students - {selectedCourse?.title}
                </h5>
                <button 
                  type="button" 
                  className="btn-close"
                  onClick={() => setShowEnrollmentModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                {enrollments.length === 0 ? (
                  <div className="text-center py-4">
                    <i className="bi bi-inbox" style={{ fontSize: '3rem', color: '#ccc' }}></i>
                    <p className="text-muted mt-2">No students enrolled yet</p>
                  </div>
                ) : (
                  <div className="table-responsive">
                    <table className="table table-hover">
                      <thead>
                        <tr>
                          <th>Student Name</th>
                          <th>Enrolled Date</th>
                          <th>Progress</th>
                          <th>Status</th>
                          <th>Certificate</th>
                        </tr>
                      </thead>
                      <tbody>
                        {enrollments.map((enrollment) => (
                          <tr key={enrollment.id}>
                            <td>{enrollment.studentName}</td>
                            <td>{new Date(enrollment.enrolledAt).toLocaleDateString()}</td>
                            <td>
                              <div className="progress" style={{ height: '20px' }}>
                                <div 
                                  className="progress-bar" 
                                  style={{ width: `${enrollment.progress}%` }}
                                >
                                  {enrollment.progress}%
                                </div>
                              </div>
                            </td>
                            <td>
                              <span className={`badge bg-${
                                enrollment.status === 'COMPLETED' ? 'success' : 
                                enrollment.status === 'IN_PROGRESS' ? 'primary' : 'info'
                              }`}>
                                {enrollment.status}
                              </span>
                            </td>
                            <td>
                              {enrollment.certificateIssued ? (
                                <i className="bi bi-check-circle-fill text-success"></i>
                              ) : (
                                <i className="bi bi-x-circle text-muted"></i>
                              )}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
              <div className="modal-footer">
                <button 
                  type="button" 
                  className="btn btn-secondary"
                  onClick={() => setShowEnrollmentModal(false)}
                >
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default InstructorDashboard;