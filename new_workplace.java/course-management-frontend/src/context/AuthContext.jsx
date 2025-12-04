import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const API_URL = 'http://localhost:8080/api';

  // Configure axios defaults
  axios.defaults.withCredentials = true;

  useEffect(() => {
    checkAuth();
  }, []);

  const checkAuth = async () => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const userData = JSON.parse(storedUser);
      setUser(userData);
      
      // Set auth header for all requests
      axios.defaults.auth = {
        username: userData.email,
        password: userData.password
      };
    }
    setLoading(false);
  };

  const login = async (email, password) => {
    try {
      // FIXED: Use Basic Auth properly
      const response = await axios.post(`${API_URL}/auth/login`, {
        email,
        password
      }, {
        auth: {
          username: email,
          password: password
        },
        withCredentials: true
      });

      if (response.data.success) {
        const userData = {
          ...response.data.data,
          email,
          password // Store for Basic Auth
        };
        setUser(userData);
        localStorage.setItem('user', JSON.stringify(userData));
        
        // Set default auth for all future requests
        axios.defaults.auth = {
          username: email,
          password: password
        };
        
        return { success: true };
      }
      return { success: false, message: response.data.message };
    } catch (error) {
      console.error('Login error:', error);
      return { 
        success: false, 
        message: error.response?.data?.message || 'Login failed. Please check your credentials.' 
      };
    }
  };

  const register = async (userData) => {
    try {
      const response = await axios.post(`${API_URL}/auth/register`, userData);
      if (response.data.success) {
        return { success: true, message: 'Registration successful! Please login.' };
      }
      return { success: false, message: response.data.message };
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || 'Registration failed' 
      };
    }
  };

  const logout = async () => {
    try {
      await axios.post(`${API_URL}/auth/logout`, {}, {
        withCredentials: true
      });
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      setUser(null);
      localStorage.removeItem('user');
      axios.defaults.auth = null;
    }
  };

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    isAuthenticated: !!user,
    isAdmin: user?.role === 'ROLE_ADMIN',
    isInstructor: user?.role === 'ROLE_INSTRUCTOR' || user?.role === 'ROLE_ADMIN',
    isStudent: user?.role === 'ROLE_STUDENT'
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
};