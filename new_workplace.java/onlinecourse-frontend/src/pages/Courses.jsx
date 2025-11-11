import React, { useEffect, useState, useContext } from "react";
import { getPublishedCourses, createCourse } from "../api";
import { AuthContext } from "../context/AuthContext";

export default function Courses() {
  const [courses, setCourses] = useState([]);
  const [newCourse, setNewCourse] = useState({ title:"", description:"" });
  const { user } = useContext(AuthContext);

  useEffect(() => {
    getPublishedCourses().then((res) => setCourses(res.data));
  }, []);

  const handleAddCourse = async (e) => {
    e.preventDefault();
    const res = await createCourse(newCourse);
    setCourses([...courses, res.data]);
    setNewCourse({ title:"", description:"" });
  };

  return (
    <div className="page">
      <h2>Published Courses</h2>
      <ul>
        {courses.map(c => (
          <li key={c.id}>
            <b>{c.title}</b> — {c.description}
            <span style={{marginLeft:"10px",color:"gray"}}>
              by {c.instructorName || "Unknown"}
            </span>
          </li>
        ))}
      </ul>

      {user && (user.role === "ROLE_INSTRUCTOR" || user.role === "ROLE_ADMIN") && (
        <form onSubmit={handleAddCourse}>
          <h3>Add Course</h3>
          <input placeholder="Title"
                 value={newCourse.title}
                 onChange={(e)=>setNewCourse({...newCourse,title:e.target.value})} required />
          <textarea placeholder="Description"
                    value={newCourse.description}
                    onChange={(e)=>setNewCourse({...newCourse,description:e.target.value})} required />
          <button type="submit">Add</button>
        </form>
      )}
    </div>
  );
}
