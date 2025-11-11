import React, { useState } from "react";
import api from "../api";       


function CreateCourse() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [published, setPublished] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/courses", { title, description, published });
      alert("Course created successfully!");
      setTitle("");
      setDescription("");
      setPublished(false);
    } catch (err) {
      alert("Error creating course: " + (err.response?.data || err.message));
    }
  };

  return (
    <div>
      <h2>Create Course</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Course title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="Course description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
        <label>
          <input
            type="checkbox"
            checked={published}
            onChange={(e) => setPublished(e.target.checked)}
          />
          Published
        </label>
        <button type="submit">Create</button>
      </form>
    </div>
  );
}

export default CreateCourse;
