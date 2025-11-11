import React, { useState, useEffect } from "react";
import API from "../../api";

export default function ManageInstructors() {
  const [instructors, setInstructors] = useState([]);
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const [editId, setEditId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchInstructors = async () => {
    try {
      const res = await API.get("/admin/instructors");
      setInstructors(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch instructors.");
    }
  };

  useEffect(() => {
    fetchInstructors();
  }, []);

  const handleSubmit = async () => {
    if (!form.name || !form.email) {
      setError("Name and Email are required.");
      return;
    }

    setLoading(true);
    setError("");

    try {
      if (editId) {
        // Update instructor
        await API.put(`/admin/instructors/${editId}`, form);
      } else {
        // Add new instructor
        await API.post("/admin/instructors", form);
      }

      setForm({ name: "", email: "", password: "" });
      setEditId(null);
      fetchInstructors();
    } catch (err) {
      console.error(err);
      setError(err.response?.data || "Failed to save instructor.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this instructor?")) return;
    try {
      await API.delete(`/admin/instructors/${id}`);
      fetchInstructors();
    } catch (err) {
      console.error(err);
      setError("Failed to delete instructor.");
    }
  };

  const handleEdit = (i) => {
    setForm({ name: i.name, email: i.email, password: "" });
    setEditId(i.id);
  };

  return (
    <div className="card shadow-sm border-0 rounded-4">
      <div className="card-header bg-primary text-white">
        <h5 className="mb-0">Manage Instructors</h5>
      </div>
      <div className="card-body">
        {error && <div className="alert alert-danger">{error}</div>}

        <div className="mb-3">
          <input
            type="text"
            className="form-control mb-2"
            placeholder="Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
          />
          <input
            type="email"
            className="form-control mb-2"
            placeholder="Email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
          />
          {!editId && (
            <input
              type="password"
              className="form-control mb-2"
              placeholder="Password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
            />
          )}
          <button
            className="btn btn-success w-100"
            disabled={loading}
            onClick={handleSubmit}
          >
            {loading ? "Saving..." : editId ? "Update Instructor" : "Add Instructor"}
          </button>
        </div>

        <table className="table table-striped table-hover align-middle">
          <thead className="table-light">
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {instructors.map((i) => (
              <tr key={i.id}>
                <td>{i.name}</td>
                <td>{i.email}</td>
                <td>
                  <button
                    className="btn btn-outline-primary btn-sm me-2"
                    onClick={() => handleEdit(i)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-outline-danger btn-sm"
                    onClick={() => handleDelete(i.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {instructors.length === 0 && (
              <tr>
                <td colSpan="3" className="text-center text-muted">
                  No instructors found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
