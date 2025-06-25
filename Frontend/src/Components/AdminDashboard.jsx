import React, { useEffect } from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/js/dist/dropdown';
import 'bootstrap/js/dist/collapse';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import BannerImage from "../assets/payment-customer-commerce-lifestyle-laptop_1150-1701.webp";
import axios from 'axios';
import './Dashboard.css';

function Dashboard() {
  const navigate = useNavigate();
  useEffect(() => {
    // Disable scrolling when the component mounts
    document.body.style.overflow = 'hidden';

    // Re-enable scrolling when the component unmounts
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, []);

  const containerStyle = {
    backgroundImage: `url(${BannerImage})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
  };


  return (
    <div className="container-fluid" style={containerStyle}>
      <div className="row flex-nowrap">
        <div className="col-auto col-md-4 col-xl-3 px-sm-4 px-0 bg-dark">
          <div className="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
            <a href="/" className="d-flex align-items-center pb-3 mb-md-1 mt-md-3 me-md-auto text-white text-decoration-none">
              <span className="fs-5 fw-bolder d-none d-sm-inline">Admin Dashboard</span>
            </a>
            <ul className="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
              <li>
                <Link to="/admin/profile" className="nav-link px-0 align-middle text-white">
                  <i className="fs-4 bi-person"></i> <span className="ms-1 d-none d-sm-inline">Profile</span>
                </Link>
              </li>
              <li>
                <Link to="/admin/employeelist" className="nav-link px-0 align-middle text-white">
                  <i className="bi bi-grid"></i> <span className="ms-1 d-none d-sm-inline">Employee</span>
                </Link>
              </li>
              <li>
                <Link to="/start" className="nav-link px-0 align-middle text-white">
                  <i className="fs-4 bi-power"></i> <span className="ms-1 d-none d-sm-inline">Logout</span>
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="col p-0 m-0">
          <div className="p-2 d-flex justify-content-center shadow">
            <h4 style={{ color: 'white' }}>Bank Management System</h4>


          </div>
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
