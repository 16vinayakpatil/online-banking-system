import React from 'react';
import { useNavigate } from 'react-router-dom';
import onlineBankingImage from './assets/bankingmain.jpg';
import vendorManagementImage from './assets/vendormain.jpg';

function Main() {
  const containerStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    backgroundImage: 'linear-gradient(135deg, lightblue, pink, lavender)',
  };

  const boxStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    width: '250px', // Updated width
    height: '350px', // Updated height
    margin: '10px',
    backgroundColor: 'white',
    borderRadius: '10px',
    boxShadow: '0 2px 6px rgba(0, 0, 0, 0.15)',
    textAlign: 'center',
  };

  const imageStyle = {
    width: '100%',
    height: '200px', // Updated height
    objectFit: 'cover',
    borderRadius: '10px',
  };

  const textContainerStyle = {
    padding: '10px',
    fontWeight: 'bold', // Added fontWeight property
  };

  const nav = useNavigate();

  const handleButton1Click = () => {
    // Navigate to another page when Button 1 is clicked
    nav('/home1');
  };

  const handleButton2Click = () => {
    // Navigate to another page when Button 2 is clicked
    nav('/home2');
  };

  return (
    <div style={containerStyle}>
      <div style={boxStyle}>
        <button style={{ ...boxStyle, padding: '0' }} onClick={handleButton1Click}>
          <img src={onlineBankingImage} alt="Online Banking Portal" style={imageStyle} />
        </button>
        <div style={textContainerStyle}><b>Online Banking Portal</b></div>
      </div>
      <div style={boxStyle}>
        <button style={{ ...boxStyle, padding: '0' }} onClick={handleButton2Click}>
          <img src={vendorManagementImage} alt="Vendor Management Tool" style={imageStyle} />
        </button>
        <div style={textContainerStyle}><b>Vendor Productivity Management Tool</b></div>
      </div>
    </div>
  );
}

export default Main;
