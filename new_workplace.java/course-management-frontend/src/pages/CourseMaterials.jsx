// pages/CourseMaterials.jsx
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { materialAPI } from '../services/materialAPI';
import FileUpload from '../components/FileUpload';
import VideoPlayer from '../components/VideoPlayer';
import PDFViewer from '../components/PDFViewer';
import { useAuth } from '../context/AuthContext';
// // At the top of CourseMaterials.jsx
// import { materialAPI } from '../services/materialAPI';

const CourseMaterials = () => {
  const { courseId } = useParams();
  const { user, isInstructor } = useAuth();
  const [materials, setMaterials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showUploadModal, setShowUploadModal] = useState(false);
  const [selectedMaterial, setSelectedMaterial] = useState(null);
  const [viewMode, setViewMode] = useState('list');

  useEffect(() => {
    fetchMaterials();
  }, [courseId]);

  const fetchMaterials = async () => {
    try {
      const response = await materialAPI.getCourseMaterials(courseId);
      if (response.data.success) {
        setMaterials(response.data.data);
      }
    } catch (error) {
      console.error('Failed to load materials', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this material?')) return;

    try {
      await materialAPI.deleteMaterial(id);
      alert('Material deleted successfully');
      fetchMaterials();
    } catch (error) {
      alert('Failed to delete material');
    }
  };

  const handleView = (material) => {
    setSelectedMaterial(material);
    setViewMode('detail');
  };

  const getMaterialIcon = (type) => {
    const icons = {
      VIDEO: { icon: 'bi-camera-video-fill', color: 'danger' },
      PDF: { icon: 'bi-file-pdf-fill', color: 'danger' },
      DOCUMENT: { icon: 'bi-file-text-fill', color: 'primary' },
      IMAGE: { icon: 'bi-image-fill', color: 'success' },
      LINK: { icon: 'bi-link-45deg', color: 'info' }
    };
    return icons[type] || icons.DOCUMENT;
  };

  const renderMaterialContent = (material) => {
    const fileUrl = material.materialType === 'LINK' 
      ? material.filePath 
      : materialAPI.getFileUrl(material.filePath.split('/').pop());

    switch (material.materialType) {
      case 'VIDEO':
      case 'LINK':
        return <VideoPlayer src={fileUrl} title={material.title} />;
      case 'PDF':
        return <PDFViewer src={fileUrl} title={material.title} />;
      case 'IMAGE':
        return (
          <img 
            src={fileUrl} 
            alt={material.title} 
            className="img-fluid rounded"
            style={{ maxHeight: '500px' }}
          />
        );
      case 'DOCUMENT':
        return (
          <div className="text-center p-5">
            <i className="bi bi-file-text display-1 text-primary"></i>
            <h4 className="mt-3">{material.fileName}</h4>
            <a href={fileUrl} download className="btn btn-primary mt-3">
              <i className="bi bi-download me-2"></i>
              Download Document
            </a>
          </div>
        );
      default:
        return <p>Unsupported file type</p>;
    }
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary"></div>
      </div>
    );
  }

  if (viewMode === 'detail' && selectedMaterial) {
    return (
      <div className="container mt-4">
        <button
          className="btn btn-outline-secondary mb-3"
          onClick={() => {
            setViewMode('list');
            setSelectedMaterial(null);
          }}
        >
          <i className="bi bi-arrow-left me-2"></i>
          Back to Materials
        </button>

        <div className="card shadow-sm">
          <div className="card-header bg-primary text-white">
            <h4 className="mb-0">{selectedMaterial.title}</h4>
          </div>
          <div className="card-body">
            {selectedMaterial.description && (
              <p className="text-muted mb-4">{selectedMaterial.description}</p>
            )}
            {renderMaterialContent(selectedMaterial)}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3>
          <i className="bi bi-folder2-open me-2"></i>
          Course Materials
        </h3>
        {isInstructor && (
          <button
            className="btn btn-primary"
            onClick={() => setShowUploadModal(true)}
          >
            <i className="bi bi-plus-circle me-2"></i>
            Add Material
          </button>
        )}
      </div>

      {materials.length === 0 ? (
        <div className="text-center py-5">
          <i className="bi bi-inbox" style={{ fontSize: '4rem', color: '#ccc' }}></i>
          <h4 className="mt-3 text-muted">No materials uploaded yet</h4>
          {isInstructor && (
            <button
              className="btn btn-primary mt-3"
              onClick={() => setShowUploadModal(true)}
            >
              Upload First Material
            </button>
          )}
        </div>
      ) : (
        <div className="row g-4">
          {materials.map((material) => {
            const iconInfo = getMaterialIcon(material.materialType);
            return (
              <div key={material.id} className="col-md-6 col-lg-4">
                <div className="card h-100 shadow-sm" style={{ transition: 'all 0.3s' }}>
                  <div className="card-body">
                    <div className="d-flex align-items-start mb-3">
                      <div
                        className={`rounded-circle bg-${iconInfo.color} bg-opacity-10 p-3 me-3`}
                        style={{ minWidth: '60px', height: '60px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
                      >
                        <i className={`bi ${iconInfo.icon} text-${iconInfo.color}`} style={{ fontSize: '1.5rem' }}></i>
                      </div>
                      <div className="flex-grow-1">
                        <h5 className="card-title mb-1">{material.title}</h5>
                        <small className="text-muted">
                          {material.materialType}
                          {material.fileSize && ` • ${(material.fileSize / (1024 * 1024)).toFixed(2)} MB`}
                        </small>
                      </div>
                    </div>

                    {material.description && (
                      <p className="card-text text-muted small">
                        {material.description.substring(0, 100)}
                        {material.description.length > 100 ? '...' : ''}
                      </p>
                    )}

                    <div className="d-flex gap-2 mt-3">
                      <button
                        className="btn btn-primary btn-sm flex-grow-1"
                        onClick={() => handleView(material)}
                      >
                        <i className="bi bi-eye me-1"></i>
                        View
                      </button>
                      {isInstructor && (
                        <button
                          className="btn btn-outline-danger btn-sm"
                          onClick={() => handleDelete(material.id)}
                        >
                          <i className="bi bi-trash"></i>
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* Upload Modal */}
      {showUploadModal && (
        <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-lg modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Upload Course Material</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowUploadModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <FileUpload
                  courseId={courseId}
                  onUploadSuccess={() => {
                    setShowUploadModal(false);
                    fetchMaterials();
                  }}
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CourseMaterials;