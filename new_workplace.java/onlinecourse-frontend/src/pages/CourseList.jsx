import React, { useEffect, useState } from "react";
import api from "../api";        

import { useAuth } from "../context/AuthContext";
import { Link } from "react-router-dom";

function CourseList() {
  const [courses, setCourses] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    api.get("/api/courses")
      .then((res) => setCourses(res.data))
      .catch((err) => console.error("Error fetching courses:", err));
  }, []);

  return (
    <div className="course-list">
      <h2>Published Courses</h2>

      {user && (user.role === "ROLE_INSTRUCTOR" || user.role === "ROLE_ADMIN") && (
        <Link to="/create" className="btn">+ Add New Course</Link>
      )}

      <div className="grid">
        {courses.map((c) => (
          <div key={c.id} className="card">
            <h3>{c.title}</h3>
            <p>{c.description}</p>
            <p><strong>Instructor:</strong> {c.instructorName}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CourseList;
