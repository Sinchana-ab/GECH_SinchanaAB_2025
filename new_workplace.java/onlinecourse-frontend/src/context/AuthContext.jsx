import React, { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

// ✅ Create context
export const AuthContext = createContext();

// ✅ Define provider component
export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  // Load user from localStorage on app start
  useEffect(() => {
    const saved = localStorage.getItem("user");
    if (saved) setUser(JSON.parse(saved));
  }, []);

  // Login function (called after successful login)
  const login = (data) => {
    localStorage.setItem("token", data.token);
    localStorage.setItem(
      "user",
      JSON.stringify({ email: data.email, role: data.role })
    );
    setUser({ email: data.email, role: data.role });

    // Redirect based on role
    if (data.role === "ROLE_ADMIN") navigate("/admin/dashboard");
    else if (data.role === "ROLE_INSTRUCTOR") navigate("/instructor/dashboard");
    else navigate("/student/dashboard");
  };

  // Logout function
  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/"); // redirect to home
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
