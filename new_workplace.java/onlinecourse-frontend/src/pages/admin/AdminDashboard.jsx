import React, { useEffect, useState } from "react";
import axios from "axios";
import { Button, Form, Table, Alert } from "react-bootstrap";

export default function AdminDashboard() {
  const [instructors, setInstructors] = useState([]);
  const [courses, setCourses] = useState([]);
  const [instructorForm, setInstructorForm] = useState({ name: "", email: "", password: "" });
  const [courseForm, setCourseForm] = useState({ title: "", description: "", instructor: "" });
  const [message, setMessage] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchInstructors();
    fetchCourses();
  }, []);

  const fetchInstructors = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/admin/instructors", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setInstructors(res.data);
    } catch {
      setMessage({ type: "danger", text: "Failed to load instructors" });
    }
  };

  const fetchCourses = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/admin/courses", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCourses(res.data);
    } catch {
      setMessage({ type: "danger", text: "Failed to load courses" });
    }
  };

  const addInstructor = async () => {
    try {
      await axios.post("http://localhost:8080/api/admin/instructors", instructorForm, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchInstructors();
      setInstructorForm({ name: "", email: "", password: "" });
      setMessage({ type: "success", text: "Instructor added successfully!" });
    } catch {
      setMessage({ type: "danger", text: "Failed to save instructor." });
    }
  };

  const addCourse = async () => {
    try {
      await axios.post("http://localhost:8080/api/admin/courses", courseForm, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchCourses();
      setCourseForm({ title: "", description: "", instructor: "" });
      setMessage({ type: "success", text: "Course added successfully!" });
    } catch {
      setMessage({ type: "danger", text: "Failed to save course." });
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">Admin Dashboard</h2>
      {message && (
        <Alert variant={message.type} onClose={() => setMessage(null)} dismissible>
          {message.text}
        </Alert>
      )}
      <div className="row">
        {/* Manage Instructors */}
        <div className="col-md-6 mb-4">
          <div className="card shadow-sm">
            <div className="card-header bg-primary text-white fw-bold">Manage Instructors</div>
            <div className="card-body">
              <Form>
                <Form.Control
                  placeholder="Name"
                  className="mb-2"
                  value={instructorForm.name}
                  onChange={(e) => setInstructorForm({ ...instructorForm, name: e.target.value })}
                />
                <Form.Control
                  placeholder="Email"
                  className="mb-2"
                  value={instructorForm.email}
                  onChange={(e) => setInstructorForm({ ...instructorForm, email: e.target.value })}
                />
                <Form.Control
                  placeholder="Password"
                  className="mb-3"
                  type="password"
                  value={instructorForm.password}
                  onChange={(e) => setInstructorForm({ ...instructorForm, password: e.target.value })}
                />
                <Button variant="success" className="w-100" onClick={addInstructor}>
                  Add Instructor
                </Button>
              </Form>

              <Table bordered hover size="sm" className="mt-3">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                  </tr>
                </thead>
                <tbody>
                  {instructors.length > 0 ? (
                    instructors.map((i) => (
                      <tr key={i.id}>
                        <td>{i.name}</td>
                        <td>{i.email}</td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td colSpan="2" className="text-center text-muted">
                        No instructors found
                      </td>
                    </tr>
                  )}
                </tbody>
              </Table>
            </div>
          </div>
        </div>

        {/* Manage Courses */}
        <div className="col-md-6 mb-4">
          <div className="card shadow-sm">
            <div className="card-header bg-info text-white fw-bold">Manage Courses</div>
            <div className="card-body">
              <Form>
                <Form.Control
                  placeholder="Title"
                  className="mb-2"
                  value={courseForm.title}
                  onChange={(e) => setCourseForm({ ...courseForm, title: e.target.value })}
                />
                <Form.Control
                  placeholder="Description"
                  className="mb-2"
                  value={courseForm.description}
                  onChange={(e) => setCourseForm({ ...courseForm, description: e.target.value })}
                />
                <Form.Control
                  placeholder="Instructor Email"
                  className="mb-3"
                  value={courseForm.instructor}
                  onChange={(e) => setCourseForm({ ...courseForm, instructor: e.target.value })}
                />
                <Button variant="success" className="w-100" onClick={addCourse}>
                  Add Course
                </Button>
              </Form>

              <Table bordered hover size="sm" className="mt-3">
                <thead>
                  <tr>
                    <th>Title</th>
                    <th>Instructor</th>
                  </tr>
                </thead>
                <tbody>
                  {courses.length > 0 ? (
                    courses.map((c) => (
                      <tr key={c.id}>
                        <td>{c.title}</td>
                        <td>{c.instructorName || "—"}</td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td colSpan="2" className="text-center text-muted">
                        No courses found
                      </td>
                    </tr>
                  )}
                </tbody>
              </Table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
