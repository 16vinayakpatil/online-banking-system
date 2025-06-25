import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const LockerPaymentForm = ({ jwt }) => {
  const [email, setEmail] = useState('');
  const [lockerId, setLockerId] = useState('');
  const [lockertype, setLockerType] = useState('');
  const [lockeramount, setLockerAmount] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    axios
      .put(
        `http://localhost:${config.port}/locker/customer/locker-payment`,
        {
          email: email,
          lockerid: lockerId,
          lockertype: lockertype,
          lockeramount: parseInt(lockeramount),
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        if (response.data.startsWith('Locker payment failed.')) {
          toast.error(response.data);
        } else {
          toast.success(response.data);
          setEmail('');
          setLockerId('');
          setLockerType('');
          setLockerAmount('');
        }
      })
      .catch((error) => {
        console.error(error);
        toast.error('An error occurred. Please try again.');
      });
  };

  const formContainerStyle = {
    maxWidth: '400px',
    margin: '0 auto',
    padding: '20px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
    fontSize: '14px',
  };

  const inputStyle = {
    padding: '8px',
    borderRadius: '4px',
    border: '1px solid #ccc',
  };

  const selectStyle = {
    padding: '8px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '100%',
  };

  const buttonStyle = {
    padding: '8px 16px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    marginTop: '10px',
  };

  return (
    <div style={formContainerStyle}>
      <h2>Locker Payment</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          Email:
          <input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <label style={labelStyle}>
          Locker ID:
          <input
            type="number"
            value={lockerId}
            onChange={(e) => setLockerId(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <label style={labelStyle}>
          Locker Type:
          <select
            value={lockertype}
            onChange={(e) => setLockerType(e.target.value)}
            style={selectStyle}
            required
          >
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
          Locker Amount:
          <input
            type="number"
            value={lockeramount}
            onChange={(e) => setLockerAmount(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <button type="submit" style={buttonStyle}>
          Submit
        </button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default LockerPaymentForm;
