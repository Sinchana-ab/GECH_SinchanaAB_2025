// pages/Home.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { courseAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const Home = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const [featuredCourses, setFeaturedCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats] = useState({
    students: '10,000+',
    courses: '500+',
    instructors: '100+',
    countries: '50+'
  });

  useEffect(() => {
    fetchFeaturedCourses();
  }, []);

  const fetchFeaturedCourses = async () => {
    try {
      const response = await courseAPI.getAllPublished();
      if (response.data.success) {
        // Get first 6 courses as featured
        setFeaturedCourses(response.data.data.slice(0, 6));
      }
    } catch (err) {
      console.error('Failed to load courses');
    } finally {
      setLoading(false);
    }
  };

  const categories = [
    { name: 'Programming', icon: 'bi-code-slash', color: 'primary' },
    { name: 'Design', icon: 'bi-palette', color: 'success' },
    { name: 'Business', icon: 'bi-briefcase', color: 'warning' },
    { name: 'Marketing', icon: 'bi-graph-up', color: 'info' },
    { name: 'Photography', icon: 'bi-camera', color: 'danger' },
    { name: 'Music', icon: 'bi-music-note', color: 'secondary' }
  ];

  const features = [
    {
      icon: 'bi-book',
      title: 'Expert-Led Courses',
      description: 'Learn from industry professionals with real-world experience'
    },
    {
      icon: 'bi-award',
      title: 'Certificates',
      description: 'Earn recognized certificates upon course completion'
    },
    {
      icon: 'bi-clock-history',
      title: 'Flexible Learning',
      description: 'Study at your own pace, anytime, anywhere'
    },
    {
      icon: 'bi-people',
      title: 'Community Support',
      description: 'Join a community of learners and grow together'
    }
  ];

  const testimonials = [
    {
      name: 'Sarah Johnson',
      role: 'Web Developer',
      image: 'https://i.pravatar.cc/150?img=1',
      rating: 5,
      text: 'This platform transformed my career! The courses are comprehensive and the instructors are amazing.'
    },
    {
      name: 'Michael Chen',
      role: 'Data Scientist',
      image: 'https://i.pravatar.cc/150?img=2',
      rating: 5,
      text: 'Best online learning experience. The certificate helped me land my dream job!'
    },
    {
      name: 'Emma Davis',
      role: 'UX Designer',
      image: 'https://i.pravatar.cc/150?img=3',
      rating: 5,
      text: 'The quality of content and teaching methodology is outstanding. Highly recommended!'
    }
  ];

  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero-section" style={{
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        color: 'white',
        padding: '100px 0',
        marginTop: '-20px'
      }}>
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-6">
              <h1 className="display-3 fw-bold mb-4">
                Learn Without Limits
              </h1>
              <p className="lead mb-4">
                Discover thousands of courses taught by expert instructors. 
                Advance your career with in-demand skills.
              </p>
              <div className="d-flex gap-3 mb-4">
                {isAuthenticated ? (
                  <Link to="/courses" className="btn btn-light btn-lg px-5">
                    Browse Courses
                  </Link>
                ) : (
                  <>
                    <Link to="/register" className="btn btn-light btn-lg px-5">
                      Get Started Free
                    </Link>
                    <Link to="/login" className="btn btn-outline-light btn-lg px-5">
                      Sign In
                    </Link>
                  </>
                )}
              </div>
              <div className="d-flex gap-4 text-white">
                <div>
                  <h4 className="mb-0">{stats.students}</h4>
                  <small>Active Students</small>
                </div>
                <div>
                  <h4 className="mb-0">{stats.courses}</h4>
                  <small>Online Courses</small>
                </div>
                <div>
                  <h4 className="mb-0">{stats.instructors}</h4>
                  <small>Expert Instructors</small>
                </div>
              </div>
            </div>
            <div className="col-lg-6 d-none d-lg-block">
              <img 
                src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=600" 
                alt="Learning" 
                className="img-fluid rounded shadow-lg"
                style={{ borderRadius: '20px' }}
              />
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-5 bg-light">
        <div className="container">
          <div className="row text-center">
            <div className="col-md-3 col-6 mb-4 mb-md-0">
              <i className="bi bi-people-fill text-primary" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.students}</h3>
              <p className="text-muted">Happy Students</p>
            </div>
            <div className="col-md-3 col-6 mb-4 mb-md-0">
              <i className="bi bi-book-fill text-success" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.courses}</h3>
              <p className="text-muted">Quality Courses</p>
            </div>
            <div className="col-md-3 col-6">
              <i className="bi bi-award-fill text-warning" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.instructors}</h3>
              <p className="text-muted">Expert Instructors</p>
            </div>
            <div className="col-md-3 col-6">
              <i className="bi bi-globe text-info" style={{ fontSize: '3rem' }}></i>
              <h3 className="mt-3 mb-0">{stats.countries}</h3>
              <p className="text-muted">Countries Reached</p>
            </div>
          </div>
        </div>
      </section>

      {/* Categories Section */}
      <section className="py-5">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">Explore Top Categories</h2>
            <p className="lead text-muted">Discover courses in the most popular categories</p>
          </div>
          <div className="row g-4">
            {categories.map((category, index) => (
              <div key={index} className="col-md-4 col-lg-2">
                <Link 
                  to={`/courses?category=${category.name}`}
                  className="text-decoration-none"
                >
                  <div className="card h-100 text-center hover-shadow" style={{ 
                    transition: 'all 0.3s',
                    cursor: 'pointer'
                  }}>
                    <div className="card-body">
                      <i className={`bi ${category.icon} text-${category.color}`} 
                         style={{ fontSize: '3rem' }}></i>
                      <h6 className="mt-3 mb-0">{category.name}</h6>
                    </div>
                  </div>
                </Link>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Courses */}
      <section className="py-5 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">Featured Courses</h2>
            <p className="lead text-muted">Start learning with our most popular courses</p>
          </div>
          {loading ? (
            <div className="text-center">
              <div className="spinner-border text-primary"></div>
            </div>
          ) : (
            <div className="row g-4">
              {featuredCourses.map((course) => (
                <div key={course.id} className="col-md-6 col-lg-4">
                  <div className="card h-100 shadow-sm hover-shadow" style={{ transition: 'all 0.3s' }}>
                    <img 
                      src={course.thumbnail || 'https://via.placeholder.com/400x200'} 
                      className="card-img-top" 
                      alt={course.title}
                      style={{ height: '200px', objectFit: 'cover' }}
                    />
                    <div className="card-body">
                      <div className="mb-2">
                        <span className="badge bg-primary me-2">{course.category}</span>
                        <span className="badge bg-secondary">{course.level}</span>
                      </div>
                      <h5 className="card-title">{course.title}</h5>
                      <p className="card-text text-muted">
                        {course.description?.substring(0, 80)}...
                      </p>
                      <div className="d-flex justify-content-between align-items-center">
                        <small className="text-muted">
                          <i className="bi bi-people me-1"></i>
                          {course.enrollmentCount || 0} students
                        </small>
                        <Link to={`/courses/${course.id}`} className="btn btn-primary btn-sm">
                          Learn More
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
          <div className="text-center mt-5">
            <Link to="/courses" className="btn btn-primary btn-lg px-5">
              View All Courses
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-5">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">Why Choose Us</h2>
            <p className="lead text-muted">The best platform for online learning</p>
          </div>
          <div className="row g-4">
            {features.map((feature, index) => (
              <div key={index} className="col-md-6 col-lg-3">
                <div className="card h-100 border-0 text-center">
                  <div className="card-body">
                    <div className="rounded-circle bg-primary bg-opacity-10 d-inline-flex align-items-center justify-content-center mb-3" 
                         style={{ width: '80px', height: '80px' }}>
                      <i className={`bi ${feature.icon} text-primary`} style={{ fontSize: '2rem' }}></i>
                    </div>
                    <h5 className="card-title">{feature.title}</h5>
                    <p className="card-text text-muted">{feature.description}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials Section */}
      <section className="py-5 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">What Our Students Say</h2>
            <p className="lead text-muted">Join thousands of satisfied learners</p>
          </div>
          <div className="row g-4">
            {testimonials.map((testimonial, index) => (
              <div key={index} className="col-md-4">
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <div className="d-flex align-items-center mb-3">
                      <img 
                        src={testimonial.image} 
                        alt={testimonial.name}
                        className="rounded-circle me-3"
                        style={{ width: '60px', height: '60px', objectFit: 'cover' }}
                      />
                      <div>
                        <h6 className="mb-0">{testimonial.name}</h6>
                        <small className="text-muted">{testimonial.role}</small>
                        <div className="text-warning">
                          {[...Array(testimonial.rating)].map((_, i) => (
                            <i key={i} className="bi bi-star-fill"></i>
                          ))}
                        </div>
                      </div>
                    </div>
                    <p className="card-text text-muted">"{testimonial.text}"</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-5" style={{
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        color: 'white'
      }}>
        <div className="container text-center">
          <h2 className="display-5 fw-bold mb-4">Start Learning Today</h2>
          <p className="lead mb-4">Join millions of students learning on our platform</p>
          {!isAuthenticated && (
            <Link to="/register" className="btn btn-light btn-lg px-5">
              Sign Up Now - It's Free!
            </Link>
          )}
        </div>
      </section>

      {/* Add custom styles */}
      <style jsx>{`
        .hover-shadow:hover {
          box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;
          transform: translateY(-5px);
        }
      `}</style>
    </div>
  );
};

export default Home;