
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './ModernNavbar.css';

const ModernNavbar = () => {
  const { user, logout, isAuthenticated, isAdmin, isInstructor } = useAuth();
  const navigate = useNavigate();
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 50);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className={`modern-navbar navbar navbar-expand-lg ${scrolled ? 'scrolled' : ''}`}>
      <div className="container">
        <Link className="navbar-brand" to="/">
          <i className="bi bi-mortarboard-fill me-2"></i>
          <span className="brand-text">EduLearn</span>
        </Link>
        
        <button 
          className="navbar-toggler" 
          type="button" 
          data-bs-toggle="collapse" 
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav mx-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                <i className="bi bi-house-door me-1"></i>
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/courses">
                <i className="bi bi-book me-1"></i>
                Courses
              </Link>
            </li>
            <li className="nav-item">
                          <Link className="nav-link" to="/about">
                           <i className="bi bi-book me-1"></i>
                           About
                           </Link>
                        </li>
                        <li className="nav-item">
                          <Link className="nav-link" to="/contact">
                          <i className="bi bi-book me-1"></i>Contact</Link>
                        </li>
            {isAuthenticated && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/dashboard">
                    <i className="bi bi-speedometer2 me-1"></i>
                    Dashboard
                  </Link>
                </li>
                {user?.role === 'ROLE_STUDENT' && (
                  <>
                    <li className="nav-item">
                      <Link className="nav-link" to="/my-courses">
                        <i className="bi bi-journal-bookmark me-1"></i>
                        My Courses
                      </Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" to="/certificates">
                        <i className="bi bi-award me-1"></i>
                        Certificates
                      </Link>
                    </li>
                  </>
                )}
                {isInstructor && !isAdmin && (
                  <li className="nav-item">
                    <Link className="nav-link" to="/instructor">
                      <i className="bi bi-person-video3 me-1"></i>
                      Instructor
                    </Link>
                  </li>
                )}
                {isAdmin && (
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin">
                      <i className="bi bi-shield-check me-1"></i>
                      Admin
                    </Link>
                  </li>
                )}
              </>
            )}
          </ul>
          
          <div className="d-flex align-items-center gap-3">
            {isAuthenticated ? (
              <>
                <div className="user-info">
                  <i className="bi bi-person-circle me-2"></i>
                  <span className="user-name">{user?.name}</span>
                </div>
                <button className="btn btn-outline-danger rounded-pill" onClick={handleLogout}>
                  <i className="bi bi-box-arrow-right me-1"></i>
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link className="btn btn-outline-primary rounded-pill" to="/login">
                  Login
                </Link>
                <Link className="btn btn-gradient" to="/register">
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default ModernNavbar;