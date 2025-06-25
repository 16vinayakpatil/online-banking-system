import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const ApplyLoanForm = ({ jwt }) => {
  const [loanType, setLoanType] = useState('');
  const [loanAmount, setLoanAmount] = useState('');
  const [duration, setDuration] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoanTypeApplied, setIsLoanTypeApplied] = useState(false);

  useEffect(() => {
    // Check if the user already has an active loan of the same type
    const token = localStorage.getItem('jwt');
    axios
      .get('http://localhost:8083/customer/loan/my-loans', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const activeLoans = response.data.filter((loan) => loan.status === 'active');
        if (activeLoans.some((loan) => loan.loantype === loanType)) {
          setIsLoanTypeApplied(true);
        } else {
          setIsLoanTypeApplied(false);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }, [loanType]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    setIsSubmitting(true);

    axios
      .post(
        `http://localhost:${config.port}/loan/customer/apply-loan`,
        {
          loantype: loanType,
          loanamount: parseFloat(loanAmount),
          duration: parseInt(duration),
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
        if (response.data.includes('Loan application failed')) {
          setIsLoanTypeApplied(true);
          toast.error(response.data);
        } else {
          setIsLoanTypeApplied(false);
          toast.success(response.data);
          setLoanType('');
          setLoanAmount('');
          setDuration('');
        }
      })
      .catch((error) => {
        // setIsSubmitting(false);
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
    margin: '20px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '520px',
    border: '1px solid black',
  };

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '5px',
  };

  const buttonStyle = {
    padding: '10px 20px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
    margin: '10px 0',
  };

  const errorStyle = {
    color: 'red',
    marginBottom: '10px',
  };

  return (
    <div style={formContainerStyle}>
      <h2>Apply for Loan</h2>
      {isLoanTypeApplied && <div style={errorStyle}>You have already applied for the {loanType} loan.</div>}
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
          Loan Type:
          <select value={loanType} onChange={(e) => setLoanType(e.target.value)} required>
            <option value="">Select a loan type</option>
            <option value="Home Loan">Home Loan</option>
            <option value="Personal Loan">Personal Loan</option>
            <option value="Vehicle Loan">Vehicle Loan</option>
            <option value="Education Loan">Education Loan</option>
            <option value="Car Loan">Car Loan</option>
          </select>
        </label>
        <br />
        <label style={labelStyle}>
          Loan Amount:
          <input
            type="number"
            value={loanAmount}
            onChange={(e) => setLoanAmount(e.target.value)}
            required
          />
        </label>
        <br />
        <label style={labelStyle}>
          Duration (in months):
          <input
            type="number"
            value={duration}
            onChange={(e) => setDuration(e.target.value)}
            required
          />
        </label>
        <br />
        <button type="submit" style={buttonStyle} disabled={isSubmitting}>
          {isSubmitting ? 'Submitting...' : 'Apply'}
        </button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default ApplyLoanForm;
