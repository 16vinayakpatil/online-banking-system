import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const DialogueBox = () => {
  const [giftcardname, setGiftCardName] = useState('');
  const [recipientname, setRecipientName] = useState('');
  const [recipientemail, setRecipientEmail] = useState('');
  const [giftcardamount, setGiftCardAmount] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();

    const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
    console.log(token);
    const requestData = {
      giftcardname,
      recipientname,
      recipientemail,
      giftcardamount: parseFloat(giftcardamount),
    };

    axios
      .post(`http://localhost:${config.port}/giftcard/customer/buy-giftcard`, requestData, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      .then(response => {
        toast.success(response.data);
        setGiftCardName('');
        setRecipientName('');
        setRecipientEmail('');
        setGiftCardAmount('');
      })
      .catch(error => {
        console.error(error);
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
      <h2>Buy Gift Card</h2>
      <form onSubmit={handleSubmit}>
        <label style={labelStyle}>
        Gift Card Name:
          <select value={giftcardname} onChange={(e) => setGiftCardName(e.target.value)} required>
            <option value="">Select gift card name</option>
            <option value="Amazon Card">Amazon Card</option>
            <option value="Flipcart Card">Flipcart Card</option>
            <option value="Myntra Card">Myntra Card</option>
            <option value="Meesho Card">Meesho Card</option>
            <option value="Blinkit Card">Blinkit Card</option>
            <option value="Swiggy Card">Swiggy Card</option>
            <option value="Zomato Card">Zomato Card</option>
          </select>
        </label>

        <label style={labelStyle}>Recipient Name:</label>
        <input
          type="text"
          id="recipientname"
          value={recipientname}
          onChange={event => setRecipientName(event.target.value)}
        />

        <label style={labelStyle}>Recipient Email:</label>
        <input
          type="email"
          id="recipientemail"
          value={recipientemail}
          onChange={event => setRecipientEmail(event.target.value)}
        />

        <label style={labelStyle}>Gift Card Amount:</label>
        <input
          type="number"
          id="giftcardamount"
          value={giftcardamount}
          onChange={event => setGiftCardAmount(event.target.value)}
        /><button type="submit" style={buttonStyle}>Submit</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default DialogueBox;
