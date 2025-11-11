import React from 'react';

const sampleCourses = [
  { id:1, title:'Data Science and Machine Learning with Python - Hands On!', author:'Jason Williams', price:385, duration:'08 hr 15 mins', lectures:29, img:'https://images.unsplash.com/photo-1516251193007-45ef944ab0c6?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=6be540d8c3525b0fbd0b0b0b' },
  { id:2, title:'Create Amazing Color Schemes for Your UX Design Projects', author:'Pamela Foster', price:420, duration:'08 hr 15 mins', lectures:29, img:'https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=6f8f445e2f1d1b5343f1a1f2' },
  { id:3, title:'Culture & Leadership: Strategies for a Successful Business', author:'Rose Simmons', price:295, duration:'08 hr 15 mins', lectures:29, img:'https://images.unsplash.com/photo-1492711236154-8fdc8f46e2f5?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=1a4ef0e7c3fa3b2f4b5b1d2f' },
  { id:4, title:'Project Management Fundamentals', author:'Alex Morgan', price:210, duration:'06 hr', lectures:20, img:'https://images.unsplash.com/photo-1521737604893-d14cc237f11d?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=8e3c4e2b9d8a6c3c97b1ab2c' },
  { id:5, title:'Digital Marketing Basics', author:'Samantha Lee', price:180, duration:'05 hr', lectures:14, img:'https://images.unsplash.com/photo-1522202176988-66273c2fd55f?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=3f81a9b5e3a7dbe452c5d2b6' },
  { id:6, title:'UI/UX Design for Beginners', author:'Michael Chan', price:240, duration:'07 hr', lectures:18, img:'https://images.unsplash.com/photo-1542744094-24638eff58bb?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&s=a1d1e1f2b3a2c1d2e3f4a5b6' },
];

const categories = ['Data Science','Business','Financial','Marketing','Design'];

export default function Courses(){
  return (
    <section className="courses-section">
      <div className="category-pill d-flex align-items-center justify-content-between">
        <div className="d-flex gap-2 align-items-center overflow-auto">
          {categories.map((c,i)=>(
            <button key={i} className="btn btn-sm btn-light rounded-pill border">{c}</button>
          ))}
        </div>
        <div>
          <input className="form-control" placeholder="Search your course" style={{minWidth:220}} />
        </div>
      </div>

      <div className="row g-3">
        {sampleCourses.map(course=>(
          <div className="col-md-4" key={course.id}>
            <div className="course-card">
              <img className="course-img" src={course.img} alt={course.title}/>
              <div className="d-flex align-items-center mt-3 mb-2">
                <img src={`https://i.pravatar.cc/40?u=${course.author}`} alt="avatar" style={{width:40, height:40, borderRadius:50}} />
                <div className="ms-2">
                  <div style={{fontSize:14, fontWeight:600}}>{course.author}</div>
                  <small className="text-muted">Science</small>
                </div>
              </div>
              <h6 style={{minHeight:48}}>{course.title}</h6>
              <div className="d-flex justify-content-between text-muted mt-2">
                <small>⏱ {course.duration}</small>
                <small>📚 {course.lectures} Lectures</small>
              </div>
              <div className="d-flex justify-content-between align-items-center mt-3">
                <div>
                  <div style={{fontWeight:700, color:'#167a3b'}}>${course.price.toFixed(2)}</div>
                  <div style={{textDecoration:'line-through', color:'#bbbbbb', fontSize:12}}>${(course.price+50).toFixed(2)}</div>
                </div>
                <div>
                  <span style={{color:'#f7c24c'}}>★★★★☆</span>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}