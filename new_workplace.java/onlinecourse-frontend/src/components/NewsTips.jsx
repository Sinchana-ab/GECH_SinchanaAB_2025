import React from 'react';

const news = [
  { id:1, title:'Platform Update: New Courses Added', date:'Nov 1, 2025', excerpt:'We added 20+ new courses in Data Science and AI.' },
  { id:2, title:'Scholarships Open for Spring 2026', date:'Oct 20, 2025', excerpt:'Apply now to cut course fees by up to 50%.' }
];

const tips = [
  { id:1, title:'Study Tip: Pomodoro Method', excerpt:'Use 25/5 minute intervals to stay focused.' },
  { id:2, title:'Career Tip: Build Projects', excerpt:'Add hands-on projects to your portfolio.' }
];

export default function NewsTips(){
  return (
    <section className="news-tips">
      <div style={{flex:'1 1 400px'}}>
        <h4>Latest News</h4>
        {news.map(n=>(
          <div className="news-card mt-3" key={n.id}>
            <div style={{fontSize:12, color:'#6b6b6b'}}>{n.date}</div>
            <h6 style={{marginTop:6}}>{n.title}</h6>
            <p style={{color:'#6b6b6b'}}>{n.excerpt}</p>
            <a href="#" className="text-success">Read more →</a>
          </div>
        ))}
      </div>

      <div style={{flex:'1 1 400px'}}>
        <h4>Educational Tips & Tricks</h4>
        {tips.map(t=>(
          <div className="tips-card mt-3" key={t.id}>
            <h6>{t.title}</h6>
            <p style={{color:'#6b6b6b'}}>{t.excerpt}</p>
            <a href="#" className="text-success">Learn more →</a>
          </div>
        ))}
      </div>
    </section>
  );
}