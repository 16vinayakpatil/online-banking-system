import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const BankTransferForm = () => {
  const [accountNumber, setAccountNumber] = useState('');
  const [ifscCode, setIFSCCode] = useState('DEMO0015014');
  const [username, setUsername] = useState('');
  const [amount, setAmount] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (!accountNumber || !ifscCode || !username || !amount) {
      setErrorMessage('All fields are required.');
      return;
    }
    
   const token = localStorage.getItem('jwt');
    console.log(token);
   
    axios
      .put(
        `http://localhost:${config.port}/transaction/customer/bank-transfer`,
        {
          accno: accountNumber,
          ifsccode: ifscCode,
          username: username,
          amount: parseFloat(amount),
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        
        console.log(response.data); 
        // Clear form fields
        setAccountNumber('');
        setIFSCCode('');
        setUsername('');
        setAmount('');
        toast.success(response.data);
       
      })
      .catch((error) => {
        toast.error('An error occurred. Please try again.');
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '240px',
    margin: '10px auto 0',
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
    marginBottom: '5px',
  };

  const buttonStyle = {
    padding: '8px 16px',
    background: '#4caf50',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  };

  return (
    <div style={formContainerStyle}>
      <h2>Bank Transfer</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>Account Number:</label>
        <input
          type="number"
          id="giftcardname"
          value={accountNumber}
          onChange={event => setAccountNumber(event.target.value)}
          required
        />

       <label style={labelStyle}>IFSC Code:</label>
        <input
          type="text"
          id="recipientemail"
          value={ifscCode}
          onChange={event => setIFSCCode(event.target.value)}
          required
        />

        <label style={labelStyle}>Username:</label>
        <input
          type="text"
          id="giftcardamount"
          value={username}
          onChange={event => setUsername(event.target.value)}
          required
        />
         <label style={labelStyle}>Amount:</label>
        <input
          type="number"
          id="recipientname"
          value={amount}
          onChange={event => setAmount(event.target.value)}
          required
        />
        <button type="submit" style={buttonStyle}>Submit</button>
      </form>
       <ToastContainer />
     </div>
  );
};

export default BankTransferForm;



