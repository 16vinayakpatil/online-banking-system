import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AllGiftCard.css';
import config from './Configuration/config';

const GiftCardTable = () => {
  const [loans, setLoans] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
    console.log(token);
    axios
      .get(`http://localhost:${config.port}/loan/employee/pending-loans`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then(response => {
        // Handle the response from the server
        console.log(response.data);
        setLoans(response.data); // Update the state with the received gift cards
      })
      .catch(error => {
        // Handle errors
        console.error('Error:', error);
      });
  }, []);

  return (
    <div>
    <h2  style={{ textAlign: 'center' }}>Pending Loans</h2>
    <div className="gift-card-table" >
        
        <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
      <table>
        <thead>
          <tr>
            <th>LoanId:</th>
            <th>LoanType</th>
            <th>LoanAmount</th>
            <th>MonthlyEmi</th>
            <th>Statedate</th>
            <th>EndDate</th>
            <th>Duration</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {loans.map(loan => (
            <tr key={loan.loanid}>
                <td>{loan.loanid}</td>
              <td>{loan.loantype}</td>
              <td>{loan.loanamount}</td>
              <td>{loan.monthlyemi}</td>
              <td>{loan.statedate}</td>
              <td>{loan.enddate}</td>
              <td>{loan.duration}</td>
              <td>{loan.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
    </div>
    </div>
  );
};

export default GiftCardTable;
