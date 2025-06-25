// src/components/ForgotPassword.js
import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';
const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const handleSubmit = (e) => {
    e.preventDefault();
    const forgotPasswordRequest = {
      username: email,
    };
    axios
      .post(`http://localhost:${config.port}/login/employee/forgot-password`, forgotPasswordRequest, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        toast.success(res.data); // Show success toast
        setEmail('');
      })
      .catch((error) => {
        toast.error('Error occurred while processing the request.'); // Show error toast
      });
  };
  return (
    <div className='d-flex justify-content-center align-items-center vh-100 loginPage'>
      <div className='p-3 rounded w-25 border loginForm'>
        <h2 style={{ textAlign: 'center' }}>Forgot Password</h2>
        <form onSubmit={handleSubmit} style={{ width: '100%' }}>
          <div className='mb-3'>
            <label htmlFor="email"><strong>Email</strong></label>
            <input
              type="email"
              placeholder='Enter Email'
              name='email'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className='form-control rounded-0'
              autoComplete='off'
            />
          </div>
          <button type='submit' className='btn btn-success w-100 rounded-0'>
            Send OTP
          </button>
          <Link to="/employee-reset-password" className="btn btn-success w-100 rounded-0 mt-3">
            Continue to Reset Password
          </Link>
        </form>
        <ToastContainer />
      </div>
    </div>
  );
};
export default ForgotPassword;