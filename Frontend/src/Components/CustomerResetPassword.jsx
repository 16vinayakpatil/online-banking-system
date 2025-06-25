// src/components/ForgotPassword.js
import React, { useState } from 'react';
import axios from 'axios';
import { Link,useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';
const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState(''); // OTP field
  const [newPassword, setNewPassword] = useState('');
  const nav = useNavigate();
  const handleSubmit = (e) => {
    e.preventDefault();
    const forgotPasswordRequest = {
      username: email,
      otp: otp, // Include OTP in the request
      newPassword: newPassword, // Include new password in the request
    };
    axios
      .post(`http://localhost:${config.port}/login/customer/reset-password`, forgotPasswordRequest, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        toast.success(res.data); // Show success toast
        setEmail('');
        setOtp('');
        setNewPassword('');
        nav('/customerlogin');
      })
      .catch((error) => {
        toast.error('Error occurred while processing the request.'); // Show error toast
      });
  };
  return (
    <div className='d-flex justify-content-center align-items-center vh-100 loginPage'>
      <div className='p-3 rounded w-25 border loginForm'>
        <h2 style={{ textAlign: 'center' }}>Reset Password</h2>
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
          <div className='mb-3'>
                <label htmlFor="otp"><strong>OTP</strong></label>
                <input
                  type="text"
                  placeholder='Enter OTP'
                  name='otp'
                  value={otp}
                  onChange={(e) => setOtp(e.target.value)}
                  className='form-control rounded-0'
                  autoComplete='off'
                />
              </div>
              <div className='mb-3'>
                <label htmlFor="newPassword"><strong>New Password</strong></label>
                <input
                  type="password"
                  placeholder='Enter New Password'
                  name='newPassword'
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  className='form-control rounded-0'
                  autoComplete='off'
                />
              </div>
          <button type='submit' className='btn btn-success w-100 rounded-0'>
            Reset
          </button>
        </form>
        <ToastContainer /> {/* Toast container to display notifications */}
      </div>
    </div>
  );
};
export default ForgotPassword;