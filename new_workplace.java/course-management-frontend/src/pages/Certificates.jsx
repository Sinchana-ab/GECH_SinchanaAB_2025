// pages/Certificates.jsx
import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { certificateAPI, API_URL } from '../services/api';

const Certificates = () => {
  const { user } = useAuth();
  const [certificates, setCertificates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCertificate, setSelectedCertificate] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchCertificates();
  }, []);

  const fetchCertificates = async () => {
    // In real app, fetch from backend
    // For demo, using sample data
    const sampleCertificates = [
      {
        id: 1,
        certificateNumber: 'CERT-2024-001',
        courseTitle: 'Java Programming Fundamentals',
        studentName: user?.name,
        instructorName: 'John Instructor',
        issuedAt: '2024-01-15T10:30:00',
        finalScore: 92.5,
        skills: 'Java, OOP, Data Structures',
        pdfUrl: '/certificates/cert-001.pdf'
      },
      {
        id: 2,
        certificateNumber: 'CERT-2024-002',
        courseTitle: 'React Development',
        studentName: user?.name,
        instructorName: 'Jane Developer',
        issuedAt: '2024-02-20T14:45:00',
        finalScore: 88.0,
        skills: 'React, JavaScript, Hooks, Redux',
        pdfUrl: '/certificates/cert-002.pdf'
      }
    ];

    setCertificates(sampleCertificates);
    setLoading(false);
  };

  const handleViewCertificate = (certificate) => {
    setSelectedCertificate(certificate);
    setShowModal(true);
  };

  const handleDownloadCertificate = async (certificate) => {
    try {
      setLoading(true);
      const response = await certificateAPI.downloadCertificate(certificate.id);
      
      // Check if response has data
      if (!response.data) {
        throw new Error('No data received');
      }
      
      // Create blob from response
      const blob = new Blob([response.data], { type: 'application/pdf' });
      
      // Create download link
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `certificate-${certificate.certificateNumber}.pdf`);
      
      // Trigger download
      document.body.appendChild(link);
      link.click();
      
      // Cleanup
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      
      alert('Certificate downloaded successfully!');
    } catch (err) {
      console.error('Download error:', err);
      // Fallback: Open in new window
      alert('PDF generation is being processed. Opening certificate preview...');
      window.open(`${API_URL}/student/certificates/download/${certificate.id}`, '_blank');
    } finally {
      setLoading(false);
    }
  };

  const handleShareCertificate = (certificate) => {
    // In real app, generate shareable link
    const shareUrl = `${window.location.origin}/verify/${certificate.certificateNumber}`;
    navigator.clipboard.writeText(shareUrl);
    alert('Certificate verification link copied to clipboard!');
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary"></div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>
          <i className="bi bi-award-fill text-warning me-2"></i>
          My Certificates
        </h2>
      </div>

      {certificates.length === 0 ? (
        <div className="text-center py-5">
          <i className="bi bi-award" style={{ fontSize: '5rem', color: '#ccc' }}></i>
          <h4 className="mt-3 text-muted">No Certificates Yet</h4>
          <p className="text-muted">
            Complete courses and pass assessments to earn certificates
          </p>
          <button 
            className="btn btn-primary mt-3"
            onClick={() => window.location.href = '/courses'}
          >
            Browse Courses
          </button>
        </div>
      ) : (
        <div className="row">
          {certificates.map((cert) => (
            <div key={cert.id} className="col-md-6 mb-4">
              <div className="card shadow-sm h-100 border-warning">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-start mb-3">
                    <div className="flex-grow-1">
                      <h5 className="card-title text-primary mb-1">
                        {cert.courseTitle}
                      </h5>
                      <small className="text-muted">
                        <i className="bi bi-calendar3 me-1"></i>
                        Issued: {new Date(cert.issuedAt).toLocaleDateString('en-US', {
                          year: 'numeric',
                          month: 'long',
                          day: 'numeric'
                        })}
                      </small>
                    </div>
                    <div className="text-warning">
                      <i className="bi bi-award-fill" style={{ fontSize: '2rem' }}></i>
                    </div>
                  </div>

                  <div className="mb-3">
                    <span className="badge bg-light text-dark me-2">
                      <i className="bi bi-hash"></i> {cert.certificateNumber}
                    </span>
                    <span className="badge bg-success">
                      Score: {cert.finalScore}%
                    </span>
                  </div>

                  <div className="mb-3">
                    <small className="text-muted d-block mb-1">
                      <i className="bi bi-person-circle me-1"></i>
                      Instructor: {cert.instructorName}
                    </small>
                    <small className="text-muted d-block">
                      <i className="bi bi-lightbulb me-1"></i>
                      Skills: {cert.skills}
                    </small>
                  </div>

                  <div className="border-top pt-3 mt-3">
                    <div className="d-flex gap-2">
                      <button
                        className="btn btn-sm btn-primary flex-grow-1"
                        onClick={() => handleViewCertificate(cert)}
                      >
                        <i className="bi bi-eye me-1"></i>
                        View
                      </button>
                      <button
                        className="btn btn-sm btn-success flex-grow-1"
                        onClick={() => handleDownloadCertificate(cert)}
                      >
                        <i className="bi bi-download me-1"></i>
                        Download
                      </button>
                      <button
                        className="btn btn-sm btn-outline-primary"
                        onClick={() => handleShareCertificate(cert)}
                        title="Share"
                      >
                        <i className="bi bi-share"></i>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Certificate Preview Modal */}
      {showModal && selectedCertificate && (
        <div 
          className="modal show d-block" 
          style={{ backgroundColor: 'rgba(0,0,0,0.7)' }}
          onClick={() => setShowModal(false)}
        >
          <div 
            className="modal-dialog modal-lg modal-dialog-centered"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="modal-content">
              <div className="modal-header border-0">
                <h5 className="modal-title">Certificate Preview</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body p-0">
                {/* Certificate Design */}
                <div 
                  className="certificate-preview p-5 text-center"
                  style={{
                    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                    color: 'white',
                    minHeight: '500px',
                    position: 'relative'
                  }}
                >
                  {/* Decorative Border */}
                  <div 
                    style={{
                      position: 'absolute',
                      top: '20px',
                      left: '20px',
                      right: '20px',
                      bottom: '20px',
                      border: '4px solid rgba(255,255,255,0.3)',
                      borderRadius: '10px'
                    }}
                  ></div>

                  {/* Content */}
                  <div style={{ position: 'relative', zIndex: 1 }}>
                    <div className="mb-4">
                      <i className="bi bi-award-fill" style={{ fontSize: '5rem' }}></i>
                    </div>

                    <h2 className="mb-4" style={{ fontFamily: 'Georgia, serif' }}>
                      Certificate of Completion
                    </h2>

                    <p className="mb-4" style={{ fontSize: '1.1rem' }}>
                      This is to certify that
                    </p>

                    <h3 className="mb-4" style={{ 
                      fontFamily: 'Georgia, serif',
                      fontSize: '2.5rem',
                      textShadow: '2px 2px 4px rgba(0,0,0,0.3)'
                    }}>
                      {selectedCertificate.studentName}
                    </h3>

                    <p className="mb-4" style={{ fontSize: '1.1rem' }}>
                      has successfully completed the course
                    </p>

                    <h4 className="mb-4" style={{ fontFamily: 'Georgia, serif' }}>
                      {selectedCertificate.courseTitle}
                    </h4>

                    <div className="row justify-content-center mb-4">
                      <div className="col-md-6">
                        <p className="mb-2">
                          <strong>Final Score:</strong> {selectedCertificate.finalScore}%
                        </p>
                        <p className="mb-2">
                          <strong>Date:</strong> {new Date(selectedCertificate.issuedAt).toLocaleDateString()}
                        </p>
                        <p className="mb-2">
                          <strong>Certificate ID:</strong> {selectedCertificate.certificateNumber}
                        </p>
                      </div>
                    </div>

                    <div className="mt-5 pt-4 border-top border-white">
                      <p className="mb-1">
                        <strong>{selectedCertificate.instructorName}</strong>
                      </p>
                      <p>Course Instructor</p>
                    </div>

                    <div className="mt-4">
                      <small style={{ opacity: 0.8 }}>
                        Verify this certificate at: 
                        verify/{selectedCertificate.certificateNumber}
                      </small>
                    </div>
                  </div>
                </div>
              </div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Close
                </button>
                <button
                  className="btn btn-success"
                  onClick={() => handleDownloadCertificate(selectedCertificate)}
                >
                  <i className="bi bi-download me-2"></i>
                  Download PDF
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Certificate Statistics */}
      {certificates.length > 0 && (
        <div className="card mt-4 border-info">
          <div className="card-body">
            <h5 className="card-title mb-3">
              <i className="bi bi-graph-up me-2"></i>
              Certificate Statistics
            </h5>
            <div className="row">
              <div className="col-md-3 text-center">
                <h3 className="text-primary">{certificates.length}</h3>
                <p className="text-muted mb-0">Total Certificates</p>
              </div>
              <div className="col-md-3 text-center">
                <h3 className="text-success">
                  {(certificates.reduce((sum, c) => sum + c.finalScore, 0) / certificates.length).toFixed(1)}%
                </h3>
                <p className="text-muted mb-0">Average Score</p>
              </div>
              <div className="col-md-3 text-center">
                <h3 className="text-warning">
                  {Math.max(...certificates.map(c => c.finalScore))}%
                </h3>
                <p className="text-muted mb-0">Highest Score</p>
              </div>
              <div className="col-md-3 text-center">
                <h3 className="text-info">
                  {certificates.flatMap(c => c.skills.split(', ')).length}
                </h3>
                <p className="text-muted mb-0">Skills Acquired</p>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Skills Summary */}
      {certificates.length > 0 && (
        <div className="card mt-4">
          <div className="card-body">
            <h5 className="card-title mb-3">
              <i className="bi bi-lightbulb-fill me-2"></i>
              Skills Portfolio
            </h5>
            <div className="d-flex flex-wrap gap-2">
              {Array.from(
                new Set(
                  certificates
                    .flatMap(c => c.skills.split(', '))
                    .map(s => s.trim())
                )
              ).map((skill, index) => (
                <span key={index} className="badge bg-primary px-3 py-2">
                  {skill}
                </span>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Certificates;