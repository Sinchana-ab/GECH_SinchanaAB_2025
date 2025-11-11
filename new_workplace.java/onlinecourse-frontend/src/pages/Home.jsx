import React from "react";
import Header from "../components/Header";
import Hero from "../components/Hero";
import Courses from "../components/Courses";
import HowItWorks from "../components/HowItWorks";
import Testimonials from "../components/Testimonials";
import NewsTips from "../components/NewsTips";
import Footer from "../components/Footer";

export default function Home() {
  return (
    <div className="app">
      <Header />
      <main>
        <Hero />
        <div className="container">
          <Courses />
          <HowItWorks />
          <Testimonials />
          <NewsTips />
        </div>
      </main>
      <Footer />
    </div>
  );
}
