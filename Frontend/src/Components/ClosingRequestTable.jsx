import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllLoans.css';
import config from './Configuration/config';

const LockerTable = ({ jwt }) => {
  const [lockerData, setLockerData] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchLockerData();
  }, []);
  const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
  console.log(token);
  const fetchLockerData = () => {
    axios
      .get(`http://localhost:${config.port}/locker/employee/closing-locker-requests`, {
        headers: {
            'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then((response) => {
        setLockerData(response.data);
        
      })
      .catch((error) => {
        setErrorMessage(error.response.data);
      });
  };

  return (
    <div><h2 style={{ textAlign: 'center' }}>All Closing Requests</h2>
    <div className="loan-table-container" >
      
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
        <table className="loan-table">
          <thead>
            <tr>
              <th>Locker ID</th>
              <th>Locker Type</th>
              <th>Locker Location</th>
              <th>Locker Size</th>
              <th>Locker Price</th>
              <th>Locker Status</th>
              </tr>
          </thead>
          <tbody>
            {lockerData.map((locker) => (
              <tr key={locker.lockerid}>
                <td>{locker.lockerid}</td>
                <td>{locker.lockertype}</td>
                <td>{locker.lockerlocation}</td>
                <td>{locker.lockersize}</td>
                <td>{locker.lockerprice}</td>
                <td>{locker.lockerstatus}</td>
                </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
    </div>
  );
};

export default LockerTable;
