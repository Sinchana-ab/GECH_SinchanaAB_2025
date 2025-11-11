import React from 'react';

export default function Footer(){
  return (
    <footer className="footer">
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <h5>EduLe</h5>
            <p style={{maxWidth:300}}>EduLe is your online learning partner. Build skills and earn certificates from top instructors.</p>
          </div>
          <div className="col-md-2">
            <h6>Company</h6>
            <ul className="list-unstyled">
              <li><a href="#">About</a></li>
              <li><a href="#">Careers</a></li>
              <li><a href="#">Contact</a></li>
            </ul>
          </div>
          <div className="col-md-3">
            <h6>Courses</h6>
            <ul className="list-unstyled">
              <li><a href="#">Data Science</a></li>
              <li><a href="#">Design</a></li>
              <li><a href="#">Marketing</a></li>
            </ul>
          </div>
          <div className="col-md-3">
            <h6>Subscribe</h6>
            <div className="input-group">
              <input className="form-control" placeholder="Your email" />
              <button className="btn btn-success">Subscribe</button>
            </div>
          </div>
        </div>

        <div className="text-center mt-4" style={{opacity:0.7}}>
          © {new Date().getFullYear()} EduLe. All rights reserved.
        </div>
      </div>
    </footer>
  );
}