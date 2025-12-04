// Add this debug version to help diagnose issues

import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const CourseMaterialViewer = () => {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const [materials, setMaterials] = useState([]);
  const [currentMaterial, setCurrentMaterial] = useState(null);
  const [loading, setLoading] = useState(true);
  const [fileUrl, setFileUrl] = useState(null);
  const [debugInfo, setDebugInfo] = useState(null);
  const [showDebug, setShowDebug] = useState(true); // Toggle debug panel
  
  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchMaterials();
  }, [courseId]);

  useEffect(() => {
    if (currentMaterial) {
      loadMaterialFile(currentMaterial);
    }
  }, [currentMaterial]);

  const fetchMaterials = async () => {
    try {
      const response = await axios.get(`${API_URL}/courses/${courseId}/materials`, {
        auth: { username: user.email, password: user.password }
      });

      if (response.data.success) {
        const mats = response.data.data;
        setMaterials(mats);
        if (mats.length > 0) {
          setCurrentMaterial(mats[0]);
        }
      }
    } catch (error) {
      console.error('Error fetching materials:', error);
      alert('Failed to load materials');
    } finally {
      setLoading(false);
    }
  };

  const loadMaterialFile = async (material) => {
    try {
      const debug = {
        materialId: material.id,
        materialType: material.materialType,
        filePath: material.filePath,
        fileName: material.fileName,
        timestamp: new Date().toISOString()
      };

      // Clean up previous URL
      if (fileUrl && !fileUrl.startsWith('http')) {
        URL.revokeObjectURL(fileUrl);
      }

      if (material.materialType === 'LINK') {
        setFileUrl(material.filePath);
        debug.method = 'External link';
        debug.url = material.filePath;
        setDebugInfo(debug);
        return;
      }

      // Get filename from path
      const pathParts = material.filePath.split('/');
      const fileName = pathParts[pathParts.length - 1];
      
      debug.extractedFileName = fileName;

      // Build authenticated URL
      const credentials = btoa(`${user.email}:${user.password}`);
      const url = `${API_URL}/courses/materials/download/${fileName}`;
      
      debug.downloadUrl = url;
      debug.hasCredentials = !!credentials;

      // Test if file exists using debug endpoint
      try {
        const testResponse = await axios.get(`${API_URL}/debug/test-download/${material.id}`);
        debug.serverCheck = testResponse.data;
      } catch (e) {
        debug.serverCheckError = e.message;
      }

      setFileUrl(url);
      setDebugInfo(debug);
      
    } catch (error) {
      console.error('Error loading material file:', error);
      setDebugInfo(prev => ({
        ...prev,
        error: error.message
      }));
    }
  };

  const renderMaterialContent = () => {
    if (!currentMaterial) {
      return <div className="text-center py-5">Select a material to view</div>;
    }

    switch (currentMaterial.materialType) {
      case 'PDF':
        return (
          <div>
            <div className="alert alert-info">
              <strong>PDF File:</strong> {currentMaterial.fileName}
            </div>
            
            {fileUrl ? (
              <>
                {/* Method 1: IFrame */}
                <div className="mb-3">
                  <h6>Method 1: IFrame (Basic Auth)</h6>
                  <iframe
                    src={`${fileUrl}?credentials=${btoa(user.email + ':' + user.password)}`}
                    width="100%"
                    height="600px"
                    title={currentMaterial.title}
                    style={{ border: '1px solid #ddd', borderRadius: '8px' }}
                  />
                </div>

                {/* Method 2: Object tag */}
                <div className="mb-3">
                  <h6>Method 2: Object Tag</h6>
                  <object
                    data={fileUrl}
                    type="application/pdf"
                    width="100%"
                    height="600px"
                    style={{ border: '1px solid #ddd' }}
                  >
                    <p>PDF cannot be displayed. <a href={fileUrl} download>Download instead</a></p>
                  </object>
                </div>

                {/* Fallback download button */}
                <div className="mt-3 d-flex gap-2">
                  <a 
                    href={fileUrl} 
                    download={currentMaterial.fileName}
                    className="btn btn-primary"
                  >
                    <i className="bi bi-download me-2"></i>
                    Download PDF
                  </a>
                  <a 
                    href={fileUrl} 
                    target="_blank" 
                    rel="noopener noreferrer" 
                    className="btn btn-outline-primary"
                  >
                    <i className="bi bi-box-arrow-up-right me-2"></i>
                    Open in New Tab
                  </a>
                </div>
              </>
            ) : (
              <div className="text-center py-5">
                <div className="spinner-border text-primary"></div>
                <p className="mt-3">Loading PDF...</p>
              </div>
            )}
          </div>
        );

      case 'VIDEO':
        return (
          <div>
            {fileUrl ? (
              <video
                controls
                width="100%"
                src={fileUrl}
                style={{ maxHeight: '600px', backgroundColor: '#000' }}
              >
                Your browser does not support the video tag.
              </video>
            ) : (
              <div className="text-center py-5">
                <div className="spinner-border text-primary"></div>
                <p className="mt-3">Loading video...</p>
              </div>
            )}
          </div>
        );

      case 'LINK':
        return (
          <div className="ratio ratio-16x9">
            <iframe
              src={currentMaterial.filePath}
              allowFullScreen
              title={currentMaterial.title}
              style={{ border: 'none', borderRadius: '8px' }}
            />
          </div>
        );

      default:
        return <div>Unsupported material type</div>;
    }
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border text-primary"></div>
      </div>
    );
  }

  return (
    <div className="container-fluid">
      <div className="row">
        {/* Sidebar */}
        <div className="col-md-3 bg-light p-3">
          <button className="btn btn-link p-0 mb-3" onClick={() => navigate('/student/my-courses')}>
            <i className="bi bi-arrow-left me-2"></i>
            Back
          </button>

          <div className="materials-list">
            {materials.map((material) => (
              <div
                key={material.id}
                className={`p-2 mb-2 rounded ${currentMaterial?.id === material.id ? 'bg-primary text-white' : 'bg-white'}`}
                onClick={() => setCurrentMaterial(material)}
                style={{ cursor: 'pointer', border: '1px solid #ddd' }}
              >
                <div className="small">{material.title}</div>
                <small className="text-muted">{material.materialType}</small>
              </div>
            ))}
          </div>
        </div>

        {/* Main Content */}
        <div className="col-md-9 p-4">
          {currentMaterial && (
            <>
              <h3>{currentMaterial.title}</h3>
              {renderMaterialContent()}
            </>
          )}

          {/* Debug Panel */}
          {showDebug && debugInfo && (
            <div className="card mt-4 border-warning">
              <div className="card-header bg-warning d-flex justify-content-between">
                <strong>🔍 Debug Info</strong>
                <button 
                  className="btn btn-sm btn-close" 
                  onClick={() => setShowDebug(false)}
                ></button>
              </div>
              <div className="card-body">
                <pre style={{ fontSize: '12px', maxHeight: '300px', overflow: 'auto' }}>
                  {JSON.stringify(debugInfo, null, 2)}
                </pre>
                <div className="mt-3">
                  <button 
                    className="btn btn-sm btn-outline-primary"
                    onClick={() => window.open(`${API_URL}/debug/files`, '_blank')}
                  >
                    Open Debug Console
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CourseMaterialViewer;