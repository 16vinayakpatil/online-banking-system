import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const CloseCreditCardRequestForm = () => {
  const [creditCardNumber, setCreditCardNumber] = useState('');

  const token = localStorage.getItem('jwt');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.put(
        `http://localhost:${config.port}/creditcard/customer/close-credit-card-request`,
        { creditcardnumber: creditCardNumber },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      toast.success(response.data);
      setCreditCardNumber('');
    } catch (error) {
      console.error(error);
      if (error.response && error.response.data) {
        toast.error(error.response.data);
      } else {
        toast.error('An error occurred. Please try again.');
      }
    }
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '310px',
    margin: '90px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '350px',
    border: '1px solid black',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  };

  const buttonStyle = {
    marginTop: '30px',
    padding: '8px 16px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    alignSelf: 'center', 
  };

  return (
    <div style={formContainerStyle}>
      <h2 style={{ textAlign: 'center' }}>Close Credit Card Request</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label style={labelStyle}>
            Credit Card Number:
            <input
              type="text"
              value={creditCardNumber}
              onChange={(e) => setCreditCardNumber(e.target.value)}
              required
            />
          </label>
        </div>
        <button type="submit" style={buttonStyle}>
          Close Request
        </button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default CloseCreditCardRequestForm;
