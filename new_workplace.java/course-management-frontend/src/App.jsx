import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';

// ===== LAYOUT COMPONENTS =====
import ModernNavbar from './components/ModernNavbar';
import ModernFooter from './components/ModernFooter';


// ===== DASHBOARD COMPONENTS =====
import ImprovedStudentDashboard from './pages/ImprovedStudentDashboard';
import CompleteInstructorDashboard from './pages/CompleteInstructorDashboard';
import ImprovedAdminDashboard from './pages/ImprovedAdminDashboard';

// ===== PUBLIC PAGES =====
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import CourseList from './pages/CourseList';
import CourseDetail from './pages/CourseDetail';
import CourseMaterialViewer from './pages/CourseMaterialViewer';

// ===== AUTH PAGES =====
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';

// ===== PROTECTED PAGES =====
import MyCourses from './pages/MyCourses';
import QuizPage from './pages/QuizPage';
import Certificates from './pages/Certificates';
import CourseMaterials from './pages/CourseMaterials';

// ===== STYLES =====
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './index.css';

// ===== PROTECTED ROUTE COMPONENT =====
const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <div className="text-center">
          <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }} role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3 text-muted">Loading...</p>
        </div>
      </div>
    );
  }

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && allowedRoles.length > 0) {
    const userRoles = user.roles || [user.role];
    const hasRequiredRole = allowedRoles.some(role => userRoles.includes(role));
    
    if (!hasRequiredRole) {
      return <Navigate to="/dashboard" replace />;
    }
  }

  return children;
};

// ===== MAIN APP COMPONENT =====
function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          {/* ===== MODERN NAVBAR ===== */}
          <ModernNavbar />
          
          {/* ===== MAIN CONTENT AREA ===== */}
          <div style={{ minHeight: 'calc(100vh - 200px)' }}>
            <Routes>
              {/* ========================================
                  PUBLIC ROUTES (No Authentication Required)
                  ======================================== */}
              <Route path="/" element={<Home />} />
              <Route path="/about" element={<About />} />
              <Route path="/contact" element={<Contact />} />
              <Route path="/courses" element={<CourseList />} />
              <Route path="/courses/:id" element={<CourseDetail />} />
              
              {/* ========================================
                  AUTHENTICATION ROUTES
                  ======================================== */}
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              
              {/* ========================================
                  MAIN DASHBOARD ROUTER
                  (Redirects to role-specific dashboard)
                  ======================================== */}
              <Route 
                path="/dashboard" 
                element={
                  <ProtectedRoute>
                    <Dashboard />
                  </ProtectedRoute>
                } 
              />

              {/* ========================================
                  STUDENT ROUTES
                  ======================================== */}
              {/* Student Dashboard with all sub-routes */}
              <Route 
                path="/student/*" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_STUDENT']}>
                    <ImprovedStudentDashboard />
                  </ProtectedRoute>
                } 
              />
              
              {/* Legacy My Courses Route (for backward compatibility) */}
              <Route 
                path="/my-courses" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_STUDENT']}>
                    <MyCourses />
                  </ProtectedRoute>
                } 
              />
              
              {/* Quiz Page */}
              <Route 
                path="/quiz/:quizId" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_STUDENT']}>
                    <QuizPage />
                  </ProtectedRoute>
                } 
              />
              <Route 
                  path="/courses/:courseId/learn" 
                  element={
                    <ProtectedRoute allowedRoles={['ROLE_STUDENT']}>
                      <CourseMaterialViewer />
                    </ProtectedRoute>
                  } 
                />
              
              {/* Certificates Page */}
              <Route 
                path="/certificates" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_STUDENT']}>
                    <Certificates />
                  </ProtectedRoute>
                } 
              />

              {/* ========================================
                  INSTRUCTOR ROUTES
                  ======================================== */}
              {/* Instructor Dashboard with all sub-routes */}
              <Route 
                path="/instructor/*" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_INSTRUCTOR', 'ROLE_ADMIN']}>
                    <CompleteInstructorDashboard />
                  </ProtectedRoute>
                } 
              />
              
              {/* Legacy Instructor Dashboard Route (for backward compatibility) */}
              <Route 
                path="/instructor-dashboard" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_INSTRUCTOR', 'ROLE_ADMIN']}>
                    <CompleteInstructorDashboard />
                  </ProtectedRoute>
                } 
              />

              {/* ========================================
                  ADMIN ROUTES
                  ======================================== */}
              {/* Admin Dashboard with all sub-routes */}
              <Route 
                path="/admin/*" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                    <ImprovedAdminDashboard />
                  </ProtectedRoute>
                } 
              />

              {/* ========================================
                  SHARED PROTECTED ROUTES
                  (Accessible by multiple roles)
                  ======================================== */}
              {/* Course Materials - Accessible by Students and Instructors */}
              <Route 
                path="/courses/:courseId/materials" 
                element={
                  <ProtectedRoute allowedRoles={['ROLE_STUDENT', 'ROLE_INSTRUCTOR', 'ROLE_ADMIN']}>
                    <CourseMaterials />
                  </ProtectedRoute>
                } 
              />

              {/* ========================================
                  404 NOT FOUND ROUTE
                  ======================================== */}
              <Route 
                path="*" 
                element={
                  <div className="container text-center py-5">
                    <div className="modern-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
                      <i className="bi bi-exclamation-triangle-fill text-warning" style={{ fontSize: '5rem' }}></i>
                      <h2 className="mt-4">404 - Page Not Found</h2>
                      <p className="text-muted mb-4">The page you're looking for doesn't exist.</p>
                      <a href="/" className="btn btn-gradient btn-lg">
                        <i className="bi bi-house-door me-2"></i>
                        Go Home
                      </a>
                    </div>
                  </div>
                } 
              />
            </Routes>
          </div>
          
          {/* ===== MODERN FOOTER ===== */}
          <ModernFooter />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;