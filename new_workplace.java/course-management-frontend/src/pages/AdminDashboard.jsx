// // pages/AdminDashboard.jsx
// import React, { useState, useEffect } from 'react';
// import { courseAPI, userAPI, enrollmentAPI } from '../services/api';
// import { useAuth } from '../context/AuthContext';

// const AdminDashboard = () => {
//   const { user } = useAuth();
//   const [activeTab, setActiveTab] = useState('overview');
//   const [stats, setStats] = useState({
//     totalUsers: 0,
//     totalCourses: 0,
//     totalEnrollments: 0,
//     totalInstructors: 0,
//     totalStudents: 0
//   });
//   const [users, setUsers] = useState([]);
//   const [courses, setCourses] = useState([]);
//   const [loading, setLoading] = useState(true);
//   const [showUserModal, setShowUserModal] = useState(false);
//   const [editingUser, setEditingUser] = useState(null);
//   const [userForm, setUserForm] = useState({
//     name: '',
//     email: '',
//     role: 'ROLE_STUDENT',
//     phone: '',
//     enabled: true
//   });

//   useEffect(() => {
//     fetchData();
//   }, []);

//   const fetchData = async () => {
//     try {
//       setLoading(true);
      
//       // Fetch all data
//       const [usersRes, coursesRes] = await Promise.all([
//         userAPI.getAllUsers(),
//         courseAPI.getAllCourses ? courseAPI.getAllCourses() : courseAPI.getAllPublished()
//       ]);

//       if (usersRes.data.success) {
//         const userData = usersRes.data.data;
//         setUsers(userData);
        
//         // Calculate stats
//         setStats({
//           totalUsers: userData.length,
//           totalInstructors: userData.filter(u => u.role === 'ROLE_INSTRUCTOR').length,
//           totalStudents: userData.filter(u => u.role === 'ROLE_STUDENT').length,
//           totalCourses: coursesRes.data.success ? coursesRes.data.data.length : 0,
//           totalEnrollments: 0 // Can be calculated from backend
//         });
//       }

//       if (coursesRes.data.success) {
//         setCourses(coursesRes.data.data);
//       }
//     } catch (err) {
//       console.error('Failed to fetch data', err);
//     } finally {
//       setLoading(false);
//     }
//   };

//   const handleDeleteUser = async (userId) => {
//     if (window.confirm('Are you sure you want to delete this user?')) {
//       try {
//         await userAPI.deleteUser(userId);
//         fetchData();
//       } catch (err) {
//         alert('Failed to delete user');
//       }
//     }
//   };

//   const handleEditUser = (user) => {
//     setEditingUser(user);
//     setUserForm({
//       name: user.name,
//       email: user.email,
//       role: user.role,
//       phone: user.phone || '',
//       enabled: true
//     });
//     setShowUserModal(true);
//   };

//   const handleSaveUser = async (e) => {
//     e.preventDefault();
//     try {
//       if (editingUser) {
//         await userAPI.updateUser(editingUser.id, userForm);
//       }
//       setShowUserModal(false);
//       setEditingUser(null);
//       fetchData();
//     } catch (err) {
//       alert('Failed to save user');
//     }
//   };

//   const handleDeleteCourse = async (courseId) => {
//     if (window.confirm('Are you sure you want to delete this course?')) {
//       try {
//         await courseAPI.deleteCourse(courseId);
//         fetchData();
//       } catch (err) {
//         alert('Failed to delete course');
//       }
//     }
//   };

//   if (loading) {
//     return (
//       <div className="text-center mt-5">
//         <div className="spinner-border text-primary" role="status">
//           <span className="visually-hidden">Loading...</span>
//         </div>
//       </div>
//     );
//   }

//   return (
//     <div className="container-fluid">
//       <h2 className="mb-4">Admin Dashboard</h2>

//       {/* Navigation Tabs */}
//       <ul className="nav nav-tabs mb-4">
//         <li className="nav-item">
//           <button
//             className={`nav-link ${activeTab === 'overview' ? 'active' : ''}`}
//             onClick={() => setActiveTab('overview')}
//           >
//             <i className="bi bi-speedometer2 me-2"></i>Overview
//           </button>
//         </li>
//         <li className="nav-item">
//           <button
//             className={`nav-link ${activeTab === 'users' ? 'active' : ''}`}
//             onClick={() => setActiveTab('users')}
//           >
//             <i className="bi bi-people me-2"></i>Users
//           </button>
//         </li>
//         <li className="nav-item">
//           <button
//             className={`nav-link ${activeTab === 'courses' ? 'active' : ''}`}
//             onClick={() => setActiveTab('courses')}
//           >
//             <i className="bi bi-book me-2"></i>Courses
//           </button>
//         </li>
//         <li className="nav-item">
//           <button
//             className={`nav-link ${activeTab === 'reports' ? 'active' : ''}`}
//             onClick={() => setActiveTab('reports')}
//           >
//             <i className="bi bi-graph-up me-2"></i>Reports
//           </button>
//         </li>
//       </ul>

//       {/* Overview Tab */}
//       {activeTab === 'overview' && (
//         <div>
//           <div className="row mb-4">
//             <div className="col-md-3">
//               <div className="card bg-primary text-white">
//                 <div className="card-body">
//                   <div className="d-flex justify-content-between align-items-center">
//                     <div>
//                       <h6 className="card-title mb-0">Total Users</h6>
//                       <h2 className="mb-0">{stats.totalUsers}</h2>
//                     </div>
//                     <i className="bi bi-people" style={{ fontSize: '3rem', opacity: 0.3 }}></i>
//                   </div>
//                 </div>
//               </div>
//             </div>

//             <div className="col-md-3">
//               <div className="card bg-success text-white">
//                 <div className="card-body">
//                   <div className="d-flex justify-content-between align-items-center">
//                     <div>
//                       <h6 className="card-title mb-0">Total Courses</h6>
//                       <h2 className="mb-0">{stats.totalCourses}</h2>
//                     </div>
//                     <i className="bi bi-book" style={{ fontSize: '3rem', opacity: 0.3 }}></i>
//                   </div>
//                 </div>
//               </div>
//             </div>

//             <div className="col-md-3">
//               <div className="card bg-info text-white">
//                 <div className="card-body">
//                   <div className="d-flex justify-content-between align-items-center">
//                     <div>
//                       <h6 className="card-title mb-0">Instructors</h6>
//                       <h2 className="mb-0">{stats.totalInstructors}</h2>
//                     </div>
//                     <i className="bi bi-person-badge" style={{ fontSize: '3rem', opacity: 0.3 }}></i>
//                   </div>
//                 </div>
//               </div>
//             </div>

//             <div className="col-md-3">
//               <div className="card bg-warning text-white">
//                 <div className="card-body">
//                   <div className="d-flex justify-content-between align-items-center">
//                     <div>
//                       <h6 className="card-title mb-0">Students</h6>
//                       <h2 className="mb-0">{stats.totalStudents}</h2>
//                     </div>
//                     <i className="bi bi-mortarboard" style={{ fontSize: '3rem', opacity: 0.3 }}></i>
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </div>

//           {/* Recent Activity */}
//           <div className="row">
//             <div className="col-md-6">
//               <div className="card">
//                 <div className="card-header bg-light">
//                   <h5 className="mb-0">Recent Users</h5>
//                 </div>
//                 <div className="card-body">
//                   <div className="list-group list-group-flush">
//                     {users.slice(0, 5).map(user => (
//                       <div key={user.id} className="list-group-item d-flex justify-content-between align-items-center">
//                         <div>
//                           <h6 className="mb-0">{user.name}</h6>
//                           <small className="text-muted">{user.email}</small>
//                         </div>
//                         <span className="badge bg-primary rounded-pill">
//                           {user.role.replace('ROLE_', '')}
//                         </span>
//                       </div>
//                     ))}
//                   </div>
//                 </div>
//               </div>
//             </div>

//             <div className="col-md-6">
//               <div className="card">
//                 <div className="card-header bg-light">
//                   <h5 className="mb-0">Recent Courses</h5>
//                 </div>
//                 <div className="card-body">
//                   <div className="list-group list-group-flush">
//                     {courses.slice(0, 5).map(course => (
//                       <div key={course.id} className="list-group-item d-flex justify-content-between align-items-center">
//                         <div>
//                           <h6 className="mb-0">{course.title}</h6>
//                           <small className="text-muted">By {course.instructorName}</small>
//                         </div>
//                         <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'} rounded-pill`}>
//                           {course.published ? 'Published' : 'Draft'}
//                         </span>
//                       </div>
//                     ))}
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}

//       {/* Users Tab */}
//       {activeTab === 'users' && (
//         <div>
//           <div className="d-flex justify-content-between align-items-center mb-3">
//             <h4>User Management</h4>
//           </div>

//           <div className="card">
//             <div className="card-body">
//               <div className="table-responsive">
//                 <table className="table table-hover">
//                   <thead className="table-light">
//                     <tr>
//                       <th>ID</th>
//                       <th>Name</th>
//                       <th>Email</th>
//                       <th>Phone</th>
//                       <th>Role</th>
//                       <th>Status</th>
//                       <th>Actions</th>
//                     </tr>
//                   </thead>
//                   <tbody>
//                     {users.map(user => (
//                       <tr key={user.id}>
//                         <td>{user.id}</td>
//                         <td>{user.name}</td>
//                         <td>{user.email}</td>
//                         <td>{user.phone || 'N/A'}</td>
//                         <td>
//                           <span className="badge bg-primary">
//                             {user.role.replace('ROLE_', '')}
//                           </span>
//                         </td>
//                         <td>
//                           <span className="badge bg-success">Active</span>
//                         </td>
//                         <td>
//                           <button
//                             className="btn btn-sm btn-outline-primary me-2"
//                             onClick={() => handleEditUser(user)}
//                           >
//                             <i className="bi bi-pencil"></i>
//                           </button>
//                           <button
//                             className="btn btn-sm btn-outline-danger"
//                             onClick={() => handleDeleteUser(user.id)}
//                             disabled={user.id === user.id} // Can't delete yourself
//                           >
//                             <i className="bi bi-trash"></i>
//                           </button>
//                         </td>
//                       </tr>
//                     ))}
//                   </tbody>
//                 </table>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}

//       {/* Courses Tab */}
//       {activeTab === 'courses' && (
//         <div>
//           <div className="d-flex justify-content-between align-items-center mb-3">
//             <h4>Course Management</h4>
//           </div>

//           <div className="card">
//             <div className="card-body">
//               <div className="table-responsive">
//                 <table className="table table-hover">
//                   <thead className="table-light">
//                     <tr>
//                       <th>ID</th>
//                       <th>Title</th>
//                       <th>Instructor</th>
//                       <th>Category</th>
//                       <th>Level</th>
//                       <th>Students</th>
//                       <th>Price</th>
//                       <th>Status</th>
//                       <th>Actions</th>
//                     </tr>
//                   </thead>
//                   <tbody>
//                     {courses.map(course => (
//                       <tr key={course.id}>
//                         <td>{course.id}</td>
//                         <td>{course.title}</td>
//                         <td>{course.instructorName}</td>
//                         <td><span className="badge bg-secondary">{course.category}</span></td>
//                         <td><span className="badge bg-info">{course.level}</span></td>
//                         <td>{course.enrollmentCount || 0}</td>
//                         <td>${course.price}</td>
//                         <td>
//                           <span className={`badge ${course.published ? 'bg-success' : 'bg-warning'}`}>
//                             {course.published ? 'Published' : 'Draft'}
//                           </span>
//                         </td>
//                         <td>
//                           <button
//                             className="btn btn-sm btn-outline-primary me-2"
//                             onClick={() => window.location.href = `/courses/${course.id}`}
//                           >
//                             <i className="bi bi-eye"></i>
//                           </button>
//                           <button
//                             className="btn btn-sm btn-outline-danger"
//                             onClick={() => handleDeleteCourse(course.id)}
//                           >
//                             <i className="bi bi-trash"></i>
//                           </button>
//                         </td>
//                       </tr>
//                     ))}
//                   </tbody>
//                 </table>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}

//       {/* Reports Tab */}
//       {activeTab === 'reports' && (
//         <div>
//           <h4 className="mb-4">System Reports</h4>
          
//           <div className="row">
//             <div className="col-md-6 mb-4">
//               <div className="card">
//                 <div className="card-header bg-light">
//                   <h5 className="mb-0">User Distribution</h5>
//                 </div>
//                 <div className="card-body">
//                   <div className="mb-3">
//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Students</span>
//                       <strong>{stats.totalStudents}</strong>
//                     </div>
//                     <div className="progress mb-3">
//                       <div 
//                         className="progress-bar bg-success" 
//                         style={{ width: `${(stats.totalStudents / stats.totalUsers) * 100}%` }}
//                       ></div>
//                     </div>

//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Instructors</span>
//                       <strong>{stats.totalInstructors}</strong>
//                     </div>
//                     <div className="progress mb-3">
//                       <div 
//                         className="progress-bar bg-primary" 
//                         style={{ width: `${(stats.totalInstructors / stats.totalUsers) * 100}%` }}
//                       ></div>
//                     </div>

//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Admins</span>
//                       <strong>{users.filter(u => u.role === 'ROLE_ADMIN').length}</strong>
//                     </div>
//                     <div className="progress">
//                       <div 
//                         className="progress-bar bg-danger" 
//                         style={{ width: `${(users.filter(u => u.role === 'ROLE_ADMIN').length / stats.totalUsers) * 100}%` }}
//                       ></div>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             </div>

//             <div className="col-md-6 mb-4">
//               <div className="card">
//                 <div className="card-header bg-light">
//                   <h5 className="mb-0">Course Statistics</h5>
//                 </div>
//                 <div className="card-body">
//                   <div className="mb-3">
//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Published Courses</span>
//                       <strong>{courses.filter(c => c.published).length}</strong>
//                     </div>
//                     <div className="progress mb-3">
//                       <div 
//                         className="progress-bar bg-success" 
//                         style={{ width: `${(courses.filter(c => c.published).length / stats.totalCourses) * 100}%` }}
//                       ></div>
//                     </div>

//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Draft Courses</span>
//                       <strong>{courses.filter(c => !c.published).length}</strong>
//                     </div>
//                     <div className="progress mb-3">
//                       <div 
//                         className="progress-bar bg-warning" 
//                         style={{ width: `${(courses.filter(c => !c.published).length / stats.totalCourses) * 100}%` }}
//                       ></div>
//                     </div>

//                     <div className="d-flex justify-content-between mb-2">
//                       <span>Total Enrollments</span>
//                       <strong>{courses.reduce((sum, c) => sum + (c.enrollmentCount || 0), 0)}</strong>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}

//       {/* User Edit Modal */}
//       {showUserModal && (
//         <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
//           <div className="modal-dialog">
//             <div className="modal-content">
//               <div className="modal-header">
//                 <h5 className="modal-title">Edit User</h5>
//                 <button
//                   type="button"
//                   className="btn-close"
//                   onClick={() => setShowUserModal(false)}
//                 ></button>
//               </div>
//               <div className="modal-body">
//                 <form onSubmit={handleSaveUser}>
//                   <div className="mb-3">
//                     <label className="form-label">Name</label>
//                     <input
//                       type="text"
//                       className="form-control"
//                       value={userForm.name}
//                       onChange={(e) => setUserForm({ ...userForm, name: e.target.value })}
//                       required
//                     />
//                   </div>

//                   <div className="mb-3">
//                     <label className="form-label">Email</label>
//                     <input
//                       type="email"
//                       className="form-control"
//                       value={userForm.email}
//                       onChange={(e) => setUserForm({ ...userForm, email: e.target.value })}
//                       required
//                       disabled
//                     />
//                   </div>

//                   <div className="mb-3">
//                     <label className="form-label">Phone</label>
//                     <input
//                       type="tel"
//                       className="form-control"
//                       value={userForm.phone}
//                       onChange={(e) => setUserForm({ ...userForm, phone: e.target.value })}
//                     />
//                   </div>

//                   <div className="mb-3">
//                     <label className="form-label">Role</label>
//                     <select
//                       className="form-select"
//                       value={userForm.role}
//                       onChange={(e) => setUserForm({ ...userForm, role: e.target.value })}
//                     >
//                       <option value="ROLE_STUDENT">Student</option>
//                       <option value="ROLE_INSTRUCTOR">Instructor</option>
//                       <option value="ROLE_ADMIN">Admin</option>
//                     </select>
//                   </div>

//                   <div className="modal-footer">
//                     <button
//                       type="button"
//                       className="btn btn-secondary"
//                       onClick={() => setShowUserModal(false)}
//                     >
//                       Cancel
//                     </button>
//                     <button type="submit" className="btn btn-primary">
//                       Save Changes
//                     </button>
//                   </div>
//                 </form>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// };

// export default AdminDashboard;


import React, { useEffect, useState } from "react";
import axios from "axios";
import { Button, Spinner, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { FaUserCog, FaChalkboardTeacher, FaBookOpen, FaChartBar, FaSignOutAlt } from "react-icons/fa";
import "./admin/AdminDashboard.css"; // (you’ll create this for custom styles)

const AdminDashboard = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [admin, setAdmin] = useState(null);
  const [stats, setStats] = useState({
    totalCourses: 0,
    totalStudents: 0,
    totalInstructors: 0,
  });

  // ✅ Fetch admin profile and dashboard stats
  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const token = localStorage.getItem("token");
        const headers = { Authorization: `Bearer ${token}` };

        const [profileRes, statsRes] = await Promise.all([
          axios.get("http://localhost:8080/api/admin/profile", { headers }),
          axios.get("http://localhost:8080/api/admin/stats", { headers }),
        ]);

        setAdmin(profileRes.data?.data);
        setStats(statsRes.data?.data);
      } catch (err) {
        console.error("Error loading admin data", err);
      } finally {
        setLoading(false);
      }
    };

    fetchAdminData();
  }, []);

  // ✅ Logout
  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (loading)
    return (
      <div className="d-flex justify-content-center align-items-center vh-100">
        <Spinner animation="border" variant="primary" />
      </div>
    );

  return (
    <div className="admin-dashboard d-flex">
      {/* ======= Sidebar ======= */}
      <div className="sidebar bg-dark text-white p-3 vh-100">
        <h4 className="text-center mb-4">Admin Panel</h4>
        <ul className="nav flex-column">
          <li className="nav-item mb-2">
            <Button
              variant="outline-light"
              className="w-100 text-start"
              onClick={() => navigate("/admin/dashboard")}
            >
              <FaChartBar className="me-2" /> Dashboard
            </Button>
          </li>
          <li className="nav-item mb-2">
            <Button
              variant="outline-light"
              className="w-100 text-start"
              onClick={() => navigate("/admin/courses")}
            >
              <FaBookOpen className="me-2" /> Manage Courses
            </Button>
          </li>
          <li className="nav-item mb-2">
            <Button
              variant="outline-light"
              className="w-100 text-start"
              onClick={() => navigate("/admin/instructors")}
            >
              <FaChalkboardTeacher className="me-2" /> Instructors
            </Button>
          </li>
          <li className="nav-item mb-2">
            <Button
              variant="outline-light"
              className="w-100 text-start"
              onClick={() => navigate("/admin/students")}
            >
              <FaUserCog className="me-2" /> Students
            </Button>
          </li>
        </ul>
        <div className="mt-auto text-center">
          <Button variant="danger" onClick={handleLogout}>
            <FaSignOutAlt className="me-2" /> Logout
          </Button>
        </div>
      </div>

      {/* ======= Main Content ======= */}
      <div className="content flex-grow-1 p-4">
        <h3 className="fw-bold mb-4">Welcome, {admin?.name || "Admin"}</h3>

        <div className="row">
          <div className="col-md-4 mb-3">
            <Card className="text-center shadow-sm">
              <Card.Body>
                <FaBookOpen size={35} className="text-primary mb-2" />
                <h5>Total Courses</h5>
                <h3>{stats.totalCourses}</h3>
              </Card.Body>
            </Card>
          </div>

          <div className="col-md-4 mb-3">
            <Card className="text-center shadow-sm">
              <Card.Body>
                <FaChalkboardTeacher size={35} className="text-success mb-2" />
                <h5>Instructors</h5>
                <h3>{stats.totalInstructors}</h3>
              </Card.Body>
            </Card>
          </div>

          <div className="col-md-4 mb-3">
            <Card className="text-center shadow-sm">
              <Card.Body>
                <FaUserCog size={35} className="text-warning mb-2" />
                <h5>Students</h5>
                <h3>{stats.totalStudents}</h3>
              </Card.Body>
            </Card>
          </div>
        </div>

        <div className="mt-5">
          <h4>📊 Analytics Overview</h4>
          <p>Charts and course analytics will appear here (can integrate Chart.js or Recharts).</p>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
