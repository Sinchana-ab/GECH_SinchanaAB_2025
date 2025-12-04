// services/api.js - Updated for Exam system
import axios from 'axios';
import { materialAPI } from './materialAPI';

// const API_URL = import.meta.env.VITE_API_URL 
//   ? `${import.meta.env.VITE_API_URL}/api` 
//   : 'http://localhost:8080/api';

// axios.defaults.withCredentials = true;

// const getAuthHeader = () => {
//   const user = JSON.parse(localStorage.getItem('user') || '{}');
//   if (user.email && user.password) {
//     return {
//       auth: {
//         username: user.email,
//         password: user.password
//       }
//     };
//   }
//   return {};
// };

// Course APIs
// services/api.js - FIXED COURSE API SECTION


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
      },
      withCredentials: true
    };
  }
  return { withCredentials: true };
};

// ===== COURSE APIs =====
export const courseAPI = {
  // Public - anyone can view
  getAllPublished: () => axios.get(`${API_URL}/public/courses`),
  
  getCourseById: (id) => axios.get(`${API_URL}/public/courses/${id}`),
  
  searchCourses: (keyword) => 
    axios.get(`${API_URL}/public/courses/search`, { params: { keyword } }),
  
  // Instructor - can only manage their own courses
  getInstructorCourses: (instructorId) => 
    axios.get(`${API_URL}/instructor/courses`, { 
      params: { instructorId },
      ...getAuthHeader()
    }),
  
  createCourse: (courseData) => 
    axios.post(`${API_URL}/instructor/courses`, courseData, getAuthHeader()),
  
  updateCourse: (id, courseData) => 
    axios.put(`${API_URL}/instructor/courses/${id}`, courseData, getAuthHeader()),
  
  // Instructor delete - can only delete own courses
  deleteCourse: (id) => 
    axios.delete(`${API_URL}/instructor/courses/${id}`, getAuthHeader()),
  
  // Admin - can manage all courses
  adminGetAllCourses: () =>
    axios.get(`${API_URL}/admin/courses`, getAuthHeader()),
  
  adminDeleteCourse: (id) =>
    axios.delete(`${API_URL}/admin/courses/${id}`, getAuthHeader()),
};

// Export the API_URL for use in other files

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

// Exam APIs (NEW - Different from Quiz)
export const examAPI = {
  // Get final exam for a course
  getCourseExam: (courseId) =>
    axios.get(`${API_URL}/public/courses/${courseId}/exam`),
  
  // Start exam attempt
  startExam: (examId, studentId, enrollmentId) => 
    axios.post(`${API_URL}/student/exams/start`, 
      { examId, studentId, enrollmentId }, 
      getAuthHeader()
    ),
  
  // Submit exam
  submitExam: (attemptId, answers) => 
    axios.post(`${API_URL}/student/exams/submit/${attemptId}`, 
      { answers }, 
      getAuthHeader()
    ),
  
  // Get exam attempts
  getAttempts: (studentId, examId) => 
    axios.get(`${API_URL}/student/exams/attempts`, { 
      params: { studentId, examId },
      ...getAuthHeader()
    }),
  
  // Get best attempt
  getBestAttempt: (enrollmentId) =>
    axios.get(`${API_URL}/student/exams/best-attempt/${enrollmentId}`, 
      getAuthHeader()
    ),
};

// Quiz APIs (Keep existing for practice quizzes)
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
export const certificateAPI = {
  getStudentCertificates: (studentId) =>
    axios.get(`${API_URL}/student/certificates`, {
      params: { studentId },
      ...getAuthHeader()
    }),
  
  checkEligibility: (enrollmentId) =>
    axios.get(`${API_URL}/student/enrollments/${enrollmentId}/certificate-eligibility`, 
      getAuthHeader()
    ),
  
  requestCertificate: (enrollmentId) =>
    axios.post(`${API_URL}/student/enrollments/${enrollmentId}/request-certificate`, 
      {}, 
      getAuthHeader()
    ),
  
  downloadCertificate: (id) =>
    axios.get(`${API_URL}/student/certificates/download/${id}`, {
      ...getAuthHeader(),
      responseType: 'blob'
    }),
  
  verifyCertificate: (certificateNumber) =>
    axios.get(`${API_URL}/public/certificates/verify/${certificateNumber}`)
};

// Progress Tracking APIs
export const progressAPI = {
  markMaterialComplete: (materialId, studentId, enrollmentId) =>
    axios.post(`${API_URL}/student/materials/${materialId}/complete`, null, {
      params: { studentId, enrollmentId },
      ...getAuthHeader()
    }),
    
  updateVideoProgress: (materialId, data) =>
    axios.post(`${API_URL}/student/materials/${materialId}/watch-time`, data, 
      getAuthHeader()
    ),
    
  getMaterialProgress: (studentId, enrollmentId) =>
    axios.get(`${API_URL}/student/materials/progress`, {
      params: { studentId, enrollmentId },
      ...getAuthHeader()
    }),
};

// Rating APIs
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

export { 
  materialAPI,
  API_URL 
};

export default {
  courseAPI,
  enrollmentAPI,
  examAPI,    // NEW
  quizAPI,
  userAPI,
  certificateAPI,
  ratingAPI,
  progressAPI,
  materialAPI
};