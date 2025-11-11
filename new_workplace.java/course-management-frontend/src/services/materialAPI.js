// services/materialAPI.js
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
      }
    };
  }
  return {};
};

export const materialAPI = {
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
  
  // Get all materials for a course
  getCourseMaterials: (courseId) =>
    axios.get(`${API_URL}/courses/${courseId}/materials`),
  
  // Update material metadata
  updateMaterial: (id, data) =>
    axios.put(`${API_URL}/instructor/materials/${id}`, data, getAuthHeader()),
  
  // Delete material
  deleteMaterial: (id) =>
    axios.delete(`${API_URL}/instructor/materials/${id}`, getAuthHeader()),
  
  // Get file download/stream URL
  getFileUrl: (fileName) => 
    `${API_URL}/courses/materials/download/${fileName}`
};

export default materialAPI;