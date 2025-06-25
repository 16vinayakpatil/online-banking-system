import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const ActivateAccountForm = () => {
  const [accid, setAccid] = useState('');
 
  const handleInputChange = (e) => {
    setAccid(e.target.value);
  };
  const token = localStorage.getItem('jwt'); 
  console.log(token);
  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
  
    const headers = {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    };
  
    axios
      .put(`http://localhost:${config.port}/login/employee/activate-account`, { accid }, { headers })
      .then((response) => {
        toast.success(response.data);
        setAccid('');
      })
      .catch((error) => {
        if (error.response && error.response.data) {
          toast.error(error.response.data.error);
        } else {
          toast.error('An error occurred. Please try again.');
        }
      });
  };

  const formContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '290px',
    margin: '110px auto 0',
    padding: '10px',
    borderRadius: '10px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)',
    backgroundColor: 'rgba(242, 242, 242, 0.8)',
    height: '310px',
    border: '1px solid black',
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

  const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  };
  return (
    <div style={formContainerStyle}>
      <h2>Activate Account</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>Account ID:</label>
        <input type="number" value={accid} onChange={handleInputChange} />
        <button type="submit" style={buttonStyle} >Activate</button>
      </form>
      <ToastContainer />
     </div>
  );
};

export default ActivateAccountForm;
