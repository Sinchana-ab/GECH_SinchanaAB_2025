// pages/CourseList.jsx
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { courseAPI, ratingAPI } from '../services/api';

const CourseList = () => {
  const [courses, setCourses] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [selectedLevel, setSelectedLevel] = useState('all');
  const [courseRatings, setCourseRatings] = useState({});

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await courseAPI.getAllPublished();
      if (response.data.success) {
        const coursesData = response.data.data;
        setCourses(coursesData);
        
        // Fetch ratings for each course
        coursesData.forEach(course => {
          fetchCourseRating(course.id);
        });
      }
    } catch (err) {
      setError('Failed to load courses');
    } finally {
      setLoading(false);
    }
  };

  const fetchCourseRating = async (courseId) => {
    try {
      const response = await ratingAPI.getRatingStatistics(courseId);
      if (response.data.success) {
        setCourseRatings(prev => ({
          ...prev,
          [courseId]: response.data.data
        }));
      }
    } catch (err) {
      // Silently fail for ratings
      console.log('Failed to load rating for course', courseId);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      fetchCourses();
      return;
    }
    
    try {
      setLoading(true);
      const response = await courseAPI.searchCourses(searchTerm);
      if (response.data.success) {
        setCourses(response.data.data);
      }
    } catch (err) {
      setError('Search failed');
    } finally {
      setLoading(false);
    }
  };

  const renderStars = (rating) => {
    const stars = [];
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    
    for (let i = 0; i < fullStars; i++) {
      stars.push(<i key={`full-${i}`} className="bi bi-star-fill text-warning"></i>);
    }
    
    if (hasHalfStar) {
      stars.push(<i key="half" className="bi bi-star-half text-warning"></i>);
    }
    
    const emptyStars = 5 - stars.length;
    for (let i = 0; i < emptyStars; i++) {
      stars.push(<i key={`empty-${i}`} className="bi bi-star text-warning"></i>);
    }
    
    return stars;
  };

  // Get unique categories and levels
  const categories = ['all', ...new Set(courses.map(c => c.category).filter(Boolean))];
  const levels = ['all', 'BEGINNER', 'INTERMEDIATE', 'ADVANCED'];

  // Filter courses
  const filteredCourses = courses.filter(course => {
    const matchesCategory = selectedCategory === 'all' || course.category === selectedCategory;
    const matchesLevel = selectedLevel === 'all' || course.level === selectedLevel;
    return matchesCategory && matchesLevel;
  });

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
        <p className="mt-2">Loading courses...</p>
      </div>
    );
  }

  return (
    <div>
      {/* Header Section */}
      <div className="row mb-4">
        <div className="col-12">
          <h2 className="text-center mb-4">
            <i className="bi bi-mortarboard-fill text-primary me-2"></i>
            Explore Our Courses
          </h2>
          <p className="text-center text-muted mb-4">
            Discover courses taught by expert instructors and advance your skills
          </p>
        </div>
      </div>

      {/* Search and Filter Section */}
      <div className="row mb-4">
        <div className="col-md-12">
          <div className="card shadow-sm">
            <div className="card-body">
              <form onSubmit={handleSearch} className="mb-3">
                <div className="input-group">
                  <span className="input-group-text bg-white">
                    <i className="bi bi-search"></i>
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Search courses by title or keyword..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                  <button className="btn btn-primary" type="submit">
                    Search
                  </button>
                  {searchTerm && (
                    <button 
                      className="btn btn-outline-secondary" 
                      type="button"
                      onClick={() => {
                        setSearchTerm('');
                        fetchCourses();
                      }}
                    >
                      Clear
                    </button>
                  )}
                </div>
              </form>

              {/* Filters */}
              <div className="row">
                <div className="col-md-6 mb-2">
                  <label className="form-label small text-muted">Category</label>
                  <select 
                    className="form-select form-select-sm"
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                  >
                    {categories.map(cat => (
                      <option key={cat} value={cat}>
                        {cat === 'all' ? 'All Categories' : cat}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="col-md-6 mb-2">
                  <label className="form-label small text-muted">Level</label>
                  <select 
                    className="form-select form-select-sm"
                    value={selectedLevel}
                    onChange={(e) => setSelectedLevel(e.target.value)}
                  >
                    {levels.map(level => (
                      <option key={level} value={level}>
                        {level === 'all' ? 'All Levels' : level}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>
          </div>
          
          {error && (
            <div className="alert alert-danger mt-3" role="alert">
              <i className="bi bi-exclamation-triangle me-2"></i>
              {error}
            </div>
          )}
        </div>
      </div>

      {/* Results Count */}
      <div className="row mb-3">
        <div className="col-12">
          <p className="text-muted">
            Showing {filteredCourses.length} course{filteredCourses.length !== 1 ? 's' : ''}
          </p>
        </div>
      </div>

      {/* Courses Grid */}
      <div className="row">
        {filteredCourses.length === 0 ? (
          <div className="col-12">
            <div className="card shadow-sm">
              <div className="card-body text-center py-5">
                <i className="bi bi-inbox display-1 text-muted"></i>
                <h4 className="mt-3">No courses found</h4>
                <p className="text-muted">
                  {searchTerm ? 'Try adjusting your search or filters' : 'Check back later for new courses'}
                </p>
              </div>
            </div>
          </div>
        ) : (
          filteredCourses.map(course => {
            const rating = courseRatings[course.id];
            
            return (
              <div key={course.id} className="col-md-6 col-lg-4 mb-4">
                <div className="card h-100 shadow-sm hover-shadow" style={{ transition: 'all 0.3s' }}>
                  {course.thumbnail && (
                    <img 
                      src={course.thumbnail} 
                      className="card-img-top" 
                      alt={course.title}
                      style={{ height: '200px', objectFit: 'cover' }}
                      onError={(e) => {
                        e.target.src = 'https://via.placeholder.com/400x200?text=Course+Image';
                      }}
                    />
                  )}
                  
                  <div className="card-body d-flex flex-column">
                    <div className="mb-2">
                      <span className="badge bg-primary me-2">{course.category || 'General'}</span>
                      <span className="badge bg-secondary">{course.level || 'All Levels'}</span>
                    </div>

                    <h5 className="card-title">{course.title}</h5>
                    
                    <p className="card-text text-muted flex-grow-1">
                      {course.description?.substring(0, 100)}
                      {course.description?.length > 100 ? '...' : ''}
                    </p>
                    
                    {/* Rating Display */}
                    {rating && rating.totalRatings > 0 ? (
                      <div className="mb-2">
                        <div className="d-flex align-items-center">
                          <div className="me-2">
                            {renderStars(rating.averageRating)}
                          </div>
                          <span className="text-warning fw-bold me-2">
                            {rating.averageRating.toFixed(1)}
                          </span>
                          <span className="text-muted small">
                            ({rating.totalRatings} {rating.totalRatings === 1 ? 'review' : 'reviews'})
                          </span>
                        </div>
                      </div>
                    ) : (
                      <div className="mb-2">
                        <span className="text-muted small">
                          <i className="bi bi-star text-muted me-1"></i>
                          No reviews yet
                        </span>
                      </div>
                    )}

                    <div className="mb-2">
                      <small className="text-muted">
                        <i className="bi bi-person me-1"></i>
                        {course.instructorName}
                      </small>
                    </div>
                    
                    <div className="mb-3">
                      <small className="text-muted">
                        <i className="bi bi-clock me-1"></i>
                        {course.durationHours} hours
                      </small>
                      <small className="text-muted ms-3">
                        <i className="bi bi-people me-1"></i>
                        {course.enrollmentCount || 0} students
                      </small>
                    </div>
                    
                    <div className="d-flex justify-content-between align-items-center mt-auto">
                      {course.price ? (
                        <h5 className="text-success mb-0">${course.price}</h5>
                      ) : (
                        <span className="badge bg-success">Free</span>
                      )}
                      <Link 
                        to={`/courses/${course.id}`} 
                        className="btn btn-primary btn-sm"
                      >
                        View Details
                        <i className="bi bi-arrow-right ms-2"></i>
                      </Link>
                    </div>
                  </div>
                </div>
              </div>
            );
          })
        )}
      </div>

      {/* Call to Action for Instructors */}
      <div className="row mt-5">
        <div className="col-12">
          <div className="card bg-light border-0">
            <div className="card-body text-center py-5">
              <h3>Want to teach on our platform?</h3>
              <p className="text-muted mb-4">Share your knowledge with thousands of students worldwide</p>
              <Link to="/register" className="btn btn-primary btn-lg">
                Become an Instructor
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CourseList;