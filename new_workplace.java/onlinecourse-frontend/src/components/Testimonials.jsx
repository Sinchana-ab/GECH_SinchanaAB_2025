import React from 'react';

const data = [
  { id:1, name:'Sara Alexander', role:'Product Designer, USA', text:'Lorem Ipsum has been the industry\'s standard dummy text since the 1500s.', avatar:'https://i.pravatar.cc/80?img=5' },
  { id:2, name:'Melissa Roberts', role:'Product Designer, USA', text:'Lorem Ipsum has been the industry\'s standard dummy text since the 1500s.', avatar:'https://i.pravatar.cc/80?img=6' },
];

export default function Testimonials(){
  return (
    <section className="testimonials">
      <div style={{textAlign:'center', marginBottom:24}}>
        <div style={{color:'#2c8a58'}}>Student Testimonial</div>
        <h2>Feedback From <span style={{color:'#2c8a58'}}>Student</span></h2>
      </div>

      <div className="row g-3">
        {data.map(d=>(
          <div className="col-md-6" key={d.id}>
            <div className="testimonial-card">
              <img src={d.avatar} alt={d.name} style={{width:80, height:80, borderRadius:50, border:'6px solid #fff', marginTop:-40}} />
              <div style={{marginTop:10, color:'#f7c24c'}}>★★★★☆</div>
              <p style={{color:'#6b6b6b', marginTop:12}}>{d.text}</p>
              <h5 style={{marginTop:18}}>{d.name}</h5>
              <div style={{color:'#2c8a58'}}>{d.role}</div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}