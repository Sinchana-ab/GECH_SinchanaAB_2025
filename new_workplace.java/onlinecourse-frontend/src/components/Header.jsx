import React from "react";
import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header className="header-top shadow-sm">
      <div className="container">
        <nav className="navbar navbar-expand-lg bg-transparent px-0">
          <Link className="navbar-brand d-flex align-items-center" to="/">
            <span className="logo-mark"></span>
            <span style={{ fontWeight: 700, color: "#0b3b2a", fontSize: 20 }}>
              Parvam LMS
            </span>
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navmenu"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navmenu">
            <ul className="navbar-nav ms-auto me-3 mb-2 mb-lg-0 align-items-center">
              <li className="nav-item">
                <Link className="nav-link active" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#courses">Courses</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#how">How It Works</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#contact">Contact</a>
              </li>
            </ul>

            <div className="d-flex align-items-center">
              <Link to="/login" className="btn btn-outline-success me-2">
                Sign In
              </Link>
              <Link
                to="/register"
                className="btn btn-success text-white rounded-pill"
                style={{ background: "#2c8a58" }}
              >
                Sign Up
              </Link>
            </div>
          </div>
        </nav>
      </div>
    </header>
  );
}
