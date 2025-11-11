import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { BookOpen, Users, Award, Clock, Star, TrendingUp, Globe, Heart } from "lucide-react";

const Index = () => {
  const stats = [
    { label: "Active Students", value: "10,000+", icon: Users },
    { label: "Quality Courses", value: "500+", icon: BookOpen },
    { label: "Expert Instructors", value: "100+", icon: Award },
    { label: "Countries Reached", value: "50+", icon: Globe },
  ];

  const categories = [
    { name: "Programming", icon: "💻", color: "bg-primary" },
    { name: "Design", icon: "🎨", color: "bg-secondary" },
    { name: "Business", icon: "💼", color: "bg-accent" },
    { name: "Marketing", icon: "📈", color: "bg-primary" },
    { name: "Photography", icon: "📷", color: "bg-secondary" },
    { name: "Music", icon: "🎵", color: "bg-accent" },
  ];

  const features = [
    {
      icon: BookOpen,
      title: "Expert-Led Courses",
      description: "Learn from industry professionals with real-world experience",
    },
    {
      icon: Award,
      title: "Certificates",
      description: "Earn recognized certificates upon course completion",
    },
    {
      icon: Clock,
      title: "Flexible Learning",
      description: "Study at your own pace, anytime, anywhere",
    },
    {
      icon: Users,
      title: "Community Support",
      description: "Join a community of learners and grow together",
    },
  ];

  const testimonials = [
    {
      name: "Sarah Johnson",
      role: "Web Developer",
      rating: 5,
      text: "This platform transformed my career! The courses are comprehensive and the instructors are amazing.",
    },
    {
      name: "Michael Chen",
      role: "Data Scientist",
      rating: 5,
      text: "Best online learning experience. The certificate helped me land my dream job!",
    },
    {
      name: "Emma Davis",
      role: "UX Designer",
      rating: 5,
      text: "The quality of content and teaching methodology is outstanding. Highly recommended!",
    },
  ];

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="relative gradient-hero text-primary-foreground py-24 md:py-32 overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-br from-primary/20 to-transparent" />
        <div className="container relative z-10 mx-auto px-4">
          <div className="grid md:grid-cols-2 gap-12 items-center">
            <div className="space-y-6 animate-slide-up">
              <Badge className="bg-primary-foreground/20 text-primary-foreground hover:bg-primary-foreground/30 border-0">
                🎓 #1 Online Learning Platform
              </Badge>
              <h1 className="text-5xl md:text-6xl lg:text-7xl font-bold leading-tight">
                Learn Without
                <span className="block text-primary-glow">Limits</span>
              </h1>
              <p className="text-lg md:text-xl text-primary-foreground/90 max-w-lg">
                Discover thousands of courses taught by expert instructors. 
                Advance your career with in-demand skills.
              </p>
              <div className="flex flex-wrap gap-4">
                <Button size="lg" variant="secondary" className="shadow-medium hover:shadow-strong transition-smooth hover:scale-105">
                  Get Started Free
                </Button>
                <Button size="lg" variant="outline" className="bg-primary-foreground/10 border-primary-foreground/30 hover:bg-primary-foreground/20 text-primary-foreground">
                  Explore Courses
                </Button>
              </div>
            </div>
            <div className="hidden md:block animate-fade-in">
              <div className="relative">
                <div className="absolute inset-0 bg-gradient-to-br from-primary-glow/30 to-transparent rounded-3xl blur-3xl" />
                <img 
                  src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=800&q=80" 
                  alt="Students Learning" 
                  className="relative rounded-3xl shadow-strong w-full"
                />
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 bg-card">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {stats.map((stat, index) => (
              <div key={index} className="text-center animate-scale-in" style={{ animationDelay: `${index * 100}ms` }}>
                <div className="inline-flex items-center justify-center w-16 h-16 rounded-2xl gradient-card mb-4">
                  <stat.icon className="w-8 h-8 text-primary" />
                </div>
                <h3 className="text-3xl font-bold text-foreground">{stat.value}</h3>
                <p className="text-muted-foreground mt-1">{stat.label}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Categories Section */}
      <section className="py-20">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12 animate-slide-up">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">Explore Top Categories</h2>
            <p className="text-xl text-muted-foreground">Discover courses in the most popular categories</p>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
            {categories.map((category, index) => (
              <Card 
                key={index} 
                className="group cursor-pointer transition-smooth hover:scale-105 hover:shadow-medium border-2 hover:border-primary"
              >
                <CardContent className="p-6 text-center">
                  <div className={`w-16 h-16 ${category.color} rounded-2xl flex items-center justify-center text-3xl mb-3 mx-auto transition-smooth group-hover:scale-110`}>
                    {category.icon}
                  </div>
                  <h3 className="font-semibold text-foreground">{category.name}</h3>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Courses Section */}
      <section className="py-20 bg-muted/30">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12 animate-slide-up">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">Featured Courses</h2>
            <p className="text-xl text-muted-foreground">Start learning with our most popular courses</p>
          </div>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            {[1, 2, 3].map((course) => (
              <Card key={course} className="group overflow-hidden transition-smooth hover:shadow-strong hover:scale-105">
                <div className="relative h-48 overflow-hidden">
                  <img 
                    src={`https://images.unsplash.com/photo-${1517486808906 + course}?w=600&q=80`}
                    alt="Course"
                    className="w-full h-full object-cover transition-smooth group-hover:scale-110"
                  />
                  <div className="absolute top-4 left-4 flex gap-2">
                    <Badge className="bg-primary text-primary-foreground">Programming</Badge>
                    <Badge variant="secondary">Beginner</Badge>
                  </div>
                </div>
                <CardHeader>
                  <CardTitle className="line-clamp-2">Complete Web Development Bootcamp 2024</CardTitle>
                  <CardDescription className="line-clamp-2">
                    Master web development from scratch with HTML, CSS, JavaScript, React, and more.
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-2">
                      <Users className="w-4 h-4 text-muted-foreground" />
                      <span className="text-sm text-muted-foreground">1,234 students</span>
                    </div>
                    <div className="flex items-center gap-1">
                      <Star className="w-4 h-4 fill-yellow-400 text-yellow-400" />
                      <span className="text-sm font-semibold">4.8</span>
                    </div>
                  </div>
                  <Button className="w-full mt-4" variant="default">
                    Learn More
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>
          <div className="text-center mt-12">
            <Button size="lg" variant="outline" className="shadow-soft hover:shadow-medium transition-smooth">
              View All Courses
            </Button>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12 animate-slide-up">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">Why Choose Us</h2>
            <p className="text-xl text-muted-foreground">The best platform for online learning</p>
          </div>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {features.map((feature, index) => (
              <Card key={index} className="text-center border-2 transition-smooth hover:border-primary hover:shadow-medium">
                <CardContent className="pt-6">
                  <div className="inline-flex items-center justify-center w-16 h-16 rounded-2xl gradient-hero mb-4">
                    <feature.icon className="w-8 h-8 text-primary-foreground" />
                  </div>
                  <h3 className="font-bold text-lg mb-2">{feature.title}</h3>
                  <p className="text-muted-foreground">{feature.description}</p>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials Section */}
      <section className="py-20 bg-muted/30">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12 animate-slide-up">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">What Our Students Say</h2>
            <p className="text-xl text-muted-foreground">Join thousands of satisfied learners</p>
          </div>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            {testimonials.map((testimonial, index) => (
              <Card key={index} className="transition-smooth hover:shadow-medium">
                <CardContent className="pt-6">
                  <div className="flex items-center gap-4 mb-4">
                    <div className="w-14 h-14 rounded-full bg-gradient-hero flex items-center justify-center text-2xl text-primary-foreground font-bold">
                      {testimonial.name.charAt(0)}
                    </div>
                    <div>
                      <h4 className="font-semibold">{testimonial.name}</h4>
                      <p className="text-sm text-muted-foreground">{testimonial.role}</p>
                    </div>
                  </div>
                  <div className="flex gap-1 mb-4">
                    {[...Array(testimonial.rating)].map((_, i) => (
                      <Star key={i} className="w-4 h-4 fill-yellow-400 text-yellow-400" />
                    ))}
                  </div>
                  <p className="text-muted-foreground italic">"{testimonial.text}"</p>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 gradient-hero text-primary-foreground relative overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-br from-primary-foreground/10 to-transparent" />
        <div className="container mx-auto px-4 text-center relative z-10">
          <h2 className="text-4xl md:text-5xl font-bold mb-6">Start Learning Today</h2>
          <p className="text-xl mb-8 text-primary-foreground/90 max-w-2xl mx-auto">
            Join millions of students learning on our platform and unlock your potential
          </p>
          <Button size="lg" variant="secondary" className="shadow-strong hover:shadow-medium transition-smooth hover:scale-105">
            Sign Up Now - It's Free!
          </Button>
        </div>
      </section>
    </div>
  );
};

export default Index;
