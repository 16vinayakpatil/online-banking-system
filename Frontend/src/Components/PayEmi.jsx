import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const ApplyLoanForm = ({ jwt }) => {
  const [emiAmount, setEMIAmount] = useState('');
  const [creditCardNumber, setCreditCardNumber] = useState('');
  const [creditCardCVV, setCreditCardCVV] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    setIsSubmitting(true);

    axios
      .post(
        `http://localhost:${config.port}/creditcard/customer/pay-emi`, // Replace with the correct backend endpoint URL
        {
          emiAmount: parseFloat(emiAmount),
          creditcardnumber: creditCardNumber,
          creditcardcvv: creditCardCVV,
          expirydate: expiryDate,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        setIsSubmitting(false);
        if (response.status === 200) {
          toast.success(response.data);
          setEMIAmount('');
          setCreditCardNumber('');
          setCreditCardCVV('');
          setExpiryDate('');
        } else {
          toast.error(response.data);
        }
      })
      .catch((error) => {
        setIsSubmitting(false);
        console.error(error);
        toast.error('An error occurred. Please try again.');
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '350px',
    margin: '15px auto',
    padding: '20px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: '#f2f2f2',
    border: '1px solid #333',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
    fontSize: '16px',
  };

  const inputStyle = {
    padding: '8px',
    fontSize: '16px',
    borderRadius: '4px',
    border: '1px solid #ccc',
  };

  const buttonStyle = {
    padding: '10px 20px',
    background: '#4CAF50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
    margin: '10px 0',
  };

  return (
    <div style={formContainerStyle}>
      <h2>Pay EMI</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          EMI Amount:
          <input
            type="number"
            value={emiAmount}
            onChange={(e) => setEMIAmount(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <label style={labelStyle}>
          Credit Card Number:
          <input
            type="number"
            value={creditCardNumber}
            onChange={(e) => setCreditCardNumber(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <label style={labelStyle}>
          Credit Card CVV:
          <input
            type="number"
            value={creditCardCVV}
            onChange={(e) => setCreditCardCVV(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <label style={labelStyle}>
          Expiry Date:
          <input
            type="date"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
            style={inputStyle}
            required
          />
        </label>
        <button type="submit" style={buttonStyle} disabled={isSubmitting}>
          {isSubmitting ? 'Submitting...' : 'Pay EMI'}
        </button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default ApplyLoanForm;
