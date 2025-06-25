import React, { useState } from 'react'

import { Link, navigate, Router } from 'react-router-dom'
import { useNavigate } from 'react-router-dom';


import axios from 'axios';
import { Button } from 'react-bootstrap';
import config from './Configuration/config';

function Register() {
  const navigate = useNavigate()

  /*const handleSignup = () => {

      navigate('/login');
  }; */


  const [values, setValues] = useState({
   mobileNumber: '',
    email: '',
    position: '',
    firstName: '',
    lastName: '',
    fatherName: '',
    motherName: '',
    dob: '',
    gender: '',
    aadharNumber: '',
    panNumber: '',
    branchName: '',
    branchCode: '',
    ifsc: '',
    username: '',
    password: '',
    address: '',
    phone: ''
  });

  const [errors, setErrors] = useState({});

  const handleInput = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // TODO: Form validation and error handling
    console.log(values);

    axios
      .post(`http://localhost:${config.port}/login/customer/register`, values, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        console.log(res);
        // TODO: Handle successful registration
      })
      .catch((error) => {
        console.log(error);
        // TODO: Handle registration errors
      });
  };

  return (
    <div className='d-flex justify-content-center align-items-center bg-secondary bg-opacity-25 vh-100'>
      <div className='mb-3 bg-white p-3 rounded w-35'>
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          {/* Render other fields similarly */}
          <div className='mb-3'>
            <label htmlFor='mobileNumber'>
              <strong>Mobile Number</strong>
            </label>
            <input
              type='text'
              placeholder='Enter Mobile Number'
              name='mobileNumber'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.mobileNumber && (
              <span className='text-danger'>{errors.mobileNumber}</span>
            )}
          </div>

          <div className='mb-3'>
            <label htmlFor='email'>
              <strong>Email</strong>
            </label>
            <input
              type='email'
              placeholder='Enter Email'
              name='email'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.email && (
              <span className='text-danger'>{errors.email}</span>
            )}
          </div>

          <div className='mb-3'>
            <label htmlFor='position'>
              <strong>Position</strong>
            </label>
            <input
              type='text'
              placeholder='Enter Position'
              name='position'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.position && (
              <span className='text-danger'>{errors.position}</span>
            )}
          </div>

          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          <div className='mb-3'>
            <label htmlFor='firstName'>
              <strong>First Name</strong>
            </label>
            <input
              type='text'
              placeholder='Enter First Name'
              name='firstName'
              onChange={handleInput}
              className='form-control rounded-0'
            />
            {errors.firstName && (
              <span className='text-danger'>{errors.firstName}</span>
            )}
          </div>
          

          {/* Render other fields similarly */}

          <Button type='submit' className='btn btn-success w-100 rounded-0'>
            Register
          </Button>
          <p>You agree to our terms and policies</p>
        </form>
      </div>
    </div>
  );
}

export default Register;












