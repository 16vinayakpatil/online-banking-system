import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllLoans.css';
import config from './Configuration/config';

const LockerTable = ({ jwt }) => {
  const [accountData, setAccountData] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchLockerData();
  }, []);
  const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
  console.log(token);
  const fetchLockerData = () => {
    axios
      .get(`http://localhost:${config.port}/login/employee/all-accounts`, {
        headers: {
            'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then((response) => {
        setAccountData(response.data);
      })
      .catch((error) => {
        setErrorMessage(error.response.data);
      });
  };

  return (
    <div className="loan-table-container" style={{ overflowY: 'scroll', maxHeight: '400px' }}>
      <h2 style={{ textAlign: 'center' }}>All Account Details</h2>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div>
        <table className="loan-table" >
          <thead>
            <tr>
              <th>Account ID</th>
              <th>User ID</th>
              <th>Account No.</th>
              <th>Account Type</th>
              <th>IFSCC Code</th>
              <th>Balance</th>
              <th>Status</th>
              </tr>
          </thead>
          <tbody>
            {accountData.map((account) => (
              <tr key={account.accid}>
                <td>{account.accid}</td>
                <td>{account.userid}</td>
                <td>{account.accno}</td>
                <td>{account.acctype}</td>
                <td>{account.ifsccode}</td>
                <td>{account.balance}</td>
                <td>{account.status}</td>
                </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LockerTable;
