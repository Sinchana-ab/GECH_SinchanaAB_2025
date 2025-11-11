
import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';
import './CourseMaterialViewer.css';

const CourseMaterialViewer = () => {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const videoRef = useRef(null);

  const [course, setCourse] = useState(null);
  const [materials, setMaterials] = useState([]);
  const [enrollment, setEnrollment] = useState(null);
  const [currentMaterial, setCurrentMaterial] = useState(null);
  const [materialProgress, setMaterialProgress] = useState([]);
  const [certificateEligibility, setCertificateEligibility] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showCertificateModal, setShowCertificateModal] = useState(false);

  // Video progress tracking
  const [watchTime, setWatchTime] = useState(0);
  const watchTimeIntervalRef = useRef(null);

  useEffect(() => {
    fetchCourseData();
    return () => {
      // Cleanup video tracking on unmount
      if (watchTimeIntervalRef.current) {
        clearInterval(watchTimeIntervalRef.current);
      }
    };
  }, [courseId, user]);

  const fetchCourseData = async () => {
    try {
      setLoading(true);

      // Fetch course details
      const courseRes = await axios.get(`/api/public/courses/${courseId}`);
      setCourse(courseRes.data.data);

      // Fetch materials
      const materialsRes = await axios.get(`/api/courses/${courseId}/materials`);
      setMaterials(materialsRes.data.data);

      // Fetch enrollment
      const enrollmentRes = await axios.get(`/api/student/enrollments`, {
        params: { studentId: user.id }
      });
      const currentEnrollment = enrollmentRes.data.data.find(
        e => e.courseId === parseInt(courseId)
      );
      setEnrollment(currentEnrollment);

      if (currentEnrollment) {
        // Fetch material progress
        const progressRes = await axios.get(`/api/student/materials/progress`, {
          params: {
            studentId: user.id,
            enrollmentId: currentEnrollment.id
          }
        });
        setMaterialProgress(progressRes.data.data || []);

        // Check certificate eligibility
        checkCertificateEligibility(currentEnrollment.id);
      }

      // Set first incomplete material or first material
      if (materialsRes.data.data.length > 0) {
        const firstIncompleteMaterial = materialsRes.data.data.find(
          m => !isMaterialCompleted(m.id, progressRes.data.data || [])
        );
        setCurrentMaterial(firstIncompleteMaterial || materialsRes.data.data[0]);
      }
    } catch (error) {
      console.error('Error fetching course data:', error);
    } finally {
      setLoading(false);
    }
  };

  const checkCertificateEligibility = async (enrollmentId) => {
    try {
      const res = await axios.get(
        `/api/student/enrollments/${enrollmentId}/certificate-eligibility`
      );
      setCertificateEligibility(res.data.data);
    } catch (error) {
      console.error('Error checking certificate eligibility:', error);
    }
  };

  const isMaterialCompleted = (materialId, progressList = materialProgress) => {
    const progress = progressList.find(p => p.materialId === materialId);
    return progress ? progress.completed : false;
  };

  const getMaterialProgress = (materialId) => {
    return materialProgress.find(p => p.materialId === materialId);
  };

  const markMaterialComplete = async (materialId) => {
    if (!enrollment) return;

    try {
      await axios.post(
        `/api/student/materials/${materialId}/complete`,
        null,
        {
          params: {
            studentId: user.id,
            enrollmentId: enrollment.id
          }
        }
      );

      // Refresh data
      await fetchCourseData();
      
      alert('Material marked as complete! ✓');
    } catch (error) {
      console.error('Error marking material complete:', error);
      alert('Failed to mark material as complete');
    }
  };

  const handleVideoPlay = () => {
    if (!currentMaterial || currentMaterial.materialType !== 'VIDEO') return;

    // Track watch time every 5 seconds
    // watchTimeIntervalRef.current = setInterval(() => {
    //   if (videoRef.current && !videoRef.current.paused) {
    //     const currentTime = Math.floor(videoRef.current.currentTime);
    //     const duration = Math.floor(videoRef.current.duration);
        
    //     setWatchTime(currentTime);
        
    //     // Update backend every 10 seconds
    //     if (currentTime % 10 === 0) {
    //       updateVideoProgress(currentTime, duration);
    //     }
    //   }
    // }, 5000);
    // Change line in handleVideoPlay()
watchTimeIntervalRef.current = setInterval(() => {
  if (videoRef.current && !videoRef.current.paused) {
    const currentTime = Math.floor(videoRef.current.currentTime);
    const duration = Math.floor(videoRef.current.duration);
    
    setWatchTime(currentTime);
    
    // Update backend every interval (5 seconds)
    updateVideoProgress(currentTime, duration);
  }
}, 5000); // Updates every 5 seconds now
  };

  const handleVideoPause = () => {
    if (watchTimeIntervalRef.current) {
      clearInterval(watchTimeIntervalRef.current);
    }
    
    // Save final progress
    if (videoRef.current) {
      const currentTime = Math.floor(videoRef.current.currentTime);
      const duration = Math.floor(videoRef.current.duration);
      updateVideoProgress(currentTime, duration);
    }
  };

  const updateVideoProgress = async (watchTimeSeconds, totalDurationSeconds) => {
    if (!enrollment || !currentMaterial) return;

    try {
      await axios.post(`/api/student/materials/${currentMaterial.id}/watch-time`, {
        studentId: user.id,
        enrollmentId: enrollment.id,
        watchTimeSeconds,
        totalDurationSeconds
      });
    } catch (error) {
      console.error('Error updating video progress:', error);
    }
  };

  const requestCertificate = async () => {
    if (!enrollment) return;

    try {
      const res = await axios.post(
        `/api/student/enrollments/${enrollment.id}/request-certificate`
      );
      
      alert('🎉 Congratulations! Your certificate has been generated!');
      setShowCertificateModal(false);
      navigate('/certificates');
    } catch (error) {
      alert(error.response?.data?.message || 'Failed to generate certificate');
    }
  };

  const renderMaterialContent = () => {
    if (!currentMaterial) {
      return <div className="text-center py-5">Select a material to view</div>;
    }

    const progress = getMaterialProgress(currentMaterial.id);

    switch (currentMaterial.materialType) {
      case 'VIDEO':
        return (
          <div className="video-container">
            <video
              ref={videoRef}
              controls
              width="100%"
              onPlay={handleVideoPlay}
              onPause={handleVideoPause}
              onEnded={() => markMaterialComplete(currentMaterial.id)}
              src={`/api/courses/materials/download/${currentMaterial.filePath}`}
            >
              Your browser does not support the video tag.
            </video>
            {progress && (
              <div className="video-progress-info">
                <small>
                  Watched: {Math.floor((progress.watchTimeSeconds || 0) / 60)} min / 
                  {Math.floor((progress.totalDurationSeconds || 0) / 60)} min
                  ({progress.completionPercentage?.toFixed(0) || 0}%)
                </small>
              </div>
            )}
          </div>
        );

      case 'PDF':
      case 'DOCUMENT':
        return (
          <div className="document-viewer">
            <iframe
              src={`/api/courses/materials/download/${currentMaterial.filePath}`}
              width="100%"
              height="600px"
              title={currentMaterial.title}
            />
            <div className="mt-3">
              <button
                className="btn btn-success"
                onClick={() => markMaterialComplete(currentMaterial.id)}
                disabled={isMaterialCompleted(currentMaterial.id)}
              >
                {isMaterialCompleted(currentMaterial.id) ? '✓ Completed' : 'Mark as Complete'}
              </button>
            </div>
          </div>
        );

      case 'LINK':
        return (
          <div className="link-content">
            <div className="embed-responsive embed-responsive-16by9">
              <iframe
                className="embed-responsive-item"
                src={currentMaterial.filePath}
                allowFullScreen
                title={currentMaterial.title}
                width="100%"
                height="500px"
              />
            </div>
            <div className="mt-3">
              <a
                href={currentMaterial.filePath}
                target="_blank"
                rel="noopener noreferrer"
                className="btn btn-primary me-2"
              >
                <i className="bi bi-box-arrow-up-right me-2"></i>
                Open in New Tab
              </a>
              <button
                className="btn btn-success"
                onClick={() => markMaterialComplete(currentMaterial.id)}
                disabled={isMaterialCompleted(currentMaterial.id)}
              >
                {isMaterialCompleted(currentMaterial.id) ? '✓ Completed' : 'Mark as Complete'}
              </button>
            </div>
          </div>
        );

      case 'IMAGE':
        return (
          <div className="image-viewer text-center">
            <img
              src={`/api/courses/materials/download/${currentMaterial.filePath}`}
              alt={currentMaterial.title}
              className="img-fluid"
              style={{ maxHeight: '600px' }}
            />
            <div className="mt-3">
              <button
                className="btn btn-success"
                onClick={() => markMaterialComplete(currentMaterial.id)}
                disabled={isMaterialCompleted(currentMaterial.id)}
              >
                {isMaterialCompleted(currentMaterial.id) ? '✓ Completed' : 'Mark as Complete'}
              </button>
            </div>
          </div>
        );

      default:
        return (
          <div className="text-center py-5">
            <p>Unsupported material type</p>
            <a
              href={`/api/courses/materials/download/${currentMaterial.filePath}`}
              download
              className="btn btn-primary"
            >
              <i className="bi bi-download me-2"></i>
              Download Material
            </a>
          </div>
        );
    }
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }}></div>
        <p className="mt-3">Loading course materials...</p>
      </div>
    );
  }

  return (
    <div className="course-material-viewer">
      <div className="container-fluid">
        <div className="row">
          {/* Sidebar - Materials List */}
          <div className="col-md-3 materials-sidebar">
            <div className="sidebar-header">
              <button className="btn btn-link" onClick={() => navigate('/student/my-courses')}>
                <i className="bi bi-arrow-left me-2"></i>
                Back to My Courses
              </button>
              <h5 className="mt-3">{course?.title}</h5>
            </div>

            {/* Overall Progress */}
            <div className="progress-summary">
              <div className="d-flex justify-content-between mb-2">
                <span>Overall Progress</span>
                <span className="fw-bold">{enrollment?.progress?.toFixed(0) || 0}%</span>
              </div>
              <div className="progress">
                <div
                  className="progress-bar bg-success"
                  style={{ width: `${enrollment?.progress || 0}%` }}
                ></div>
              </div>
              <small className="text-muted mt-2 d-block">
                {materialProgress.filter(p => p.completed).length} / {materials.length} materials completed
              </small>
            </div>

            {/* Certificate Button */}
            {certificateEligibility?.eligible && !enrollment?.certificateIssued && (
              <button
                className="btn btn-gradient w-100 mb-3"
                onClick={() => setShowCertificateModal(true)}
              >
                <i className="bi bi-award me-2"></i>
                Get Certificate
              </button>
            )}

            {/* Materials List */}
            <div className="materials-list">
              {materials.map((material, index) => {
                const completed = isMaterialCompleted(material.id);
                const progress = getMaterialProgress(material.id);
                
                return (
                  <div
                    key={material.id}
                    className={`material-item ${currentMaterial?.id === material.id ? 'active' : ''} ${completed ? 'completed' : ''}`}
                    onClick={() => setCurrentMaterial(material)}
                  >
                    <div className="d-flex align-items-center">
                      <div className="material-icon me-3">
                        {completed ? (
                          <i className="bi bi-check-circle-fill text-success"></i>
                        ) : (
                          <i className={`bi bi-${getMaterialIcon(material.materialType)}`}></i>
                        )}
                      </div>
                      <div className="flex-grow-1">
                        <div className="material-title">{material.title}</div>
                        <small className="text-muted">{material.materialType}</small>
                        {progress && progress.completionPercentage > 0 && !completed && (
                          <div className="progress mt-1" style={{ height: '3px' }}>
                            <div
                              className="progress-bar"
                              style={{ width: `${progress.completionPercentage}%` }}
                            ></div>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>

          {/* Main Content Area */}
          <div className="col-md-9 content-area">
            {currentMaterial && (
              <>
                <div className="material-header">
                  <h3>{currentMaterial.title}</h3>
                  <p className="text-muted">{currentMaterial.description}</p>
                  {isMaterialCompleted(currentMaterial.id) && (
                    <span className="badge bg-success">
                      <i className="bi bi-check-circle me-1"></i>
                      Completed
                    </span>
                  )}
                </div>
                <div className="material-content">
                  {renderMaterialContent()}
                </div>
              </>
            )}
          </div>
        </div>
      </div>

      {/* Certificate Request Modal */}
      {showCertificateModal && (
        <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  <i className="bi bi-award me-2"></i>
                  Request Certificate
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowCertificateModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <div className="text-center">
                  <i className="bi bi-trophy text-warning" style={{ fontSize: '4rem' }}></i>
                  <h4 className="mt-3">Congratulations!</h4>
                  <p>You've completed all requirements for this course:</p>
                  <ul className="text-start">
                    <li>✓ All materials completed ({certificateEligibility?.completedMaterials}/{certificateEligibility?.totalMaterials})</li>
                    <li>✓ All quizzes passed</li>
                    <li>✓ Final Score: {certificateEligibility?.finalScore?.toFixed(2)}%</li>
                  </ul>
                  <p className="mt-3">Click below to generate your certificate!</p>
                </div>
              </div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => setShowCertificateModal(false)}
                >
                  Cancel
                </button>
                <button
                  className="btn btn-gradient"
                  onClick={requestCertificate}
                >
                  <i className="bi bi-award me-2"></i>
                  Generate Certificate
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

const getMaterialIcon = (type) => {
  switch (type) {
    case 'VIDEO': return 'play-circle';
    case 'PDF': return 'file-earmark-pdf';
    case 'DOCUMENT': return 'file-earmark-text';
    case 'LINK': return 'link-45deg';
    case 'IMAGE': return 'image';
    default: return 'file-earmark';
  }
};

export default CourseMaterialViewer;