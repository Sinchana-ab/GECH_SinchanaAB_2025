import axios from "axios";


const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const registerUser = (data) => API.post("/auth/register", data);
export const loginUser = (data) => API.post("/auth/login", data);
export const addInstructor = (data) => API.post("/admin/instructors", data);
export const getInstructors = () => API.get("/admin/instructors");
export const deleteInstructor = (id) => API.delete(`/admin/instructors/${id}`);

export const createCourse = (data) => API.post("/admin/courses", data);
export const getCourses = () => API.get("/admin/courses");
export const deleteCourse = (id) => API.delete(`/admin/courses/${id}`);

API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export default API;
