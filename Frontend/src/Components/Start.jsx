import React from 'react';
import { useNavigate } from 'react-router-dom';

function Start() {
  const navigate = useNavigate();

  return (
    <div className='d-flex justify-content-center align-items-center vh-100 loginPage'>
      <div className='p-4 rounded w-35 border loginForm text-center'>
        <h2 className='btn btn-info'>Login As</h2>
        <button
          className='btn btn-warning btn-lg position-absolute top-0 end-0 m-3'
          onClick={() => navigate('/')}
        >
          Home
        </button>
        <div className='d-flex justify-content-between mt-5'>
          <button className='btn btn-success btn-lg' onClick={() => navigate('/adminlogin')}>
            Admin
          </button>
          <button className='btn btn-primary btn-lg' onClick={() => navigate('/customerlogin')}>
            Customer
          </button>
          <button className='btn btn-secondary btn-lg' onClick={() => navigate('/employeelogin')}>
            Employee
          </button>
        </div>
      </div>
    </div>
  );
}

export default Start;
