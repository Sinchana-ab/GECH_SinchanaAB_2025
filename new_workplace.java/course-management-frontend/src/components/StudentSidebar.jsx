import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './StudentSidebar.css';

const StudentSidebar = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [collapsed, setCollapsed] = useState(false);

  const menuItems = [
    { title: 'Dashboard', icon: 'bi-speedometer2', path: '/dashboard', exact: true },
    { title: 'My Courses', icon: 'bi-book', path: '/student/my-courses' },
    { title: 'Browse Courses', icon: 'bi-search', path: '/student/browse' },
    { title: 'Certificates', icon: 'bi-award', path: '/certificates' },
    { title: 'Progress', icon: 'bi-graph-up', path: '/student/progress' },
    { title: 'Settings', icon: 'bi-gear', path: '/student/settings' }
  ];

  const isActive = (path, exact = false) => {
    if (exact) return location.pathname === path;
    return location.pathname.includes(path);
  };

  return (
    <div className={`student-sidebar ${collapsed ? 'collapsed' : ''}`}>
      <div className="sidebar-header">
        <button onClick={() => navigate('/dashboard')} className="sidebar-brand">
          <i className="bi bi-mortarboard-fill me-2"></i>
          {!collapsed && <span>EduLearn</span>}
        </button>
        <button 
          className="btn btn-link text-white p-0"
          onClick={() => setCollapsed(!collapsed)}
        >
          <i className={`bi ${collapsed ? 'bi-chevron-right' : 'bi-chevron-left'}`}></i>
        </button>
      </div>

      <div className="sidebar-user">
        <div className="user-avatar">
          <i className="bi bi-person-circle"></i>
        </div>
        {!collapsed && (
          <div className="user-details">
            <div className="user-name">{user?.name}</div>
            <div className="user-role">Student</div>
          </div>
        )}
      </div>

      <nav className="sidebar-nav">
        {menuItems.map((item, index) => (
          <div key={index} className="sidebar-item">
            <button
              onClick={() => navigate(item.path)}
              className={`sidebar-link ${isActive(item.path, item.exact) ? 'active' : ''}`}
            >
              <i className={`bi ${item.icon}`}></i>
              {!collapsed && <span>{item.title}</span>}
            </button>
          </div>
        ))}
      </nav>
{/* Exam Button - Show if materials completed but exam not passed */}

      <div className="sidebar-footer">
        <button onClick={() => navigate('/')} className="sidebar-link">
          <i className="bi bi-box-arrow-left"></i>
          {!collapsed && <span>Back to Site</span>}
        </button>
      </div>
    </div>
  );
};

export default StudentSidebar;