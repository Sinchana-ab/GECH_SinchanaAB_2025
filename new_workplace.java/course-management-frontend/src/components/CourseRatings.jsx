// components/CourseRatings.jsx
import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const CourseRatings = ({ courseId }) => {
  const { user } = useAuth();
  const [ratings, setRatings] = useState([]);
  const [statistics, setStatistics] = useState(null);
  const [userRating, setUserRating] = useState(null);
  const [showRatingForm, setShowRatingForm] = useState(false);
  const [newRating, setNewRating] = useState({
    rating: 5,
    review: ''
  });
  const [loading, setLoading] = useState(true);

  const API_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchRatings();
    fetchStatistics();
    if (user) {
      checkUserRating();
    }
  }, [courseId, user]);

  const fetchRatings = async () => {
    try {
      const response = await axios.get(`${API_URL}/public/courses/${courseId}/ratings/top`);
      if (response.data.success) {
        setRatings(response.data.data);
      }
    } catch (err) {
      console.error('Failed to fetch ratings', err);
    }
  };

  const fetchStatistics = async () => {
    try {
      const response = await axios.get(`${API_URL}/public/courses/${courseId}/ratings/statistics`);
      if (response.data.success) {
        setStatistics(response.data.data);
      }
    } catch (err) {
      console.error('Failed to fetch statistics', err);
    } finally {
      setLoading(false);
    }
  };

  const checkUserRating = async () => {
    try {
      const response = await axios.get(`${API_URL}/student/ratings`, {
        params: { studentId: user.id }
      });
      if (response.data.success) {
        const myRating = response.data.data.find(r => r.courseId === courseId);
        if (myRating) {
          setUserRating(myRating);
          setNewRating({
            rating: myRating.rating,
            review: myRating.review
          });
        }
      }
    } catch (err) {
      console.error('Failed to check user rating', err);
    }
  };

  const handleSubmitRating = async (e) => {
    e.preventDefault();
    
    if (!user) {
      alert('Please login to rate this course');
      return;
    }

    try {
      const response = await axios.post(`${API_URL}/student/ratings`, {
        studentId: user.id,
        courseId: courseId,
        rating: newRating.rating,
        review: newRating.review
      });

      if (response.data.success) {
        alert(userRating ? 'Rating updated successfully!' : 'Rating submitted successfully!');
        setShowRatingForm(false);
        fetchRatings();
        fetchStatistics();
        checkUserRating();
      }
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to submit rating');
    }
  };

  const handleMarkHelpful = async (ratingId) => {
    try {
      await axios.put(`${API_URL}/student/ratings/${ratingId}/helpful`);
      fetchRatings();
    } catch (err) {
      console.error('Failed to mark as helpful', err);
    }
  };

  const renderStars = (rating, size = 'md', interactive = false, onChange = null) => {
    const stars = [];
    const sizeClass = size === 'lg' ? 'fs-3' : size === 'sm' ? 'fs-6' : 'fs-5';
    
    for (let i = 1; i <= 5; i++) {
      stars.push(
        <i
          key={i}
          className={`bi ${i <= rating ? 'bi-star-fill' : 'bi-star'} text-warning ${sizeClass} ${
            interactive ? 'cursor-pointer' : ''
          }`}
          style={{ cursor: interactive ? 'pointer' : 'default' }}
          onClick={() => interactive && onChange && onChange(i)}
        ></i>
      );
    }
    return stars;
  };

  if (loading) {
    return <div className="text-center"><div className="spinner-border"></div></div>;
  }

  return (
    <div className="course-ratings mt-4">
      {/* Rating Statistics */}
      {statistics && (
        <div className="card mb-4">
          <div className="card-body">
            <div className="row align-items-center">
              <div className="col-md-4 text-center border-end">
                <h1 className="display-3 mb-0">{statistics.averageRating.toFixed(1)}</h1>
                <div className="mb-2">
                  {renderStars(Math.round(statistics.averageRating), 'md')}
                </div>
                <p className="text-muted mb-0">
                  {statistics.totalRatings} {statistics.totalRatings === 1 ? 'rating' : 'ratings'}
                </p>
              </div>

              <div className="col-md-8">
                <div className="mb-2">
                  <div className="d-flex align-items-center">
                    <span className="me-2" style={{ width: '60px' }}>5 stars</span>
                    <div className="progress flex-grow-1 me-2">
                      <div
                        className="progress-bar bg-warning"
                        style={{
                          width: `${(statistics.fiveStars / statistics.totalRatings) * 100}%`
                        }}
                      ></div>
                    </div>
                    <span style={{ width: '40px' }}>{statistics.fiveStars}</span>
                  </div>
                </div>

                <div className="mb-2">
                  <div className="d-flex align-items-center">
                    <span className="me-2" style={{ width: '60px' }}>4 stars</span>
                    <div className="progress flex-grow-1 me-2">
                      <div
                        className="progress-bar bg-warning"
                        style={{
                          width: `${(statistics.fourStars / statistics.totalRatings) * 100}%`
                        }}
                      ></div>
                    </div>
                    <span style={{ width: '40px' }}>{statistics.fourStars}</span>
                  </div>
                </div>

                <div className="mb-2">
                  <div className="d-flex align-items-center">
                    <span className="me-2" style={{ width: '60px' }}>3 stars</span>
                    <div className="progress flex-grow-1 me-2">
                      <div
                        className="progress-bar bg-warning"
                        style={{
                          width: `${(statistics.threeStars / statistics.totalRatings) * 100}%`
                        }}
                      ></div>
                    </div>
                    <span style={{ width: '40px' }}>{statistics.threeStars}</span>
                  </div>
                </div>

                <div className="mb-2">
                  <div className="d-flex align-items-center">
                    <span className="me-2" style={{ width: '60px' }}>2 stars</span>
                    <div className="progress flex-grow-1 me-2">
                      <div
                        className="progress-bar bg-warning"
                        style={{
                          width: `${(statistics.twoStars / statistics.totalRatings) * 100}%`
                        }}
                      ></div>
                    </div>
                    <span style={{ width: '40px' }}>{statistics.twoStars}</span>
                  </div>
                </div>

                <div className="mb-2">
                  <div className="d-flex align-items-center">
                    <span className="me-2" style={{ width: '60px' }}>1 star</span>
                    <div className="progress flex-grow-1 me-2">
                      <div
                        className="progress-bar bg-warning"
                        style={{
                          width: `${(statistics.oneStar / statistics.totalRatings) * 100}%`
                        }}
                      ></div>
                    </div>
                    <span style={{ width: '40px' }}>{statistics.oneStar}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* User Rating Form */}
      {user && (
        <div className="mb-4">
          {!showRatingForm ? (
            <button
              className="btn btn-primary"
              onClick={() => setShowRatingForm(true)}
            >
              {userRating ? 'Update Your Rating' : 'Rate This Course'}
            </button>
          ) : (
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">
                  {userRating ? 'Update Your Rating' : 'Rate This Course'}
                </h5>
                <form onSubmit={handleSubmitRating}>
                  <div className="mb-3">
                    <label className="form-label">Your Rating</label>
                    <div>
                      {renderStars(newRating.rating, 'lg', true, (rating) =>
                        setNewRating({ ...newRating, rating })
                      )}
                    </div>
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Your Review (Optional)</label>
                    <textarea
                      className="form-control"
                      rows="4"
                      value={newRating.review}
                      onChange={(e) =>
                        setNewRating({ ...newRating, review: e.target.value })
                      }
                      placeholder="Share your experience with this course..."
                    ></textarea>
                  </div>

                  <div className="d-flex gap-2">
                    <button type="submit" className="btn btn-primary">
                      Submit Rating
                    </button>
                    <button
                      type="button"
                      className="btn btn-secondary"
                      onClick={() => setShowRatingForm(false)}
                    >
                      Cancel
                    </button>
                  </div>
                </form>
              </div>
            </div>
          )}
        </div>
      )}

      {/* Reviews List */}
      <h4 className="mb-3">Student Reviews</h4>
      {ratings.length === 0 ? (
        <p className="text-muted">No reviews yet. Be the first to review this course!</p>
      ) : (
        <div>
          {ratings.map((rating) => (
            <div key={rating.id} className="card mb-3">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-start mb-2">
                  <div>
                    <h6 className="mb-1">{rating.studentName}</h6>
                    <div className="mb-2">{renderStars(rating.rating, 'sm')}</div>
                  </div>
                  <small className="text-muted">
                    {new Date(rating.createdAt).toLocaleDateString()}
                  </small>
                </div>

                {rating.review && (
                  <p className="card-text mb-2">{rating.review}</p>
                )}

                <div className="d-flex align-items-center gap-3">
                  <button
                    className="btn btn-sm btn-outline-secondary"
                    onClick={() => handleMarkHelpful(rating.id)}
                  >
                    <i className="bi bi-hand-thumbs-up me-1"></i>
                    Helpful ({rating.helpfulCount})
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CourseRatings;