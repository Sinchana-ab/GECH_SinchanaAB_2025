import React, { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function Dashboard() {
  const { user } = useContext(AuthContext);

  if (!user)
    return <h3>Please login to access your dashboard.</h3>;

  return (
    <div className="page">
      <h2>Dashboard</h2>
      <p>Welcome <b>{user.email}</b></p>
      <p>Your role: <b>{user.role.replace("ROLE_", "")}</b></p>

      {user.role === "ROLE_ADMIN" && (
        <p>Admins can add instructors via API or UI (to be built next)</p>
      )}
      {user.role === "ROLE_INSTRUCTOR" && (
        <p>You can add and edit courses.</p>
      )}
      {user.role === "ROLE_STUDENT" && (
        <p>You can browse published courses and enroll later.</p>
      )}
    </div>
  );
}
