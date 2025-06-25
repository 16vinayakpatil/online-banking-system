import React, { useEffect } from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/js/dist/dropdown';
import 'bootstrap/js/dist/collapse';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import BannerImage from "../assets/istockphoto-1212757122-612x612.jpg";
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
              <span className="fs-5 fw-bolder d-none d-sm-inline">Employee Dashboard</span>
            </a>
            <ul className="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">

              <li>
                <Link to="/employee/employeeprofile" className="nav-link px-0 align-middle text-white">
                  <i className="fs-4 bi-person"></i> <span className="ms-1 d-none d-sm-inline">Profile</span>
                </Link>
              </li>
              <li>
                <a href="#submenu2" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Accounts</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu2" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/employee/allaccounts" className="nav-link text-white" aria-current="page">All Accounts</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/pendingaccounts" className="nav-link text-white" aria-current="page">Pending Accounts</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/activateaccount" className="nav-link text-white" aria-current="page">Activate Account</Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link to="/employee/customerlist" className="nav-link px-0 align-middle text-white">
                  <i className="fs-4 bi-person"></i> <span className="ms-1 d-none d-sm-inline">Customer</span>
                </Link>
              </li>
              <li>
                <a href="#submenu1" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Transaction</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu1" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/employee/employeetransaction" className="nav-link text-white" aria-current="page">All Transaction</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu3" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Loan</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu3" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/employee/employeepending" className="nav-link text-white" aria-current="page">Pending Loans</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/activateloan" className="nav-link text-white" aria-current="page">Activate Loan</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/closeloan" className="nav-link text-white" aria-current="page">Close Loans</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/allloan" className="nav-link text-white" aria-current="page">All Loans</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu4" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Credit Cards</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu4" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/employee/allcreditcards" className="nav-link text-white" aria-current="page">All Credit Cards</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/pendingcreditcardrequest" className="nav-link text-white" aria-current="page">Credit Card Pending Request</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/activatecreditcard" className="nav-link text-white" aria-current="page">Activate Credit Card</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/closingcreditcardrequest" className="nav-link text-white" aria-current="page">Credit Card Closing Request</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/closecreditcard" className="nav-link text-white" aria-current="page">Close Credit Card</Link>
                  </li>
                </ul>
              </li>
              <li>
                <a href="#submenu5" className="nav-link px-0 align-middle text-white" data-bs-toggle="collapse">
                  <i className="bi bi-grid"></i> <span className="ms-2">Locker</span> <i className="bi bi-arrow-down-short ms-3"></i>
                </a>
                <ul className="collapse nav ms-2 flex-column" id="submenu5" data-bs-parent="#menu">
                  <li className="nav-item">
                    <Link to="/employee/alllockers" className="nav-link text-white" aria-current="page">All Lockers</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/activatelocker" className="nav-link text-white" aria-current="page">Activate Locker</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/closelocker" className="nav-link text-white" aria-current="page">Close Locker</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/closingrequest" className="nav-link text-white" aria-current="page">All Closing Request</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/employee/pendingrequest" className="nav-link text-white" aria-current="page">Pending Locker Request</Link>
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
