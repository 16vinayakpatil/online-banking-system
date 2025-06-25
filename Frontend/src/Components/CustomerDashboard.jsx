import React, { useEffect } from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/js/dist/dropdown';
import 'bootstrap/js/dist/collapse';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Dashboard.css';
import BannerImage from "../assets/man-holding-credit-card-payment-shopping-online-smart-phone_34755-140.webp";

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
    // height: '100px'
  };

  return (
    <div className="container-fluid" style={containerStyle}>
      <div className="row flex-nowrap">
        <div className="col-auto col-md-4 col-xl-3 px-sm-4 px-0 bg-dark">
          <div className="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
            <a href="/" className="d-flex align-items-center pb-3 mb-md-1 mt-md-3 me-md-auto text-white text-decoration-none">
              <span className="fs-5 fw-bolder d-none d-sm-inline">Customer Dashboard</span>
            </a>
            <ul className="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
            
              <li>
                <Link to="/customer/customerprofile" className="nav-link px-0 align-middle text-white">
                  <i className="fs-4 bi-person"></i> <span className="ms-1 d-none d-sm-inline">Profile</span>
                </Link>
              </li>
              <li>
                <Link to="/customer/customeraccount" className="nav-link px-0 align-middle text-white">
                  <i className="bi bi-grid"></i> <span className="ms-1 d-none d-sm-inline">Account</span>
                </Link>
              </li>
              <li>
                <a href="#submenu1" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Transaction</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu1" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/customer/customertransaction" className="nav-link text-white" aria-current="page">My Transaction</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerdeposit" className="nav-link text-white" aria-current="page">Deposit</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerwithdraw" className="nav-link text-white" aria-current="page">Withdraw</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customertransfer" className="nav-link text-white" aria-current="page">Bank Transfer</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu2" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Loan</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu2" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/customer/customerallloans" className="nav-link text-white" aria-current="page">My Loans</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerapplyloan" className="nav-link text-white" aria-current="page">Apply For Loan</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerloanpayment" className="nav-link text-white" aria-current="page">Loan Payment</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu3" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Gift Cards</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu3" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/customer/customerallgiftcard" className="nav-link text-white" aria-current="page">My Gift Cards</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerbuygiftcard" className="nav-link text-white" aria-current="page">Buy Gift Cards</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu4" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Credit Cards</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu4" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/customer/customerallcreditcards" className="nav-link text-white" aria-current="page">My Credit Card</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerapplycreditcard" className="nav-link text-white" aria-current="page">Apply For Credit Card</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerclosecreditcard" className="nav-link text-white" aria-current="page">Close Credit Card</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customermakepayment" className="nav-link text-white" aria-current="page">Make Payment</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customeremicalculator" className="nav-link text-white" aria-current="page">Calculate EMI</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerpayemi" className="nav-link text-white" aria-current="page">Pay Balance</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu5" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Locker</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu5" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/customer/customermylocker" className="nav-link text-white" aria-current="page">My Locker</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerapplylocker" className="nav-link text-white" aria-current="page">Apply For Locker</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerlockerpayment" className="nav-link text-white" aria-current="page">Locker Payment</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/customer/customerclosinglocker" className="nav-link text-white" aria-current="page">Close Locker Request</Link>
                  </li>
                </ul>
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
            <h4>Bank Management System</h4>
          </div>
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
