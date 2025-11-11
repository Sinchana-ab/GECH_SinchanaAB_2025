import React, { useState } from 'react';

const Contact = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: ''
  });
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you would typically send to backend
    console.log('Form submitted:', formData);
    setSubmitted(true);
    setTimeout(() => {
      setSubmitted(false);
      setFormData({ name: '', email: '', subject: '', message: '' });
    }, 3000);
  };

  return (
    <div className="contact-page">
      {/* Header */}
      <section className="py-5 bg-primary text-white text-center">
        <div className="container">
          <h1 className="display-4 fw-bold mb-3">Get In Touch</h1>
          <p className="lead">We'd love to hear from you. Send us a message!</p>
        </div>
      </section>

      <div className="container py-5">
        <div className="row g-5">
          {/* Contact Form */}
          <div className="col-lg-8">
            <div className="card shadow-sm">
              <div className="card-body p-4">
                <h3 className="mb-4">Send Us a Message</h3>
                
                {submitted && (
                  <div className="alert alert-success">
                    <i className="bi bi-check-circle me-2"></i>
                    Thank you! Your message has been sent successfully.
                  </div>
                )}

                <form onSubmit={handleSubmit}>
                  <div className="row g-3">
                    <div className="col-md-6">
                      <label className="form-label">Your Name *</label>
                      <input
                        type="text"
                        className="form-control"
                        value={formData.name}
                        onChange={(e) => setFormData({...formData, name: e.target.value})}
                        required
                      />
                    </div>
                    <div className="col-md-6">
                      <label className="form-label">Your Email *</label>
                      <input
                        type="email"
                        className="form-control"
                        value={formData.email}
                        onChange={(e) => setFormData({...formData, email: e.target.value})}
                        required
                      />
                    </div>
                    <div className="col-12">
                      <label className="form-label">Subject *</label>
                      <input
                        type="text"
                        className="form-control"
                        value={formData.subject}
                        onChange={(e) => setFormData({...formData, subject: e.target.value})}
                        required
                      />
                    </div>
                    <div className="col-12">
                      <label className="form-label">Message *</label>
                      <textarea
                        className="form-control"
                        rows="6"
                        value={formData.message}
                        onChange={(e) => setFormData({...formData, message: e.target.value})}
                        required
                      ></textarea>
                    </div>
                    <div className="col-12">
                      <button type="submit" className="btn btn-primary btn-lg px-5">
                        <i className="bi bi-send me-2"></i>
                        Send Message
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>

          {/* Contact Info */}
          <div className="col-lg-4">
            <div className="card shadow-sm mb-4">
              <div className="card-body p-4">
                <h5 className="mb-4">Contact Information</h5>
                
                <div className="mb-4">
                  <div className="d-flex align-items-start mb-3">
                    <i className="bi bi-geo-alt-fill text-primary me-3" style={{ fontSize: '1.5rem' }}></i>
                    <div>
                      <h6 className="mb-1">Address</h6>
                      <p className="text-muted mb-0">123 Learning Street<br/>Education City, EC 12345</p>
                    </div>
                  </div>
                </div>

                <div className="mb-4">
                  <div className="d-flex align-items-start mb-3">
                    <i className="bi bi-envelope-fill text-primary me-3" style={{ fontSize: '1.5rem' }}></i>
                    <div>
                      <h6 className="mb-1">Email</h6>
                      <p className="text-muted mb-0">support@coursemanagement.com</p>
                    </div>
                  </div>
                </div>

                <div className="mb-4">
                  <div className="d-flex align-items-start mb-3">
                    <i className="bi bi-telephone-fill text-primary me-3" style={{ fontSize: '1.5rem' }}></i>
                    <div>
                      <h6 className="mb-1">Phone</h6>
                      <p className="text-muted mb-0">+1 (555) 123-4567</p>
                    </div>
                  </div>
                </div>

                <div>
                  <div className="d-flex align-items-start">
                    <i className="bi bi-clock-fill text-primary me-3" style={{ fontSize: '1.5rem' }}></i>
                    <div>
                      <h6 className="mb-1">Business Hours</h6>
                      <p className="text-muted mb-0">
                        Mon - Fri: 9:00 AM - 6:00 PM<br/>
                        Sat - Sun: Closed
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="card shadow-sm">
              <div className="card-body p-4">
                <h5 className="mb-4">Follow Us</h5>
                <div className="d-flex gap-3">
                  <a href="#" className="btn btn-outline-primary btn-lg">
                    <i className="bi bi-facebook"></i>
                  </a>
                  <a href="#" className="btn btn-outline-info btn-lg">
                    <i className="bi bi-twitter"></i>
                  </a>
                  <a href="#" className="btn btn-outline-danger btn-lg">
                    <i className="bi bi-instagram"></i>
                  </a>
                  <a href="#" className="btn btn-outline-primary btn-lg">
                    <i className="bi bi-linkedin"></i>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* FAQ Section */}
        <div className="row mt-5">
          <div className="col-12">
            <h3 className="text-center mb-4">Frequently Asked Questions</h3>
            <div className="accordion" id="faqAccordion">
              <div className="accordion-item">
                <h2 className="accordion-header">
                  <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#faq1">
                    How do I enroll in a course?
                  </button>
                </h2>
                <div id="faq1" className="accordion-collapse collapse show" data-bs-parent="#faqAccordion">
                  <div className="accordion-body">
                    Simply browse our course catalog, select a course you're interested in, and click the "Enroll Now" button. You'll need to create an account if you haven't already.
                  </div>
                </div>
              </div>
              <div className="accordion-item">
                <h2 className="accordion-header">
                  <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq2">
                    Do I receive a certificate after completing a course?
                  </button>
                </h2>
                <div id="faq2" className="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                  <div className="accordion-body">
                    Yes! Upon successful completion of a course and passing all assessments, you'll receive a downloadable certificate that you can share on LinkedIn and other platforms.
                  </div>
                </div>
              </div>
              <div className="accordion-item">
                <h2 className="accordion-header">
                  <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq3">
                    Can I access courses on mobile devices?
                  </button>
                </h2>
                <div id="faq3" className="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                  <div className="accordion-body">
                    Absolutely! Our platform is fully responsive and works seamlessly on all devices including smartphones, tablets, and desktop computers.
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;