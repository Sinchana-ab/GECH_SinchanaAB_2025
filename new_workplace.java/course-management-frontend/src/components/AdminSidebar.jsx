import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './AdminSidebar.css';

const AdminSidebar = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [collapsed, setCollapsed] = useState(false);

  const menuItems = [
    {
      title: 'Dashboard',
      icon: 'bi-speedometer2',
      path: '/admin',
      exact: true
    },
    {
      title: 'Users',
      icon: 'bi-people',
      path: '/admin/users'
    },
    {
      title: 'Instructors',
      icon: 'bi-person-video3',
      path: '/admin/instructors'
    },
    {
      title: 'Courses',
      icon: 'bi-book',
      path: '/admin/courses'
    },
    {
      title: 'Exams',
      icon: 'bi-clipboard-check',
      path: '/admin/exams'
    },
    {
      title: 'Certificates',
      icon: 'bi-award',
      path: '/admin/certificates'
    },
    {
      title: 'Reports',
      icon: 'bi-graph-up',
      path: '/admin/reports'
    },
    {
      title: 'Settings',
      icon: 'bi-gear',
      path: '/admin/settings'
    }
  ];

  const isActive = (path, exact = false) => {
    if (exact) {
      return location.pathname === path;
    }
    return location.pathname.includes(path);
  };

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <div className={`admin-sidebar ${collapsed ? 'collapsed' : ''}`}>
      <div className="sidebar-header">
        <Link to="/admin" className="sidebar-brand">
          <i className="bi bi-mortarboard-fill me-2"></i>
          {!collapsed && <span>EduLearn Admin</span>}
        </Link>
        <button 
          className="btn btn-link text-white p-0"
          onClick={() => setCollapsed(!collapsed)}
          title={collapsed ? 'Expand' : 'Collapse'}
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
            <div className="user-role">Administrator</div>
          </div>
        )}
      </div>

      <nav className="sidebar-nav">
        {menuItems.map((item, index) => (
          <div key={index} className="sidebar-item">
            <button
              onClick={() => handleNavigation(item.path)}
              className={`sidebar-link ${isActive(item.path, item.exact) ? 'active' : ''}`}
              title={collapsed ? item.title : ''}
            >
              <i className={`bi ${item.icon}`}></i>
              {!collapsed && <span>{item.title}</span>}
            </button>
          </div>
        ))}
      </nav>

      <div className="sidebar-footer">
        <Link to="/" className="sidebar-link">
          <i className="bi bi-box-arrow-left"></i>
          {!collapsed && <span>Back to Site</span>}
        </Link>
      </div>
    </div>
  );
};

export default AdminSidebar;