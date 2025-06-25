import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllLoans.css';
import config from './Configuration/config';

const LoanTable = () => {
  const [loanData, setLoanData] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchLoanData();
  }, []);

  const fetchLoanData = () => {
    const token = localStorage.getItem('jwt');
    axios
      .get(`http://localhost:${config.port}/loan/customer/my-loans`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response.data); // Check the received data in the console
        setLoanData(response.data);
      })
      .catch((error) => {
        setErrorMessage(error.response?.data || 'An error occurred. Please try again.');
      });
  };

  const getStatusStyle = (status) => {
    const lowerCaseStatus = status.toLowerCase();
    if (lowerCaseStatus === 'active') {
      return { color: 'green' }; // Active loans displayed with green text
    } else if (lowerCaseStatus === 'pending') {
      return { color: 'red' }; // Pending loans displayed with red text
    } else if (lowerCaseStatus === 'completed') {
      return { color: 'blue' }; // Completed loans displayed with yellow text
    }
    return { color: 'black' }; // Default color for other statuses
  };

  return (
    <div>
      <h2 style={{ textAlign: 'center' }}>My Loans</h2>
      <div className="loan-table-container">
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
          <table className="loan-table">
            <thead>
              <tr>
                <th>Loan ID</th>
                <th>Loan Type</th>
                <th>Outstanding Loan Amount</th>
                <th>Monthly EMI</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Duration</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {loanData.map((loan) => (
                <tr key={loan.loanid}>
                  <td>{loan.loanid}</td>
                  <td>{loan.loantype}</td>
                  <td>${loan.loanamount}</td>
                  <td>${loan.monthlyemi}</td>
                  <td>{loan.statedate}</td>
                  <td>{loan.enddate}</td>
                  <td>{loan.duration} months</td>
                  <td style={getStatusStyle(loan.status)}>{loan.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default LoanTable;
