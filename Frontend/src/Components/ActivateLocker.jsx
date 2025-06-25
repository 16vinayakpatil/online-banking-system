import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const ActivateLockerForm = () => {
  const [lockerId, setLockerId] = useState('');

  const token = localStorage.getItem('jwt');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.put(
        `http://localhost:${config.port}/locker/employee/activate-locker`,
        { lockerid: lockerId },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      if (response.status === 200) {
        toast.success(response.data);
        setLockerId('');
      } else {
        toast.error('An error occurred. Please try again.');
      }
    } catch (error) {
      if (error.response) {
        // The request was made, but the server responded with an error
        if (error.response.status === 404) {
          toast.error('Locker not found. Please enter a valid Locker ID.');
        } else if (error.response.status === 409) {
          toast.error('Locker is already active.');
        } else if (error.response.status === 403) {
          toast.error('Locker cannot be activated until the customer pays the locker amount. An email notification has been sent to the customer.');
        } else {
          toast.error('An error occurred. Please try again.');
        }
      } else {
        // Something else happened while making the request
        toast.error('An error occurred. Please check your internet connection and try again.');
      }
    }
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '260px',
    margin: '110px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '350px',
    border: '1px solid black',
  };

  const buttonStyle = {
    padding: '8px 16px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  };

  return (
    <div style={formContainerStyle}>
      <h2 style={{ textAlign: 'center' }}>Activate Locker</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label style={labelStyle}>Locker ID:</label>
          <input
            type="number"
            id="lockerId"
            value={lockerId}
            onChange={(e) => setLockerId(e.target.value)}
            required
          />
        </div>
        <button type="submit" style={buttonStyle}>Activate</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default ActivateLockerForm;
