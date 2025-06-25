import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const CalculateEMIForm = ({ jwt }) => {
  const fixedInterestRate = 0.12;
  const fixedTenureMonths = 6;
  const [loanAmount, setLoanAmount] = useState('');
  const [emiAmount, setEMIAmount] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    axios
      .get(`http://localhost:${config.port}/creditcard/customer/calculate-emi`, {
        params: {
          loanAmount: loanAmount,
          interestRate: fixedInterestRate,
          tenureMonths: fixedTenureMonths,
        },
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setEMIAmount(response.data);
        setLoanAmount('');
        toast.success('EMI calculated successfully!');
      })
      .catch((error) => {
        console.error(error);
        toast.error('An error occurred. Please try again.');
      });
  };

  const formContainerStyle2 = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '310px',
    margin: '100px auto 0',
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
    <div style={formContainerStyle2}>
      <h2>Calculate EMI</h2>
      <form onSubmit={handleSubmit}>
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
        <button type="submit" style={buttonStyle}>
          Calculate EMI
        </button>
      </form>
      {emiAmount !== null && (
        <div>
          <h3>EMI Amount: {emiAmount.toFixed(2)}</h3>
        </div>
      )}
      <ToastContainer />
    </div>
  );
};

export default CalculateEMIForm;
