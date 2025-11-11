export const About = () => {
  const team = [
    {
      name: 'John Doe',
      role: 'CEO & Founder',
      image: 'https://i.pravatar.cc/200?img=12',
      bio: 'Passionate about making education accessible to everyone'
    },
    {
      name: 'Jane Smith',
      role: 'Head of Education',
      image: 'https://i.pravatar.cc/200?img=5',
      bio: 'Expert in curriculum development and instructional design'
    },
    {
      name: 'Mike Johnson',
      role: 'CTO',
      image: 'https://i.pravatar.cc/200?img=8',
      bio: 'Building cutting-edge technology for better learning'
    },
    {
      name: 'Sarah Williams',
      role: 'Head of Content',
      image: 'https://i.pravatar.cc/200?img=9',
      bio: 'Curating the best learning experiences worldwide'
    }
  ];

  const values = [
    {
      icon: 'bi-lightbulb',
      title: 'Innovation',
      description: 'We constantly innovate to provide the best learning experience'
    },
    {
      icon: 'bi-heart',
      title: 'Quality',
      description: 'We maintain the highest standards in all our courses'
    },
    {
      icon: 'bi-people',
      title: 'Community',
      description: 'We believe in the power of learning together'
    },
    {
      icon: 'bi-trophy',
      title: 'Excellence',
      description: 'We strive for excellence in everything we do'
    }
  ];

  return (
    <div className="about-page">
      {/* Hero Section */}
      <section className="py-5 bg-primary text-white text-center">
        <div className="container">
          <h1 className="display-4 fw-bold mb-3">About Us</h1>
          <p className="lead">Empowering learners worldwide through quality education</p>
        </div>
      </section>

      {/* Mission Section */}
      <section className="py-5">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-6 mb-4 mb-lg-0">
              <img 
                src="https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=600" 
                alt="Our Mission" 
                className="img-fluid rounded shadow"
              />
            </div>
            <div className="col-lg-6">
              <h2 className="display-5 fw-bold mb-4">Our Mission</h2>
              <p className="lead mb-4">
                We're on a mission to make quality education accessible to everyone, everywhere.
              </p>
              <p className="text-muted mb-4">
                Our platform connects passionate instructors with eager learners, creating a vibrant community 
                of knowledge sharing. We believe that education is the key to unlocking human potential and 
                creating a better world.
              </p>
              <p className="text-muted">
                Through innovative technology and expert instruction, we're breaking down barriers to education 
                and helping millions of students achieve their goals.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Values Section */}
      <section className="py-5 bg-light">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">Our Values</h2>
            <p className="lead text-muted">The principles that guide everything we do</p>
          </div>
          <div className="row g-4">
            {values.map((value, index) => (
              <div key={index} className="col-md-6 col-lg-3">
                <div className="card h-100 border-0 text-center shadow-sm">
                  <div className="card-body">
                    <div className="rounded-circle bg-primary bg-opacity-10 d-inline-flex align-items-center justify-content-center mb-3" 
                         style={{ width: '80px', height: '80px' }}>
                      <i className={`bi ${value.icon} text-primary`} style={{ fontSize: '2rem' }}></i>
                    </div>
                    <h5 className="card-title">{value.title}</h5>
                    <p className="card-text text-muted">{value.description}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Team Section */}
      <section className="py-5">
        <div className="container">
          <div className="text-center mb-5">
            <h2 className="display-5 fw-bold mb-3">Meet Our Team</h2>
            <p className="lead text-muted">The people behind our success</p>
          </div>
          <div className="row g-4">
            {team.map((member, index) => (
              <div key={index} className="col-md-6 col-lg-3">
                <div className="card h-100 border-0 shadow-sm text-center">
                  <div className="card-body">
                    <img 
                      src={member.image} 
                      alt={member.name}
                      className="rounded-circle mb-3"
                      style={{ width: '120px', height: '120px', objectFit: 'cover' }}
                    />
                    <h5 className="card-title mb-1">{member.name}</h5>
                    <p className="text-primary mb-2">{member.role}</p>
                    <p className="card-text text-muted small">{member.bio}</p>
                    <div className="mt-3">
                      <a href="#" className="btn btn-sm btn-outline-primary me-2">
                        <i className="bi bi-linkedin"></i>
                      </a>
                      <a href="#" className="btn btn-sm btn-outline-primary">
                        <i className="bi bi-twitter"></i>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-5 bg-primary text-white">
        <div className="container">
          <div className="row text-center">
            <div className="col-md-3 col-6 mb-4 mb-md-0">
              <h2 className="display-4 fw-bold">10K+</h2>
              <p>Active Students</p>
            </div>
            <div className="col-md-3 col-6 mb-4 mb-md-0">
              <h2 className="display-4 fw-bold">500+</h2>
              <p>Quality Courses</p>
            </div>
            <div className="col-md-3 col-6">
              <h2 className="display-4 fw-bold">100+</h2>
              <p>Expert Instructors</p>
            </div>
            <div className="col-md-3 col-6">
              <h2 className="display-4 fw-bold">50+</h2>
              <p>Countries</p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-5">
        <div className="container text-center">
          <h2 className="display-5 fw-bold mb-4">Join Us Today</h2>
          <p className="lead mb-4">Start your learning journey with us</p>
          <a href="/register" className="btn btn-primary btn-lg px-5">
            Get Started Free
          </a>
        </div>
      </section>
    </div>
  );
};

export default  About; 