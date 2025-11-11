import React, { useState, useEffect } from "react";
import API from "../../api";

export default function ManageCourses() {
  const [courses, setCourses] = useState([]);
  const [form, setForm] = useState({ title: "", description: "", instructorEmail: "" });
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const fetchCourses = async () => {
    try {
      const res = await API.get("/admin/courses");
      setCourses(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch courses.");
    }
  };

  useEffect(() => {
    fetchCourses();
  }, []);

  const handleSubmit = async () => {
    if (!form.title || !form.instructorEmail) {
      setError("Title and Instructor Email are required.");
      return;
    }

    setLoading(true);
    setError("");

    try {
      if (editId) {
        await API.put(`/admin/courses/${editId}`, form);
      } else {
        await API.post("/admin/courses", form);
      }
      setForm({ title: "", description: "", instructorEmail: "" });
      setEditId(null);
      fetchCourses();
    } catch (err) {
      console.error(err);
      setError(err.response?.data || "Failed to save course.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this course?")) return;
    try {
      await API.delete(`/admin/courses/${id}`);
      fetchCourses();
    } catch (err) {
      console.error(err);
      setError("Failed to delete course.");
    }
  };

  const handleEdit = (c) => {
    setForm({ title: c.title, description: c.description, instructorEmail: c.instructorEmail });
    setEditId(c.id);
  };

  return (
    <div className="card shadow-sm border-0 rounded-4">
      <div className="card-header bg-info text-white">
        <h5 className="mb-0">Manage Courses</h5>
      </div>
      <div className="card-body">
        {error && <div className="alert alert-danger">{error}</div>}

        <div className="mb-3">
          <input
            type="text"
            className="form-control mb-2"
            placeholder="Title"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
          />
          <input
            type="text"
            className="form-control mb-2"
            placeholder="Description"
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
          />
          <input
            type="email"
            className="form-control mb-2"
            placeholder="Instructor Email"
            value={form.instructorEmail}
            onChange={(e) => setForm({ ...form, instructorEmail: e.target.value })}
          />
          <button
            className="btn btn-success w-100"
            disabled={loading}
            onClick={handleSubmit}
          >
            {loading ? "Saving..." : editId ? "Update Course" : "Add Course"}
          </button>
        </div>

        <table className="table table-striped table-hover align-middle">
          <thead className="table-light">
            <tr>
              <th>Title</th>
              <th>Instructor</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {courses.map((c) => (
              <tr key={c.id}>
                <td>{c.title}</td>
                <td>{c.instructorEmail}</td>
                <td>
                  <button
                    className="btn btn-outline-primary btn-sm me-2"
                    onClick={() => handleEdit(c)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-outline-danger btn-sm"
                    onClick={() => handleDelete(c.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {courses.length === 0 && (
              <tr>
                <td colSpan="3" className="text-center text-muted">
                  No courses found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
