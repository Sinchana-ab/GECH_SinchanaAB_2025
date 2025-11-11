import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { courseAPI, enrollmentAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import CourseRatings from '../components/CourseRatings';

const CourseDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const [course, setCourse] = useState(null);
  const [loading, setLoading] = useState(true);
  const [enrolling, setEnrolling] = useState(false);
  const [message, setMessage] = useState('');
  const [isEnrolled, setIsEnrolled] = useState(false);

  useEffect(() => {
    fetchCourseDetails();
    if (isAuthenticated && user) {
      checkEnrollmentStatus();
    }
  }, [id, user]);

  const fetchCourseDetails = async () => {
    try {
      const response = await courseAPI.getCourseById(id);
      if (response.data.success) {
        setCourse(response.data.data);
      }
    } catch (err) {
      setMessage('Failed to load course details');
    } finally {
      setLoading(false);
    }
  };

  const checkEnrollmentStatus = async () => {
    try {
      const response = await enrollmentAPI.getStudentEnrollments(user.id);
      if (response.data.success) {
        const enrolled = response.data.data.some(e => e.courseId === parseInt(id));
        setIsEnrolled(enrolled);
      }
    } catch (err) {
      console.error('Failed to check enrollment status');
    }
  };

  const handleEnroll = async () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    setEnrolling(true);
    try {
      const response = await enrollmentAPI.enrollInCourse(user.id, course.id);
      if (response.data.success) {
        setMessage('Successfully enrolled! Check "My Courses" to start learning.');
        setIsEnrolled(true);
        setTimeout(() => navigate('/my-courses'), 2000);
      }
    } catch (err) {
      setMessage(err.response?.data?.message || 'Enrollment failed');
    } finally {
      setEnrolling(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-5"><div className="spinner-border"></div></div>;
  }

  if (!course) {
    return <div className="alert alert-danger">Course not found</div>;
  }

  return (
    <div className="row">
      <div className="col-md-8">
        <h2>{course.title}</h2>
        
        {message && (
          <div className={`alert ${message.includes('Success') ? 'alert-success' : 'alert-danger'}`}>
            {message}
          </div>
        )}
        
        {course.thumbnail && (
          <img 
            src={course.thumbnail} 
            alt={course.title}
            className="img-fluid rounded mb-4"
            style={{ maxHeight: '400px', width: '100%', objectFit: 'cover' }}
          />
        )}
        
        <div className="mb-4">
          <h4>About this course</h4>
          <p>{course.description}</p>
        </div>
        
        <div className="mb-4">
          <h4>What you'll learn</h4>
          <ul className="list-unstyled">
            <li><i className="bi bi-check-circle text-success me-2"></i>
              Comprehensive understanding of {course.title}
            </li>
            <li><i className="bi bi-check-circle text-success me-2"></i>
              Practical skills and hands-on experience
            </li>
            <li><i className="bi bi-check-circle text-success me-2"></i>
              Certificate upon completion
            </li>
          </ul>
        </div>

        {/* Course Ratings Component - NEW! */}
        <div className="mb-4">
          <CourseRatings courseId={parseInt(id)} />
        </div>
      </div>
      
      <div className="col-md-4">
        <div className="card shadow sticky-top" style={{ top: '20px' }}>
          <div className="card-body">
            {course.price && (
              <h3 className="text-success mb-3">${course.price}</h3>
            )}
            
            <ul className="list-unstyled mb-4">
              <li className="mb-2">
                <i className="bi bi-person me-2"></i>
                <strong>Instructor:</strong> {course.instructorName}
              </li>
              <li className="mb-2">
                <i className="bi bi-clock me-2"></i>
                <strong>Duration:</strong> {course.durationHours} hours
              </li>
              <li className="mb-2">
                <i className="bi bi-bar-chart me-2"></i>
                <strong>Level:</strong> {course.level}
              </li>
              <li className="mb-2">
                <i className="bi bi-people me-2"></i>
                <strong>Students:</strong> {course.enrollmentCount}
              </li>
            </ul>
            
            {isEnrolled ? (
              <button className="btn btn-success w-100 mb-2" disabled>
                <i className="bi bi-check-circle me-2"></i>
                Already Enrolled
              </button>
            ) : (
              <button 
                className="btn btn-primary w-100 mb-2"
                onClick={handleEnroll}
                disabled={enrolling}
              >
                {enrolling ? 'Enrolling...' : 'Enroll Now'}
              </button>
            )}
            
            {!isAuthenticated && (
              <p className="text-muted text-center small mb-0">
                Please login to enroll
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CourseDetail;