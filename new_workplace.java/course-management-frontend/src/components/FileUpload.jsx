import React, { useState, useRef } from 'react';
import { materialAPI } from '../services/materialAPI';

const FileUpload = ({ courseId, onUploadSuccess }) => {
  const [uploading, setUploading] = useState(false);
  const [dragActive, setDragActive] = useState(false);
  const [uploadType, setUploadType] = useState('file');
  const [uploadProgress, setUploadProgress] = useState(0);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    materialType: 'VIDEO',
    file: null,
    externalUrl: ''
  });
  const fileInputRef = useRef(null);

  const materialTypes = {
    VIDEO: { icon: 'bi-camera-video', color: 'danger', accept: 'video/*' },
    PDF: { icon: 'bi-file-pdf', color: 'danger', accept: '.pdf' },
    DOCUMENT: { icon: 'bi-file-text', color: 'primary', accept: '.doc,.docx,.txt' },
    IMAGE: { icon: 'bi-image', color: 'success', accept: 'image/*' }
  };

  const handleDrag = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.type === "dragenter" || e.type === "dragover") {
      setDragActive(true);
    } else if (e.type === "dragleave") {
      setDragActive(false);
    }
  };

  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragActive(false);
    
    if (e.dataTransfer.files && e.dataTransfer.files[0]) {
      handleFileSelect(e.dataTransfer.files[0]);
    }
  };

  const handleFileSelect = (file) => {
    if (file.size > 100 * 1024 * 1024) {
      alert('File size exceeds 100MB limit');
      return;
    }
    setFormData({ ...formData, file });
  };

  const handleFileInput = (e) => {
    if (e.target.files && e.target.files[0]) {
      handleFileSelect(e.target.files[0]);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.title) {
      alert('Please enter a title');
      return;
    }

    setUploading(true);
    setUploadProgress(0);

    try {
      if (uploadType === 'file') {
        if (!formData.file) {
          alert('Please select a file');
          setUploading(false);
          return;
        }

        const uploadFormData = new FormData();
        uploadFormData.append('file', formData.file);
        uploadFormData.append('title', formData.title);
        uploadFormData.append('description', formData.description);
        uploadFormData.append('materialType', formData.materialType);

        // Simulate progress for better UX
        const progressInterval = setInterval(() => {
          setUploadProgress(prev => {
            if (prev >= 90) {
              clearInterval(progressInterval);
              return 90;
            }
            return prev + 10;
          });
        }, 200);

        const response = await materialAPI.uploadMaterial(courseId, uploadFormData);
        
        clearInterval(progressInterval);
        setUploadProgress(100);
        
        if (response.data.success) {
          setTimeout(() => {
            alert('File uploaded successfully!');
            resetForm();
            onUploadSuccess?.();
          }, 500);
        }
      } else {
        if (!formData.externalUrl) {
          alert('Please enter a URL');
          setUploading(false);
          return;
        }

        const response = await materialAPI.addExternalLink(courseId, {
          title: formData.title,
          description: formData.description,
          materialType: 'LINK',
          filePath: formData.externalUrl
        });

        if (response.data.success) {
          alert('Link added successfully!');
          resetForm();
          onUploadSuccess?.();
        }
      }
    } catch (error) {
      console.error('Upload error:', error);
      alert(error.response?.data?.message || 'Upload failed. Please try again.');
    } finally {
      setUploading(false);
      setUploadProgress(0);
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      materialType: 'VIDEO',
      file: null,
      externalUrl: ''
    });
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  return (
    <div className="card shadow-sm border-0">
      <div className="card-header" style={{ background: 'var(--gradient-primary)', color: 'white' }}>
        <h5 className="mb-0">
          <i className="bi bi-cloud-upload me-2"></i>
          Upload Course Material
        </h5>
      </div>
      <div className="card-body p-4">
        {/* Upload Type Selector */}
        <div className="btn-group w-100 mb-4" role="group">
          <button
            type="button"
            className={`btn ${uploadType === 'file' ? 'btn-primary' : 'btn-outline-primary'}`}
            onClick={() => setUploadType('file')}
          >
            <i className="bi bi-upload me-2"></i>
            Upload File
          </button>
          <button
            type="button"
            className={`btn ${uploadType === 'link' ? 'btn-primary' : 'btn-outline-primary'}`}
            onClick={() => setUploadType('link')}
          >
            <i className="bi bi-link-45deg me-2"></i>
            Add External Link
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label fw-bold">Title *</label>
            <input
              type="text"
              className="form-control modern-input"
              value={formData.title}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
              placeholder="e.g., Introduction to Java Programming"
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Description</label>
            <textarea
              className="form-control modern-input"
              rows="3"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              placeholder="Brief description..."
            ></textarea>
          </div>

          {uploadType === 'file' ? (
            <>
              <div className="mb-3">
                <label className="form-label fw-bold">Material Type *</label>
                <select
                  className="form-select modern-input"
                  value={formData.materialType}
                  onChange={(e) => setFormData({ ...formData, materialType: e.target.value })}
                >
                  <option value="VIDEO">Video</option>
                  <option value="PDF">PDF Document</option>
                  <option value="DOCUMENT">Document (Word, Text)</option>
                  <option value="IMAGE">Image</option>
                </select>
              </div>

              <div
                className={`border-2 border-dashed rounded p-4 text-center mb-3 ${
                  dragActive ? 'border-primary bg-primary bg-opacity-10' : 'border-secondary'
                }`}
                onDragEnter={handleDrag}
                onDragLeave={handleDrag}
                onDragOver={handleDrag}
                onDrop={handleDrop}
                onClick={() => fileInputRef.current?.click()}
                style={{ 
                  cursor: 'pointer', 
                  minHeight: '200px', 
                  display: 'flex', 
                  flexDirection: 'column', 
                  justifyContent: 'center',
                  transition: 'all 0.3s ease'
                }}
              >
                <input
                  ref={fileInputRef}
                  type="file"
                  className="d-none"
                  accept={materialTypes[formData.materialType].accept}
                  onChange={handleFileInput}
                />

                {formData.file ? (
                  <div>
                    <i 
                      className={`bi ${materialTypes[formData.materialType].icon} text-${materialTypes[formData.materialType].color}`} 
                      style={{ fontSize: '3rem' }}
                    ></i>
                    <h5 className="mt-3">{formData.file.name}</h5>
                    <p className="text-muted">
                      {(formData.file.size / (1024 * 1024)).toFixed(2)} MB
                    </p>
                    <button
                      type="button"
                      className="btn btn-sm btn-outline-danger"
                      onClick={(e) => {
                        e.stopPropagation();
                        setFormData({ ...formData, file: null });
                        if (fileInputRef.current) {
                          fileInputRef.current.value = '';
                        }
                      }}
                    >
                      <i className="bi bi-x-circle me-1"></i>
                      Remove
                    </button>
                  </div>
                ) : (
                  <div>
                    <i className="bi bi-cloud-upload text-primary" style={{ fontSize: '3rem' }}></i>
                    <h5 className="mt-3">Drag & Drop or Click to Upload</h5>
                    <p className="text-muted">
                      Maximum file size: 100MB
                      <br />
                      Supported: {materialTypes[formData.materialType].accept || 'All types'}
                    </p>
                  </div>
                )}
              </div>

              {uploading && uploadProgress > 0 && (
                <div className="mb-3">
                  <div className="d-flex justify-content-between mb-1">
                    <span className="small">Uploading...</span>
                    <span className="small">{uploadProgress}%</span>
                  </div>
                  <div className="modern-progress">
                    <div 
                      className="modern-progress-bar" 
                      style={{ width: `${uploadProgress}%` }}
                    ></div>
                  </div>
                </div>
              )}
            </>
          ) : (
            <div className="mb-3">
              <label className="form-label fw-bold">External URL *</label>
              <input
                type="url"
                className="form-control modern-input"
                value={formData.externalUrl}
                onChange={(e) => setFormData({ ...formData, externalUrl: e.target.value })}
                placeholder="https://www.youtube.com/watch?v=..."
                required
              />
              <small className="text-muted">
                YouTube, Vimeo, Google Drive, or any external resource
              </small>
            </div>
          )}

          <div className="d-grid gap-2">
            <button
              type="submit"
              className="btn btn-gradient btn-lg"
              disabled={uploading || (!formData.file && uploadType === 'file' && !formData.externalUrl)}
            >
              {uploading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2"></span>
                  Uploading...
                </>
              ) : (
                <>
                  <i className="bi bi-check-circle me-2"></i>
                  {uploadType === 'file' ? 'Upload File' : 'Add Link'}
                </>
              )}
            </button>
            <button
              type="button"
              className="btn btn-outline-secondary"
              onClick={resetForm}
              disabled={uploading}
            >
              <i className="bi bi-arrow-counterclockwise me-2"></i>
              Reset
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FileUpload;