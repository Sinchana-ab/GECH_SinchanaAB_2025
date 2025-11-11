// // pages/Dashboard.jsx
// import React from 'react';
// import { useAuth } from '../context/AuthContext';
// import { useNavigate } from 'react-router-dom';

// const Dashboard = () => {
//   const { user, isAdmin, isInstructor, isStudent } = useAuth();
//   const navigate = useNavigate();

//   return (
//     <div className="container mt-4">
//       <div className="row">
//         <div className="col-md-12">
//           <h2 className="mb-4">Welcome, {user?.name}!</h2>
          
//           <div className="row">
//             {isStudent && (
//               <>
//                 <div className="col-md-4 mb-4">
//                   <div 
//                     className="card text-center shadow-sm cursor-pointer h-100"
//                     onClick={() => navigate('/courses')}
//                     style={{ cursor: 'pointer' }}
//                   >
//                     <div className="card-body">
//                       <i className="bi bi-book-fill text-primary" style={{ fontSize: '3rem' }}></i>
//                       <h5 className="card-title mt-3">Browse Courses</h5>
//                       <p className="card-text">Explore available courses and enroll</p>
//                     </div>
//                   </div>
//                 </div>
                
//                 <div className="col-md-4 mb-4">
//                   <div 
//                     className="card text-center shadow-sm cursor-pointer h-100"
//                     onClick={() => navigate('/my-courses')}
//                     style={{ cursor: 'pointer' }}
//                   >
//                     <div className="card-body">
//                       <i className="bi bi-journals text-success" style={{ fontSize: '3rem' }}></i>
//                       <h5 className="card-title mt-3">My Courses</h5>
//                       <p className="card-text">View your enrolled courses and progress</p>
//                     </div>
//                   </div>
//                 </div>
                
//                 <div className="col-md-4 mb-4">
//                   <div 
//                     className="card text-center shadow-sm cursor-pointer h-100"
//                     onClick={() => navigate('/certificates')}
//                     style={{ cursor: 'pointer' }}
//                   >
//                     <div className="card-body">
//                       <i className="bi bi-award-fill text-warning" style={{ fontSize: '3rem' }}></i>
//                       <h5 className="card-title mt-3">Certificates</h5>
//                       <p className="card-text">View and download your certificates</p>
//                     </div>
//                   </div>
//                 </div>
//               </>
//             )}
            
//             {isInstructor && (
//               <>
//                 <div className="col-md-6 mb-4">
//                   <div 
//                     className="card text-center shadow-sm cursor-pointer h-100"
//                     onClick={() => navigate('/instructor')}
//                     style={{ cursor: 'pointer' }}
//                   >
//                     <div className="card-body">
//                       <i className="bi bi-mortarboard-fill text-primary" style={{ fontSize: '3rem' }}></i>
//                       <h5 className="card-title mt-3">Instructor Panel</h5>
//                       <p className="card-text">Manage your courses and students</p>
//                     </div>
//                   </div>
//                 </div>
                
//                 <div className="col-md-6 mb-4">
//                   <div 
//                     className="card text-center shadow-sm cursor-pointer h-100"
//                     onClick={() => navigate('/courses')}
//                     style={{ cursor: 'pointer' }}
//                   >
//                     <div className="card-body">
//                       <i className="bi bi-plus-circle-fill text-success" style={{ fontSize: '3rem' }}></i>
//                       <h5 className="card-title mt-3">Create Course</h5>
//                       <p className="card-text">Design and publish new courses</p>
//                     </div>
//                   </div>
//                 </div>
//               </>
//             )}
            
//             {isAdmin && (
//               <div className="col-md-12 mb-4">
//                 <div 
//                   className="card text-center shadow-sm cursor-pointer"
//                   onClick={() => navigate('/admin')}
//                   style={{ cursor: 'pointer' }}
//                 >
//                   <div className="card-body">
//                     <i className="bi bi-shield-fill-check text-danger" style={{ fontSize: '3rem' }}></i>
//                     <h5 className="card-title mt-3">Admin Panel</h5>
//                     <p className="card-text">Manage users, courses, and system settings</p>
//                   </div>
//                 </div>
//               </div>
//             )}
//           </div>
          
//           <div className="row mt-4">
//             <div className="col-md-12">
//               <div className="card">
//                 <div className="card-header bg-primary text-white">
//                   <h5 className="mb-0">Profile Information</h5>
//                 </div>
//                 <div className="card-body">
//                   <div className="row">
//                     <div className="col-md-6">
//                       <p><strong>Name:</strong> {user?.name}</p>
//                       <p><strong>Email:</strong> {user?.email}</p>
//                     </div>
//                     <div className="col-md-6">
//                       <p><strong>Role:</strong> 
//                         <span className="badge bg-primary ms-2">
//                           {user?.role?.replace('ROLE_', '')}
//                         </span>
//                       </p>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Dashboard;



import React, { useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const { user, isAdmin, isInstructor, isStudent } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    // Redirect based on role
    if (isAdmin) {
      navigate('/admin');
    } else if (isInstructor) {
      navigate('/instructor');
    } else if (isStudent) {
      navigate('/student');
    }
  }, [user, isAdmin, isInstructor, isStudent, navigate]);

  return (
    <div className="container mt-4">
      <div className="text-center">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
        <p className="mt-3">Redirecting to your dashboard...</p>
      </div>
    </div>
  );
};

export default Dashboard;