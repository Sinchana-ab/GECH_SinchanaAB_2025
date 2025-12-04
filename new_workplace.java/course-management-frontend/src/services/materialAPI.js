// services/materialAPI.js - COMPLETE WITH ALL CRUD OPERATIONS
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL 
  ? `${import.meta.env.VITE_API_URL}/api` 
  : 'http://localhost:8080/api';

const getAuthHeader = () => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  if (user.email && user.password) {
    return {
      auth: {
        username: user.email,
        password: user.password
      },
      withCredentials: true
    };
  }
  return { withCredentials: true };
};

export const materialAPI = {
  // ===== CREATE =====
  
  // Upload file (video, PDF, document, image)
  uploadMaterial: (courseId, formData) =>
    axios.post(`${API_URL}/instructor/courses/${courseId}/materials/upload`, formData, {
      ...getAuthHeader(),
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }),
  
  // Add external link (YouTube, etc.)
  addExternalLink: (courseId, data) =>
    axios.post(`${API_URL}/instructor/courses/${courseId}/materials/link`, data, getAuthHeader()),
  
  // ===== READ =====
  
  // Get all materials for a course
  getCourseMaterials: (courseId) =>
    axios.get(`${API_URL}/courses/${courseId}/materials`, getAuthHeader()),
  
  // Get material by ID
  getMaterialById: (id) =>
    axios.get(`${API_URL}/materials/${id}`, getAuthHeader()),
  
  // ===== UPDATE =====
  
  // Update material metadata
  updateMaterial: (id, data) =>
    axios.put(`${API_URL}/instructor/materials/${id}`, data, getAuthHeader()),
  
  // ===== DELETE =====
  
  // Delete material
  deleteMaterial: (id) =>
    axios.delete(`${API_URL}/instructor/materials/${id}`, getAuthHeader()),
  
  // ===== FILE ACCESS =====
  
  // Get file download/stream URL with authentication
  getFileUrl: (fileName) => {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    if (user.email && user.password) {
      const credentials = btoa(`${user.email}:${user.password}`);
      return `${API_URL}/courses/materials/download/${fileName}?auth=${credentials}`;
    }
    return `${API_URL}/courses/materials/download/${fileName}`;
  },

  // Get authenticated file blob for preview
  getFileBlob: async (fileName) => {
    try {
      const response = await axios.get(
        `${API_URL}/courses/materials/download/${fileName}`,
        {
          ...getAuthHeader(),
          responseType: 'blob'
        }
      );
      return URL.createObjectURL(response.data);
    } catch (error) {
      console.error('Error fetching file blob:', error);
      throw error;
    }
  }
};

export default materialAPI;