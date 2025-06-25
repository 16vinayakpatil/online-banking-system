import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const CreditCardApplicationForm = ({ jwt }) => {
  const [creditCardName, setCreditCardName] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
  
    axios
      .post(
        `http://localhost:${config.port}/creditcard/customer/apply-for-credit-card`,
        {
          creditcardname: creditCardName,
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
        setCreditCardName('');
      })
      .catch((error) => {
        console.error(error);
        if (error.response && typeof error.response.data === 'string') {
          // Check if the error response contains the error message as a string
          const errorMessage = error.response.data;
          if (errorMessage.includes('active credit card')) {
            // Credit card application failed due to an active credit card with the same name
            toast.error("You already have ghe same credit card");
          } else {
            // Other errors, show generic error message
            toast.error(errorMessage);
          }
        } else {
          // Other errors, show generic error message
          toast.error("You already have the same credit card");
        }
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '280px',
    margin: '90px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '330px',
    border: '1px solid black',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  };

  const buttonStyle = {
    marginTop: '20px',
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
      <h2>Apply Credit Card</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          Credit Card Name:
          <select value={creditCardName} onChange={(e) => setCreditCardName(e.target.value)} required>
            <option value="">Select credit card name</option>
            <option value="IndianOil Axis Bank Credit Card">IndianOil Axis Bank Credit Card</option>
            <option value="Flipkart Axis Bank Credit Card">Flipkart Axis Bank Credit Card</option>
            <option value="Axis Bank MY Zone Credit Card">Axis Bank MY Zone Credit Card</option>
            <option value="Axis Bank Vistara Credit Card">Axis Bank Vistara Credit Card</option>
            <option value="IRCTC SBI Platinum Card">IRCTC Axis Platinum Card</option>
            <option value="Air India SBI Platinum Card">Air India Axis Platinum Card</option>
          </select>
        </label>
        <button type="submit" style={buttonStyle}>Apply</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default CreditCardApplicationForm;
