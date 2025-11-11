import React from 'react';

export default function HowItWorks(){
  const steps = [
    { id:1, title:'Find Your Course', desc:'Search and pick a course that fits your goals.' },
    { id:2, title:'Book A Seat', desc:'Choose schedule and reserve a spot.' },
    { id:3, title:'Get Certificate', desc:'Complete course and earn your certificate.' },
  ];

  return (
    <section className="how-section">
      <div style={{maxWidth:800, margin:'0 auto', textAlign:'center'}}>
        <div style={{color:'#2c8a58', fontWeight:600}}>Over 1,235+ Course</div>
        <h2>How It Work?</h2>
      </div>

      <div className="how-steps mt-4">
        {steps.map(s=>(
          <div className="how-card" key={s.id}>
            <div style={{fontSize:22, color:'#2c8a58', marginBottom:10}}>🔎</div>
            <h5>{s.title}</h5>
            <p style={{color:'#6b6b6b'}}>{s.desc}</p>
          </div>
        ))}
      </div>
    </section>
  );
}