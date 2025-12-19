// pages/EnhancedCertificates.jsx - COMPLETE VERSION
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const EnhancedCertificates = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  
  const [certificates, setCertificates] = useState([]);
  const [eligibleCourses, setEligibleCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [generating, setGenerating] = useState(false);
  const [selectedCert, setSelectedCert] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [stats, setStats] = useState({
    total: 0,
    thisMonth: 0,
    avgScore: 0
  });

  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      await Promise.all([
        fetchCertificates(),
        fetchEligibleCourses()
      ]);
    } finally {
      setLoading(false);
    }
  };

  const fetchCertificates = async () => {
    try {
      const response = await axios.get(
        `${API_URL}/student/certificates`,
        {
          params: { studentId: user.id },
          auth: { username: user.email, password: user.password }
        }
      );
      
      if (response.data.success) {
        const certs = response.data.data;
        setCertificates(certs);
        
        const thisMonth = certs.filter(c => {
          const issued = new Date(c.issuedAt);
          const now = new Date();
          return issued.getMonth() === now.getMonth() && 
                 issued.getFullYear() === now.getFullYear();
        }).length;
        
        const avgScore = certs.length > 0 
          ? certs.reduce((sum, c) => sum + c.finalScore, 0) / certs.length 
          : 0;
        
        setStats({
          total: certs.length,
          thisMonth,
          avgScore: avgScore.toFixed(1)
        });
      }
    } catch (err) {
      console.error('Failed to load certificates', err);
    }
  };

  const fetchEligibleCourses = async () => {
    try {
      const response = await axios.get(
        `${API_URL}/student/enrollments`,
        {
          params: { studentId: user.id },
          auth: { username: user.email, password: user.password }
        }
      );
      
      if (response.data.success) {
        const enrollments = response.data.data;
        
        const eligibilityChecks = await Promise.all(
          enrollments.map(async enrollment => {
            try {
              const eligResponse = await axios.get(
                `${API_URL}/student/enrollments/${enrollment.id}/certificate-eligibility`,
                { auth: { username: user.email, password: user.password } }
              );
              return {
                enrollment,
                eligible: eligResponse.data.success && eligResponse.data.data
              };
            } catch {
              return { enrollment, eligible: false };
            }
          })
        );
        
        setEligibleCourses(
          eligibilityChecks
            .filter(({ eligible }) => eligible)
            .map(({ enrollment }) => enrollment)
        );
      }
    } catch (err) {
      console.error('Failed to load eligible courses', err);
    }
  };

  const handleGenerateCertificate = async (enrollmentId) => {
    if (!window.confirm('Generate certificate for this course?')) return;

    setGenerating(true);
    try {
      const response = await axios.post(
        `${API_URL}/student/enrollments/${enrollmentId}/request-certificate`,
        {},
        { auth: { username: user.email, password: user.password } }
      );

      if (response.data.success) {
        alert('Certificate generated successfully!');
        await fetchData();
      } else {
        alert(response.data.message || 'Failed to generate certificate');
      }
    } catch (err) {
      alert('Failed to generate certificate: ' + (err.response?.data?.message || err.message));
    } finally {
      setGenerating(false);
    }
  };

  const handleDownload = async (certificateId, certificateNumber) => {
    try {
      const response = await axios.get(
        `${API_URL}/student/certificates/download/${certificateId}`,
        {
          auth: { username: user.email, password: user.password },
          responseType: 'blob'
        }
      );

      const blob = new Blob([response.data], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `certificate-${certificateNumber}.pdf`;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
      
      alert('Certificate downloaded successfully!');
    } catch (err) {
      console.error('Download failed:', err);
      alert('Download failed. Opening in new tab...');
      window.open(`${API_URL}/student/certificates/download/${certificateId}`, '_blank');
    }
  };

  const handleShare = (cert) => {
    const verificationUrl = `${window.location.origin}/verify/${cert.certificateNumber}`;
    
    if (navigator.share) {
      navigator.share({
        title: `Certificate - ${cert.courseTitle}`,
        text: `I've earned a certificate for ${cert.courseTitle}!`,
        url: verificationUrl
      }).catch(() => {
        navigator.clipboard.writeText(verificationUrl);
        alert('Verification link copied to clipboard!');
      });
    } else {
      navigator.clipboard.writeText(verificationUrl);
      alert('Verification link copied to clipboard!');
    }
  };

  if (loading) {
    return (
      <div className="container mt-5 text-center">
        <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }}></div>
        <p className="mt-3">Loading certificates...</p>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2>
            <i className="bi bi-award-fill text-warning me-2"></i>
            My Certificates
          </h2>
          <p className="text-muted mb-0">View and manage your earned certificates</p>
        </div>
        <button 
          className="btn btn-outline-primary"
          onClick={() => navigate('/courses')}
        >
          <i className="bi bi-search me-2"></i>
          Browse More Courses
        </button>
      </div>

      {/* Stats Cards */}
      <div className="row g-4 mb-4">
        <div className="col-md-4">
          <div className="card text-center h-100">
            <div className="card-body">
              <i className="bi bi-award-fill text-warning" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.total}</h3>
              <p className="text-muted mb-0">Total Certificates</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center h-100">
            <div className="card-body">
              <i className="bi bi-calendar-check text-success" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.thisMonth}</h3>
              <p className="text-muted mb-0">Earned This Month</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center h-100">
            <div className="card-body">
              <i className="bi bi-star-fill text-primary" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.avgScore}%</h3>
              <p className="text-muted mb-0">Average Score</p>
            </div>
          </div>
        </div>
      </div>

      {/* Eligible Courses Alert */}
      {eligibleCourses.length > 0 && (
        <div className="alert alert-success mb-4">
          <div className="d-flex align-items-start">
            <i className="bi bi-trophy-fill fs-3 me-3 text-warning"></i>
            <div className="flex-grow-1">
              <h5 className="alert-heading mb-3">
                🎉 Congratulations! You're eligible for {eligibleCourses.length} certificate{eligibleCourses.length > 1 ? 's' : ''}!
              </h5>
              <p className="mb-3">You've successfully completed the following courses and can now generate your certificates:</p>
              
              <div className="list-group">
                {eligibleCourses.map(enrollment => (
                  <div key={enrollment.id} className="list-group-item list-group-item-action">
                    <div className="d-flex justify-content-between align-items-center">
                      <div>
                        <h6 className="mb-1">{enrollment.courseTitle}</h6>
                        <small className="text-muted">
                          <i className="bi bi-calendar-check me-1"></i>
                          Completed on {new Date(enrollment.completedAt).toLocaleDateString()}
                        </small>
                      </div>
                      <button
                        className="btn btn-primary btn-sm"
                        onClick={() => handleGenerateCertificate(enrollment.id)}
                        disabled={generating}
                      >
                        {generating ? (
                          <>
                            <span className="spinner-border spinner-border-sm me-2"></span>
                            Generating...
                          </>
                        ) : (
                          <>
                            <i className="bi bi-award me-2"></i>
                            Generate Certificate
                          </>
                        )}
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Certificates Grid */}
      {certificates.length === 0 ? (
        <div className="card">
          <div className="card-body text-center py-5">
            <i className="bi bi-award" style={{ fontSize: '6rem', color: '#ddd' }}></i>
            <h3 className="mt-4">No Certificates Yet</h3>
            <p className="text-muted mb-4">
              Complete courses and pass assessments to earn certificates
            </p>
            <button 
              className="btn btn-primary btn-lg"
              onClick={() => navigate('/courses')}
            >
              <i className="bi bi-search me-2"></i>
              Explore Courses
            </button>
          </div>
        </div>
      ) : (
        <>
          <h4 className="mb-3">Your Certificates ({certificates.length})</h4>
          <div className="row g-4">
            {certificates.map(cert => (
              <div key={cert.id} className="col-md-6 col-lg-4">
                <div className="card h-100 shadow-sm border-warning border-2">
                  <div className="card-body">
                    <div className="text-center mb-3">
                      <i className="bi bi-award-fill text-warning" style={{ fontSize: '4rem' }}></i>
                    </div>

                    <h5 className="card-title text-center text-primary mb-3">
                      {cert.courseTitle}
                    </h5>

                    <div className="mb-3">
                      <div className="row text-center small">
                        <div className="col-6 mb-2">
                          <div className="text-muted">Certificate ID</div>
                          <div className="fw-bold">{cert.certificateNumber}</div>
                        </div>
                        <div className="col-6 mb-2">
                          <div className="text-muted">Final Score</div>
                          <div className="fw-bold text-success">{cert.finalScore}%</div>
                        </div>
                        <div className="col-12">
                          <div className="text-muted">Issued On</div>
                          <div className="fw-bold">
                            {new Date(cert.issuedAt).toLocaleDateString('en-US', {
                              year: 'numeric',
                              month: 'long',
                              day: 'numeric'
                            })}
                          </div>
                        </div>
                      </div>
                    </div>

                    <hr />

                    <div className="d-grid gap-2">
                      <button
                        className="btn btn-outline-primary btn-sm"
                        onClick={() => {
                          setSelectedCert(cert);
                          setShowModal(true);
                        }}
                      >
                        <i className="bi bi-eye me-2"></i>
                        Preview
                      </button>
                      
                      <div className="btn-group">
                        <button
                          className="btn btn-success btn-sm"
                          onClick={() => handleDownload(cert.id, cert.certificateNumber)}
                        >
                          <i className="bi bi-download me-2"></i>
                          Download PDF
                        </button>
                        <button
                          className="btn btn-outline-success btn-sm"
                          onClick={() => handleShare(cert)}
                          title="Share Certificate"
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
        </>
      )}

      {/* Certificate Preview Modal */}
      {showModal && selectedCert && (
        <div 
          className="modal show d-block" 
          style={{ backgroundColor: 'rgba(0,0,0,0.85)' }}
          onClick={() => setShowModal(false)}
        >
          <div 
            className="modal-dialog modal-xl modal-dialog-centered"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="modal-content">
              <div className="modal-header border-0">
                <h5 className="modal-title">
                  <i className="bi bi-award me-2"></i>
                  Certificate Preview
                </h5>
                <button 
                  className="btn-close" 
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body p-0">
                <div 
                  className="certificate-preview p-5 text-center text-white"
                  style={{
                    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                    minHeight: '500px',
                    position: 'relative'
                  }}
                >
                  <div style={{
                    position: 'absolute',
                    top: '20px',
                    left: '20px',
                    right: '20px',
                    bottom: '20px',
                    border: '4px solid rgba(255,255,255,0.3)',
                    borderRadius: '10px'
                  }}></div>

                  <div style={{ position: 'relative', zIndex: 1 }}>
                    <i className="bi bi-award-fill" style={{ fontSize: '5rem' }}></i>
                    <h2 className="mt-4 mb-4" style={{ fontFamily: 'Georgia, serif' }}>
                      Certificate of Completion
                    </h2>
                    <p style={{ fontSize: '1.2rem' }}>This is to certify that</p>
                    <h3 className="my-4" style={{ fontSize: '2.5rem', fontFamily: 'Georgia, serif' }}>
                      {user.name}
                    </h3>
                    <p style={{ fontSize: '1.2rem' }}>has successfully completed</p>
                    <h4 className="my-4">{selectedCert.courseTitle}</h4>
                    <div className="row justify-content-center my-4">
                      <div className="col-md-8">
                        <div className="row">
                          <div className="col-6">
                            <strong>Final Score:</strong> {selectedCert.finalScore}%
                          </div>
                          <div className="col-6">
                            <strong>Date:</strong> {new Date(selectedCert.issuedAt).toLocaleDateString()}
                          </div>
                        </div>
                        <div className="mt-2">
                          <strong>Certificate ID:</strong> {selectedCert.certificateNumber}
                        </div>
                      </div>
                    </div>
                    <div className="mt-5 pt-4 border-top border-white">
                      <p><strong>{selectedCert.instructorName}</strong></p>
                      <p>Course Instructor</p>
                    </div>
                  </div>
                </div>
              </div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setShowModal(false)}>
                  Close
                </button>
                <button 
                  className="btn btn-success"
                  onClick={() => handleDownload(selectedCert.id, selectedCert.certificateNumber)}
                >
                  <i className="bi bi-download me-2"></i>
                  Download PDF
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default EnhancedCertificates;