
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './InstructorSidebar.css';

const InstructorSidebar = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [collapsed, setCollapsed] = useState(false);

  const menuItems = [
    { 
      title: 'Dashboard', 
      icon: 'bi-speedometer2', 
      path: '/instructor', 
      exact: true 
    },
    { 
      title: 'My Courses', 
      icon: 'bi-book', 
      path: '/instructor/courses' 
    },
    { 
      title: 'Students', 
      icon: 'bi-people', 
      path: '/instructor/students' 
    },
    { 
      title: 'Analytics', 
      icon: 'bi-graph-up', 
      path: '/instructor/analytics' 
    },
    { 
      title: 'Settings', 
      icon: 'bi-gear', 
      path: '/instructor/settings' 
    }
  ];

  const isActive = (path, exact = false) => {
    if (exact) {
      return location.pathname === path;
    }
    return location.pathname.startsWith(path);
  };

  const handleNavigation = (path) => {
    navigate(path);
  };

  const handleLogout = () => {
    if (window.confirm('Are you sure you want to logout?')) {
      logout();
      navigate('/login');
    }
  };

  return (
    <div className={`instructor-sidebar ${collapsed ? 'collapsed' : ''}`}>
      {/* Sidebar Header */}
      <div className="sidebar-header">
        <button 
          onClick={() => handleNavigation('/instructor')} 
          className="sidebar-brand"
          title="EduLearn - Instructor Dashboard"
        >
          <i className="bi bi-mortarboard-fill"></i>
          {!collapsed && <span>EduLearn</span>}
        </button>
        <button 
          className="btn btn-link text-white p-0"
          onClick={() => setCollapsed(!collapsed)}
          title={collapsed ? 'Expand Sidebar' : 'Collapse Sidebar'}
        >
          <i className={`bi ${collapsed ? 'bi-chevron-right' : 'bi-chevron-left'}`}></i>
        </button>
      </div>

      {/* User Profile Section */}
      <div className="sidebar-user">
        <div className="user-avatar">
          <i className="bi bi-person-circle"></i>
        </div>
        {!collapsed && (
          <div className="user-details">
            <div className="user-name">{user?.name || 'Instructor'}</div>
            <div className="user-role">Instructor</div>
          </div>
        )}
      </div>

      {/* Navigation Menu */}
      <nav className="sidebar-nav">
        {menuItems.map((item, index) => (
          <div key={index} className="sidebar-item">
            <button
              onClick={() => handleNavigation(item.path)}
              className={`sidebar-link ${isActive(item.path, item.exact) ? 'active' : ''}`}
              data-title={item.title}
              title={collapsed ? item.title : ''}
            >
              <i className={`bi ${item.icon}`}></i>
              {!collapsed && <span>{item.title}</span>}
            </button>
          </div>
        ))}
      </nav>

      {/* Sidebar Footer */}
      <div className="sidebar-footer">
        <button 
          onClick={() => handleNavigation('/')} 
          className="sidebar-link"
          data-title="Back to Site"
          title={collapsed ? 'Back to Site' : ''}
        >
          <i className="bi bi-house-door"></i>
          {!collapsed && <span>Back to Site</span>}
        </button>
        
        <button 
          onClick={handleLogout} 
          className="sidebar-link"
          data-title="Logout"
          title={collapsed ? 'Logout' : ''}
        >
          <i className="bi bi-box-arrow-left"></i>
          {!collapsed && <span>Logout</span>}
        </button>
      </div>
    </div>
  );
};

export default InstructorSidebar;