// services/api.js - Updated for Vite with Rating APIs
import axios from 'axios';

// Add to services/api.js
export { materialAPI } from './materialAPI';

// // Or if you want to keep it in the same file:
// export const materialAPI = {
//   uploadMaterial: (courseId, formData) =>
//     axios.post(`${API_URL}/instructor/courses/${courseId}/materials/upload`, formData, {
//       ...getAuthHeader(),
//       headers: { 'Content-Type': 'multipart/form-data' }
//     }),
  
//   addExternalLink: (courseId, data) =>
//     axios.post(`${API_URL}/instructor/courses/${courseId}/materials/link`, data, getAuthHeader()),
  
//   getCourseMaterials: (courseId) =>
//     axios.get(`${API_URL}/courses/${courseId}/materials`),
  
//   updateMaterial: (id, data) =>
//     axios.put(`${API_URL}/instructor/materials/${id}`, data, getAuthHeader()),
  
//   deleteMaterial: (id) =>
//     axios.delete(`${API_URL}/instructor/materials/${id}`, getAuthHeader()),
  
//   getFileUrl: (fileName) => `${API_URL}/courses/materials/download/${fileName}`
// };
// Use Vite environment variable or fallback
const API_URL = import.meta.env.VITE_API_URL 
  ? `${import.meta.env.VITE_API_URL}/api` 
  : 'http://localhost:8080/api';

axios.defaults.withCredentials = true;

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
// services/api.js - Update certificateAPI

// Course APIs
export const courseAPI = {
  getAllPublished: () => axios.get(`${API_URL}/public/courses`),
  
  getCourseById: (id) => axios.get(`${API_URL}/public/courses/${id}`),
  
  searchCourses: (keyword) => 
    axios.get(`${API_URL}/public/courses/search`, { params: { keyword } }),
  
  getInstructorCourses: (instructorId) => 
    axios.get(`${API_URL}/instructor/courses`, { 
      params: { instructorId },
      ...getAuthHeader()
    }),
  
  createCourse: (courseData) => 
    axios.post(`${API_URL}/instructor/courses`, courseData, getAuthHeader()),
  
  updateCourse: (id, courseData) => 
    axios.put(`${API_URL}/instructor/courses/${id}`, courseData, getAuthHeader()),
  
  deleteCourse: (id) => 
    axios.delete(`${API_URL}/instructor/courses/${id}`, getAuthHeader()),
};

// Enrollment APIs
export const enrollmentAPI = {
  enrollInCourse: (studentId, courseId) => 
    axios.post(`${API_URL}/student/enroll`, 
      { studentId, courseId }, 
      getAuthHeader()
    ),
  
  getStudentEnrollments: (studentId) => 
    axios.get(`${API_URL}/student/enrollments`, { 
      params: { studentId },
      ...getAuthHeader()
    }),
  
  updateProgress: (enrollmentId, progress) => 
    axios.put(`${API_URL}/student/enrollments/${enrollmentId}/progress`, 
      { progress }, 
      getAuthHeader()
    ),
  
  getCourseEnrollments: (courseId) => 
    axios.get(`${API_URL}/student/enrollments/course/${courseId}`, getAuthHeader()),
};

// Quiz APIs
export const quizAPI = {
  startQuiz: (quizId, studentId) => 
    axios.post(`${API_URL}/student/quiz/start`, 
      { quizId, studentId }, 
      getAuthHeader()
    ),
  
  submitQuiz: (attemptId, answers, score, totalPoints) => 
    axios.post(`${API_URL}/student/quiz/submit/${attemptId}`, 
      { answers, score, totalPoints }, 
      getAuthHeader()
    ),
  
  getAttempts: (studentId, quizId) => 
    axios.get(`${API_URL}/student/quiz/attempts`, { 
      params: { studentId, quizId },
      ...getAuthHeader()
    }),
};

// User APIs
export const userAPI = {
  getAllUsers: () => 
    axios.get(`${API_URL}/admin/users`, getAuthHeader()),
  
  getUsersByRole: (role) => 
    axios.get(`${API_URL}/admin/users/role/${role}`, getAuthHeader()),
  
  updateUser: (id, userData) => 
    axios.put(`${API_URL}/admin/users/${id}`, userData, getAuthHeader()),
  
  deleteUser: (id) => 
    axios.delete(`${API_URL}/admin/users/${id}`, getAuthHeader()),
};

// Certificate APIs
// services/api.js - Update certificateAPI
export const certificateAPI = {
  getStudentCertificates: (studentId) =>
    axios.get(`${API_URL}/student/certificates`, {
      params: { studentId },
      ...getAuthHeader()
    }),
  
  generateCertificate: (studentId, courseId, finalScore) =>
    axios.post(`${API_URL}/student/certificates/generate`,
      { studentId, courseId, finalScore },
      getAuthHeader()
    ),
  
  // FIXED: Added proper syntax
  downloadCertificate: (id) =>
    axios.get(`${API_URL}/student/certificates/download/${id}`, {
      ...getAuthHeader(),
      responseType: 'blob' // IMPORTANT: This ensures proper PDF handling
    }),
  
  // NEW: Preview certificate endpoint
  previewCertificate: (id) =>
    axios.get(`${API_URL}/student/certificates/preview/${id}`, {
      ...getAuthHeader(),
      responseType: 'blob'
    }),
  
  verifyCertificate: (certificateNumber) =>
    axios.get(`${API_URL}/public/certificates/verify/${certificateNumber}`)
};

export const progressAPI = {
  markMaterialComplete: (materialId, studentId, enrollmentId) =>
    axios.post(`/api/student/materials/${materialId}/complete`, null, {
      params: { studentId, enrollmentId }
    }),
    
  updateVideoProgress: (materialId, data) =>
    axios.post(`/api/student/materials/${materialId}/watch-time`, data),
    
  getMaterialProgress: (studentId, enrollmentId) =>
    axios.get(`/api/student/materials/progress`, {
      params: { studentId, enrollmentId }
    }),
    
  checkCertificateEligibility: (enrollmentId) =>
    axios.get(`/api/student/enrollments/${enrollmentId}/certificate-eligibility`),
    
  requestCertificate: (enrollmentId) =>
    axios.post(`/api/student/enrollments/${enrollmentId}/request-certificate`)
};

// Rating APIs (NEW!)
export const ratingAPI = {
  submitRating: (studentId, courseId, rating, review) =>
    axios.post(`${API_URL}/student/ratings`, 
      { studentId, courseId, rating, review },
      getAuthHeader()
    ),
  
  getCourseRatings: (courseId) =>
    axios.get(`${API_URL}/public/courses/${courseId}/ratings`),
  
  getTopRatings: (courseId) =>
    axios.get(`${API_URL}/public/courses/${courseId}/ratings/top`),
  
  getRatingStatistics: (courseId) =>
    axios.get(`${API_URL}/public/courses/${courseId}/ratings/statistics`),
  
  getStudentRatings: (studentId) =>
    axios.get(`${API_URL}/student/ratings`, {
      params: { studentId },
      ...getAuthHeader()
    }),
  
  markAsHelpful: (ratingId) =>
    axios.put(`${API_URL}/student/ratings/${ratingId}/helpful`, {}, getAuthHeader()),
  
  deleteRating: (ratingId) =>
    axios.delete(`${API_URL}/student/ratings/${ratingId}`, getAuthHeader())
};

// Export API_URL if needed elsewhere
export { API_URL };

export default {
  courseAPI,
  enrollmentAPI,
  quizAPI,
  userAPI,
  certificateAPI,
  ratingAPI
};