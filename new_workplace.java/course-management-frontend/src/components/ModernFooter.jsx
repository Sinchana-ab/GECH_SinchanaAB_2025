
import React from 'react';
import { Link } from 'react-router-dom';
import './ModernFooter.css';

const ModernFooter = () => {
  return (
    <footer className="modern-footer">
      <div className="container">
        <div className="row g-4 mb-4">
          <div className="col-lg-4">
            <div className="footer-brand mb-3">
              <i className="bi bi-mortarboard-fill me-2"></i>
              EduLearn
            </div>
            <p className="text-white-50 mb-4">
              Empowering learners worldwide through quality online education. 
              Transform your future with expert-led courses.
            </p>
            <div className="social-links">
              <a href="#" className="social-link">
                <i className="bi bi-facebook"></i>
              </a>
              <a href="#" className="social-link">
                <i className="bi bi-twitter"></i>
              </a>
              <a href="#" className="social-link">
                <i className="bi bi-instagram"></i>
              </a>
              <a href="#" className="social-link">
                <i className="bi bi-linkedin"></i>
              </a>
              <a href="#" className="social-link">
                <i className="bi bi-youtube"></i>
              </a>
            </div>
          </div>
          
          <div className="col-lg-2 col-md-4">
            <h5 className="footer-title mb-3">Quick Links</h5>
            <ul className="footer-links">
              <li><Link to="/about"><i className="bi bi-chevron-right"></i> About Us</Link></li>
              <li><Link to="/courses"><i className="bi bi-chevron-right"></i> Courses</Link></li>
              <li><Link to="/contact"><i className="bi bi-chevron-right"></i> Contact</Link></li>
              <li><Link to="/careers"><i className="bi bi-chevron-right"></i> Careers</Link></li>
            </ul>
          </div>
          
          <div className="col-lg-2 col-md-4">
            <h5 className="footer-title mb-3">Categories</h5>
            <ul className="footer-links">
              <li><a href="#"><i className="bi bi-chevron-right"></i> Programming</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> Design</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> Business</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> Marketing</a></li>
            </ul>
          </div>
          
          <div className="col-lg-2 col-md-4">
            <h5 className="footer-title mb-3">Support</h5>
            <ul className="footer-links">
              <li><a href="#"><i className="bi bi-chevron-right"></i> Help Center</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> FAQ</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> Privacy Policy</a></li>
              <li><a href="#"><i className="bi bi-chevron-right"></i> Terms of Service</a></li>
            </ul>
          </div>
          
          <div className="col-lg-2 col-md-4">
            <h5 className="footer-title mb-3">Newsletter</h5>
            <p className="text-white-50 small mb-3">Subscribe to get updates</p>
            <div className="newsletter-form">
              <input 
                type="email" 
                className="form-control mb-2" 
                placeholder="Your email"
              />
              <button className="btn btn-gradient w-100">
                <i className="bi bi-send me-2"></i>
                Subscribe
              </button>
            </div>
          </div>
        </div>
        
        <div className="footer-bottom pt-4 mt-4 border-top border-secondary">
          <div className="row align-items-center">
            <div className="col-md-6 text-center text-md-start mb-3 mb-md-0">
              <p className="text-white-50 mb-0">
                © 2024 EduLearn. All rights reserved.
              </p>
            </div>
            <div className="col-md-6 text-center text-md-end">
              <div className="payment-methods">
                <i className="bi bi-credit-card fs-4 text-white-50 me-2"></i>
                <i className="bi bi-paypal fs-4 text-white-50 me-2"></i>
                <i className="bi bi-stripe fs-4 text-white-50"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default ModernFooter;