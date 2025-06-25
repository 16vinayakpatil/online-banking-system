import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PendingAccounts.css';
import config from './Configuration/config';

const PendingAccountsTable = ({ jwt }) => {
  const [accounts, setAccounts] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchPendingAccounts();
  }, []);
  const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
    console.log(token);

  const fetchPendingAccounts = () => {
    axios
      .get(`http://localhost:${config.port}/login/employee/pending-accounts`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setAccounts(response.data);
      })
      .catch((error) => {
        setErrorMessage(error.response.data);
      });
  };

  return (
    // // <div className="pending-accounts-table-container" style={{ overflowY: 'scroll', maxHeight: '400px' }}>
    //   {/* // <div> <h2 style={{ textAlign: 'center' }}>Pending Accounts</h2>
    //   // {errorMessage && <p className="error-message">{errorMessage}</p>}
    //   // <div style={{ overflowY: 'scroll', maxHeight: '400px' }}> */}
      
        <table className="pending-accounts-table" style={{ overflowY: 'auto', maxHeight: '400px' }}>
          <thead>
            <tr>
              <th>Account ID</th>
              <th>User ID</th>
              <th>Account Number</th>
              <th>Account Type</th>
              <th>IFSC Code</th>
              <th>Balance</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {accounts.map((account) => (
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
      //  </div>
    // </div>
    // </div>
  );
};

export default PendingAccountsTable;
