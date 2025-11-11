import React from 'react';

export default function Hero(){
  return (
    <section className="hero">
      <div className="container">
        <div className="row align-items-center">
          <div className="col-md-6 left">
            <div style={{color:'#2c8a58', fontWeight:600, marginBottom:8}}>Start your favourite course</div>
            <h1>Now learning from anywhere, and build your bright career.</h1>
            <p className="lead">It has survived not only five centuries but also the leap into electronic typesetting.</p>
            <div className="d-flex gap-2 mt-3">
              <button className="cta-btn">Start A Course</button>
              <button className="btn btn-outline-secondary">Watch Video</button>
            </div>
            <div className="d-flex gap-3 mt-4 align-items-center">
              <div className="stat-circle">
                <div style={{fontSize:20}}>1,235</div>
                <small style={{fontSize:12}}>courses</small>
              </div>
              <div style={{color:'#6b6b6b'}}>4.8 <span style={{color:'#f7c24c'}}>★</span> Rating (86K)</div>
            </div>
          </div>
          <div className="col-md-6 text-center">
            <img src="https://images.unsplash.com/photo-1522071820081-009f0129c71c?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=6d4f2e2d3b3e3dbb7dc9f3a7b8f9c1c6" alt="student" style={{width:'70%', borderRadius:12}}/>
          </div>
        </div>
      </div>
    </section>
  );
}