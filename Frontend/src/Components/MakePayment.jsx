import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';
const ApplyLoanForm = ({ jwt }) => {
  const [amount, setAmount] = useState('');
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
        `http://localhost:${config.port}/creditcard/customer/make-payment`, // Replace with your backend endpoint for payment
        {
          amount: parseFloat(amount),
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
          setAmount('');
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
  const formContainerStyle1 = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '310px',
    margin: '15px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '530px',
    border: '1px solid black',
  };
  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
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
    <div style={formContainerStyle1}>
      <h2>Make Payment</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          Amount:
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
          />
        </label>
        <label style={labelStyle}>
          Credit Card Number:
          <input
            type="number"
            value={creditCardNumber}
            onChange={(e) => setCreditCardNumber(e.target.value)}
            required
          />
        </label>
        <label style={labelStyle}>
          Credit Card CVV:
          <input
            type="number"
            value={creditCardCVV}
            onChange={(e) => setCreditCardCVV(e.target.value)}
            required
          />
        </label>
        <label style={labelStyle}>
          Expiry Date:
          <input
            type="date"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
            required
          />
        </label>
        <button type="submit" style={buttonStyle} disabled={isSubmitting}>
          {isSubmitting ? 'Submitting...' : 'Apply'}
        </button>
      </form>
      <ToastContainer />
    </div>
  );
};
export default ApplyLoanForm;