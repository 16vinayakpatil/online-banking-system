import React, { useState, useEffect } from 'react';
import axios from 'axios';
import config from './Configuration/config';

  const GiftCardTable = () => {
    const [giftCards, setGiftCards] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchCreditCardData();
  }, []);

  const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage

  const fetchCreditCardData = () => {
    axios
      .get(`http://localhost:${config.port}/giftcard/customer/my-giftcards-purchased`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then((response) => {
        setGiftCards(response.data);
      })
      .catch((error) => {
        setErrorMessage(error.response.data);
      });
  };

  return (
    <div>  <h2 style={{ textAlign: 'center' }}>My Gift Cards</h2>
    <div className="credit-card-table-container" >
     
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
        <table className="credit-card-table">
          <thead>
            <tr>
            <th>Gift Card Name</th>
           <th>Recipient Name</th>
             <th>Recipient Email</th>
             <th>Gift Card Amount</th>
            </tr>
          </thead>
          <tbody>
          {giftCards.map(giftCard => (
           <tr key={giftCard.giftcardname}>
               <td>{giftCard.giftcardname}</td>
               <td>{giftCard.recipientname}</td>
               <td>{giftCard.recipientemail}</td>
               <td>{giftCard.giftcardamount}</td>
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
