import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllLoans.css';
import config from './Configuration/config';

const CreditCardTable = ({ jwt }) => {
  const [creditCardData, setCreditCardData] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchCreditCardData();
  }, []);

  const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage

  const fetchCreditCardData = () => {
    axios
      .get(`http://localhost:${config.port}/creditcard/employee/credit-card-closing-requests`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then((response) => {
        setCreditCardData(response.data);
      })
      .catch((error) => {
        setErrorMessage(error.response.data);
      });
  };

  return (
    <div> <h2 style={{ textAlign: 'center' }}>Closing Credit Card Requests</h2>
    <div className="loan-table-container" >
 
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
        <table className="loan-table">
          <thead>
          <tr>
              <th>Card Id </th>
              <th>Card Name</th>
              <th>Card Number</th>
              <th>Card CVV</th>
              <th>Card Limit</th>
              <th>Card Expiry Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {creditCardData.map((creditCard) => (
                <tr key={creditCard.creditcardid}>
                <td>{creditCard.creditcardid}</td>
                <td>{creditCard.creditcardname}</td>
                <td>{creditCard.creditcardnumber}</td>
                <td>{creditCard.creditcardcvv}</td>
                <td>{creditCard.creditcardlimit}</td>
                <td>{creditCard.expirydate}</td>
                <td>{creditCard.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
    </div>
  );
};

export default CreditCardTable;
