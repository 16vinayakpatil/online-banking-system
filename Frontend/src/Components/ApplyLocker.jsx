import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const LockerPaymentForm = ({ jwt }) => {
  const [lockertype, setLockerType] = useState('');
  const [lockersize, setLockerSize] = useState('');
  const [lockerlocation, setLockerLocation] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    console.log(token);
    axios
      .post(
        `http://localhost:${config.port}/locker/customer/apply-for-locker`,
        {
          lockertype: lockertype,
          lockersize: lockersize,
          lockerlocation: lockerlocation,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        toast.success(response.data);
        setLockerType('');
        setLockerSize('');
        setLockerLocation('');
      })
      .catch((error) => {
        console.error(error);
        toast.error('An error occurred. Please try again.');
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '310px',
    margin: '30px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '500px',
    border: '1px solid black',
  };
  const buttonStyle = {
    padding: '8px 16px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    // alignSelf: 'center',
  };


  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  };

  return (
    <div style={formContainerStyle}>
      <h2 >Apply Locker</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
        Locker Type:
          <select value={lockertype} onChange={(e) => setLockerType(e.target.value)} required>
            <option value="">Select a locker type</option>
            <option value="Temporary Locker">Temporary Locker</option>
            <option value="Personal Locker">Personal Locker</option>
            <option value="Self Deposit Locker">Self Deposit Locker</option>
            <option value="Business Locker">Business Locker</option>
            <option value="Shared Locker">Shared Locker</option>
            <option value="Digital Locker">Digital Locker</option>
            <option value="Keyless Locker">Keyless Locker</option>
          </select>
        </label>
        <label style={labelStyle}>
        Locker Size:
          <select value={lockersize} onChange={(e) => setLockerSize(e.target.value)} required>
            <option value="">Select a locker size</option>
            <option value="Small">Small</option>
            <option value="Medium">Medium</option>
            <option value="Large">Large</option>
            <option value="Extra Large">Extra Large</option>
           </select>
        </label>
        <label style={labelStyle}>
        Locker Location:
          <select value={lockerlocation} onChange={(e) => setLockerLocation(e.target.value)} required>
            <option value="">Select a locker location</option>
            <option value="Mumbai">Mumbai</option>
            <option value="Pune">Pune</option>
            <option value="Delhi">Delhi</option>
            <option value="Bengaluru">Bengaluru</option>
            <option value="Hyderabad">Hyderabad</option>
          </select>
        </label>
        <button type="submit" style={buttonStyle}>Apply</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default LockerPaymentForm;
