import React, { useState } from 'react';
import './style.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../utility/auth';
import { useLocation } from 'react-router';
import config from './Configuration/config';

function Login() {
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const auth = useAuth();
  const nav = useNavigate();
  const location = useLocation();
  // const redirectPath = location.state?.path || '/';

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (!username || !password) {
      setError('Please fill in both username and password fields.');
      return;
    }
  
    try {
      const user = {
        username: username,
        password: password,
      };
  
      const payload = JSON.stringify(user);
  
      const res = await axios.post(`http://localhost:${config.port}/login/admin/authenticate`, payload, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      const jwt = res.data;
      localStorage.setItem('jwt', jwt);
      auth.login(username, password, jwt);
      nav('/admin/profile', { replace: true });
    } catch (error) {
      setError('Invalid credentials. Please check your username and password.');
      console.error(error);
    }
  };
  

  return (
    <div className='d-flex justify-content-center align-items-center vh-100 loginPage'>
      <div className='p-3 rounded w-25 border loginForm'>
        <div className='text-danger'>{error}</div>
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
          <button type='button' className='btn btn-primary w-100 rounded-0' onClick={() => nav('/start')}>
            Back
          </button>
          <p>You agree to our terms and policies</p>
        </form>
       
      </div>
    </div>
  );
}

export default Login;
