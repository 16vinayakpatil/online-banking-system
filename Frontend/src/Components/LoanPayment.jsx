import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const DialogueBox = () => {
  const [loantype, setLoanType] = useState('');
  const [loanemi, setLoanEmi] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();

    const token = localStorage.getItem('jwt');
    const requestData = {
      loantype,
      loanemi: parseFloat(loanemi),
    };

    setIsSubmitting(true);

    axios
      .put(`http://localhost:${config.port}/loan/customer/loan-payment`, requestData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        if (response.data.includes('successfully')) {
          toast.success('EMI Payment is done successfully', { toastId: 'success-toast' });
          setLoanType('');
          setLoanEmi('');
        } else {
          toast.error(response.data, { toastId: 'error-toast' });
        }
        setIsSubmitting(false);
      })
      .catch((error) => {
        console.error('Error:', error);
        if (error.response && error.response.status === 400) {
          toast.error('Loan payment cannot be processed until the loan status is active.', {
            toastId: 'error-toast',
          });
        } else {
          toast.error('Payment failed', { toastId: 'error-toast' });
        }
        setIsSubmitting(false);
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '290px',
    margin: '70px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '390px',
    border: '1px solid black',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '5px',
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

  return (
    <div style={formContainerStyle}>
      <h2>Loan Payment</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          Loan Type:
          <select value={loantype} onChange={(e) => setLoanType(e.target.value)} required>
            <option value="">Select a loan type</option>
            <option value="Home Loan">Home Loan</option>
            <option value="Personal Loan">Personal Loan</option>
            <option value="Vehicle Loan">Vehicle Loan</option>
            <option value="Education Loan">Education Loan</option>
            <option value="Car Loan">Car Loan</option>
          </select>
        </label>
        <label htmlFor="loanemi">Loan EMI:</label>
        <input
          type="number"
          id="loanemi"
          value={loanemi}
          onChange={(event) => setLoanEmi(event.target.value)}
        />

        <button type="submit" style={buttonStyle} disabled={isSubmitting}>
          {isSubmitting ? 'Submitting...' : 'Submit'}
        </button>
      </form>

      <ToastContainer />
    </div>
  );
};

export default DialogueBox;
