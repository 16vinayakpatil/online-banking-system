import React, { useState } from 'react';
import './style.css';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../utility/auth';
import { useLocation } from 'react-router';
import { toast, ToastContainer } from 'react-toastify';
import config from './Configuration/config';

function Login() {
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [loginAttempts, setLoginAttempts] = useState(0);
  const auth = useAuth();
  const nav = useNavigate();
  const location = useLocation();
  // const redirectPath = location.state?.path || "/";

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!username || !password) {
      toast.error('Please enter both username and password.');
      return;
    }

    const user = {
      username: username,
      password: password,
    };

    axios
      .post(`http://localhost:${config.port}/login/customer/authenticate`, user, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then((res) => {
        if (res.status === 200 && res.data) {
          // Successful login
          const jwt = res.data;
          localStorage.setItem('jwt', jwt);
          auth.login(username, password, jwt);
          toast.success('Logged in successfully.');
          nav('/customer/customerprofile', { replace: true });
        } else if (res.status === 403) {
          // Account blocked
          toast.error('Your account is blocked. Check your email for unblock link.');
        } else {
          // Other errors
          setLoginAttempts((prevAttempts) => prevAttempts + 1);
          const attemptsLeft = 3 - loginAttempts;
          if (attemptsLeft > 0) {
            toast.error(`Invalid username/password. Attempts left: ${attemptsLeft}`);
          } else {
            toast.error('Your account is blocked. Check your email for unblock link.');
          }
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 403) {
          // Account blocked
          toast.error('Your account is blocked. Check your email for unblock link.');
        } else {
          // Other errors
          setLoginAttempts((prevAttempts) => prevAttempts + 1);
          const attemptsLeft = 3 - loginAttempts;
          if (attemptsLeft > 0) {
            toast.error(`Invalid username/password. Attempts left: ${attemptsLeft}`);
          } else {
            toast.error('Your account is blocked. Check your email for unblock link.');
          }
        }
      });
  };

  return (
    <div className='d-flex justify-content-center align-items-center vh-100 loginPage'>
      <div className='p-3 rounded w-25 border loginForm'>
        <div className='text-danger'></div>
        <h2 style={{ textAlign: 'center' }}>Login</h2>
        <form onSubmit={handleSubmit} style={{ width: '100%' }}>
          <div className='mb-3'>
            <label htmlFor='username'>
              <strong>Username</strong>
            </label>
            <input
              type='text'
              placeholder='Enter Username'
              name='username'
              value={username}
              onChange={(e) => setUserName(e.target.value)}
              className='form-control rounded-0'
              autoComplete='off'
            />
          </div>
          <div className='mb-3'>
            <label htmlFor='password'>
              <strong>Password</strong>
            </label>
            <input
              type='password'
              placeholder='Enter Password'
              name='password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className='form-control rounded-0'
            />
          </div>
          <button type='submit' className='btn btn-success w-100 rounded-0'>
            Log in
          </button>
        
          <button
            type='button'
            className='btn btn-success w-100 rounded-0 mt-3'
            onClick={() => nav('/start')}
          >
            Back
          </button>
          <p>You are agreeing to our terms and policies</p>
        </form>
        <ToastContainer />
      </div>
    </div>
  );
}

export default Login;
