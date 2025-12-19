// File: src/pages/StudentCertificates.jsx (New Component)

import React, { useState, useEffect } from 'react';
import axios from '../api/axios'; // Assuming your axios setup
import { Link } from 'react-router-dom';

const StudentCertificates = () => {
  const [certificates, setCertificates] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCertificates = async () => {
      try {
        // Backend Endpoint: GET /api/student/certificates
        const res = await axios.get('/api/student/certificates');
        setCertificates(res.data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching certificates:", error);
        setLoading(false);
      }
    };

    fetchCertificates();
  }, []);

  if (loading) return <div className="text-center p-5">Loading certificates...</div>;

  return (
    <div className="container my-5">
      <h2 className="mb-4 text-primary">
        <i className="bi bi-award me-2"></i>
        My Certificates
      </h2>
      <hr />

      {certificates.length === 0 ? (
        <div className="alert alert-info text-center" role="alert">
          You haven't earned any certificates yet. Keep learning!
        </div>
      ) : (
        <div className="row g-4">
          {certificates.map((cert) => (
            <div className="col-md-6 col-lg-4" key={cert.id}>
              <div className="card shadow-sm h-100">
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title text-success">
                    <i className="bi bi-patch-check-fill me-2"></i>
                    Certificate Earned!
                  </h5>
                  <p className="card-text mb-1">
                    **Course:** {cert.courseTitle}
                  </p>
                  <p className="card-text mb-1 small text-muted">
                    **Issued:** {new Date(cert.issuedAt).toLocaleDateString()}
                  </p>
                  <p className="card-text mb-3 small text-muted">
                    **Final Score:** {cert.finalScore.toFixed(2)}%
                  </p>
                  
                  <div className="mt-auto d-grid">
                    <a 
                      href={cert.pdfUrl} // Link to your backend PDF generation endpoint
                      target="_blank" 
                      rel="noopener noreferrer"
                      className="btn btn-sm btn-primary"
                    >
                      <i className="bi bi-download me-2"></i>
                      Download PDF
                    </a>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default StudentCertificates;